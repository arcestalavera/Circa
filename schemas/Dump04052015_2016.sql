CREATE DATABASE  IF NOT EXISTS `circa` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `circa`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: circa
-- ------------------------------------------------------
-- Server version	5.6.19

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
-- Table structure for table `add_user_to_cluster`
--

DROP TABLE IF EXISTS `add_user_to_cluster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `add_user_to_cluster` (
  `adderID` int(11) NOT NULL,
  `addedID` int(11) NOT NULL,
  `clusterID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `add_user_to_cluster`
--

LOCK TABLES `add_user_to_cluster` WRITE;
/*!40000 ALTER TABLE `add_user_to_cluster` DISABLE KEYS */;
INSERT INTO `add_user_to_cluster` VALUES (1,2,2),(1,3,1),(1,3,1),(1,3,1),(1,3,1),(1,3,1);
/*!40000 ALTER TABLE `add_user_to_cluster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attending_an_event`
--

DROP TABLE IF EXISTS `attending_an_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attending_an_event` (
  `eventID` int(11) NOT NULL,
  `attendingID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attending_an_event`
--

LOCK TABLES `attending_an_event` WRITE;
/*!40000 ALTER TABLE `attending_an_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `attending_an_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buddy`
--

DROP TABLE IF EXISTS `buddy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buddy` (
  `friend_1` int(11) NOT NULL,
  `friend_2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buddy`
--

LOCK TABLES `buddy` WRITE;
/*!40000 ALTER TABLE `buddy` DISABLE KEYS */;
INSERT INTO `buddy` VALUES (1,2),(3,1);
/*!40000 ALTER TABLE `buddy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cluster`
--

DROP TABLE IF EXISTS `cluster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cluster` (
  `clusterID` int(11) NOT NULL AUTO_INCREMENT,
  `creatorID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `isDeleted` binary(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`clusterID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cluster`
--

LOCK TABLES `cluster` WRITE;
/*!40000 ALTER TABLE `cluster` DISABLE KEYS */;
INSERT INTO `cluster` VALUES (1,1,'Team Nerds','0'),(2,1,'Team Fabcon','0'),(3,1,'New Cluster','0');
/*!40000 ALTER TABLE `cluster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `commentID` int(11) NOT NULL AUTO_INCREMENT,
  `postID` int(11) NOT NULL,
  `commentText` varchar(256) NOT NULL,
  `userID` int(11) NOT NULL,
  PRIMARY KEY (`commentID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'Hello!',1),(2,1,'wahahaha',1),(3,1,'wahahaha',1),(4,2,'ayyy',1),(5,3,'s',1);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `eventID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `startDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `venue` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `hostID` int(11) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `eventPicture` varchar(45) NOT NULL DEFAULT 'img\\default.jpg',
  `isDeleted` binary(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`eventID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'happy thursday','2015-04-02 15:12:48','1992-10-28 16:00:00','sa bahay','public',1,NULL,'img\\default.jpg','0'),(2,'e','2015-04-02 15:12:48','2014-12-31 17:01:00','e','Public',1,'e','img\\default.jpg','0'),(3,'The Event','2015-04-02 15:12:48','2015-04-09 13:00:00','The Venue','Public',1,'Tell me something I need to knoooow','img\\default.jpg','0'),(4,'Test','2015-01-01 05:00:00','2014-01-02 05:00:00','Kahit Saan','Public',2,'Secret hehehe','img\\default.jpg','0');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_view_restriction`
--

DROP TABLE IF EXISTS `event_view_restriction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_view_restriction` (
  `eventID` int(11) NOT NULL,
  `clusterID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_view_restriction`
--

LOCK TABLES `event_view_restriction` WRITE;
/*!40000 ALTER TABLE `event_view_restriction` DISABLE KEYS */;
INSERT INTO `event_view_restriction` VALUES (1,1),(2,1);
/*!40000 ALTER TABLE `event_view_restriction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invite_to_event`
--

DROP TABLE IF EXISTS `invite_to_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invite_to_event` (
  `hostID` int(11) NOT NULL,
  `eventID` int(11) NOT NULL,
  `invitedID` int(11) NOT NULL,
  `status` binary(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invite_to_event`
--

LOCK TABLES `invite_to_event` WRITE;
/*!40000 ALTER TABLE `invite_to_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `invite_to_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likes` (
  `postID` int(11) NOT NULL,
  `userID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `postID` int(11) NOT NULL AUTO_INCREMENT,
  `eventID` varchar(20) NOT NULL,
  `userID` int(11) NOT NULL,
  `postText` varchar(256) NOT NULL,
  `imgPath` varchar(45) DEFAULT NULL,
  `isDeleted` varchar(45) NOT NULL DEFAULT '0',
  PRIMARY KEY (`postID`),
  KEY `eventID_idx` (`eventID`),
  KEY `userID_idx` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'1',2,'I lav eeeeet',NULL,'0'),(2,'1',3,'ano ba yan :(',NULL,'0'),(3,'1',2,'Hello World',NULL,'0'),(4,'1',1,'stop it plzzzz',NULL,'0'),(5,'1',1,'my post worked!!!',NULL,'0'),(6,'4',2,'wee first post',NULL,'0'),(7,'4',1,'wee second post',NULL,'0'),(8,'2',1,'wee',NULL,'0');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_to_join`
--

DROP TABLE IF EXISTS `request_to_join`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_to_join` (
  `hostID` int(11) NOT NULL,
  `eventID` int(11) NOT NULL,
  `requestorID` int(11) NOT NULL,
  `status` binary(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_to_join`
--

LOCK TABLES `request_to_join` WRITE;
/*!40000 ALTER TABLE `request_to_join` DISABLE KEYS */;
/*!40000 ALTER TABLE `request_to_join` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `emailAddress` varchar(45) NOT NULL,
  `birthDate` date DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `profilePicture` varchar(45) NOT NULL DEFAULT 'img\\default.jpg',
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Jose','Rizal','jose_rizal@yahoo.com','1989-06-12','josejose','josephine','img\\default.jpg'),(2,'Arren','Matthew','arrenmatthew@gmail.com','1997-05-14','arvention','password','img\\event\\party1.jpg'),(3,'Aaron','Marc','aaron@gmail.com','2004-10-19','marc11','password','img\\default.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-05 20:16:49
