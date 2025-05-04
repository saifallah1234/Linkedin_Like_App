-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 25, 2024 at 01:10 PM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `khademadb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `user_id` bigint NOT NULL,
  `admin_level` int NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  `typecontent` varchar(255) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `comment_reaction`
--

DROP TABLE IF EXISTS `comment_reaction`;
CREATE TABLE IF NOT EXISTS `comment_reaction` (
  `comment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `reactiontype` varchar(255) NOT NULL,
  `creationdate` date NOT NULL,
  PRIMARY KEY (`comment_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `user_id` bigint NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `industry` varchar(255) NOT NULL,
  `website` varchar(255) NOT NULL,
  `company_size` int NOT NULL,
  `speciality` varchar(225) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `competance`
--

DROP TABLE IF EXISTS `competance`;
CREATE TABLE IF NOT EXISTS `competance` (
  `competance_id` bigint NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) NOT NULL,
  `technologie` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `niveau` int NOT NULL,
  PRIMARY KEY (`competance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `competance`
--

INSERT INTO `competance` (`competance_id`, `titre`, `technologie`, `description`, `niveau`) VALUES
(1, 'sdsss', 'dsads', 'dsads', 6);

-- --------------------------------------------------------

--
-- Table structure for table `contact_info`
--

DROP TABLE IF EXISTS `contact_info`;
CREATE TABLE IF NOT EXISTS `contact_info` (
  `contact_info_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `phone number` int DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`contact_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contact_info`
--

INSERT INTO `contact_info` (`contact_info_id`, `email`, `phone number`, `address`) VALUES
(2, 'company@gmail.com', 0, NULL),
(3, 'company@gmail.com', 0, NULL),
(4, 'company@gmail.com', 0, NULL),
(5, 'company@gmail.com', 0, NULL),
(6, 'company@gmail.com', 0, NULL),
(7, 'company@gmail.com', 0, NULL),
(8, 'company@gmail.com', 0, NULL),
(9, 'company@gmail.com', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `experience`
--

DROP TABLE IF EXISTS `experience`;
CREATE TABLE IF NOT EXISTS `experience` (
  `experience_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `mission` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `technologie` varchar(255) NOT NULL,
  PRIMARY KEY (`experience_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `experience`
--

INSERT INTO `experience` (`experience_id`, `description`, `mission`, `type`, `technologie`) VALUES
(1, 'sd', 'sds', 'dsasd', 'sad');

-- --------------------------------------------------------

--
-- Table structure for table `followers`
--

DROP TABLE IF EXISTS `followers`;
CREATE TABLE IF NOT EXISTS `followers` (
  `follower_id` bigint NOT NULL,
  `followed_id` bigint NOT NULL,
  PRIMARY KEY (`follower_id`,`followed_id`),
  KEY `followed_id` (`followed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
CREATE TABLE IF NOT EXISTS `images` (
  `post_id` bigint NOT NULL,
  `image` longblob NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `job_offers`
--

DROP TABLE IF EXISTS `job_offers`;
CREATE TABLE IF NOT EXISTS `job_offers` (
  `offer_id` bigint NOT NULL AUTO_INCREMENT,
  `company_id` bigint NOT NULL,
  `position` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `pay_range` double NOT NULL,
  `summary` varchar(255) NOT NULL,
  `employment_type` varchar(255) NOT NULL,
  PRIMARY KEY (`offer_id`),
  KEY `company_id` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `job_request`
--

DROP TABLE IF EXISTS `job_request`;
CREATE TABLE IF NOT EXISTS `job_request` (
  `offer_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`offer_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
CREATE TABLE IF NOT EXISTS `messages` (
  `message_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `sender_id` bigint NOT NULL,
  `creation_date` date NOT NULL,
  `parent_message_id` bigint NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `sender_id` (`sender_id`),
  KEY `parent_message_id` (`parent_message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message_receiver`
--

DROP TABLE IF EXISTS `message_receiver`;
CREATE TABLE IF NOT EXISTS `message_receiver` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `message_id` bigint NOT NULL,
  `is_read` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `user_id` bigint NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `job` varchar(50) NOT NULL,
  `sexe` varchar(50) NOT NULL,
  `age` int NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `type` varchar(255) NOT NULL,
  `creationdate` datetime NOT NULL,
  `content` varchar(255) NOT NULL,
  `post_parent` bigint NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`post_id`, `user_id`, `type`, `creationdate`, `content`, `post_parent`) VALUES
(1, 2, 'text', '2024-03-21 12:41:34', 'testing post creation', 0),
(2, 2, 'text', '2024-03-21 00:00:00', 'slmmm', 0),
(3, 2, 'text', '2024-03-21 00:00:00', 't', 0),
(4, 2, 'text', '2024-03-21 00:00:00', 'slm', 0),
(5, 2, 'text', '2024-03-21 00:00:00', '.setStyle(\"-fx-text-fill: red;\");', 0),
(6, 2, 'text', '2024-03-21 00:00:00', 'd', 0),
(7, 2, 'text', '2024-03-21 00:00:00', 'dsss', 0),
(8, 2, 'text', '2024-03-21 00:00:00', 's', 0),
(9, 2, 'text', '2024-03-21 00:00:00', 'sdsad', 0),
(10, 2, 'text', '2024-03-21 00:00:00', 'ds', 0),
(11, 2, 'text', '2024-03-21 00:00:00', 'ds', 0),
(12, 2, 'text', '2024-03-21 00:00:00', 'vd', 0),
(13, 2, 'text', '2024-03-21 00:00:00', 'ds', 0),
(14, 2, 'text', '2024-03-21 00:00:00', 'ds', 0),
(15, 2, 'text', '2024-03-21 00:00:00', 'dd', 0),
(16, 2, 'text', '2024-03-21 00:00:00', 'dd', 0),
(17, 2, 'text', '2024-03-21 00:00:00', 'dd', 0),
(18, 2, 'text', '2024-03-21 00:00:00', 'ddd', 0),
(19, 2, 'text', '2024-03-21 00:00:00', 'ds', 0),
(20, 2, 'text', '2024-03-21 00:00:00', 'test', 0),
(21, 2, 'text', '2024-03-21 00:00:00', 'testt', 0),
(22, 2, 'text', '2024-03-21 18:02:48', 'testtt', 0),
(23, 2, 'text', '2024-03-21 22:39:22', 't', 0),
(24, 2, 'text', '2024-03-21 22:39:23', 't', 0),
(25, 2, 'text', '2024-03-21 23:05:26', 'testing last time', 0),
(26, 2, 'text', '2024-03-23 15:35:05', 'test', 0),
(27, 2, 'text', '2024-03-23 15:36:53', 'test', 0),
(28, 2, 'text', '2024-03-23 15:37:58', 'test', 0),
(29, 2, 'text', '2024-03-23 15:42:29', 't', 0),
(30, 2, 'text', '2024-03-23 15:43:33', 't', 0),
(31, 2, 'text', '2024-03-23 15:46:30', 't', 0),
(32, 2, 'text', '2024-03-23 15:47:21', 't', 0),
(33, 2, 'text', '2024-03-23 15:48:41', 'ts', 0),
(34, 2, 'text', '2024-03-23 15:50:36', 'ts', 0),
(35, 2, 'text', '2024-03-23 15:54:05', 'ts', 0),
(36, 2, 'text', '2024-03-23 15:54:16', 'tss', 0),
(37, 2, 'text', '2024-03-23 15:54:44', 'tss', 0),
(38, 2, 'text', '2024-03-23 15:57:58', 'test', 0),
(39, 2, 'text', '2024-03-23 16:01:12', 'test', 0),
(40, 2, 'text', '2024-03-23 16:07:10', 'slm how are you ?', 0),
(41, 2, 'text', '2024-03-23 16:09:25', 'e5er taswira nhabetha', 0),
(42, 2, 'text', '2024-03-23 16:39:18', 'teting quality', 0),
(43, 2, 'text', '2024-03-23 16:39:50', 'test', 0),
(44, 2, 'text', '2024-03-23 17:19:40', 'ez pz', 0),
(45, 2, 'text', '2024-03-23 17:23:03', 'test', 0);

-- --------------------------------------------------------

--
-- Table structure for table `post_reaction`
--

DROP TABLE IF EXISTS `post_reaction`;
CREATE TABLE IF NOT EXISTS `post_reaction` (
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `reactiontype` varchar(255) NOT NULL,
  `creationdate` date NOT NULL,
  PRIMARY KEY (`post_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `post_reaction`
--

INSERT INTO `post_reaction` (`post_id`, `user_id`, `reactiontype`, `creationdate`) VALUES
(2, 2, 'like', '2024-03-21'),
(6, 2, 'like', '2024-03-21'),
(22, 2, 'like', '2024-03-21'),
(23, 2, 'like', '2024-03-21'),
(25, 2, 'like', '2024-03-21');

-- --------------------------------------------------------

--
-- Table structure for table `saved_jobs`
--

DROP TABLE IF EXISTS `saved_jobs`;
CREATE TABLE IF NOT EXISTS `saved_jobs` (
  `offer_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`offer_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `password_encrypted` varchar(255) NOT NULL,
  `contact_info_id` bigint NOT NULL,
  `username` varchar(255) NOT NULL,
  `creationdate` date NOT NULL,
  `last_login` date NOT NULL,
  `banned` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `contact_info_id` (`contact_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `password_encrypted`, `contact_info_id`, `username`, `creationdate`, `last_login`, `banned`, `is_active`, `photo`) VALUES
(2, 'f59d8cebcfd52b4a890876dd185200969743a57432ff39c0812a70b979804b9a', 9, 'company', '2024-02-25', '2024-03-19', 0, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_competance_details`
--

DROP TABLE IF EXISTS `user_competance_details`;
CREATE TABLE IF NOT EXISTS `user_competance_details` (
  `user_id` bigint NOT NULL,
  `competance_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`competance_id`),
  KEY `competance_id` (`competance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_experience`
--

DROP TABLE IF EXISTS `user_experience`;
CREATE TABLE IF NOT EXISTS `user_experience` (
  `experience_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`experience_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

DROP TABLE IF EXISTS `videos`;
CREATE TABLE IF NOT EXISTS `videos` (
  `post_id` bigint NOT NULL,
  `video` longblob NOT NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `comment_reaction`
--
ALTER TABLE `comment_reaction`
  ADD CONSTRAINT `comment_reaction_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_reaction_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `company`
--
ALTER TABLE `company`
  ADD CONSTRAINT `company_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `followers`
--
ALTER TABLE `followers`
  ADD CONSTRAINT `followers_ibfk_1` FOREIGN KEY (`follower_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `followers_ibfk_2` FOREIGN KEY (`followed_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `images_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `job_offers`
--
ALTER TABLE `job_offers`
  ADD CONSTRAINT `job_offers_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `job_request`
--
ALTER TABLE `job_request`
  ADD CONSTRAINT `job_request_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `job_offers` (`offer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `job_request_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `person` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`parent_message_id`) REFERENCES `messages` (`message_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `message_receiver`
--
ALTER TABLE `message_receiver`
  ADD CONSTRAINT `message_receiver_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `person_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `post_reaction`
--
ALTER TABLE `post_reaction`
  ADD CONSTRAINT `post_reaction_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `post_reaction_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `saved_jobs`
--
ALTER TABLE `saved_jobs`
  ADD CONSTRAINT `saved_jobs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `person` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `saved_jobs_ibfk_2` FOREIGN KEY (`offer_id`) REFERENCES `job_offers` (`offer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`contact_info_id`) REFERENCES `contact_info` (`contact_info_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_competance_details`
--
ALTER TABLE `user_competance_details`
  ADD CONSTRAINT `user_competance_details_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `person` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_competance_details_ibfk_5` FOREIGN KEY (`competance_id`) REFERENCES `competance` (`competance_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_experience`
--
ALTER TABLE `user_experience`
  ADD CONSTRAINT `user_experience_ibfk_1` FOREIGN KEY (`experience_id`) REFERENCES `experience` (`experience_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_experience_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `videos`
--
ALTER TABLE `videos`
  ADD CONSTRAINT `videos_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
