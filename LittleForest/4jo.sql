DROP TABLE user_table;
DROP TABLE attendance_table;
DROP TABLE wallet_table;
DROP TABLE payment_table;
DROP TABLE merchant_table;
DROP TABLE product_table;
DROP TABLE emission_table;
DROP TABLE growing_tree_table;
DROP TABLE grown_tree_table;
DROP TABLE donation_table;
DROP TABLE community_table;
DROP TABLE comment_table;
DROP TABLE likes_table;
DROP TABLE point_event_table;
DROP TABLE chat_room_table;
DROP TABLE chat_message_table;
DROP TABLE point_table;
DROP TABLE point_rule_table;
drop table stock_table;
drop table inquery_table;
drop table answer_table;

DROP SEQUENCE user_seq;
DROP SEQUENCE wallet_seq;
DROP SEQUENCE payment_seq;
DROP SEQUENCE merchant_seq;
DROP SEQUENCE product_seq;
DROP SEQUENCE growing_tree_seq;
DROP SEQUENCE grown_tree_seq;
DROP SEQUENCE donation_seq;
DROP SEQUENCE community_seq;
DROP SEQUENCE comment_seq;
DROP SEQUENCE likes_Seq;
DROP SEQUENCE point_event_seq;
DROP SEQUENCE point_seq;
DROP SEQUENCE point_rule_seq;
drop sequence stock_seq;
drop sequence inquery_Seq;
drop sequence answer_Seq;
--///////////////////////////////////////////////////////  CREATE  /////////////////////////////////////////////////////--

-- 1. users (사용자)
CREATE TABLE user_table (
    id             INT PRIMARY KEY,
    oauth_id       VARCHAR2(100),
    password       VARCHAR2(255),
    name           VARCHAR2(50),
    phone          VARCHAR2(20),
    birth          DATE,
    role           VARCHAR2(10) CHECK(role IN ('user', 'admin')),
    badge          VARCHAR2(100),
    created_at     DATE,
    address        VARCHAR2(200),
    email          VARCHAR2(250),
    nickname       VARCHAR2(50),
    point          INT,
    profile_photo  VARCHAR2(200)
);

-- 2. attendance (출석)
CREATE TABLE attendance_table (
    user_id     INT,
    attend_date DATE
);

-- 3. wallet (지갑)
CREATE TABLE wallet_table (
    id              INT PRIMARY KEY,
    user_id         INT,
    bank_name       VARCHAR2(100),
    account_balance INT,
    last_update     DATE,
    account_number  VARCHAR2(50)
);

-- 4. payments (지갑 내역)
CREATE TABLE payment_table (
    id          INT PRIMARY KEY,
    wallet_id   INT,
    product_id  INT,
    amount      INT,
    type        VARCHAR2(20),
    description CLOB,
    tran_at     TIMESTAMP
);

-- 5. merchants (가게)
CREATE TABLE merchant_table (
    id          INT PRIMARY KEY,
    admin_id    INT,
    name        VARCHAR2(100),
    address     VARCHAR2(200),
    phone       VARCHAR2(20),
    category    VARCHAR2(50)
);

-- 6. products (각 가게 상품)
CREATE TABLE product_table (
    id              INT PRIMARY KEY,
    merchants_id    INT,
    name            VARCHAR2(100),
    category        VARCHAR2(50),
    image_path      VARCHAR(100),
    price           INT,
    carbon_effect   FLOAT,
    description     CLOB
    
);

-- 7. emission (탄소 절감)
CREATE TABLE emission_table (
    user_id     INT,
    emission      INT,
    emission_date DATE,
    payment_id    INT             --  어떤 결제에서 발생했는지 추적
);

-- 8. growing_tree (키우고 있는 나무)
CREATE TABLE growing_tree_table (
    id              INT PRIMARY KEY,
    user_id         INT,
    tree_level      INT,
    tree_name       VARCHAR2(100),
    carbon_saved    FLOAT,
    water_count     INT,
    water_stock     INT,
    biyro_stock     INT,
    last_updated    DATE,
    biyro_used_at   DATE
);

-- 9. grwon_tree (다 키운 나무)
CREATE TABLE grown_tree_table (
    id              INT PRIMARY KEY,
    user_id         INT,
    growing_tree_id INT,
    complete_tree   DATE,
    ident_tree      VARCHAR2(100),
    photo varchar2(1000)
);

-- 10. donations (기부 내역)
CREATE TABLE donation_table (
    id              INT PRIMARY KEY,
    user_id         INT,
    amount          INT,
    donation_date   DATE,
    description     CLOB
);

-- 11. community (커뮤니티 글)
CREATE TABLE community_table (
    id          INT PRIMARY KEY,
    user_id     INT,
    type        VARCHAR2(50),
    title       VARCHAR2(200),
    content     CLOB,
    created_at  DATE,
    photo       VARCHAR2(200),
    likes       INT
);

-- 12. comment (커뮤니티 댓글)
CREATE TABLE comment_table (
    id          INT PRIMARY KEY,
    user_id     INT,
    community_id INT,
    comment_id  CLOB,
    created_at  DATE,
    likes       INT
);

-- 13. likes(좋아요 로그 관리)
CREATE TABLE likes_table (
    id          NUMBER(38) PRIMARY KEY,
    user_id     NUMBER(38) NOT NULL,
    table_type  VARCHAR2(20) NOT NULL,
    community_id INT,
    comment_id   INT,
    press_at     DATE,
    
    -- table_type과 id 컬럼 정합성 체크
    CONSTRAINT ck_like_type CHECK (
        (table_type = 'community' AND community_id IS NOT NULL AND comment_id IS NULL) OR
        (table_type = 'comment'   AND comment_id IS NOT NULL   AND community_id IS NULL)
    )
);
-- 커뮤니티 좋아요
CREATE UNIQUE INDEX uq_like_community
ON likes_table(
    (CASE WHEN table_type = 'community' THEN user_id ELSE NULL END),
    (CASE WHEN table_type = 'community' THEN community_id ELSE NULL END)
);

