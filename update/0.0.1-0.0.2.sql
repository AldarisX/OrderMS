--rsa公钥 密钥表
CREATE UNLOGGED TABLE IF NOT EXISTS rsa_list
(
  id      SERIAL NOT NULL
    CONSTRAINT rsa_list_pk
    PRIMARY KEY,
  pub_key BYTEA  NOT NULL,
  pri_key BYTEA  NOT NULL
);

UPDATE site_config
SET "value" = '0.0.2';