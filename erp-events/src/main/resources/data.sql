-- ROLES
INSERT INTO roles (name, description, creation_date, update_date) VALUES ('USER', 'Rol básico de usuario', NOW(), NOW());
INSERT INTO roles (name, description, creation_date, update_date) VALUES ('ADMIN', 'Rol con privilegios de administrador', NOW(), NOW());
INSERT INTO roles (name, description, creation_date, update_date) VALUES ('EMPLOYEE', 'Rol para empleados del sistema', NOW(), NOW());

-- CLIENTS
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, soft_delete, creation_date, update_date)
VALUES ('Carlos', 'Gómez', 'carlos.gomez@example.com', '1122334455', 'carlos.gomez.cbu', false, NOW(), NOW());

INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, soft_delete, creation_date, update_date)
VALUES ('Lucía', 'Martínez', 'lucia.martinez@example.com', '1166778899', 'lucia.martinez.alias', false, NOW(), NOW());

INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, soft_delete, creation_date, update_date)
VALUES ('Juan', 'Pérez', 'juan.perez@example.com', '1144556677', 'juan.perez.cbu', false, NOW(), NOW());

-- LOCATIONS
INSERT INTO locations (latitude, longitude, soft_delete, creation_date, update_date)
VALUES (-34.603722, -58.381592, false, NOW(), NOW());

INSERT INTO locations (latitude, longitude, soft_delete, creation_date, update_date)
VALUES (-32.947750, -60.630806, false, NOW(), NOW());

INSERT INTO locations (latitude, longitude, soft_delete, creation_date, update_date)
VALUES (-31.420083, -64.188776, false, NOW(), NOW());

-- GUESTS
INSERT INTO guests (first_name, last_name, type, email, note, soft_delete, creation_date, update_date)
VALUES ('Carla', 'González', 'VIP', 'carla.g@example.com', 'Amiga cercana de los novios', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, type, email, note, soft_delete, creation_date, update_date)
VALUES ('Luis', 'Martínez', 'GENERAL', 'luis.m@example.com', NULL, false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, type, email, note, soft_delete, creation_date, update_date)
VALUES ('Sandra', 'López', 'STAFF', 'sandra.l@example.com', 'Encargada de decoración', false, NOW(), NOW());

-- USERS
INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Federico', 'Ramírez', '1990-03-10', 'DNI', '30500123', 'federico.r@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 3);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Lucía', 'Fernández', '1987-08-15', 'DNI', '28999555', 'lucia.f@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 3);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Martín', 'Luna', '1985-12-01', 'DNI', '27888555', 'martin.l@example.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 3);

INSERT INTO users (first_name, last_name, birth_date, document_type, document_number, email, password, soft_delete, creation_date, update_date, id_role)
VALUES ('Mateo', 'Buraschi', '1986-07-20', 'DNI', '32373000', 'buraschi.mateo@gmail.com', '$2a$10$W82PvGcfH0Vh62/5UxS6x.FBTu8vsvRoX.xxL/ZcKmobLSP6n9hAy', false, NOW(), NOW(), 2);

-- EMPLOYEES
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-01-15', 'Organizador', 'Federico', 'Ramírez', 'DNI', '30500123', 'federico.r@example.com', '20230500123', '1990-03-10', 'fed.ram.mp', false, NOW(), NOW(), 1);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-09-30', 'Coordinador', 'Lucía', 'Fernández', 'DNI', '28999555', 'lucia.f@example.com', '20228999555', '1987-08-15', 'luc.fer.mp', false, NOW(), NOW(), 2);

INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-05-12', 'Técnico Sonido', 'Martín', 'Luna', 'DNI', '27888555', 'martin.l@example.com', '20227888555', '1985-12-01', 'mar.lun.mp', false, NOW(), NOW(), 3);

-- SUPPLIERS
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Delicias SRL', '30711888762', 'contacto@delicias.com', '1134567890', 'delicias.banco', 'CATERING', 'Av. Libertador 1010', false, NOW(), NOW());

INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Musical SRL', '30711999123', 'musica@eventos.com', '1145642312', 'musical.eventos', 'SOUND', 'Calle Música 123', false, NOW(), NOW());

INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Decorarte', '30711777001', 'info@decorarte.com', '1156781234', 'decorarte.cbualias', 'DECORATION', 'San Juan 321', false, NOW(), NOW());

-- EVENTS
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client)
VALUES ('Lanzamiento Producto', 'Evento de presentación del nuevo producto.', 'CORPORATE', '2025-05-10 18:00:00', '2025-05-10 23:00:00', 'CONFIRMED', false, NOW(), NOW(), 1);

INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client)
VALUES ('Boda Primavera', 'Casamiento en jardines del sur.', 'SOCIAL', '2025-10-20 17:00:00', '2025-10-20 03:00:00', 'IN_PROGRESS', false, NOW(), NOW(), 2);

INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client)
VALUES ('Cena Fin de Año', 'Cena de fin de año para empleados y directivos.', 'INTERNAL', '2025-12-15 20:00:00', '2025-12-16 02:00:00', 'SCHEDULED', false, NOW(), NOW(), 3);

-- TASKS
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Reservar Catering', 'Confirmar y reservar servicio de catering.', 'PENDING', false, NOW(), NOW(), 1);

INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Confirmar Invitaciones', 'Revisar lista de invitados y enviar mails.', 'IN_PROGRESS', false, NOW(), NOW(), 2);

INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Montar Escenario', 'Instalar escenario y sonido.', 'COMPLETED', false, NOW(), NOW(), 3);

-- RELACIONES EVENTO - SUPPLIERS
INSERT INTO event_suppliers (id_event, id_supplier) VALUES (1, 1);
INSERT INTO event_suppliers (id_event, id_supplier) VALUES (2, 2);
INSERT INTO event_suppliers (id_event, id_supplier) VALUES (2, 3);
INSERT INTO event_suppliers (id_event, id_supplier) VALUES (3, 1);

-- RELACIONES EVENTO - EMPLOYEES
INSERT INTO event_employees (id_event, id_employee) VALUES (1, 1);
INSERT INTO event_employees (id_event, id_employee) VALUES (1, 2);
INSERT INTO event_employees (id_event, id_employee) VALUES (2, 2);
INSERT INTO event_employees (id_event, id_employee) VALUES (3, 2);
INSERT INTO event_employees (id_event, id_employee) VALUES (3, 3);

-- RELACIONES EVENTO - GUESTS
INSERT INTO event_guests (id_event, id_guest) VALUES (1, 1);
INSERT INTO event_guests (id_event, id_guest) VALUES (1, 2);
INSERT INTO event_guests (id_event, id_guest) VALUES (2, 1);
INSERT INTO event_guests (id_event, id_guest) VALUES (2, 3);
INSERT INTO event_guests (id_event, id_guest) VALUES (3, 2);
INSERT INTO event_guests (id_event, id_guest) VALUES (3, 3);
