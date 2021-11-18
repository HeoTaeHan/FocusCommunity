/* Cusbod Table Create script */
CREATE TABLE Cusbod(
    CSNUM int NOT NULL , 
    CTYPE varchar(20) NOT NULL , 
    CUSID varchar(20), 
    CSBJT varchar(50), 
    CCONT text, 
    CRDCN int, 
    CDATE datetime, 
    CTMID varchar(20), 
    CRERF int, 
    CRELV int, 
    CRESQ int, 
    Primary Key (CSNUM, CTYPE)
);