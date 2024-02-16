-- Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- ----------------------------------------------------
-- Schema MindHarborDB
-- ----------------------------------------------------
DROP SCHEMA IF EXISTS `mindharbordb`;

-- ----------------------------------------------------
-- Schema MindHarborDB
--
-- ISPW 2023/2024 Database
-- ----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mindharbordb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `mindharbordb`;


-- ----------------------------------------------------
-- Tabella Utente
-- ----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mindharbordb`.`Utente`(
    `Username` VARCHAR(45) NOT NULL,
    `Password` CHAR(10) NOT NULL,
    `Nome` VARCHAR(45) NOT NULL,
    `Cognome` VARCHAR(45) NOT NULL,
    `Ruolo` ENUM('Paziente','Psicologo') NOT NULL,
    PRIMARY KEY(`Username`))
ENGINE=InnoDB;

-- ----------------------------------------------------
-- Tabella Paziente
-- ----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mindharbordb`.`Paziente`(
    `Username` VARCHAR(45) NOT NULL,
    `Età` INT NOT NULL,
    `CodiceFiscale` VARCHAR(16) NOT NULL,
    PRIMARY KEY(`Username`),
    CONSTRAINT `UtentePaziente`
    FOREIGN KEY(`Username`)
        REFERENCES `mindharbordb`.`Utente`(`Username`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION)
ENGINE=InnoDB;


-- ----------------------------------------------------
-- Tabella Psicologo
-- ----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mindharbordb`.`Psicologo`(
    `Username` VARCHAR(45) NOT NULL,
    `CostoOrario` INT NOT NULL,
    `NomeStudio` VARCHAR(45) NOT NULL,
    PRIMARY KEY(`Username`),
    CONSTRAINT `UtentePaziente`
    FOREIGN KEY(`Username`)
        REFERENCES `mindharbordb`.`Utente`(`Username`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION)
ENGINE=InnoDB;



-- -----------------------------------------------------
-- Qui dovranno esserci le altre tabelle come ad esempio
-- Appuntamento
-- Prescrizione
-- e qualunque altra cosa serva manterere nel DB
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Data for table `SyncroNotesDB`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `mindharbordb`;
INSERT INTO `mindharbordb`.`Utente` (`Username`, `Password`, `Nome`, `Cognome`, `Ruolo`) VALUES ('Gino', 'a@a.it', 'Pa', 'Ziente','Paziente');
INSERT INTO `mindharbordb`.`Utente` (`Username`, `Password`, `Nome`, `Cognome`, `Ruolo`) VALUES ('Corsivo', 'd@d.en', 'Davide', 'Falessi','Paziente');
INSERT INTO `mindharbordb`.`Utente` (`Username`, `Password`, `Nome`, `Cognome`, `Ruolo`) VALUES ('Mister', 'd2@d.it', 'Guglielmo','De Angelis','Psicologo');
INSERT INTO `mindharbordb`.`Utente` (`Username`, `Password`, `Nome`, `Cognome`, `Ruolo`) VALUES ('Boh', 'g@g.it', 'Chat','GPT','Psicologo');

COMMIT;
