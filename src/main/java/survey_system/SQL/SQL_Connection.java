package survey_system.SQL;

import java.sql.*;

public class SQL_Connection  implements AutoCloseable{
    private final String url = "jdbc:mysql://localhost:3306/survey_system";
    private final String userDB = "root";
    private final String passwordDB = "root";
    private Connection connection;
    private Statement stmt;

    @Override
    public String toString() {
        return "SQL_Connection{" +
                "passwordDB='" + passwordDB + '\'' +
                ", userDB='" + userDB + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public SQL_Connection() throws SQLException {
            connection = DriverManager.getConnection(url, userDB, passwordDB);
            stmt = connection.createStatement();
    }



    public ResultSet execute(String SQL_query) throws  SQLException {
            return stmt.executeQuery(SQL_query);
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
