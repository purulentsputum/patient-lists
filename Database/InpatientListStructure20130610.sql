CREATE DATABASE  IF NOT EXISTS `inpatient_list` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `inpatient_list`;
-- MySQL dump 10.13  Distrib 5.6.10, for Win64 (x86_64)
--
-- Host: localhost    Database: inpatient_list
-- ------------------------------------------------------
-- Server version	5.6.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_001_patients`
--

DROP TABLE IF EXISTS `tbl_001_patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_001_patients` (
  `URN` varchar(10) NOT NULL,
  `DateOfBirth` datetime DEFAULT NULL,
  `Gender` varchar(2) DEFAULT NULL,
  `GivenNames` varchar(45) DEFAULT NULL,
  `Postcode` varchar(5) DEFAULT NULL,
  `Surname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`URN`),
  UNIQUE KEY `URN_UNIQUE` (`URN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_002_admissions`
--

DROP TABLE IF EXISTS `tbl_002_admissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_002_admissions` (
  `AdmDate` datetime DEFAULT NULL,
  `AdmNo` varchar(45) NOT NULL DEFAULT '',
  `AdmTime` varchar(10) DEFAULT NULL,
  `AdmType` varchar(45) DEFAULT NULL,
  `AdmUnit` varchar(45) DEFAULT NULL,
  `AdmWard` varchar(45) DEFAULT NULL,
  `Bed` varchar(45) DEFAULT NULL,
  `BedDays` int(11) DEFAULT NULL,
  `BedHours` int(11) DEFAULT NULL,
  `DateLastMoved` datetime DEFAULT NULL,
  `Destination` varchar(45) DEFAULT NULL,
  `DischargeDate` datetime DEFAULT NULL,
  `DischargeStatus` varchar(45) DEFAULT NULL,
  `DischargeTime` varchar(45) DEFAULT NULL,
  `DischargeUnit` varchar(45) DEFAULT NULL,
  `DischargeWard` varchar(45) DEFAULT NULL,
  `DrAdmitting` varchar(45) DEFAULT NULL,
  `DrReferringAddress` varchar(255) DEFAULT NULL,
  `DrReferringName` varchar(45) DEFAULT NULL,
  `DrReferringPostCode` varchar(45) DEFAULT NULL,
  `DrReferringSuburb` varchar(45) DEFAULT NULL,
  `DrTreating` varchar(45) DEFAULT NULL,
  `Episodes` int(11) DEFAULT NULL,
  `URN` varchar(45) DEFAULT NULL,
  `Issues` varchar(255) DEFAULT NULL,
  `Plan` varchar(255) DEFAULT NULL,
  `Results` varchar(255) DEFAULT NULL,
  `Updated` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`AdmNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_003_unitlist`
--

DROP TABLE IF EXISTS `tbl_003_unitlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_003_unitlist` (
  `Unit` varchar(10) NOT NULL,
  PRIMARY KEY (`Unit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_004_wardlist`
--

DROP TABLE IF EXISTS `tbl_004_wardlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_004_wardlist` (
  `Ward` varchar(10) NOT NULL,
  PRIMARY KEY (`Ward`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_005_doctorlist`
--

DROP TABLE IF EXISTS `tbl_005_doctorlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_005_doctorlist` (
  `DrCode` varchar(10) NOT NULL,
  `Title` varchar(45) DEFAULT NULL,
  `GivenNames` varchar(45) DEFAULT NULL,
  `Surname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DrCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_010_admissiontypes`
--

DROP TABLE IF EXISTS `tbl_010_admissiontypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_010_admissiontypes` (
  `AdmType` varchar(10) NOT NULL,
  `AdmDesc` varchar(45) DEFAULT NULL,
  `Active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`AdmType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_100_notes`
--

DROP TABLE IF EXISTS `tbl_100_notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_100_notes` (
  `Key` char(30) NOT NULL,
  `URN` varchar(10) DEFAULT NULL,
  `CreatedBy` varchar(15) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  `DateEdited` datetime DEFAULT NULL,
  `EditedBy` varchar(15) DEFAULT NULL,
  `Note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Key`),
  UNIQUE KEY `Key_UNIQUE` (`Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_110_tasks`
--

DROP TABLE IF EXISTS `tbl_110_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_110_tasks` (
  `Key` char(30) NOT NULL,
  `TaskDate` date DEFAULT NULL,
  `URN` varchar(45) DEFAULT NULL,
  `TaskDesc` varchar(255) DEFAULT NULL,
  `UIDCreatedBy` varchar(15) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  `UIDEditedBy` varchar(15) DEFAULT NULL,
  `DateEdited` datetime DEFAULT NULL,
  `UIDCompletedBy` varchar(15) DEFAULT NULL,
  `DateCompleted` datetime DEFAULT NULL,
  `Completed` tinyint(1) DEFAULT NULL,
  `TaskType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_900_users`
--

DROP TABLE IF EXISTS `tbl_900_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_900_users` (
  `UID` varchar(15) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Admin` tinyint(1) DEFAULT '0',
  `DefaultWard` varchar(45) DEFAULT NULL,
  `DefaultUnit` varchar(45) DEFAULT NULL,
  `DefaultCons` varchar(45) DEFAULT NULL,
  `Locked` tinyint(1) DEFAULT '1',
  `Active` tinyint(1) DEFAULT '0',
  `Created` datetime DEFAULT NULL,
  `LastValidLogin` datetime DEFAULT NULL,
  `LastInvalidLogin` datetime DEFAULT NULL,
  `ValidLogins` int(11) DEFAULT NULL,
  `InvalidLogins` int(11) DEFAULT NULL,
  `Limited` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`UID`),
  UNIQUE KEY `UID_UNIQUE` (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-06-10 23:03:24
