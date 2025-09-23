-- Estados del cliente
INSERT IGNORE INTO client_states (id, name) VALUES (1, 'Activo');
INSERT IGNORE INTO client_states (id, name) VALUES (2, 'Restringido');

-- Estados de la herramienta
INSERT IGNORE INTO tool_states (id, name) VALUES (1, 'Disponible');
INSERT IGNORE INTO tool_states (id, name) VALUES (2, 'Prestada');
INSERT IGNORE INTO tool_states (id, name) VALUES (3, 'En reparación');
INSERT IGNORE INTO tool_states (id, name) VALUES (4, 'Dada de baja');

-- Estados del préstamo
INSERT IGNORE INTO loan_states (id, name) VALUES (1, 'En progreso');
INSERT IGNORE INTO loan_states (id, name) VALUES (2, 'Completado');
INSERT IGNORE INTO loan_states (id, name) VALUES (3, 'Atrasado');
INSERT IGNORE INTO loan_states (id, name) VALUES (4, 'Devuelto');

-- Tipos de Kardex
INSERT IGNORE INTO kardex_types (id, name) VALUES (1, 'Ingreso');
INSERT IGNORE INTO kardex_types (id, name) VALUES (2, 'Préstamo');
INSERT IGNORE INTO kardex_types (id, name) VALUES (3, 'Devolución');
INSERT IGNORE INTO kardex_types (id, name) VALUES (4, 'Baja');
INSERT IGNORE INTO kardex_types (id, name) VALUES (5, 'Reparación');
