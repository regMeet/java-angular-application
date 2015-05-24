SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Angular
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Angular` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Angular` ;

-- -----------------------------------------------------
-- Table `Angular`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Angular`.`Users` (
  idUser INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  telephone VARCHAR(255) NOT NULL,
  mobile VARCHAR(255) NOT NULL,
  PRIMARY KEY(idUser)
);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

