SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sample
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema demo
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema movers-usermang
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `movers-usermang` ;
CREATE SCHEMA IF NOT EXISTS `movers-usermang` DEFAULT CHARACTER SET utf8 ;
USE `movers-usermang` ;

-- -----------------------------------------------------
-- Table `movers-usermang`.`User_Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movers-usermang`.`User_Role` ;

CREATE TABLE IF NOT EXISTS `movers-usermang`.`User_Role` (
  `RoleID` INT(11) NOT NULL AUTO_INCREMENT,
  `RoleName` VARCHAR(45) NOT NULL,
  `Active` TINYINT(1) NULL DEFAULT 1,
  `CreatedDate` DATE NULL,
  PRIMARY KEY (`RoleID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `movers-usermang`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movers-usermang`.`Users` ;

CREATE TABLE IF NOT EXISTS `movers-usermang`.`Users` (
  `UserID` INT(11) NOT NULL AUTO_INCREMENT,
  `Password` VARCHAR(255) NOT NULL,
  `Email` VARCHAR(255) NOT NULL,
  `Active` TINYINT(1) NOT NULL DEFAULT 1,
  `LastLogin` DATETIME NULL,
  `CreatedDate` DATE NULL,
  PRIMARY KEY (`UserID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `movers-usermang`.`User_has_User_Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movers-usermang`.`User_has_User_Role` ;

CREATE TABLE IF NOT EXISTS `movers-usermang`.`User_has_User_Role` (
  `UserID` INT(11) NOT NULL,
  `RoleID` INT(11) NOT NULL,
  `CreatedByUserID` INT(11) NOT NULL,
  `CreatedDate` DATE NOT NULL,
  INDEX `FK_87547o8m6yoj4mv5t45kcwmwi` (`RoleID` ASC),
  INDEX `FK_sqwnap4hn4aj8uxmj5cjm98t2` (`UserID` ASC),
  PRIMARY KEY (`UserID`, `RoleID`),
  CONSTRAINT `FK_sqwnap4hn4aj8uxmj5cjm98t2`
    FOREIGN KEY (`UserID`)
    REFERENCES `movers-usermang`.`Users` (`UserID`),
  CONSTRAINT `FK_87547o8m6yoj4mv5t45kcwmwi`
    FOREIGN KEY (`RoleID`)
    REFERENCES `movers-usermang`.`User_Role` (`RoleID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
