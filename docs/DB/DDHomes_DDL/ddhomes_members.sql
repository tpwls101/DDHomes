-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ddhomes
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `userId` varchar(16) NOT NULL,
  `userPwd` varchar(200) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `emailId` varchar(20) NOT NULL,
  `emailDomain` varchar(30) NOT NULL,
  `joinDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `grade` varchar(20) NOT NULL DEFAULT 'basic',
  `token` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('1273287695','$2a$10$lA5eD86fHQErH.2Rsvha7u/ky33XHHQNqI/dK5SuTMUSKR.DRIF9m','효자정보공인중개사사무소','1273287695','google.com','2023-11-24 00:02:48','enter',NULL),('1282649219','$2a$10$UKduKq/VyGtKF0Q4Jhixh.iGq2WcjT.WjpDYjX2FLxoS1Q4X0wGi6','신세계 공인중개사','1282649219','google.com','2023-11-24 00:14:55','enter',NULL),('admin','$2a$10$PrD4cP.4oJF.NgV4JatdV.Owt9pwIUYBMPSMY76D8WuDieJyZ4sbm','관리자','admin','google.com','2023-11-24 00:15:33','admin','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNzAwNzg4NTU3LCJleHAiOjE3MDMzODA1NTcsInVzZXJJZCI6ImFkbWluIn0.CL-U9HlwG1PToHEa79SN5hUZ-eEz0b6F96-_vTohNUw'),('bullie_hu','$2a$10$t1dxC0wA6lbRafgT4XUuvOMbjW08Cfyk3dZGS4PTZTXC6NC.5CFyK','이항우','bullie_hu','ssafy.com','2023-11-24 00:00:19','basic','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNzAwNzkwMjMzLCJleHAiOjE3MDMzODIyMzMsInVzZXJJZCI6ImJ1bGxpZV9odSJ9.ENIDDWLXPtxvauMfUl58dCbEBGFbDYLUZ_GPY0Ls9R0'),('tpwls101','$2a$10$VRZx6r8.Sqhbz3SslqhOa..eWi07ESvhzuL1m3MV7fuArMa9D3Ilq','유세진','tpwls101','naver.com','2023-11-24 00:43:53','basic','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNzAwNzg2NjUxLCJleHAiOjE3MDMzNzg2NTEsInVzZXJJZCI6InRwd2xzMTAxIn0.H_87RBa91a63zW3Cl3NGDknWgbu05rDexZquVRBGkJY');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-24 11:02:24
