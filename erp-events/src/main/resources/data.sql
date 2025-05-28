-- ROLES
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (999,'SUPER_ADMIN', 'Rol con privilegios de administrador del sistema', NOW(), NOW());
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (100,'ADMIN', 'Rol con privilegios de administrador', NOW(), NOW());
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (200,'SUPERVISOR', 'Rol básico de usuario', NOW(), NOW());
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (300,'EMPLOYEE', 'Rol para empleados del sistema', NOW(), NOW());

-- CLIENTS
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date)
VALUES ('Carlos', 'Gómez', 'buraschi.mateo@gmail.com', '1122334455', 'carlos.gomez.cbu', 'DNI', '40123456', false, NOW(), NOW());

INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date)
VALUES ('Lucía', 'Martínez', 'lucia.martinez@example.com', '1166778899', 'lucia.martinez.alias', 'DNI', 'AB123456', false, NOW(), NOW());

INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date)
VALUES ('Juan', 'Pérez', 'juan.perez@example.com', '1144556677', 'juan.perez.cbu', 'CUIT', '20345678901', false, NOW(), NOW());

-- LOCATIONS
INSERT INTO locations (
    fantasy_name, street_address, number, city, province, country,
    postal_code, latitude, longitude, soft_delete, creation_date, update_date
) VALUES (
             'Centro Cultural Buenos Aires', 'Av. Corrientes', 1234, 'Buenos Aires',
             'BUENOS_AIRES', 'ARGENTINA', 1001, -34.603722, -58.381592,
             FALSE, NOW(), NOW()
         );

INSERT INTO locations (
    fantasy_name, street_address, number, city, province, country,
    postal_code, latitude, longitude, soft_delete, creation_date, update_date
) VALUES (
             'Espacio Rosario', 'Calle Córdoba', 567, 'Rosario',
             'SANTA_FE', 'ARGENTINA', 2000, -32.947750, -60.630806,
             FALSE, NOW(), NOW()
         );

INSERT INTO locations (
    fantasy_name, street_address, number, city, province, country,
    postal_code, latitude, longitude, soft_delete, creation_date, update_date
) VALUES (
             'Auditorio Córdoba', 'Bv. San Juan', 890, 'Córdoba',
             'CORDOBA', 'ARGENTINA', 5000, -31.420083, -64.188776,
             FALSE, NOW(), NOW()
         );

-- GUESTS
INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, soft_delete, creation_date, update_date)
VALUES ('Carla', 'González', 'carla.g@example.com', 'DNI', '30555123', '1990-05-12', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, soft_delete, creation_date, update_date)
VALUES ('Luis', 'Martínez', 'luis.m@example.com', 'DNI', '28900234', '1985-09-22', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, soft_delete, creation_date, update_date)
VALUES ('Sandra', 'López', 'sandra.l@example.com', 'DNI', '31233456', '1992-03-30', false, NOW(), NOW());

