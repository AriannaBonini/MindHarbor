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
USE `MindHarborDB` ;

-- -----------------------------------------------------
-- Table `mydb`.`Utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utente` (
    `Username` VARCHAR(45) NOT NULL,
    `Password` INT NOT NULL,
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
    PRIMARY KEY (`Paziente_Username`),
    INDEX `fk_Paziente_Utente1_idx` (`Paziente_Username` ASC) VISIBLE,
    CONSTRAINT `fk_Paziente_Utente1`
    FOREIGN KEY (`Paziente_Username`)
    REFERENCES `mydb`.`Utente` (`Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Lista Test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Lista Test` (
    `Nome` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`Nome`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DomandeTest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`DomandeTest` (
    `Domanda` VARCHAR(200) NOT NULL,
    `Nome` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`Domanda`, `Nome`),
    INDEX `fk_DomandeTest_Lista Test1_idx` (`Nome` ASC) VISIBLE,
    CONSTRAINT `fk_DomandeTest_Lista Test1`
    FOREIGN KEY (`Nome`)
    REFERENCES `mydb`.`Lista Test` (`Nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Test Psicologico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Test Psicologico` (
    `DataOdierna` VARCHAR(10) NOT NULL,
    `Risultato` INT NULL DEFAULT 0,
    `Psicologo` VARCHAR(45) NOT NULL,
    `Paziente` VARCHAR(45) NOT NULL,
    `Test` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`DataOdierna`, `Psicologo`, `Paziente`),
    INDEX `fk_Test Psicologico_Psicologo1_idx` (`Psicologo` ASC) VISIBLE,
    INDEX `fk_Test Psicologico_Paziente1_idx` (`Paziente` ASC) VISIBLE,
    INDEX `fk_Test Psicologico_Lista Test1_idx` (`Test` ASC) VISIBLE,
    CONSTRAINT `fk_Test Psicologico_Psicologo1`
    FOREIGN KEY (`Psicologo`)
    REFERENCES `mydb`.`Psicologo` (`Psicologo_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_Test Psicologico_Paziente1`
    FOREIGN KEY (`Paziente`)
    REFERENCES `mydb`.`Paziente` (`Paziente_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_Test Psicologico_Lista Test1`
    FOREIGN KEY (`Test`)
    REFERENCES `mydb`.`Lista Test` (`Nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Terapia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Terapia` (
    `Psicologo_Psicologo_Username` VARCHAR(45) NOT NULL,
    `Paziente_Paziente_Username` VARCHAR(45) NOT NULL,
    `Terapia` VARCHAR(300) NOT NULL,
    `DataOdierna` VARCHAR(10) NULL,
    PRIMARY KEY (`Psicologo_Psicologo_Username`, `Paziente_Paziente_Username`),
    INDEX `fk_table1_Paziente1_idx` (`Paziente_Paziente_Username` ASC) VISIBLE,
    CONSTRAINT `fk_table1_Psicologo1`
    FOREIGN KEY (`Psicologo_Psicologo_Username`)
    REFERENCES `mydb`.`Psicologo` (`Psicologo_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_table1_Paziente1`
    FOREIGN KEY (`Paziente_Paziente_Username`)
    REFERENCES `mydb`.`Paziente` (`Paziente_Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;