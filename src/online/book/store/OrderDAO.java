
package online.book.store;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class OrderDAO extends ConnectDB {
    
    public boolean createOrder(int userId, int bookId, int qty) {
        String getPriceSql = "SELECT price FROM books WHERE id=?";
        String reduceStockSql = "UPDATE books SET stock = stock - ? WHERE id=? AND stock >= ?";
        String insertOrderSql = "INSERT INTO orders(user_id, book_id, qty, total_price) VALUES(?,?,?,?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            double price;
            try (PreparedStatement ps = conn.prepareStatement(getPriceSql)) {
                ps.setInt(1, bookId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) { conn.rollback(); return false; }
                    price = rs.getDouble("price");
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement(reduceStockSql)) {
                ps2.setInt(1, qty);
                ps2.setInt(2, bookId);
                ps2.setInt(3, qty);
                int updated = ps2.executeUpdate();
                if (updated != 1) { conn.rollback(); return false; } // not enough stock
            }

            try (PreparedStatement ps3 = conn.prepareStatement(insertOrderSql)) {
                ps3.setInt(1, userId);
                ps3.setInt(2, bookId);
                ps3.setInt(3, qty);
                ps3.setDouble(4, price * qty);
                ps3.executeUpdate();
            }

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<OrderRecords> getOrdersByUser(int userId) {
        List<OrderRecords> list = new ArrayList<>();
        String sql = "SELECT id, user_id, book_id, qty, total_price FROM orders WHERE user_id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new OrderRecords(rs.getInt("id"), rs.getInt("user_id"),
                        rs.getInt("book_id"), rs.getInt("qty"), rs.getDouble("total_price")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public List<Order> getOrdersByUserId(int userId) {
    List<Order> orders = new ArrayList<>();
    String sql = "SELECT o.id, b.title, o.qty, o.total_price, o.order_date " +
                 "FROM orders o JOIN books b ON o.book_id = b.id " +
                 "WHERE o.user_id = ?";

    try (Connection conn = new ConnectDB().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setBookTitle(rs.getString("title"));
            order.setQuantity(rs.getInt("qty"));
            order.setTotalPrice(rs.getDouble("total_price"));
            order.setOrderDate(rs.getTimestamp("order_date"));
            orders.add(order);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return orders;
    }
    
    
    public List<Order> getAllOrders() {
    List<Order> orders = new ArrayList<>();
    String sql = "SELECT o.id, o.user_id, b.title, o.qty, o.total_price, o.order_date " +
                 "FROM orders o JOIN books b ON o.book_id = b.id";

    try (Connection conn = new ConnectDB().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("user_id"));
            order.setBookTitle(rs.getString("title"));
            order.setQuantity(rs.getInt("qty"));
            order.setTotalPrice(rs.getDouble("total_price"));
            order.setOrderDate(rs.getTimestamp("order_date"));
            orders.add(order);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return orders;
}

    
}