-- 댓글 좋아요
CREATE UNIQUE INDEX uq_like_comment
ON likes_table(
    (CASE WHEN table_type = 'comment' THEN user_id ELSE NULL END),
    (CASE WHEN table_type = 'comment' THEN comment_id ELSE NULL END)
);

-- 하루 1회 이벤트 적립 로그
CREATE TABLE point_event_table (
  id         INT        PRIMARY KEY,
  user_id    INT        NOT NULL,
  event_code VARCHAR2(50)  NOT NULL,  -- 어떤 이벤트인지 구분용 코드. 예) DAILY_TIP, COMMUNITY.
  claim_date DATE          NOT NULL,     -- “하루 1회”를 판단할 날짜 단위(시분초 00:00으로 취급)
  claimed_at DATE          DEFAULT SYSDATE NOT NULL, --실제 적립 시각(타임스탬프)
  amount     INT        NOT NULL,  --적립된 포인트
  memo       VARCHAR2(100),
  CONSTRAINT uq_pecl_user_event_date UNIQUE (user_id, event_code, claim_date) --unique 하루1회
);

--채팅 방
CREATE TABLE chat_room_table (
    id          INT,
    name        VARCHAR2(200),
    user_id     INT
);

--채팅 메세지
CREATE TABLE chat_message_table (
    chat_room_id INT,
    type VARCHAR2(20),
    sender VARCHAR2(200),
    content clob,
    timestamp TIMESTAMP
);


-- 포인트 테이블
CREATE TABLE point_table (
    id INT PRIMARY KEY,  
    user_id INT,   
    amount INT NOT NULL CHECK (amount <> 0),
    type VARCHAR2(10)  NOT NULL       -- 거래 유형
        CHECK (type IN ('EARN','SPEND','CHARGE','DONATE','GIFT')),
    counterparty_user_id INT, -- 선물 시 상대방 유저, 그 외 NULL
    memo VARCHAR2(200), -- 메모(선물 메시지 등)
    created_at DATE DEFAULT SYSDATE NOT NULL
);

-- 환경 실천 룰
CREATE TABLE point_rule_table (
  id           NUMBER PRIMARY KEY,
  keyword      VARCHAR2(100) UNIQUE NOT NULL,  -- 설명문에 포함될 키워드
  unit         VARCHAR2(20) NOT NULL,          -- 'CASE' | 'KM' | 'KG'
  reward       NUMBER NOT NULL                  -- 건/단위당 포인트
);
-- 재고 테이블 (다 키웠을때 재고가 남았을경우 재고가 저장)
create table stock_table(
id int primary key,
user_id int not null,
biyro_Stock int,
water_stock int,
biyro_used_at date,
save_date date,
update_date date);

-- 문의 테이블
create table inquery_table(
id int primary key,
user_id int not null,
category varchar2(50) not null,
title varchar2(200) not null,
content clob not null,
created_At date,
status varchar2(50));

--문의 답변테이블
create table answer_Table(
id int primary key,
user_id int not null,
inquery_id int not null,
answer_id int,
content clob,
created_at date);


--///////////////////////////////////////////////////////  시퀀스  /////////////////////////////////////////////////////--

-- user 테이블용 시퀀스
CREATE SEQUENCE user_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- wallet 테이블용 시퀀스
CREATE SEQUENCE wallet_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- payment 테이블용 시퀀스
CREATE SEQUENCE payment_seq
  START WITH 18
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- merchant 테이블용 시퀀스
CREATE SEQUENCE merchant_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- product 테이블용 시퀀스
CREATE SEQUENCE product_seq
  START WITH 5
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- growing_tree 테이블용 시퀀스
CREATE SEQUENCE growing_tree_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- grown_tree 테이블용 시퀀스
CREATE SEQUENCE grown_tree_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- donation 테이블용 시퀀스
CREATE SEQUENCE donation_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- community 테이블용 시퀀스
CREATE SEQUENCE community_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- comment 테이블용 시퀀스
CREATE SEQUENCE comment_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;
  
-- likes 테이블용 시퀀스
CREATE SEQUENCE likes_Seq
START WITH 1       -- 시작 번호
INCREMENT BY 1     -- 1씩 증가
NOCACHE            -- 캐시 사용 안함 (원하면 CACHE 가능)
NOCYCLE;           -- 최대값 도달해도 다시 1로 돌아가지 않음

  -- point_event 테이블용 시퀀스 
  CREATE SEQUENCE point_event_seq
  START WITH 1 
  INCREMENT BY 1 
  NOCACHE NOCYCLE;
  
  -- point_table 시퀀스
  CREATE SEQUENCE point_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
    
    CREATE SEQUENCE point_rule_seq 
    START WITH 1 
    INCREMENT BY 1 
    NOCACHE 
    NOCYCLE;
    
    CREATE SEQUENCE stock_Seq
    start with 1
    increment by 1
    nocache
    nocycle;
    
    create sequence inquery_seq
start with 1
increment by 1
nocache
nocycle;

    create sequence answer_seq
start with 1
increment by 1
nocache
nocycle;

  
  --///////////////////////////////////////////////////////  INSERT  /////////////////////////////////////////////////////--

