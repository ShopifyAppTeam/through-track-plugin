CREATE TABLE IF NOT EXISTS "order" (
    id bigint PRIMARY KEY,
    service text
);

CREATE TABLE IF NOT EXISTS "user" (
    id bigint PRIMARY KEY,
    username text,
    email text,
    provider int
);
