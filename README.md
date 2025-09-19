# portfolio-littleforest


# 🌳 Little Forest

**개발기간** : 2025.07.23 ~ 2025.09.08 (총 44일)  
**참여인원** : 5명  
**담당업무** : 제안, 기획, 포인트 적립 시스템 정립, 담당 파트 기능 개발 및 디자인  
**개발환경** : Tomcat 8.5, Oracle DB  
**사용도구** : Figma  
**사용기술** : Spring Boot, MyBatis, Java, JavaScript, SQL, API, HTML, CSS, JSON  

---

## 📖 개요
그린핀테크는 환경적 지속가능성을 높이는 금융기술로, 디지털 기술과 녹색금융(Green Finance), 기후·환경 데이터를 활용해 기후변화와 탄소중립에 대응하는 금융 서비스이다.  
리틀포레스트는 개인 소비자의 교통, 에너지, 쇼핑 등의 데이터를 기록한 디지털 가계부를 기반으로 친환경 소비를 관리하고, 일상 속 소비가 탄소 배출 감축 활동으로 이어지도록 지원한다.  
또한 탄소 감축 효과를 시각화하여 사용자들이 습관적으로 탄소 절감에 참여할 수 있도록 돕는 프로젝트이다.

---

## 💡 기획 의도(동기)
2026년부터는 대기업뿐만 아니라 금융회사들도 온실가스 배출량을 공시해야 하는 ‘금융배출량(financed emissions)’의 시대가 열린다.  
이에 따라 기업뿐 아니라 개인도 자신의 탄소배출량을 직관적으로 확인하고 줄이기 위한 전략이 필요하다.  
리틀포레스트는 이러한 변화에 대응하여 개인의 지출 내역 기반 탄소 가계부와 포인트 보상 시스템을 제공, 친환경 소비와 저탄소 생활 습관을 유도한다.  

---

## 🎯 목표 및 설계
### 1. 팀 목표
- 소비자의 계좌·결제 내역을 기반으로 탄소 소비 분석 및 시각화  
- 친환경 소비에 따른 **그린포인트 적립** 제공  
- **나무 키우기(게이미피케이션)** 기능과 친환경 제품 구매 활용  
- 사용자들의 탄소 중립 금융 습관 형성 유도  

### 2. 담당 파트 목표
- 직관적이고 단순한 포인트 적립 시스템 설계  
- 소비에 따른 적립 방법과 감축 효과를 쉽게 이해할 수 있는 UX 구현  
- 소비 내역 기반 탄소 감축량 시각화 → 작은 행동의 긍정적 효과 체감  

---

## 🛠️ 담당 역할
### (1) 지갑/포인트/결제 관리
- 은행별 탭 전환 및 통장 UI 시각화  
- 거래 내역 기반 포인트 자동 적립  
- 결제 상세 내역 → CO₂ 절감량 산출  

### (2) 포인트 기부/선물/충전
- confetti.js 효과 & 기부 증빙 UI  
- 사용자 간 포인트 선물 기능  
- 카카오페이 API 활용 충전  

### (3) 탄소중립 포인트 조회/시각화
- NetZero Point API (CODEF) 연동  
- Chart.js 기반 월별/연간 절감량 그래프  

### (4) 적립 방법 & 회사 소개
- 친환경 활동 이미지 기반 직관적 안내  
- Deepl API 다국어 번역 지원  
- 카운트업.js 활용 시각적 효과  

---

<details>
<summary>📷 화면 구성</summary>


