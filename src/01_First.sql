select * from dept;
select * from emp;
select * from bonus;
select * from salgrade;
select * from tabs;

-- single comment : -- ~
-- multiline comment : /* ~ */

/*
    DB : 체계적으로 데이터를 저장하고 유지하며, 검색에 효율적인 답을 내어놓도록 데이터 등을 저장관리하는 프로그램
    DBMS(Database Management System) : 데이터베이스 프로그램에 요청하고 명령하여 데이터베이스의 조작 운영을 가능하게 하는 프로그램.

    각 데이터베이스 제품들에는 자신의 데이터베이스를 관리할 수 있는 DBMS가 존재
        - 오라클 데이터베이스에서 사용가능한 DBMS : SQL Developers, SQL PLUS, Eclipse

    SQL(Structured Query Language) :
        - 관계형 데이터베이스 관리 시스템(RDBMS)의 데이터를 관리하기위해 설계된 특수 목적의 프로그래밍 언어.
        - 데이터베이스에 데이터를 저장, 조회, 수정, 삭제 등의 명령을 내릴 수 있음.

    데이터베이스 테이블의 구조
        - Table : 데이터베이스에서 사용되는 데이터 집합의 단위
        - Field : Attribute, 열, 속성
        - Record : Tuple, 행

    SQL의 세가지 종류
        - 1. DDL(Database Definition Language) 정의어 - 테이블 또는 그 외 객체 생성, 수정, 삭제
        - 2. DML(Database Management Language) 조작어 - 레코드(실제데이터)의 삽입, 수정, 삭제, 조회
        - 3. DCL(Database Control Language) 제어어 - 권한 설정

        - DDL(Database Definition Language)
            CREATE : 테이블, 뷰 등을 생성
            ALTER : 이미 생성되어 있는 테이블 또는 뷰 등의 구조를 수정
            DROP : 이미 생성되어 있는 테이블 또는 뷰 등을 삭제

        - DML(Database Management Language)
            INSERT : 레코드 추가
            UPDATE : 레코드 수정
            DELETE : 레코드 삭제
            SELECT : 레코드 조회

        - DCL(Database Control Language)
            GRANT : 특정사용자의 권한을 설정
            REVOKE : 특정사용자의 권한을 해제

    위와 같은 명령들로 만들어진 테이블에는 무결성 유지를 위해 몇가지 규칙을 정하고 유지합니다.
        - 개체 무결성
            - 각 테이블에는 기본키(Primary Key)를 지정할 수 있습니다.
            - 기본키는 중복된 값을 가질 수 없으며, 비워둔 값을 넣을수도 없는 규칙이 있습니다.
            - 기본키를 지정하고 규칙을 지키며 유지하는 것을 개체 무결성이라고 합니다.
            - 테이블 내에서 단 하나의 레코드를 선택하여 추출할 수 있는 값이 저장됩니다.
            예) 일련번호, 상품코드, 주민번호, 학번 등

        - 참조 무결성
            - 도서테이블의 도서번호를 이용하여 매출 테이블에 매출 내역을 기록한다면
            - 도서테이블에 없는 도서 번호가 매출테이블에 쓰여지는 것을 막는 규칙입니다.
            - 최소한 보유한 도서들에 대해서만 매출이 일어나게 하는 규칙이며
            - 혹시라도 있을지 모를 오타, 오입력 등에 의해 없는 도서코드가 매출에 기록되고,
            - 매출로 기록되어야할 도서가 누락되는 일을 방지하기 위해 사용
            - 참조되는 매출테이블의 도서코드에 해당하는 필드를 외래키(Foreign Key)라고 합니다.
*/