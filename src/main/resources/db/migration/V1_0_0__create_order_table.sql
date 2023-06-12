CREATE TABLE IF NOT EXISTS "user" (
                                      id_shopify bigint,
                                      email text PRIMARY KEY,
                                      password text,
                                      enabled boolean,
                                      auth_method character varying,
                                      update_time int,
                                      orders_send_time int
);

CREATE TABLE IF NOT EXISTS "shop" (
                                      subdomain text PRIMARY KEY,
                                      token text,
                                      shop_user VARCHAR(255),
    FOREIGN KEY (shop_user) REFERENCES "user"(email)
    );

CREATE TABLE IF NOT EXISTS "order" (
                                       id bigint PRIMARY KEY,
                                       status text,
                                       service text,
                                       merchant text,
                                       order_shop VARCHAR(255),
    FOREIGN KEY (order_shop) REFERENCES "shop"(subdomain)
    );