-- USERS
INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Federico', 'Ramírez', '1990-03-10', 'DNI', '30500123', 'federico.r@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 3);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Lucía', 'Fernández', '1987-08-15', 'DNI', '28999554', 'lucia.f@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Martín', 'Luna', '1985-12-01', 'DNI', '27888554', 'martin.l@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Mateo', 'Buraschi', '1986-07-20', 'DNI', '32373000', 'buraschi.mateo@gmail.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 2);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Ana', 'Torres', '1992-04-21', 'DNI', '30233456', 'ana.torres@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Bruno', 'Acosta', '1991-11-02', 'DNI', '30344567', 'bruno.acosta@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Camila', 'Silva', '1993-06-17', 'DNI', '30455678', 'camila.silva@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Diego', 'Méndez', '1989-12-29', 'DNI', '30566789', 'diego.mendez@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Elena', 'Rivas', '1990-08-13', 'DNI', '30677890', 'elena.rivas@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Facundo', 'Herrera', '1988-03-05', 'DNI', '30788901', 'facundo.herrera@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Gisela', 'Morales', '1991-09-07', 'DNI', '30899012', 'gisela.morales@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Hernán', 'Gómez', '1986-01-26', 'DNI', '30910123', 'hernan.gomez@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Ivana', 'Sosa', '1992-02-10', 'DNI', '31021234', 'ivana.sosa@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Jorge', 'Cabrera', '1994-04-19', 'DNI', '31132345', 'jorge.cabrera@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Karina', 'Leiva', '1989-05-25', 'DNI', '31243456', 'karina.leiva@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Luis', 'Funes', '1987-06-30', 'DNI', '31354567', 'luis.funes@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('María', 'Paz', '1995-07-12', 'DNI', '31465678', 'maria.paz@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Nicolás', 'Barrios', '1990-10-04', 'DNI', '31576789', 'nicolas.barrios@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 4);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Oriana', 'Delgado', '1991-03-22', 'DNI', '31687890', 'oriana.delgado@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 3);


-- EMPLOYEES
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-01-15', 'Organizador', 'Federico', 'Ramírez', 'DNI', '30500123', 'buraschi.mateo@gmail.com', '20230500123', '1990-03-10', 'fed.ram.mp', false, NOW(), NOW(), 1);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-09-30', 'Coordinador', 'Lucía', 'Fernández', 'DNI', '28999555', 'lucia.f@example.com', '20228999555', '1987-08-15', 'luc.fer.mp', false, NOW(), NOW(), 2);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-05-12', 'Técnico Sonido', 'Martín', 'Luna', 'DNI', '27888555', 'martin.l@example.com', '20227888555', '1985-12-01', 'mar.lun.mp', false, NOW(), NOW(), 3);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2023-01-05', 'Asistente', 'Ana', 'Torres', 'DNI', '30233456', 'ana.torres@example.com', '20302334567', '1992-04-21', 'ana.tor.mp', false, NOW(), NOW(), 5);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-02-11', 'Coordinador', 'Bruno', 'Acosta', 'DNI', '30344567', 'bruno.acosta@example.com', '20303445677', '1991-11-02', 'bru.acos.mp', false, NOW(), NOW(), 6);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-03-20', 'Organizador', 'Camila', 'Silva', 'DNI', '30455678', 'camila.silva@example.com', '20304556787', '1993-06-17', 'cam.sil.mp', false, NOW(), NOW(), 7);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-11-30', 'Técnico', 'Diego', 'Méndez', 'DNI', '30566789', 'diego.mendez@example.com', '20305667897', '1989-12-29', 'die.men.mp', false, NOW(), NOW(), 8);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-04-10', 'Supervisora', 'Elena', 'Rivas', 'DNI', '30677890', 'elena.rivas@example.com', '20306778907', '1990-08-13', 'ele.riv.mp', false, NOW(), NOW(), 9);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2019-05-12', 'Asistente', 'Facundo', 'Herrera', 'DNI', '30788901', 'facundo.herrera@example.com', '20307889017', '1988-03-05', 'fac.her.mp', false, NOW(), NOW(), 10);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2023-01-18', 'Coordinadora', 'Gisela', 'Morales', 'DNI', '30899012', 'gisela.morales@example.com', '20308990127', '1991-09-07', 'gis.mor.mp', false, NOW(), NOW(), 11);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-12-01', 'Montaje', 'Hernán', 'Gómez', 'DNI', '30910123', 'hernan.gomez@example.com', '20309101237', '1986-01-26', 'her.gom.mp', false, NOW(), NOW(), 12);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-06-15', 'Supervisora', 'Ivana', 'Sosa', 'DNI', '31021234', 'ivana.sosa@example.com', '20310212347', '1992-02-10', 'iva.sos.mp', false, NOW(), NOW(), 13);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-07-03', 'Organizador', 'Jorge', 'Cabrera', 'DNI', '31132345', 'jorge.cabrera@example.com', '20311323457', '1994-04-19', 'jor.cab.mp', false, NOW(), NOW(), 14);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-10-22', 'Recepción', 'Karina', 'Leiva', 'DNI', '31243456', 'karina.leiva@example.com', '20312434567', '1989-05-25', 'kar.lei.mp', false, NOW(), NOW(), 15);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-11-11', 'Montaje', 'Luis', 'Funes', 'DNI', '31354567', 'luis.funes@example.com', '20313545677', '1987-06-30', 'lui.fun.mp', false, NOW(), NOW(), 16);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-01-27', 'Sonido', 'María', 'Paz', 'DNI', '31465678', 'maria.paz@example.com', '20314656787', '1995-07-12', 'mar.paz.mp', false, NOW(), NOW(), 17);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2019-08-09', 'Asistente', 'Nicolás', 'Barrios', 'DNI', '31576789', 'nicolas.barrios@example.com', '20315767897', '1990-10-04', 'nic.bar.mp', false, NOW(), NOW(), 18);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2023-03-01', 'Recepción', 'Oriana', 'Delgado', 'DNI', '31687890', 'oriana.delgado@example.com', '20316878907', '1991-03-22', 'ori.del.mp', false, NOW(), NOW(), 19);


