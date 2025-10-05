
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Read connection info from environment so Codespaces / Docker can configure it at runtime.
    private static final String URL = System.getenv("DB_URL") != null
            ? System.getenv("DB_URL")
            : "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = System.getenv("DB_USER") != null
            ? System.getenv("DB_USER")
            : "system";
    private static final String PASS = System.getenv("DB_PASS") != null
            ? System.getenv("DB_PASS")
            : "OraclePass123";

    static {
        // Try to load the Oracle JDBC driver early so errors are clearer if the jar is missing.
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            // Driver not on classpath; connection will fail with a clearer message later.
            System.err.println("Warning: Oracle JDBC driver not found on classpath. Add ojdbc jar to run the app.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
