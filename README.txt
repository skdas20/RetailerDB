
Retailer Database Project - DBMS Lab Phase I
=============================================

Tech Stack:
------------
• Database: Oracle XE 21c (Docker Container)
• Interface: Java (JDBC Console App)
• JDBC Driver: ojdbc8-21.9.0.0.jar
• ER Diagram: Detailed (PDF)
• Report: Abstract + Screenshots

Prerequisites (GitHub Codespaces):
-----------------------------------
✅ Oracle XE container running (oracle-xe)
✅ Database schema created
✅ Sample data inserted
✅ JDBC driver downloaded (ojdbc8.jar)
✅ Java sources compiled

Database Connection:
--------------------
• URL: jdbc:oracle:thin:@localhost:1521/XEPDB1
• Username: system
• Password: OraclePass123
• Container: oracle-xe (Docker)

How to Run:
-----------
Simply execute:
    java -cp .:ojdbc8.jar:src RetailerApp

Note: Use semicolon (;) instead of colon (:) on Windows:
    java -cp .;ojdbc8.jar;src RetailerApp

Application Features:
---------------------
1. View Products - Display all products with price and category
2. Add Sale - Insert new sale with validation (Product, Customer, Store must exist)
3. Top Products - Show top 5 best-selling products by quantity
4. Exit - Close the application

Sample Output:
--------------
@skdas20 ➜ /workspaces/RetailerDB (main) $ java -cp .:ojdbc8.jar:src RetailerApp
Connected to Oracle successfully!

==== Retailer Database ====
1. View Products
2. Add Sale
3. Top Products
4. Exit
Enter choice: 1
101 | Pepsi 500ml | ₹45.00 | Beverage
102 | Lays Chips | ₹30.00 | Snacks
103 | Maggie Noodles | ₹25.00 | Food
105 | Nescafe Coffee | ₹150.00 | Beverage

==== Retailer Database ====
1. View Products
2. Add Sale
3. Top Products
4. Exit
Enter choice: 3
Top Selling Products:
Pepsi 500ml - 5 units
Maggie Noodles - 4 units
Lays Chips - 3 units
Nescafe Coffee - 2 units

==== Retailer Database ====
1. View Products
2. Add Sale
3. Top Products
4. Exit
Enter choice: 4
Goodbye!

Database Schema:
----------------
• Brand - Product brands (PepsiCo, Nestle, P&G)
• Product - Product catalog with prices
• Store - Retail store locations
• Customer - Customer information
• Vendor - Supplier details
• Inventory - Stock levels per store/product
• Sale - Sales transactions
• Supplies - Vendor-Product relationships

Files:
------
• src/DBConnection.java - Database connection manager
• src/RetailerApp.java - Main application with interactive menu
• relational_schema.sql - Table and sequence definitions
• insert_data.sql - Sample data
• queries.sql - Sample SQL queries
• ojdbc8.jar - Oracle JDBC driver
• Retailer_ER_Diagram.pdf - Entity-Relationship diagram
• report.pdf - Project report

Troubleshooting:
----------------
If you need to recompile:
    javac -cp ojdbc8.jar src/*.java

If Oracle container is not running:
    docker start oracle-xe
    # Wait 30 seconds for initialization

To access SQL*Plus directly:
    docker exec -it oracle-xe sqlplus system/OraclePass123@XEPDB1

