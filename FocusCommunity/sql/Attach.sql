/* Attach Table Create script */
CREATE TABLE Attach(
    UUID varchar(36) NOT NULL , 
    UPLOADPATH varchar(10), 
    FILENAME varchar(255), 
    FILETYPE varchar(1), 
    BNO int, 
    CUSID varchar(20), 
    Primary Key (UUID)
);