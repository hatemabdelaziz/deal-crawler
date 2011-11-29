-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 29, 2011 at 06:59 ã
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dealsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE IF NOT EXISTS `cities` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` char(35) NOT NULL DEFAULT '',
  `CountryCode` char(3) NOT NULL DEFAULT '',
  `lat` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `lon` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `currency` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT 'EGP',
  PRIMARY KEY (`ID`),
  KEY `CountryCode` (`CountryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `cities`
--


-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE IF NOT EXISTS `countries` (
  `Code` char(3) NOT NULL DEFAULT '',
  `Name` char(52) NOT NULL DEFAULT '',
  PRIMARY KEY (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `countries`
--


-- --------------------------------------------------------

--
-- Table structure for table `deals`
--

CREATE TABLE IF NOT EXISTS `deals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) DEFAULT NULL,
  `language_id` int(10) unsigned NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `value` decimal(10,2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `saving` decimal(10,2) DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `views` int(11) NOT NULL DEFAULT '0',
  `best_deal` int(1) unsigned NOT NULL DEFAULT '0',
  `currency` varchar(20) NOT NULL DEFAULT 'EGP',
  PRIMARY KEY (`id`),
  KEY `language_id` (`language_id`),
  KEY `deals_ibfk_2` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `deals`
--


-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE IF NOT EXISTS `language` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `defualt_language` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `language`
--

INSERT INTO `language` (`id`, `name`, `defualt_language`) VALUES
(1, 'Arabic', 0),
(2, 'English', 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cities`
--
ALTER TABLE `cities`
  ADD CONSTRAINT `cities_ibfk_1` FOREIGN KEY (`CountryCode`) REFERENCES `countries` (`Code`);

--
-- Constraints for table `deals`
--
ALTER TABLE `deals`
  ADD CONSTRAINT `deals_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  ADD CONSTRAINT `deals_ibfk_2` FOREIGN KEY (`city_id`) REFERENCES `cities` (`ID`);
