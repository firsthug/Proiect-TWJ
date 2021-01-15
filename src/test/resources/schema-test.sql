
CREATE TABLE USERS (
  user_id number(3) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  tip varchar(2) NOT NULL,
  PRIMARY KEY (user_id)
);

create sequence MEDCOMSEQ
start with 1
increment by 1;
