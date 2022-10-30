-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 14, 2022 at 09:46 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `soen387_school`
--
CREATE DATABASE IF NOT EXISTS `soen387_school` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `soen387_school`;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `courseCode` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `semester` varchar(255) NOT NULL,
  `room` varchar(255) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `days` varchar(255) NOT NULL,
  `time` time NOT NULL,
  `instructor` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`courseCode`, `title`, `semester`, `room`, `startDate`, `endDate`, `days`, `time`, `instructor`) VALUES
('COMP101', 'Intro to Computer Science', 'FALL-2022', 'H725', '2022-05-01', '2022-08-31', 'Friday', '18:00:00', 'Lisa E'),
('COMP232', 'Mathematics for Computer Science', 'FALL-2022', 'H640', '2022-10-09', '2022-12-31', 'Monday', '08:30:00', 'Jane A'),
('COMP233', 'Probability and Statistics for Computer Science', 'FALL-2022', 'H543', '2022-10-09', '2022-12-31', 'Monday', '19:30:10', 'Samuel J'),
('COMP248', 'Object‑Oriented Programming I ', 'FALL-2022', 'H843', '2022-10-09', '2022-12-31', 'Tuesday', '10:30:00', 'Richard J'),
('COMP326', 'Computer Architecture', 'FALL-2022', 'H925', '2022-10-09', '2022-12-31', 'Wednesday', '10:30:00', 'Richard J'),
('COMP333', 'Data Analytics', 'FALL-2022', 'H525', '2022-10-09', '2022-12-31', 'Wednesday', '13:00:00', 'Matthew R'),
('COMP335', 'Introduction to Theoretical Computer Science', 'FALL-2022', 'H605', '2022-10-09', '2022-12-31', 'Friday', '14:00:00', 'Allison D'),
('COMP346', 'Operating Systems', 'SUMMER2-2023', 'H125', '2023-06-01', '2023-08-31', 'Thursday', '17:00:00', 'Elisa K.'),
('COMP348', 'Principles of Programming Languages', 'SUMMER1-2023', 'H725', '2023-05-01', '2023-08-31', 'Friday', '18:00:00', 'Lisa E'),
('COMP352', 'Data Structures and Algorithms', 'SUMMER1-2023', 'H925', '2023-05-01', '2023-08-31', 'Thursday', '16:00:00', 'Rebecca MJ');

-- --------------------------------------------------------

--
-- Table structure for table `student_courses`
--

CREATE TABLE `student_courses` (
  `id` int(11) NOT NULL,
  `courseCode` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student_courses`
--

INSERT INTO `student_courses` (`id`, `courseCode`) VALUES
(4, 'COMP232'),
(4, 'COMP233'),
(4, 'COMP248'),
(4, 'COMP326'),
(4, 'COMP333'),
(4, 'COMP348'),
(4, 'COMP352'),
(6, 'COMP232'),
(6, 'COMP333');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `password` varchar(255) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `firstName`, `lastName`, `address`, `email`, `phoneNumber`, `dateOfBirth`, `password`, `isAdmin`) VALUES
(1, 'john', 'smith', '123 home drive', 'jsmith@gmail.com', '111-111-1111', '1980-01-04', 'john', 1),
(2, 'sam', 'night', '345 yellow street', 'samnight@gmail.com', '222-222-2222', '1985-01-04', 'sam', 1),
(3, 'jane', 'doe', '999 green street', 'janedoe@gmail.com', '333-333-3333', '1999-08-08', 'jane', 1),
(4, 'alex', 'smith', '666 red street', 'alexsmith@gmail.com', '444-444-4444', '2000-03-03', 'alex', 0),
(5, 'kimberly', 'rae', '888 purple street', 'kimberlyrae@gmail.com', '555-555-5555', '2001-06-06', 'kimberly', 0),
(6, 'jackson', 'willow', '888 purple street', 'jacksonwillow@gmail.com', '555-555-5555', '2002-07-07', 'jackson', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`courseCode`);

--
-- Indexes for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD PRIMARY KEY (`id`,`courseCode`),
  ADD KEY `student_courses_courseCode_FK` (`courseCode`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `student_courses`
--
ALTER TABLE `student_courses`
  ADD CONSTRAINT `student_courses_courseCode_FK` FOREIGN KEY (`courseCode`) REFERENCES `courses` (`courseCode`) ON UPDATE CASCADE,
  ADD CONSTRAINT `student_courses_id_FK` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
