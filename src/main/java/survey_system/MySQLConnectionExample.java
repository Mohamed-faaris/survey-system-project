package survey_system;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLConnectionExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "";

        // JDBC variables for opening and managing connection
        Connection conn = null;

        try {
            // Step 1: Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish a connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!");

            // Step 3: Create a statement
            Statement stmt = conn.createStatement();

            // Step 4: Execute a query
            String query = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(query);

            // Step 5: Process the ResultSet
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Email: " + rs.getString("email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 6: Close the connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

