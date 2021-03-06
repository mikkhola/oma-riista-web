DROP TABLE IF EXISTS import_harvest_report_fields;

CREATE TABLE import_harvest_report_fields (
  official_code            INTEGER NOT NULL,
  "name"                   VARCHAR(255) NOT NULL,
  used_with_permit         BOOLEAN      NOT NULL,
  hunting_area_type        VARCHAR(255) NOT NULL,
  hunting_party            VARCHAR(255) NOT NULL,
  hunting_area_size        VARCHAR(255) NOT NULL,
  permit_number            VARCHAR(255) NOT NULL,
  hunting_method           VARCHAR(255) NOT NULL,
  weight                   VARCHAR(255) NOT NULL,
  reported_with_phone_call VARCHAR(255) NOT NULL,
  free_hunting_also        BOOLEAN      NOT NULL,
  age                      VARCHAR(255) NOT NULL,
  gender                   VARCHAR(255) NOT NULL,
  additional_info          VARCHAR(255) NOT NULL,
  weight_estimated         VARCHAR(255) NOT NULL,
  weight_measured          VARCHAR(255) NOT NULL,
  fitness_class            VARCHAR(255) NOT NULL,
  antlers_type             VARCHAR(255) NOT NULL,
  antlers_width            VARCHAR(255) NOT NULL,
  antler_points_left       VARCHAR(255) NOT NULL,
  antler_points_right      VARCHAR(255) NOT NULL,
  harvests_as_list         BOOLEAN      NOT NULL,
  not_edible               VARCHAR(255) NOT NULL,
  PRIMARY KEY (official_code, name, reported_with_phone_call)
);

\COPY import_harvest_report_fields FROM './csv/harvest_report_fields.csv' WITH CSV DELIMITER ';' NULL '' ENCODING 'UTF-8';

INSERT INTO harvest_report_fields (
  game_species_id,
  name,
  used_with_permit,
  hunting_area_type,
  hunting_party,
  hunting_area_size,
  permit_number,
  hunting_method,
  weight,
  reported_with_phone_call,
  free_hunting_also,
  age,
  gender,
  additional_info,
  weight_estimated,
  weight_measured,
  fitness_class,
  antlers_type,
  antlers_width,
  antler_points_left,
  antler_points_right,
  harvests_as_list,
  not_edible)
  SELECT
    g.game_species_id,
    f.name,
    f.used_with_permit,
    f.hunting_area_type,
    f.hunting_party,
    f.hunting_area_size,
    f.permit_number,
    f.hunting_method,
    f.weight,
    f.reported_with_phone_call,
    f.free_hunting_also,
    f.age,
    f.gender,
    f.additional_info,
    f.weight_estimated,
    f.weight_measured,
    f.fitness_class,
    f.antlers_type,
    f.antlers_width,
    f.antler_points_left,
    f.antler_points_right,
    f.harvests_as_list,
    f.not_edible
  FROM import_harvest_report_fields f
    JOIN game_species g ON (g.official_code = f.official_code);
