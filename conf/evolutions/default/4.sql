# --- !Ups
DROP TABLE IF EXISTS relationTypes;

CREATE TABLE relationTypes(
	id 				int(10) 		NOT NULL AUTO_INCREMENT,
	relationType 	varchar(100)	NOT NULL,
	PRIMARY KEY (id)
);

# --- !Downs
DROP TABLE IF EXISTS relationTypes;