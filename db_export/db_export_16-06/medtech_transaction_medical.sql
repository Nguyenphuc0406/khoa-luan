-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: medtech
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `transaction_medical`
--

DROP TABLE IF EXISTS `transaction_medical`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_medical` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `create_date` datetime(6) DEFAULT NULL,
  `status` int DEFAULT '0',
  `doctor_id` int NOT NULL,
  `patient_id` int NOT NULL,
  `POT_ID` int DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FK8cphycmts6iannx36axrhy4rg` (`doctor_id`),
  KEY `FKf8q4gwmyr3bgt2tqi1rqiyfqc` (`patient_id`),
  KEY `transaction_medical___fk_pot` (`POT_ID`),
  CONSTRAINT `FK8cphycmts6iannx36axrhy4rg` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `FKf8q4gwmyr3bgt2tqi1rqiyfqc` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `transaction_medical___fk_pot` FOREIGN KEY (`POT_ID`) REFERENCES `process_of_treatment` (`pot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_medical`
--

LOCK TABLES `transaction_medical` WRITE;
/*!40000 ALTER TABLE `transaction_medical` DISABLE KEYS */;
INSERT INTO `transaction_medical` VALUES (1,'2021-05-15 10:42:10.005000',NULL,1,2,NULL),(2,'2021-05-15 10:43:49.731000',NULL,1,2,NULL),(3,'2021-05-16 15:05:11.864000',1,1,2,NULL),(4,'2021-05-16 15:08:29.146000',1,1,2,NULL),(5,'2021-05-16 16:48:12.780000',1,1,2,NULL),(6,'2021-05-22 12:03:05.098000',1,1,2,NULL),(7,'2021-05-22 17:06:22.888000',1,1,1,NULL),(8,'2021-06-13 11:38:05.175000',0,1,2,NULL),(9,'2021-06-13 12:13:36.133000',0,1,2,NULL),(10,'2021-06-13 14:00:39.506000',1,1,2,NULL),(11,'2021-06-13 14:01:38.013000',1,1,2,NULL),(12,'2021-06-13 14:07:27.422000',0,1,2,NULL),(13,'2021-06-13 14:08:43.215000',0,1,2,NULL),(14,'2021-06-13 14:12:55.550000',0,1,2,NULL),(15,'2021-06-13 14:25:11.694000',0,1,2,NULL),(16,'2021-06-13 14:31:38.530000',0,1,2,NULL),(17,'2021-06-13 14:41:28.306000',0,1,2,NULL),(18,'2021-06-13 14:43:43.341000',0,1,2,NULL),(19,'2021-06-13 14:57:53.540000',0,1,2,19),(20,'2021-06-13 15:58:34.256000',0,1,2,19),(21,'2021-06-13 10:04:00.026000',0,1,2,19);
/*!40000 ALTER TABLE `transaction_medical` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-16 17:21:10
