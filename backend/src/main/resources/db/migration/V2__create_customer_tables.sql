CREATE TABLE customer (
    id                  UUID PRIMARY KEY NOT NULL,
    customer_type       VARCHAR(20)  CHECK (customer_type IN ('INDIVIDUAL', 'LEGAL_ENTITY')) NOT NULL,
    document_number     VARCHAR(14)  UNIQUE NOT NULL,
    document_type       VARCHAR(4)   CHECK (document_type IN ('CPF', 'CNPJ')) NOT NULL,
    legal_name          VARCHAR(150),
    trade_name          VARCHAR(150),
    phone               VARCHAR(20),
    email               VARCHAR(150) UNIQUE,
    state_registration  VARCHAR(20),
    status              VARCHAR(20)  CHECK (status IN ('ACTIVE', 'INACTIVE')) NOT NULL,
    created_at          TIMESTAMP    NOT NULL,
    updated_at          TIMESTAMP
);

CREATE TABLE customer_address (
    id             UUID PRIMARY KEY NOT NULL,
    customer_id    UUID         NOT NULL REFERENCES customer(id),
    address_type   VARCHAR(20)  CHECK (address_type IN ('BILLING', 'SHIPPING', 'MAIN')) NOT NULL,
    street         VARCHAR(150) NOT NULL,
    number         VARCHAR(10),
    complement     VARCHAR(100),
    neighborhood   VARCHAR(100) NOT NULL,
    city           VARCHAR(100) NOT NULL,
    state          VARCHAR(2)   NOT NULL,
    zip_code       VARCHAR(9)   NOT NULL,
    is_default     BOOLEAN      NOT NULL DEFAULT false,
    created_at     TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE INDEX idx_customer_address_customer_id ON customer_address(customer_id);