SELECT * FROM EMP;
SELECT * FROM DEPT;

-- [1] 이름 scott이 근무하는 부서명, 부서의 지역명 출력 (서브쿼리)
SELECT DEPTNO FROM EMP WHERE ENAME = 'SCOTT';
SELECT DNAME, LOC FROM DEPT WHERE DEPTNO = 20;

-- 서브쿼리 사용
SELECT DNAME, LOC FROM DEPT
WHERE DEPTNO = (SELECT DEPTNO FROM EMP WHERE ENAME = 'SCOTT');

/*
    위 둘의 공통 단점 : 결과에 SCOTT이라는 이름이 나오지 않습니다.
    서브쿼리의 단점 : 서브쿼리 결과가 2건이상이면 예기치않은 오류를 발생시킵니다.
        => =대신 IN을 사용하면 해결되긴함.
*/
SELECT DNAME, LOC FROM DEPT
WHERE DEPTNO IN(SELECT DEPTNO FROM EMP WHERE ENAME = 'SCOTT'); -- IN안에 데이터만큼 결과가 출력

/* 
    [2] JOIN : 두개 이상의 테이블에 떨어진 데이터를 한번의 SQL문으로 원하는 결과를 얻을 수 있음.*/

        /*
        CROSS JOIN
            : 두개 이상의 테이블이 조인될때 where절에 의해 공통되는 컬럼에 의한 결합이 발생하지 않는 경우, 가장 최악의 결과 발생
            = A테이블의 총 레코드 * B테이블의 총 레코드
        */
            SELECT * FROM EMP, DEPT;
        /*

        /*
        EQUI JOIN
            : 조인 대상이 되는 두테이블에서 공통적으로 존재하는 컬럼의 값이 일치하는 행을 연결하여 결과를 생성
              CROSS JOIN에서 같은 값을 갖는 필드를 조건을 걸어 필터링
        */
            /*(1)*/
            SELECT * FROM EMP, DEPT; -- 시작은 CROSS JOIN과 동일

            /*(2)*/
            SELECT *
            FROM EMP, DEPT 
            WHERE 
                EMP.DEPTNO = DEPT.DEPTNO; -- WHERE를 통해 교집합추출

            /*(3)*/
            SELECT
                EMP.ENAME
                , DEPT.DNAME
                , DEPT.LOC
                , EMP.DEPTNO
            FROM EMP, DEPT
            WHERE 
                EMP.DEPTNO = DEPT.DEPTNO 
                AND EMP.ENAME = 'SCOTT'; -- 2개이상의 테이블을 취급할 때, 필드명이 충돌될 경우 어떤테이블인지 명시해야 정상실행된다.

            /*(4)*/
            SELECT
                A.ENAME
                , B.DNAME
                , B.LOC
                , A.DEPTNO
            FROM EMP A, DEPT B
            WHERE 
                A.DEPTNO = B.DEPTNO 
                AND A.ENAME = 'SCOTT'; -- 컬럼지정시 테이블명대신 별칭을 사용할 경우, 모든 필드에 별칭을 명시해야함.
        /*

        /*
        NON-EQUI JOIN
            : 동일 컬럼이 없이 다른 조건을 사용하여 조인
              조인 조건에 특정 범위내에 있는지를 조사하기위해 조건절에 조인 조건을 = 연ㅅ나자 이외의 비교 연산자를 이용
        */
            /*(1)*/
            SELECT * FROM EMP;
            SELECT * FROM SALGRADE;

            /*(2)*/
            SELECT
                E.ENAME
                , E.SAL
                , S.GRADE
            FROM EMP E, SALGRADE S
            WHERE
                E.SAL >= S.LOSAL
                AND E.SAL <= S.HISAL;
            
            SELECT
                E.ENAME
                , E.SAL
                , S.GRADE
            FROM EMP E, SALGRADE S
            WHERE E.SAL BETWEEN S.LOSAL AND S.HISAL;

        /*

        /*
        OUTER JOIN
            : 조인 조건에 만족하지 못해서 해당 결과를 출력시에 누락이 되는 문제점이 발생할때
              해당 레코드를 출력하는 조인
        */
            /*(1)*/
            SELECT
                B.BOOKNUM
                , B.SUBJECT
                , I.OUT_DATE
            FROM BOOKLIST B, IN_OUT I
            WHERE B.BOOKNUM = I.BOOKNUM(+); -- 대여되지 않은 건 포함
            -- 결과적으로 대여된 책정보와 대여되지 않은 책정보를 같이 출력하고, 대여되지않은 정보는 연결되지않은 열이 NULL

        /*

        /*
        ANSI JOIN

            /*1. ANSI CROSS JOIN*/
                SELECT * FROM EMP CROSS JOIN DEPT; -- CROSS JOIN
            /*2. ANSI INNER JOIN*/
                SELECT ENAME, DNAME FROM EMP INNER JOIN DEPT ON EMP.DEPTNO = DEPTNO.DEPTNO; -- EQUI JOIN
                SELECT ENAME, DNAME FROM EMP INNER JOIN DEPT USING(DEPTNO); -- EQUI JOIN + 필드명이 같을경우
            /*3. ANSI OUTER JOIN*/
                SELECT * FROM EMP LEFT OUTER JOIN DEPT ON EMP.DEPTNO = DEPT.DEPTNO; -- SELECT * FROM EMP, DEPT WHERE EMP.DEPTNO = DEPT.DEPTNO(+);
                SELECT * FROM EMP RIGHT OUTER JOIN DEPT ON EMP.DEPTNO = DEPT.DEPTNO; -- SELECT * FROM EMP, DEPT WHERE EMP.DEPTNO(+) = DEPT.DEPTNO;
                
        /*

*/

