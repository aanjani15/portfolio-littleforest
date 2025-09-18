package com.fintecho.littleforest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmissionController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 탄소 배출량 일별 합계 조회
    @GetMapping("/emissions")
    public Map<Integer, Integer> getMonthlyEmissions(@RequestParam int year, @RequestParam int month) {
        String sql = "SELECT EXTRACT(DAY FROM emission_date) AS day, SUM(emission) AS total " +
                     "FROM emission_table " +
                     "WHERE EXTRACT(YEAR FROM emission_date) = ? AND EXTRACT(MONTH FROM emission_date) = ? " +
                     "GROUP BY EXTRACT(DAY FROM emission_date)";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, year, month);

        Map<Integer, Integer> result = new HashMap<>();
        for (Map<String, Object> row : rows) {
            Integer day = ((Number) row.get("day")).intValue();
            Integer total = ((Number) row.get("total")).intValue();
            result.put(day, total);
        }
        return result;
    }

    // 결제 금액 일별 합계 조회
    @GetMapping("/payments")
    public Map<Integer, Integer> getMonthlyPayments(@RequestParam int year, @RequestParam int month) {
        String sql = "SELECT EXTRACT(DAY FROM tran_at) AS day, SUM(amount) AS total " +
                     "FROM payment_table " +
                     "WHERE EXTRACT(YEAR FROM tran_at) = ? AND EXTRACT(MONTH FROM tran_at) = ? " +
                     "GROUP BY EXTRACT(DAY FROM tran_at)";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, year, month);

        Map<Integer, Integer> result = new HashMap<>();
        for (Map<String, Object> row : rows) {
            Integer day = ((Number) row.get("day")).intValue();
            Integer total = ((Number) row.get("total")).intValue();
            result.put(day, total);
        }
        return result;
    }
}
