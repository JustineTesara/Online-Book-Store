
package online.book.store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends ConnectDB {

    public boolean addBook(Book b) {
        String sql = "INSERT INTO books(title,author,category,price,stock) VALUES(?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getCategory());
            ps.setDouble(4, b.getPrice());
            ps.setInt(5, b.getStock());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title=?, author=?, category=?, price=?, stock=? WHERE id=?";
        try (Connection conn = new ConnectDB().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setDouble(4, book.getPrice());
            ps.setInt(5, book.getStock());
            ps.setInt(6, book.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                    rs.getString("category"), rs.getDouble("price"), rs.getInt("stock")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Book getById(int id) {
        String sql = "SELECT * FROM books WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("category"), rs.getDouble("price"), rs.getInt("stock"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Book> searchByTitle(String q) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + q + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("category"), rs.getDouble("price"), rs.getInt("stock")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    public List<Book> searchBooks(String keyword) {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
    try (Connection conn = new ConnectDB().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            books.add(new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("category"),
                rs.getDouble("price"),
                rs.getInt("stock")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return books;
    }
    


}