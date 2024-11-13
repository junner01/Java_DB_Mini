"# Java_DB_Mini" 

##DB Table 추가 241113

CREATE TABLE board( 
   boardNo NUMBER(4) CONSTRAINT boardNO_PK PRIMARY KEY, 
   title VARCHAR2(100) not null, 
   content VARCHAR2(1000) not null, 
   writer VARCHAR2(100) not null,
   empno NUMBER(4) CONSTRAINT board_empNO_FK REFERENCES emp (empno),
   regdate DATE
);
