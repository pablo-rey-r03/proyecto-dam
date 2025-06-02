USE sql7782531;

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
(1, 2, '2024-01-01', '2027-01-01', 'Subcontratación de servicios tecnológicos'),
(2, 3, '2024-02-01', '2026-02-01', 'Subcontratación para campañas de marketing'),
(3, 1, '2024-02-01', '2029-02-01', 'Subcontratación para gestión de contabilidad');


COMMIT;