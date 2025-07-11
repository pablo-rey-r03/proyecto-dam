USE sql7782531;

DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS subcontracting_relationship;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS company;

/**
 * TABLA company (EMPRESA)
 */
DROP TABLE IF EXISTS company;
CREATE TABLE company (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	cif VARCHAR(16) NOT NULL UNIQUE,
	name VARCHAR(32) NOT NULL,
	country ENUM(
	  'AF', 'AL', 'DZ', 'AD', 'AO', 'AG', 'AR', 'AM', 'AU', 'AT',
	  'AZ', 'BS', 'BH', 'BD', 'BB', 'BY', 'BE', 'BZ', 'BJ', 'BT',
	  'BO', 'BA', 'BW', 'BR', 'BN', 'BG', 'BF', 'BI', 'CV', 'KH',
	  'CM', 'CA', 'CF', 'TD', 'CL', 'CN', 'CO', 'KM', 'CG', 'CR',
	  'CI', 'HR', 'CU', 'CY', 'CZ', 'KP', 'CD', 'DK', 'DJ', 'DM',
	  'DO', 'EC', 'EG', 'SV', 'GQ', 'ER', 'EE', 'SZ', 'ET', 'FJ',
	  'FI', 'FR', 'GA', 'GM', 'GE', 'DE', 'GH', 'GR', 'GD', 'GT',
	  'GN', 'GW', 'GY', 'HT', 'HN', 'HU', 'IS', 'IN', 'ID', 'IR',
	  'IQ', 'IE', 'IL', 'IT', 'JM', 'JP', 'JO', 'KZ', 'KE', 'KI',
	  'KW', 'KG', 'LA', 'LV', 'LB', 'LS', 'LR', 'LY', 'LI', 'LT',
	  'LU', 'MG', 'MW', 'MY', 'MV', 'ML', 'MT', 'MH', 'MR', 'MU',
	  'MX', 'FM', 'MC', 'MN', 'ME', 'MA', 'MZ', 'MM', 'NA', 'NR',
	  'NP', 'NL', 'NZ', 'NI', 'NE', 'NG', 'MK', 'NO', 'OM', 'PK',
	  'PW', 'PA', 'PG', 'PY', 'PE', 'PH', 'PL', 'PT', 'QA', 'KR',
	  'MD', 'RO', 'RU', 'RW', 'KN', 'LC', 'VC', 'WS', 'SM', 'ST',
	  'SA', 'SN', 'RS', 'SC', 'SL', 'SG', 'SK', 'SI', 'SB', 'SO',
	  'ZA', 'SS', 'ES', 'LK', 'SD', 'SR', 'SE', 'CH', 'SY', 'TJ',
	  'TH', 'TL', 'TG', 'TO', 'TT', 'TN', 'TR', 'TM', 'TV', 'UG',
	  'UA', 'AE', 'GB', 'TZ', 'US', 'UY', 'UZ', 'VU', 'VE', 'VN',
	  'YE', 'ZM', 'ZW', 'VA', 'PS'
	)
 	NOT NULL,
	address VARCHAR(64) NOT NULL
);


