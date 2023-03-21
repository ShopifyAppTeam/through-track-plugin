CREATE TABLE IF NOT EXISTS "order" (
    id SERIAL PRIMARY KEY,
    shipmentId bigint NOT NULL CHECK (shipmentId > 0)
);