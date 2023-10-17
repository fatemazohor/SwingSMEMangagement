CREATE DATABASE  IF NOT EXISTS `smgiftshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `smgiftshop`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: smgiftshop
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `idcustomers` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `cell` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  PRIMARY KEY (`idcustomers`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Faria Khan','Female','012536','Dhaka','Kakrail','2023-09-25'),(2,'Oni','Male','0125698574','Chittagong','Hali Town','2023-10-03'),(3,'Fatema Tuz Zohora','Female','0123456','Dhaka','Dhanmondi','2023-10-06'),(4,'Farhan Ahmed','Male','014256639','Khulna','Khulna Town','2023-10-08'),(5,'Wasim Akram','Male','014254639','Rajshahi','Main town','2023-10-08'),(6,'Javed Ahmed','Male','01425798985','Khulna','Bagarhat','2023-10-08'),(7,'Sabera Khan','Female','0157456','Rajshahi','Ghoramara','2023-10-12'),(9,'Muhammad Emran Hossain','Male','01619192323','Dhaka','Khilgaon','2023-10-13'),(10,'Md. Moshaidul Islam','Male','01711071219','Dhaka','Agargaon','2023-10-14'),(11,'Sabit Hasan','Male','0196587','Dhaka','Shankar','2023-10-14'),(12,'Jenia Islam','Female','013589','Chittagong','Hali Town','2023-10-15'),(13,'Runa Islam','Female','012543','Mymensingh','Main town','2023-10-17'),(14,'Tasfia Islam','Female','0123543','Mymensingh','Main town','2023-10-17');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_charge`
--

