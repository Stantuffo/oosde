-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ago 24, 2018 alle 11:57
-- Versione del server: 10.1.28-MariaDB
-- Versione PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oosde`
--
CREATE DATABASE IF NOT EXISTS `oosde` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `oosde`;

-- --------------------------------------------------------

--
-- Struttura della tabella `artwork`
--

DROP TABLE IF EXISTS `artwork`;
CREATE TABLE `artwork` (
  `art_id` int(11) NOT NULL,
  `isbn` char(13) DEFAULT NULL,
  `title` varchar(250) NOT NULL,
  `description` text,
  `language` char(3) NOT NULL,
  `year` smallint(4) DEFAULT NULL,
  `cat_id` int(11) NOT NULL,
  `added_by` int(11) DEFAULT NULL,
  `published` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `artwork`
--

INSERT INTO `artwork` (`art_id`, `isbn`, `title`, `description`, `language`, `year`, `cat_id`, `added_by`, `published`) VALUES
(1, '123stella', 'Il Signore degli Anelli - La Compagnia dell\'Anello', 'Descrizione del signore degli anelli - la compagnia dell\'anello. - Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'IT', 1954, 1, 1, 1),
(2, 'asdasd', 'Harry Potter', 'Descrizione Harry Potter -   Lorem ipsum dolor sit amet, consectetur adipiscing elit. ', 'IT', 2006, 1, 1, 0),
(3, 'isbn di prova', 'Opera mista', 'Descrizione opera mista (Alighieri, D\'andrea, Tolkien)', 'LAT', 2000, 3, 1, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `author`
--

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `auth_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `birth_place` varchar(200) DEFAULT NULL,
  `birth_country` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `author`
--

INSERT INTO `author` (`auth_id`, `name`, `surname`, `birth_date`, `birth_place`, `birth_country`) VALUES
(1, 'John Ronald Reuel', 'Tolkien', '1892-01-03', 'Bloemfontein', 'Sudafrica'),
(2, 'J.K.', 'Rowling', '1965-07-31', 'Yate', 'Inghilterra'),
(3, 'Dante', 'Alighieri', '1265-06-06', 'Firenze', 'Italia'),
(4, 'Niccolò', 'Ammaniti', '1966-09-25', 'Roma', 'Italia'),
(5, 'Sant\'Agostino', 'D\'Andrea', '2018-08-12', 'Sant\'Omero', 'Italia');

-- --------------------------------------------------------

