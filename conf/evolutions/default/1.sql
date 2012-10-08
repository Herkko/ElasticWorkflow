# --- !Ups

CREATE TABLE processElements (
	modelProcessId int,
	elementTypeId int,
	relationId int,
	value varchar,
	size int,
	xCoord int,
	yCoord int);
	
CREATE TABLE modelProcesses(
	id int,
	modelId int,
	processId int,
	dateCreated Date);

CREATE TABLE models(
	id int NOT NULL AUTO_INCREMENT,
	name varchar,
	dateCreated Date,
	PRIMARY KEY (id)
);

CREATE TABLE processes(
	id int,
	name varchar,
	dateCreated Date);

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
	id int,
	name varchar,
	elementType int,
	description varchar,
	picture int);

# --- !Downs 

DROP TABLE IF EXISTS processElements;
DROP TABLE IF EXISTS modelProcesses;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS processes;
DROP TABLE IF EXISTS relations;
DROP TABLE IF EXISTS relationTypes;
DROP TABLE IF EXISTS elementTypes;