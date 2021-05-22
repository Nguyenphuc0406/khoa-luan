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
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2021-05-15','BS-01','Nguyen Hoang Anh','123456788','BS khoa noi','Viet Nam','$2y$12$g1CkXMJE5nYkGv8Msq3NOOKZnW8l0tEfSyLVtQsBTjJqWF6lRrzY6','0989788789','ROLE_ADMIN','hoanganh','male'),(2,'1977-01-01','BN_001','Hoang Thu Hang','174568596','Giao vien','Viet Nam','$2a$10$QmSBc9bd1SpcqnVmtdFmNOPDubfPp/IKW7zr09U42YoDs1WZz2b1O','0123456789','ROLE_USER','thuhang','female'),(3,'1977-01-01','BN_002','Nguyen Ngoc Linh','174568596','Giao vien','Viet Nam','$2a$10$tDr.vIcSxWswNlb6cXiGGud8xRh2Xf6n8sXS8szL.LFJ8pwkO2CRW','0123456789','ROLE_USER','ngoclinh','female'),(4,'1977-01-01','BS_A12','Hoang Hung Anh','174568596','Bac si','Viet Nam','$2a$10$LwMNLidec8b0MdVB9wAJ3ukZkm/0TdRdHNp.EEOm9YEV1h2HkQNzS','0123456789','ROLE_ADMIN','hunganh','male');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dept` (
  `dept_id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (1,'C102','Phong kham chuyen khoa noi','Khám nội','0978678879'),(2,'A205','Phong kham da lieu','Khám da liễu','0897676568'),(3,'B1-401','Chuyen khoa chua cac benh ve tieu hoa','Khoa tiêu hóa','0945978765');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `doctor_id` int NOT NULL AUTO_INCREMENT,
  `active` int DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  `dept_id` int NOT NULL,
  PRIMARY KEY (`doctor_id`),
  KEY `FK464ali1gjqfqsmov2usdt6onm` (`account_id`),
  KEY `FKe828ryylo3gnkxna0g6t7bac` (`dept_id`),
  CONSTRAINT `FK464ali1gjqfqsmov2usdt6onm` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FKe828ryylo3gnkxna0g6t7bac` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,1,'A302',1,1),(2,1,'A301',4,3);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_of_dept`
--

DROP TABLE IF EXISTS `item_of_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_of_dept` (
  `iod_id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `consulting_room` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `dept_id` int NOT NULL,
  PRIMARY KEY (`iod_id`),
  KEY `FKk8ga14s6yyvhr4k4e1vbyqhr0` (`dept_id`),
  CONSTRAINT `FKk8ga14s6yyvhr4k4e1vbyqhr0` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_of_dept`
--

LOCK TABLES `item_of_dept` WRITE;
/*!40000 ALTER TABLE `item_of_dept` DISABLE KEYS */;
INSERT INTO `item_of_dept` VALUES (1,'B2CA','C3-202','Phong sieu am chuan doan gan','Sieu am gan',5000,1),(2,'B2CQ','C3-201','Phong sieu am chuan doan mat','Sieu am mat',6000,1),(3,'A123','C4-201','Phong sieu am chuan doan da day','Sieu am da day',5500,3),(4,'B120','C2-201','Phòng xét nghiệm phân tích tế bào da','Phân tích tế bào da',5100,2);
/*!40000 ALTER TABLE `item_of_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `account_id` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  `permanent_address` varchar(255) DEFAULT NULL,
  `health_insurance` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,2,45,'Dong Da, Ha Noi',NULL),(2,3,29,'Dong Da, Ha Noi',NULL);
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` varchar(60) NOT NULL,
  `outpatient_cost` int DEFAULT NULL,
  `total_price` int DEFAULT NULL,
  `patient_id` int NOT NULL,
  `trans_medical_id` int DEFAULT NULL,
  `payment_code` varchar(25) DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `FK8t7hyujfhrl2jneu9jayv89tq` (`patient_id`),
  KEY `FK84sk1yf7mmp8kmho7yr5cf020` (`trans_medical_id`),
  CONSTRAINT `FK84sk1yf7mmp8kmho7yr5cf020` FOREIGN KEY (`trans_medical_id`) REFERENCES `transaction_medical` (`transaction_id`),
  CONSTRAINT `FK8t7hyujfhrl2jneu9jayv89tq` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('0e63ac8f-fc4b-46d1-871f-6f633b2878cc',0,11000,2,5,'BKHD1796',NULL);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_detail`
