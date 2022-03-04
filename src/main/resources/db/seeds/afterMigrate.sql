insert into users (id, password, email,phone,user_role, name ) values
(10000000000000, '$2a$10$aGHTszCbXx4.ne9VwwWyWOq462euLoYqpImcV5vMZsZjOa6a5PBGm',
 'joelaugusto97@gmail.com', '+258846410995', 'ADMIN', 'Joel Augusto'
) ON CONFLICT (id) DO NOTHING;









