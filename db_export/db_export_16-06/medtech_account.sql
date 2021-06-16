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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `identity_card` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `device_token` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2021-05-15','BS-01','Nguyen Hoang Anh','123456788','BS khoa noi','Viet Nam','$2y$12$g1CkXMJE5nYkGv8Msq3NOOKZnW8l0tEfSyLVtQsBTjJqWF6lRrzY6','0989788789','ROLE_ADMIN','hoanganh','male',NULL),(2,'1977-01-01','BN_001','Hoang Thu Hang','174568596','Giao vien','Viet Nam','$2a$10$QmSBc9bd1SpcqnVmtdFmNOPDubfPp/IKW7zr09U42YoDs1WZz2b1O','0123456789','ROLE_USER','thuhang','female',NULL),(3,'1977-01-01','BN_002','Nguyen Ngoc Linh','174568596','Giao vien','Viet Nam','$2a$10$tDr.vIcSxWswNlb6cXiGGud8xRh2Xf6n8sXS8szL.LFJ8pwkO2CRW','0123456789','ROLE_USER','ngoclinh','female','cvxRS9jlQOuacKjjnxkkEh:APA91bHOZpMyf2uex18cZB0NgQU0kIyZn5o58jyjU_fI2BL2X8bZLP1wAB0HU3XVFWNkQLlW_9uA6g3Fp_XsnyNf-aK30eGqfxo-UjICs4Yu2DfWGreNLdwGwVGzY4_iM8DOBLpFQPZb'),(4,'1977-01-01','BS_A12','Hoang Hung Anh','174568596','Bac si','Viet Nam','$2a$10$LwMNLidec8b0MdVB9wAJ3ukZkm/0TdRdHNp.EEOm9YEV1h2HkQNzS','0123456789','ROLE_ADMIN','hunganh','male',NULL),(5,'1977-01-01','BN_001','Đinh thị Hiền','174568596','Giao vien','Viet Nam','$2a$10$QmSBc9bd1SpcqnVmtdFmNOPDubfPp/IKW7zr09U42YoDs1WZz2b1O','0123456789','ROLE_USER','dinhhien','female',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
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
