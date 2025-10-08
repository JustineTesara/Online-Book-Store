
package online.book.store;

import java.sql.*;

public class UserDAO extends ConnectDB {

    public boolean createUser(String username, String password) {
        String sql = "INSERT INTO users(username,password,is_admin) VALUES(?,?,0)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public User login(String username, String password) {
        String sql = "SELECT id, username, is_admin FROM users WHERE username=? AND password=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getInt("is_admin") == 1);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
 
}
