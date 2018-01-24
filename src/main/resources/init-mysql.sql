CREATE DATABASE IF NOT EXISTS `orderms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `orderms`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: orderms
-- ------------------------------------------------------
-- Server version	5.5.5-10.2.12-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `item_list`
--

DROP TABLE IF EXISTS `item_list`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_list` (
  `id`         INT(11)        NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(65)    NOT NULL,
  `itemType`   INT(11)        NOT NULL,
  `manufactor` INT(11)        NOT NULL,
  `model`      VARCHAR(64)             DEFAULT NULL,
  `count`      INT(11)        NOT NULL,
  `remain`     INT(11)                 DEFAULT NULL,
  `price`      DECIMAL(32, 5) NOT NULL,
  `exData`     LONGTEXT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin                  DEFAULT NULL,
  `desc`       TEXT                    DEFAULT NULL,
  `isAlive`    TINYINT(4)              DEFAULT 1,
  `insDate`    INT(11)                 DEFAULT NULL,
  `delDate`    INT(11)                 DEFAULT NULL,
  `upDate`     INT(11)                 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_index` (`itemType`, `manufactor`, `name`, `upDate`, `insDate`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100002
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_list_statistics`
--

DROP TABLE IF EXISTS `item_list_statistics`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_list_statistics` (
  `id`         INT(11)        NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(65)    NOT NULL,
  `itemType`   INT(11)        NOT NULL,
  `manufactor` INT(11)        NOT NULL,
  `model`      VARCHAR(65)             DEFAULT NULL,
  `count`      INT(11)        NOT NULL,
  `price`      DECIMAL(32, 5) NOT NULL,
  `exData`     LONGTEXT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin                  DEFAULT NULL,
  `upDate`     INT(11)                 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_index` (`itemType`, `manufactor`, `name`, `upDate`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2442
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_out`
--

DROP TABLE IF EXISTS `item_out`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_out` (
  `id`        INT(11)        NOT NULL AUTO_INCREMENT,
  `item_list` INT(11)        NOT NULL,
  `count`     INT(11)        NOT NULL,
  `price`     DECIMAL(32, 5) NOT NULL,
  `desc`      TEXT                    DEFAULT NULL,
  `isAlive`   TINYINT(4)              DEFAULT 1,
  `insDate`   INT(11)                 DEFAULT NULL,
  `upDate`    INT(11)                 DEFAULT NULL,
  `delDate`   INT(11)                 DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type`
--

DROP TABLE IF EXISTS `item_type`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_type` (
  `id`      INT(11)    NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(45)         DEFAULT NULL,
  `exData`  LONGTEXT CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin           DEFAULT NULL,
  `isAlive` TINYINT(4) NOT NULL DEFAULT 1,
  `insDate` INT(11)             DEFAULT NULL,
  `delDate` INT(11)             DEFAULT NULL,
  `upDate`  INT(11)             DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manufactor`
--

DROP TABLE IF EXISTS `manufactor`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manufactor` (
  `id`       INT(11) NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(45)      DEFAULT NULL,
  `itemType` INT(11)          DEFAULT -1,
  `isAlive`  TINYINT(4)       DEFAULT 1,
  `insDate`  INT(11)          DEFAULT NULL,
  `delDate`  INT(11)          DEFAULT NULL,
  `upDate`   INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2018-01-24 13:04:43
