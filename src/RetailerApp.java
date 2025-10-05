
import java.sql.*;
import java.util.*;

public class RetailerApp {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection();
             Scanner sc = new Scanner(System.in)) {
            System.out.println("Connected to Oracle successfully!");
            while (true) {
                System.out.println("\n==== Retailer Database ====");
                System.out.println("1. View Products");
                System.out.println("2. Add Sale");
                System.out.println("3. Top Products");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();
                switch (ch) {
                    case 1 -> viewProducts(con);
                    case 2 -> addSale(con, sc);
                    case 3 -> topProducts(con);
                    case 4 -> { System.out.println("Goodbye!"); return; }
                    default -> System.out.println("Invalid!");
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void viewProducts(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Product");
        while (rs.next())
            System.out.printf("%d | %s | ₹%.2f | %s%n",
                rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
        rs.close(); st.close();
    }

    private static void addSale(Connection con, Scanner sc) throws SQLException {
        try {
            System.out.print("Product ID: "); int pid=sc.nextInt();
            System.out.print("Customer ID: "); int cid=sc.nextInt();
            System.out.print("Store ID: "); int sid=sc.nextInt();
            System.out.print("Quantity: "); int qty=sc.nextInt();

            if (qty <= 0) {
                System.out.println("❌ Error: Quantity must be positive!");
                return;
            }

            System.out.print("Total Amt: "); double amt=sc.nextDouble();

            if (amt <= 0) {
                System.out.println("❌ Error: Amount must be positive!");
                return;
            }

            // Validate Product exists
            if (!recordExists(con, "Product", "product_id", pid)) {
                System.out.println("❌ Error: Product ID " + pid + " does not exist!");
                return;
            }

            // Validate Customer exists
            if (!recordExists(con, "Customer", "cust_id", cid)) {
                System.out.println("❌ Error: Customer ID " + cid + " does not exist!");
                return;
            }

            // Validate Store exists
            if (!recordExists(con, "Store", "store_id", sid)) {
                System.out.println("❌ Error: Store ID " + sid + " does not exist!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO Sale (sale_id, product_id, cust_id, store_id, quantity, total_amt, sale_date) VALUES (sale_seq.NEXTVAL,?,?,?,?,?,SYSDATE)");
            ps.setInt(1,pid); ps.setInt(2,cid); ps.setInt(3,sid);
            ps.setInt(4,qty); ps.setDouble(5,amt);
            ps.executeUpdate(); con.commit();
            System.out.println("✅ Sale inserted successfully!");
            ps.close();
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            con.rollback();
        } catch (Exception e) {
            System.out.println("❌ Invalid input! Please enter valid numbers.");
            sc.nextLine(); // Clear buffer
        }
    }

    private static boolean recordExists(Connection con, String table, String column, int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM " + table + " WHERE " + column + "=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        boolean exists = rs.getInt(1) > 0;
        rs.close();
        ps.close();
        return exists;
    }

    private static void topProducts(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT p.name, SUM(s.quantity) FROM Sale s JOIN Product p ON s.product_id=p.product_id GROUP BY p.name ORDER BY SUM(s.quantity) DESC FETCH FIRST 5 ROWS ONLY");
        System.out.println("Top Selling Products:");
        while (rs.next())
            System.out.println(rs.getString(1)+" - "+rs.getInt(2)+" units");
        rs.close(); st.close();
    }
}
