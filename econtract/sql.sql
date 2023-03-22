/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - econtract
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `bank` */

insert  into `bank`(`bid`,`uid`,`accnumber`,`key`,`ifsccode`,`amount`) values 
(1,3,'99','00','998','99040'),
(2,6,'123','123','999','98680'),
(3,1,'8086733531','123','8086','100000');

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

insert  into `chat`(`chatid`,`fromid`,`toid`,`message`,`date`) values 
(1,2,3,'ok','2022-07-22'),
(2,3,5,'ok','2022-07-23'),
(3,3,5,'g5gtg','2022-07-23'),
(4,5,3,'jj','2022-07-23'),
(5,2,3,'hi','2022-07-23'),
(6,2,3,'helow','2022-07-23'),
(7,6,9,'hi','2022-07-25'),
(8,11,6,'hi','2022-07-26'),
(9,11,3,'ho','2022-07-26'),
(10,11,12,'hi','2022-07-26'),
(11,12,11,'helow','2022-07-26'),
(12,11,12,'hi','2022-07-26'),
(13,12,11,'hi','2022-07-26'),
(14,11,12,'hi','2022-07-26'),
(15,12,11,'hi','2022-07-26');

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

insert  into `complaint`(`complaint_id`,`complaint`,`date`,`user_id`,`reply`) values 
(1,'cmp','12',3,'ok'),
(2,'vvg gh try','2022-07-22',3,'ok'),
(3,'not bad','2022-07-23',6,'pending'),
(4,'slow work ','2022-07-23',6,'pending'),
(5,'boor work ','2022-07-26',12,'pending');

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

insert  into `features`(`features_id`,`contractor_id`,`skill`,`experience`) values 
(2,2,'wfcaslm','asclp sf '),
(3,2,'wekfsdp','fkwqpacs');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `feedback` text,
  `user_id` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `type` varchar(43) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`feedback_id`,`feedback`,`user_id`,`date`,`type`) values 
(1,'good','3','2022-07-22','customer'),
(2,'nice','2','2022-07-22','contractor'),
(3,'okapevld','2','2022-07-22','contractor'),
(4,'okk','3','2022-07-22','customer'),
(5,'good','6','2022-07-23','customer'),
(6,'good','6','2022-07-23','customer'),
(7,'','2','2022-07-23','contractor'),
(8,'good','2','2022-07-24','contractor'),
(9,'','2','2022-07-24','contractor'),
(10,'not bad','12','2022-07-26','customer');

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

insert  into `gpchathod`(`id`,`fid`,`msg`,`date`) values 
(1,2,'ok','2022-07-22'),
(2,5,'msaklmxasklmclsA','2022-07-22'),
(3,11,'hi','2022-07-26');

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

insert  into `job application`(`id`,`vaccancy_id`,`user_id`,`date`,`status`) values 
(1,3,2,'2022-07-22','pending'),
(2,2,3,'2022-07-22','pending'),
(3,2,6,'2022-07-23','pending'),
(4,3,6,'2022-07-23','pending'),
(5,7,6,'2022-07-25','pending'),
(6,4,6,'2022-07-25','pending'),
(7,5,6,'2022-07-25','pending'),
(8,6,6,'2022-07-25','pending'),
(9,8,12,'2022-07-26','pending'),
(10,7,12,'2022-07-26','pending'),
(11,5,12,'2022-07-26','pending');

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `lattitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `location` */

insert  into `location`(`location_id`,`user_id`,`lattitude`,`longitude`) values 
(1,2,11.1411397,75.8615891),
(2,5,11.1411397,75.8615891),
(3,3,11.1446387,75.8627371),
(4,6,11.608495,75.591705),
(5,8,11.608495,75.591705),
(6,9,11.608495,75.591705),
(7,11,11.258753,75.780411);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`lid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`lid`,`username`,`password`,`type`) values 
(1,'admin','admin','admin'),
(2,'Ammu','ammu123','blocked'),
(3,'a','a','user'),
(5,'sacssc','po123','contractor'),
(6,'Arjun','Arjun123','customer'),
(7,'Appu','appu','customer'),
(8,'Minnu','minnu','contractor'),
(9,'vishnu','vishnu','contractor'),
(10,'midhun','midhun','customer'),
(11,'Raju','Raju1234','contractor'),
(12,'Arun','arun123','customer');

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `date` varchar(54) DEFAULT NULL,
  `stauts` varchar(54) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `order` */

