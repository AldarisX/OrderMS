ALTER TABLE "item_out"
  DROP COLUMN "count";
ALTER TABLE "item_out"
  DROP COLUMN "price";

ALTER TABLE "item_out_statis"
  DROP COLUMN "is_alive";

UPDATE site_config
SET "value" = '0.0.4';