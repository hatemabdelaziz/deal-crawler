ALTER TABLE `deals` CHANGE `end` `end` BIGINT( 20 ) NULL DEFAULT NULL;

ALTER TABLE `deals` CHANGE `title` `simpleTitle` VARCHAR( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;

ALTER TABLE `deals` CHANGE `description` `simpleDescription` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;