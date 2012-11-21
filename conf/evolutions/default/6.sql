# --- !Ups
DROP TABLE IF EXISTS elementTypes;

CREATE TABLE elementTypes(
	id 				int(10) 		NOT NULL AUTO_INCREMENT,
	name 			varchar(100)	NOT NULL,
	description 	varchar(200)    NOT NULL,
	picture 		int(10)			NOT NULL,
	PRIMARY KEY (id)
);

# --- !Downs
DROP TABLE IF EXISTS elementTypes;