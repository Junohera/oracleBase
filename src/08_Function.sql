/*
    Function 내장 함수

        [1] 샘플데이터인 dual테이블*/
        SELECT * FROM TAB; -- 현재 사용자가 소유한 테이블 목록
        SELECT * FROM DUAL; -- 라이브러리 함수의 결과를 보기위한 키워드 사용 DUAL : 임시데이터 출력시 사용
        /*임시 데이터란 특정 테이블을 대상으로 하는 SELECT문이 아니면서 계산의 결과가 출력되어야 하는 곳에 쓰는 키워드
        
        임시 데이터 출력*/
        SELECT SAL * 12 + NVL(COMM, 0) AS "연봉" FROM EMP;
        SELECT 1234 * 1234 FROM DUAL; -- 특정테이블이 아닌 결과
        /*

        문자열

            LOWER 모든 문자 소문자*/
                SELECT LOWER('Hong Kil Dong') AS "소문자" FROM DUAL;
            /*UPPER 모든 문자 대문자*/
                SELECT UPPER('Hong Kil Dong') AS "대문자" FROM DUAL;
            /*INITCAP 모든단어의 첫자 대문자*/
                SELECT INITCAP(LOWER('Hong Kil Dong')) AS "첫글자만 대문자" FROM DUAL;
            /*CONCAT 문자열 연결*/
                SELECT CONCAT('이젠 IT', '아카데미') FROM DUAL;
            /*LENGTH 문자열 길이*/
                SELECT LENGTH('이젠 아이티 아카데미') AS LEN1, LENGTH('The ezen IT') AS LEN2 FROM DUAL;
            /*SUBSTR 문자열 추출 : SUBSTR('TARGET', BEGIN_INDEX, COUNT)*/
                SELECT SUBSTR('홍길동 만세', 2, 4) FROM DUAL;
            /*INSTR 문자열 시작위치 찾기*/
                SELECT INSTR('홍길동 만세 동그라미', '동') FROM DUAL;
            /*LPAD, RPAD*/
                SELECT LPAD('Oracle', 20, '#') FROM DUAL;
                SELECT RPAD('Oracle', 20, '*') FROM DUAL;
            /*TRIM 앞뒤로 특정문자 제거*/
                SELECT TRIM('a' FROM 'aaaOracleaaaaaaaaaaaaaa') AS RESULT FROM DUAL;
                SELECT TRIM(' ' FROM '    Oracle   ') AS RESULT FROM DUAL;/* 
        
        숫자

            ROUND*/
                SELECT ROUND(1728.9382, 3) FROM DUAL; -- 1728.938
                SELECT ROUND(1728.9382, 2) FROM DUAL; -- 1728.94
                SELECT ROUND(1728.9382, 1) FROM DUAL; -- 1728.9
                SELECT ROUND(1728.9382, 0) FROM DUAL; -- 1729
                SELECT ROUND(1728.9382, -1) FROM DUAL; -- 1730
                SELECT ROUND(1728.9382, -2) FROM DUAL; -- 1700
                SELECT ROUND(1728.9382, -3) FROM DUAL; -- 2000
                /*

                3 : 소수점 넷째자리에서 반올림하여 셋째자리까지 남김
                2 : 소수점 셋째자리에서 반올림하여 둘째자리까지 남김
                1 : 소수점 둘째자리에서 반올림하여 첫째자리까지 남김
                0 : 소수점 첫째자리에서 반올림하여 소수점 자리 없앰
                -1 : 1의 자리에서 반올림하여 10의 자리까지 남김
                -2 : 10의 자리에서 반올림하여 100의 자리까지 남김
                -3 : 100의 자리에서 반올림하여 1000의 자리까지 남김

            ABS 절대값*/
                SELECT ABS(-10) FROM DUAL; -- 10
            /*FLOOR 소수점 아래 절사*/
                SELECT FLOOR(12.94567) FROM DUAL; -- 12
            /*CEIL 소수점 아래 올림*/
                SELECT CEIL(12.14567) FROM DUAL; -- 13
            /*TRUNC 버림*/
                SELECT TRUNC(12.34567, 3) FROM DUAL; -- 12.345
            /*MOD 나머지*/
                SELECT MOD(8, 5) FROM DUAL;
            /*

        날짜

            SYSDATE : 날짜*/
                SELECT SYSDATE FROM DUAL; -- 12/17/2020 17:30:38
            /*MONTHS_BETWEEN : 개월수*/
                SELECT MONTHS_BETWEEN('2021-12-31', '2020-07-10') FROM DUAL;
            /*ADD_MONTHS : 개월수 더하기*/
                SELECT ADD_MONTHS(SYSDATE, 200) FROM DUAL;
            /*NEXT_DAY : 다가올 요일에 해당하는 날짜*/
                SELECT NEXT_DAY(SYSDATE, '일요일') FROM DUAL;
            /*LAST_DAY : 해당 달의 마지막 일*/
                SELECT LAST_DAY(SYSDATE) FROM DUAL;
                SELECT LAST_DAY('2020-12-15') FROM DUAL;
            /*TO_CHAR */
                SELECT TO_CHAR(SYSDATE, 'yyyy-mm-dd') FROM DUAL;
            /*TO_DATE*/
                SELECT TO_DATE('2019/12/31', 'yyyy/mm/dd') FROM DUAL;
                SELECT TO_DATE('2019/12/31', 'YYYY/MM/DD') FROM DUAL;
            

            /*NVL*/
            SELECT COMM / 100 AS COMM_PCT FROM EMP;
            SELECT NVL(COMM, 1) / 100 AS COMM_PCT FROM EMP;

            /*DECODE*/
            SELECT 
                ENAME
                , DEPTNO
                , DECODE ( DEPTNO, 10, 'ACCOUNT', 20, 'RESEARCH', 30, 'SALES', 40, 'OPERATIONS', 50, 'SH_CLECK', 60, 'IT_PROG', 70, 'PR_REP', 80, 'SA_REP', 90, 'AD_PRES', 100, 'FI_ACCOUNT', 110, 'AC_ACCOUNT') AS "부서이름"
            FROM EMP;

            /*DECODE*/
                SELECT
                    ENAME
                    , DEPTNO
                    , CASE WHEN DEPTNO = 10 THEN 'ACCOUNT'
                            WHEN DEPTNO = 20 THEN 'RESEARCH'
                            WHEN DEPTNO = 30 THEN 'SALES'
                            WHEN DEPTNO = 40 THEN 'OPERATIONS'
                            WHEN DEPTNO = 50 THEN 'SH_CLECK'
                            WHEN DEPTNO = 60 THEN 'IT_PROG'
                            WHEN DEPTNO = 70 THEN 'PR_REP'
                            WHEN DEPTNO = 80 THEN 'SA_REP'
                            WHEN DEPTNO = 90 THEN 'AD_PRES'
                            WHEN DEPTNO = 100 THEN 'FI_ACCOUNT'
                            WHEN DEPTNO = 110 THEN 'AC_ACCOUNT'
                    END AS "부서명"
                FROM EMP;
            /*
*/