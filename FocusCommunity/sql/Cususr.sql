/* Cususr Table Create script */
CREATE TABLE Cususr(
    CUSID varchar(20) NOT NULL , 
    PASWD varchar(60), 
    CNAME varchar(40), 
    PHONE varchar(20), 
    BIRTH varchar(10), 
    EMAIL varchar(40), 
    ZCODE varchar(10), 
    ADRES varchar(255), 
    ADR02 varchar(255), 
    CHINT varchar(100), 
    RESLT varchar(100), 
    CDATE date, 
    ADATE date, 
    FDATE date, 
    Primary Key (CUSID)
);