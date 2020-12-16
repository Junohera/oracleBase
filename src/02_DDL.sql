/*
    DDL(Data Definition Language) -- 데이터 정의어
    
    테이블의 생성(CREATE)

        CREATE TABLE ${tableName} (
            ${fieldName1} DATATYPE [DEFAULT CONSTRAINT TYPE]
            ${fieldName2} DATATYPE [DEFAULT CONSTRAINT TYPE]
            ${fieldName3} DATATYPE [DEFAULT CONSTRAINT TYPE]
            ${fieldName4} DATATYPE [DEFAULT CONSTRAINT TYPE]
            ...
        )

    CREATE TABLE 명령의 특징 및 규칙
        - 테이블명은 객체를 의미할 수 있는 적절한 이름을 사용
        - 다른테이블과 중복불가
        - 한 테이블내에서 필드이름 중복불가
        - 각 필드는 ,로 구분하여 생성
        - CREATE를 비롯한 모든 SQL명령은 ;로 종료
        - 필드 뒤에 DATATYPE은 반드시 지정하고 []안에 내용은 해당내용이 있을 때 작성하며, 생략 가능
        - 테이블명괴 필드명은 반드시 문자로 시작해야하고 예약어 명령어등을 테이블명과 필드명으로 쓸 수 없습니다.
        - 테이블 생성시 대/소문자 구분은 하지 않습니다.(기본적으로 테이블이나 컬럼명은 대문자로)
        - DATE유형은 별도로 크기를 지정하지 않습니다.
        - 문자 데이터유형은 반드시 가질 수 있는 최대 길이를 표시해야 합니다.
        - 컬럼과 컬럼의 구분은 컴마로 하되, 마지막 컬럼은 컴마를 찍지 않습니다.
*/

--테이블 작성의 예(도서대여점 테이블)
CREATE TABLE BookList(
    BookNum varchar2(5) Not null, -- 5글자의 문자열, Null값이 될 수 없는 제약 조건 설정
    title varchar2(30) Not null,
    makeyear number(4), -- 출판연도, 숫자형식, 4바이트가 아니고, 4자리 숫자라는 의미
    inprice number(6), -- 입고가격
    outprice number(6), -- 출고가격

    constraint booklist_pk primary key(BookNum)
    -- 추가 제약 조건 BookNum을 기본키로 설정
    -- 테이블의 외부에서 현재의 제약 조건을 bookilist_pk로 접근가능합니다.
);

SELECT * FROM BOOKLIST; -- 테이블의 내용을 조회하는 명령
/*
    컬럼에 대한 제약조건이 있으면 CONSTRAINT를 이용하여 추가할 수 있습니다.
    제약조건은 bookNum뒤에 Not Null을 기술한 것과 같은 필드 LEVEL방식과
    CONSTRAINT로 테이블 생성 마지막에 모든 제약조건을 기술하는 테이블 LEVEL방식이 있다.
*/

/*
    제약조건(CONSTRAINT)
    
        PRIMARY
            테이블에 저장된 레코드를 고유하게 식별하기 위한 키. 하나의 테이블에 하나의 기본키만 정의할 수 있습니다.
            여러 필드가 조합된 기본키 생성이 가능합니다. - 기본키는 중복 값을 가질 수 없으며 빈칸도 있을 수 없습니다.
            (ex: PRIMARY = UNIQUE + NOT NULL)

        UNIQUE
            테이블에 저장된 행 데이터를 고유하게 식별하기 위한 고유키를 정의
            단 NULL은 고유키 제약의 대상이 아니므로, NULL값을 가진 여러개가 UNIQUE KEY 제약에
            위반하지는 않습니다.
        
        NOT NULL
            비어있는 상태, 아무것도 없는 상태를 허용하지 않습니다. - 입력 필수
        
        CHECK
            입력할 수 있는 값의 범위를 제한하며, CHECK제약으로는 TRUE or FALSE로 평가할 수 있는 논리식을 지정

        FOREIGN KEY
            관계형 데이터베이스에서 테이블간에 관계를 정의하고 공통 필드간의 참조관계를 설정할 때 사용.
*/

/*
    테이블생성 2
    테이블 이름 : Person
    필드 : personNum, personName, phone, birth, bPoint
    데이터 형식 : VARCHAR2(5), VARCHAR2(12), VARCHAR2(13), DATE, NUMNBER(6)
    제약조건 : Not Null[personNum, personName, phone], primary key[personNum]
    기본값 : bPoint - 0
*/
CREATE TABLE PERSON (
    personNum VARCHAR2(5) NOT NULL,
    personName VARCHAR2(12) NOT NULL,
    phone VARCHAR2(13) NOT NULL,
    birth DATE,
    enterDate DATE default sysdate,
    bPoint NUMBER(6) default 0,

    CONSTRAINT PERSON_pk PRIMARY KEY (personNum)
);

SELECT *
FROM tab;

CREATE TABLE IN_OUT (
    OUT_DATE DATE NOT NULL,
    INDEXK NUMBER(3) NOT NULL,
    BOOKNUM VARCHAR2(5) NOT NULL,
    PERSONNUM VARCHAR2(5) NOT NULL,
    DISCONUT NUMBER(6),

    CONSTRAINT IN_OUT_PK PRIMARY KEY (OUT_DATE, INDEXK),
    CONSTRAINT FK1 FOREIGN KEY(BOOKNUM) REFERENCES BOOKLIST(BOOKNUM)
);

SELECT * FROM IN_OUT;
/*
    OUT_DATE, INDEXK 두개의 필드를 조합하여 기본키(IN_OUT_PK)를 생성함
    booknum은 IN_OUT테이블의 외래키(fk1)로 BOOKLIST 테이블의 BOOKNUM을 참조
    PSERSONNUM은  IN_OUT테이블의 외래키(fk2)로 PERSON 테이블의 PERSONNUM 참조
*/
