DELETE FROM tbl_user;
DELETE FROM tbl_user_base;
DELETE FROM tbl_password;
DELETE FROM tbl_role;


INSERT INTO tbl_role (id, role_name) VALUES ('21232f297a57a5a743894a0e4a801fc3', 'ROLE_ADMIN');
INSERT INTO tbl_role (id, role_name) VALUES ('cd73502828457d15655bbd7a63fb0bc8', 'ROLE_USER');

INSERT INTO tbl_user_base (id, role_id, login, phoneNumber, secretDigit)
VALUES ('e805000334c9ac870f2c8c702911697d', '21232f297a57a5a743894a0e4a801fc3', 'bendus', '+380979098969',
        '4e732ced3463d06de0ca9a15b6153677');
INSERT INTO tbl_user_base (id, role_id, login, phoneNumber, secretDigit)
VALUES ('945b50cc6d123f5c8634557ff4380c48', 'cd73502828457d15655bbd7a63fb0bc8', 'deputat', '+380958479756',
        '1679091c5a880faf6fb5e6087eb1b2dc');

INSERT INTO tbl_password (id, password) VALUES ('1ee197b99dcc933aa6fe2ca0bf9a1456', '5650C72C0CE6FA687759EBDC8A8BA530');
INSERT INTO tbl_password (id, password) VALUES ('3a5b96eec1c0f212c4fe8d05e18dd3db', '547E177201F1E8979F3B6B8C80689BBB');

INSERT INTO tbl_user (id, user_base_id, password_id)
VALUES ('c350ad372dfd08b5ca997401e55bb076', 'e805000334c9ac870f2c8c702911697d', '1ee197b99dcc933aa6fe2ca0bf9a1456');
INSERT INTO tbl_user (id, user_base_id, password_id)
VALUES ('36149a4fed1c2eedb7f7a50189b06e69', '945b50cc6d123f5c8634557ff4380c48', '3a5b96eec1c0f212c4fe8d05e18dd3db');