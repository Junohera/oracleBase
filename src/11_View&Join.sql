
/* 
    view

        물리적인 테이블에 근거한 논리적인 가상 테이블
        사용자는 테이블과 동일하게 뷰를 사용
        사용자에게 주어진 뷰를 통해 기본테이블을 제한적으로 사용하게됨
        뷰는 이미 존재하고있는 테이블에 제한적으로 접근하도록 한다.
        뷰를 생성하기위해서는 실질적으로 데이터를 저장하고 있는 물리적인 테이블이 존재해야하는데, 그 테이블을 기본테이블이라고 한다.
    */
    
    /*
        how to create
        CREATE OR REPLACE VIEW ${VIEW_NAME}
    */
        CREATE OR REPLACE VIEW RESULT_INNER_JOIN AS
        SELECT
            E.EMPNO
            , E.ENAME
            , E.JOB
            , E.HIREDATE
            , E.DEPTNO
            , D.DNAME
            , D.LOC
        FROM EMP E, DEPT D
        WHERE E.DEPTNO = D.DEPTNO;

        SELECT * FROM RESULT_INNER_JOIN;
        SELECT EMPNO, ENAME, JOB FROM RESULT_INNER_JOIN;
        SELECT EMPNO, ENAME, JOB FROM RESULT_INNER_JOIN WHERE JOB = 'MANAGER';

    /*

    /* 
    Sub Query

        하나의 SELECT 문장의 절 안에 포함된 또하나의 SELECT 쿼리문
    */

    SELECT DNAME, LOC
    FROM DEPT
    WHERE DEPTNO = (SELECT DEPTNO FROM EMP WHERE ENAME = 'SCOTT');

    /*
    Sub Query & Group Function
    */
        -- 전체 사원 평균급여보다 더 많은 급여를 받는 사원의 이름, 급여, 포지션
        SELECT ENAME, SAL, JOB FROM EMP WHERE SAL >= (SELECT AVG(SAL) FROM EMP);

        -- 다중행 서브쿼리 : 서브쿼리의 결과가 둘 이상(=, >= 말고 IN()
        -- 급여를 2000이상 받는 사원이 소속된 부서와 소속된 부서에서 근무하는 사원들의 이름, 부서번호, JOB
        SELECT DEPTNO FROM EMP WHERE SAL >= 2000; -- 급여 2000이상의 부서번호 (중복포함)
        SELECT DISTINCT DEPTNO FROM EMP WHERE SAL >= 2000; -- 급여 2000이상의 부서번호 (중복제거)

        -- 급여 2000 이상 사원의 부서번호(중복제거)
        SELECT 
            ENAME
            , SAL
            , JOB
            , DEPTNO
        FROM EMP
        WHERE
            DEPTNO IN(SELECT DISTINCT DEPTNO FROM EMP WHERE SAL >= 2000); -- 2000이상 급여받는 사원의 부서가 중복될 수 있으니 DISTINCT를 사용
        


-- SUB QUERY PRACTICE

    -- SCOTT과 동일직급(job)을 가진 사원을 조회
    SELECT
        E.*
    FROM EMP E
    WHERE
       E.JOB = (SELECT E.JOB FROM EMP E WHERE E.ENAME = 'SCOTT');
    
    -- SCOTT의 급여이상으로 받는 사원의 이름과 급여 출력
    SELECT
        E.ENAME
        , E.SAL
    FROM EMP E
    WHERE
        E.SAL >= (SELECT SAL FROM EMP WHERE ENAME = 'SCOTT');

    -- 30번 부서 소속 사원들 중에서 급여를 가장 많이 받는 사원보다 ... 급여가 더 많은 사원의 이름과 job, 급여
    SELECT ENAME, JOB, SAL FROM EMP WHERE
    SAL > (SELECT MAX(SAL) FROM EMP WHERE DEPTNO = 30);

    SELECT ENAME, JOB, SAL FROM EMP WHERE
    SAL > ALL(SELECT SAL FROM EMP WHERE DEPTNO = 30); -- 동일: 30번부서의 사원들의 급여들과 비교했을때 누구보다 높은 급여를 받는 사원

    -- 부서번호가 30번인 사원들의 급여 중에서 가장 낮은 급여보다... 높은 급여를 받는 사원의 이름과 job, 급여
    SELECT ENAME, JOB, SAL FROM EMP WHERE
    SAL > (SELECT MIN(SAL) FROM EMP WHERE DEPTNO = 30); -- 30번부서의 최저급여보다 높은 급여를 받는 사원

    SELECT ENAME, JOB, SAL FROM EMP WHERE
    SAL > ANY(SELECT SAL FROM EMP WHERE DEPTNO = 30); -- 동일: 30번 부서의 사원들과 비교하여 최소한 한명보다는 높은 급여를 받는 사원

    -- 영업사원(job='SALESMAN')들의 최소 급여보다 많이 받는 사원들의 이름, 직급, 급여를 출력하되, 영업사원은 제외
    SELECT ENAME, SAL, JOB FROM EMP
    WHERE
        SAL > (SELECT MIN(SAL) FROM EMP WHERE JOB = 'SALESMAN')
        AND JOB <> 'SALESMAN';

    SELECT ENAME, SAL, JOB FROM EMP
    WHERE
        SAL > ANY(SELECT SAL FROM EMP WHERE JOB = 'SALESMAN')
        AND JOB <> 'SALESMAN'; -- 동일 : ANY 