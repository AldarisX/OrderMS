ALTER TABLE item_type
  ADD COLUMN iorder SMALLINT;
ALTER TABLE manufactor
  ADD COLUMN iorder SMALLINT;
ALTER TABLE logistics
  ADD COLUMN iorder SMALLINT;

UPDATE site_config
SET "value" = '0.0.3';