DROP TABLE IF EXISTS `delivery_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_charge` (
  `delivery_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(45) DEFAULT NULL,
  `delivery_code` varchar(45) DEFAULT NULL,
  `delivery_chargecol` float DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `delivery_address` varchar(45) DEFAULT NULL,
  `delivery_company` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`delivery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_charge`
--

LOCK TABLES `delivery_charge` WRITE;
/*!40000 ALTER TABLE `delivery_charge` DISABLE KEYS */;
INSERT INTO `delivery_charge` VALUES (1,'2','de0410cId2r8',30,'2023-10-04','2023-10-07','haha','Item 3'),(2,'1','de0410cId1r54',30,'2023-10-04','2023-10-07','Kakrail','Item 4'),(3,'2','de0410cId2r56',30,'2023-10-04','2023-10-07','Kakrail','Item 2'),(4,'1','de0410cId1r3',30,'2023-10-04','2023-10-07','Dhaka','Item 3'),(5,'1','de0410cId1r7',30,'2023-10-04','2023-10-04','ooo','Item 4'),(6,'1','de0410cId1r2',30,'2023-10-04','2023-10-07','uuuuu','Item 3'),(7,'2','de0410cId2r1',30,'2023-10-04','2023-10-07','ppppp','Item 3'),(8,'3','de0610cId3r14',60,'2023-10-06','2023-10-09','Dhamondi','Delivery Person 01'),(9,NULL,'de0810cId3r15',NULL,NULL,NULL,NULL,NULL),(10,NULL,'de0910cId3r20',NULL,NULL,NULL,NULL,NULL),(11,NULL,'de0910cId3r98',NULL,NULL,NULL,NULL,NULL),(12,NULL,'de0910cId3r93',NULL,NULL,NULL,NULL,NULL),(13,NULL,'de0910cId3r17',NULL,NULL,NULL,NULL,NULL),(14,NULL,'de0910cId3r38',NULL,NULL,NULL,NULL,NULL),(15,NULL,'de0910cId3r80',NULL,NULL,NULL,NULL,NULL),(16,NULL,'de0910cId5r97',NULL,NULL,NULL,NULL,NULL),(17,NULL,'de0910cId3r49',NULL,NULL,NULL,NULL,NULL),(18,NULL,'de0910cId6r32',NULL,NULL,NULL,NULL,NULL),(19,NULL,'de0910cId1r77',NULL,NULL,NULL,NULL,NULL),(20,NULL,'de1210cId3r51',NULL,NULL,NULL,NULL,NULL),(21,NULL,'de1210cId1r50',NULL,NULL,NULL,NULL,NULL),(22,NULL,'de1210cId2r97',NULL,NULL,NULL,NULL,NULL),(23,NULL,'de1210cId3r15',NULL,NULL,NULL,NULL,NULL),(24,NULL,'de1210cId1r23',NULL,NULL,NULL,NULL,NULL),(25,NULL,'de1210cId6r54',NULL,NULL,NULL,NULL,NULL),(26,NULL,'de1210cId7r84',NULL,NULL,NULL,NULL,NULL),(27,NULL,'de1310cId8r70',NULL,NULL,NULL,NULL,NULL),(28,NULL,'de1310cIdr15',NULL,NULL,NULL,NULL,NULL),(29,NULL,'de1310cIdr10',NULL,NULL,NULL,NULL,NULL),(30,NULL,'de1310cIdr37',NULL,NULL,NULL,NULL,NULL),(31,NULL,'de1310cIdr24',NULL,NULL,NULL,NULL,NULL),(32,NULL,'de1310cId4r8',NULL,NULL,NULL,NULL,NULL),(33,NULL,'de1310cId7r38',NULL,NULL,NULL,NULL,NULL),(34,NULL,'de1310cId5r64',NULL,NULL,NULL,NULL,NULL),(35,NULL,'de1310cId1r96',NULL,NULL,NULL,NULL,NULL),(36,NULL,'de1310cId4r8',NULL,NULL,NULL,NULL,NULL),(37,NULL,'de1310cId2r43',NULL,NULL,NULL,NULL,NULL),(38,NULL,'de1310cId4r22',NULL,NULL,NULL,NULL,NULL),(39,NULL,'de1310cId1r25',NULL,NULL,NULL,NULL,NULL),(40,NULL,'de1310cId3r78',NULL,NULL,NULL,NULL,NULL),(41,NULL,'de1310cId9r19',NULL,NULL,NULL,NULL,NULL),(42,NULL,'de1310cId7r21',NULL,NULL,NULL,NULL,NULL),(43,NULL,'de1310cId7r49',NULL,NULL,NULL,NULL,NULL),(44,NULL,'de1310cId7r80',NULL,NULL,NULL,NULL,NULL),(45,NULL,'de1310cId4r56',NULL,NULL,NULL,NULL,NULL),(46,NULL,'de1410cId3r12',NULL,NULL,NULL,NULL,NULL),(47,NULL,'de1410cId9r66',NULL,NULL,NULL,NULL,NULL),(48,NULL,'de1410cId9r66',NULL,NULL,NULL,NULL,NULL),(49,NULL,'de1410cId11r9',NULL,NULL,NULL,NULL,NULL),(50,NULL,'de1410cId5r62',NULL,NULL,NULL,NULL,NULL),(51,NULL,'de1410cId2r15',NULL,NULL,NULL,NULL,NULL),(52,NULL,'de1410cId2r10',NULL,NULL,NULL,NULL,NULL),(53,NULL,'de1410cId9r17',NULL,NULL,NULL,NULL,NULL),(54,NULL,'de1410cId1r44',NULL,NULL,NULL,NULL,NULL),(55,NULL,'de1410cId5r95',NULL,NULL,NULL,NULL,NULL),(56,NULL,'de1410cId4r6',NULL,NULL,NULL,NULL,NULL),(57,NULL,'de1410cId1r85',NULL,NULL,NULL,NULL,NULL),(58,NULL,'de1410cId10r10',NULL,NULL,NULL,NULL,NULL),(59,NULL,'de1410cId10r30',NULL,NULL,NULL,NULL,NULL),(60,NULL,'de1510cId9r96',NULL,NULL,NULL,NULL,NULL),(61,NULL,'de1510cId3r89',NULL,NULL,NULL,NULL,NULL),(62,NULL,'de1510cId12r67',NULL,NULL,NULL,NULL,NULL),(63,NULL,'de1610cId12r40',NULL,NULL,NULL,NULL,NULL),(64,NULL,'de1610cId12r46',NULL,NULL,NULL,NULL,NULL),(65,NULL,'de1710cId3r51',NULL,NULL,NULL,NULL,NULL),(66,NULL,'de1710cId10r66',NULL,NULL,NULL,NULL,NULL),(67,NULL,'de1710cId10r35',NULL,NULL,NULL,NULL,NULL),(68,NULL,'de1710cId10r99',NULL,NULL,NULL,NULL,NULL),(69,NULL,'de1710cId11r82',NULL,NULL,NULL,NULL,NULL),(70,NULL,'de1710cId9r93',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `delivery_charge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `delivery_code` varchar(45) DEFAULT NULL,
  `payment` float DEFAULT NULL,
  `paid` float DEFAULT NULL,
  `due` float DEFAULT NULL,
  `delivery_charge` float DEFAULT NULL,
  `payment_option` varchar(45) DEFAULT NULL,
  `delivery_status` varchar(45) DEFAULT 'Order Confirmed',
  `delivery_person` varchar(45) DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,NULL,30,30,0,20,NULL,'Order Complete',NULL,'2023-10-01'),(2,NULL,40,40,0,20,NULL,'Order Complete',NULL,'2023-10-01'),(3,NULL,40,40,0,20,NULL,'Order Complete',NULL,'2023-10-01'),(4,NULL,40,40,0,20,NULL,'Order Complete',NULL,'2023-10-01'),(5,NULL,40,40,0,20,NULL,'Order Complete',NULL,'2023-10-01'),(6,NULL,40,40,0,20,NULL,'Order Complete',NULL,'2023-10-01'),(7,'de0410cId2r8',60,60,0,20,NULL,'Order Complete',NULL,'2023-10-01'),(8,'de0410cId1r54',40,40,0,20,'--Select Payment method--','Order Complete',NULL,'2023-10-01'),(9,'de0410cId1r3',40,40,0,20,'Pay Online','Order Complete',NULL,'2023-10-01'),(10,'de0410cId1r7',40,40,0,20,'Pay Online','Order Complete',NULL,'2023-10-01'),(11,'de0410cId1r2',40,40,0,20,'Pay Online','Order Complete',NULL,'2023-10-01'),(12,'de0410cId2r1',40,40,0,20,'Pay Online','Order Complete',NULL,'2023-10-01'),(13,'de0610cId3r14',560,920,0,20,'Pay Online','Order Complete',NULL,'2023-10-01'),(14,'de0910cId3r20',170,170,0,20,'Cash on Delivery','Order Complete','Delivery Person 01','2023-10-12'),(15,'de0910cId3r93',90,90,0,10,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-12'),(16,'de0910cId3r17',300,300,0,30,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-12'),(17,'de0910cId3r38',330,240,0,60,'Cash on Delivery','Order Complete','Delivery Person 03','2023-10-12'),(18,'de0910cId3r4',330,330,0,60,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-09'),(19,'de0910cId6r32',290,290,0,90,'Cash on Delivery','Order Complete','Delivery Person 01','2023-10-12'),(20,'de0910cId1r77',490,490,0,90,'Cash on Delivery','Order Complete','Delivery Person 03','2023-10-12'),(21,'de1210cId3r51',278,278,0,50,'Cash on Delivery','Order Complete','Delivery Person 01','2023-10-17'),(22,'de1210cId1r50',9360,9360,0,80,'Pay Online','Order Complete','Delivery Person 01','2023-10-11'),(23,'de1210cId2r97',5240,5240,0,40,'Pay Online','Order Complete','Delivery Person 02','2023-10-10'),(24,'de1210cId3r15',10180,10180,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-12'),(25,'de1210cId1r23',9664,9664,0,60,'Pay Online','Order Complete','Delivery Person 03','2023-10-14'),(26,'de1210cId6r54',7178,7178,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-14'),(27,'de1210cId7r84',5186,5186,0,90,'Pay Online','Order Complete','Delivery Person 03','2023-10-15'),(28,'de1310cIdr37',0,0,0,0,'--Select Payment method--','Order Complete','-Select --','2023-10-11'),(29,'de1310cIdr84',0,0,0,0,'--Select Payment method--','Order Complete','-Select --','2023-10-11'),(30,'de1310cId4r8',5156,5156,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-14'),(31,'de1310cId7r38',5410,5410,0,60,'Pay Online','Order Complete','Delivery Person 03','2023-10-16'),(32,'de1310cId5r64',80425,80425,0,90,'Pay Online','Order Complete','Delivery Person 03','2023-10-16'),(33,'de1310cId1r96',360,360,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-10-15'),(34,'de1310cId4r8',660,660,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-10-16'),(35,'de1310cId2r43',180,180,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-10-16'),(36,'de1310cId4r22',43305,43305,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-07-27'),(37,'de1310cId1r25',45705,45705,0,60,'Cash on Delivery','Order Complete','Delivery Person 03','2023-10-30'),(38,'de1310cId3r78',50105,50105,0,60,'Cash on Delivery','Order Complete','Delivery Person 02','2023-08-08'),(39,'de1310cId9r19',52065,52065,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-10-16'),(40,'de1310cId7r21',87805,87805,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-08-12'),(41,'de1310cId7r49',88905,88905,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-09-06'),(42,'de1310cId7r80',123695,123695,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-08-23'),(43,'de1310cId7r2',127105,127105,0,90,'Pay Online','Order Complete','Delivery Person 01','2023-09-10'),(44,'de1310cId4r56',163645,163645,0,90,'Pay Online','Order Complete','Delivery Person 01','2023-09-18'),(45,'de1410cId3r12',180,180,0,60,'Cash on Delivery','Order Complete','Delivery Person 01','2023-10-17'),(46,'de1410cId11r9',4160,4160,0,90,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-18'),(47,'de1410cId5r62',35560,35560,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-09-30'),(48,'de1410cId2r15',300,300,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-10-19'),(49,'de1410cId2r10',1100,1100,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-19'),(50,'de1410cId9r17',12015,12015,0,60,'Cash on Delivery','Order Complete','Delivery Person 03','2023-10-18'),(51,'de1410cId1r44',1690,1690,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-14'),(52,'de1410cId5r95',1330,1330,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-20'),(53,'de1410cId4r6',930,930,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-20'),(54,'de1410cId1r85',810,810,0,60,'Pay Online','Order Complete','Delivery Person 02','2023-10-20'),(55,'de1410cId10r10',210,210,0,60,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-16'),(56,'de1410cId10r30',960,100,0,60,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-19'),(57,'de1510cId9r96',210,210,0,90,'Pay Online','Order Complete','Delivery Person 03','2023-10-18'),(58,'de1510cId3r89',210,210,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-10-18'),(59,'de1510cId12r67',1971,1971,0,60,'Cash on Delivery','Order Complete','Delivery Person 01','2023-10-18'),(60,'de1610cId12r40',1590,1590,0,60,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-19'),(61,'de1610cId12r46',570,570,0,60,'Cash on Delivery','Order Complete','Delivery Person 01','2023-10-19'),(62,'de1710cId3r51',3000,3000,0,60,'Cash on Delivery','Order Complete','Delivery Person 01','2023-10-21'),(63,'de1710cId10r66',4230,4230,0,60,'Pay Online','Order Complete','Delivery Person 01','2023-10-20'),(64,'de1710cId10r35',2080,2080,0,60,'Pay Online','Order Complete','Delivery Person 03','2023-10-21'),(65,'de1710cId10r99',2310,2310,0,60,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-21'),(66,'de1710cId1r82',1620,1620,0,60,'Cash on Delivery','Order Complete','Delivery Person 03','2023-10-22'),(67,'de1710cId11r82',310,310,0,60,'Cash on Delivery','Order Complete','Delivery Person 02','2023-10-22'),(68,'de1710cId9r93',4789,4789,0,60,'Cash on Delivery','Order Complete','Delivery Person 03','2023-10-22');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_stock`
--

DROP TABLE IF EXISTS `product_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_stock` (
  `idproduct_stock` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`idproduct_stock`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_stock`
