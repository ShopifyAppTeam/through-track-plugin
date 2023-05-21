CREATE TABLE IF NOT EXISTS "order" (
    id bigint PRIMARY KEY,
    service text
);

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    id_shopify bigint,
    email text,
    password text,
    enabled boolean,
    auth_method character varying,
    update_time int,
    orders_send_time int
);

CREATE TABLE IF NOT EXISTS "roles" (
    role_id bigint PRIMARY KEY,
    user_id bigint
);