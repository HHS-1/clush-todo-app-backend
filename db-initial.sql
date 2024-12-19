-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: clush
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `calendar_entity`
--

DROP TABLE IF EXISTS `calendar_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendar_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj0c4sylehwufb1mgd54xwrsuy` (`user_id`),
  CONSTRAINT `FKj0c4sylehwufb1mgd54xwrsuy` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar_entity`
--

LOCK TABLES `calendar_entity` WRITE;
/*!40000 ALTER TABLE `calendar_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendar_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar_user`
--

DROP TABLE IF EXISTS `calendar_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendar_user` (
  `calendar_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FKtm0riy9ea6fnsxo8wuphy7b4c` (`user_id`),
  KEY `FK370o23ap0e9v64i8tkkbhj4sa` (`calendar_id`),
  CONSTRAINT `FK370o23ap0e9v64i8tkkbhj4sa` FOREIGN KEY (`calendar_id`) REFERENCES `shared_calendar_entity` (`id`),
  CONSTRAINT `FKtm0riy9ea6fnsxo8wuphy7b4c` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar_user`
--

LOCK TABLES `calendar_user` WRITE;
/*!40000 ALTER TABLE `calendar_user` DISABLE KEYS */;
INSERT INTO `calendar_user` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `calendar_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shared_calendar_entity`
--

DROP TABLE IF EXISTS `shared_calendar_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shared_calendar_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shared_calendar_entity`
--

LOCK TABLES `shared_calendar_entity` WRITE;
/*!40000 ALTER TABLE `shared_calendar_entity` DISABLE KEYS */;
INSERT INTO `shared_calendar_entity` VALUES (1,'공유캘린터 테스트!');
/*!40000 ALTER TABLE `shared_calendar_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shared_to_do_entity`
--

DROP TABLE IF EXISTS `shared_to_do_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shared_to_do_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `priority` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `shared_calendar_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3tjby0wnleg4qqr8ijefp8igx` (`shared_calendar_id`),
  KEY `FKbyukw4im0twhoyu5s28q83hjn` (`user_id`),
  CONSTRAINT `FK3tjby0wnleg4qqr8ijefp8igx` FOREIGN KEY (`shared_calendar_id`) REFERENCES `shared_calendar_entity` (`id`),
  CONSTRAINT `FKbyukw4im0twhoyu5s28q83hjn` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shared_to_do_entity`
--

LOCK TABLES `shared_to_do_entity` WRITE;
/*!40000 ALTER TABLE `shared_to_do_entity` DISABLE KEYS */;
INSERT INTO `shared_to_do_entity` VALUES (1,'2024-12-19 19:00:22.528000','2024-12-19','<p>클러쉬 과제 제출입니다.</p>',1,'진행중','클러쉬 과제 제출입니다.','2024-12-19 19:00:22.528000',1,1);
/*!40000 ALTER TABLE `shared_to_do_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `to_do_entity`
--

DROP TABLE IF EXISTS `to_do_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `to_do_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `priority` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `calendar_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKltn5w9rqseilcl3awiuh9qt5e` (`calendar_id`),
  KEY `FKejtodi2fr3pr3r870uf12iwsw` (`user_id`),
  CONSTRAINT `FKejtodi2fr3pr3r870uf12iwsw` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`),
  CONSTRAINT `FKltn5w9rqseilcl3awiuh9qt5e` FOREIGN KEY (`calendar_id`) REFERENCES `calendar_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `to_do_entity`
--

LOCK TABLES `to_do_entity` WRITE;
/*!40000 ALTER TABLE `to_do_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `to_do_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_entity`
--

DROP TABLE IF EXISTS `user_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_entity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `user_pass_word` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_entity`
--

LOCK TABLES `user_entity` WRITE;
/*!40000 ALTER TABLE `user_entity` DISABLE KEYS */;
INSERT INTO `user_entity` VALUES (1,'qazplm10219@gmail.com','$2a$10$vsjVfpPD0Moxx0As5pTiSeONQnXUE4AytifSP2OyWRjWvXtbTn3E6'),(2,'qazplm1021@naver.com','$2a$10$lt0ir7Zgr6VlCrNsLi5Jdu/./zLaaA4x8Fj9jNVWbfORakmRJeoCW');
/*!40000 ALTER TABLE `user_entity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-19 10:05:40
