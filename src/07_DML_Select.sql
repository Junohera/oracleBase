-- 오라클 명령어 : SELECT

-- 사용양식(일반)
-- SELECT * FROM ${TABLE_NAME}

-- [1] 현재 계정 사용자가 관리(소유)하고 있는 테이블의 목록
SELECT * FROM TAB;

-- [2] 특정 테이블의 구조 조회(필드 리스트/데이터형식)
desc DEPT;
desc PERSON;

/*
    SELECT : SELECT와 FROM사이에 조회하고자 하는 필드명들을 ,로 구분하여 지목
        SELECT BOOKNUM, SUBJECT, OUTPRICE FROM ...
        모든 필드를 한번에 지목하려면 *
        FROM뒤에는 대상 테이블명
        WHERE 절을 붙여서 조건에 맞는 행만 골라내기도 합니다.
        SELECT ... FROM ... WHERE ...
        위와 같이 연산식을 써서 연산된 결과를 필드로 조회하고자 할땐 AS와 함게 만들어진 필드명을 지어주기도 합니다.
        SELECT EMPNO || '-' || ENAME AS EMP_INFO FROM 
        EMPNO || '-' || ENAME : EMPNO와 ENAME을 '-'와 함께 이어붙이기 하고 그렇게 만들어진 필드의 이름을 EMP_INFO로 설정
        필드명에 공백이 있거나 기술하기 어려운 필드명일때도 AS로 별칭을 붙이기도합니다.
*/

SELECT EMPNO || '-' || ENAME AS EMP_INFO FROM EMP;

-- [3] 특정 테이블의 DATA 표시
SELECT * FROM IN_OUT;

-- [4] 모든 컬럼(필드명)이 아닌, 필요한 필드만 조회
SELECT SUBJECT, MAKEYEAR FROM BOOKLIST;

-- [5] 각각의 필드명에 별칭을 부여해서 출력
SELECT SUBJECT AS 영화제목, MAKEYEAR AS 출판연도 FROM BOOKLIST;

-- (주의) 별칭에 띄어쓰기 하지 않습니다. 띄어쓰기가 들어간 별칭을 쓰려면 큰따옴표로 묶어서 사용
SELECT SUBJECT AS "영화제목", MAKEYEAR AS "출판 년도" FROM BOOKLIST;

-- [6] 중복제거
SELECT DISTINCT BOOKNUM FROM IN_OUT;
-- IN_OUT 테이블에서 BOOKNUM 값만을 조회하되 중복된 값은 한번만 표시하도록 조회
-- [7] 검색 조건의 추가 : 입고 가격이 20000원 이상인 BOOK목록
SELECT * FROM BOOKLIST WHERE INPRICE >= 20000;
-- [8] 이름이 박지성인 회원의 모든 회원정보 출력
SELECT * FROM PERSON WHERE PERSONNAME = '박지성';
-- [9] 1983년도 이후로 태어난 회원의 모든 회원정보
SELECT * FROM PERSON WHERE BIRTH >= '1983/01/01';
-- [10] 사은포인트(BPOINT)가 250점 이상이고 1982년 이후로 태어난 회원의 모든 회원정보 
SELECT * FROM PERSON WHERE BPOINT >= 250 AND BIRTH >= '1982/01/01';
-- [11] 제작년도가 2016년 이전이거나 입고가격(INPRICE)이 18000원 이하인 BOOK목록
SELECT * FROM BOOKLIST WHERE MAKEYEAR < 2016 OR INPRICE <= 18000;
-- [12] 성명이 '이'로 시작하는 회원의 모든정보
SELECT * FROM PERSON WHERE PERSONNAME LIKE '이%';
-- [13] 이름이 '용'으로 끝나는 회원의 정보
SELECT * FROM PERSON WHERE PERSONNAME LIKE '%용';
-- [14] 도서 제목에 '이'가 포함되는 도서정보
SELECT * FROM BOOKLIST WHERE SUBJECT LIKE '%이%';
-- [15] 사은포인트가 NULL이 아닌 회원의 이름과 전화번호
SELECT  PERSONNAME, PHONE FROM PERSON WHERE BPOINT IS NOT NULL;
-- [16] 도서 제목에 두번째 글자가 '것'인 도서 정보
SELECT * FROM BOOKLIST WHERE SUBJECT LIKE '_것%';