|구분 | 화면 | 미리보기 |
|----------|----------|----------|
|공통| 메인화면 | <img width="746" alt="image" src="https://github.com/user-attachments/assets/6c95713b-f8a5-45d2-8f28-81abb075366f" /> |
|공통| 회원가입 (주소api) | <img width="736" alt="image" src="https://github.com/user-attachments/assets/a63c4f22-351f-4f1b-9cf2-77495a53f5b2" /> |
|공통| 회사소개 (deeplapi) | <img width="749" height="353" alt="image" src="https://github.com/user-attachments/assets/c69ed595-ebd2-4a51-ad87-b41359c4900c" /> <img width="748" height="355" alt="image" src="https://github.com/user-attachments/assets/f7027617-4d7f-410b-b9e1-210869e166dc" /> <img width="746" height="355" alt="image" src="https://github.com/user-attachments/assets/f7f50b56-008b-4d6f-9d11-45536bc7129a" /> |
|공통| 적립방법 | <img width="741" height="357" alt="image" src="https://github.com/user-attachments/assets/8a2a88a6-7c8c-4e01-9127-9d2c8ea8a6e0" /> |
|유저| 유저채팅 | <img width="743" height="353" alt="image" src="https://github.com/user-attachments/assets/4b0193dc-6e41-40aa-b39d-199032c6cb70" /> |
|관리자| 관리자채팅| <img width="734" alt="image" src="https://github.com/user-attachments/assets/4a3681b6-f76b-4af9-bb9b-a54391470a99" /> |
|유저| 나의 지갑| <img width="750" alt="image" src="https://github.com/user-attachments/assets/cebe28e8-1a11-47d9-a746-792e7d503d28" /> |
|유저| 포인트 페이지 (다크모드) | <img width="750" alt="image" src="https://github.com/user-attachments/assets/7655bfd8-61e4-431e-9b09-cffaaaeaefeb" /> |
|유저| 기부하기 | <img width="750" alt="image" src="https://github.com/user-attachments/assets/7304c463-6872-4e76-a511-597c1f5811a7" /> <img width="750" alt="image" src="https://github.com/user-attachments/assets/abd40aa5-6683-48eb-acd2-4a65caa3b6e2" /> |
|유저| 포인트 선물하기 | <img width="939" height="489" alt="image" src="https://github.com/user-attachments/assets/a8533b00-eadc-40da-9436-0e70ec017ba7" /> |
|유저| 포인트 보상 (광고보기) | <img width="734" height="353" alt="image" src="https://github.com/user-attachments/assets/8fb213cb-c670-4eda-8576-5500eaf88d0d" /> |
|유저| 결제내역 | <img width="750" alt="image" src="https://github.com/user-attachments/assets/ebd3e3b7-aa2b-4999-8f33-78757fc65fc8" /> |
|유저| 탄소중립포인트 조회 (codef api) | <img width="800" alt="image" src="https://github.com/user-attachments/assets/3e310db2-d359-4e80-800a-8a51e0b81a4d" /> <img width="939" height="398" alt="image" src="https://github.com/user-attachments/assets/57d90ae7-59d9-4cb0-a458-77a82b214710" /> |
|유저| 나무키우기 | <img width="727" height="353" alt="image" src="https://github.com/user-attachments/assets/28763204-a6a3-4d5b-8893-591e5d7eccac" /> <img width="729" height="352" alt="image" src="https://github.com/user-attachments/assets/aabe8c4e-0b32-43e6-8520-8741f818a189" /> |
|유저| 나무키우기 (출석보상) | <img width="711" height="354" alt="image" src="https://github.com/user-attachments/assets/1a7b7dd3-69a9-45c6-a3ee-ccc215bc1875" /> |
|유저| 나무키우기 (비료구매) |<img width="715" height="353" alt="image" src="https://github.com/user-attachments/assets/66a2d028-e088-45b3-88ba-fb50acebe37d" /> |
|유저| 나무키우기 (랜덤잡초) | <img width="719" height="350" alt="image" src="https://github.com/user-attachments/assets/413bc60e-c334-47ce-b593-d3e86586ea3b" /> |
|유저| 탄소감축량 (차트&달력) | <img width="666" height="356" alt="image" src="https://github.com/user-attachments/assets/f267278d-d173-4b7b-8a17-6c4ed5ec5a1f" /> <img width="665" height="353" alt="image" src="https://github.com/user-attachments/assets/dc93e05c-7c57-43d3-a2f1-81b395c710e8" /> |
|관리자| 회원통계 | <img width="782" height="356" alt="image" src="https://github.com/user-attachments/assets/21becd59-5e0c-4aed-b2ec-a913c9756fca" /> <img width="780" height="356" alt="image" src="https://github.com/user-attachments/assets/a2bc5a0c-9142-4e86-9fed-ce83bc06bb17" /> |

</details>

---
## 📊 ERD & 테이블 설계
ERD 이미지 첨부 (https://drive.google.com/file/d/1c2CmWteDQokKMabPMcZ7VuJjKofZd2Uz/view?usp=drive_link)

<details>
<summary>📷 ERD 이미지 더보기</summary>
  
<img width="398" height="592" alt="image" src="https://github.com/user-attachments/assets/bcb4685c-67ec-40bf-91e1-251c93e85e41" />
<img width="383" height="620" alt="화면 캡처 2025-09-18 163515" src="https://github.com/user-attachments/assets/7c7d80ee-11b2-4b6e-b78a-2f7b6e5f087a" />

</details>

주요 테이블:
- 사용자/인증: `user_table`, `attendance_table`  
- 지갑/결제: `wallet_table`, `payment_table`, `merchant_table`  
- 탄소/포인트: `emission_table`, `point_table`, `point_event_table`  
- 나무 키우기: `growing_tree_table`, `grown_tree_table`  
- 커뮤니티: `community_table`, `comment_table`, `likes_table`  
- 고객지원: `inquery_table`, `answer_table`  
- 채팅: `chat_room_table`, `chat_message_table`  

---

## 📌 후기
- API 선정 과정에서 실질적 활용 가능성 검증의 중요성을 배움  
- Deepl API 다국어 지원 시 레이아웃 깨짐 문제 해결 경험  
- 결제 키워드 기반 포인트 적립 정책 설계 과정에서 비즈니스 로직 반영 훈련  

---

## 🏆 성과
- **NetZero Point API (CODEF) 연동 성공** → 실제 사용자별 탄소중립 포인트 데이터 조회  
- **Deepl API 번역 기능 구현** → 다국어 지원  
- **포인트 자동 적립 정책 설계** 및 안정화 

---

## 🎥 시연 자료
- [📺 시연 영상 보기](https://drive.google.com/file/d/1B79tO0RvvM4-UvIusbN7ap0b9GK5vbZp/view?usp=drive_link)  
- [📑 발표 자료 (PPT)](https://docs.google.com/presentation/d/16lXHTDZbE-LNdOH8F0PCaCt6K38miHoa/edit?usp=drive_link&ouid=115939005204624444347&rtpof=true&sd=true)
- [📑 발표 자료 (pdf)](https://drive.google.com/file/d/1R2O6azIVtrfG5PVHf0HQbu1ax7nbVQV5/view?usp=drive_link)
- [📑 UML](https://drive.google.com/file/d/1nqFyjvWFnB1mlrkAlK9wcyfHImJZQSMl/view?usp=drive_link)
