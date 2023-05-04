CREATE TABLE IF NOT EXISTS "order" (
    id bigint PRIMARY KEY,
    status text,
    service text,
    merchant text
);