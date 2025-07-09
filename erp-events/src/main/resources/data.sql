-- ROLES
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (999,'SUPER_ADMIN', 'Rol con privilegios de administrador del sistema', NOW(), NOW());
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (100,'ADMIN', 'Rol con privilegios de administrador', NOW(), NOW());
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (200,'SUPERVISOR', 'Rol básico de usuario', NOW(), NOW());
INSERT INTO roles (role_code, name, description, creation_date, update_date) VALUES (300,'EMPLOYEE', 'Rol para empleados del sistema', NOW(), NOW());

-- CLIENTS
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date)
VALUES ('Carlos', 'Gómez', 'buraschi.mateo@gmail.com', '1122334455', 'carlos.gomez.cbu', 'DNI', '40123456', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date)
VALUES ('Lucía', 'Martínez', 'matiob@hotmail.com', '1166778899', 'lucia.martinez.alias', 'DNI', 'AB123456', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date)
VALUES ('Juan', 'Pérez', 'juan.perez@example.com', '1144556677', 'juan.perez.cbu', 'CUIT', '20345678901', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Carmen', 'Martínez', 'bgil@example.net', '657343707', 'carmen.martínez.cbu', 'CUIT', '40876768', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Emilio', 'Rojas', 'jesus44@example.net', '606177841', 'emilio.rojas.cbu', 'CUIT', '69261885', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Luis', 'Suárez', 'patricia74@example.org', '650807980', 'luis.suárez.cbu', 'DNI', '97112592', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Natalia', 'Vicente', 'ibarrairene@example.org', '636471066', 'natalia.vicente.cbu', 'CUIT', '97044174', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Héctor', 'Blázquez', 'areyes@example.org', '684238674', 'héctor.blázquez.cbu', 'CUIT', '98301189', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Patricia', 'Aguilar', 'hgutierrez@example.com', '685839026', 'patricia.aguilar.cbu', 'DNI', '19351898', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('José', 'Cano', 'veronicamendez@example.net', '665217052', 'josé.cano.cbu', 'CUIT', '71072858', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Isabel', 'Valverde', 'fbautista@example.net', '698348761', 'isabel.valverde.cbu', 'DNI', '42922250', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Juan', 'Iglesias', 'carla05@example.com', '607143445', 'juan.iglesias.cbu', 'CUIT', '35964315', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Marta', 'Rodríguez', 'gmarina@example.com', '679457611', 'marta.rodríguez.cbu', 'DNI', '92628067', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Jesús', 'Navarro', 'moises13@example.net', '661234906', 'jesús.navarro.cbu', 'CUIT', '68225297', false, NOW(), NOW());
INSERT INTO clients (first_name, last_name, email, phone_number, bank_alias_cbu, document_type, document_number, soft_delete, creation_date, update_date) VALUES ('Dolores', 'Gil', 'mmartin@example.com', '699988254', 'dolores.gil.cbu', 'CUIT', '27488036', false, NOW(), NOW());

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
INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Madrid', 'Calle de Alcobendas', 914, 'Zamora', 'BUENOS_AIRES', 'ARGENTINA', 4897, -80.0533285, -100.916123, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Valencia', 'Calle de Oviedo', 1255, 'Elche', 'CORDOBA', 'ARGENTINA', 2373, 32.588347, 24.520879, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Málaga', 'Calle de Melilla', 914, 'Gijón', 'SANTA_FE', 'ARGENTINA', 5407, -7.025016, 134.664671, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Santander', 'Calle de Ávila', 1104, 'Valencia', 'ENTRE_RIOS', 'ARGENTINA', 8412, -58.564669, 48.961183, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Vigo', 'Calle de Burgos', 1855, 'Zaragoza', 'MENDOZA', 'ARGENTINA', 9721, 34.734234, 81.350179, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Bilbao', 'Calle de Gerona', 1249, 'Bilbao', 'SALTA', 'ARGENTINA', 2086, -65.355126, -122.805937, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio León', 'Calle de Tarragona', 1633, 'Cádiz', 'CHACO', 'ARGENTINA', 6201, 8.514512, -80.260972, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Granada', 'Calle de Zaragoza', 1413, 'Murcia', 'JUJUY', 'ARGENTINA', 3192, 54.087526, 124.883907, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Logroño', 'Calle de Ceuta', 1030, 'Almería', 'SAN_JUAN', 'ARGENTINA', 1050, 9.232693, 63.947933, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Sevilla', 'Calle de Huelva', 684, 'Salamanca', 'TIERRA_DEL_FUEGO', 'ARGENTINA', 7044, -31.810375, 35.701632, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Cuenca', 'Calle de Pamplona', 1867, 'Pamplona', 'LA_PAMPA', 'ARGENTINA', 4890, 21.318371, 105.430218, FALSE, NOW(), NOW());

INSERT INTO locations (fantasy_name, street_address, number, city, province, country, postal_code, latitude, longitude, soft_delete, creation_date, update_date)
VALUES ('Espacio Teruel', 'Calle de Lugo', 1218, 'Sevilla', 'NEUQUEN', 'ARGENTINA', 4937, -27.453888, 29.508115, FALSE, NOW(), NOW());


-- GUESTS
INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Carla', 'González', 'carla.g@example.com', 'DNI', '30555123', '1990-05-12', '1134567890', false, NOW(), NOW());
INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Luis', 'Martínez', 'luis.m@example.com', 'DNI', '28900234', '1985-09-22', '1122334455', false, NOW(), NOW());
INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Sandra', 'López', 'sandra.l@example.com', 'DNI', '31233456', '1992-03-30', '1199887766', false, NOW(), NOW());
INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Cristina', 'Gómez', 'guerreroerica@example.org', 'DNI', '87510428', '1993-03-23', '685218920', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Raúl', 'Jiménez', 'luis58@example.org', 'DNI', '56310269', '1971-12-06', '609874059', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Sofía', 'Ortega', 'lidia90@example.org', 'DNI', '96063201', '1991-08-05', '610204297', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Mario', 'Castillo', 'ricardorodriguez@example.net', 'DNI', '80326157', '1970-09-28', '626982888', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Teresa', 'Sanz', 'manuel06@example.com', 'DNI', '53558060', '1984-03-13', '610302434', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Manuel', 'Sánchez', 'guillermorivera@example.net', 'DNI', '72527443', '1973-07-22', '669169029', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Pilar', 'Blanco', 'monicacano@example.net', 'DNI', '81379060', '1983-04-11', '680911365', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Fernando', 'Ramírez', 'irene68@example.org', 'DNI', '82842907', '1988-02-17', '681232670', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Laura', 'Hernández', 'natalia76@example.org', 'DNI', '29299071', '1981-06-09', '608343120', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Antonio', 'Giménez', 'raulgonzalez@example.com', 'DNI', '90505906', '1994-10-30', '620985769', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Andrea', 'Crespo', 'lorenagonzalez@example.net', 'DNI', '34200785', '1985-06-14', '616908077', false, NOW(), NOW());

INSERT INTO guests (first_name, last_name, email, document_type, document_number, birth_date, phone_number, soft_delete, creation_date, update_date)
VALUES ('Ángel', 'Silva', 'angelicadiaz@example.com', 'DNI', '92454035', '1992-01-07', '604256155', false, NOW(), NOW());

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
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu, address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-01-15', 'Organizador', 'Federico', 'Ramírez', 'DNI', '30500123', 'buraschi.mateo@gmail.com', '20230500123', '1990-03-10', 'fed.ram.mp', 'Av. Libertador 1010', '3511111111', false, NOW(), NOW(), 1);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-09-30', 'Coordinador', 'Lucía', 'Fernández', 'DNI', '28999555', 'lucia.f@example.com', '20228999555', '1987-08-15', 'luc.fer.mp', 'Av. Libertador 1010','3511111112', false, NOW(), NOW(), 2);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-05-12', 'Técnico Sonido', 'Martín', 'Luna', 'DNI', '27888555', 'martin.l@example.com', '20227888555', '1985-12-01', 'mar.lun.mp', 'Av. Libertador 1010','3511111113', false, NOW(), NOW(), 3);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2023-01-05', 'Asistente', 'Ana', 'Torres', 'DNI', '30233456', 'ana.torres@example.com', '20302334567', '1992-04-21', 'ana.tor.mp', 'Av. Libertador 1010','3511111114', false, NOW(), NOW(), 5);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-02-11', 'Coordinador', 'Bruno', 'Acosta', 'DNI', '30344567', 'bruno.acosta@example.com', '20303445677', '1991-11-02', 'bru.acos.mp', 'Av. Libertador 1010','3511111115', false, NOW(), NOW(), 6);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-03-20', 'Organizador', 'Camila', 'Silva', 'DNI', '30455678', 'camila.silva@example.com', '20304556787', '1993-06-17', 'cam.sil.mp', 'Av. Libertador 1010','3511111116', false, NOW(), NOW(), 7);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-11-30', 'Técnico', 'Diego', 'Méndez', 'DNI', '30566789', 'diego.mendez@example.com', '20305667897', '1989-12-29', 'die.men.mp', 'Av. Libertador 1010','3511111117', false, NOW(), NOW(), 8);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-04-10', 'Supervisora', 'Elena', 'Rivas', 'DNI', '30677890', 'elena.rivas@example.com', '20306778907', '1990-08-13', 'ele.riv.mp', 'Av. Libertador 1010','3511111118', false, NOW(), NOW(), 9);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2019-05-12', 'Asistente', 'Facundo', 'Herrera', 'DNI', '30788901', 'facundo.herrera@example.com', '20307889017', '1988-03-05', 'fac.her.mp', 'Av. Libertador 1010','3511111119', false, NOW(), NOW(), 10);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2023-01-18', 'Coordinadora', 'Gisela', 'Morales', 'DNI', '30899012', 'gisela.morales@example.com', '20308990127', '1991-09-07', 'gis.mor.mp', 'Av. Libertador 1010','3511111120', false, NOW(), NOW(), 11);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2022-12-01', 'Montaje', 'Hernán', 'Gómez', 'DNI', '30910123', 'hernan.gomez@example.com', '20309101237', '1986-01-26', 'her.gom.mp', 'Av. Libertador 1010','3511111121', false, NOW(), NOW(), 12);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-06-15', 'Supervisora', 'Ivana', 'Sosa', 'DNI', '31021234', 'ivana.sosa@example.com', '20310212347', '1992-02-10', 'iva.sos.mp', 'Av. Libertador 1010','3511111122', false, NOW(), NOW(), 13);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2021-07-03', 'Organizador', 'Jorge', 'Cabrera', 'DNI', '31132345', 'jorge.cabrera@example.com', '20311323457', '1994-04-19', 'jor.cab.mp', 'Av. Libertador 1010','3511111123', false, NOW(), NOW(), 14);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-10-22', 'Recepción', 'Karina', 'Leiva', 'DNI', '31243456', 'karina.leiva@example.com', '20312434567', '1989-05-25', 'kar.lei.mp', 'Av. Libertador 1010','3511111124', false, NOW(), NOW(), 15);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-11-11', 'Montaje', 'Luis', 'Funes', 'DNI', '31354567', 'luis.funes@example.com', '20313545677', '1987-06-30', 'lui.fun.mp', 'Av. Libertador 1010','3511111125', false, NOW(), NOW(), 16);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2020-01-27', 'Sonido', 'María', 'Paz', 'DNI', '31465678', 'maria.paz@example.com', '20314656787', '1995-07-12', 'mar.paz.mp', 'Av. Libertador 1010','3511111126', false, NOW(), NOW(), 17);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2019-08-09', 'Asistente', 'Nicolás', 'Barrios', 'DNI', '31576789', 'nicolas.barrios@example.com', '20315767897', '1990-10-04', 'nic.bar.mp', 'Av. Libertador 1010','3511111127', false, NOW(), NOW(), 18);
INSERT INTO employees (hire_date, position, first_name, last_name, document_type, document_number, email, cuit, birth_date, bank_alias_cbu,  address, phone_number, soft_delete, creation_date, update_date, id_user)
VALUES ('2023-03-01', 'Recepción', 'Oriana', 'Delgado', 'DNI', '31687890', 'oriana.delgado@example.com', '20316878907', '1991-03-22', 'ori.del.mp', 'Av. Libertador 1010','3511111128', false, NOW(), NOW(), 19);

