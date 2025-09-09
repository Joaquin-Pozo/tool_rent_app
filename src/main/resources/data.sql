-- Estados del cliente
INSERT IGNORE INTO client_states (name) VALUES ('Activo');
INSERT IGNORE INTO client_states (name) VALUES ('Restringido');

-- Estados de la herramienta
INSERT IGNORE INTO tool_states (name) VALUES ('Disponible');
INSERT IGNORE INTO tool_states (name) VALUES ('Prestada');
INSERT IGNORE INTO tool_states (name) VALUES ('En reparación');
INSERT IGNORE INTO tool_states (name) VALUES ('Dada de baja');

-- Estados del préstamo
INSERT IGNORE INTO loan_states (name) VALUES ('En progreso');
INSERT IGNORE INTO loan_states (name) VALUES ('Completado');
INSERT IGNORE INTO loan_states (name) VALUES ('Atrasado');

-- Tipos de Kardex
INSERT IGNORE INTO kardex_types (name) VALUES ('Ingreso');
INSERT IGNORE INTO kardex_types (name) VALUES ('Préstamo');
INSERT IGNORE INTO kardex_types (name) VALUES ('Devolución');
INSERT IGNORE INTO kardex_types (name) VALUES ('Baja');
INSERT IGNORE INTO kardex_types (name) VALUES ('Reparación');
