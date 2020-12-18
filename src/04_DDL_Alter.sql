/*
    테이블의 수정(ALTER)

    1. 필드명의 변경
    ALTER TABLE이름
    rename column 변경전 이름 to 변경 후 이름;
*/
ALTER TABLE BOOKLIST RENAME COLUMN TITLE TO SUBJECT;
-- booklist테이블의 title필드 이름을 subject로 수정하세요
SELECT * FROM BOOKLIST;

/*
    2. 필드 자료형의 변경
    varchar2(12)였던 person 테이블의 personname 필드를 varchar2(30)으로 변경
*/
ALTER TABLE PERSON MODIFY personname VARCHAR2(30);
SELECT * FROM PERSON;

/*
    3. 필드 추가
    booklist에 구매등급을 저장할 수 있는 grade필드를 varchar2(15)로 추가
*/
 -- BOOKLIST 테이블에 GRADE(VARCHAR2(15))로 필드 추가
ALTER TABLE BOOKLIST ADD GRADE VARCHAR2(15);
 -- PERSON 테이블에 GENDER(VARCHAR2(3))로 필드 추가
ALTER TABLE PERSON ADD GENDER VARCHAR2(3);
 -- PERSON 테이블에 AGE(NUMBER(2))로 필드 추가
ALTER TABLE PERSON ADD AGE NUMBER(2);

SELECT * FROM BOOKLIST;
SELECT * FROM PERSON;

/*
    4. 필드의 삭제
    ALTER TABLE 테이블명 DROP COLUMN 필드명
    person 테이블에서 성별 필드 제거
*/
ALTER TABLE PERSON DROP COLUMN GENDER;
SELECT * FROM PERSON;

/*
    5. 제약 조건의 추가/제거
    ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 제약조건식;
    ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건명;
*/
    -- person 테이블에서 성별 필드에 'F', 'M'만 입력되도록 제약조건 추가. 제약조건명 (CHECK_GENDER)
ALTER TABLE PERSON ADD CONSTRAINT CHECK_GENDER CHECK(GENDER IN ('F', 'M'));
    -- 나이 필드에 120살이 초과되는 나이는 입력되지 못하게 제약조건 추가. 제약조건명 (CHECK_AGE)
ALTER TABLE PERSON ADD CONSTRAINT CHECK_AGE CHECK(AGE <= 120);
    -- 제약 조건의 제거
--ALTER TABLE PERSON DROP CONSTRAINT 제약조건명


/*
    1. IN_OUT 테이블에서 booklist테이블의 booknum을 참조하는 외래키 제거

    2. IN_OUT에서 기본키 제거

    3. IN_OUT에서 기본키 다시 추가(제거한 기본키와 같은 이름, 같은 필드를 이용)

    4. IN_OUT에서 booklist테이블의 booknum을 참조하는 외래키 다시 추가

    5. booklist의 grade필드에 '전체', '청소년구매가능', '성인전용'으로 입력을 제한
*/

-- 1.
ALTER TABLE IN_OUT DROP CONSTRAINT FK1; -- 1
ALTER TABLE IN_OUT DROP CONSTRAINT IN_OUT_PK; -- 2
ALTER TABLE IN_OUT ADD CONSTRAINT IN_OUT_PK PRIMARY KEY (OUT_DATE, INDEXK); -- 3
ALTER TABLE IN_OUT ADD CONSTRAINT FK1 FOREIGN KEY (BOOKNUM) REFERENCES BOOKLIST(BOOKNUM); -- 4
ALTER TABLE BOOKLIST ADD CONSTRAINT CHECK_GRADE CHECK( GRADE IN('전체', '청소년구매가능', '성인전용')); -- 5

/*
    테이블명 : ORDERS
    필드 : 
        ORDER_ID NUMBER(12, 0)
        ORDER_DATE DATE
        ORDER_MODE VARCHAR2(8)
        CUSTOMER_ID NUMBER(6, 0)
        ORDER_STATUS NUMBER(2, 0)
        ORDER_TOTAL NUMBER(8, 2)
        SALES_REP_ID NUMBER(6, 0)
        PROMOTION_ID NUMBER(6, 0)

        CONSTRAINT
            ORDER_ID PRIMARY KEY
            ORDER_MODE CHECK ('direct', 'online')
            ORDER_TOTAL DEFAULT 0
*/

DROP TABLE ORDERS;

CREATE TABLE ORDERS
(
    ORDER_ID NUMBER(12, 0),
    ORDER_DATE DATE,
    ORDER_MODE VARCHAR2(8),
    CUSTOMER_ID NUMBER(6, 0),
    ORDER_STATUS NUMBER(2, 0),
    ORDER_TOTAL NUMBER(8, 2) DEFAULT 0,
    SALES_REP_ID NUMBER(6, 0),
    PROMOTION_ID NUMBER(6, 0),

    CONSTRAINT PK_ORDERS PRIMARY KEY(ORDER_ID),
    CONSTRAINT CHECK_ORDER_MODE CHECK (ORDER_MODE IN ('direct', 'online'))
);

SELECT * FROM ORDERS;
SELECT * FROM COLS WHERE TABLE_NAME = 'ORDERS';
SELECT * FROM ALL_CONSTRAINTS WHERE TABLE_NAME = 'ORDERS';


/*
    테이블 수정
        CUSTOMER_ID 필드명을 CUSTOMER_NUMBER로 수정
        PROMOTION_ID 값은 10000~99999 사이의 값만 저장 가능
*/
ALTER TABLE ORDERS RENAME COLUMN CUSTOMER_ID TO CUSTOMER_NUMBER;
ALTER TABLE ORDERS ADD CONSTRAINT CHECK_PROMOTION_ID CHECK (PROMOTION_ID BETWEEN 10000 AND 99999);

/* 테이블의 복사 */
CREATE TABLE ORDERS_2 AS SELECT * FROM ORDERS;

/* 테이블의 제거 */
DROP TABLE ORDER_2 PERGE; --PERGE는 생략가능
/*
    PERGE가 없이 삭제된 테이블은 나중에 휴지통과 같이 복구 가능
    PERGE를 사용하면 완전 삭제
*/

/* 휴지통에 있는 데이터 조회 */
SELECT * FROM RECYCLEBIN;
-- 삭제된 정보가 나오며 ORIGINAL_NAME과 OPERATION을 활용하여 복구가 가능합니다.

-- 휴지통 비우기
PURGE RECYCLEBIN;

-- 테이블 복구하기
FLASHBACK TABLE ORDER_2 TO BEFORE DROP;
