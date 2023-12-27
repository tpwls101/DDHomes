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
-- Table structure for table `imginfo`
--

DROP TABLE IF EXISTS `imginfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imginfo` (
  `imgInfoNo` int NOT NULL AUTO_INCREMENT,
  `forsaleNo` int DEFAULT NULL,
  `articleNo` int DEFAULT NULL,
  `saveFolder` varchar(45) NOT NULL,
  `originalName` varchar(50) NOT NULL,
  `saveName` varchar(50) NOT NULL,
  PRIMARY KEY (`imgInfoNo`),
  KEY `forsaleNo` (`forsaleNo`),
  KEY `articleNo` (`articleNo`),
  CONSTRAINT `imginfo_ibfk_1` FOREIGN KEY (`forsaleNo`) REFERENCES `forsale` (`forsaleNo`) ON DELETE CASCADE,
  CONSTRAINT `imginfo_ibfk_2` FOREIGN KEY (`articleNo`) REFERENCES `board` (`articleNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imginfo`
--

LOCK TABLES `imginfo` WRITE;
/*!40000 ALTER TABLE `imginfo` DISABLE KEYS */;
INSERT INTO `imginfo` VALUES (1,1,NULL,'231124','개포동_현대1차1.jpg','f27415a2-85bb-433b-ac70-1960cb644e1f.jpg'),(2,1,NULL,'231124','개포동_현대1차2.jpg','e2b81e73-b0e8-40e3-962b-ef248b7c289a.jpg'),(3,2,NULL,'231124','개포동_현대2차1.jpg','fc6dd6b8-6ec9-475f-acad-5115f0c342c7.jpg'),(4,2,NULL,'231124','개포동_현대2차2.jpg','759cb0e1-8f21-467d-997c-e4e154172893.jpg'),(5,3,NULL,'231124','개포동_개포자이_1.jpg','04f70f7b-420e-4091-853c-7f4cee659f34.jpg'),(6,3,NULL,'231124','개포동_개포자이_2.jpg','0dac2353-fe8d-4c13-b729-56c938905965.jpg'),(7,3,NULL,'231124','개포동_개포자이_3.jpg','5a6cde37-a5cd-4cea-bf4f-f3b77d844cfe.jpg'),(8,4,NULL,'231124','개포동_디에이치아너힐즈_1.jpg','705e5a41-fbbc-4a5d-99ea-06090b0004be.jpg'),(9,4,NULL,'231124','개포동_디에이치아너힐즈_2.jpg','f1073a24-b445-42fe-9f33-5215e05794d3.jpg'),(10,5,NULL,'231124','개포동_개포래미안포레스트_1.jpg','ecfb459b-df14-485d-b60c-a6841158fe73.jpg'),(11,5,NULL,'231124','개포동_개포래미안포레스트_2.jpg','148e23e0-b1e0-428c-bcaf-e9f98903609c.jpg'),(12,5,NULL,'231124','개포동_개포래미안포레스트_3.jpg','149ebfce-2c8a-49a6-b6b9-ec0ea785d2eb.jpg'),(13,NULL,1,'231124','전세사기예방법1.png','a502e16d-2f10-4620-8a41-85f9d6f0770e.png'),(14,NULL,1,'231124','전세사기예방법2.png','77e9dccc-2c84-4896-b4b6-09f5b2ae6f3a.png'),(15,NULL,1,'231124','전세사기예방법3.png','10d47d3e-280e-45a9-84b4-6845344abf91.png'),(16,NULL,1,'231124','전세사기예방법4.png','b6a46495-3f6c-4ad1-8a35-49eafb612e0e.png'),(18,NULL,3,'231124','서울톡 챗봇서비스 안내.png','3f66625a-85c1-4bb1-a89a-7f9ed8a95c68.png'),(19,NULL,3,'231124','전월세종합지원센터 안내.png','f624a756-523c-4d6a-9f0d-1e517d7d0895.png'),(20,NULL,4,'231124','서울톡 챗봇서비스 안내.png','6bb67682-00f0-459c-88b2-11f7d8b3854e.png');
/*!40000 ALTER TABLE `imginfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-24 11:02:23