Insert into USER_TABLE (ID,OAUTH_ID,PASSWORD,NAME,PHONE,BIRTH,ROLE,BADGE,CREATED_AT,ADDRESS,EMAIL,NICKNAME,POINT,PROFILE_PHOTO) values (user_seq.nextval,'oauth123','$2a$10$3Ea8nZsW84EhlcUxGmV.TObkq9hR4Lq0gnlrdRo7bKbzOLYs9nGCy','김민수',null,to_date('03/04/04','RR/MM/DD'),'user','초보자',to_date('23/08/01','RR/MM/DD'),'경기 의정부시 신촌로 18-7 바다앞아파트 1105동 905호 사서함','example1@exam.pleSww','imageDDsssew',120000,null);
Insert into USER_TABLE (ID,OAUTH_ID,PASSWORD,NAME,PHONE,BIRTH,ROLE,BADGE,CREATED_AT,ADDRESS,EMAIL,NICKNAME,POINT,PROFILE_PHOTO) values (user_seq.nextval,'oauth456','$2a$10$3Ea8nZsW84EhlcUxGmV.TObkq9hR4Lq0gnlrdRo7bKbzOLYs9nGCy','이지은','010-3333-2222',to_date('99/06/14','RR/MM/DD'),'admin','관리자',to_date('23/09/12','RR/MM/DD'),'부산광역시 해운대구','example2@exam.ple','jieun',3200,'https://res.cloudinary.com/dkaeihkco/image/upload/v1757292749/onoin3goqgwynbodfbjf.png');
Insert into USER_TABLE (ID,OAUTH_ID,PASSWORD,NAME,PHONE,BIRTH,ROLE,BADGE,CREATED_AT,ADDRESS,EMAIL,NICKNAME,POINT,PROFILE_PHOTO) values (user_seq.nextval,'oauth789','$2a$10$3Ea8nZsW84EhlcUxGmV.TObkq9hR4Lq0gnlrdRo7bKbzOLYs9nGCy','박지성','010-5555-2333',to_date('94/09/25','RR/MM/DD'),'user','활동가',to_date('24/01/05','RR/MM/DD'),'대전광역시 유성구','example3@exam.ple','jspark',4500,'https://res.cloudinary.com/dkaeihkco/image/upload/v1757292749/onoin3goqgwynbodfbjf.png');
Insert into USER_TABLE (ID,OAUTH_ID,PASSWORD,NAME,PHONE,BIRTH,ROLE,BADGE,CREATED_AT,ADDRESS,EMAIL,NICKNAME,POINT,PROFILE_PHOTO) values (29485,'oauth23789','$2a$10$3Ea8nZsW84EhlcUxGmV.TObkq9hR4Lq0gnlrdRo7bKbzOLYs9nGCy','최승민','010-7777-1133',to_date('03/09/25','RR/MM/DD'),'user','가수',to_date('24/02/05','RR/MM/DD'),'충청남도 천안시','example4@exam.ple','smchoi',1000,null);


INSERT INTO attendance_table VALUES (1, TO_DATE('2025-08-01', 'YYYY-MM-DD'));
INSERT INTO attendance_table VALUES (2, TO_DATE('2025-08-02', 'YYYY-MM-DD'));
INSERT INTO attendance_table VALUES (3, TO_DATE('2025-08-03', 'YYYY-MM-DD'));
INSERT INTO wallet_table VALUES (wallet_seq.NEXTVAL, 1, '신한은행', 50000000, TO_DATE('2025-07-30', 'YYYY-MM-DD'), '110-123-456789');
INSERT INTO wallet_table VALUES (wallet_seq.NEXTVAL, 2, '농협은행', 30000, TO_DATE('2025-07-31', 'YYYY-MM-DD'), '110-987-654321');
INSERT INTO wallet_table VALUES (wallet_seq.NEXTVAL, 3, '기업은행', 75000, TO_DATE('2025-08-01', 'YYYY-MM-DD'), '110-555-888888');
INSERT INTO wallet_table VALUES (wallet_seq.NEXTVAL, 2, '카카오뱅크', 40000, TO_DATE('2025-08-03', 'YYYY-MM-DD'), '110-987-654333');
INSERT INTO wallet_table VALUES (wallet_seq.NEXTVAL, 2, '토스뱅크', 130000, TO_DATE('2025-08-06', 'YYYY-MM-DD'), '110-98327-444333');

-- ///////////////////////// payment_table 더미 추가 ///////////////////////////
INSERT INTO payment_table VALUES (1, 1, 1, 15000, 'withdrawal', '커피 구매', TO_DATE('2025-08-01', 'YYYY-MM-DD'));
INSERT INTO payment_table VALUES (2, 2, 2, 20000, 'deposit', '포인트 충전', TO_DATE('2025-08-02', 'YYYY-MM-DD'));
INSERT INTO payment_table VALUES (3, 3, 3, 10000, 'withdrawal', '에코백 구매', TO_DATE('2025-08-03', 'YYYY-MM-DD'));

INSERT INTO wallet_table VALUES (
   wallet_seq.NEXTVAL, 1, '농협은행', 0, SYSDATE , 345);

-- 전자영수증 (100원 / 회)
INSERT INTO payment_table VALUES (10, 1, NULL, 2500, 'withdrawal', '전자영수증 발급: 카페 커피', TO_DATE('2025-08-05','YYYY-MM-DD'));
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 100, 'EARN', '전자영수증 발급', TO_DATE('2025-08-05','YYYY-MM-DD'));

-- 텀블러·다회용컵 이용 (300원 / 개)
INSERT INTO payment_table VALUES (11, 1, NULL, 4500, 'withdrawal', '텀블러 사용: 아이스라떼', TO_DATE('2025-08-06','YYYY-MM-DD'));
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 300, 'EARN', '텀블러 이용', TO_DATE('2025-08-06','YYYY-MM-DD'));

-- 일회용컵 반환 (200원 / 개)
INSERT INTO payment_table VALUES (12, 1, NULL, 0, 'withdrawal', '일회용컵 반환', TO_DATE('2025-08-07','YYYY-MM-DD'));
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 200, 'EARN', '일회용컵 반환', TO_DATE('2025-08-07','YYYY-MM-DD'));

-- 리필 스테이션 이용 (2,000원 / 회)
INSERT INTO payment_table VALUES (13, 1, NULL, 7000, 'withdrawal', '리필 스테이션: 세제 구매', TO_DATE('2025-08-08','YYYY-MM-DD'));
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 2000, 'EARN', '리필 스테이션 이용', TO_DATE('2025-08-08','YYYY-MM-DD'));

-- 친환경제품 구매 (1,000원 / 건)
INSERT INTO payment_table VALUES (14, 1, 4, 1500, 'withdrawal', '친환경제품 구매: 나무 수저', TO_DATE('2025-08-09','YYYY-MM-DD'));
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 1000, 'EARN', '친환경제품 구매', TO_DATE('2025-08-09','YYYY-MM-DD'));
-- 일반 식사
INSERT INTO payment_table VALUES (15, 1, NULL, 12000, 'withdrawal', '일반 식사 결제', TO_DATE('2025-08-10','YYYY-MM-DD'));

