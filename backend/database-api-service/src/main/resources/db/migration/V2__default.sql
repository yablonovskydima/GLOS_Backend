USE glos_database;

INSERT IGNORE INTO access_types(id, name)
VALUES
(1, 'RW_USER_^'),
(2, 'R_USER_*'),
(3, 'RW_USER_*'),
(4, 'R_GROUP_^'),
(5, 'RW_GROUP_^'),
(6, 'R_GROUP_*'),
(7, 'RW_GROUP_*');

INSERT IGNORE INTO roles(id, name)
VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_SYS');

INSERT IGNORE INTO users(id, username, email, password_hash)
VALUES
(1, 'sys', 'global.storage.info@gmail.com', '$2a$12$J/qxhK3mIwYdAfHWGLEPEePWpOpMgB/zwFfWhW8BhFqbiQ4xo0Tbi');

INSERT IGNORE INTO users_roles(id, user_id, role_id)
VALUES
(1, 1, 3);