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

CREATE TABLE relations(
	id int,
	relationTypeId int,
	startPointId int,
	endPointId int,
	value varchar,
	relationId int);

CREATE TABLE relationTypes(
	id int,
	relationType varchar);

CREATE TABLE elementTypes(
	id int NOT NULL AUTO_INCREMENT,
	name varchar,
	elementType int,
	description varchar,
	picture int,
	PRIMARY KEY (id)
);

INSERT INTO elementTypes(id, name, elementType, description, picture) VALUES (1, 'Swimlane', 1, 'Swimlane contains many smaller elements', 0);
INSERT INTO elementTypes VALUES (2, 'Start Element', 2, 'Start element begins process', 0);

CREATE TABLE processElements (
	modelProcessId int NOT NULL AUTO_INCREMENT,
	elementTypeId int NOT NULL AUTO_INCREMENT,
	relationId int NOT NULL AUTO_INCREMENT,
	value varchar,
	size int,
	xCoord int,
	yCoord int,
	FOREIGN KEY (modelProcessId) REFERENCES modelProcesses (id),
	FOREIGN KEY (elementTypeId) REFERENCES elementTypes (id)
);

# --- !Downs 
DROP TABLE IF EXISTS processElements;
DROP TABLE IF EXISTS modelProcesses;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS processes;
DROP TABLE IF EXISTS relations;
DROP TABLE IF EXISTS relationTypes;
DROP TABLE IF EXISTS elementTypes;