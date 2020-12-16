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

DROP TABLE USER_MASTER;

CREATE TABLE USER_MASTER (
    /* columnName DATATYPE*/
);

-- Create a new relational table with 3 columns

CREATE TABLE TABLE1 
(
  Column1 VARCHAR2(255) NOT NULL,
  Column2 VARCHAR2(1024),
  Column3 NUMBER(3)
);

CREATE TABLE USER_MASTER
(
/*
    FIELD_NAME DATATYPE DEFAULT || NOT NULL,
*/
    USER_NAME VARCHAR2(30) NOT NULL,
    REG_DT DATE DEFAULT SYSDATE,
    CHG_DT DATE DEFAULT SYSDATE,

    CONSTRAINT PK_USER_MASTER PRIMARY KEY(USER_NAME)
);

DROP TABLE USER_MASTER;