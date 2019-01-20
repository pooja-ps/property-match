CREATE SCHEMA IF NOT EXISTS `radius`;

CREATE TABLE IF NOT EXISTS `radius`.`property` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique id',
  `type` int(11) NOT NULL COMMENT 'Type of the Property: 0 - Rent, 1 - Sale',
  `latitude` double NOT NULL COMMENT 'Latitude of the property',
  `longitude` double NOT NULL COMMENT 'Longitude of the property',
  `no_of_bedrooms` int(11) DEFAULT NULL COMMENT 'No. of bed rooms in the property',
  `no_of_bathrooms` int(11) DEFAULT NULL COMMENT 'No. of bathrooms in the property',
  `price` int(11) NOT NULL COMMENT 'Price of the property',
  `created_at` datetime DEFAULT NULL COMMENT 'Record created by',
  `created_by` varchar(45) DEFAULT 'admin',
  `updated_at` datetime DEFAULT NULL COMMENT 'Record updated at',
  `updated_by` varchar(45) DEFAULT NULL COMMENT 'Record updated by',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='This table provides the properties posted by agents';

CREATE TABLE IF NOT EXISTS `radius`.`search_requirement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Unique id',
  `type` int(11) NOT NULL COMMENT 'Type of the Property: 0 - Rent, 1 - Sale',
  `latitude` double DEFAULT NULL COMMENT 'Location latitude around which property required',
  `longitude` double DEFAULT NULL COMMENT 'Location longitude around which property required',
  `min_bedrooms` int(11) DEFAULT NULL COMMENT 'Minimum bedrooms required',
  `max_bedrooms` int(11) DEFAULT NULL COMMENT 'Maximum bedrooms required',
  `min_bathrooms` int(11) DEFAULT NULL COMMENT 'Minimum bathrooms required',
  `max_bathrooms` int(11) DEFAULT NULL COMMENT 'Maximum bathrooms required',
  `min_budget` int(11) DEFAULT NULL COMMENT 'Minimum budget ',
  `max_budget` int(11) DEFAULT NULL COMMENT 'Maximum budget',
  `created_at` datetime DEFAULT NULL COMMENT 'Timestamp when the requirement was posted',
  `created_by` varchar(45) DEFAULT NULL COMMENT 'Requirement posted by ',
  `updated_at` datetime DEFAULT NULL COMMENT 'Timestamp when the requirement was updated',
  `updated_by` varchar(45) DEFAULT NULL COMMENT 'Requirement updated by ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='This table provides the search requirements of properties provided by agents';
