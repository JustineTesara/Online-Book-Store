
package online.book.store;

public class OrderRecords {
    private int id;
    private int userId;
    private int bookId;
    private int qty;
    private double totalPrice;

    public OrderRecords(int id, int userId, int bookId, int qty, double totalPrice) {
        this.id = id; this.userId = userId; this.bookId = bookId; this.qty = qty; this.totalPrice = totalPrice; 
    }

    public String toString() {
        return String.format("Order %d | user %d | book %d | qty %d | total ₱%.2f", id, userId, bookId, qty, totalPrice);
    }
}
