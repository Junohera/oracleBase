CREATE TABLE CUSTOMER (
	NUM NUMBER(3),
	NAME VARCHAR2(10),
	EMAIL VARCHAR2(12),
	TEL VARCHAR2(15)
);

INSERT INTO CUSTOMER VALUES (1, '홍길동', 'abc1@abc.com', '010-1234-1234');
INSERT INTO CUSTOMER VALUES (2, '홍길서', 'abc2@abc.com', '010-1234-1234');
INSERT INTO CUSTOMER VALUES (3, '홍길남', 'abc3@abc.com', '010-1234-1234');
INSERT INTO CUSTOMER VALUES (4, '홍길북', 'abc4@abc.com', '010-1234-1234');
INSERT INTO CUSTOMER VALUES (5, '아무개', 'abc5@abc.com', '010-1234-1234');

SELECT * FROM CUSTOMER;