-- SUPPLIERS
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Delicias SRL', '30711888762', 'buraschi.mateo@gmail.com', '1134567890', 'delicias.banco', 'CATERING', 'Av. Libertador 1010', false, NOW(), NOW());

INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Musical SRL', '30711999123', 'musica@eventos.com', '1145642312', 'musical.eventos', 'SOUND', 'Calle Música 123', false, NOW(), NOW());

INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Decorarte', '30711777001', 'info@decorarte.com', '1156781234', 'decorarte.cbualias', 'DECORATION', 'San Juan 321', false, NOW(), NOW());

-- EVENTS
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location)
VALUES ('Lanzamiento Producto', 'Evento de presentación del nuevo producto.', 'CORPORATE', '2025-05-10 18:00:00', '2025-05-10 23:00:00', 'CONFIRMED', false, NOW(), NOW(), 1, 2);

INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location)
VALUES ('Boda Primavera', 'Casamiento en jardines del sur.', 'SOCIAL', '2025-10-20 17:00:00', '2025-10-20 03:00:00', 'IN_PROGRESS', false, NOW(), NOW(), 2, 1);

INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location)
VALUES ('Cena Fin de Año', 'Cena de fin de año para empleados y directivos.', 'SPORTS', '2025-12-15 20:00:00', '2025-12-16 02:00:00', 'SUSPENDED', false, NOW(), NOW(), 3, 3);

-- TASKS
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Reservar Catering', 'Confirmar y reservar servicio de catering.', 'PENDING', false, NOW(), NOW(), 1);

INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Confirmar Invitaciones', 'Revisar lista de invitados y enviar mails.', 'IN_PROGRESS', false, NOW(), NOW(), 2);

INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Montar Escenario', 'Instalar escenario y sonido.', 'COMPLETED', false, NOW(), NOW(), 3);

-- FILES
INSERT INTO files (
    file_type, file_name, file_content_type, file_url, review_note,
    creation_date, update_date,
    id_supplier, id_client, id_employee
)
VALUES
('RECEIPT', 'dni_document_front_sample.jpg', 'image/jpg', 'http://localhost:8080/event-files/1', 'Validado por contabilidad', NOW(), NOW(), 1, NULL, NULL),
('BILLING', 'dni_document_back_sample.jpg', 'image/jpg', 'http://localhost:8080/event-files/2', 'Pendiente de auditoría', NOW(), NOW(), 1, NULL, NULL),
('PAYMENT', 'purchase_sale_sample.pdf', 'application/pdf', 'http://localhost:8080/event-files/3', 'Confirmado', NOW(), NOW(), 2, NULL, NULL),
('OTHER', 'propuesta_decorarte.pdf', 'application/pdf', 'https://minio.local/files/propuesta_decorarte.pdf', 'Versión preliminar', NOW(), NOW(), 3, NULL, NULL),
('RECEIPT', 'recibo_decorarte_enero.pdf', 'application/pdf', 'https://minio.local/files/recibo_decorarte_enero.pdf', 'Escaneado', NOW(), NOW(), 3, NULL, NULL);

INSERT INTO files (
    file_type, file_name, file_content_type, file_url, review_note,
    creation_date, update_date,
    id_supplier, id_client, id_employee
)
VALUES
    ('OTHER', 'contrato_cliente1.pdf', 'application/pdf', 'https://minio.local/files/contrato_cliente1.pdf', 'Contrato firmado', NOW(), NOW(), NULL, 1, NULL),
    ('RECEIPT', 'pago_cliente1_marzo.pdf', 'application/pdf', 'https://minio.local/files/pago_cliente1_marzo.pdf', 'Pago registrado', NOW(), NOW(), NULL, 1, NULL),
    ('BILLING', 'factura_cliente2_abril.pdf', 'application/pdf', 'https://minio.local/files/factura_cliente2_abril.pdf', 'Facturación manual', NOW(), NOW(), NULL, 2, NULL),
    ('OTHER', 'documentacion_cliente2.pdf', 'application/pdf', 'https://minio.local/files/documentacion_cliente2.pdf', 'Documentación adicional', NOW(), NOW(), NULL, 2, NULL);

