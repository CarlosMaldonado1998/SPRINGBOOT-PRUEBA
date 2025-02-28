CREATE TABLE Person (
    person_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender CHAR(1) CHECK (gender IN ('M', 'F', 'O')),
    age TINYINT CHECK (age >= 0),
    identification VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    person_id INT,
    password VARCHAR(255),
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES Person(person_id),
    CONSTRAINT uq_identification UNIQUE (customer_id)  
);

CREATE TABLE Account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_type VARCHAR(50),
    initial_balance DECIMAL(15, 2),
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Transaction (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    transaction_date DATE,
	transaction_type VARCHAR(50),
    amount DECIMAL(15, 2),
    balance DECIMAL(15, 2),
    status BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES Account(account_id)
);