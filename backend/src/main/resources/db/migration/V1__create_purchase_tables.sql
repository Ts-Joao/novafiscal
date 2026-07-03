CREATE TABLE purchase(
    id            UUID PRIMARY KEY NOT NULL,
    customer_name VARCHAR(255)     NOT NULL,
    created_at     TIMESTAMP        NOT NULL,
    total_amount  NUMERIC(19, 2)   NOT NULL
);

CREATE TABLE purchase_item(
    id            UUID PRIMARY KEY NOT NULL,
    purchase_id   UUID             NOT NULL REFERENCES purchase(id),
    description   VARCHAR(255)     NOT NULL,
    price         NUMERIC(19, 2)   NOT NULL,
    quantity      INTEGER          NOT NULL
);