INSERT INTO files (
    file_type, file_name, file_content_type, file_url, review_note,
    creation_date, update_date,
    id_supplier, id_client, id_employee
)
VALUES
    ('PAYMENT', 'recibo_sueldo_emp1.pdf', 'application/pdf', 'https://minio.local/files/recibo_sueldo_emp1.pdf', 'Abril 2025', NOW(), NOW(), NULL, NULL, 1),
    ('PAYMENT', 'recibo_sueldo_emp2.pdf', 'application/pdf', 'https://minio.local/files/recibo_sueldo_emp2.pdf', 'Marzo 2025', NOW(), NOW(), NULL, NULL, 2),
    ('OTHER', 'certificado_emp3.pdf', 'application/pdf', 'https://minio.local/files/certificado_emp3.pdf', 'Curso completado', NOW(), NOW(), NULL, NULL, 3);



-- RELACIONES EVENTO - SUPPLIERS
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (1, 1, 'DUE', 1000.0, 500.0, 'TRANSFER', NOW(), NOW());

INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (2, 2, 'PAID', 1500.0, 0.0, 'CASH', NOW(), NOW());

INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (2, 3, 'DUE', 1200.0, 600.0, 'TRANSFER', NOW(), NOW());

INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (3, 1, 'PARTIALLY_PAID', 2000.0, 1000.0, 'CARD', NOW(), NOW());


-- RELACIONES EVENTO - EMPLOYEES
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date)
VALUES (1, 1, 'DUE', 1000.0, 500.0, 'TRANSFER', NOW(), NOW());

INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date)
VALUES (1, 2, 'PAID', 1500.0, 0.0, 'CASH', NOW(), NOW());

INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date)
VALUES (2, 2, 'DUE', 1200.0, 600.0, 'TRANSFER', NOW(), NOW());

INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date)
VALUES (3, 2, 'PARTIALLY_PAID', 2000.0, 1000.0, 'CARD', NOW(), NOW());

INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date)
VALUES (3, 3, 'PAID', 1800.0, 0.0, 'TRANSFER', NOW(), NOW());


-- RELACIONES EVENTO - GUESTS
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, creation_date, update_date)
VALUES (1, 1, 'REGULAR', 'Amiga cercana de los novios', 'ENTRY', NOW(), false, NOW(), NOW());

INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, creation_date, update_date)
VALUES (1, 2, 'REGULAR', 'Amiga de los novios', 'ENTRY', NOW(), false, NOW(), NOW());

INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, creation_date, update_date)
VALUES (2, 1, 'REGULAR', 'Amiga de los novios', 'ENTRY', NOW(), false, NOW(), NOW());

INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, creation_date, update_date)
VALUES (2, 3, 'VIP', 'Amiga cercana de los padres', 'ENTRY', NOW(), true, NOW(), NOW());

INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, creation_date, update_date)
VALUES (1, 2, 'FRIEND', 'Familiar', 'ENTRY', NOW(), false, NOW(), NOW());

INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, creation_date, update_date)
VALUES (2, 3, 'REGULAR', 'Familiar de la novia', 'ENTRY', NOW(), true, NOW(), NOW());

-- TEMPLATES
INSERT INTO templates (name, body, active) VALUES ('Welcome Email', '<h1>Welcome to our service!</h1><p>Thank you for joining us.</p>', true);
INSERT INTO templates (name, body, active) VALUES
    ('Nuevo Usuario',
     '<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Nuevo Usuario</title><style>body {font-family: ''Arial'', sans-serif;background-color: #F8F9FA;margin: 0;padding: 0;}.container {background-color: #ffffff;margin: 50px auto;padding: 20px 20px 1px;border-radius: 8px;box-shadow: 0 0 10px rgba(19, 80, 91, 0.2);max-width: 600px;}.header {background-color: #13505B;color: #ffffff;padding: 16px;text-align: center;border-top-left-radius: 8px;border-top-right-radius: 8px;}.header h1 {margin: 0;font-size: 24px;}.content {padding: 20px;}.content p {line-height: 1.6;color: #040404;margin-bottom: 16px;}.footer {text-align: center;font-size: 12px;color: #777777;padding: 16px 0;}.button-accent {display: inline-block;padding: 10px 20px;background-color: #FF7F11;color: #ffffff;text-decoration: none;border-radius: 4px;font-weight: bold;}</style></head><body><div class="container"><div class="header"><h1>Nuevo Usuario</h1></div><div class="content"><p>Hola <strong>{{FIRST_NAME}}</strong>,</p><p>Nos complace informarle que su cuenta para operar en ERP Eventos fue creada con exito</p><p>Si tiene alguna consulta o necesita más información, no dude en ponerse en contacto con nosotros.</p><p><a href="http://localhost:4200/login" class="button-accent">Iniciar Sesion</a></p><p>Gracias por confiar en <strong>ERP Eventos</strong>.</p></div><div class="footer"><hr><p>&copy; 2025 ERP Eventos</p></div></div></body></html>',
     true);
