CREATE DATABASE IF NOT EXISTS ` orderms ` /*!40100 DEFAULT CHARACTER SET utf8 */;
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

DROP TABLE IF EXISTS ` item_list `;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ` item_list` (
  `id` INT (11
) NOT NULL AUTO_INCREMENT
COMMENT '入库ID',
  ` NAME ` VARCHAR (65
) NOT NULL
COMMENT '型号',
  `itemType` INT (11
) NOT NULL
COMMENT '物品类型ID(逻辑映射)',
  `manufactor` INT (11
) NOT NULL
COMMENT '厂商ID(逻辑映射)',
  `model` VARCHAR (64
) DEFAULT NULL
COMMENT '名称(留着备用)',
  ` COUNT ` INT (11
) NOT NULL
COMMENT '入库数量',
  `remain` INT (11
) DEFAULT NULL
COMMENT '剩余',
  `price` DECIMAL (32, 5
) NOT NULL
COMMENT '进价',
  `exData`     LONGTEXT CHARACTER SET utf8mb4
COLLATE utf8mb4_bin DEFAULT NULL
COMMENT '附加数据(JSON)',
  ` DESC ` TEXT DEFAULT NULL
COMMENT '备注',
  `isAlive`    TINYINT (4
) DEFAULT 1
COMMENT '是否删除',
  `insDate` INT (11
) DEFAULT NULL
COMMENT '插入时间',
  `delDate` INT (11
) DEFAULT NULL
COMMENT '删除时间',
  ` UPDATE ` INT (11
) DEFAULT NULL
COMMENT '更新时间',
PRIMARY KEY (`id`
),
KEY `t_index` (`itemType`, `manufactor`, ` NAME `, ` UPDATE `, `insDate`
)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100001
DEFAULT CHARSET = UTF8
COMMENT ='入库记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_list_statis`
--

DROP TABLE IF EXISTS ` item_list_statis `;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ` item_list_statis` (
  `id` INT (11
) NOT NULL AUTO_INCREMENT,
  ` NAME ` VARCHAR (65
) NOT NULL,
  `itemType` INT (11
) NOT NULL,
  `manufactor` INT (11
) NOT NULL,
  `model` VARCHAR (65
) DEFAULT NULL,
  ` COUNT ` INT (11
) NOT NULL,
  `price` DECIMAL (32, 5
) NOT NULL,
  `exData`     LONGTEXT CHARACTER SET utf8mb4
COLLATE utf8mb4_bin DEFAULT NULL,
  ` UPDATE ` INT (11
) DEFAULT NULL,
PRIMARY KEY (`id`
),
KEY `t_index` (`itemType`, `manufactor`, ` NAME `, ` UPDATE `
)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2441
DEFAULT CHARSET = UTF8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_out`
--

DROP TABLE IF EXISTS ` item_out `;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ` item_out` (
  `id` INT (11
) NOT NULL AUTO_INCREMENT,
  `item_list_statis` INT (11
) NOT NULL,
  `item_list`        LONGTEXT CHARACTER SET utf8mb4
COLLATE utf8mb4_bin NOT NULL
COMMENT '映射到BaseItem.exData',
  ` COUNT ` INT (11
) NOT NULL,
  `price` DECIMAL (32, 5
) NOT NULL,
  ` DESC ` TEXT DEFAULT NULL,
  `isAlive`          TINYINT (4
) DEFAULT 1,
  `insDate` INT (11
) DEFAULT NULL,
  ` UPDATE ` INT (11
) DEFAULT NULL,
  `delDate` INT (11
) DEFAULT NULL,
PRIMARY KEY (`id`
)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
DEFAULT CHARSET = UTF8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_out_statis`
--

DROP TABLE IF EXISTS ` item_out_statis `;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ` item_out_statis` (
  `id` INT (11
) NOT NULL AUTO_INCREMENT,
  `item_list_statis` INT (11
) NOT NULL,
  ` COUNT ` INT (11
) NOT NULL,
  `price` DECIMAL (32, 5
) NOT NULL,
  `alive`            TINYINT (4
) DEFAULT 1,
  `insDate` INT (11
) DEFAULT NULL,
  ` UPDATE ` INT (11
) DEFAULT NULL,
  `delDate` INT (11
) DEFAULT NULL,
PRIMARY KEY (`id`
)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
DEFAULT CHARSET = UTF8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_type`
--

DROP TABLE IF EXISTS ` item_type `;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ` item_type` (
  `id` INT (11
) NOT NULL AUTO_INCREMENT
COMMENT '物品类型ID',
  ` NAME ` VARCHAR (45
) DEFAULT NULL
COMMENT '物品类型名',
  `exData`  LONGTEXT CHARACTER SET utf8mb4
COLLATE utf8mb4_bin DEFAULT NULL
COMMENT '附加数据(JSON)',
  `isAlive` TINYINT (4
) NOT NULL DEFAULT 1
COMMENT '时候删除',
  `insDate` INT (11
) DEFAULT NULL
COMMENT '插入时间',
  `delDate` INT (11
) DEFAULT NULL
COMMENT '删除时间',
  ` UPDATE ` INT (11
) DEFAULT NULL
COMMENT '更新时间',
PRIMARY KEY (`id`
)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
DEFAULT CHARSET = UTF8
COMMENT ='物品类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manufactor`
--

DROP TABLE IF EXISTS ` manufactor `;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE ` manufactor` (
  `id` INT (11
) NOT NULL AUTO_INCREMENT
COMMENT '厂商ID',
  ` NAME ` VARCHAR (45
) DEFAULT NULL
COMMENT '厂商名',
  `itemType` INT (11
) DEFAULT -1
COMMENT '物品类型ID(逻辑映射)',
  `isAlive`  TINYINT (4
) DEFAULT 1
COMMENT '是否删除',
  `insDate` INT (11
) DEFAULT NULL
COMMENT '插入时间',
  `delDate` INT (11
) DEFAULT NULL
COMMENT '删除时间',
  ` UPDATE ` INT (11
) DEFAULT NULL
COMMENT '更新时间',
PRIMARY KEY (`id`
)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 12
DEFAULT CHARSET = UTF8
COMMENT ='厂商表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2018-01-29 13:35:05
