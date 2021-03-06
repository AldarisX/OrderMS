CREATE FUNCTION unix_timestamp()
  RETURNS INTEGER AS $$
SELECT (date_part('epoch', now())) :: INTEGER;
$$
LANGUAGE SQL
IMMUTABLE;

CREATE FUNCTION from_unixtime(INT)
  RETURNS TIMESTAMP AS $$
SELECT to_timestamp($1) :: TIMESTAMP;
$$
LANGUAGE SQL
IMMUTABLE;

--库存
CREATE TABLE item_list
(
  id         SERIAL         NOT NULL
    CONSTRAINT item_list_pk
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

--库存索引
CREATE INDEX item_list_index
  ON item_list (ins_date DESC);

--库存统计
CREATE TABLE item_list_statis
(
  id         SERIAL         NOT NULL
    CONSTRAINT item_list_statis_pk
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

--出库
CREATE TABLE item_out
(
  id        SERIAL         NOT NULL
    CONSTRAINT item_out_pk
    PRIMARY KEY,
  order_id  INTEGER        NOT NULL DEFAULT -1,
  item_list JSONB,
  count     INTEGER        NOT NULL,
  price     NUMERIC(32, 5) NOT NULL,
  "desc"    TEXT,
  is_alive  SMALLINT                DEFAULT 1,
  ins_date  INTEGER,
  up_date   INTEGER,
  del_date  INTEGER
);

--出库统计
CREATE TABLE item_out_statis
(
  id               SERIAL         NOT NULL
    CONSTRAINT item_out_statis_pk
    PRIMARY KEY,
  item_list_statis INTEGER        NOT NULL,
  count            INTEGER        NOT NULL,
  price            NUMERIC(32, 5) NOT NULL,
  ins_date         INTEGER,
  up_date          INTEGER,
  del_date         INTEGER
);

--物品类型
CREATE TABLE item_type
(
  id       SERIAL       NOT NULL
    CONSTRAINT item_type_pk
    PRIMARY KEY,
  name     VARCHAR(128) NOT NULL,
  ex_data  JSONB,
  iorder   SMALLINT,
  is_alive SMALLINT DEFAULT 1,
  ins_date INTEGER,
  del_date INTEGER,
  up_date  INTEGER,
  in_index SMALLINT DEFAULT 1
);

--厂商
CREATE TABLE manufactor
(
  id        SERIAL       NOT NULL
    CONSTRAINT manufactor_pk
    PRIMARY KEY,
  name      VARCHAR(128) NOT NULL,
  item_type INTEGER      NOT NULL,
  iorder    SMALLINT,
  is_alive  SMALLINT DEFAULT 1,
  ins_date  INTEGER,
  del_date  INTEGER,
  up_date   INTEGER
);

--订单
CREATE TABLE order_list
(
  id               SERIAL             NOT NULL
    CONSTRAINT order_list_pk
    PRIMARY KEY,
  order_num        TEXT               NOT NULL,
  user_name        TEXT               NOT NULL,
  userww           TEXT,
  tel              TEXT,
  phone            TEXT               NOT NULL,
  address          TEXT               NOT NULL,
  item_statis_list JSONB              NOT NULL,
  logistics_type   INTEGER            NOT NULL,
  logistics        INTEGER            NOT NULL,
  logistics_num    TEXT               NOT NULL,
  trans_cost       DOUBLE PRECISION   NOT NULL,
  "desc"           TEXT,
  order_state      INTEGER,
  is_alive         SMALLINT DEFAULT 1 NOT NULL,
  ins_date         INTEGER,
  up_date          INTEGER,
  del_date         INTEGER
);

--物流
CREATE TABLE logistics
(
  id       SERIAL             NOT NULL
    CONSTRAINT logistics_pk
    PRIMARY KEY,
  name     TEXT               NOT NULL,
  iorder   SMALLINT,
  is_alive SMALLINT DEFAULT 1 NOT NULL,
  ins_date INTEGER            NOT NULL,
  del_date INTEGER,
  up_date  INTEGER
);

--网站设置
CREATE TABLE site_config
(
  id    SERIAL NOT NULL
    CONSTRAINT site_config_pk
    PRIMARY KEY,
  name  TEXT   NOT NULL,
  value TEXT   NOT NULL
);

--user
CREATE TABLE admin_role
(
  id     SERIAL NOT NULL
    CONSTRAINT admin_role_pk
    PRIMARY KEY,
  name   TEXT,
  "desc" TEXT
);

CREATE TABLE admin_user
(
  id       SERIAL NOT NULL
    CONSTRAINT admin_user_pk
    PRIMARY KEY,
  username TEXT,
  password TEXT
);

CREATE TABLE admin_user_roles
(
  admin_user_id SERIAL NOT NULL
    CONSTRAINT admin_user_fk
    REFERENCES admin_user,
  roles_id      BIGINT NOT NULL
    CONSTRAINT admin_role_fk
    REFERENCES admin_role
);

-- session
CREATE UNLOGGED TABLE IF NOT EXISTS spring_session
(
  session_id            CHAR(36) NOT NULL
    CONSTRAINT spring_session_pk
    PRIMARY KEY,
  creation_time         BIGINT   NOT NULL,
  last_access_time      BIGINT   NOT NULL,
  max_inactive_interval INTEGER  NOT NULL,
  principal_name        VARCHAR(100)
);

CREATE INDEX IF NOT EXISTS spring_session_ix1
  ON spring_session (last_access_time);

CREATE INDEX IF NOT EXISTS spring_session_ix2
  ON spring_session (principal_name);

CREATE UNLOGGED TABLE IF NOT EXISTS spring_session_attributes
(
  session_id      CHAR(36)     NOT NULL
    CONSTRAINT spring_session_attributes_fk
    REFERENCES spring_session
    ON DELETE CASCADE,
  attribute_name  VARCHAR(200) NOT NULL,
  attribute_bytes BYTEA        NOT NULL,
  CONSTRAINT spring_session_attributes_pk
  PRIMARY KEY (session_id, attribute_name)
);

--rsa公钥 密钥表
CREATE UNLOGGED TABLE IF NOT EXISTS rsa_list
(
  id      SERIAL NOT NULL
    CONSTRAINT rsa_list_pk
    PRIMARY KEY,
  pub_key BYTEA  NOT NULL,
  pri_key BYTEA  NOT NULL
);

-- 初始admin用户 密码123456
INSERT INTO admin_role ("name", "desc") VALUES ('ROLE_ADMIN', '管理员');
INSERT INTO admin_role ("name", "desc") VALUES ('ROLE_USER', '普通员工');
INSERT INTO admin_user (username, password) VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO admin_user_roles VALUES (1, 1);