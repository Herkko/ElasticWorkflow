# --- !Ups

INSERT INTO relationTypes VALUES (1, 'normal line');

# --- !Downs

delete from relationTypes where id > 0;