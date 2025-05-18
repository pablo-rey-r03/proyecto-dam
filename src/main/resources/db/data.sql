USE proyecto;

START TRANSACTION;
	DELETE FROM document;
	DELETE FROM users;
	DELETE FROM employee;
	DELETE FROM subcontracting_relationship;
	DELETE FROM company;
COMMIT;

START TRANSACTION;

INSERT INTO company (cif, name, country, address)
VALUES 
('A12345678', 'Tech Solutions S.A.', 'ES', 'Calle Ficticia 123, Madrid'),
('B98765432', 'Global Enterprises Ltd.', 'MX', 'Avenida Siempre Viva 456, Ciudad de México'),
('C12345678', 'Innovatech Corp.', 'AR', 'Avenida Innovadora 789, Buenos Aires');


INSERT INTO employee (nif, name, surname, active, country, start_date, end_date, job, department, additional_info, company_id)
VALUES 
('12345678A', 'Juan', 'Pérez', 1, 'ES', '2024-01-01', '2025-01-01', 'Desarrollador', 'IT', 'Empleado altamente cualificado', 1),
('87654321B', 'María', 'González', 1, 'MX', '2024-02-01', '2025-02-01', 'Gerente de Marketing', 'Marketing', 'Experta en estrategias de crecimiento', 2),
('22334455C', 'Carlos', 'Martínez', 1, 'AR', '2024-03-01', '2025-03-01', 'Director de Innovación', 'R&D', 'Líder en nuevas tecnologías', 3);


INSERT INTO subcontracting_relationship (contractor_id, subcontract_id, start_date, end_date, additional_info)
VALUES 
(1, 2, '2024-01-01', '2025-01-01', 'Subcontratación de servicios tecnológicos'),
(2, 1, '2024-02-01', '2025-02-01', 'Subcontratación para campañas de marketing');


INSERT INTO document (validation_state, contractor_id, subcontract_id, name, date, expiration_date, validation_date, employee_id, additional_info, file_path)
VALUES 
('VA', 1, 2, 'Contrato de subcontratación', '2025-01-01', '2026-01-01', NULL, NULL, 'Documento relacionado con la subcontratación de servicios tecnológicos', '/path/to/document1.pdf'),
('VA', 2, 1, 'Contrato de marketing', '2025-02-01', '2026-02-01', NULL, 1, 'Documento para empleado relacionado con la campaña de marketing', '/path/to/document2.pdf');

COMMIT;