--

LOCK TABLES `product_stock` WRITE;
/*!40000 ALTER TABLE `product_stock` DISABLE KEYS */;
INSERT INTO `product_stock` VALUES (1,'demo1',9,NULL),(2,'demo2',12,NULL),(3,'demo3',0,NULL),(4,'funcopop1',8,NULL),(5,'demo33',0,NULL);
/*!40000 ALTER TABLE `product_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_tag`
--

DROP TABLE IF EXISTS `product_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_tag` (
  `idproduct_tag` int NOT NULL AUTO_INCREMENT,
  `product_tag` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idproduct_tag`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_tag`
--

LOCK TABLES `product_tag` WRITE;
/*!40000 ALTER TABLE `product_tag` DISABLE KEYS */;
INSERT INTO `product_tag` VALUES (1,'Pen'),(2,'Notebook'),(3,'Brause Calligraphy'),(4,'Mechanical Pencil'),(5,'Sketchbook '),(6,'Water colors'),(7,'Sticker'),(8,'Organizer'),(9,'Fountain Pens');
/*!40000 ALTER TABLE `product_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `idproducts` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `quentity` float DEFAULT '0',
  `unit_price` float DEFAULT NULL,
  `product_tag` varchar(45) DEFAULT NULL,
  `entry_date` date DEFAULT NULL,
  PRIMARY KEY (`idproducts`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Fs pen',7,120,'Pen','2023-10-07'),(2,'fs pen max',7,150,'Pen','2023-10-07'),(3,'Galaxy note',3,80,'Notebook','2023-10-07'),(5,'Fountain pen',7,520,'Pen','2023-10-10'),(6,'Majohn A1 Fountain Pen With Clip',49,3550,'Pen','2023-07-01'),(7,'Pilot Kakuno Fountain Pen',28,980,'Pen','2023-07-02'),(8,'JASE All Weather Waterproof Notepad',43,880,'Notebook','2023-07-03'),(9,'Fairy Quill Dip Calligraphy pen set',14,1549,'Brause Calligraphy','2023-07-01'),(10,'BAOKE Calligraphy Brush pens',20,120,'Brause Calligraphy','2023-07-01'),(11,'Majohn S1 Fountain Red Pen ',5,3550,'Fountain Pens','2023-07-02'),(12,'Jinhao Transparent Red Pen',10,1060,'Fountain Pens','2023-07-02'),(13,'Pilot Petit Mini Red Pen',10,400,'Fountain Pens','2023-07-04'),(14,'Pilot Kakuno Grey Pen',10,980,'Fountain Pens','2023-07-03'),(15,'JASE Waterproof Notepad',10,490,'Notebook','2023-07-03'),(16,'Harry Potter Mini Notebook set',9,250,'Notebook','2023-07-03'),(17,'Fizz Premium Black Diary Gift Set',10,520,'Notebook','2023-07-03'),(18,'MM Signature Hardbound Sketch Book',10,580,'Sketchbook ','2023-07-03'),(19,'Deli Finenolo Mini Sketchbook',19,400,'Sketchbook ','2023-08-01'),(20,'Mont Marte Soft Pastel 36pcs',6,1200,'Water colors','2023-08-01'),(21,'MM Acrylic Colour Pastel Set',14,3380,'Water colors','2023-07-14'),(22,'Retro Foiled Label Sticker Set',6,150,'Sticker','2023-08-14'),(23,'Colorful Quote Sticker Sheet Set',9,150,'Sticker','2023-09-01'),(24,'Deli Organizer Pouch',12,750,'Organizer','2023-09-01'),(25,'F14 pen',98,120,'Pen','2023-10-14'),(26,'Mali WaterColor 36pcs',10,1200,'Water colors','2023-10-14'),(27,'Himi Water Color 12pcs',8,1200,'Water colors','2023-10-14'),(28,'Omecon pen',7,120,'Pen','2023-10-15'),(29,'Tailor Notebook',9,250,'Notebook','2023-10-15'),(30,'JASE notebook',8,490,'Notebook','2023-08-15');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchases` (
  `idpurchases` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) DEFAULT NULL,
  `quentity` float DEFAULT NULL,
  `unit_price` float DEFAULT NULL,
  `total_price` float DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  PRIMARY KEY (`idpurchases`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES (1,'Fs pen',10,60,600,'2023-10-07'),(2,'Fs pen',10,60,600,'2023-10-07'),(3,'fs pen max',50,60,3000,'2023-10-07'),(4,'Fs pen',10,60,600,'2023-10-07'),(5,'Fountain pen',10,200,2000,'2023-10-07'),(6,'Fountain pen',10,200,2000,'2023-10-07'),(7,'Fs pen',10,60,600,'2023-10-07'),(8,'fs pen max',10,60,600,'2023-10-07'),(9,'Galaxy note',10,60,600,'2023-10-07'),(10,'Fs pen',10,60,600,'2023-10-08'),(11,'fs pen max',10,60,600,'2023-10-08'),(12,'Galaxy note',10,60,600,'2023-10-08'),(13,'Fs pen',10,60,600,'2023-10-09'),(14,'fs pen max',10,60,600,'2023-10-09'),(15,'Galaxy note',10,60,600,'2023-10-09'),(16,'Fountain pen',10,200,2000,'2023-10-09'),(17,'Fs pen',1,60,60,'2023-10-09'),(18,'Fountain pen',50,150,7500,'2023-10-12'),(19,'Majohn A1 Fountain Pen With Clip',50,500,25000,'2023-10-12'),(20,'Pilot Kakuno Fountain Pen',50,150,7500,'2023-07-03'),(21,'JASE All Weather Waterproof Notepad',50,120,6000,'2023-07-03'),(22,'Majohn A1 Fountain Pen With Clip',5,1100,5500,'2023-07-11'),(23,'Majohn A1 Fountain Pen With Clip',5,1100,5500,'2023-07-25'),(24,'Majohn A1 Fountain Pen With Clip',5,1100,5500,'2023-08-08'),(25,'Majohn A1 Fountain Pen With Clip',5,1100,5500,'2023-09-20'),(26,'--Select Product Name--',5,1100,5500,'2023-09-08'),(27,'Majohn A1 Fountain Pen With Clip',5,1100,5500,'2023-09-25'),(28,'BAOKE Calligraphy Brush pens',4,500,2000,'2023-07-15'),(29,'BAOKE Calligraphy Brush pens',5,500,2500,'2023-08-22'),(30,'BAOKE Calligraphy Brush pens',5,500,2500,'2023-09-08'),(31,'BAOKE Calligraphy Brush pens',6,500,3000,'2023-11-27'),(32,'Colorful Quote Sticker Sheet Set',20,30,600,'2023-09-11'),(33,'Deli Finenolo Mini Sketchbook',20,90,1800,'2023-08-08'),(34,'Deli Organizer Pouch',20,120,2400,'2023-08-31'),(35,'MM Acrylic Colour Pastel Set',5,1120,5600,'2023-09-21'),(36,'Mont Marte Soft Pastel 36pcs',5,290,1450,'2023-08-03'),(37,'Mont Marte Soft Pastel 36pcs',5,290,1450,'2023-08-27'),(38,'Fizz Premium Black Diary Gift Set',10,125,1250,'2023-07-18'),(39,'Harry Potter Mini Notebook set',10,70,700,'2023-07-03'),(40,'JASE Waterproof Notepad',10,190,1900,'2023-08-08'),(41,'Jinhao Transparent Red Pen',10,400,4000,'2023-10-10'),(42,'Retro Foiled Label Sticker Set',10,15,150,'2023-09-14'),(43,'Pilot Petit Mini Red Pen',10,100,1000,'2023-09-21'),(44,'MM Signature Hardbound Sketch Book',10,150,1500,'2023-08-02'),(45,'Pilot Kakuno Grey Pen',10,400,4000,'2023-08-08'),(46,'Fairy Quill Dip Calligraphy pen set',10,500,5000,'2023-09-10'),(47,'MM Acrylic Colour Pastel Set',10,1120,11200,'2023-10-06'),(48,'Majohn A1 Fountain Pen With Clip',30,1100,33000,'2023-09-15'),(49,'Fs pen',10,40,400,'2023-10-13'),(50,'Majohn A1 Fountain Pen With Clip',10,1120,11200,'2023-10-10'),(51,'F14 pen',100,40,4000,'2023-10-14'),(52,'Himi Water Color 12pcs',10,400,4000,'2023-10-14'),(53,'Mali WaterColor 36pcs',10,400,4000,'2023-10-14'),(54,'Omecon pen',10,40,400,'2023-10-15'),(55,'Tailor Notebook',10,120,1200,'2023-10-15'),(56,'JASE notebook',10,120,1200,'2023-08-17'),(57,'Fairy Quill Dip Calligraphy pen set',10,500,5000,'2023-10-17');
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `idsales` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) DEFAULT NULL,
  `item` varchar(45) DEFAULT NULL,
  `purchase_quentity` float DEFAULT NULL,
  `actual_price` float DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `sales_date` date DEFAULT NULL,
  `unit_price` float DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `payment_id` int DEFAULT NULL,
  `delivery_code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idsales`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,'demo1',NULL,1,20,0,'2023-09-23',NULL,NULL,NULL,NULL),(2,'demo1',NULL,1,10,0,'2023-09-23',10,NULL,NULL,NULL),(3,'demo2',NULL,1,10,0,'2023-09-23',10,NULL,NULL,NULL),(4,'demo2',NULL,2,20,0,'2023-09-24',10,NULL,NULL,NULL),(5,'funcopop1',NULL,1,120,0,'2023-09-25',120,NULL,NULL,NULL),(6,'funcopop1',NULL,2,240,0,'2023-09-25',120,NULL,NULL,NULL),(7,'funcopop1',NULL,1,120,0,'2023-10-02',120,NULL,NULL,NULL),(8,'demo3',NULL,1,20,0,'2023-10-02',20,NULL,NULL,NULL),(9,'funcopop1',NULL,1,120,0,'2023-10-02',120,NULL,NULL,NULL),(10,'demo3',NULL,1,20,0,'2023-10-02',20,NULL,NULL,NULL),(11,'demo3',NULL,1,20,0,'2023-10-02',20,NULL,NULL,NULL),(12,'demo3',NULL,1,20,0,'2023-10-02',20,NULL,NULL,NULL),(13,'demo3',NULL,1,20,0,'2023-10-02',20,NULL,NULL,NULL),(14,'demo1',NULL,1,0,10,NULL,10,NULL,NULL,NULL),(15,'demo2',NULL,1,0,10,NULL,10,NULL,NULL,NULL),(16,'funcopop1',NULL,1,0,120,NULL,120,NULL,NULL,NULL),(17,'demo3',NULL,1,0,20,'2023-10-02',20,NULL,NULL,NULL),(18,'demo1',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(19,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(20,'demo3',NULL,1,0,20,NULL,20,NULL,NULL,NULL),(21,'funcopop1',NULL,1,0,120,NULL,120,NULL,NULL,NULL),(22,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(23,'demo2',NULL,2,0,20,'2023-10-01',10,NULL,NULL,NULL),(24,'demo1',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(25,'demo1',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(26,'demo3',NULL,1,0,20,'2023-10-02',20,NULL,NULL,NULL),(27,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(28,'demo3',NULL,1,0,20,'2023-10-01',20,NULL,NULL,NULL),(29,'demo2',NULL,1,0,10,'2023-10-01',10,NULL,NULL,NULL),(30,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(31,'demo2',NULL,1,0,10,'2023-10-01',10,NULL,NULL,NULL),(32,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(33,'funcopop1',NULL,1,0,10,'2023-10-01',120,NULL,NULL,NULL),(34,'demo1',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(35,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(36,'demo2',NULL,2,0,20,'2023-10-02',10,NULL,NULL,NULL),(37,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(38,'demo2',NULL,2,0,20,'2023-10-02',10,NULL,NULL,NULL),(39,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(40,'demo2',NULL,2,0,20,'2023-10-02',10,NULL,NULL,NULL),(41,'demo2',NULL,1,0,10,'2023-10-02',10,NULL,NULL,NULL),(42,'demo2',NULL,3,0,30,'2023-10-02',10,NULL,NULL,NULL),(43,'demo2',NULL,2,0,20,'2023-10-03',10,1,NULL,'1'),(44,'demo1',NULL,1,0,10,'2023-10-03',10,1,NULL,'1'),(45,'demo2',NULL,1,0,10,'2023-10-03',10,2,NULL,'de0310cId2r71'),(46,'demo2',NULL,1,0,10,'2023-10-03',10,1,NULL,'de0310cId1r77'),(47,'demo2',NULL,1,0,10,'2023-10-03',10,1,NULL,'de0310cId1r52'),(48,'demo2',NULL,1,0,10,'2023-10-03',10,1,NULL,'de0310cId1r5'),(49,'demo1',NULL,1,0,10,'2023-10-04',10,2,NULL,'de0410cId2r8'),(50,'demo2',NULL,2,0,20,'2023-10-04',10,2,NULL,'de0410cId2r8'),(51,'demo1',NULL,1,0,10,'2023-10-04',10,1,NULL,'de0410cId1r54'),(52,'demo1',NULL,1,0,10,'2023-10-04',10,2,NULL,'de0410cId2r56'),(53,'demo2',NULL,1,0,10,'2023-10-04',10,2,NULL,'de0410cId2r56'),(54,'demo2',NULL,1,0,10,'2023-10-04',10,1,NULL,'de0410cId1r3'),(55,'demo2',NULL,1,0,10,'2023-10-04',10,1,NULL,'de0410cId1r7'),(56,'demo2',NULL,1,10,0,'2023-10-04',10,1,NULL,'de0410cId1r2'),(57,'demo2',NULL,1,10,0,'2023-10-04',10,2,NULL,'de0410cId2r1'),(58,'funcopop1',NULL,2,240,0,'2023-10-06',120,3,NULL,'de0610cId3r14'),(59,'demo2',NULL,2,20,0,'2023-10-06',10,3,NULL,'de0610cId3r14'),(60,'funcopop1',NULL,2,240,0,'2023-10-06',120,3,NULL,'de0610cId3r14'),(61,'Galaxy note',NULL,1,80,0,'2023-10-08',80,3,NULL,'de0810cId3r15'),(62,'fs pen max',NULL,1,150,0,'2023-10-09',150,3,NULL,'de0910cId3r20'),(63,'fs pen max','Pen',1,150,0,'2023-10-09',150,3,NULL,'de0910cId3r98'),(64,'Galaxy note','Notebook',1,80,0,'2023-10-09',80,3,NULL,'de0910cId3r93'),(65,'Fs pen','Pen',1,120,0,'2023-10-09',120,3,NULL,'de0910cId3r17'),(66,'fs pen max','Pen',1,150,0,'2023-10-09',150,3,NULL,'de0910cId3r17'),(67,'fs pen max','Pen',1,150,0,'2023-10-09',150,3,NULL,'de0910cId3r38'),(68,'Fs pen','Pen',1,120,0,'2023-10-09',120,3,NULL,'de0910cId3r38'),(69,'Fs pen','Pen',1,120,0,'2023-10-09',120,3,NULL,'de0910cId3r80'),(70,'fs pen max','Pen',1,150,0,'2023-10-09',150,3,NULL,'de0910cId3r80'),(71,'Fs pen','Pen',1,120,0,'2023-10-09',120,5,NULL,'de0910cId5r97'),(72,'Galaxy note','Notebook',1,80,0,'2023-10-09',80,5,NULL,'de0910cId5r97'),(73,'fs pen max','Pen',1,150,0,'2023-10-09',150,3,NULL,'de0910cId3r49'),(74,'Galaxy note','Notebook',1,80,0,'2023-10-09',80,3,NULL,'de0910cId3r49'),(75,'Fs pen','Pen',1,120,0,'2023-10-09',120,6,NULL,'de0910cId6r32'),(76,'Galaxy note','Notebook',1,80,0,'2023-10-09',80,6,NULL,'de0910cId6r32'),(77,'Fs pen','Pen',2,240,0,'2023-10-09',120,1,NULL,'de0910cId1r77'),(78,'Galaxy note','Notebook',2,160,0,'2023-10-09',80,1,NULL,'de0910cId1r77'),(79,'Fs pen','Pen',2,228,5,'2023-10-12',120,3,NULL,'de1210cId3r51'),(80,'Fs pen','Pen',10,1200,0,'2023-10-03',120,1,NULL,'de1210cId1r50'),(81,'Galaxy note','Notebook',10,800,0,'2023-10-03',80,1,NULL,'de1210cId1r50'),(82,'Fountain pen','Pen',14,7280,0,'2023-10-03',520,1,NULL,'de1210cId1r50'),(83,'Fountain pen','Pen',10,5200,0,'2023-10-09',520,2,NULL,'de1210cId2r97'),(84,'Pilot Kakuno Fountain Pen','Pen',10,9800,0,'2023-10-10',980,3,NULL,'de1210cId3r15'),(85,'Galaxy note','Notebook',2,160,0,'2023-10-10',80,3,NULL,'de1210cId3r15'),(86,'Pilot Kakuno Fountain Pen','Pen',10,9604,2,'2023-10-12',980,1,NULL,'de1210cId1r23'),(87,'Majohn A1 Fountain Pen With Clip','Pen',2,6958,2,'2023-10-12',3550,6,NULL,'de1210cId6r54'),(88,'Galaxy note','Notebook',2,160,0,'2023-10-12',80,6,NULL,'de1210cId6r54'),(89,'Fountain pen','Pen',10,5096,2,'2023-10-12',520,7,NULL,'de1210cId7r84'),(90,'Fountain pen','Pen',10,5096,2,'2023-10-12',520,4,NULL,'de1310cId4r8'),(91,'Fountain pen','Pen',10,5200,0,'2023-10-13',520,7,NULL,'de1310cId7r38'),(92,'fs pen max','Pen',1,150,0,'2023-10-13',150,7,NULL,'de1310cId7r38'),(93,'Fountain pen','Pen',10,5200,0,'2023-10-13',520,5,NULL,'de1310cId5r64'),(94,'Majohn A1 Fountain Pen With Clip','Pen',10,35500,0,'2023-10-13',3550,5,NULL,'de1310cId5r64'),(95,'fs pen max','Pen',2,300,0,'2023-10-13',150,1,NULL,'de1310cId1r96'),(96,'fs pen max','Pen',4,600,0,'2023-10-13',150,4,NULL,'de1310cId4r8'),(97,'Fs pen','Pen',1,120,0,'2023-10-13',120,2,NULL,'de1310cId2r43'),(98,'Fairy Quill Dip Calligraphy pen set','Brause Calligraphy',5,7745,0,'2023-07-24',1549,4,NULL,'de1310cId4r22'),(99,'Majohn A1 Fountain Pen With Clip','Pen',10,35500,0,'2023-07-24',3550,4,NULL,'de1310cId4r22'),(100,'Mont Marte Soft Pastel 36pcs','Water colors',2,2400,0,'2023-07-27',1200,1,NULL,'de1310cId1r25'),(101,'JASE All Weather Waterproof Notepad','Notebook',5,4400,0,'2023-08-03',880,3,NULL,'de1310cId3r78'),(102,'Pilot Kakuno Fountain Pen','Pen',2,1960,0,'2023-10-10',980,9,NULL,'de1310cId9r19'),(103,'Colorful Quote Sticker Sheet Set','Sticker',2,300,0,'2023-08-07',150,7,NULL,'de1310cId7r21'),(104,'Majohn A1 Fountain Pen With Clip','Pen',10,35500,0,'2023-08-07',3550,7,NULL,'de1310cId7r21'),(105,'Fountain pen','Pen',2,1040,0,'2023-09-04',520,7,NULL,'de1310cId7r49'),(106,'Majohn A1 Fountain Pen With Clip','Pen',10,34790,2,'2023-08-17',3550,7,NULL,'de1310cId7r80'),(107,'Majohn A1 Fountain Pen With Clip','Pen',10,35500,0,'2023-09-12',3550,4,NULL,'de1310cId4r56'),(108,'Fountain pen','Pen',2,1040,0,'2023-09-12',520,4,NULL,'de1310cId4r56'),(109,'Fs pen','Pen',1,120,0,'2023-10-14',120,3,NULL,'de1410cId3r12'),(110,'Fountain pen','Pen',1,520,0,'2023-10-14',520,11,NULL,'de1410cId11r9'),(111,'Majohn A1 Fountain Pen With Clip','Pen',10,35500,0,'2023-09-19',3550,5,NULL,'de1410cId5r62'),(112,'Fs pen','Pen',1,120,0,'2023-10-14',120,2,NULL,'de1410cId2r15'),(113,'Fountain pen','Pen',1,520,0,'2023-10-14',520,2,NULL,'de1410cId2r10'),(114,'Fountain pen','Pen',1,520,0,'2023-10-14',520,2,NULL,'de1410cId2r10'),(115,'Majohn A1 Fountain Pen With Clip','Pen',3,10437,2,'2023-10-14',3550,9,NULL,'de1410cId9r17'),(116,'Fairy Quill Dip Calligraphy pen set','Brause Calligraphy',1,1518.02,2,'2023-10-14',1549,9,NULL,'de1410cId9r17'),(117,'JASE All Weather Waterproof Notepad','Notebook',1,880,0,'2023-10-14',880,1,NULL,'de1410cId1r44'),(118,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-14',750,1,NULL,'de1410cId1r44'),(119,'Fountain pen','Pen',1,520,0,'2023-10-14',520,5,NULL,'de1410cId5r95'),(120,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-14',750,5,NULL,'de1410cId5r95'),(121,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-14',750,4,NULL,'de1410cId4r6'),(122,'Fs pen','Pen',1,120,0,'2023-10-14',120,4,NULL,'de1410cId4r6'),(123,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-14',750,1,NULL,'de1410cId1r85'),(124,'fs pen max','Pen',1,150,0,'2023-10-14',150,10,NULL,'de1410cId10r10'),(125,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-14',750,10,NULL,'de1410cId10r30'),(126,'F14 pen','Pen',1,120,0,'2023-10-15',120,9,NULL,'de1510cId9r96'),(127,'fs pen max','Pen',1,150,0,'2023-10-15',150,3,NULL,'de1510cId3r89'),(128,'Colorful Quote Sticker Sheet Set','Sticker',5,735,2,'2023-10-15',150,12,NULL,'de1510cId12r67'),(129,'Himi Water Color 12pcs','Water colors',1,1176,2,'2023-10-15',1200,12,NULL,'de1510cId12r67'),(130,'JASE notebook','Notebook',2,980,0,'2023-10-16',490,12,NULL,'de1610cId12r40'),(131,'Retro Foiled Label Sticker Set','Sticker',2,300,0,'2023-10-16',150,12,NULL,'de1610cId12r40'),(132,'Tailor Notebook','Notebook',1,250,0,'2023-10-16',250,12,NULL,'de1610cId12r40'),(133,'Colorful Quote Sticker Sheet Set','Sticker',1,150,0,'2023-10-16',150,12,NULL,'de1610cId12r46'),(134,'Fs pen','Pen',2,240,0,'2023-10-16',120,12,NULL,'de1610cId12r46'),(135,'Omecon pen','Pen',1,120,0,'2023-10-16',120,12,NULL,'de1610cId12r46'),(136,'Omecon pen','Pen',1,120,0,'2023-10-17',120,3,NULL,'de1710cId3r51'),(137,'Retro Foiled Label Sticker Set','Sticker',1,150,0,'2023-10-17',150,3,NULL,'de1710cId3r51'),(138,'Colorful Quote Sticker Sheet Set','Sticker',1,150,0,'2023-10-17',150,3,NULL,'de1710cId3r51'),(139,'Mont Marte Soft Pastel 36pcs','Water colors',1,1200,0,'2023-10-17',1200,3,NULL,'de1710cId3r51'),(140,'Fs pen','Pen',1,120,0,'2023-10-17',120,3,NULL,'de1710cId3r51'),(141,'Himi Water Color 12pcs','Water colors',1,1200,0,'2023-10-17',1200,3,NULL,'de1710cId3r51'),(142,'Fs pen','Pen',1,120,0,'2023-10-17',120,10,NULL,'de1710cId10r66'),(143,'Omecon pen','Pen',1,120,0,'2023-10-17',120,10,NULL,'de1710cId10r66'),(144,'MM Acrylic Colour Pastel Set','Water colors',1,3380,0,'2023-10-17',3380,10,NULL,'de1710cId10r66'),(145,'Deli Finenolo Mini Sketchbook','Sketchbook ',1,400,0,'2023-10-17',400,10,NULL,'de1710cId10r66'),(146,'Colorful Quote Sticker Sheet Set','Sticker',1,150,0,'2023-10-17',150,10,NULL,'de1710cId10r66'),(147,'Fountain pen','Pen',1,520,0,'2023-10-17',520,10,NULL,'de1710cId10r35'),(148,'Colorful Quote Sticker Sheet Set','Sticker',1,150,0,'2023-10-17',150,10,NULL,'de1710cId10r35'),(149,'Retro Foiled Label Sticker Set','Sticker',1,150,0,'2023-10-17',150,10,NULL,'de1710cId10r35'),(150,'Mont Marte Soft Pastel 36pcs','Water colors',1,1200,0,'2023-10-17',1200,10,NULL,'de1710cId10r35'),(151,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-17',750,10,NULL,'de1710cId10r99'),(152,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-17',750,10,NULL,'de1710cId10r99'),(153,'Deli Organizer Pouch','Organizer',1,750,0,'2023-10-17',750,10,NULL,'de1710cId10r99'),(154,'Harry Potter Mini Notebook set','Notebook',1,250,0,'2023-10-17',250,11,NULL,'de1710cId11r82'),(155,'F14 pen','Pen',1,120,0,'2023-10-17',120,9,NULL,'de1710cId9r93'),(156,'Majohn A1 Fountain Pen With Clip','Pen',1,3479,2,'2023-10-17',3550,9,NULL,'de1710cId9r93'),(157,'JASE All Weather Waterproof Notepad','Notebook',1,880,0,'2023-10-17',880,9,NULL,'de1710cId9r93');
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `idstock` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) DEFAULT NULL,
  `product_tag` varchar(45) DEFAULT NULL,
  `quentity` float DEFAULT NULL,
  PRIMARY KEY (`idstock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `iduser` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Admin','A@1234','admin');
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

-- Dump completed on 2023-10-17 12:18:01
