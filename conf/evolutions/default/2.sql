# --- !Ups
DROP TABLE IF EXISTS processes;

CREATE TABLE processes(
	id 			int(10) 	 NOT NULL AUTO_INCREMENT,
	name 		varchar(100) NOT NULL DEFAULT 'Empty',
	dateCreated timestamp 	 NOT NULL DEFAULT current_timestamp,
	PRIMARY KEY (id)	
);

# --- !Downs
DROP TABLE IF EXISTS processes;