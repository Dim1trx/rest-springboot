


CREATE DATABASE IF NOT EXISTS `rest_course` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `rest_course`;

-- Copiando estrutura para tabela rest_course.people
CREATE TABLE IF NOT EXISTS `people` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(80) NOT NULL,
    `last_name` varchar(80) DEFAULT NULL,
    `address` varchar(100) DEFAULT NULL,
    `gender` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
