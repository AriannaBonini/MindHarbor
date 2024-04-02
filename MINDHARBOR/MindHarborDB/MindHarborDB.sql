-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema mindharbordb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mindharbordb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mindharbordb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utente` (
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Categoria` ENUM('Paziente', 'Psicologo') NOT NULL,
  PRIMARY KEY (`Username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Psicologo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Psicologo` (
  `CostoOrario` INT NOT NULL,
  `Nome_Studio` VARCHAR(45) NOT NULL,
  `Psicologo_Username` VARCHAR(45) NOT NULL,
  INDEX `fk_Psicologo_Utente1_idx` (`Psicologo_Username` ASC) VISIBLE,
  PRIMARY KEY (`Psicologo_Username`),
  CONSTRAINT `fk_Psicologo_Utente1`
    FOREIGN KEY (`Psicologo_Username`)
    REFERENCES `mydb`.`Utente` (`Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Paziente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Paziente` (
  `Età` INT NOT NULL,
  `CodiceFiscale` VARCHAR(45) NOT NULL,
  `Diagnosi` VARCHAR(45) NULL,
  `Paziente_Username` VARCHAR(45) NOT NULL,
  `Psicologo_Username` VARCHAR(45) NULL,
  PRIMARY KEY (`Paziente_Username`),
  INDEX `fk_Paziente_Utente1_idx` (`Paziente_Username` ASC) VISIBLE,
  INDEX `fk_Paziente_Psicologo1_idx` (`Psicologo_Username` ASC) VISIBLE,
  CONSTRAINT `fk_Paziente_Utente1`
    FOREIGN KEY (`Paziente_Username`)
    REFERENCES `mydb`.`Utente` (`Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Paziente_Psicologo1`
    FOREIGN KEY (`Psicologo_Username`)
    REFERENCES `mydb`.`Psicologo` (`Psicologo_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Test Psicologico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Test Psicologico` (
  `Data` VARCHAR(10) NOT NULL,
  `Risultato` INT NULL DEFAULT 0,
  `Username_Psicologo` VARCHAR(45) NOT NULL,
  `Username_Paziente` VARCHAR(45) NOT NULL,
  `NomeTest` VARCHAR(45) NOT NULL,
  `ID_Test` INT NOT NULL AUTO_INCREMENT,
  INDEX `fk_Test Psicologico_Psicologo1_idx` (`Username_Psicologo` ASC) VISIBLE,
  INDEX `fk_Test Psicologico_Paziente1_idx` (`Username_Paziente` ASC) VISIBLE,
  PRIMARY KEY (`ID_Test`),
  CONSTRAINT `fk_Test Psicologico_Psicologo1`
    FOREIGN KEY (`Username_Psicologo`)
    REFERENCES `mydb`.`Psicologo` (`Psicologo_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Test Psicologico_Paziente1`
    FOREIGN KEY (`Username_Paziente`)
    REFERENCES `mydb`.`Paziente` (`Paziente_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Terapia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Terapia` (
  `Username_Psicologo` VARCHAR(45) NOT NULL,
  `Username_Paziente` VARCHAR(45) NOT NULL,
  `Descrizione` VARCHAR(300) NOT NULL,
  `Data` VARCHAR(10) NOT NULL,
  `ID_Terapia` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID_Terapia`),
  INDEX `fk_table1_Paziente1_idx` (`Username_Paziente` ASC) VISIBLE,
  CONSTRAINT `fk_table1_Psicologo1`
    FOREIGN KEY (`Username_Psicologo`)
    REFERENCES `mydb`.`Psicologo` (`Psicologo_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_table1_Paziente1`
    FOREIGN KEY (`Username_Paziente`)
    REFERENCES `mydb`.`Paziente` (`Paziente_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `mindharbordb`.`Appuntamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Appuntamento` (
  `ID_Appuntamento` INT NOT NULL AUTO_INCREMENT,
  `Data` VARCHAR(10) NOT NULL,
  `Ora` VARCHAR(5) NOT NULL,
  `Username_Paziente` VARCHAR(45) NOT NULL,
  `Username_Psicologo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_Appuntamento`),
  INDEX `fk_Appuntamento_Paziente1_idx` (`Username_Paziente` ASC) VISIBLE,
  INDEX `fk_Appuntamento_Psicologo1_idx` (`Username_Psicologo` ASC) VISIBLE,
  CONSTRAINT `fk_Appuntamento_Paziente1`
    FOREIGN KEY (`Username_Paziente`)
    REFERENCES `mydb`.`Paziente` (`Paziente_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appuntamento_Psicologo1`
    FOREIGN KEY (`Username_Psicologo`)
    REFERENCES `mydb`.`Psicologo` (`Psicologo_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