--

DROP TABLE IF EXISTS `payment_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_detail` (
  `iod_id` int NOT NULL,
  `payment_id` int NOT NULL,
  PRIMARY KEY (`iod_id`,`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_detail`
--

LOCK TABLES `payment_detail` WRITE;
/*!40000 ALTER TABLE `payment_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_detail` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_of_treatment`
--

LOCK TABLES `process_of_treatment` WRITE;
/*!40000 ALTER TABLE `process_of_treatment` DISABLE KEYS */;
INSERT INTO `process_of_treatment` VALUES (1,'2021-05-15 10:21:58.555000','ALCL1302','Toi kham da',2),(2,'2021-05-15 20:17:50.876000','BNLS1093','fbnnja',2),(3,'2021-05-15 20:20:12.130000','HKFD3452','sxc',2),(4,'2021-05-15 20:23:35.118000','BLSE7834','chjn',2),(5,'2021-05-16 15:12:12.324000','SFMG4092','Toi kham da',2);
/*!40000 ALTER TABLE `process_of_treatment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_of_treatment_detail`
--

DROP TABLE IF EXISTS `process_of_treatment_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `process_of_treatment_detail` (
  `dept_id` int NOT NULL,
  `pot_id` int NOT NULL,
  PRIMARY KEY (`dept_id`,`pot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_of_treatment_detail`
--

LOCK TABLES `process_of_treatment_detail` WRITE;
/*!40000 ALTER TABLE `process_of_treatment_detail` DISABLE KEYS */;
INSERT INTO `process_of_treatment_detail` VALUES (0,2),(0,3),(1,4),(2,1),(2,4),(2,5);
/*!40000 ALTER TABLE `process_of_treatment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_medical`
--

DROP TABLE IF EXISTS `transaction_medical`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_medical` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `create_date` datetime(6) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `doctor_id` int NOT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FK8cphycmts6iannx36axrhy4rg` (`doctor_id`),
  KEY `FKf8q4gwmyr3bgt2tqi1rqiyfqc` (`patient_id`),
  CONSTRAINT `FK8cphycmts6iannx36axrhy4rg` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `FKf8q4gwmyr3bgt2tqi1rqiyfqc` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_medical`
--

LOCK TABLES `transaction_medical` WRITE;
/*!40000 ALTER TABLE `transaction_medical` DISABLE KEYS */;
INSERT INTO `transaction_medical` VALUES (1,'2021-05-15 10:42:10.005000',NULL,1,2),(2,'2021-05-15 10:43:49.731000',NULL,1,2),(3,'2021-05-16 15:05:11.864000',1,1,2),(4,'2021-05-16 15:08:29.146000',1,1,2),(5,'2021-05-16 16:48:12.780000',1,1,2);
/*!40000 ALTER TABLE `transaction_medical` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_medical_detail`
--

DROP TABLE IF EXISTS `transaction_medical_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_medical_detail` (
  `item_of_dept_id` int NOT NULL,
  `transaction_id` int NOT NULL,
  PRIMARY KEY (`item_of_dept_id`,`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_medical_detail`
--

LOCK TABLES `transaction_medical_detail` WRITE;
/*!40000 ALTER TABLE `transaction_medical_detail` DISABLE KEYS */;
INSERT INTO `transaction_medical_detail` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(2,1),(2,2),(2,3),(2,4),(2,5);
/*!40000 ALTER TABLE `transaction_medical_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-22 11:20:42
