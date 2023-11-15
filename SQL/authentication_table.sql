USE floor_is_java_CiaraS;

CREATE TABLE `role` (
	role_id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name varchar(64) NOT NULL

);

INSERT INTO role(role_id, name) VALUES (1,'Admin');
INSERT INTO role (role_id, name) VALUES (2, 'User');

CREATE TABLE `user` (

	username varchar(64) NOT NULL,
    password varchar(64) NOT NULL,
    role_id TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (username),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

INSERT INTO `user`(username, password, role_id) VALUES ('admin', 'admin', 1);
INSERT INTO `user`(username, password, role_id) VALUES ('user', 'user', 2);

CREATE TABLE token (

	username varchar(64) NOT NULL,
    token varchar(64) NOT NULL,
    expiry DATETIME NOT NULL,
    FOREIGN KEY (username) REFERENCES `user`(username)

);
SHOW TABLES;