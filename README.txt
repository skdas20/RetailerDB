
Retailer Database Project - DBMS Lab Phase I
=============================================

Tech Stack:
------------
• Database: Oracle Live SQL / Oracle XE
• Interface: Java (JDBC Console App)
• ER Diagram: Detailed (PDF)
• Report: Abstract + Screenshots

How to Run:
------------
1. Install Oracle XE and Oracle SQL Developer.
2. Run 'relational_schema.sql' and then 'insert_data.sql' in Oracle.
3. Compile Java code:
      javac -cp .;ojdbc8.jar src/*.java
   (Use ':' instead of ';' on Mac/Linux)
4. Run the program:
      java -cp .;ojdbc8.jar src.RetailerApp
5. Explore menu options to view products, insert sales, and view analytics.

