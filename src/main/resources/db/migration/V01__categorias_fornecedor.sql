CREATE TABLE IF NOT EXISTS `cuidapet_db`.`categorias_fornecedor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome_categoria` VARCHAR(45) NULL DEFAULT NULL,
  `tipo_categoria` CHAR(1) NULL DEFAULT 'P',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `categorias_fornecedor` (`id`, `nome_categoria`, `tipo_categoria`) VALUES (1, 'Petshop', 'P');
INSERT INTO `categorias_fornecedor` (`id`, `nome_categoria`, `tipo_categoria`) VALUES (2, 'Veterinária', 'V');
INSERT INTO `categorias_fornecedor` (`id`, `nome_categoria`, `tipo_categoria`) VALUES (3, 'Pet Center', 'C');
