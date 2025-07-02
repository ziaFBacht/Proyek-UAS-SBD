-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 02, 2025 at 11:46 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas_sbd`
--
CREATE DATABASE IF NOT EXISTS `uas_sbd` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `uas_sbd`;

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `delete_penjualan`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_penjualan` (IN `p_kd_trans` VARCHAR(10))   BEGIN
    DELETE FROM t_jual WHERE kd_trans = p_kd_trans;
END$$

DROP PROCEDURE IF EXISTS `delete_stok`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_stok` (IN `p_kode_brg` VARCHAR(10))   BEGIN
    DELETE FROM stok WHERE kode_brg = p_kode_brg;
END$$

DROP PROCEDURE IF EXISTS `insert_penjualan`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_penjualan` (IN `p_kd_trans` VARCHAR(10), IN `p_tgl_trans` DATE, IN `p_kode_brg` VARCHAR(10), IN `p_jml_jual` INT)   BEGIN
    INSERT INTO t_jual(kd_trans, tgl_trans, kode_brg, jml_jual)
    VALUES(p_kd_trans, p_tgl_trans, p_kode_brg, p_jml_jual);
END$$

DROP PROCEDURE IF EXISTS `insert_stok`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_stok` (IN `p_kode_brg` VARCHAR(10), IN `p_nama_brg` VARCHAR(50), IN `p_satuan` VARCHAR(20), IN `p_jml_stok` INT)   BEGIN
    INSERT INTO stok(kode_brg, nama_brg, satuan, jml_stok)
    VALUES(p_kode_brg, p_nama_brg, p_satuan, p_jml_stok);
END$$

DROP PROCEDURE IF EXISTS `update_penjualan`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_penjualan` (IN `p_kd_trans` VARCHAR(10), IN `p_tgl_trans` DATE, IN `p_kode_brg` VARCHAR(10), IN `p_jml_jual` INT)   BEGIN
    UPDATE t_jual
    SET tgl_trans = p_tgl_trans,
        kode_brg = p_kode_brg,
        jml_jual = p_jml_jual
    WHERE kd_trans = p_kd_trans;
END$$

DROP PROCEDURE IF EXISTS `update_stok`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_stok` (IN `p_kode_brg` VARCHAR(10), IN `p_nama_brg` VARCHAR(50), IN `p_satuan` VARCHAR(20), IN `p_jml_stok` INT)   BEGIN
    UPDATE stok
    SET nama_brg = p_nama_brg,
        satuan = p_satuan,
        jml_stok = p_jml_stok
    WHERE kode_brg = p_kode_brg;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `lock_status`
--

DROP TABLE IF EXISTS `lock_status`;
CREATE TABLE `lock_status` (
  `id` int(11) NOT NULL,
  `is_locked` tinyint(1) DEFAULT 0,
  `locked_by` varchar(50) DEFAULT NULL,
  `locked_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lock_status`
--

INSERT INTO `lock_status` (`id`, `is_locked`, `locked_by`, `locked_at`) VALUES
(1, 0, NULL, '2025-07-02 09:39:47'),
(2, 0, NULL, '2025-07-02 09:44:34');

-- --------------------------------------------------------

--
-- Table structure for table `stok`
--

DROP TABLE IF EXISTS `stok`;
CREATE TABLE `stok` (
  `kode_brg` varchar(10) NOT NULL,
  `nama_brg` varchar(50) DEFAULT NULL,
  `satuan` varchar(20) DEFAULT NULL,
  `jml_stok` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stok`
--

INSERT INTO `stok` (`kode_brg`, `nama_brg`, `satuan`, `jml_stok`) VALUES
('A11', 'Handphone', 'Box', 30),
('A12', 'Tablet', 'Box', 20),
('A13', 'Kursi', 'Palet', 6),
('A14', 'Deodorant', 'Box', 10);

-- --------------------------------------------------------

--
-- Table structure for table `t_jual`
--

DROP TABLE IF EXISTS `t_jual`;
CREATE TABLE `t_jual` (
  `kd_trans` varchar(10) NOT NULL,
  `tgl_trans` date DEFAULT NULL,
  `kode_brg` varchar(10) DEFAULT NULL,
  `jml_jual` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `t_jual`
--

INSERT INTO `t_jual` (`kd_trans`, `tgl_trans`, `kode_brg`, `jml_jual`) VALUES
('0112', '2025-07-01', 'A11', 1),
('0114', '2025-07-02', 'A13', 4);

--
-- Triggers `t_jual`
--
DROP TRIGGER IF EXISTS `trg_kurangi_stok_setelah_penjualan`;
DELIMITER $$
CREATE TRIGGER `trg_kurangi_stok_setelah_penjualan` AFTER INSERT ON `t_jual` FOR EACH ROW BEGIN
    UPDATE stok
    SET jml_stok = jml_stok - NEW.jml_jual
    WHERE kode_brg = NEW.kode_brg;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_tambah_stok_setelah_delete_penjualan`;
DELIMITER $$
CREATE TRIGGER `trg_tambah_stok_setelah_delete_penjualan` AFTER DELETE ON `t_jual` FOR EACH ROW BEGIN
    UPDATE stok
    SET jml_stok = jml_stok + OLD.jml_jual
    WHERE kode_brg = OLD.kode_brg;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_update_stok_setelah_update_penjualan`;
DELIMITER $$
CREATE TRIGGER `trg_update_stok_setelah_update_penjualan` AFTER UPDATE ON `t_jual` FOR EACH ROW BEGIN
    UPDATE stok
    SET jml_stok = jml_stok + OLD.jml_jual - NEW.jml_jual
    WHERE kode_brg = NEW.kode_brg;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `lock_status`
--
ALTER TABLE `lock_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stok`
--
ALTER TABLE `stok`
  ADD PRIMARY KEY (`kode_brg`);

--
-- Indexes for table `t_jual`
--
ALTER TABLE `t_jual`
  ADD PRIMARY KEY (`kd_trans`),
  ADD KEY `kode_brg` (`kode_brg`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `t_jual`
--
ALTER TABLE `t_jual`
  ADD CONSTRAINT `t_jual_ibfk_1` FOREIGN KEY (`kode_brg`) REFERENCES `stok` (`kode_brg`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
