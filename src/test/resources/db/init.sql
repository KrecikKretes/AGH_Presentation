CREATE TABLE endpoint_model (
     id int PRIMARY KEY,
     template varchar(50),
     email_origin varchar(100),
     email_destination varchar(100)
);

INSERT INTO endpoint_model (id, template, email_origin, email_destination) VALUES (4, 'test-template1', 'test-origin1@test.com', 'test-destination1@test.com');
INSERT INTO endpoint_model (id, template, email_origin, email_destination) VALUES (5, 'test-template1', 'test-origin1@test.com', 'test-destination2@test.com');
INSERT INTO endpoint_model (id, template, email_origin, email_destination) VALUES (6, 'test-template2', 'test-origin2@test.com', 'test-destination2@test.com');