-- EQUI JOIN PRACTICE

    -- IN_OUT, BOOKLIST에서 대여일자, 도서코드, 도서제목, 출판년도를 출력

SELECT * FROM IN_OUT;
SELECT * FROM BOOKLIST;

SELECT
    A.OUT_DATE AS 대여일자
    , A.BOOKNUM AS 도서코드
    , B.SUBJECT AS 도서제목
    , B.MAKEYEAR AS 출판년도
FROM IN_OUT A, BOOKLIST B
WHERE A.BOOKNUM = B.BOOKNUM;

    -- IN_OUT, PERSON 테이블을 이용하여 대여일자, 회원이름, 전화번호, 사은포인트를 EQUI조인으로 출력

SELECT COUNT(*) FROM IN_OUT;
SELECT COUNT(*) FROM PERSON;

SELECT
    I.OUT_DATE AS 대여일자
    , P.PERSONNAME AS 회원이름
    , P.PHONE AS 전화번호
    , P.BPOINT AS 사은포인트
FROM IN_OUT I, PERSON P
WHERE I.PERSONNUM = P.PERSONNUM;

    -- 세개의 테이블(IN_OUT, PERSON, BOOKLIST)를 EQUI JOIN으로 조합 - 표시할 필드명칭 : 대여일자, 도서제목, 회원이름, 대여가격, 할인금액, 매출액

SELECT * FROM IN_OUT I;
SELECT * FROM PERSON P;
SELECT * FROM BOOKLIST B;

SELECT COUNT(*)
FROM IN_OUT I, PERSON P, BOOKLIST B;

SELECT
    I.OUT_DATE AS 대여일자
    , B.SUBJECT AS 도서제목
    , P.PERSONNAME AS 회원이름
    , B.INPRICE AS 대여가격
    , I.DISCOUNT AS 할인금액
    , B.OUTPRICE AS 매출액
FROM IN_OUT I, PERSON P, BOOKLIST B
WHERE
    I.BOOKNUM = B.BOOKNUM
    AND I.PERSONNUM = P.PERSONNUM;

-- OUTER JOIN PRACTICE

    -- OUTER JOIN으로 EMP의 인원에 대한 부서명을 출력하되 부서명이 없는 필드는 NULL로 표시
    ALTER TABLE EMP DROP CONSTRAINT FK_DEPTNO;
    UPDATE EMP SET DEPTNO = 50 WHERE JOB = 'CLERK';
    UPDATE EMP SET DEPTNO = 60 WHERE JOB = 'ANALYST';

    SELECT
        E.ENAME
        , NVL(D.DNAME, 'UNKNOWN') AS DNAME
    FROM EMP E, DEPT D
    WHERE
        E.DEPTNO = D.DEPTNO(+); -- 부서테이블에 존재하지않은 부서코드를 가진 직원을 포함하여 조회