# --- !Ups
DROP TABLE IF EXISTS modelProcesses;

CREATE TABLE modelProcesses(
	id 			int(10) 	NOT NULL AUTO_INCREMENT,
	modelId 	int(10) 	NOT NULL AUTO_INCREMENT,
	processId 	int(10) 	NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (id),
	FOREIGN KEY (modelId) REFERENCES models (id),
	FOREIGN KEY (processId) REFERENCES processes (id)
);

# --- !Downs
DROP TABLE IF EXISTS modelProcesses;