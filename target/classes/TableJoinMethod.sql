-- Retrieve All Transactions with Customer and Item Details
SELECT t.transaction_id, t.quantity, t.transaction_date,
       c.name AS customer_name, c.email AS customer_email,
       i.name AS item_name, i.price AS item_price, i.category AS item_category
FROM transactions t
JOIN customers c ON t.customer_id = c.customer_id
JOIN items i ON t.item_id = i.item_id;

--Retrieve All Customers Who Have Made Transactions
SELECT DISTINCT c.customer_id, c.name, c.email, c.paw_points_balance
FROM customers c
JOIN transactions t ON c.customer_id = t.customer_id;

--Retrieve All Items That Have Been Purchased
SELECT DISTINCT i.item_id, i.name, i.price, i.category, i.quantity
FROM items i
JOIN transactions t ON i.item_id = t.item_id;

--Retrieve Total Points Spent by Each Customer
SELECT c.customer_id, c.name, c.email,
       SUM(t.quantity * i.price) AS total_points_spent
FROM customers c
JOIN transactions t ON c.customer_id = t.customer_id
JOIN items i ON t.item_id = i.item_id
GROUP BY c.customer_id, c.name, c.email;

--Retrieve Transactions Made in the Last 30 Days
SELECT t.transaction_id, t.quantity, t.transaction_date,
       c.name AS customer_name, c.email AS customer_email,
       i.name AS item_name, i.price AS item_price
FROM transactions t
JOIN customers c ON t.customer_id = c.customer_id
JOIN items i ON t.item_id = i.item_id
WHERE t.transaction_date >= NOW() - INTERVAL '30 days';
