/*
SQLyog Community Edition- MySQL GUI v8.03 
MySQL - 5.6.12-log : Database - econtract
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`econtract` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `econtract`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `accno` varchar(50) DEFAULT NULL,
  `ifsc` varchar(50) DEFAULT NULL,
  `pin` varchar(50) DEFAULT NULL,
  `balance` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `account` */

/*Table structure for table `bank` */

DROP TABLE IF EXISTS `bank`;

CREATE TABLE `bank` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `accnumber` varchar(54) DEFAULT NULL,
  `key` varchar(45) DEFAULT NULL,
  `ifsccode` varchar(54) DEFAULT NULL,
  `amount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `bank` */

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chatid` int(11) NOT NULL AUTO_INCREMENT,
  `fromid` int(11) DEFAULT NULL,
  `toid` int(11) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`chatid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `complaint` text,
  `date` varchar(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `reply` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

/*Table structure for table `features` */

DROP TABLE IF EXISTS `features`;

CREATE TABLE `features` (
  `features_id` int(11) NOT NULL AUTO_INCREMENT,
  `contractor_id` int(11) DEFAULT NULL,
  `skill` varchar(100) DEFAULT NULL,
  `experience` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`features_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `features` */

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `feedback` text,
  `user_id` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `type` varchar(43) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

/*Table structure for table `gpchathod` */

DROP TABLE IF EXISTS `gpchathod`;

CREATE TABLE `gpchathod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) NOT NULL,
  `msg` text,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `gpchathod` */

/*Table structure for table `group` */

DROP TABLE IF EXISTS `group`;

CREATE TABLE `group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `group` */

/*Table structure for table `job application` */

DROP TABLE IF EXISTS `job application`;

CREATE TABLE `job application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vaccancy_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `job application` */

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `lattitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `location` */

insert  into `location`(`location_id`,`user_id`,`lattitude`,`longitude`) values (9,17,12.033,75.7137),(10,19,12.1525,75.4639);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`lid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`lid`,`username`,`password`,`type`) values (1,'admin','admin','admin'),(17,'ramesh','Ramesh@123','contractor'),(18,'anu','Anu@1234','customer'),(19,'alex','Alex@1234','contractor'),(20,'sneha','Sneha@123','customer');

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `date` varchar(54) DEFAULT NULL,
  `stauts` varchar(54) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `order` */

insert  into `order`(`order_id`,`user_id`,`total`,`date`,`stauts`) values (10,18,0,'2023-01-09','pending'),(11,18,0,'2023-01-09','pending'),(12,18,0,'2023-01-09','pending');

/*Table structure for table `orderdproduct` */

DROP TABLE IF EXISTS `orderdproduct`;

CREATE TABLE `orderdproduct` (
  `orderdproduct_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderdproduct_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `orderdproduct` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(49) DEFAULT NULL,
  `quantity` varchar(60) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `contractor_id` int(49) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`product_name`,`quantity`,`price`,`contractor_id`) values (23,'Yellow Paint','10',750,17);

/*Table structure for table `product_img` */

DROP TABLE IF EXISTS `product_img`;

CREATE TABLE `product_img` (
  `pid` int(11) NOT NULL,
  `image` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product_img` */

insert  into `product_img`(`pid`,`image`) values (23,'download.jpg');

/*Table structure for table `register` */

DROP TABLE IF EXISTS `register`;

CREATE TABLE `register` (
  `register_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(40) DEFAULT NULL,
  `gender` varchar(40) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `place` varchar(40) DEFAULT NULL,
  `pin` bigint(20) DEFAULT NULL,
  `post` varchar(40) DEFAULT NULL,
  `service` varchar(40) DEFAULT NULL,
  `phonenum` bigint(20) DEFAULT NULL,
  `email` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`register_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `register` */

insert  into `register`(`register_id`,`lid`,`firstname`,`lastname`,`gender`,`age`,`place`,`pin`,`post`,`service`,`phonenum`,`email`) values (7,17,'Ramesh','M','male',40,'Iritty',670706,'Vallithode','painter',9747838707,'ramesh@gmail.com'),(8,19,'Alex','R R','male',45,'Kannur',670582,'Vayattuparamba','plumber',8025403265,'alex@gmail.com');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `contractor_id` int(11) DEFAULT NULL,
  `work` varchar(100) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `firstname` varchar(40) DEFAULT NULL,
  `lastname` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `gender` varchar(30) DEFAULT NULL,
  `place` varchar(30) DEFAULT NULL,
  `post` varchar(30) DEFAULT NULL,
  `pin` bigint(20) DEFAULT NULL,
  `district` varchar(40) DEFAULT NULL,
  `phoneno` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`lid`,`firstname`,`lastname`,`email`,`gender`,`place`,`post`,`pin`,`district`,`phoneno`) values (8,18,'ANU TOMY',' TOMY','anutomy@gmail.com','Male','Iritty','Vallithode',670706,'Kannur',9747838707),(9,20,'Sneha','roy','sneha@gmail.com','Male','Kannur','Vayattuparamba',670582,'Kannur',9747838707);

/*Table structure for table `vaccancy` */

DROP TABLE IF EXISTS `vaccancy`;

CREATE TABLE `vaccancy` (
  `vaccancy_id` int(11) NOT NULL AUTO_INCREMENT,
  `job` varchar(20) DEFAULT NULL,
  `details` varchar(30) DEFAULT NULL,
  `contractor_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`vaccancy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `vaccancy` */

/*Table structure for table `work` */

DROP TABLE IF EXISTS `work`;

CREATE TABLE `work` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contractor_id` int(11) DEFAULT NULL,
  `work` varchar(100) DEFAULT NULL,
  `document` varchar(100) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `work` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
