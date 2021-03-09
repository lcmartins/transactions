CREATE TABLE customer (
    id VARCHAR(250) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE account (
    id VARCHAR(250) PRIMARY KEY,
    customer_id VARCHAR(250) NOT NULL,
    balance DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE customer_transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origin_account_id VARCHAR(250) NOT NULL,
    destination_account_id VARCHAR(250) NOT NULL,
    transaction_value DECIMAL(10,2) NOT NULL,
    type VARCHAR(6) NOT NULL,
    completed BOOLEAN NOT NULL,
    control_code UUID NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    FOREIGN KEY(origin_account_id) REFERENCES account(id),
    FOREIGN KEY(destination_account_id) REFERENCES account(id)
);