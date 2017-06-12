-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.55-0ubuntu0.14.04.1 - (Ubuntu)
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for kalampoki
CREATE DATABASE IF NOT EXISTS `kalampoki` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `kalampoki`;

-- Dumping structure for table kalampoki.blog
CREATE TABLE IF NOT EXISTS `blog` (
  `kd_blog` int(10) NOT NULL AUTO_INCREMENT,
  `judul` varchar(100) NOT NULL,
  `isi` text NOT NULL,
  `foto` varchar(100) NOT NULL,
  `kategori` enum('Happening','Inside') NOT NULL,
  `insert_date` date NOT NULL,
  PRIMARY KEY (`kd_blog`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table kalampoki.blog: 7 rows
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` (`kd_blog`, `judul`, `isi`, `foto`, `kategori`, `insert_date`) VALUES
	(1, 'Test Judul', 'Test IISIIII', 'http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png', 'Inside', '2017-04-29'),
	(2, 'Test Lagi', 'Test ISI LAGI', 'http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png', 'Happening', '2017-04-29'),
	(3, 'TEST HAPPENING', 'ISI HAPPENING', 'http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png', 'Happening', '2017-05-14'),
	(4, 'TEST INSIDE', 'ISI INSIDE', 'http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png', 'Inside', '2017-05-14'),
	(5, 'TEST HAPPENING 2', 'ISI HAPPENING 2 ', 'http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png', 'Happening', '2017-05-14'),
	(6, 'TEST INSIDE 2', 'ISI INSIDE 2', 'http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png', 'Inside', '2017-05-14'),
	(7, 'TEST HAPPENING 3', 'ISI HAPPENING 3', 'http://bolindonews.com/wp-content/uploads/2016/02/logoarema_bolindo.png', 'Happening', '2017-05-14');
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;

-- Dumping structure for table kalampoki.det_magz
CREATE TABLE IF NOT EXISTS `det_magz` (
  `kd_det` int(10) NOT NULL AUTO_INCREMENT,
  `kd_magz` int(10) NOT NULL,
  `foto` longtext NOT NULL,
  PRIMARY KEY (`kd_det`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Dumping data for table kalampoki.det_magz: 16 rows
/*!40000 ALTER TABLE `det_magz` DISABLE KEYS */;
INSERT INTO `det_magz` (`kd_det`, `kd_magz`, `foto`) VALUES
	(1, 1, 'http://10.212.86.94/server-kalampoki/assets/1.jpg'),
	(2, 1, 'http://192.168.1.100/server-kalampoki/assets/2.jpg'),
	(3, 1, 'http://192.168.1.100/server-kalampoki/assets/3.jpg'),
	(4, 1, 'http://192.168.1.100/server-kalampoki/assets/4.jpg'),
	(14, 2, 'http://192.168.1.100/server-kalampoki/assets/5.jpg'),
	(5, 1, 'http://192.168.1.100/server-kalampoki/assets/5.jpg'),
	(6, 1, 'http://192.168.1.100/server-kalampoki/assets/6.jpg'),
	(7, 1, 'http://192.168.1.100/server-kalampoki/assets/7.jpg'),
	(12, 2, 'http://192.168.1.100/server-kalampoki/assets/3.jpg'),
	(8, 1, 'http://192.168.1.100/server-kalampoki/assets/8.jpg'),
	(9, 1, 'http://192.168.1.100/server-kalampoki/assets/9.jpg'),
	(15, 2, 'http://192.168.1.100/server-kalampoki/assets/6.jpg'),
	(13, 2, 'http://192.168.1.100/server-kalampoki/assets/4.jpg'),
	(11, 2, 'http://192.168.1.100/server-kalampoki/assets/2.jpg'),
	(10, 2, 'http://192.168.1.100/server-kalampoki/assets/1.jpg'),
	(16, 2, 'http://192.168.1.100/server-kalampoki/assets/7.jpg');
/*!40000 ALTER TABLE `det_magz` ENABLE KEYS */;

-- Dumping structure for table kalampoki.magz
CREATE TABLE IF NOT EXISTS `magz` (
  `kd_magz` int(10) NOT NULL AUTO_INCREMENT,
  `judul` varchar(100) NOT NULL,
  `foto_cover` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL,
  `insert_date` date NOT NULL,
  PRIMARY KEY (`kd_magz`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kalampoki.magz: 2 rows
/*!40000 ALTER TABLE `magz` DISABLE KEYS */;
INSERT INTO `magz` (`kd_magz`, `judul`, `foto_cover`, `deskripsi`, `insert_date`) VALUES
	(1, 'ENTAHLAH', 'http://192.168.1.100/server-kalampoki/assets/1.jpg', '123', '2017-04-29'),
	(2, 'DUA', 'http://192.168.1.100/server-kalampoki/assets/1.jpg', '123', '2017-05-31');
/*!40000 ALTER TABLE `magz` ENABLE KEYS */;

-- Dumping structure for table kalampoki.menu
CREATE TABLE IF NOT EXISTS `menu` (
  `kd_menu` int(10) NOT NULL AUTO_INCREMENT,
  `nama_menu` varchar(30) NOT NULL,
  `kategori` enum('food','baverage') NOT NULL,
  `deskripsi` text NOT NULL,
  `foto` varchar(100) NOT NULL,
  `harga` int(10) NOT NULL,
  PRIMARY KEY (`kd_menu`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table kalampoki.menu: 2 rows
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`kd_menu`, `nama_menu`, `kategori`, `deskripsi`, `foto`, `harga`) VALUES
	(1, 'Onde Onde', 'food', 'Wijennya Ganjil', 'SEK GORONG', 12000),
	(2, 'Pempek', 'baverage', 'Gak pake wijen', 'SEK GORONG', 15000);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;

-- Dumping structure for table kalampoki.m_order
CREATE TABLE IF NOT EXISTS `m_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meja` varchar(50) DEFAULT NULL,
  `tanggal` varchar(50) DEFAULT NULL,
  `atas_nama` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

-- Dumping data for table kalampoki.m_order: ~35 rows (approximately)
/*!40000 ALTER TABLE `m_order` DISABLE KEYS */;
INSERT INTO `m_order` (`id`, `meja`, `tanggal`, `atas_nama`) VALUES
	(1, '11', '17171717-0505-1414', 'beni'),
	(2, '11', '17171717-0505-1414', 'beni'),
	(3, '11', '17171717-0505-1414', 'beni'),
	(4, '11', '17171717-0505-1414', 'beni'),
	(5, '11', '2017201720172017-0505-1414', 'beni'),
	(6, '11', '2017-05-14', 'beni'),
	(7, '11', '2017-05-14', 'beni'),
	(8, '11', '2017-05-14', 'beni'),
	(9, '11', '2017-05-14', 'benis'),
	(10, '11', '2017-05-14', 'benis'),
	(11, '11', '2017-05-14', 'benis'),
	(12, '11', '2017-05-14', 'benis'),
	(13, '12', '2017-05-14', 'benis'),
	(14, '55', '2017-05-14', 'brni'),
	(15, '55', '2017-05-14', 'brni'),
	(16, '67', '2017-05-14', 'gvg'),
	(17, '67', '2017-05-14', 'gvg'),
	(18, '67', '2017-05-14', 'gvg'),
	(19, '67', '2017-05-14', 'gvg'),
	(20, '67', '2017-05-14', 'gvg'),
	(21, '99', '2017-05-14', 'eef'),
	(22, '99', '2017-05-14', 'eef'),
	(23, '99', '2017-05-14', 'eef'),
	(24, '99', '2017-05-14', 'eef'),
	(25, '99', '2017-05-14', 'eef'),
	(26, '99', '2017-05-14', 'eef'),
	(27, '99', '2017-05-14', 'eef'),
	(28, '99', '2017-05-14', 'eef'),
	(29, '67', '2017-05-14', 'tt'),
	(30, '12', '2017-05-27', 'beni'),
	(31, '09', '2017-05-31', 'bogem'),
	(32, '09', '2017-05-31', 'bogem'),
	(33, '9', '2017-05-31', 'rumor'),
	(34, '9', '2017-05-31', 'rumor'),
	(35, '7', '2017-05-31', 'yog');
/*!40000 ALTER TABLE `m_order` ENABLE KEYS */;

-- Dumping structure for table kalampoki.t_order
CREATE TABLE IF NOT EXISTS `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kd_order` int(11) DEFAULT NULL,
  `kd_menu` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `total_harga` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Dumping data for table kalampoki.t_order: ~12 rows (approximately)
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
INSERT INTO `t_order` (`id`, `kd_order`, `kd_menu`, `qty`, `total_harga`) VALUES
	(1, 31, 1, 1, 10000),
	(2, 14, 2, 2, 15000),
	(3, 14, 1, 1, 12000),
	(4, 16, 1, 2, 12000),
	(5, 21, 1, 1, 12000),
	(6, 29, 1, 5, 12000),
	(7, 30, 1, 3, 12000),
	(8, 31, 1, 5, 12000),
	(9, 31, 1, 0, 12000),
	(10, 33, 1, 2, 12000),
	(11, 35, 1, 6, 12000),
	(12, 35, 1, 2, 12000);
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;

-- Dumping structure for table kalampoki.user
CREATE TABLE IF NOT EXISTS `user` (
  `kd_user` int(10) NOT NULL AUTO_INCREMENT,
  `nama` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `level` enum('user','admin') NOT NULL,
  PRIMARY KEY (`kd_user`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table kalampoki.user: 0 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table kalampoki.value
CREATE TABLE IF NOT EXISTS `value` (
  `kd_value` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`kd_value`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- Dumping data for table kalampoki.value: 14 rows
/*!40000 ALTER TABLE `value` DISABLE KEYS */;
INSERT INTO `value` (`kd_value`, `name`, `value`) VALUES
	(1, 'shortDescription', 'TEST DESCRIPTION'),
	(2, 'contact', '123'),
	(3, 'location', '123,123123#131,123123'),
	(4, 'menu', 'Homes'),
	(5, 'menu', 'Inside'),
	(6, 'menu', 'Happening'),
	(7, 'menu', 'Magz'),
	(8, 'menu', 'Menu'),
	(9, 'menu', 'Contact'),
	(11, 'actionInside', 'getBlog.php/kategori=2'),
	(12, 'actonHappening', 'getBlog.php/kategori=1'),
	(13, 'actionMagz', 'getMagz.php'),
	(14, 'serverRoot', '/'),
	(15, 'vDb', '1');
/*!40000 ALTER TABLE `value` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
