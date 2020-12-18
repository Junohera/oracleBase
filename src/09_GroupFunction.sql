/*
    Internal Function들의 연산 대상이 필드 또는 레코드였다면
    그룹함수는 테이블 전체를 대상으로 하는 함수를 지칭합니다.

    일반적인 한 명의 학생의 총점 : SELECT 과목1+과목2+과목3+과목4 AS 총점 FROM 성적;
        => 학생수 만큼의 결과
    그룹함수를 이용한 과목 총점 : SELECT SUM(과목1) AS 총점 FROM 성적;
        => 결과는 하나
*/

SELECT * FROM EMP;
SELECT SAL FROM EMP;
SELECT SUM(SAL) AS 총급여 FROM EMP;
SELECT COUNT(*) AS 사원수 FROM EMP;

/* SUM */
SELECT SUM(SAL) AS 총급여 FROM EMP;
/* COUNT */
SELECT COUNT(*) AS 사원수 AS FROM EMP;
/* AVG */
SELECT ROUND(AVG(SAL), 2) AS 평균급여 FROM EMP;
/* MAX */
SELECT MAX(SAL) AS 최대급여 FROM EMP;
/* MIN */
SELECT MIN(SAL) AS 최소급여 FROM EMP;

/* GROUP BY절 : 직업별 평균 급여 */
SELECT JOB, ROUND(AVG(SAL), 2) AS 평균급여 FROM EMP GROUP BY JOB;

/* HAVING 절 : 그룹화한 데이터들에 조건을 걸어 필터링
    직업별 평균급여에서 평균급여가 2000이상인 데이터만 표시
    (= 평균급여가 2000이상인 직업의 데이터만)
*/
SELECT JOB, ROUND(AVG(SAL), 2) AS 평균급여
FROM EMP
GROUP BY JOB
HAVING AVG(SAL) > 2000;