--
-- Struttura della tabella `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cat_id` int(11) NOT NULL,
  `name` char(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `category`
--

INSERT INTO `category` (`cat_id`, `name`) VALUES
(1, 'Fantasy'),
(2, 'Narrativa'),
(3, 'Epica Cavalleresca'),
(4, 'Storico'),
(5, 'Romanzo'),
(6, 'Saggio');

-- --------------------------------------------------------

--
-- Struttura della tabella `image`
--

DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `img_id` int(11) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `artwork_id` int(11) NOT NULL,
  `transcription` text,
  `img_validated` smallint(1) NOT NULL DEFAULT '0',
  `tr_validated` smallint(6) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `image`
--

INSERT INTO `image` (`img_id`, `image_url`, `artwork_id`, `transcription`, `img_validated`, `tr_validated`) VALUES
(1, 'LOTR.jpg', 1, '<h1>Bilbo Baggings e le Java Exceptions</h1><p><em>C\'era una volta un giovane hobbitt che non sapeva ancora debuggare...</em></p>', 1, 1),
(2, 'lotr1.jpg', 1, '<h1><strong>Aggiungo qualcosa</strong></h1><p><strong>trascrizione della seconda pagina del signore degli anelli</strong> - Duis gravida ex at mauris mattis, in commodo arcu facilisis. Maecenas a nisi eros.</p><p>Donec elit libero, eleifend at elit eget, pharetra varius ligula. Maecenas lacus tellus, ultricies id magna a, dignissim posuere lectus. Phasellus sed imperdiet eros. Aliquam eu lorem sed nisi mattis dapibus. Phasellus rutrum vel velit a rhoncus. Mauris sit amet aliquet turpis, ac faucibus massa.</p><p>Donec congue condimentum mauris, id tempor nisl congue sit amet. Aliquam urna tellus, tincidunt nec nisi a, condimentum porttitor libero.</p><p>Nulla fermentum mauris urna, at vehicula tortor aliquet et. Quisque mi tellus, varius nec mi tempor, venenatis ornare nisl. Sed pretium erat cursus, hendrerit lectus id, tincidunt orci.</p><p>Donec sodales suscipit ligula a ullamcorper. Aliquam iaculis accumsan mauris, ultrices aliquet leo varius quis.</p><p>Suspendisse massa turpis, pharetra ut mi a, dictum tempor lacus. Nam dapibus velit vitae lectus cursus, at vulputate augue rhoncus. Maecenas neque sem, vulputate vel dui ac, cursus semper dolor.</p><p>Cras vulputate, orci eget fermentum commodo, orci urna auctor lacus, vel efficitur dolor lorem sed est.</p><p>Aliquam non arcu lacus. Nunc dignissim nisl ac magna luctus, id mollis nulla hendrerit. </p><p>Praesent iaculis vehicula augue, a vulputate lorem porta eu. Sed non accumsan leo, non finibus lacus. In velit dui, efficitur pretium dignissim sed, accumsan quis risus. Nullam vel finibus odio, non pretium nisl.</p><p>Quisque convallis, lectus in rhoncus aliquet, mauris sapien iaculis elit, non sollicitudin libero ligula placerat nisl. Fusce non luctus est, vel fermentum nulla.</p>', 1, 1),
(3, 'lotr2.jpg', 1, '<p><strong>trascrizione della terza pagina del signore degli anelli</strong> - Nunc eleifend, mi ut convallis hendrerit, libero velit feugiat tellus, at luctus metus nunc bibendum sem. Suspendisse quis ipsum dictum, condimentum ligula in, feugiat nunc. Aliquam quis lobortis arcu. Proin elementum nisl dolor, sed accumsan quam fringilla nec. Proin sem mauris, vulputate in tortor non, rhoncus laoreet ante. Integer et sapien nibh. Etiam bibendum, lorem et rhoncus pellentesque, ipsum massa auctor turpis, eu cursus enim velit congue nulla. Mauris scelerisque tempor ligula. Praesent dignissim neque risus, pellentesque malesuada risus interdum quis. Aliquam porta nulla sed tortor sagittis mollis. Proin rutrum vel metus non dignissim. Nam et elit id dolor dignissim venenatis eu at purus. Nam malesuada eu ligula nec vehicula. Vivamus interdum ligula nec augue luctus dapibus sit amet nec ante. Nunc fringilla metus sed mauris malesuada, quis pharetra nisi consequat. Morbi eleifend ipsum lacus, vitae ultrices est euismod sit amet. Pellentesque ligula mauris, efficitur a ipsum nec, vulputate mattis est. Nam consectetur, quam at vehicula congue, tellus urna tempus leo, at fermentum tortor est non nisi. Ut vitae lobortis urna, quis laoreet nibh. Aenean porttitor diam fermentum odio elementum consequat. Quisque eu laoreet ante. Sed mauris velit, pellentesque vel ex sit amet, maximus bibendum lorem. Vestibulum rhoncus placerat libero, quis aliquam sem. Sed pulvinar ultrices metus, in efficitur eros porta vel. Maecenas finibus ante in risus ultrices eleifend. Mauris eget porta nunc, at tempus neque.</p>', 1, 1),
(4, 'HP.jpeg', 2, '<h1>Harry Potter e le Java Exceptions</h1><p><em>C\'era una volta un giovane mago che non sapeva ancora debuggare...</em></p>', 1, 1),
(8, 'LOTR.jpg', 1, '<p><strong>trascrizione della quarta pagina del signore degli anelli</strong> - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi in luctus libero. Pellentesque sollicitudin sit amet neque ac dapibus. Donec at lacus a mauris consectetur pretium sit amet eu turpis. Morbi viverra placerat augue a commodo. Suspendisse sed dapibus mauris. Morbi placerat erat in dictum auctor. Pellentesque et diam ac ante aliquam tempus. Vestibulum maximus turpis et ornare faucibus. Sed dictum justo sed bibendum tempus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Interdum et malesuada fames ac ante ipsum primis in faucibus. Morbi faucibus lectus nisi, vel eleifend enim pretium id. Sed vestibulum neque eu luctus pellentesque. Aliquam mollis nulla ipsum, eu convallis ligula feugiat quis. Mauris sed mattis nisl, id facilisis nulla. In vel elit id mi posuere faucibus. Pellentesque sit amet commodo risus. Sed aliquam elit mi, ut pellentesque sem ullamcorper sed. Mauris placerat felis tellus. In suscipit risus dapibus massa pulvinar, non semper dolor eleifend. Aenean in ex vehicula, bibendum massa in, sodales nulla. Proin porttitor semper dolor, ut interdum ligula tincidunt ac. Duis erat mi, auctor vitae faucibus sit amet, mollis id mauris. In posuere orci et tortor blandit mollis. Vivamus molestie felis nibh, vitae accumsan justo congue nec. Cras nisl ipsum, eleifend sit amet sapien scelerisque, euismod convallis libero.</p>', 0, 1),
(9, 'blog3.jpg', 2, '<p>Ci scrivo qualcosa</p>', 1, 1),
(10, 'blog3.jpg', 3, NULL, 0, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `metatag`
--

DROP TABLE IF EXISTS `metatag`;
CREATE TABLE `metatag` (
  `tag_id` int(11) NOT NULL,
  `art_id` int(11) NOT NULL,
  `tag_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `paternity`
--

DROP TABLE IF EXISTS `paternity`;
CREATE TABLE `paternity` (
  `pat_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `artwork_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `paternity`
--

INSERT INTO `paternity` (`pat_id`, `author_id`, `artwork_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 2, 1),
(7, 3, 3),
(8, 5, 3),
(9, 1, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `usr_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` char(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `residence` varchar(50) NOT NULL,
  `qualification` varchar(50) NOT NULL,
  `profession` varchar(50) NOT NULL,
  `fiscal_code` varchar(16) NOT NULL,
  `transcriber` tinyint(1) DEFAULT NULL,
  `uploader` tinyint(1) DEFAULT NULL,
  `manager` tinyint(1) DEFAULT NULL,
  `administrator` tinyint(1) DEFAULT NULL,
  `request` tinyint(1) DEFAULT NULL,
  `downloader` tinyint(1) NOT NULL DEFAULT '0',
  `tr_level` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`usr_id`, `email`, `password`, `name`, `surname`, `birth_date`, `residence`, `qualification`, `profession`, `fiscal_code`, `transcriber`, `uploader`, `manager`, `administrator`, `request`, `downloader`, `tr_level`) VALUES
(1, 'admin', 'password', 'Simone', 'Bucciarelli', '1990-07-04', 'Spoltore', 'None', 'IT specialist to be', 'BCCSMN90L04G482O', 1, 1, 1, 1, 0, 1, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `write_assignment`
--

DROP TABLE IF EXISTS `write_assignment`;
CREATE TABLE `write_assignment` (
  `w_a_id` int(11) NOT NULL,
  `image_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `write_assignment`
--

INSERT INTO `write_assignment` (`w_a_id`, `image_id`, `user_id`) VALUES
(4, 1, 1),
(12, 4, 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `artwork`
--
ALTER TABLE `artwork`
  ADD PRIMARY KEY (`art_id`),
  ADD KEY `artwork_user_usr_id_fk` (`added_by`),
  ADD KEY `artwork_category_cat_id_fk` (`cat_id`);

--
-- Indici per le tabelle `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`auth_id`);

--
-- Indici per le tabelle `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cat_id`);

--
-- Indici per le tabelle `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`img_id`),
  ADD KEY `image_artwork_art_id_fk` (`artwork_id`);

--
-- Indici per le tabelle `metatag`
--
ALTER TABLE `metatag`
  ADD PRIMARY KEY (`tag_id`),
  ADD KEY `metatag_artwork_art_id_fk` (`art_id`);

--
-- Indici per le tabelle `paternity`
--
ALTER TABLE `paternity`
  ADD PRIMARY KEY (`pat_id`),
  ADD KEY `paternity_author_auth_id_fk` (`author_id`),
  ADD KEY `paternity_artwork_art_id_fk` (`artwork_id`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`usr_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `cf` (`fiscal_code`);

--
-- Indici per le tabelle `write_assignment`
--
ALTER TABLE `write_assignment`
  ADD PRIMARY KEY (`w_a_id`),
  ADD KEY `write_assignment_image_img_id_fk` (`image_id`),
  ADD KEY `write_assignment_user_usr_id_fk` (`user_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `artwork`
--
ALTER TABLE `artwork`
  MODIFY `art_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `author`
--
ALTER TABLE `author`
  MODIFY `auth_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `category`
--
ALTER TABLE `category`
  MODIFY `cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `image`
--
ALTER TABLE `image`
  MODIFY `img_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la tabella `paternity`
--
ALTER TABLE `paternity`
  MODIFY `pat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `usr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `write_assignment`
--
ALTER TABLE `write_assignment`
  MODIFY `w_a_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `artwork`
--
ALTER TABLE `artwork`
  ADD CONSTRAINT `artwork_category_cat_id_fk` FOREIGN KEY (`cat_id`) REFERENCES `category` (`cat_id`);

--
-- Limiti per la tabella `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `image_artwork_art_id_fk` FOREIGN KEY (`artwork_id`) REFERENCES `artwork` (`art_id`);

--
-- Limiti per la tabella `metatag`
--
ALTER TABLE `metatag`
  ADD CONSTRAINT `metatag_artwork_art_id_fk` FOREIGN KEY (`art_id`) REFERENCES `artwork` (`art_id`);

--
-- Limiti per la tabella `paternity`
--
ALTER TABLE `paternity`
  ADD CONSTRAINT `paternity_artwork_art_id_fk` FOREIGN KEY (`artwork_id`) REFERENCES `artwork` (`art_id`),
  ADD CONSTRAINT `paternity_author_auth_id_fk` FOREIGN KEY (`author_id`) REFERENCES `author` (`auth_id`);

--
-- Limiti per la tabella `write_assignment`
--
ALTER TABLE `write_assignment`
  ADD CONSTRAINT `write_assignment_image_img_id_fk` FOREIGN KEY (`image_id`) REFERENCES `image` (`img_id`),
  ADD CONSTRAINT `write_assignment_user_usr_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`usr_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
