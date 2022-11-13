INSERT INTO roles (id, name) values
     (1, 'Administrador'),
     (2, 'Agricultor'),
     (3, 'Esquicista'),
     (4, 'Vendedor de Insumos'),
     (5, 'Varejista'),
     (6, 'Distribuidor') ON CONFLICT (id) DO NOTHING;


insert into users (id, password, email,phone,role_id, name ) values
(10000000000000, '$2a$10$nqKeddeftRqIVrG4zZXfC./lOLmMCjuQISpdME6jBGqXuwS7WIII6',
 'joelaugusto97@gmail.com', '+258846410995', 1, 'Joel Augusto'
) ON CONFLICT (id) DO NOTHING;






