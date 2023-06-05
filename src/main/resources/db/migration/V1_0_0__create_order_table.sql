CREATE TABLE IF NOT EXISTS "order" (
    id bigint PRIMARY KEY,
    status text,
    service text,
    merchant text
);

CREATE TABLE IF NOT EXISTS "user" (
    id_shopify bigint,
    email text PRIMARY KEY,
    password text,
    enabled boolean,
    auth_method character varying,
    update_time int,
    orders_send_time int
);
