import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    private Connection connection; // Use java.sql.Connection here

    private static final String dbName = "diarydb";
    private static final String userName = "root";
    private static final String password = "20020512";
    private static final String URL = "jdbc:mysql://localhost:3308/" + dbName;

    public Connection getConnection() {
        if (connection == null) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create the connection
                connection = DriverManager.getConnection(URL, userName, password);
            } catch (ClassNotFoundException | SQLException exception) {
                // Handle any exceptions that may occur during connection setup
                exception.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                // Handle any exceptions that may occur while closing the connection
                exception.printStackTrace();
            }
        }
    }
}
