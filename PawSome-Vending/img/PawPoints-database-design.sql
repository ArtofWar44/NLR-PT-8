/**************************************************************************
-- Step 1: Drop all database objects to start with an empty database
**************************************************************************/
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS customers;

/**************************************************************************
-- Step 2: Create the customers table
**************************************************************************/
-- Table customersCREATE TABLE customers (
    customer_id SERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    paw_points_balance DECIMAL(10, 2) DEFAULT 20.00,
    CONSTRAINT PK_customers PRIMARY KEY (customer_id)
);

/**************************************************************************
-- Step 3: Create the items table
**************************************************************************/

CREATE TABLE items (
    item_id SERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT PK_items PRIMARY KEY (item_id)
);

/**************************************************************************
-- Step 4: Create the transactions table
**************************************************************************/

CREATE TABLE transactions (
    transaction_id SERIAL NOT NULL,
    customer_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_transactions PRIMARY KEY (transaction_id),
    CONSTRAINT FK_transactions_customers FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    CONSTRAINT FK_transactions_items FOREIGN KEY (item_id) REFERENCES items (item_id)
);

/**************************************************************************
-- Step 5: Insert test data into the customers table and select it back out.
**************************************************************************/
INSERT INTO customers (name, email, paw_points_balance) VALUES
    ('John Doe', 'john.doe@example.com', 10.00),
    ('Jane Smith', 'jane.smith@example.com', 20.00),
    ('Alice Johnson', 'alice.johnson@example.com', 30.00),
    ('Bob Brown', 'bob.brown@example.com', 5.00);

SELECT * FROM customers;

/**************************************************************************
-- Step 6: Insert test data into the items table and select it back out.
**************************************************************************/
INSERT INTO items (name, price, category, quantity) VALUES
    ('Squeaky Ball', 6.00, 'DOG_TOY', 5),
    ('Rope Tug Toy', 8.00, 'DOG_TOY', 5),
    ('Plush Chew Toy', 10.00, 'DOG_TOY', 5),
    ('Rubber Chew Toy', 10.00, 'DOG_TOY', 5),
    ('Twisted Rawhide', 6.50, 'RAWHIDE_BONE', 5),
    ('Knotted Rawhide', 8.50, 'RAWHIDE_BONE', 5),
    ('Compressed', 11.50, 'RAWHIDE_BONE', 5),
    ('Flavored', 11.75, 'RAWHIDE_BONE', 5),
    ('Standard', 14.00, 'BULLY_STICK', 5),
    ('Braided', 19.50, 'BULLY_STICK', 5),
    ('Jumbo', 15.50, 'BULLY_STICK', 5),
    ('Curly', 18.50, 'BULLY_STICK', 5),
    ('Peanut Butter Surprise', 15.00, 'MYSTERY_TREAT', 5),
    ('Cheese & Bacon Surprise', 15.00, 'MYSTERY_TREAT', 5),
    ('Chicken & Sweet Potato Surprise', 15.00, 'MYSTERY_TREAT', 5),
    ('Beef and Apple Surprise', 15.00, 'MYSTERY_TREAT', 5);

SELECT * FROM items;

/**************************************************************************
-- Step 7: Insert test data into the transactions table and select it back out.
**************************************************************************/
INSERT INTO transactions (customer_id, item_id, quantity) VALUES
    ((SELECT customer_id FROM customers WHERE name = 'John Doe'), (SELECT item_id FROM items WHERE name = 'Squeaky Ball'), 1),
    ((SELECT customer_id FROM customers WHERE name = 'Jane Smith'), (SELECT item_id FROM items WHERE name = 'Knotted Rawhide'), 2),
    ((SELECT customer_id FROM customers WHERE name = 'Alice Johnson'), (SELECT item_id FROM items WHERE name = 'Jumbo'), 1),
    ((SELECT customer_id FROM customers WHERE name = 'Bob Brown'), (SELECT item_id FROM items WHERE name = 'Beef and Apple Surprise'), 3);

SELECT * FROM transactions;