-- 편의점 담배 구매
INSERT INTO payment_table VALUES (16, 1, NULL, 5000, 'withdrawal', '편의점 담배', TO_DATE('2025-08-11','YYYY-MM-DD'));
-- 재활용품 2.5kg (kg당 100포인트라고 가정)
INSERT INTO payment_table VALUES (17, 1, NULL, 0, 'withdrawal', '재활용품 2.5kg 배출', TO_DATE('2025-08-12','YYYY-MM-DD'));



INSERT INTO merchant_table VALUES (1, 2, '그린카페', '서울특별시 마포구', '02-1234-5678', '식음료');
INSERT INTO merchant_table VALUES (2, 2, '에코샵', '서울특별시 성동구', '02-8765-4321', '생활용품');
INSERT INTO merchant_table VALUES (3, 3, '제로마켓', '경기도 수원시', '031-111-2222', '기타');

INSERT INTO merchant_table VALUES (4, 4, '리틀포레스트', '서울특별시 종로구', '031-111-3333', '친환경 물품');

INSERT INTO product_table VALUES (1, 1, '텀블러', '비건제품', '../image/merchant/4리틀포레스트/1나무_수저.jpg', 12000, 2.5, '친환경 재질의 텀블러');
INSERT INTO product_table VALUES (2, 2, '커피', '음료', '../image/merchant/4리틀포레스트/1나무_수저.jpg', 4000, 1.2, '공정무역 원두 사용');
INSERT INTO product_table VALUES (3, 3, '장바구니', '생활용품', '../image/merchant/4리틀포레스트/1나무_수저.jpg', 8000, 1.8, '재사용 가능한 장바구니');

INSERT INTO product_table VALUES (4, 4, '나무수저', '주방용품', '../image/merchant/4리틀포레스트/1나무_수저.jpg', 1500, 0.85, '플라스틱 사용을 줄이는 친환경 나무 수저입니다. 100% 생분해성 원료로 제작되었습니다.');
INSERT INTO product_table VALUES (5, 4, '미세플라스틱 제로 칫솔', '위생용품', '../image/merchant/4리틀포레스트/2미세플라스틱_없는_칫솔.jpg', 2500, 1.2, '플라스틱 없이 제작된 대나무 칫솔로, 사용 후 자연분해되어 환경에 부담이 없습니다.');
INSERT INTO product_table VALUES (6, 4, '에코빗 솔세트', '세정용품', '../image/merchant/4리틀포레스트/3에코빗_솔세트.jpg', 5200, 1.5, '다회용 세정 솔 세트로, 주방과 욕실 모두에 사용 가능합니다. 재활용 플라스틱으로 제작.');
INSERT INTO product_table VALUES (7, 4, '친환경 종이컵', '일회용품', '../image/merchant/4리틀포레스트/4친환경_종이컵.jpg', 3000, 0.6, '코팅 없이 재활용 가능한 종이컵입니다. 비목재 재료로 만들어져 산림 파괴를 줄입니다.');
/* =========================
   user_id = 1 (2025-08) – 결제와 매칭
   ========================= */
INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
VALUES (1, 300, TO_DATE('2025-08-10','YYYY-MM-DD'), 15); -- 일반 식사 결제(15)
INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
VALUES (1, 500, TO_DATE('2025-08-03','YYYY-MM-DD'), 3);  -- 에코백 구매(3)
INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
VALUES (1, 150, TO_DATE('2025-08-07','YYYY-MM-DD'), 12); -- 일회용컵 반환(12)

/* =========================
   user_id = 1 (2025-01 ~ 2025-07) – 개별 결제 미지정 → NULL
   ========================= */
