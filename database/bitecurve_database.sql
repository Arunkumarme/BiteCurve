-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: food_delivery_app
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `menu_item_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `menu_item_id` (`menu_item_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
-- Seed data for `cart` intentionally omitted from the public repository.
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_items`
--

DROP TABLE IF EXISTS `menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `restaurant_id` int NOT NULL,
  `name` varchar(150) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `menu_items_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_items`
--

LOCK TABLES `menu_items` WRITE;
/*!40000 ALTER TABLE `menu_items` DISABLE KEYS */;
INSERT INTO `menu_items` VALUES (1,1,'Margherita Pizza','Cheese pizza',199.00,'Pizza','p1.jpg','VEG'),(2,1,'Farmhouse Pizza','Veg pizza',299.00,'Pizza','p2.jpg','VEG'),(3,2,'Veg Burger','Crispy burger',99.00,'Burger','b1.jpg','VEG'),(4,2,'Chicken Burger','Spicy chicken burger',149.00,'Burger','b2.jpg','NON_VEG'),(5,3,'Masala Dosa','South Indian dosa',80.00,'South','d1.jpg','VEG'),(6,3,'Idli','Soft idli',60.00,'South','d2.jpg','VEG'),(7,4,'Veg Noodles','Hakka noodles',120.00,'Chinese','c1.jpg','VEG'),(8,4,'Fried Rice','Veg fried rice',140.00,'Chinese','c2.jpg','VEG'),(9,1,'Cheese Burst Pizza','Extra cheese',350.00,'Pizza','p3.jpg','VEG'),(10,1,'Veg Supreme Pizza','Loaded pizza',400.00,'Pizza','p4.jpg','VEG'),(11,2,'Cheese Burger','Cheesy burger',120.00,'Burger','b3.jpg','VEG'),(12,2,'Double Patty Burger','Double chicken',180.00,'Burger','b4.jpg','VEG'),(13,3,'Vada','Crispy vada',50.00,'South','d3.jpg','VEG'),(14,3,'Upma','Healthy upma',70.00,'South','d4.jpg','VEG'),(15,4,'Noodles Combo','Combo meal',180.00,'Chinese','c3.jpg','VEG'),(16,4,'Manchurian','Spicy gravy',150.00,'Chinese','c4.jpg','VEG'),(17,5,'Chocolate Cake','Rich chocolate cake',120.00,'Dessert',NULL,'VEG'),(18,5,'Ice Cream Sundae','Vanilla ice cream with toppings',90.00,'Dessert',NULL,'VEG'),(19,5,'Brownie','Hot chocolate brownie',100.00,'Dessert',NULL,'VEG'),(20,5,'Cupcake','Soft cream cupcake',60.00,'Dessert',NULL,'VEG'),(21,1,'Paneer Tikka Pizza','Paneer tikka topping',310.00,NULL,NULL,'VEG'),(22,1,'Chicken Tikka Pizza','Spicy chicken pizza',340.00,NULL,NULL,'NON_VEG'),(23,1,'Veg Loaded Pizza','Fully loaded veg pizza',360.00,NULL,NULL,'VEG'),(24,1,'Chicken Sausage Pizza','Sausage topping',370.00,NULL,NULL,'NON_VEG'),(25,2,'Grilled Chicken Burger','Grilled chicken patty',160.00,NULL,NULL,'NON_VEG'),(26,2,'Crispy Paneer Burger','Paneer crispy burger',140.00,NULL,NULL,'VEG'),(27,2,'Chicken Wrap','Spicy chicken wrap',150.00,NULL,NULL,'NON_VEG'),(28,2,'Veg Wrap','Veg roll wrap',120.00,NULL,NULL,'VEG'),(29,3,'Chicken Biryani','Spicy biryani',180.00,NULL,NULL,'NON_VEG'),(30,3,'Veg Biryani','Mixed veg biryani',140.00,NULL,NULL,'VEG'),(31,3,'Egg Dosa','Dosa with egg',120.00,NULL,NULL,'NON_VEG'),(32,3,'Curd Rice','Cool curd rice',70.00,NULL,NULL,'VEG'),(33,4,'Chicken Noodles','Spicy chicken noodles',160.00,NULL,NULL,'NON_VEG'),(34,4,'Egg Fried Rice','Egg rice',140.00,NULL,NULL,'NON_VEG'),(35,4,'Chicken Soup','Hot chicken soup',130.00,NULL,NULL,'NON_VEG'),(36,4,'Paneer Noodles','Paneer noodles',150.00,NULL,NULL,'VEG'),(37,5,'Vanilla Ice Cream','Classic vanilla',80.00,NULL,NULL,'VEG'),(38,5,'Strawberry Shake','Cold shake',110.00,NULL,NULL,'VEG'),(39,5,'Chocolate Donut','Sweet donut',90.00,NULL,NULL,'VEG'),(40,5,'Fruit Salad','Healthy dessert',100.00,NULL,NULL,'VEG'),(41,6,'Andhra Fish Curry','Spicy fish curry',240.00,NULL,NULL,'NON_VEG'),(42,6,'Chicken Fry','Dry spicy chicken',210.00,NULL,NULL,'NON_VEG'),(43,6,'Veg Meals','Full meals thali',150.00,NULL,NULL,'VEG'),(44,6,'Curd Rice','Cool curd rice',80.00,NULL,NULL,'VEG'),(45,6,'Egg Curry','Spicy egg curry',130.00,NULL,NULL,'NON_VEG'),(46,6,'Mutton Curry','Rich mutton curry',300.00,NULL,NULL,'NON_VEG'),(47,6,'Tomato Pappu','Dal curry',110.00,NULL,NULL,'VEG'),(48,6,'Chapati Combo','Chapati with curry',120.00,NULL,NULL,'VEG'),(49,7,'Paneer Butter Masala','Creamy paneer curry',200.00,NULL,NULL,'VEG'),(50,7,'Tandoori Roti','Clay oven roti',30.00,NULL,NULL,'VEG'),(51,7,'Chicken Tikka','Grilled chicken',230.00,NULL,NULL,'NON_VEG'),(52,7,'Jeera Rice','Cumin rice',120.00,NULL,NULL,'VEG'),(53,7,'Dal Makhani','Slow cooked dal',180.00,NULL,NULL,'VEG'),(54,7,'Butter Naan','Soft naan',40.00,NULL,NULL,'VEG'),(55,7,'Mutton Rogan Josh','Rich mutton curry',320.00,NULL,NULL,'NON_VEG'),(56,7,'Lassi','Sweet yogurt drink',90.00,NULL,NULL,'VEG'),(57,8,'Chicken Wings','Spicy grilled wings',260.00,NULL,NULL,'NON_VEG'),(58,8,'Mutton Seekh Kebab','Minced kebab',300.00,NULL,NULL,'NON_VEG'),(59,8,'Veg Grill Platter','Mixed grilled veg',220.00,NULL,NULL,'VEG'),(60,8,'Corn Cheese Balls','Cheesy snacks',180.00,NULL,NULL,'VEG'),(61,8,'Chicken Tikka','Grilled chicken',250.00,NULL,NULL,'NON_VEG'),(62,8,'Paneer Tikka','Grilled paneer',210.00,NULL,NULL,'VEG'),(63,8,'BBQ Chicken','Barbecue chicken',280.00,NULL,NULL,'NON_VEG'),(64,8,'French Fries','Crispy fries',120.00,NULL,NULL,'VEG'),(65,9,'Samosa','Crispy snack',30.00,NULL,NULL,'VEG'),(66,9,'Chaat','Tangy street chaat',70.00,NULL,NULL,'VEG'),(67,9,'Dabeli','Spicy bun snack',60.00,NULL,NULL,'VEG'),(68,9,'Vada Pav','Mumbai snack',50.00,NULL,NULL,'VEG'),(69,9,'Pani Puri','Street snack',50.00,NULL,NULL,'VEG'),(70,9,'Pav Bhaji','Spicy mash',100.00,NULL,NULL,'VEG'),(71,9,'Bhel Puri','Crunchy mix',60.00,NULL,NULL,'VEG'),(72,9,'Aloo Tikki','Crispy tikki',70.00,NULL,NULL,'VEG'),(73,10,'Grilled Sandwich','Healthy sandwich',110.00,NULL,NULL,'VEG'),(74,10,'Fruit Bowl','Fresh fruits',130.00,NULL,NULL,'VEG'),(75,10,'Smoothie','Mixed fruit smoothie',140.00,NULL,NULL,'VEG'),(76,10,'Sprouts Salad','Protein rich salad',100.00,NULL,NULL,'VEG'),(77,10,'Oats Meal','Healthy oats',90.00,NULL,NULL,'VEG'),(78,10,'Veg Wrap','Healthy wrap',120.00,NULL,NULL,'VEG'),(79,10,'Protein Shake','Gym drink',160.00,NULL,NULL,'VEG'),(80,10,'Quinoa Bowl','Healthy quinoa',180.00,NULL,NULL,'VEG'),(81,11,'Apple Juice','Fresh apple juice',80.00,NULL,NULL,'VEG'),(82,11,'Watermelon Juice','Refreshing drink',70.00,NULL,NULL,'VEG'),(83,11,'Banana Shake','Thick banana shake',100.00,NULL,NULL,'VEG'),(84,11,'Lassi','Sweet lassi',90.00,NULL,NULL,'VEG'),(85,11,'Mango Juice','Fresh mango juice',90.00,NULL,NULL,'VEG'),(86,11,'Pineapple Juice','Tangy drink',80.00,NULL,NULL,'VEG'),(87,11,'Cold Coffee','Chilled coffee',120.00,NULL,NULL,'VEG'),(88,11,'Strawberry Shake','Sweet shake',110.00,NULL,NULL,'VEG'),(89,12,'Grilled Chicken','Juicy grilled chicken',250.00,NULL,NULL,'NON_VEG'),(90,12,'Chicken Wings','Spicy wings',180.00,NULL,NULL,'NON_VEG'),(91,13,'Mutton Steak','Grilled mutton steak',350.00,NULL,NULL,'NON_VEG'),(92,13,'BBQ Chicken','Barbecue chicken',300.00,NULL,NULL,'NON_VEG'),(93,14,'Chicken Biryani','Hyderabadi style',220.00,NULL,NULL,'NON_VEG'),(94,14,'Mutton Biryani','Rich mutton biryani',280.00,NULL,NULL,'NON_VEG'),(95,15,'Seekh Kebab','Spicy kebabs',200.00,NULL,NULL,'NON_VEG'),(96,15,'Chicken Tikka','Grilled tikka',240.00,NULL,NULL,'NON_VEG'),(97,16,'Fish Fry','Crispy fried fish',260.00,NULL,NULL,'NON_VEG'),(98,16,'Prawn Curry','Spicy prawn curry',300.00,NULL,NULL,'NON_VEG');
/*!40000 ALTER TABLE `menu_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `menu_item_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `menu_item_id` (`menu_item_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
-- Seed data for `order_items` intentionally omitted from the public repository.
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `status` varchar(50) NOT NULL,
  `payment_status` varchar(50) NOT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
-- Seed data for `orders` intentionally omitted from the public repository.
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `payment_method` varchar(50) NOT NULL,
  `payment_status` varchar(50) NOT NULL,
  `transaction_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`),
  UNIQUE KEY `transaction_id` (`transaction_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurants` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `cuisine` varchar(100) NOT NULL,
  `rating` decimal(2,1) DEFAULT NULL,
  `delivery_time` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (1,'Pizza Palace','Italian',4.5,30,'https://images.unsplash.com/photo-1600891964599-f61ba0e24092?w=400','BOTH'),(2,'Burger Hub','Fast Food',4.2,25,'https://images.unsplash.com/photo-1550547660-d9450f859349?w=400','BOTH'),(3,'South Spice','South Indian',4.7,20,'https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400','BOTH'),(4,'Chinese Wok','Chinese',4.3,35,'https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400','BOTH'),(5,'Dessert House','Desserts',4.6,20,'https://images.unsplash.com/photo-1551024601-bec78aea704b?w=400','VEG'),(6,'Andhra Spice','South Indian',4.4,NULL,NULL,'BOTH'),(7,'Punjabi Dhaba','North Indian',4.3,NULL,NULL,'BOTH'),(8,'BBQ Nation Express','Grill & BBQ',4.5,NULL,'https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=400','BOTH'),(9,'Street Food Corner','Street Food',4.1,NULL,NULL,'VEG'),(10,'Healthy Bites','Healthy Food',4.2,NULL,NULL,'VEG'),(11,'Juice Junction','Beverages',4.0,NULL,NULL,'VEG'),(12,'Chicken King','Grill & BBQ',4.3,30,'','NON_VEG'),(13,'Meat Lovers','Steak & BBQ',4.5,35,'','NON_VEG'),(14,'Biryani House','Hyderabadi',4.4,28,'','NON_VEG'),(15,'Kebab Corner','Mughlai',4.2,25,'','NON_VEG'),(16,'Seafood Shack','Seafood',4.3,32,'','NON_VEG'),(17,'BBQ Mutton','cdb',4.0,30,'','BOTH');
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
  `review_text` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_order_review` (`order_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
-- Seed data for `reviews` intentionally omitted from the public repository.
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
-- Seed data for `users` intentionally omitted from the public repository.
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-23 10:28:54
