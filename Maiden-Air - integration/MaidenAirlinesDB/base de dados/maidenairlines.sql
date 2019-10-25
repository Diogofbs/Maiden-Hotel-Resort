
CREATE TABLE `airplane` (
  `id` int(11) NOT NULL,
  `model` varchar(100) NOT NULL,
  `cargo_hold_capacity` int(11) NOT NULL,
  `number_seats` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `airport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `short_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `airport_un` (`short_name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
CREATE TABLE `booking_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `hand_baggage` tinyint(1) NOT NULL,
  `checked_baggage` tinyint(1) NOT NULL,
  `change_date` tinyint(1) NOT NULL,
  `cancel_booking` tinyint(1) NOT NULL,
  `cost` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `booking_type_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
CREATE TABLE `client_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `annual_fee` float NOT NULL,
  `monthly_miles` int(11) NOT NULL,
  `welcome_bonus` int(11) NOT NULL,
  `bonus_miles` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_type_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
CREATE TABLE `passenger` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(100) NOT NULL,
  `vat` int(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `contact_number` int(11) NOT NULL,
  `contact_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
CREATE TABLE `backoffice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `id_role` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `backoffice_un` (`username`),
  KEY `backoffice_fk` (`id_role`),
  CONSTRAINT `backoffice_fk` FOREIGN KEY (`id_role`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
CREATE TABLE `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Date` datetime NOT NULL,
  `id_booking_type` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `booking_fk` (`id_booking_type`),
  CONSTRAINT `booking_fk` FOREIGN KEY (`id_booking_type`) REFERENCES `booking_type` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(100) NOT NULL,
  `Id_Number` int(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `contact_number` int(11) NOT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `number_miles` varchar(100) NOT NULL,
  `id_type_client` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_un` (`Id_Number`),
  UNIQUE KEY `client_un1` (`email`),
  KEY `client_fk` (`id_type_client`),
  CONSTRAINT `client_fk` FOREIGN KEY (`id_type_client`) REFERENCES `client_type` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
CREATE TABLE `flight` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `departure_date` datetime NOT NULL,
  `arrival_date` datetime NOT NULL,
  `departure_airport` int(11) NOT NULL,
  `arrival_airport` int(11) NOT NULL,
  `id_airplane` int(11) NOT NULL,
  `flight_number` varchar(100) NOT NULL,
  `gate` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `flight_un` (`departure_date`,`departure_airport`,`arrival_airport`,`flight_number`),
  KEY `flight_fk_1` (`departure_airport`),
  KEY `flight_fk_2` (`arrival_airport`),
  KEY `flight_fk` (`id_airplane`),
  CONSTRAINT `flight_fk` FOREIGN KEY (`id_airplane`) REFERENCES `airplane` (`id`) ON DELETE CASCADE,
  CONSTRAINT `flight_fk_1` FOREIGN KEY (`departure_airport`) REFERENCES `airport` (`id`) ON DELETE CASCADE,
  CONSTRAINT `flight_fk_2` FOREIGN KEY (`arrival_airport`) REFERENCES `airport` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
CREATE TABLE `booking_client_flight` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_booking` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `id_flight` int(11) NOT NULL,
  `seat` varchar(100) DEFAULT NULL,
  `check_in` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `booking_client_flight_fk` (`id_booking`),
  KEY `booking_client_flight_fk_1` (`id_client`),
  KEY `booking_client_flight_fk_2` (`id_flight`),
  CONSTRAINT `booking_client_flight_fk` FOREIGN KEY (`id_booking`) REFERENCES `booking` (`id`) ON DELETE CASCADE,
  CONSTRAINT `booking_client_flight_fk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE,
  CONSTRAINT `booking_client_flight_fk_2` FOREIGN KEY (`id_flight`) REFERENCES `flight` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;