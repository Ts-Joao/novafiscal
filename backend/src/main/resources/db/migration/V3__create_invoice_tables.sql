CREATE TABLE invoice (
    id              UUID PRIMARY KEY NOT NULL,
    invoice_type    VARCHAR(10) NOT NULL,
    customer_id     UUID NOT NULL,
    purchase_id     UUID,
    status          VARCHAR(10) CHECK (status IN ('PENDING', 'SUBMITTED', 'AUTHORIZED', 'REJECTED', 'CANCELED')) NOT NULL,
    access_key      VARCHAR(44),
    protocol_number VARCHAR(50),
    total_amount    NUMERIC(15,2) NOT NULL,
    issued_at       TIMESTAMP NOT NULL,
    authorized_at   TIMESTAMP,
    canceled_at     TIMESTAMP
);

CREATE TABLE nfe_invoice (
    id                          UUID PRIMARY KEY REFERENCES invoice(id),
    customer_state_registration VARCHAR(20) NOT NULL,
    customer_document_type      VARCHAR(4) CHECK (customer_document_type IN ('CPF', 'CNPJ')) NOT NULL,
    customer_document_number    VARCHAR(14) NOT NULL,
    operation_number            VARCHAR(100) NOT NULL,
    cfop                        VARCHAR(4) NOT NULL,
    icms_amount                 NUMERIC(15,2) NOT NULL
);

CREATE TABLE nfce_invoice (
    id              UUID PRIMARY KEY REFERENCES invoice(id),
    consumer_cpf    VARCHAR(11),
    payment_method  VARCHAR(20) CHECK (payment_method IN ('PIX', 'CREDIT_CARD', 'DEBIT_CARD')) NOT NULL,
    change_amount   NUMERIC(15,2)
);

CREATE INDEX idx_invoice_customer_id ON invoice(customer_id);
CREATE INDEX idx_invoice_purchase_id ON invoice(purchase_id);