package com.fintecho.littleforest.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 애플리케이션 레벨 야간 롤업 스케줄러
 *
 * - 매일 02:10 : 일 → 월 (최근 12개월은 원본 emission_table 유지, 그 이전은 월합계로 접어서 보관 후 삭제)
 * - 매일 02:20 : 월 → 연 (최근 12개월의 월합계는 유지, 그 이전의 월합계는 연합계로 접어서 보관 후 삭제)
 *
 * DB 권한(스케줄러/GRANT) 없이도 동작. 애플리케이션이 대신 스케줄링함.
 */
@Component
@RequiredArgsConstructor
public class EmissionRollupSchedulerRaw {

  private final JdbcTemplate jdbc;

  /**
   * 최근 12개월의 "시작월(첫날 00:00)" SQL 표현식.
   * 예) 오늘이 2025-08이라면 2024-09-01 00:00이 됨.
   *  - 최근 12개월치 일 데이터는 원본테이블에 그대로 남겨두고
   *  - 그보다 과거(= KEEP_FROM 이전)는 월요약/연요약으로 접는다.
   */
  private static final String KEEP_FROM_EXPR =
      "TRUNC(ADD_MONTHS(TRUNC(SYSDATE,'MM'), -11), 'MM')";

  /**
   * 매일 02:10 - 일 ⇒ 월
   * 1) emission_table에서 KEEP_FROM 이전 데이터를 user/연/월로 SUM → emission_monthly_sum 으로 MERGE(있으면 +=, 없으면 INSERT)
   * 2) 방금 집계한 오래된 일 데이터는 emission_table에서 삭제
   */
  
  @Scheduled(cron = "0 10 2 * * *") // 초 분 시 일 월 요일
  @Transactional
  public void dailyToMonthly() {
    // 1) MERGE (원본 일 → 월 합계)
    //  - Java 17 이상이면 """ 텍스트 블록 사용 가능. (JDK8이라면 아래 주석의 + 연산 버전으로 바꿔 써도 됨)
    String mergeSql = """
      MERGE INTO emission_monthly_sum m
      USING (
        SELECT user_id,
               EXTRACT(YEAR  FROM emission_date) AS yyyy,
               EXTRACT(MONTH FROM emission_date) AS mm,
               SUM(emission) AS kg
          FROM emission_table
         WHERE emission_date < """ + KEEP_FROM_EXPR + """
         GROUP BY user_id, EXTRACT(YEAR FROM emission_date), EXTRACT(MONTH FROM emission_date)
      ) x
      ON (m.user_id = x.user_id AND m.yyyy = x.yyyy AND m.mm = x.mm)
      WHEN MATCHED THEN UPDATE SET m.kg = m.kg + x.kg
      WHEN NOT MATCHED THEN INSERT (user_id, yyyy, mm, kg) VALUES (x.user_id, x.yyyy, x.mm, x.kg)
      """;
    jdbc.update(mergeSql);

    // 2) DELETE (KEEP_FROM 이전의 일 데이터 정리)
    String deleteSql = """
      DELETE FROM emission_table
       WHERE emission_date < """ + KEEP_FROM_EXPR;
    jdbc.update(deleteSql);
  }

  /**
   * 매일 02:20 - 월 ⇒ 연
   * 1) emission_monthly_sum에서 KEEP_FROM 이전(= 작년 이전 등) 월 데이터를 user/연으로 SUM → emission_yearly_sum으로 MERGE
   * 2) 방금 집계한 오래된 월 데이터는 emission_monthly_sum에서 삭제
   */
  @Scheduled(cron = "0 20 2 * * *")
  @Transactional
  public void monthlyToYearly() {
    // 1) MERGE (월 합계 → 연 합계)
    String mergeYear = """
      MERGE INTO emission_yearly_sum y
      USING (
        SELECT user_id, yyyy, SUM(kg) AS kg
          FROM emission_monthly_sum
         WHERE TO_DATE(TO_CHAR(yyyy)||LPAD(mm,2,'0'),'YYYYMM') < """ + KEEP_FROM_EXPR + """
         GROUP BY user_id, yyyy
      ) x
      ON (y.user_id = x.user_id AND y.yyyy = x.yyyy)
      WHEN MATCHED THEN UPDATE SET y.kg = y.kg + x.kg
      WHEN NOT MATCHED THEN INSERT (user_id, yyyy, kg) VALUES (x.user_id, x.yyyy, x.kg)
      """;
    jdbc.update(mergeYear);

    // 2) DELETE (KEEP_FROM 이전 월 데이터 정리)
    String deleteMonth = """
      DELETE FROM emission_monthly_sum
       WHERE TO_DATE(TO_CHAR(yyyy)||LPAD(mm,2,'0'),'YYYYMM') < """ + KEEP_FROM_EXPR;
    jdbc.update(deleteMonth);
  }
}