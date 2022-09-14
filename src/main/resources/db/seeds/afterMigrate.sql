insert into users (id, password, email,phone,role, name ) values
(10000000000000, '$2a$10$nqKeddeftRqIVrG4zZXfC./lOLmMCjuQISpdME6jBGqXuwS7WIII6',
 'joelaugusto97@gmail.com', '+258846410995', 'ADMIN', 'Joel Augusto'
) ON CONFLICT (id) DO NOTHING;









