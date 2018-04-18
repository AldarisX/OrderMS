ALTER TABLE "item_out"
  DROP COLUMN "count";

ALTER TABLE "item_out"
  DROP COLUMN "price";

ALTER TABLE "item_out_statis"
  DROP COLUMN "is_alive";

CREATE INDEX item_out_static_id
  ON item_out
  USING GIN (item_list);

UPDATE site_config
SET "value" = '0.0.4';