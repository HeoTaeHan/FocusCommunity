/* Csrepl Table Create script */
CREATE TABLE Csrepl(
    CSNUM int NOT NULL , 
    CTYPE varchar(20) NOT NULL , 
    CONUM int NOT NULL , 
    CUSID varchar(20), 
    CNTEN text, 
    CRERF int, 
    CRELV int, 
    CRESQ int, 
    TRMID varchar(20), 
    CDATE datetime, 
    Primary Key (CSNUM, CTYPE, CONUM)
);