-- SUPPLIERS
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Delicias SRL', '30711888762', 'buraschi.mateo@gmail.com', '1134567890', 'delicias.banco', 'CATERING', 'Av. Libertador 1010', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Musical SRL', '30711999123', 'musica@eventos.com', '1145642312', 'musical.eventos', 'SOUND', 'Calle Música 123', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Decorarte', '30711777001', 'info@decorarte.com', '1156781234', 'decorarte.cbualias', 'DECORATION', 'San Juan 321', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Andreu PLC SRL', '60826673737', 'roldanmarcos@escamilla.com', '875169933', 'andreu_plc_srl.alias', 'CATERING', 'Ronda Adelaida Peláez 24 Piso 9', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Oliva-Bautista SRL', '12470279942', 'babascal@crespi.es', '3473144657', 'oliva-bautista_srl.alias', 'SOUND', 'Pasaje de Matías Valdés 310', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Elorza Inc SRL', '16492751702', 'aroaamoros@anguita.es', '988731142', 'elorza_inc_srl.alias', 'SOUND', 'Cuesta Sonia Bello 10 Apt. 21', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Lledó, Madrigal and Cabrera SRL', '90257837188', 'maximiano04@segura-puerta.org', '601550497', 'lledó_madrigal_and_cabrera_srl.alias', 'CATERING', 'Avenida Trini Alonso 72 Apt. 71', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Barrio, García and Bueno SRL', '87234720229', 'vsanmiguel@puente.es', '741674294', 'barrio_garcía_and_bueno_srl.alias', 'FURNITURE', 'Acceso Cebrián Heredia 59', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Plaza LLC SRL', '99639616186', 'natalio95@borrego-gabaldon.com', '978553032', 'plaza_llc_srl.alias', 'DECORATION', 'Cañada de Dafne Dominguez 36', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Martinez-Canales SRL', '21920440098', 'poumaximo@palmer.com', '864435514', 'martinez-canales_srl.alias', 'CATERING', 'Pasadizo Juan Bautista Murcia 71 Apt. 31', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Nevado-Ballesteros SRL', '70498660274', 'jose-luishidalgo@marti.com', '722885255', 'nevado-ballesteros_srl.alias', 'FURNITURE', 'Rambla de Julio Barco 234', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Mora-Lobo SRL', '59914257474', 'pujolche@uria.org', '905440547', 'mora-lobo_srl.alias', 'ENTERTAINMENT', 'Vial de Amaro Aguado 72', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Alcántara Inc SRL', '28740588954', 'socorro15@infante-amigo.es', '654421187', 'alcántara_inc_srl.alias', 'CATERING', 'C. de Gabino Jimenez 36 Puerta 3', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Roig PLC SRL', '32505541414', 'amaliacal@zaragoza-arranz.es', '600483199', 'roig_plc_srl.alias', 'SOUND', 'Pasadizo de Trini Garcés 1 Piso 0', false, NOW(), NOW());
INSERT INTO suppliers (name, cuit, email, phone_number, bank_alias_cbu, supplier_type, address, soft_delete, creation_date, update_date)
VALUES ('Alemán LLC SRL', '57100183550', 'maristela59@santamaria-morata.net', '731851950', 'alemán_llc_srl.alias', 'DECORATION', 'Pasaje de Dominga Palau 2', false, NOW(), NOW());

