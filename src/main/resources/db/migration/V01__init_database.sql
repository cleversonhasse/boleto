CREATE TABLE BANKSLIP (
    id UUID NOT NULL,
    due_date DATE NOT NULL,
    total_in_cents int NOT NULL,
    customer VARCHAR(256) NOT NULL,
    status VARCHAR(64) NOT NULL,
    CONSTRAINT bankslip_pk PRIMARY KEY (id)
);