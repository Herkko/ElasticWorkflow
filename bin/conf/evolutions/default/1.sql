# --- !Ups
	
CREATE TABLE models(
	id int NOT NULL AUTO_INCREMENT,
	name varchar,
	dateCreated Date,
	PRIMARY KEY (id)
);

CREATE TABLE processes(
	id int NOT NULL AUTO_INCREMENT,
	name varchar,
	dateCreated Date,
	PRIMARY KEY (id)	
);

CREATE TABLE modelProcesses(
	id int NOT NULL AUTO_INCREMENT,
	modelId int NOT NULL AUTO_INCREMENT,
	processId int NOT NULL AUTO_INCREMENT,
	dateCreated Date,
	PRIMARY KEY (id),
	FOREIGN KEY (modelId) REFERENCES models (id),
	FOREIGN KEY (processId) REFERENCES processes (id)
);

CREATE TABLE relationTypes(
	id int NOT NULL AUTO_INCREMENT,
	relationType varchar,
	PRIMARY KEY (id)
);
INSERT INTO relationTypes VALUES (1, 'normal line');

CREATE TABLE elementTypes(
	id int NOT NULL AUTO_INCREMENT,
	name varchar,
	elementType int,
	description varchar,
	picture int,
	PRIMARY KEY (id)
);

INSERT INTO elementTypes(id, name, elementType, description, picture) VALUES (1, 'Swimlane', 1, 'rect', 0);
INSERT INTO elementTypes VALUES (2, 'Start', 2, 'circle', 0);
INSERT INTO elementTypes VALUES (3, 'End', 3, 'circle', 0);
INSERT INTO elementTypes VALUES (4, 'Action', 4, 'rect', 0);

CREATE TABLE processElements (
	modelProcessId int NOT NULL AUTO_INCREMENT,
	elementTypeId int NOT NULL AUTO_INCREMENT,
	relationId int NOT NULL AUTO_INCREMENT,
	value varchar,
	size int,
	xCoord int,
	yCoord int,
	PRIMARY KEY (relationId),
	FOREIGN KEY (modelProcessId) REFERENCES modelProcesses (id),
	FOREIGN KEY (elementTypeId) REFERENCES elementTypes (id)
);


CREATE TABLE relations(
	id int NOT NULL AUTO_INCREMENT,
	relationTypeId int NOT NULL AUTO_INCREMENT,
	startPointId int,
	endPointId int,
	value varchar,
	relationId int NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (id),
	FOREIGN KEY (relationTypeId) REFERENCES relationTypes (id),
	FOREIGN KEY (relationId) REFERENCES processElements (relationId)	
);

# --- !Downs 
DROP TABLE IF EXISTS processElements;
DROP TABLE IF EXISTS modelProcesses;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS processes;
DROP TABLE IF EXISTS relations;
DROP TABLE IF EXISTS relationTypes;
DROP TABLE IF EXISTS elementTypes;