-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Nov 05, 2022 at 02:30 AM
-- Server version: 5.7.30
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `soen387_school`
--

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
  `days` varchar(150) DEFAULT NULL,
  `time` time NOT NULL,
  `instructor` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`courseCode`, `title`, `semester`, `room`, `startDate`, `endDate`, `days`, `time`, `instructor`) VALUES
('comp090', 'Software imaging', 'FALL-2022', 'h222', '2022-10-09', '2022-12-31', 'Tuesday', '12:03:00', 'gg'),
('comp111', 'Business logic', 'SUMMER2-2023', 'h666', '2023-06-01', '2023-08-31', 'Wednesday', '12:22:00', 'bb'),
('comp123', 'testing QA', 'SUMMER1-2023', 'h555', '2023-05-01', '2023-08-31', 'Tuesday', '12:22:00', 'me'),
('comp222', 'Data Gateway', 'SUMMER2-2023', 'h999', '2023-06-01', '2023-08-31', 'Monday', '12:22:00', 'ff'),
('COMP232', 'Mathematics for Computer Science', 'WINTER-2023', 'H640', '2023-01-05', '2023-04-30', '20', '08:30:00', 'Jane A'),
('COMP233', 'Probability and Statistics for Computer Science', 'WINTER-2023', 'H543', '2023-01-05', '2023-04-30', '32', '19:30:10', 'Samuel J'),
('COMP248', 'Object‑Oriented Programming I ', 'WINTER-2023', 'H843', '2023-01-05', '2023-04-30', '42', '10:30:00', 'Richard J'),
('COMP333', 'Data Analytics', 'WINTER-2023', 'H525', '2023-01-05', '2023-04-30', '45', '13:00:00', 'Matthew R'),
('COMP335', 'Introduction to Theoretical Computer Science', 'WINTER-2023', 'H605', '2023-01-05', '2023-04-30', '12', '14:00:00', 'Allison D'),
('COMP345', 'Advanced Programming', 'WINTER-2023', 'h938', '2023-01-05', '2023-04-30', 'Monday', '12:22:00', 'hassan'),
('COMP890', 'Information Test', 'WINTER-2023', 'H333', '2023-01-05', '2023-04-30', '20', '08:30:10', 'Matt'),
('COMP998', 'Software analysis', 'FALL-2022', 'h543', '2022-10-09', '2022-12-31', 'Tuesday', '12:33:00', 'Hassa'),
('COMP999', 'Data Validation', 'FALL-2022', 'H444', '2022-10-09', '2022-12-31', 'Thursday', '12:22:00', 'Hassan');

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
(4, 'COMP090'),
(5, 'COMP111'),
(5, 'COMP123'),
(4, 'COMP232'),
(4, 'COMP233'),
(5, 'COMP233'),
(6, 'COMP233'),
(6, 'COMP248'),
(4, 'COMP333'),
(5, 'COMP333'),
(6, 'COMP333'),
(4, 'COMP890'),
(4, 'COMP999');

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

