
package online.book.store;

public class Order {
    private int id;
    private int userId;
    private int bookId;
    private int quantity;
    private double totalPrice;
    private java.sql.Timestamp orderDate;
    private String bookTitle;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public java.sql.Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(java.sql.Timestamp orderDate) { this.orderDate = orderDate; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
}