-- 2025-01
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 150, TO_DATE('2025-01-05','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 220, TO_DATE('2025-01-12','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 180, TO_DATE('2025-01-19','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 260, TO_DATE('2025-01-27','YYYY-MM-DD'), NULL);
-- 2025-02
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 200, TO_DATE('2025-02-04','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 170, TO_DATE('2025-02-11','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 210, TO_DATE('2025-02-18','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 190, TO_DATE('2025-02-26','YYYY-MM-DD'), NULL);
-- 2025-03
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 240, TO_DATE('2025-03-03','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 190, TO_DATE('2025-03-10','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 260, TO_DATE('2025-03-18','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 230, TO_DATE('2025-03-27','YYYY-MM-DD'), NULL);
-- 2025-04
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 210, TO_DATE('2025-04-02','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 200, TO_DATE('2025-04-09','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 180, TO_DATE('2025-04-16','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 250, TO_DATE('2025-04-24','YYYY-MM-DD'), NULL);
-- 2025-05
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 190, TO_DATE('2025-05-06','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 300, TO_DATE('2025-05-13','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 220, TO_DATE('2025-05-21','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 260, TO_DATE('2025-05-29','YYYY-MM-DD'), NULL);
-- 2025-06
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 230, TO_DATE('2025-06-05','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 180, TO_DATE('2025-06-12','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 270, TO_DATE('2025-06-20','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 200, TO_DATE('2025-06-28','YYYY-MM-DD'), NULL);
-- 2025-07
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 260, TO_DATE('2025-07-04','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 210, TO_DATE('2025-07-11','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 240, TO_DATE('2025-07-19','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (1, 280, TO_DATE('2025-07-27','YYYY-MM-DD'), NULL);

/* =========================
   user_id = 2 (2025-08) – 결제 매칭 없음 → NULL
   ========================= */
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 100, TO_DATE('2025-08-10','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 100, TO_DATE('2025-08-03','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 150, TO_DATE('2025-08-07','YYYY-MM-DD'), NULL);

/* =========================
   user_id = 2 (2025-01 ~ 2025-07) – 개별 결제 미지정 → NULL
   ========================= */
-- 2025-01
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 150, TO_DATE('2025-01-05','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 220, TO_DATE('2025-01-12','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 180, TO_DATE('2025-01-19','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 260, TO_DATE('2025-01-27','YYYY-MM-DD'), NULL);
-- 2025-02
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 200, TO_DATE('2025-02-04','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 170, TO_DATE('2025-02-11','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 210, TO_DATE('2025-02-18','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 190, TO_DATE('2025-02-26','YYYY-MM-DD'), NULL);
-- 2025-03
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 240, TO_DATE('2025-03-03','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 190, TO_DATE('2025-03-10','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 260, TO_DATE('2025-03-18','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 230, TO_DATE('2025-03-27','YYYY-MM-DD'), NULL);
-- 2025-04
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 210, TO_DATE('2025-04-02','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 200, TO_DATE('2025-04-09','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 180, TO_DATE('2025-04-16','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 250, TO_DATE('2025-04-24','YYYY-MM-DD'), NULL);
-- 2025-05
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 190, TO_DATE('2025-05-06','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 300, TO_DATE('2025-05-13','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 220, TO_DATE('2025-05-21','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 260, TO_DATE('2025-05-29','YYYY-MM-DD'), NULL);
-- 2025-06
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 230, TO_DATE('2025-06-05','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 180, TO_DATE('2025-06-12','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 270, TO_DATE('2025-06-20','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 200, TO_DATE('2025-06-28','YYYY-MM-DD'), NULL);
-- 2025-07
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 260, TO_DATE('2025-07-04','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 210, TO_DATE('2025-07-11','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 240, TO_DATE('2025-07-19','YYYY-MM-DD'), NULL);
INSERT INTO emission_table (user_id, emission, emission_date, payment_id) VALUES (2, 280, TO_DATE('2025-07-27','YYYY-MM-DD'), NULL);

/* =========================
   롤업 검증용 임시 데이터(상대월) – payment_id = NULL
   ========================= */
INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
SELECT 1, 100, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -14) + 1,  NULL FROM dual UNION ALL
SELECT 1, 150, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -14) + 10, NULL FROM dual UNION ALL
SELECT 1, 250, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -14) + 20, NULL FROM dual;

INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
SELECT 1, 200, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -13) + 5,  NULL FROM dual UNION ALL
SELECT 1, 300, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -13) + 15, NULL FROM dual;

INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
SELECT 1, 600, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -12) + 7,  NULL FROM dual;

INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
SELECT 1,  50, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -11) + 3,  NULL FROM dual UNION ALL
SELECT 1,  60, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -11) + 18, NULL FROM dual;

INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
SELECT 1,  80, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -1) + 2,   NULL FROM dual UNION ALL
SELECT 1,  90, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -1) + 12,  NULL FROM dual;

INSERT INTO emission_table (user_id, emission, emission_date, payment_id)
SELECT 1,  70, TRUNC(SYSDATE),                               NULL FROM dual;


INSERT INTO growing_tree_table VALUES (growing_tree_seq.nextval, 1, 2, '소나무', 4.5, 2, 1, 0, TO_DATE('2025-08-01', 'YYYY-MM-DD'), TO_DATE('2025-08-01', 'YYYY-MM-DD'));
INSERT INTO growing_tree_table VALUES (growing_tree_seq.nextval, 2, 3, '참나무', 6.0, 3, 2, 1, TO_DATE('2025-07-30', 'YYYY-MM-DD'), TO_DATE('2025-07-30', 'YYYY-MM-DD'));
INSERT INTO growing_tree_table VALUES (growing_tree_seq.nextval, 3, 5, '느티나무', 9.2, 1, 3, 2, TO_DATE('2025-07-28', 'YYYY-MM-DD'), TO_DATE('2025-07-28', 'YYYY-MM-DD'));
INSERT INTO growing_tree_table values (growing_tree_seq.nextval,4,1,'맛도리나무',0,0,0,0,to_date('25/08/11','RR/MM/DD'),null); 
INSERT INTO grown_tree_table VALUES (grown_tree_seq.nextval, 1, 1, TO_DATE('2025-07-10', 'YYYY-MM-DD'), '식별자1',null);
INSERT INTO grown_tree_table VALUES (grown_tree_seq.nextval, 2, 2, TO_DATE('2025-07-15', 'YYYY-MM-DD'), '식별자2',null);
INSERT INTO grown_tree_table VALUES (grown_tree_seq.nextval, 3, 3, TO_DATE('2025-07-20', 'YYYY-MM-DD'), '식별자3',null);

INSERT INTO donation_table VALUES (donation_seq.NEXTVAL, 1, 10000, TO_DATE('2025-07-10', 'YYYY-MM-DD'), '환경 단체 기부');
INSERT INTO donation_table VALUES (donation_seq.NEXTVAL, 2, 5000, TO_DATE('2025-07-11', 'YYYY-MM-DD'), '제로웨이스트 캠페인');
INSERT INTO donation_table VALUES (donation_seq.NEXTVAL, 3, 7000, TO_DATE('2025-07-12', 'YYYY-MM-DD'), '지구의날 후원');
INSERT INTO community_table VALUES (community_seq.nextval, 1, '정보공유', '친환경 텀블러 후기', '텀블러가 아주 튼튼하고 예뻐요!', TO_DATE('2025-08-01', 'YYYY-MM-DD'), 'https://res.cloudinary.com/dkaeihkco/image/upload/v1757292749/onoin3goqgwynbodfbjf.png', 12);
INSERT INTO community_table VALUES (community_seq.nextval, 2, '자유', '에코백 어디서 사요?', '좋은 에코백 파는 곳 아시나요?', TO_DATE('2025-08-02', 'YYYY-MM-DD'), 'https://res.cloudinary.com/dkaeihkco/image/upload/v1757292749/onoin3goqgwynbodfbjf.png', 5);
INSERT INTO community_table VALUES (community_seq.nextval, 3, '정보공유', '지구의 날 행사 공유', '행사 정보 올려드립니다.', TO_DATE('2025-08-03', 'YYYY-MM-DD'), 'https://res.cloudinary.com/dkaeihkco/image/upload/v1757292749/onoin3goqgwynbodfbjf.png', 20);
INSERT INTO comment_table VALUES (comment_seq.nextval, 2, 1, '저도 이 텀블러 써요!', TO_DATE('2025-08-01', 'YYYY-MM-DD'), 3);
INSERT INTO comment_table VALUES (comment_seq.nextval, 3, 1, '정보 감사합니다~', TO_DATE('2025-08-01', 'YYYY-MM-DD'), 2);
INSERT INTO comment_table VALUES (comment_seq.nextval, 1, 2, '저는 이마트에서 샀어요', TO_DATE('2025-08-02', 'YYYY-MM-DD'), 4);

INSERT INTO wallet_table VALUES (wallet_seq.NEXTVAL, 2, '농협은행', 30000, TO_DATE('2025-07-31', 'YYYY-MM-DD'), '110-987-654321');
INSERT INTO wallet_table VALUES (wallet_seq.NEXTVAL, 3, '기업은행', 75000, TO_DATE('2025-08-01', 'YYYY-MM-DD'), '110-555-888888');

Insert into LIKES_TABLE (ID,USER_ID,TABLE_TYPE,community_id,PRESS_AT) values (likes_seq.nextval,2,'community',1,to_date('25/08/01','RR/MM/DD'));
Insert into LIKES_TABLE (ID,USER_ID,TABLE_TYPE,community_id,PRESS_AT) values (likes_seq.nextval,2,'community',3,to_date('25/08/18','RR/MM/DD'));
insert into likes_table (id,user_id,table_type,comment_id, press_At) values (likes_seq.nextval,3,'comment',1,to_date('25/08/18','RR/MM/DD'));

-- INSERT INTO point_event_table VALUES (point_event_seq.NEXTVAL, 1, 'DAILY_TIP', TRUNC(SYSDATE), SYSDATE, 15, '오늘의 생활실천팁');


-- INSERT INTO point_event_table VALUES (point_event_seq.NEXTVAL, 1, 'DAILY_TIP', TRUNC(SYSDATE), SYSDATE, 15, '오늘의 생활실천팁');


INSERT INTO chat_room_table VALUES (1, '챗방1', 4);
INSERT INTO chat_room_table VALUES (1, '챗방1', 2);


-- INSERT (point_table 샘플) ////////////--

-- 1) 적립(EARN): 탄소중립포인트 예시 (emission 테이블과 연동해 발생했다고 가정)
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 300,  'EARN',  'emission 2025-08-10', TO_DATE('2025-08-10','YYYY-MM-DD'));
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 150,  'EARN',  'emission 2025-08-07', TO_DATE('2025-08-07','YYYY-MM-DD'));

-- 2) 소비(SPEND): 나무키우기 비료/물주기 등 포인트 사용
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, -500, 'SPEND', '비료 구매',              TO_DATE('2025-08-01','YYYY-MM-DD'));

-- 3) 충전(CHARGE): 현금/결제로 포인트 충전
--    payment_table의 (user_id=2의 지갑) 2025-08-02 입금 20,000원과 맞춰 예시 작성
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 2, 20000,'CHARGE','카카오페이 충전',       TO_DATE('2025-08-02','YYYY-MM-DD'));

-- 4) 기부(DONATE): donation_table과 맞춰 기록
--    donation_table: (user_id=1, 10000, 2025-07-10)
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, -10000, 'DONATE', 'donation_id=1',     TO_DATE('2025-07-10','YYYY-MM-DD'));
--    donation_table: (user_id=2, 5000, 2025-07-11)
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 2, -5000, 'DONATE',  'donation_id=2',     TO_DATE('2025-07-11','YYYY-MM-DD'));
--    donation_table: (user_id=3, 7000, 2025-07-12)
INSERT INTO point_table (id, user_id, amount, type, memo, created_at)
VALUES (point_seq.NEXTVAL, 3, -7000, 'DONATE',  'donation_id=3',     TO_DATE('2025-07-12','YYYY-MM-DD'));

-- 5) 선물(GIFT): 보내기/받기 각각 한 줄
--    1번 유저가 4번 유저에게 3,000P 선물(보낸 사람: 음수 / 받은 사람: 양수)
INSERT INTO point_table (id, user_id, amount, type, counterparty_user_id, memo, created_at)
VALUES (point_seq.NEXTVAL, 1, 5000, 'GIFT', 4, '축하 포인트',       TO_DATE('2025-08-12','YYYY-MM-DD'));
INSERT INTO point_table (id, user_id, amount, type, counterparty_user_id, memo, created_at)
VALUES (point_seq.NEXTVAL, 4,  3000, 'GIFT', 1, '감사 인사',         TO_DATE('2025-08-12','YYYY-MM-DD'));


-- 룰 등록 
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '실천지원금','CASE',  500);   -- 예: 1회차 500
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '전자영수증','CASE',  100);
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '텀블러', 'CASE',  300);
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '일회용컵 반환','CASE',  200);
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '리필 스테이션','CASE', 2000);
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '다회용기','CASE', 1000);
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '무공해차','KM',    100);  -- km당
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '친환경제품','CASE', 1000);
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '재활용품','KG',    100);  -- kg당
INSERT INTO point_rule_table VALUES (point_rule_seq.NEXTVAL, '폐휴대폰','CASE', 1000);

