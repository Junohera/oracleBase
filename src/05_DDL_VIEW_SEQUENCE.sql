/*
    oracle - view

    물리적인 테이블에 근거한 논리적인 가상 테이블.
    가상이란 단어는 실질적으로 데이터를 저장하고 있지 않기 때문에 붙인 것이고,
    테이블이란 단어는 실질적으로 데이터를 저장하고 있지 않더라도 사용 계정자는
    마치 테이블을 사용하는 것과 동일하게 뷰를 사용할 수 있기 때문에 붙인 것입니다.
    뷰는 기본테이블에서 파생된 객체로서 기본 테이블에 대한 하나의 쿼리(질의, 명령)문입니다.
    실제 테이블에 저장된 데이터를 뷰를 통해서 기본 테이블을 제한적으로 사용할 수 있습니다.
    뷰는 이미 존재하고 있는 테이블에 제한적으로 접근하도록 합니다.
    뷰를 생성하기 위해서는 실질적으로 데이터를 저장하고 있는 물리적인 테이블이
    존재해야되는데 이 테이블은 기본테이블이라고 합니다.
    두개이상의 테이블 또는 한개의 테이블이나 또 다른 뷰를 참조하는 객체입니다.
    저장된 테이블이라기보다 공식 또는 SELECT 문을 갖고 있다가 명령대로 불러와 그때 그때 구성하는 형식입니다.
    원본의 데이터변화가 실시간으로 반영됩니다.
*/

/*
    생성방법
    CREATE || REPLACE VIEW ${VIEW_NAME} AS (SELECT ~)
*/


/*
    oracle - sequence
        : 오라클 내의 유일한 숫자를 자동으로 생성하는 자동 번호 발생기.
        : 테이블 생성 후 시퀀스(일련번호)를 따로 만들어야 한다.

    생성 방법
    CREATE SEQUENCE ${SEQUENCE_NAME} START WITH ${START_NUMBER} INCREMENT BY ${INC_NUMBER};
*/

-- [1] 샘플 테이블 생성
CREATE TABLE MEMOS (
    NUM NUMBER(4) CONSTRAINT MEMOS_NUM_PK PRIMARY KEY,
    NAME VARCHAR2(20) CONSTRAINT MEMOS_NAME_NN NOT NULL,
    POST_DATE DATE DEFAULT SYSDATE
);

-- [2] 샘플 테이블의 시퀀스 생성
CREATE SEQUENCE MEMOS_SEQ START WITH 1 INCREMENT BY 1;
-- MEMOS_SEQ : 시퀀스이름
-- START WITH 1 : 시작 숫자
-- INCREMENT BY 1 : 증감량

-- [3] 데이터 입력 : 일련번호 포함
INSERT INTO MEMOS(NUM, NAME) VALUES (MEMOS_SEQ.NEXTVAL, '홍길동');
INSERT INTO MEMOS(NUM, NAME) VALUES (MEMOS_SEQ.NEXTVAL, '이순신');
INSERT INTO MEMOS(NUM, NAME) VALUES (MEMOS_SEQ.NEXTVAL, '강감찬');
INSERT INTO MEMOS(NUM, NAME) VALUES (MEMOS_SEQ.NEXTVAL, '유관순');
INSERT INTO MEMOS(NUM, NAME) VALUES (MEMOS_SEQ.NEXTVAL, '장영실');

SELECT * FROM MEMOS;
DELETE FROM MEMOS;

-- [4] 현재 시퀀스가 어디까지 증가되어져 있는지 확인
SELECT MEMOS_SEQ.CURRVAL FROM DUAL;
-- 다른요인에 의해 실행이 실패가되어도 실행되는 순간 숫자가 증가하므로 주의

-- [5] 시퀀스 수정 : 최대 증가값을 툭정숫자(14)까지로 제한.
ALTER SEQUENCE MEMOS_SEQ MAXVALUE 14;

-- [6] 시퀀스 삭제
DROP SEQUENCE MEMOS_SEQ;

-- [7] 시퀀스 재생성 : 14다음 숫자부터 시작하게하여 기존 레코드와 중복되지않게합니다.
CREATE SEQUENCE MEMOS_SEQ START WITH 206 INCREMENT BY 1;
