INSERT INTO AUTHORITY (id, name) VALUES (1,"ROLE_USER");
INSERT INTO AUTHORITY (id, name) VALUES (2,"ROLE_ADMIN");

INSERT INTO USER(id, username, password, email, name, confirmed, enabled, version) VALUES (1, "user", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "user@mailinator.com", "User", true, true, 0);
INSERT INTO USER(id, username, password, email, name, confirmed, enabled, version) VALUES (2, "admin", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "admin@mailinator.com", "Admin", true, true, 0);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);