insert into stock_table values (stock_seq.nextval,4,21,22,TO_DATE('2025-08-25 10:08:39','YYYY-MM-DD HH24:mi:SS'),to_date('2025-08-11','yyyy-MM-dd'),sysdate);

UPDATE user_table set password = '$2a$10$Gl.7Gf1Wxijjo4GgDvxMF.JtfSMkWpmCrvDQNDiBj46mkB1sbMMIy' where id = 2;
UPDATE point_rule_table SET co2_g_per_unit = 3  WHERE keyword = '전자영수증';
--///////////////////////////////////////////////////////  SELECT  /////////////////////////////////////////////////////--

insert into attendance_table values(1,TO_DATE('2025-09-07','YYYY-MM-DD'));
DELETE FROM point_event_table where user_id = 1;
update growing_tree_table set biyro_used_at = '25/07/28' where id = 1;

-- 1. 사용자
SELECT * FROM user_table;

SELECT COUNT(*) 
    FROM user_table
    WHERE email = 'example1@exam.ple' AND oauth_id = 'oauth123';
-- 2. 출석
SELECT * FROM attendance_table;
-- 3. 지갑
SELECT * FROM wallet_table;
-- 4. 지갑 내역
SELECT * FROM payment_table;
-- 5. 가게
SELECT * FROM merchant_table;
-- 6. 가게 상품
SELECT * FROM product_table;
-- 7. 탄소 절감
SELECT * FROM emission_table;
-- 8. 키우고 있는 나무
SELECT * FROM growing_tree_table;
-- 9. 다 키운 나무
SELECT * FROM grown_tree_table;
-- 10. 기부 내역
SELECT * FROM donation_table;
-- 11. 커뮤니티 글
SELECT * FROM community_table;
-- 12. 커뮤니티 댓글
SELECT * FROM comment_table;
-- 13. 좋아요 관리 
select * from likes_table; 
-- 14. 포인트 이벤트 테이블
SELECT * FROM point_event_table;
-- 15. 채팅 방
SELECT * FROM chat_room_table;
-- 16. 채팅 메세지
SELECT * FROM chat_message_table;
--17. 포인트 테이블
SELECT * FROM point_table;
--18. 포인트 룰 테이블
SELECT * FROM point_rule_table;
--19. 재고 관리 테이블 (다키운나무의 재고가 남았을경우 재고가 테이블 이동)
SELECT * FROM STOCK_TABLE;
--20. 문의 테이블
select * from inquery_Table;
--21. 답변 테이블
select * from answer_table;

