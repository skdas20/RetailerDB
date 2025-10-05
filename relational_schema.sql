
CREATE TABLE Brand (
  brand_id NUMBER PRIMARY KEY,
  brand_name VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE Product (
  product_id NUMBER PRIMARY KEY,
  name VARCHAR2(80) NOT NULL,
  price NUMBER(10,2),
  category VARCHAR2(40),
  brand_id NUMBER REFERENCES Brand(brand_id)
);

CREATE TABLE Store (
  store_id NUMBER PRIMARY KEY,
  store_name VARCHAR2(50),
  city VARCHAR2(50),
  state VARCHAR2(30)
);

CREATE TABLE Customer (
  cust_id NUMBER PRIMARY KEY,
  cust_name VARCHAR2(50),
  gender VARCHAR2(10),
  email VARCHAR2(80),
  phone VARCHAR2(20)
);

CREATE TABLE Vendor (
  vendor_id NUMBER PRIMARY KEY,
  vendor_name VARCHAR2(60),
  city VARCHAR2(40),
  contact VARCHAR2(20)
);

CREATE TABLE Inventory (
  store_id NUMBER REFERENCES Store(store_id),
  product_id NUMBER REFERENCES Product(product_id),
  stock_qty NUMBER,
  PRIMARY KEY (store_id, product_id)
);

CREATE TABLE Sale (
  sale_id NUMBER PRIMARY KEY,
  sale_date DATE DEFAULT SYSDATE,
  quantity NUMBER,
  total_amt NUMBER(10,2),
  cust_id NUMBER REFERENCES Customer(cust_id),
  store_id NUMBER REFERENCES Store(store_id),
  product_id NUMBER REFERENCES Product(product_id)
);

-- Junction table for Product-Vendor M:N relationship
CREATE TABLE Supplies (
  vendor_id NUMBER REFERENCES Vendor(vendor_id),
  product_id NUMBER REFERENCES Product(product_id),
  supply_price NUMBER(10,2),
  supply_date DATE DEFAULT SYSDATE,
  PRIMARY KEY (vendor_id, product_id)
);

-- Sequence for Sale table auto-increment
CREATE SEQUENCE sale_seq START WITH 505 INCREMENT BY 1;
