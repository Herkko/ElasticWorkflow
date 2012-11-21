# --- !Ups
DROP TABLE IF EXISTS processElements;

CREATE TABLE processElements (
	id 				int(10) 		NOT NULL AUTO_INCREMENT,
	modelProcessId	int(10) 		NOT NULL AUTO_INCREMENT,
	elementTypeId 	int(10) 		NOT NULL AUTO_INCREMENT,
	value 			varchar(100)	NOT NULL,
	size 			int(10)			NOT NULL,
	x 				int(10)			NOT NULL,
	y 				int(10)			NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (modelProcessId) REFERENCES modelProcesses (id),
	FOREIGN KEY (elementTypeId) REFERENCES elementTypes (id)
);

# --- !Downs
DROP TABLE IF EXISTS processElements;