commit;


-- ------------------------merge into using on------------------------------------

DROP TABLE emission_monthly_sum;
DROP TABLE emission_yearly_sum;

-- 월 합계 테이블: (user_id, yyyy, mm)로 한 달의 합계 kg 한 줄 저장
CREATE TABLE emission_monthly_sum (
  user_id NUMBER(38) NOT NULL,   -- 사용자 식별자
  yyyy    NUMBER(4)  NOT NULL,   -- 연도(예: 2025)
  mm      NUMBER(2)  NOT NULL CHECK (mm BETWEEN 1 AND 12), -- 월(1~12)
  kg      NUMBER     NOT NULL,   -- 그 달 총 배출량(kg)
  CONSTRAINT pk_emis_monthly PRIMARY KEY (user_id, yyyy, mm) -- 중복 방지 + 빠른 조회
);

-- 연 합계 테이블: (user_id, yyyy)로 한 해의 합계 kg 한 줄 저장
CREATE TABLE emission_yearly_sum (
  user_id NUMBER(38) NOT NULL,
  yyyy    NUMBER(4)  NOT NULL,
  kg      NUMBER     NOT NULL,
  CONSTRAINT pk_emis_yearly PRIMARY KEY (user_id, yyyy)
);

DROP INDEX idx_emis_user_date;
-- 원본(emission_table)의 범위 집계/삭제 성능을 위해 자주 쓰는 컬럼 조합 인덱스
CREATE INDEX idx_emis_user_date ON emission_table(user_id, emission_date);

-- 일 ==> 월

CREATE OR REPLACE PROCEDURE rollup_daily_to_monthly_1yr(
  p_keep_months IN PLS_INTEGER DEFAULT 12
) IS
  -- 기준월 계산: 최근 p_keep_months 개월은 남기고, 그보다 과거는 롤업 대상
  v_keep_from DATE := TRUNC(ADD_MONTHS(SYSDATE, -(p_keep_months-1)), 'MM'); 
  -- 예) 오늘이 2025-08-25, p_keep_months=12라면 v_keep_from=2024-09-01
BEGIN
  /* 1) 보존기간 '이전'의 일 데이터(= v_keep_from보다 과거)를 월 합계로 더함
        - USING 서브쿼리: user/연/월 별로 SUM(emission)
        - MERGE: 이미 있으면 UPDATE(+=), 없으면 INSERT
        - 이렇게 하면 지연 유입 데이터가 있어도 '가산'으로 안전하게 반영됨
  */
  MERGE INTO emission_monthly_sum m
  USING (
    SELECT user_id,
           EXTRACT(YEAR  FROM emission_date) AS yyyy,
           EXTRACT(MONTH FROM emission_date) AS mm,
           SUM(emission)                      AS kg
      FROM emission_table
     WHERE emission_date < v_keep_from
     GROUP BY user_id,
              EXTRACT(YEAR  FROM emission_date),
              EXTRACT(MONTH FROM emission_date)
  ) x
  ON (m.user_id = x.user_id AND m.yyyy = x.yyyy AND m.mm = x.mm)
  WHEN MATCHED THEN UPDATE SET m.kg = m.kg + x.kg
  WHEN NOT MATCHED THEN INSERT (user_id, yyyy, mm, kg) VALUES (x.user_id, x.yyyy, x.mm, x.kg);

  /* 2) 합산한 일 데이터 삭제
        - 이제 월 합계로 보존되므로 원본 일 행은 지워도 됨
        - '최근 p_keep_months 개월'은 남겨 두므로 월/달력 화면에 영향 없음
  */
  DELETE FROM emission_table
   WHERE emission_date < v_keep_from;
END;
/

-- 월 ==>연

CREATE OR REPLACE PROCEDURE rollup_monthly_to_yearly_1yr(
  p_keep_months IN PLS_INTEGER DEFAULT 12
) IS
  v_keep_from DATE := TRUNC(ADD_MONTHS(SYSDATE, -(p_keep_months-1)), 'MM'); 
