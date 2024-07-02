# NLR-PT-8
<b> Paw Points at Gray Skies Cafe </b>

The Paw Points application at Gray Skies Cafe is a comprehensive Java program designed to simulate a vending machine for dog-related items. It offers functionality for both customers and employees, ensuring a seamless experience for managing customer loyalty points and inventory

<b>Key Features:</b>
Customer Login and Management
Customers can log in using their name or email.
New customers can be added to the system.
Customers can view available items, purchase items using their loyalty points, and check their Paw Points balance.

<b>Item Purchase and Redemption:</b>
Customers can redeem points by entering a loyalty code.
Customers can select items for purchase, with the program updating their points balance and item quantities accordingly.

<b>Employee Management:</b>
Employees can log in to access special management features.
Employees can list, add, update, and delete customers.
Employees can list, add, update, and delete items in the inventory.
Employees can list, add, update, and delete transactions.

<b>Technical Details:</b>
The application uses the DAO (Data Access Object) pattern to interact with a PostgreSQL database for managing customer, item, and transaction data.
The database schema includes tables for customers, items, and transactions, with appropriate primary and foreign key relationships.
The application ensures efficient management of customer loyalty points and inventory, enhancing the overall customer experience at Gray Skies Cafe.
