
package online.book.store;

/**
 *
 * @author Justine
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private double price;
    private int stock;

    public Book() {}
    public Book(int id, String t, String a, String c, double p, int s) {
        this.id = id; title = t; author = a; category = c; price = p; stock = s;
    }

    // getters/setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%d: %s by %s | %s | ₱%.2f | stock: %d",
            id, title, author, category, price, stock);
    }
}