/**
 * TABLA employee (EMPLEADO)
 */
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	nif VARCHAR(9) NOT NULL UNIQUE,
	name VARCHAR(32) NOT NULL,
	surname VARCHAR(64),
	active TINYINT(1) NOT NULL,
	country ENUM(
	  'AF', 'AL', 'DZ', 'AD', 'AO', 'AG', 'AR', 'AM', 'AU', 'AT',
	  'AZ', 'BS', 'BH', 'BD', 'BB', 'BY', 'BE', 'BZ', 'BJ', 'BT',
	  'BO', 'BA', 'BW', 'BR', 'BN', 'BG', 'BF', 'BI', 'CV', 'KH',
	  'CM', 'CA', 'CF', 'TD', 'CL', 'CN', 'CO', 'KM', 'CG', 'CR',
	  'CI', 'HR', 'CU', 'CY', 'CZ', 'KP', 'CD', 'DK', 'DJ', 'DM',
	  'DO', 'EC', 'EG', 'SV', 'GQ', 'ER', 'EE', 'SZ', 'ET', 'FJ',
	  'FI', 'FR', 'GA', 'GM', 'GE', 'DE', 'GH', 'GR', 'GD', 'GT',
	  'GN', 'GW', 'GY', 'HT', 'HN', 'HU', 'IS', 'IN', 'ID', 'IR',
	  'IQ', 'IE', 'IL', 'IT', 'JM', 'JP', 'JO', 'KZ', 'KE', 'KI',
	  'KW', 'KG', 'LA', 'LV', 'LB', 'LS', 'LR', 'LY', 'LI', 'LT',
	  'LU', 'MG', 'MW', 'MY', 'MV', 'ML', 'MT', 'MH', 'MR', 'MU',
	  'MX', 'FM', 'MC', 'MN', 'ME', 'MA', 'MZ', 'MM', 'NA', 'NR',
	  'NP', 'NL', 'NZ', 'NI', 'NE', 'NG', 'MK', 'NO', 'OM', 'PK',
	  'PW', 'PA', 'PG', 'PY', 'PE', 'PH', 'PL', 'PT', 'QA', 'KR',
	  'MD', 'RO', 'RU', 'RW', 'KN', 'LC', 'VC', 'WS', 'SM', 'ST',
	  'SA', 'SN', 'RS', 'SC', 'SL', 'SG', 'SK', 'SI', 'SB', 'SO',
	  'ZA', 'SS', 'ES', 'LK', 'SD', 'SR', 'SE', 'CH', 'SY', 'TJ',
	  'TH', 'TL', 'TG', 'TO', 'TT', 'TN', 'TR', 'TM', 'TV', 'UG',
	  'UA', 'AE', 'GB', 'TZ', 'US', 'UY', 'UZ', 'VU', 'VE', 'VN',
	  'YE', 'ZM', 'ZW', 'VA', 'PS'
	)
 	NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE,
	job VARCHAR(64) NOT NULL,
	department VARCHAR(64) NOT NULL,
	additional_info VARCHAR(512),
	company_id BIGINT NOT NULL,
	FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);


/**
 * TABLA users (USUARIOS)
 */
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	email VARCHAR(64) NOT NULL UNIQUE,
	password VARCHAR(256) NOT NULL,
	employee_id BIGINT NOT NULL,
	FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE
);


/**
 * TABLA subcontracting_relationship (SUBCONTRATAS)
 */
DROP TABLE IF EXISTS subcontracting_relationship;
CREATE TABLE subcontracting_relationship (
	contractor_id BIGINT NOT NULL,
	subcontract_id BIGINT NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE,
	additional_info VARCHAR(512),
	PRIMARY KEY (contractor_id, subcontract_id),
	FOREIGN KEY (contractor_id) REFERENCES company(id) ON DELETE CASCADE,
	FOREIGN KEY (subcontract_id) REFERENCES company(id) ON DELETE CASCADE

);


/**
 * TABLA document (DOCUMENTO)
 */
DROP TABLE IF EXISTS document;
CREATE TABLE document (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	validation_state ENUM ('OK', 'ER', 'VA', 'EX') NOT NULL,
	contractor_id BIGINT NOT NULL,
	subcontract_id BIGINT NOT NULL,
	name VARCHAR(128) NOT NULL,
	date DATE NOT NULL,
	expiration_date DATE,
	validation_date DATE,
	employee_id BIGINT,
	additional_info VARCHAR(256),
	file_path VARCHAR(512),
	FOREIGN KEY (contractor_id) REFERENCES company(id) ON DELETE CASCADE,
	FOREIGN KEY (subcontract_id) REFERENCES company(id) ON DELETE CASCADE,
	FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE
);

/**
 * TAREA PROGRAMADA POR EVENTO: comprueba la expiration_date de los documentos
 * y actualiza el validation_state de los caducados. Una vez al día.
 * */
DROP EVENT IF EXISTS update_document_validation_state;
CREATE EVENT update_document_validation_state
ON SCHEDULE EVERY 1 DAY 
STARTS CURRENT_TIMESTAMP
DO
	UPDATE document 
	SET validation_state = 'EX'
	WHERE expiration_date <= CURDATE() AND validation_state != 'EX' AND expiration_date IS NOT NULL;