BEGIN
  /* 1) 보존기간 '이전' 월 합계(yyyy,mm)를 연(yyyy)로 합산
        - 비교 편의를 위해 (yyyy,mm)를 '그 달의 1일' 날짜로 변환하여
          v_keep_from보다 과거인지 판단
  */
  MERGE INTO emission_yearly_sum y
  USING (
    SELECT user_id,
           yyyy,
           SUM(kg) AS kg
      FROM emission_monthly_sum
     WHERE TO_DATE(TO_CHAR(yyyy)||LPAD(mm,2,'0')||'01','YYYYMMDD') < v_keep_from
     GROUP BY user_id, yyyy
  ) x
  ON (y.user_id = x.user_id AND y.yyyy = x.yyyy)
  WHEN MATCHED THEN UPDATE SET y.kg = y.kg + x.kg
  WHEN NOT MATCHED THEN INSERT (user_id, yyyy, kg) VALUES (x.user_id, x.yyyy, x.kg);

  /* 2) 합산 끝난 월 요약 삭제
        - 최근 p_keep_months 개월 월 데이터는 남고,
          그보다 과거 월 데이터만 연 합계로 접힘
  */
  DELETE FROM emission_monthly_sum
   WHERE TO_DATE(TO_CHAR(yyyy)||LPAD(mm,2,'0')||'01','YYYYMMDD') < v_keep_from;
END;
/


--점검용 (월/연)
SELECT * FROM emission_monthly_sum WHERE user_id = 1 ORDER BY yyyy, mm;
SELECT * FROM emission_yearly_sum WHERE user_id = 1 ORDER BY yyyy;

-- 14개월 전: 3건(합 500)
-- 14개월 전: 3건(합 500)
INSERT INTO emission_table(user_id, emission, emission_date, payment_id)
SELECT 1, 100, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -14) + 1,  NULL FROM dual UNION ALL
SELECT 1, 150, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -14) + 10, NULL FROM dual UNION ALL
SELECT 1, 250, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -14) + 20, NULL FROM dual;

-- 13개월 전: 2건(합 500)
INSERT INTO emission_table(user_id, emission, emission_date, payment_id)
SELECT 1, 200, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -13) + 5,  NULL FROM dual UNION ALL
SELECT 1, 300, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -13) + 15, NULL FROM dual;

-- 12개월 전: 1건(합 600)  ← KEEP_FROM보다 과거이므로 롤업 대상
INSERT INTO emission_table(user_id, emission, emission_date, payment_id)
SELECT 1, 600, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -12) + 7,  NULL FROM dual;

-- 11개월 전: 2건(합 110)  ← 보존 대상(원본에 남아야 함)
INSERT INTO emission_table(user_id, emission, emission_date, payment_id)
SELECT 1,  50, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -11) + 3,  NULL FROM dual UNION ALL
SELECT 1,  60, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -11) + 18, NULL FROM dual;

-- 지난달: 2건(합 170)     ← 보존 대상
INSERT INTO emission_table(user_id, emission, emission_date, payment_id)
SELECT 1,  80, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -1) + 2,   NULL FROM dual UNION ALL
SELECT 1,  90, ADD_MONTHS(TRUNC(SYSDATE,'MM'), -1) + 12,  NULL FROM dual;

-- 이번달: 1건(합 70)      ← 보존 대상
INSERT INTO emission_table(user_id, emission, emission_date, payment_id)
SELECT 1,  70, TRUNC(SYSDATE),                               NULL FROM dual;


COMMIT;


BEGIN
  rollup_daily_to_monthly_1yr; -- 최근 12개월 이전(=KEEP_FROM보다 과거) 일 데이터를 월합계로 접고, 원본에서 삭제
END;
/

-- 결과 확인: 월 합계(롤업된 과거 3개 월만 있어야 함: -14, -13, -12)
SELECT * 
  FROM emission_monthly_sum
 WHERE user_id = 1
 ORDER BY yyyy, mm;

-- 원본 잔존 확인: -11, 지난달, 이번달만 남아야 함
SELECT user_id, emission, emission_date
  FROM emission_table
 WHERE user_id = 1
 ORDER BY emission_date;


--2단계: 월→연 롤업 실행 & 검증
BEGIN
  rollup_monthly_to_yearly_1yr; -- KEEP_FROM보다 과거 월합계를 같은 연도로 합쳐서 연합계로 이동
END;
/

-- 결과 확인: 연 합계(롤업된 월들이 속한 연으로 합산)
SELECT * 
  FROM emission_yearly_sum
 WHERE user_id = 1
 ORDER BY yyyy;

-- 월 합계 잔존 확인: KEEP_FROM 이전 월은 삭제되었는지
SELECT * 
  FROM emission_monthly_sum
 WHERE user_id = 1
 ORDER BY yyyy, mm;
 
 
 DELETE FROM user_table WHERE id = 5;
--------------------------------------------------------------
-- point_rule emission추가
-- 1-1) 감축계수 컬럼(kg/단위) 추가
ALTER TABLE point_rule_table ADD (co2_g_per_unit NUMBER(12,0) DEFAULT 0 NOT NULL);

-- 더미값 갱신(정수 g)
-- g 값을 kg로 변환하여 저장
UPDATE point_rule_table SET co2_g_per_unit =    3  WHERE keyword = '전자영수증';
UPDATE point_rule_table SET co2_g_per_unit =   50  WHERE keyword = '텀블러';
UPDATE point_rule_table SET co2_g_per_unit =   30  WHERE keyword = '일회용컵 반환';
UPDATE point_rule_table SET co2_g_per_unit =  120  WHERE keyword = '리필 스테이션';
UPDATE point_rule_table SET co2_g_per_unit =  150  WHERE keyword = '다회용기';
UPDATE point_rule_table SET co2_g_per_unit =  130  WHERE keyword = '무공해차';
UPDATE point_rule_table SET co2_g_per_unit =  100  WHERE keyword = '친환경제품';
UPDATE point_rule_table SET co2_g_per_unit = 1500  WHERE keyword = '재활용품';
UPDATE point_rule_table SET co2_g_per_unit =10000  WHERE keyword = '폐휴대폰';
COMMIT; --비밀번호 암호와, 사진 api 적용