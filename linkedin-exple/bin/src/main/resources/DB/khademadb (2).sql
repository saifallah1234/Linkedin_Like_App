-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 13, 2024 at 07:25 PM
-- Server version: 5.5.20
-- PHP Version: 5.3.9
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */
;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */
;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */
;
/*!40101 SET NAMES utf8 */
;
--
-- Database: `khademadb`
--
-- --------------------------------------------------------
--
-- Table structure for table `comments`
--
CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 13;
-- --------------------------------------------------------
--
-- Table structure for table `comment_reaction`
--
CREATE TABLE IF NOT EXISTS `comment_reaction` (
  `comment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `reactiontype` varchar(255) NOT NULL,
  PRIMARY KEY (`comment_id`, `user_id`),
  KEY `user_id` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;
-- --------------------------------------------------------
--
-- Table structure for table `competance`
--
CREATE TABLE IF NOT EXISTS `competance` (
  `competance_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) NOT NULL,
  `technologie` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `niveau` varchar(255) NOT NULL,
  PRIMARY KEY (`competance_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 1;
-- --------------------------------------------------------
--
-- Table structure for table `contact_info`
--
CREATE TABLE IF NOT EXISTS `contact_info` (
  `contact_info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `phone number` int(11) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`contact_info_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 1;
-- --------------------------------------------------------
--
-- Table structure for table `experience`
--
CREATE TABLE IF NOT EXISTS `experience` (
  `experience_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `mission` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `technologie` varchar(255) NOT NULL,
  PRIMARY KEY (`experience_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 1;
-- --------------------------------------------------------
--
-- Table structure for table `followers`
--
CREATE TABLE IF NOT EXISTS `followers` (
  `follower_id` bigint(20) NOT NULL,
  `followed_id` bigint(20) NOT NULL,
  PRIMARY KEY (`follower_id`, `followed_id`),
  KEY `followed_id` (`followed_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;
-- --------------------------------------------------------
--
-- Table structure for table `messages`
--
CREATE TABLE IF NOT EXISTS `messages` (
  `message_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `sender_id` bigint(20) NOT NULL,
  `creation_date` date NOT NULL,
  `parent_message_id` bigint(20) NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `sender_id` (`sender_id`),
  KEY `parent_message_id` (`parent_message_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 1;
-- --------------------------------------------------------
--
-- Table structure for table `message_receiver`
--
CREATE TABLE IF NOT EXISTS `message_receiver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `message_id` bigint(20) NOT NULL,
  `is_read` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 1;
-- --------------------------------------------------------
--
-- Table structure for table `posts`
--
CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `creationdate` date NOT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `user_id` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 1;
-- --------------------------------------------------------
--
-- Table structure for table `post_reaction`
--
CREATE TABLE IF NOT EXISTS `post_reaction` (
  `post_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `reactiontype` varchar(255) NOT NULL,
  PRIMARY KEY (`post_id`, `user_id`),
  KEY `user_id` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;
-- --------------------------------------------------------
--
-- Table structure for table `user`
--
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password_encrypted` varchar(255) NOT NULL,
  `contact_info_id` bigint(20) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `creationdate` date NOT NULL,
  `last_login` date NOT NULL,
  `banned` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `contact_info_id` (`contact_info_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1 AUTO_INCREMENT = 1;
-- --------------------------------------------------------
--
-- Table structure for table `user_experience`
--
CREATE TABLE IF NOT EXISTS `user_experience` (
  `experience_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`experience_id`, `user_id`),
  KEY `user_id` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;
-- --------------------------------------------------------
--
-- Table structure for table `user_incompetance_details`
--
CREATE TABLE IF NOT EXISTS `user_incompetance_details` (
  `user_id` bigint(20) NOT NULL,
  `competance_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `competance_id`),
  KEY `competance_id` (`competance_id`)
) ENGINE = InnoDB DEFAULT CHARSET = latin1;
--
-- Constraints for dumped tables
--
--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `comment_reaction`
--
ALTER TABLE `comment_reaction`
ADD CONSTRAINT `comment_reaction_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_reaction_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `followers`
--
ALTER TABLE `followers`
ADD CONSTRAINT `followers_ibfk_2` FOREIGN KEY (`followed_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `followers_ibfk_1` FOREIGN KEY (`follower_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`parent_message_id`) REFERENCES `messages` (`message_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `message_receiver`
--
ALTER TABLE `message_receiver`
ADD CONSTRAINT `message_receiver_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `post_reaction`
--
ALTER TABLE `post_reaction`
ADD CONSTRAINT `post_reaction_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `post_reaction_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `user`
--
ALTER TABLE `user`
ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`contact_info_id`) REFERENCES `contact_info` (`contact_info_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `user_experience`
--
ALTER TABLE `user_experience`
ADD CONSTRAINT `user_experience_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_experience_ibfk_1` FOREIGN KEY (`experience_id`) REFERENCES `experience` (`experience_id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Constraints for table `user_incompetance_details`
--
ALTER TABLE `user_incompetance_details`
ADD CONSTRAINT `user_incompetance_details_ibfk_2` FOREIGN KEY (`competance_id`) REFERENCES `competance` (`competance_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_incompetance_details_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */
;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */
;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */
;