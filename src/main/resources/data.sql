DROP TABLE IF EXISTS POST;

CREATE TABLE POST
(
    ID int NOT NULL AUTO_INCREMENT,
    TITLE VARCHAR(50) NOT NULL ,
    DESCRIPTION VARCHAR(200) NOT NULL,
    CREATED_AT DATETIME DEFAULT NULL
)