
-- Top selling products
SELECT p.name, SUM(s.quantity) AS total_units
FROM Sale s JOIN Product p ON s.product_id=p.product_id
GROUP BY p.name ORDER BY total_units DESC FETCH FIRST 5 ROWS ONLY;

-- State-wise revenue
SELECT st.state, SUM(s.total_amt) AS revenue
FROM Sale s JOIN Store st ON s.store_id=st.store_id
GROUP BY st.state;

-- Frequent high-value customers
SELECT c.cust_name, SUM(s.total_amt) AS spent
FROM Customer c JOIN Sale s ON c.cust_id=s.cust_id
GROUP BY c.cust_name HAVING SUM(s.total_amt)>200;

-- Vendor-wise product count
SELECT v.vendor_name, COUNT(s.product_id) AS supplied_items
FROM Vendor v
LEFT JOIN Supplies s ON v.vendor_id = s.vendor_id
GROUP BY v.vendor_name;

-- Products supplied by each vendor with prices
SELECT v.vendor_name, p.name AS product_name, s.supply_price
FROM Vendor v
JOIN Supplies s ON v.vendor_id = s.vendor_id
JOIN Product p ON s.product_id = p.product_id
ORDER BY v.vendor_name;
