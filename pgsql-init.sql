CREATE TABLE item_list
(
  id         SERIAL         NOT NULL
    CONSTRAINT item_list_pkey
    PRIMARY KEY,
  name       TEXT           NOT NULL,
  item_type  INTEGER        NOT NULL,
  manufactor INTEGER        NOT NULL,
  model      TEXT,
  count      INTEGER        NOT NULL,
  remain     INTEGER        NOT NULL,
  price      NUMERIC(32, 5) NOT NULL,
  ex_data    JSONB,
  "desc"     TEXT,
  is_alive   SMALLINT DEFAULT 1,
  ins_date   INTEGER,
  del_date   INTEGER,
  up_date    INTEGER
);

CREATE INDEX item_list_index
  ON item_list (ins_date DESC);

CREATE TABLE item_list_statis
(
  id         SERIAL         NOT NULL
    CONSTRAINT item_list_statis_pkey
    PRIMARY KEY,
  name       TEXT           NOT NULL,
  item_type  INTEGER        NOT NULL,
  manufactor INTEGER        NOT NULL,
  model      TEXT,
  count      INTEGER        NOT NULL,
  price      NUMERIC(32, 5) NOT NULL,
  ex_data    JSONB,
  up_date    INTEGER
);

CREATE TABLE item_out
(
  id               SERIAL         NOT NULL
    CONSTRAINT item_out_pkey
    PRIMARY KEY,
  item_list_statis INTEGER        NOT NULL,
  item_list        JSONB,
  count            INTEGER        NOT NULL,
  price            NUMERIC(32, 5) NOT NULL,
  "desc"           TEXT,
  is_alive         SMALLINT DEFAULT 1,
  ins_date         INTEGER,
  up_date          INTEGER,
  del_date         INTEGER
);

CREATE TABLE item_out_statis
(
  id               SERIAL         NOT NULL
    CONSTRAINT item_out_statis_pkey
    PRIMARY KEY,
  item_list_statis INTEGER        NOT NULL,
  count            INTEGER        NOT NULL,
  price            NUMERIC(32, 5) NOT NULL,
  is_alive         SMALLINT DEFAULT 1,
  ins_date         INTEGER,
  up_date          INTEGER,
  del_date         INTEGER
);

CREATE TABLE item_type
(
  id       SERIAL       NOT NULL
    CONSTRAINT item_type_pkey
    PRIMARY KEY,
  name     VARCHAR(128) NOT NULL,
  ex_data  JSONB,
  is_alive SMALLINT DEFAULT 1,
  ins_date INTEGER,
  del_date INTEGER,
  up_date  INTEGER,
  in_index SMALLINT DEFAULT 1
);

CREATE TABLE manufactor
(
  id        SERIAL       NOT NULL
    CONSTRAINT manufactor_pkey
    PRIMARY KEY,
  name      VARCHAR(128) NOT NULL,
  item_type INTEGER      NOT NULL,
  is_alive  SMALLINT DEFAULT 1,
  ins_date  INTEGER,
  del_date  INTEGER,
  up_date   INTEGER
);

CREATE TABLE order_list
(
  id             SERIAL             NOT NULL
    CONSTRAINT order_list_pkey
    PRIMARY KEY,
  user_name      TEXT               NOT NULL,
  userww         TEXT,
  tel            TEXT,
  phone          TEXT               NOT NULL,
  address        TEXT               NOT NULL,
  item_list      JSONB              NOT NULL,
  logistics_type INTEGER            NOT NULL,
  logistics      INTEGER            NOT NULL,
  logistics_num  INTEGER            NOT NULL,
  trans_cost     DOUBLE PRECISION   NOT NULL,
  "desc"         TEXT,
  order_state    TEXT,
  is_alive       SMALLINT DEFAULT 1 NOT NULL,
  ins_date       INTEGER,
  up_date        INTEGER,
  del_date       INTEGER
);

CREATE TABLE admin_role
(
  id   SERIAL NOT NULL
    CONSTRAINT admin_role_pkey
    PRIMARY KEY,
  name TEXT
);

CREATE TABLE admin_user
(
  id       SERIAL NOT NULL
    CONSTRAINT admin_user_pkey
    PRIMARY KEY,
  username TEXT,
  password TEXT
);

CREATE TABLE admin_user_roles
(
  admin_user_id SERIAL NOT NULL
    CONSTRAINT fkm49lam8hlsew02rh5vjob0kik
    REFERENCES admin_user,
  roles_id      BIGINT NOT NULL
    CONSTRAINT fk34r04g3hvb6bh89nh93db9qi8
    REFERENCES admin_role
);

INSERT INTO admin_role (name) VALUES ('ROLE_ADMIN');
INSERT INTO admin_role (name) VALUES ('ROLE_USER');
INSERT INTO admin_user (username, password) VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO admin_user_roles VALUES (1, 1);