/**
 * TRIGGER - Document
 * _______
 * 
 * En cada inserción, se asegura de que:
 * - La fecha de validación es nula
 * - La fecha de expiración es en el futuro
 * - El documento empieza estando pendiente de validación
 * - Si el documento es para empresas, el ID de empleado debe ser nulo, y viceversa
 */
DELIMITER $$

DROP TRIGGER IF EXISTS validate_document_insert$$
CREATE TRIGGER validate_document_insert
BEFORE INSERT ON document
FOR EACH ROW
BEGIN
    -- Comprobar que la fecha de expiración, si se especifica, es posterior a la fecha actual
    IF NEW.expiration_date IS NOT NULL AND NEW.expiration_date <= CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La fecha de expiración debe ser posterior a la fecha actual.';
    END IF;

    -- Comprobar que el estado de validación es 'VA'
    IF NEW.validation_state != 'VA' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El documento debe insertarse pendiente de validación.';
    END IF;
    
END$$

DELIMITER ;


/**
 * TRIGGER - Document
 * _______
 * 
 * En cada actualización:
 * - Comprueba que si el tipo de destinatario es 'EMP' el ID del empleado NO se ha modificado a nulo.
 *   También a la viceversa: si el tipo de destinatario es 'COM' el ID de empleado debe seguir nulo obligatoriamente.
 * - Ni validation_date ni expiration_date deben ser anteriores a la fecha de inserción.
 * - Si se ha modificado validation_state a 'OK', se debe insertar en validation_date la fecha actual automáticamente.
 * */
DELIMITER $$

DROP TRIGGER IF EXISTS validate_document_update$$
CREATE TRIGGER validate_document_update
BEFORE UPDATE ON document
FOR EACH ROW
BEGIN
	IF NEW.validation_date IS NOT NULL AND NEW.validation_date < NEW.date THEN 
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'La nueva fecha de validación no puede ser anterior a la fecha de emisión del documento.';
	END IF;

	IF NEW.validation_state = 'OK' && OLD.validation_state != 'OK' THEN
    	SET NEW.validation_date = CURDATE();
	END IF;

	IF NEW.validation_date IS NOT NULL AND OLD.validation_date IS NULL THEN 
		SET NEW.validation_state = 'OK';
	END IF;

	IF NEW.validation_date IS NULL AND OLD.validation_date IS NOT NULL THEN
		SET NEW.validation_state = 'VA';
	END IF;

	IF NEW.expiration_date IS NOT NULL AND NEW.expiration_date <= CURDATE() THEN
		SET NEW.validation_state = 'EX';
	END IF;
    
END$$

DELIMITER ;


/**
 * TRIGGER - Employee
 * __________________
 * 
 * En cada inserción y actualización se comprueba que la fecha de fin
 * no sea anterior a la fecha de inicio.
 */

DELIMITER $$

DROP TRIGGER IF EXISTS validate_employee_update$$
CREATE TRIGGER validate_employee_update
BEFORE UPDATE ON employee
FOR EACH ROW
BEGIN
	IF NEW.end_date < NEW.start_date THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede finalizar el contrato antes de empezarlo.';
    END IF;
END$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS validate_employee_insert$$
CREATE TRIGGER validate_employee_insert
BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
	IF NEW.end_date < NEW.start_date THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede finalizar el contrato antes de empezarlo.';
    END IF;
END$$

DELIMITER ;


/**
 * TRIGGER - Subcontracting_relationship
 * __________________
 * 
 * En cada inserción y actualización se comprueba que la fecha de fin
 * no sea anterior a la fecha de inicio.
 */
DELIMITER $$

DROP TRIGGER IF EXISTS validate_subcontract_update$$
CREATE TRIGGER validate_subcontract_update
BEFORE UPDATE ON subcontracting_relationship
FOR EACH ROW
BEGIN
	IF NEW.end_date < NEW.start_date THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede finalizar el contrato antes de empezarlo.';
    END IF;
END$$

DELIMITER ;

DELIMITER $$
DROP TRIGGER IF EXISTS validate_subcontract_insert$$
CREATE TRIGGER validate_subcontract_insert
BEFORE INSERT ON subcontracting_relationship
FOR EACH ROW
BEGIN
	IF NEW.end_date < NEW.start_date THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede finalizar el contrato antes de empezarlo.';
    END IF;
END$$

DELIMITER ;