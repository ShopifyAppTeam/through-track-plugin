CREATE TABLE IF NOT EXISTS "order" (
    id bigint PRIMARY KEY,
    service text
);

CREATE TABLE IF NOT EXISTS "user" (
    id bigint PRIMARY KEY,
    id_shopify bigint,
    username text,
    email text,
    password text,
    enabled boolean,
    provider character varying,
    update_time int,
    orders_send_time int
);

CREATE TABLE IF NOT EXISTS "roles" (
    role_id bigint PRIMARY KEY
);