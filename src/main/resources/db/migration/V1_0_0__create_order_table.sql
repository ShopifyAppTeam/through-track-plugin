CREATE TABLE IF NOT EXISTS "order" (
    id bigint PRIMARY KEY,
    status text,
    service text,
    merchant text
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
