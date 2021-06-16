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
-- Table structure for table `process_of_treatment`
--

DROP TABLE IF EXISTS `process_of_treatment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `process_of_treatment` (
  `pot_id` int NOT NULL AUTO_INCREMENT,
  `date_of_examination` datetime(6) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`pot_id`),
  KEY `FKkv732yncf4695y4akkspi2m61` (`patient_id`),
  CONSTRAINT `FKkv732yncf4695y4akkspi2m61` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_of_treatment`
--

LOCK TABLES `process_of_treatment` WRITE;
/*!40000 ALTER TABLE `process_of_treatment` DISABLE KEYS */;
INSERT INTO `process_of_treatment` VALUES (1,'2021-05-15 10:21:58.555000','ALCL1302','Toi kham da',2),(2,'2021-05-15 20:17:50.876000','BNLS1093','fbnnja',2),(3,'2021-05-15 20:20:12.130000','HKFD3452','sxc',2),(4,'2021-05-15 20:23:35.118000','BLSE7834','chjn',2),(5,'2021-05-16 15:12:12.324000','SFMG4092','Toi kham da',2),(13,'2021-05-22 16:19:15.270000','KRXV8545','Toi kham da',3),(14,'2021-05-22 16:20:05.562000','EYVS7757','Toi kham da',2),(15,'2021-05-22 16:28:06.426000','XHXJ1133','Toi kham da',1),(16,'2021-06-12 18:53:08.179000','DKDI0699','abc',2),(17,'2021-06-12 18:54:19.399000','NWVI6963','hang abc',1),(18,'2021-06-12 18:54:55.460000','VHJY9144','hien ABC ',3),(19,'2021-06-13 11:35:29.493000','OUOE0025','Toi kham da',2);
/*!40000 ALTER TABLE `process_of_treatment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-16 17:21:09
