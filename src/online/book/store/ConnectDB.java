
package online.book.store;

import java.sql.*;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/onlinebookstore"; // replace this to your database name
    private static final String USER = "root";
    private static final String PASS = "";

    // Reusable connection method
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    
    
    
}
