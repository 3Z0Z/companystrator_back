CREATE SCHEMA `company_administrator` ;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(255) NOT NULL,
  `role` varchar(45) NOT NULL,
  `refresh_token` varchar(300) DEFAULT NULL,
  `enable` tinyint NOT NULL,
  `blocked` tinyint NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `refresh_token_UNIQUE` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `company` (
  `NIT` varchar(15) NOT NULL,
  `name` varchar(70) NOT NULL,
  `address` varchar(150) NOT NULL,
  `phone_indicator` varchar(5) NOT NULL,
  `phone` varchar(15) NOT NULL,
  PRIMARY KEY (`NIT`),
  UNIQUE KEY `NIT_UNIQUE` (`NIT`),
  UNIQUE KEY `company_name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `category_UNIQUE` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
  `code` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(80) NOT NULL,
  `price_COP` double NOT NULL,
  `price_USD` double NOT NULL,
  `price_MXN` double NOT NULL,
  `primary_category` int NOT NULL,
  `secondary_category` int DEFAULT NULL,
  `company_id` varchar(15) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `idproduct_UNIQUE` (`code`),
  KEY `company_id_product_id_idx` (`company_id`),
  KEY `primary_cat_prod_id_1_idx` (`primary_category`),
  KEY `sec_cat_prod_id_idx` (`secondary_category`),
  CONSTRAINT `comp_id_prod_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`NIT`),
  CONSTRAINT `prim_cat_prod_id` FOREIGN KEY (`primary_category`) REFERENCES `product_category` (`id`),
  CONSTRAINT `sec_cat_prod_id` FOREIGN KEY (`secondary_category`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `client` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `company_id` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `comp_id_client_id_idx` (`company_id`),
  KEY `user_id_client_id_idx` (`user_id`),
  CONSTRAINT `comp_id_client_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`NIT`),
  CONSTRAINT `user_id_client_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `client_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `total_amount` double NOT NULL,
  `currency` varchar(3) NOT NULL,
  `placed_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id_fk_idx` (`client_id`),
  CONSTRAINT `client_id_fk` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `order_id_fk_idx` (`order_id`),
  KEY `product_id_fk_idx` (`product_id`),
  CONSTRAINT `order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `client_order` (`id`),
  CONSTRAINT `product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci