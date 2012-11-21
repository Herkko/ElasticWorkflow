# --- !Ups

INSERT INTO elementTypes(id, name, description, picture) VALUES (1, 'Swimlane', 'rect', 0);
INSERT INTO elementTypes VALUES (2, 'Start', 'circle', 0);
INSERT INTO elementTypes VALUES (3, 'End', 'circle', 0);
INSERT INTO elementTypes VALUES (4, 'Activity', 'rect', 0);
INSERT INTO elementTypes VALUES (5, 'Gateway', 'circle', 0);

# --- !Downs

delete from elementTypes where id > 0;