INSERT INTO templates (name, body, active) VALUES
    ('recuperacion_contrasena',
     '<!DOCTYPE html>
     <html lang="es">
     <head>
       <meta charset="UTF-8">
       <meta name="viewport" content="width=device-width, initial-scale=1.0">
       <title>Recuperar Contraseña</title>
       <style>
         body {
           font-family: "Arial", sans-serif;
           background-color: #F8F9FA;
           margin: 0;
           padding: 0;
         }
         .container {
           background-color: #ffffff;
           margin: 50px auto;
           padding: 20px 20px 1px;
           border-radius: 8px;
           box-shadow: 0 0 10px rgba(19, 80, 91, 0.2);
           max-width: 600px;
         }
         .header {
           background-color: #13505B;
           color: #ffffff;
           padding: 16px;
           text-align: center;
           border-top-left-radius: 8px;
           border-top-right-radius: 8px;
         }
         .header h1 {
           margin: 0;
           font-size: 24px;
         }
         .content {
           padding: 20px;
         }
         .content p {
           line-height: 1.6;
           color: #040404;
           margin-bottom: 16px;
         }
         .footer {
           text-align: center;
           font-size: 12px;
           color: #777777;
           padding: 16px 0;
         }
         .button-accent {
           display: inline-block;
           padding: 10px 20px;
           background-color: #FF7F11;
           color: #ffffff;
           text-decoration: none;
           border-radius: 4px;
           font-weight: bold;
         }
       </style>
     </head>
     <body>
       <div class="container">
         <div class="header">
           <h1>Recuperar Contraseña</h1>
         </div>
         <div class="content">
           <p>Hola <strong>{{nombre}}</strong>,</p>
           <p>Hemos recibido una solicitud para restablecer tu contraseña.</p>
           <p>Hacé clic en el siguiente enlace para continuar:</p>
           <p><a href="{{link}}" class="button-accent">Restablecer contraseña</a></p>
           <p>Este enlace expirará en 15 minutos. Si no solicitaste esta acción, podés ignorar este mensaje.</p>
         </div>
         <div class="footer">
           <hr>
           <p>&copy; 2025 ERP Eventos</p>
         </div>
       </div>
     </body>
     </html>',
     true);
INSERT INTO templates (name, body, active) VALUES
    ('archivo_guardado',
     '<!DOCTYPE html>
     <html lang="es">
     <head>
       <meta charset="UTF-8">
       <meta name="viewport" content="width=device-width, initial-scale=1.0">
       <title>Archivo Guardado</title>
       <style>
         body {
           font-family: "Arial", sans-serif;
           background-color: #F8F9FA;
           margin: 0;
           padding: 0;
         }
         .container {
           background-color: #ffffff;
           margin: 50px auto;
           padding: 20px 20px 1px;
           border-radius: 8px;
           box-shadow: 0 0 10px rgba(19, 80, 91, 0.2);
           max-width: 600px;
         }
         .header {
           background-color: #13505B;
           color: #ffffff;
           padding: 16px;
           text-align: center;
           border-top-left-radius: 8px;
           border-top-right-radius: 8px;
         }
         .header h1 {
           margin: 0;
           font-size: 24px;
         }
         .content {
           padding: 20px;
         }
         .content p {
           line-height: 1.6;
           color: #040404;
           margin-bottom: 16px;
         }
         .footer {
           text-align: center;
           font-size: 12px;
           color: #777777;
           padding: 16px 0;
         }
         .button-accent {
           display: inline-block;
           padding: 10px 20px;
           background-color: #FF7F11;
           color: #ffffff;
           text-decoration: none;
           border-radius: 4px;
           font-weight: bold;
         }
       </style>
     </head>
     <body>
       <div class="container">
         <div class="header">
           <h1>Archivo Guardado</h1>
         </div>
         <div class="content">
           <p>Se ha guardado correctamente un nuevo archivo en el sistema.</p>
           <p><strong>Nombre:</strong> {{fileName}}</p>
           <p><strong>Tipo:</strong> {{fileType}}</p>
           <p><strong>Fecha de carga:</strong> {{date}}</p>
           <p>Podés acceder al sistema para ver más detalles o descargarlo si fuera necesario.</p>
         </div>
         <div class="footer">
           <hr>
           <p>&copy; 2025 ERP Eventos</p>
         </div>
       </div>
     </body>
     </html>',
     true);




