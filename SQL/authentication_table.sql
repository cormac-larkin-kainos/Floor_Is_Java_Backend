USE floor_is_java_CiaraS;

CREATE TABLE `role` (
	role_id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name varchar(64) NOT NULL

);

INSERT INTO role(role_id, name) VALUES (1,'Admin');
INSERT INTO role (role_id, name) VALUES (2, 'User');

CREATE TABLE `user` (
    `user_id` smallint unsigned NOT NULL AUTO_INCREMENT,
    `email` varchar(100) NOT NULL,
    `password_hash` blob NOT NULL,
    `password_hash_iterations` int unsigned NOT NULL,
    `password_salt` binary(16) NOT NULL,
    `role_id` TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `email` (`email`)
);

INSERT INTO `user` (email, password_hash, password_hash_iterations, password_salt, role_id)
VALUES('test@kainos.com',
0xF1B4A71AD29E3398068BCF8AF0580834CF18DD126922899239F037964D6FB7E40A587BEBD6768B5A1BE2890DAF4D5C57382CF901C22003FDA697FFB78217B335,
65536,
0xD7862CF028FC9FC5A706F14C47DE6A54,
2);

INSERT INTO `user` (email, password_hash, password_hash_iterations, password_salt, role_id)
VALUES('admin@kainos.com',
0xF1B4A71AD29E3398068BCF8AF0580834CF18DD126922899239F037964D6FB7E40A587BEBD6768B5A1BE2890DAF4D5C57382CF901C22003FDA697FFB78217B335,
65536,
0xD7862CF028FC9FC5A706F14C47DE6A54,
1);

