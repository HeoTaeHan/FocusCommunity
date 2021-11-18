/* Crecom Table Create script */
CREATE TABLE Crecom(
    CSNUM int NOT NULL , 
    CTYPE varchar(20) NOT NULL , 
    CUSID varchar(20) NOT NULL , 
    RECOM int, 
    Primary Key (CSNUM, CTYPE, CUSID)
);