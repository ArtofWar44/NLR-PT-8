DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
    customer_id SERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    paw_points_balance DECIMAL(10, 2) DEFAULT 20.00,
    CONSTRAINT PK_customers PRIMARY KEY (customer_id)
);

CREATE TABLE items (
    item_id SERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT PK_items PRIMARY KEY (item_id)
);

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

INSERT INTO customers (name, email, paw_points_balance) VALUES
    ('John Doe', 'john.doe@example.com', 10.00),
    ('Jane Smith', 'jane.smith@example.com', 20.00),
    ('Alice Johnson', 'alice.johnson@example.com', 30.00),
    ('Bob Brown', 'bob.brown@example.com', 5.00),
    ('Catherine Williams', 'catherine.williams@example.com', 25.00),
    ('David Miller', 'david.miller@example.com', 40.00),
    ('Emily Davis', 'emily.davis@example.com', 15.00),
    ('Frank Moore', 'frank.moore@example.com', 35.00),
    ('Grace Wilson', 'grace.wilson@example.com', 50.00),
    ('Hannah Taylor', 'hannah.taylor@example.com', 10.00),
    ('Isaac Anderson', 'isaac.anderson@example.com', 20.00),
    ('Jack Thomas', 'jack.thomas@example.com', 5.00),
    ('Karen Martinez', 'karen.martinez@example.com', 30.00),
    ('Louis White', 'louis.white@example.com', 25.00),
    ('Megan Harris', 'megan.harris@example.com', 10.00),
    ('Nathan Clark', 'nathan.clark@example.com', 40.00),
    ('Olivia Lewis', 'olivia.lewis@example.com', 15.00),
    ('Paul Robinson', 'paul.robinson@example.com', 35.00),
    ('Quincy Walker', 'quincy.walker@example.com', 50.00),
    ('Rachel Hall', 'rachel.hall@example.com', 10.00),
    ('Sam Young', 'sam.young@example.com', 20.00),
    ('Tina King', 'tina.king@example.com', 5.00),
    ('Ursula Wright', 'ursula.wright@example.com', 30.00),
    ('Victor Scott', 'victor.scott@example.com', 25.00),
    ('Wendy Green', 'wendy.green@example.com', 10.00),
    ('Xander Adams', 'xander.adams@example.com', 40.00),
    ('Yvonne Baker', 'yvonne.baker@example.com', 15.00),
    ('Zachary Carter', 'zachary.carter@example.com', 35.00),
    ('Aiden Mitchell', 'aiden.mitchell@example.com', 50.00),
    ('Bella Perez', 'bella.perez@example.com', 10.00),
    ('Carlos Roberts', 'carlos.roberts@example.com', 20.00),
    ('Diana Evans', 'diana.evans@example.com', 5.00),
    ('Ethan Collins', 'ethan.collins@example.com', 30.00),
    ('Fiona Turner', 'fiona.turner@example.com', 25.00),
    ('George Parker', 'george.parker@example.com', 10.00),
    ('Holly Edwards', 'holly.edwards@example.com', 40.00),
    ('Ian Phillips', 'ian.phillips@example.com', 15.00),
    ('Jenna Campbell', 'jenna.campbell@example.com', 35.00),
    ('Kyle Sanders', 'kyle.sanders@example.com', 50.00);

SELECT * FROM customers;

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

INSERT INTO transactions (customer_id, item_id, quantity) VALUES
    ((SELECT customer_id FROM customers WHERE name = 'John Doe'), (SELECT item_id FROM items WHERE name = 'Squeaky Ball'), 1),
    ((SELECT customer_id FROM customers WHERE name = 'Jane Smith'), (SELECT item_id FROM items WHERE name = 'Knotted Rawhide'), 2),
    ((SELECT customer_id FROM customers WHERE name = 'Alice Johnson'), (SELECT item_id FROM items WHERE name = 'Jumbo'), 1),
    ((SELECT customer_id FROM customers WHERE name = 'Bob Brown'), (SELECT item_id FROM items WHERE name = 'Beef and Apple Surprise'), 3);

SELECT * FROM transactions;

