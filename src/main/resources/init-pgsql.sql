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
  up_date  INTEGER
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

CREATE TABLE tb_user
(
  id         SERIAL            NOT NULL
    CONSTRAINT tb_user_pkey
    PRIMARY KEY,
  uname      VARCHAR(128)      NOT NULL,
  passwd     VARCHAR(128)      NOT NULL,
  last_login INTEGER  DEFAULT 0,
  level      INTEGER DEFAULT 6 NOT NULL,
  is_alive   SMALLINT DEFAULT 1,
  ins_date   INTEGER,
  up_date    INTEGER,
  del_date   INTEGER
);

CREATE FUNCTION unix_timestamp()
  RETURNS INTEGER
IMMUTABLE
LANGUAGE SQL
AS $$
SELECT (date_part('epoch', now())) :: INTEGER;
$$;

CREATE FUNCTION from_unixtime(INTEGER)
  RETURNS TIMESTAMP WITHOUT TIME ZONE
IMMUTABLE
LANGUAGE SQL
AS $$
SELECT to_timestamp($1) :: TIMESTAMP;
$$;

INSERT INTO tb_user (uname, passwd, level, ins_date)
VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', 0, unix_timestamp());