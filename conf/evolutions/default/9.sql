# --- !Ups
DROP TABLE IF EXISTS relations;

CREATE TABLE relations(
	id 					int(10) 	NOT NULL AUTO_INCREMENT,
	startId 			int(10)		NOT NULL AUTO_INCREMENT,
	endId 				int(10)		NOT NULL AUTO_INCREMENT,
	relationTypeId 		int(10)		NOT NULL AUTO_INCREMENT,
	value 				varchar(100),
	PRIMARY KEY (id),
	FOREIGN KEY (startId) REFERENCES processElements (id),	
	FOREIGN KEY (endId) REFERENCES processElements (id),	
	FOREIGN KEY (relationTypeId) REFERENCES relationTypes (id)
);

# --- !Downs
DROP TABLE IF EXISTS processElements;