-- CRONOGRAMAS
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date)
VALUES ('Cronograma Producto', 'Cronograma del evento de lanzamiento de producto.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date)
VALUES ('Cronograma Boda', 'Cronograma detallado para la boda de primavera.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date)
VALUES ('Cronograma Cena Anual', 'Cronograma para la cena de fin de año.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date)
VALUES ('Cronograma Congreso', 'Cronograma de actividades del congreso de tecnología.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date)
VALUES ('Cronograma Festival', 'Cronograma para el festival de música local.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date)
VALUES ('Cronograma Torneo Pádel', 'Cronograma del torneo amateur de pádel.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 7', 'Cronograma generado automáticamente número 7.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 8', 'Cronograma generado automáticamente número 8.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 9', 'Cronograma generado automáticamente número 9.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 10', 'Cronograma generado automáticamente número 10.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 11', 'Cronograma generado automáticamente número 11.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 12', 'Cronograma generado automáticamente número 12.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 13', 'Cronograma generado automáticamente número 13.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 14', 'Cronograma generado automáticamente número 14.', false, NOW(), NOW());
INSERT INTO time_schedules (title, description, soft_delete, creation_date, update_date) VALUES ('Cronograma Extra 15', 'Cronograma generado automáticamente número 15.', false, NOW(), NOW());

-- ACCOUNTS
INSERT INTO accounts (
    balance, soft_delete, creation_date, update_date
) VALUES
      (-150000.00, false, NOW(), NOW()),
      (50000.00, false, NOW(), NOW()),
      (75000.50, false, NOW(), NOW()),
      (100000.00, false, NOW(), NOW()),
      (30000.00, false, NOW(), NOW()),
      (42000.75, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (30983.03, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (142907.34, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (-28337.81, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (-174149.61, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (-64444.42, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (52046.27, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (139506.39, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (-90855.76, false, NOW(), NOW());
INSERT INTO accounts (balance, soft_delete, creation_date, update_date) VALUES (137972.5, false, NOW(), NOW());

-- CONCEPTS
INSERT INTO concepts (
    id_account, accounting_date, concept, comments, amount, id_file, file_content_type,
    soft_delete, creation_date, update_date
) VALUES
      (1, '2025-05-01 10:00:00', 'STAFF', 'Pago inicial del cliente', -50000.00, NULL, 'application/pdf', false, NOW(), NOW()),
      (1, '2025-05-05 12:00:00', 'CATERING', 'Costo de catering externo', -25000.00, NULL, 'application/pdf',false, NOW(), NOW()),
      (1, '2025-10-10 09:30:00', 'FEE', 'Primer pago para reserva de locación', -20000.00, NULL, 'application/pdf',false, NOW(), NOW()),
      (1, '2025-12-01 15:45:00', 'EVENT_HALL', 'Cena para 50 personas', -30000.50, NULL, 'application/pdf',false, NOW(), NOW()),
      (1, '2025-08-01 08:00:00', 'PAYMENT', 'Adelanto para alquiler de espacio', 60000.00, NULL, 'application/pdf',false, NOW(), NOW()),
      (1, '2025-09-01 14:20:00', 'DRINK_BAR', 'Pago por sonido e iluminación', -18000.00, NULL, 'application/pdf',false, NOW(), NOW()),
      (1, '2025-11-01 11:15:00', 'SPEAKERS', 'Servicio de almuerzo durante torneo', -12000.75, NULL, 'application/pdf',false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-10-08 00:00:00', 'DECORATION', 'Error aliquid nam.', 83950.94, NULL, 'application/pdf',false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-10-10 00:00:00', 'CATERING', 'Officia esse perferendis.', 81315.94, NULL, 'application/pdf', false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-08-10 00:00:00', 'FEE', 'Vel incidunt ad dolore ad.', -32136.29, NULL, 'application/pdf',false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-12-12 00:00:00', 'SPEAKERS', 'Quidem veritatis nobis.', 72180.45, NULL, 'application/pdf',false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-12-01 00:00:00', 'STAFF', 'Fugiat illo.', 3845.29, NULL, 'application/pdf',false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-09-05 00:00:00', 'CATERING', 'Dolorem culpa aliquid.', -41206.19, NULL, 'application/pdf',false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-11-28 00:00:00', 'FEE', 'Soluta assumenda est.', 62521.88, NULL, 'application/pdf',false, NOW(), NOW());
INSERT INTO concepts (id_account, accounting_date, concept, comments, amount, id_file, file_content_type, soft_delete, creation_date, update_date) VALUES (1, '2025-09-21 00:00:00', 'FEE', 'Deserunt occaecati ullam blanditiis.', 15872.53, NULL, 'application/pdf',false, NOW(), NOW());

-- EVENTS
INSERT INTO events (
    title, description, event_type, start_date, end_date, status,
    soft_delete, creation_date, update_date, id_client, id_location,
    id_time_schedule, id_account
) VALUES
      ('Lanzamiento Producto', 'Evento de presentación del nuevo producto.', 'CORPORATE',
       '2025-05-10 18:00:00', '2025-05-10 23:00:00', 'CONFIRMED',
       false, NOW(), NOW(), 1, 2, 1, 1),
      ('Boda Primavera', 'Casamiento en jardines del sur.', 'SOCIAL',
       '2025-10-20 17:00:00', '2025-10-21 03:00:00', 'IN_PROGRESS',
       false, NOW(), NOW(), 2, 1, 2, 2),
      ('Cena Fin de Año', 'Cena de fin de año para empleados y directivos.', 'SPORTS',
       '2025-12-15 20:00:00', '2025-12-16 02:00:00', 'SUSPENDED',
       false, NOW(), NOW(), 3, 3, 3, 3),
      ('Congreso de Tecnología', 'Evento anual de innovación y tecnología.', 'CORPORATE',
       '2025-08-05 09:00:00', '2025-08-05 18:00:00', 'CONFIRMED',
       false, NOW(), NOW(), 1, 1, 4, 4),
      ('Festival de Música', 'Festival de bandas emergentes y artistas locales.', 'SOCIAL',
       '2025-09-14 14:00:00', '2025-09-14 23:59:00', 'CONFIRMED',
       false, NOW(), NOW(), 2, 2, 5, 5),
      ('Torneo de Pádel', 'Competencia de pádel amateur en formato doble.', 'SPORTS',
       '2025-11-10 10:00:00', '2025-11-10 20:00:00', 'IN_PROGRESS',
       false, NOW(), NOW(), 3, 3, 6, 6);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 7', 'Descripción generada automáticamente del evento 7.', 'SOCIAL', '2025-10-19 00:00:00', '2025-10-19 10:00:00', 'IN_PROGRESS', false, NOW(), NOW(), 2, 1, 7, 7);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 8', 'Descripción generada automáticamente del evento 8.', 'SPORTS', '2025-11-22 00:00:00', '2025-11-22 03:00:00', 'CONFIRMED', false, NOW(), NOW(), 3, 3, 8, 8);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 9', 'Descripción generada automáticamente del evento 9.', 'CORPORATE', '2025-06-22 00:00:00', '2025-06-22 09:00:00', 'SUSPENDED', false, NOW(), NOW(), 3, 3, 9, 9);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 10', 'Descripción generada automáticamente del evento 10.', 'SPORTS', '2025-08-21 00:00:00', '2025-08-21 03:00:00', 'CONFIRMED', false, NOW(), NOW(), 2, 3, 10, 10);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 11', 'Descripción generada automáticamente del evento 11.', 'SPORTS', '2025-06-19 00:00:00', '2025-06-19 07:00:00', 'CONFIRMED', false, NOW(), NOW(), 2, 1, 11, 11);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 12', 'Descripción generada automáticamente del evento 12.', 'SOCIAL', '2025-07-31 00:00:00', '2025-07-31 07:00:00', 'SUSPENDED', false, NOW(), NOW(), 3, 2, 12, 12);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 13', 'Descripción generada automáticamente del evento 13.', 'SPORTS', '2025-07-29 00:00:00', '2025-07-29 05:00:00', 'CONFIRMED', false, NOW(), NOW(), 3, 1, 13, 13);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 14', 'Descripción generada automáticamente del evento 14.', 'CORPORATE', '2025-06-27 00:00:00', '2025-06-27 07:00:00', 'CONFIRMED', false, NOW(), NOW(), 3, 2, 14, 14);
INSERT INTO events (title, description, event_type, start_date, end_date, status, soft_delete, creation_date, update_date, id_client, id_location, id_time_schedule, id_account) VALUES ('Evento Especial 15', 'Descripción generada automáticamente del evento 15.', 'CORPORATE', '2025-11-02 00:00:00', '2025-11-02 03:00:00', 'CONFIRMED', false, NOW(), NOW(), 1, 1, 15, 15);

-- TASKS
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Reservar Catering', 'Confirmar y reservar servicio de catering.', 'PENDING', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Confirmar Invitaciones', 'Revisar lista de invitados y enviar mails.', 'IN_PROGRESS', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Montar Escenario', 'Instalar escenario y sonido.', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event)
VALUES ('Comprar Pan', 'Casero con chicharron', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 5', 'Descripción de la tarea 5', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 6', 'Descripción de la tarea 6', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 7', 'Descripción de la tarea 7', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 8', 'Descripción de la tarea 8', 'PENDING', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 9', 'Descripción de la tarea 9', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 10', 'Descripción de la tarea 10', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 11', 'Descripción de la tarea 11', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 12', 'Descripción de la tarea 12', 'COMPLETED', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 13', 'Descripción de la tarea 13', 'PENDING', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 14', 'Descripción de la tarea 14', 'PENDING', false, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, status, soft_delete, creation_date, update_date, id_event) VALUES ('Tarea Automática 15', 'Descripción de la tarea 15', 'COMPLETED', false, NOW(), NOW(), 2);

-- TIMING
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule)
VALUES ('2025-05-10 17:00:00', 1, 2);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule)
VALUES ('2025-05-10 16:00:00', 2, 2);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule)
VALUES ('2025-05-10 19:00:00', 3, 2);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-09-10 00:00:00', 12, 6);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-09-21 00:00:00', 3, 3);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-06-12 00:00:00', 6, 4);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-07-09 00:00:00', 2, 1);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-10-07 00:00:00', 6, 3);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-08-04 00:00:00', 4, 4);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-07-23 00:00:00', 6, 1);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-11-24 00:00:00', 10, 3);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-10-29 00:00:00', 8, 2);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-11-05 00:00:00', 10, 2);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-12-25 00:00:00', 12, 1);
INSERT INTO scheduled_tasks (scheduled_time, id_task, id_time_schedule) VALUES ('2025-07-31 00:00:00', 8, 3);


-- PAGOS
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date)
VALUES
    (NOW(), 1, 15000.00, 'Pago inicial por evento empresarial', 'PAID', NOW(), NOW()),
    (NOW(), 2, 10000.00, 'Anticipo para evento social', 'PENDING_PAYMENT', NOW(), NOW()),
    (NOW(), 3, 20000.00, 'Pago completo para evento privado', 'PAID', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-11-21', 3, 21377.19, 'Pago generado 4', 'PAID', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-11-23', 1, 17071.18, 'Pago generado 5', 'PAID', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-07-21', 1, 22969.14, 'Pago generado 6', 'PAID', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-09-20', 2, 17890.63, 'Pago generado 7', 'PENDING_PAYMENT', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-12-02', 2, 18507.31, 'Pago generado 8', 'PENDING_PAYMENT', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-09-13', 3, 22672.36, 'Pago generado 9', 'PENDING_PAYMENT', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-11-30', 1, 11040.81, 'Pago generado 10', 'PENDING_PAYMENT', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-10-22', 2, 17328.13, 'Pago generado 11', 'PAID', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-11-09', 2, 7450.48, 'Pago generado 12', 'PENDING_PAYMENT', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-06-27', 2, 24473.45, 'Pago generado 13', 'PAID', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-07-31', 2, 23834.11, 'Pago generado 14', 'PAID', NOW(), NOW());
INSERT INTO payments (payment_date, id_client, amount, detail, status, creation_date, update_date) VALUES ('2025-12-07', 1, 23875.23, 'Pago generado 15', 'PAID', NOW(), NOW());

-- FILES
INSERT INTO files (
    file_type, file_name, file_content_type, file_url, review_note,
    creation_date, update_date,
    id_supplier, id_client, id_employee, id_payment
)
VALUES
('RECEIPT', 'dni_document_back_sample.jpg', 'image/jpg', 'http://localhost:8080/event-files/1', 'Validado por contabilidad', NOW(), NOW(), NULL, NULL, NULL, 1),
('BILLING', 'dni_document_front_sample.jpg', 'image/jpg', 'http://localhost:8080/event-files/2', 'Pendiente de auditoría', NOW(), NOW(), NULL, NULL, NULL, 2),
('PAYMENT', 'purchase_sale_sample.pdf', 'application/pdf', 'http://localhost:8080/event-files/3', 'Confirmado', NOW(), NOW(), NULL, NULL, NULL, 3),
('OTHER', 'propuesta_decorarte.pdf', 'application/pdf', 'https://minio.local/files/propuesta_decorarte.pdf', 'Versión preliminar', NOW(), NOW(), 3, NULL, NULL, NULL),
('RECEIPT', 'recibo_decorarte_enero.pdf', 'application/pdf', 'https://minio.local/files/recibo_decorarte_enero.pdf', 'Escaneado', NOW(), NOW(), 3, NULL, NULL, NULL);

INSERT INTO files (
    file_type, file_name, file_content_type, file_url, review_note,
    creation_date, update_date,
    id_supplier, id_client, id_employee, id_payment
)
VALUES
    ('OTHER', 'contrato_cliente1.pdf', 'application/pdf', 'https://minio.local/files/contrato_cliente1.pdf', 'Contrato firmado', NOW(), NOW(), NULL, 1, NULL, NULL),
    ('RECEIPT', 'pago_cliente1_marzo.pdf', 'application/pdf', 'https://minio.local/files/pago_cliente1_marzo.pdf', 'Pago registrado', NOW(), NOW(), NULL, 1, NULL, NULL),
    ('BILLING', 'factura_cliente2_abril.pdf', 'application/pdf', 'https://minio.local/files/factura_cliente2_abril.pdf', 'Facturación manual', NOW(), NOW(), NULL, 2, NULL, NULL),
    ('OTHER', 'documentacion_cliente2.pdf', 'application/pdf', 'https://minio.local/files/documentacion_cliente2.pdf', 'Documentación adicional', NOW(), NOW(), NULL, 2, NULL, NULL);

INSERT INTO files (
    file_type, file_name, file_content_type, file_url, review_note,
    creation_date, update_date,
    id_supplier, id_client, id_employee, id_payment
)
VALUES
    ('PAYMENT', 'recibo_sueldo_emp1.pdf', 'application/pdf', 'https://minio.local/files/recibo_sueldo_emp1.pdf', 'Abril 2025', NOW(), NOW(), NULL, NULL, 1, NULL),
    ('PAYMENT', 'recibo_sueldo_emp2.pdf', 'application/pdf', 'https://minio.local/files/recibo_sueldo_emp2.pdf', 'Marzo 2025', NOW(), NOW(), NULL, NULL, 2, NULL),
    ('OTHER', 'certificado_emp3.pdf', 'application/pdf', 'https://minio.local/files/certificado_emp3.pdf', 'Curso completado', NOW(), NOW(), NULL, NULL, 3, NULL);

INSERT INTO files (
    file_type, file_name, file_content_type, file_url, review_note,
    creation_date, update_date,
    id_supplier, id_client, id_employee, id_payment
)
VALUES
    ('OTHER', 'contrato_cliente1.pdf', 'application/pdf', 'https://minio.local/files/contrato_cliente1.pdf', 'Contrato firmado', NOW(), NOW(), 1, NULL, NULL, NULL),
    ('RECEIPT', 'pago_cliente1_marzo.pdf', 'application/pdf', 'https://minio.local/files/pago_cliente1_marzo.pdf', 'Pago registrado', NOW(), NOW(), 1, NULL, NULL, NULL),
    ('BILLING', 'factura_cliente2_abril.pdf', 'application/pdf', 'https://minio.local/files/factura_cliente2_abril.pdf', 'Facturación manual', NOW(), NOW(), NULL, NULL, NULL, 2),
    ('OTHER', 'documentacion_cliente2.pdf', 'application/pdf', 'https://minio.local/files/documentacion_cliente2.pdf', 'Documentación adicional', NOW(), NOW(), NULL, NULL, NULL, 2);
INSERT INTO files (file_type, file_name, file_content_type, file_url, review_note, creation_date, update_date, id_supplier, id_client, id_employee, id_payment)
VALUES ('RECEIPT', 'archivo_14.pdf', 'application/pdf', 'https://minio.local/files/archivo_14.pdf', 'Nota archivo 14', NOW(), NOW(), 1, NULL, NULL, NULL);
INSERT INTO files (file_type, file_name, file_content_type, file_url, review_note, creation_date, update_date, id_supplier, id_client, id_employee, id_payment)
VALUES ('PAYMENT', 'archivo_15.pdf', 'application/pdf', 'https://minio.local/files/archivo_15.pdf', 'Nota archivo 15', NOW(), NOW(), 3, NULL, NULL, NULL);

-- RELACIONES EVENTO - SUPPLIERS
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (1, 1, 'DUE', 1000.0, 500.0, 'TRANSFER', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (2, 2, 'PAID', 1500.0, 0.0, 'CASH', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (2, 3, 'DUE', 1200.0, 600.0, 'TRANSFER', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date)
VALUES (3, 1, 'PARTIALLY_PAID', 2000.0, 1000.0, 'CARD', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (2, 5, 'PARTIALLY_PAID', 974.43, 444.77, 'TRANSFER', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (4, 13, 'DUE', 2555.11, 132.57, 'CASH', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (2, 7, 'PARTIALLY_PAID', 1172.42, 949.15, 'CASH', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (6, 10, 'PAID', 1749.85, 1203.27, 'CARD', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (1, 6, 'PAID', 1578.58, 402.46, 'TRANSFER', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (6, 6, 'PAID', 1180.85, 141.88, 'TRANSFER', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (3, 14, 'DUE', 2881.93, 2562.12, 'CASH', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (6, 13, 'PAID', 2999.58, 2043.23, 'CARD', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (1, 10, 'DUE', 1102.96, 426.08, 'CASH', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (6, 8, 'DUE', 1027.15, 549.75, 'CASH', NOW(), NOW());
INSERT INTO events_suppliers (id_event, id_supplier, status, amount, balance, payment, creation_date, update_date) VALUES (5, 13, 'DUE', 1578.58, 1117.73, 'CASH', NOW(), NOW());

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
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (1, 6, 'DUE', 1105.68, 731.54, 'CARD', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (1, 7, 'PAID', 1903.76, 344.25, 'CASH', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (4, 8, 'DUE', 2383.69, 110.54, 'CARD', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (2, 9, 'DUE', 1906.07, 657.9, 'CARD', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (4, 10, 'PARTIALLY_PAID', 848.63, 680.95, 'TRANSFER', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (2, 11, 'PAID', 2033.68, 90.32, 'TRANSFER', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (4, 12, 'DUE', 1315.09, 497.9, 'CASH', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (3, 13, 'PARTIALLY_PAID', 2251.28, 875.77, 'CARD', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (5, 14, 'PAID', 2176.37, 1829.32, 'CARD', NOW(), NOW());
INSERT INTO events_employees (id_event, id_employee, status, amount, balance, payment, creation_date, update_date) VALUES (4, 15, 'DUE', 1790.62, 449.44, 'CARD', NOW(), NOW());

-- RELACIONES EVENTO - GUESTS
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (1, 1, 'REGULAR', 'Amiga cercana de los novios', NULL, NOW(), false, 'A', '1', 5, false, NULL, NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (1, 2, 'REGULAR', 'Amiga de los novios', NULL, NOW(), false, 'A', '1', 6, true, 'Vegetariana', NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (2, 1, 'REGULAR', 'Amiga de los novios', NULL, NOW(), false, 'B', '2', 1, false, NULL, NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (2, 3, 'VIP', 'Amiga cercana de los padres', NULL, NOW(), true, 'VIP', '1', 1, true, 'Sin gluten', NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (3, 2, 'FRIEND', 'Familiar', NULL, NOW(), false, 'B', '3', 2, false, NULL, NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (3, 3, 'REGULAR', 'Familiar de la novia', NULL, NOW(), true, 'C', '4', 3, true, 'Vegana', NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (3, 7, 'VIP', 'Invitado auto 7', NULL, '2025-12-19', true, 'C', '2', 2, true, 'Sin gluten', NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (5, 8, 'FRIEND', 'Invitado auto 8', NULL, '2025-09-09', false, 'A', '1', 1, false, NULL, NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (1, 9, 'REGULAR', 'Invitado auto 9', NULL, '2025-07-20', false, 'VIP', '4', 9, false, NULL, NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (6, 10, 'REGULAR', 'Invitado auto 10', NULL, '2025-08-15', false, 'VIP', '2', 4, true, 'Sin gluten', NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (1, 11, 'REGULAR', 'Invitado auto 11', NULL, '2025-11-05', false, 'VIP', '1', 5, false, NULL, NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (1, 12, 'FRIEND', 'Invitado auto 12', NULL, '2025-09-09', true, 'VIP', '5', 5, true, 'Sin gluten', NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (4, 13, 'REGULAR', 'Invitado auto 13', NULL, '2025-06-23', true, 'VIP', '5', 7, true, 'Sin gluten', NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (3, 14, 'FRIEND', 'Invitado auto 14', NULL, '2025-09-02', false, 'C', '1', 10, false, NULL, NOW(), NOW());
INSERT INTO events_guests (id_event, id_guest, type, note, access_type, action_date, is_late, sector, row_table, seat, food_restriction, food_description, creation_date, update_date)
VALUES (1, 15, 'REGULAR', 'Invitado auto 15', NULL, '2025-10-25', true, 'B', '1', 10, false, NULL, NOW(), NOW());

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
INSERT INTO templates (name, body, active) VALUES
    ('pago_pendiente_cliente',
     '<!DOCTYPE html>
     <html lang="es">
     <head>
       <meta charset="UTF-8">
       <meta name="viewport" content="width=device-width, initial-scale=1.0">
       <title>Pago Pendiente</title>
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
           <h1>Pago Pendiente</h1>
         </div>
         <div class="content">
           <p>Hola <strong>{{FIRST_NAME}} {{LAST_NAME}}</strong>,</p>
           <p>Se ha generado un nuevo pago pendiente por un importe de <strong>${{AMOUNT}}</strong>.</p>
           <p>Podés realizar el pago desde el siguiente enlace:</p>
           <p>
             <a href="{{PAYMENT_URL}}" class="button-accent">Pagar Ahora</a>
           </p>
           <p>Gracias por confiar en <strong>ERP Eventos</strong>.</p>
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
    ('invitacion_evento',
     '<!DOCTYPE html>
     <html lang="es">
     <head>
       <meta charset="UTF-8">
       <meta name="viewport" content="width=device-width, initial-scale=1.0">
       <title>Invitación al Evento</title>
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
           <h1>¡Estás invitado!</h1>
         </div>
         <div class="content">
           <p>Hola <strong>{{NAME}} {{LAST_NAME}}</strong>,</p>
           <p>Te invitamos cordialmente al evento <strong>{{EVENT}}</strong>.</p>
           <p>El evento se llevará a cabo el día <strong>{{DATE}}</strong> en <strong>{{PLACE}}</strong>.</p>
           <p>¡Esperamos contar con tu presencia!</p>
           <p>Gracias por formar parte de <strong>ERP Eventos</strong>.</p>
         </div>
         <div class="footer">
           <hr>
           <p>&copy; 2025 ERP Eventos</p>
         </div>
       </div>
     </body>
     </html>', true);





