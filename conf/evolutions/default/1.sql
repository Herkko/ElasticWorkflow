# --- !Ups
DROP TABLE IF EXISTS models;

CREATE TABLE models(
	id 			int(10) 	 NOT NULL AUTO_INCREMENT,
	name 		varchar(100) NOT NULL,
	dateCreated varchar(100) NOT NULL,
	PRIMARY KEY (id)
);

# --- !Downs 
DROP TABLE IF EXISTS models;