/*
    - 조건식

        - ANY*/
            SELECT EMPNO, SAL, DEPTNO FROM EMP WHERE DEPTNO = ANY(10, 20, 30, 40);
            /*
            괄호안의 값중 하나라도 만족할 경우

        - ALL*/
            SELECT EMPNO, SAL FROM EMP WHERE SAL = ALL(2000, 3000, 4000);
            /*
            괄호안의 모든 값이 만족할 경우
        
        - SOME*/
            SELECT EMPNO, SAL, DEPTNO FROM EMP WHERE DEPTNO = SOME(10, 20, 40);
            /*
            괄호안의 값중 하나라도 만족할 경우(= ANY, IN)

        - NOT*/
            SELECT EMPNO, SAL FROM EMP WHERE DEPTNO NOT IN(10, 20, 30, 40);
            /*
            IN()안에 있는 값과 하나도 매칭되지 않는 값이 검색 대상

        - EXISTS*/
            SELECT DEPTH, DNAME FROM DEPT A WHERE EXISTS(SELECT * FROM EMP B WHERE A.DEPTNO = B.DEPTNO AND B.SAL > 3000);
            /*
            IN과 비슷하지만 후행 조건절로 값의 리스트가 아닌 서브쿼리만 올 수 있음
            또한 서브쿼리내에 조인조건이 있어야 정상작동


    - 정렬
        SELECT명령의 결과를 특정 필드값으로 정렬하는 명령
            ASC : 오름차순, (DEFAULT)
            DESC : 내림차순

        EMP 테이블 예시
                EX1) SAL이 1000이상인 데이터를 ENAME기준으로 오름차순 정렬 조회*/
                    SELECT *
                    FROM EMP 
                        WHERE SAL >= 1000 
                    ORDER BY ENAME; -- DEFAULT -> ASC
                    
                /*EX2) SAL이 1000이상인 데이터를 ENAME기준으로 내림차순 정렬 조회*/
                    SELECT *
                    FROM EMP 
                        WHERE SAL >= 1000 
                    ORDER BY ENAME DESC;
                    
                /*EX3) JOB으로 내림차순*/
                    SELECT *
                    FROM EMP
                    ORDER BY JOB DESC;
                    
                /*EX4) JOB으로 내림차순 정렬 후 같은 JOB_ID사이에서는 HIREDATE의 내림차순으로 정렬*/
                    SELECT *
                    FROM EMP
                    ORDER BY JOB DESC, HIREDATE DESC;
                    /*
                        -- 두가지 이상의 정렬 기준이 필요할경우 (,)로 구분하여 지정
                        -- 1차: JOB DESC, 2차: HIREDATE DESC

        ETC

            부서번호가 10이 아닌 사원*/
                SELECT * FROM EMP WHERE NOT (DEPTNO = 10);
                SELECT * FROM EMP WHERE DEPTNO <>10;
                
            /*급여가 1000달러 이상, 3000달러 이하*/
                SELECT * FROM EMP WHERE SAL >= 1000 AND SAL <= 3000;
                SELECT * FROM EMP WHERE SAL BETWEEN 1000 AND 3000;
                
            /*특정 필드값이 널인 레코드 또는 널이 아닌 레코드*/
                SELECT * FROM EMP WHERE COMM IS NULL; -- COMM 필드가 NULL인 레코드
                SELECT * FROM EMP WHERE COMM IS NOT NULL; -- COMM 필드가 NULL이 아닌 레코드
                
            /*사원의 연봉 출력*/
                SELECT DEPTNO, ENAME, SAL*12 AS 연봉 FROM EMP;
                
            /*커미션을 포함한 사원의 연봉 출력*/
                SELECT DEPTNO, ENAME, SAL*12 + COMM AS 연봉 FROM EMP; -- NULL 포함 계산되어 오류발생
                SELECT DEPTNO, ENAME, COMM, SAL*12+NVL(COMM, 0) AS 연봉 FROM EMP; -- NULL방어함수사용
                SELECT * FROM EMP;
                /*

*/