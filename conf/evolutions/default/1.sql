# --- !Ups
	

CREATE TABLE models(
	id 			int(10) 	 NOT NULL AUTO_INCREMENT,
	name 		varchar(100) NOT NULL,
	dateCreated varchar(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE processes(
	id 			int(10) 	 NOT NULL AUTO_INCREMENT,
	name 		varchar(100) NOT NULL,
	dateCreated varchar(100) NOT NULL ,
	PRIMARY KEY (id)	
);

CREATE TABLE modelProcesses(
	id 			int(10) 	NOT NULL AUTO_INCREMENT,
	modelId 	int(10) 	NOT NULL AUTO_INCREMENT,
	processId 	int(10) 	NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (id),
	FOREIGN KEY (modelId) REFERENCES models (id),
	FOREIGN KEY (processId) REFERENCES processes (id)
);


CREATE TABLE relationTypes(
	id 				int(10) 		NOT NULL AUTO_INCREMENT,
	relationType 	varchar(100)	NOT NULL,
	PRIMARY KEY (id)
);
INSERT INTO relationTypes VALUES (1, 'normal line');

CREATE TABLE elementTypes(
	id 				int(10) 		NOT NULL AUTO_INCREMENT,
	name 			varchar(100)	NOT NULL,
	elementType 	int(10)			NOT NULL,
	description 	varchar(200)    NOT NULL,
	picture 		int(10)			NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO elementTypes(id, name, elementType, description, picture) VALUES (1, 'Swimlane', 1, 'rect', 0);
INSERT INTO elementTypes VALUES (2, 'Start', 2, 'circle', 0);
INSERT INTO elementTypes VALUES (3, 'End', 3, 'circle', 0);
INSERT INTO elementTypes VALUES (4, 'Activity', 4, 'rect', 0);
INSERT INTO elementTypes VALUES (5, 'Gateway', 5, 'circle', 0);

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
DROP TABLE IF EXISTS modelProcesses;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS processes;
DROP TABLE IF EXISTS relations;
DROP TABLE IF EXISTS relationTypes;
DROP TABLE IF EXISTS elementTypes;