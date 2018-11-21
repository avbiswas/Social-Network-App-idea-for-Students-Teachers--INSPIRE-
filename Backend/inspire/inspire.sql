-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 08, 2016 at 05:15 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `inspire`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `name`, `password`, `email`, `batch`) VALUES
(1, 'Avi', 'q', '', 0),
(2, 'yo', 'q', '', 0),
(3, 'n', 'p', 'm', 0),
(6, 'sud', 'w', 'fgh', 2012),
(7, 'sir', 'e', 'fhh', 2012);

-- --------------------------------------------------------

--
-- Table structure for table `articles`
--

CREATE TABLE IF NOT EXISTS `articles` (
  `articleID` int(10) NOT NULL AUTO_INCREMENT,
  `authorID` int(5) NOT NULL,
  `introText` varchar(100) NOT NULL,
  `fileName` varchar(150) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`articleID`),
  KEY `authorID` (`authorID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `articles`
--

INSERT INTO `articles` (`articleID`, `authorID`, `introText`, `fileName`, `date`) VALUES
(1, 1, 'ghh', '577a5390a63d7', '2016-07-04 00:00:00'),
(2, 1, 'ghh', '577a53f58c134', '2016-07-04 00:00:00'),
(3, 2, 'vbh', '577a55769ef38', '2016-07-04 00:00:00'),
(4, 2, 'vbh', '577a559b6e121', '2016-07-04 05:54:59'),
(5, 1, 'art', '577a6014618bf', '2016-07-04 06:39:40'),
(6, 1, 'hehe', '577b751925085', '2016-07-05 02:21:37'),
(7, 1, 'fgh', '577b76ded9a5f', '2016-07-05 02:29:10'),
(8, 1, 'fgh', '577b76e41dbe1', '2016-07-05 02:29:16'),
(9, 1, 'fgh', '577b76e5ec06e', '2016-07-05 02:29:17'),
(10, 7, 'tip', '577f2cedf11ea', '2016-07-08 10:02:45'),
(11, 1, 'hn', '57c6f31bd5df6', '2016-08-31 08:39:15');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `commentID` int(6) NOT NULL AUTO_INCREMENT,
  `userID` int(5) NOT NULL,
  `articleID` int(5) NOT NULL,
  `commentText` varchar(500) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`commentID`),
  KEY `userID` (`userID`),
  KEY `articleID` (`articleID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`commentID`, `userID`, `articleID`, `commentText`, `date`) VALUES
(1, 2, 1, 'ghjbf', '2016-07-04 00:00:00'),
(2, 1, 1, 'ghj', '2016-07-04 06:38:03'),
(3, 1, 10, 'fgvv', '2016-07-08 10:05:36');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `articles`
--
ALTER TABLE `articles`
  ADD CONSTRAINT `articles_ibfk_1` FOREIGN KEY (`authorID`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`articleID`) REFERENCES `articles` (`articleID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