insert  into `order`(`order_id`,`user_id`,`total`,`date`,`stauts`) values 
(1,6,0,'2022-07-26','pending'),
(2,6,0,'2022-07-26','pending'),
(3,12,0,'2022-07-26','pending'),
(4,12,0,'2022-07-26','pending');

/*Table structure for table `orderdproduct` */

DROP TABLE IF EXISTS `orderdproduct`;

CREATE TABLE `orderdproduct` (
  `orderdproduct_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderdproduct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`product_name`,`quantity`,`price`,`contractor_id`) values 
(19,'Paint','7',300,9),
(20,'tile','200',100,9),
(22,'steel','8',9000,9);

/*Table structure for table `product_img` */

DROP TABLE IF EXISTS `product_img`;

CREATE TABLE `product_img` (
  `pid` int(11) NOT NULL,
  `image` text,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product_img` */

insert  into `product_img`(`pid`,`image`) values 
(19,'R.jpg'),
(20,'tiles1.jpg'),
(22,'steel1.jpg');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `register` */

insert  into `register`(`register_id`,`lid`,`firstname`,`lastname`,`gender`,`age`,`place`,`pin`,`post`,`service`,`phonenum`,`email`) values 
(1,2,'akshay','p','male',43,'calicut',123000,'kadalundi','plumber',9756345678,'ajmaltpm04@gmail.com'),
(2,5,'asdx','k','male',23,'calicut',123000,'kadalundi','plumber',9855883344,'ddd@gmail.com'),
(3,8,'minnu','Minnu','male',21,'kozhikode',673102,'kanookkara','plumber',8078783531,'minnu@gmail.com'),
(4,9,'vishnu','p','male',23,'kozhikode',673102,'asfghk','painter',8080808080,'vishnu@gmail.com'),
(5,11,'Raju','m','male',34,'calicut',673102,'vadakara','electrician',8086733531,'raju@gmail.com');

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

insert  into `request`(`request_id`,`user_id`,`contractor_id`,`work`,`date`,`status`) values 
(1,3,2,'workkk','12-09-22','onging'),
(2,3,2,'ju','2022-07-22','pending'),
(3,3,2,'hu as edd','2022-07-22','started'),
(4,6,2,'workkkk','2022-07-23','accepted'),
(5,6,2,'plumbing ','2022-07-23','accepted');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`lid`,`firstname`,`lastname`,`email`,`gender`,`place`,`post`,`pin`,`district`,`phoneno`) values 
(1,3,'anu','p','anu@gmail.com','male','placr','vhg',899988,'jkgjh',7685858),
(2,6,'Arjun','p','arjun@gmail.com','Male','kozikode','vadakara ',123456,'Kozhikode',8090809080),
(3,7,'Appu','A','appu@gmail.com','Male','calicut','vadakara',123456,'Kozhikode',3930303030),
(4,10,'midhun','m','midhunm@gmail.com','Male','vadakara','vadakara',673102,'Kozhikode',1010101010),
(5,12,'Arun','A','Arun@gmail.com','Male','Calicut ','vadakara ',673102,'Kozhikode',8090809090);

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

insert  into `vaccancy`(`vaccancy_id`,`job`,`details`,`contractor_id`,`date`) values 
(3,'plumber','kozikode',2,'2022-08-03'),
(4,'painting','paiting,vadakara',2,'2022-07-19'),
(5,'electrical','electrical,calicut',2,'2022-07-12'),
(6,'carpenter','wayanad',2,'2022-07-19'),
(7,'roofer','kozikode',2,'2022-07-27'),
(8,'electriction','chombala,calicut',11,'2022-07-19');

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

insert  into `work`(`id`,`contractor_id`,`work`,`document`,`amount`) values 
(3,2,'plumber','download_1.jpg',12344),
(4,2,'roofer','img.jpg',12345),
(5,2,'roofer','roofing1.jpg',60000),
(6,8,'roofer','roof4.jpg',10000),
(7,8,'plumber','plumb1.jpg',80000),
(8,8,'painting,kozhikode','paint2.jpg',70000),
(9,11,'painting','paint3.jpg',20000);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;useruseruseruser
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
