CREATE TABLE tbl_role (
  id        VARCHAR(80) NOT NULL PRIMARY KEY,
  role_name VARCHAR(80) NOT NULL UNIQUE,
  created   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified  TIMESTAMP   NULL,
  deleted   TIMESTAMP   NULL
);

CREATE TABLE tbl_password (
  id       VARCHAR(80) NOT NULL PRIMARY KEY,
  password VARCHAR(80) NOT NULL,
  created  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP   NULL,
  deleted  TIMESTAMP   NULL
);

CREATE TABLE tbl_user_base (
  id           VARCHAR(80) NOT NULL PRIMARY KEY,
  role_id      VARCHAR(80) NOT NULL,
  login        VARCHAR(80) NOT NULL UNIQUE,
  phone_number VARCHAR(80) NULL,
  secret_digit VARCHAR(80) NULL,
  sms_code     VARCHAR(80) NULL,
  created      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified     TIMESTAMP   NULL,
  deleted      TIMESTAMP   NULL
);

CREATE TABLE tbl_user (
  id           VARCHAR(80) NOT NULL PRIMARY KEY,
  user_base_id VARCHAR(80) NOT NULL,
  password_id  VARCHAR(80) NOT NULL,
  created      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified     TIMESTAMP   NULL,
  deleted      TIMESTAMP   NULL
);

CREATE TABLE tbl_random_number (
  id       VARCHAR(80) NOT NULL PRIMARY KEY,
  number   INTEGER     NOT NULL,
  created  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP   NULL,
  deleted  TIMESTAMP   NULL
);

ALTER TABLE tbl_user_base
  ADD CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES tbl_role (id);

ALTER TABLE tbl_user
  ADD CONSTRAINT fk_user_base FOREIGN KEY (user_base_id) REFERENCES tbl_user_base (id);

ALTER TABLE tbl_user
  ADD CONSTRAINT fk_password FOREIGN KEY (password_id) REFERENCES tbl_password (id);