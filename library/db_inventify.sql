/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 5.7.44 : Database - db_inventify
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_inventify` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `db_inventify`;

/*Table structure for table `sepatu` */

DROP TABLE IF EXISTS `sepatu`;

CREATE TABLE `sepatu` (
  `id_sepatu` varchar(20) NOT NULL,
  `nama_sepatu` varchar(100) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `ukuran` int(11) NOT NULL,
  `stok` int(11) DEFAULT '0',
  PRIMARY KEY (`id_sepatu`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sepatu` */

insert  into `sepatu`(`id_sepatu`,`nama_sepatu`,`brand`,`ukuran`,`stok`) values 
('SPT001','Air Jordan 1 Low','Nike',42,62),
('SPT002','Samba Classic','Adidas',41,116),
('SPT003','nike air','nike',43,124);

/*Table structure for table `stok_masuk` */

DROP TABLE IF EXISTS `stok_masuk`;

CREATE TABLE `stok_masuk` (
  `id_masuk` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_supplier` int(11) DEFAULT NULL,
  `id_sepatu` varchar(20) DEFAULT NULL,
  `jumlah` int(11) NOT NULL,
  `harga_beli` double NOT NULL,
  `total_harga` double NOT NULL,
  PRIMARY KEY (`id_masuk`),
  KEY `id_supplier` (`id_supplier`),
  KEY `id_sepatu` (`id_sepatu`),
  CONSTRAINT `stok_masuk_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE SET NULL,
  CONSTRAINT `stok_masuk_ibfk_2` FOREIGN KEY (`id_sepatu`) REFERENCES `sepatu` (`id_sepatu`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `stok_masuk` */

insert  into `stok_masuk`(`id_masuk`,`tanggal`,`id_supplier`,`id_sepatu`,`jumlah`,`harga_beli`,`total_harga`) values 
(1,'2026-06-19 23:23:54',1,'SPT002',40,500000,20000000),
(2,'2026-06-19 23:35:52',2,'SPT002',56,350000,19600000),
(3,'2026-06-19 23:36:38',2,'SPT001',23,225000,5175000),
(7,'2026-06-20 00:05:59',1,'SPT001',24,567000,13608000),
(8,'2026-06-20 07:47:13',3,'SPT003',24,567000,13608000),
(9,'2026-06-20 07:52:51',3,'SPT003',100,120,12000);

/*Table structure for table `supplier` */

DROP TABLE IF EXISTS `supplier`;

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL AUTO_INCREMENT,
  `nama_supplier` varchar(100) NOT NULL,
  `no_telp` varchar(20) NOT NULL,
  `alamat` text NOT NULL,
  PRIMARY KEY (`id_supplier`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `supplier` */

insert  into `supplier`(`id_supplier`,`nama_supplier`,`no_telp`,`alamat`) values 
(1,'PT. Jaya Sepatu Nusantara','08123456789','Denpasar, Bali'),
(2,'Canggu Shoes Distributor','08776543210','Canggu, Bali'),
(3,'PT. SINAR JAYA SEPATU','0361','panjer');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `no_hp` varchar(15) DEFAULT NULL,
  `role` enum('Super Admin','Admin Gudang') NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id_user`,`username`,`password`,`nama_lengkap`,`no_hp`,`role`) values 
(2,'Dekwai','dekwi123','Dekwai Ganteng','081453789675','Super Admin'),
(3,'wijaya','wijaya123','Wijaya Anjay','081789654723','Admin Gudang'),
(4,'kesuma','kesuma123','Wijaya Kesuma','08766283812312','Admin Gudang'),
(5,'sujana','sujana123','made sujanaaa','089789657453','Admin Gudang'),
(6,'arya','arya123','made arya weda','0872653792','Admin Gudang'),
(7,'ahmad','ahmad34','ahmad jaya','081976456732','Admin Gudang'),
(8,'pazaa','paza123','paza cihuyy','087976546378','Admin Gudang'),
(9,'yahudiii','yahudi123','gus yahudiiiii','081245673654','Admin Gudang'),
(10,'yogad','yoga123','yoga','083117995980','Admin Gudang');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
