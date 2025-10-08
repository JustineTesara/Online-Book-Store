package online.book.store;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserUI extends JFrame {
    private BookDAO bookDAO = new BookDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private JTable table;
    private JButton btnBuy, btnRefresh, btnLogout, btnViewOrders, btnSearch;
    private JTextField txtSearch;
    private User user;

    // 🎨 Color palette
    private final Color BG_COLOR = new Color(0xE0E0A5);   // background
    private final Color BTN_COLOR = new Color(0x348494);  // buttons
    private final Color BTN_HOVER = new Color(0x985C54);  // hover color
    private final Color TEXT_COLOR = new Color(0x645038); // text
    private final Color FIELD_COLOR = new Color(0xAFC0CF);// input fields

    public UserUI(User user) {
        this.user = user;
        setTitle("Welcome, " + user.getUsername());
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG_COLOR);

        // === Search Bar (Top) ===
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelTop.setBackground(BG_COLOR);

        JLabel lblSearch = new JLabel("🔍 Search Book:");
        lblSearch.setForeground(TEXT_COLOR);
        lblSearch.setFont(new Font("Arial", Font.BOLD, 13));

        txtSearch = new JTextField(20);
        txtSearch.setBackground(FIELD_COLOR);
        txtSearch.setForeground(Color.BLACK);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 13));
        txtSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnSearch = createStyledButton("Search");

        panelTop.add(lblSearch);
        panelTop.add(txtSearch);
        panelTop.add(btnSearch);

        add(panelTop, BorderLayout.NORTH);

        // === Table (Center) ===
        table = new JTable();
        refreshTable();
        styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // === Bottom Buttons ===
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBottom.setBackground(BG_COLOR);

        btnBuy = createStyledButton("🛒 Buy Book");
        btnRefresh = createStyledButton("🔄 Refresh");
        btnViewOrders = createStyledButton("📦 My Orders");
        btnLogout = createStyledButton("🚪 Logout");

        panelBottom.add(btnBuy);
        panelBottom.add(btnRefresh);
        panelBottom.add(btnViewOrders);
        panelBottom.add(btnLogout);

        add(panelBottom, BorderLayout.SOUTH);

        // === Button Actions ===
        btnBuy.addActionListener(e -> buyBook());
        btnRefresh.addActionListener(e -> refreshTable());
        btnLogout.addActionListener(e -> logout());
        btnViewOrders.addActionListener(e -> viewMyOrders());
        btnSearch.addActionListener(e -> searchBooks());
    }

    // 🎨 Reusable styled button
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(BTN_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(BTN_COLOR);
            }
        });
        return btn;
    }

    // 🎨 Table style
    private void styleTable(JTable table) {
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(BTN_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
    }

    // 🧾 Refresh and display books
    private void refreshTable() {
        List<Book> books = bookDAO.getAllBooks();
        displayBooks(books);
    }

    private void displayBooks(List<Book> books) {
        String[] columns = {"ID", "Title", "Author", "Category", "Price", "Stock"};
        Object[][] data = new Object[books.size()][6];
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            data[i][0] = b.getId();
            data[i][1] = b.getTitle();
            data[i][2] = b.getAuthor();
            data[i][3] = b.getCategory();
            data[i][4] = b.getPrice();
            data[i][5] = b.getStock();
        }
        table.setModel(new DefaultTableModel(data, columns));
        styleTable(table);
    }

    // 💸 Buy Book
    // 💸 Buy Book (with checkout and payment)
private void buyBook() {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a book first!");
        return;
    }

    int bookId = (int) table.getValueAt(row, 0);
    String title = (String) table.getValueAt(row, 1);
    double price = Double.parseDouble(table.getValueAt(row, 4).toString());
    int stock = Integer.parseInt(table.getValueAt(row, 5).toString());

    // Ask for quantity
    String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity for \"" + title + "\":");
    if (qtyStr == null || qtyStr.isEmpty()) return;

    int qty;
    try {
        qty = Integer.parseInt(qtyStr);
        if (qty <= 0) {
            JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid quantity!");
        return;
    }

    // Check stock availability
    if (qty > stock) {
        JOptionPane.showMessageDialog(this, "Not enough stock available!");
        return;
    }

    double total = price * qty;

    // Ask for payment
    String payStr = JOptionPane.showInputDialog(this,
            "Total price: ₱" + total + "\nEnter payment amount:");
    if (payStr == null || payStr.isEmpty()) return;

    double payment;
    try {
        payment = Double.parseDouble(payStr);
        if (payment < total) {
            JOptionPane.showMessageDialog(this, "Insufficient payment!");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid payment amount!");
        return;
    }

    double change = payment - total;

    // Confirm purchase
    int confirm = JOptionPane.showConfirmDialog(this,
            "Confirm purchase?\n\nBook: " + title +
            "\nQuantity: " + qty +
            "\nTotal: ₱" + total +
            "\nPayment: ₱" + payment +
            "\nChange: ₱" + change,
            "Confirm Purchase", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        // Save order in database
        if (orderDAO.createOrder(user.getId(), bookId, qty)) {
            JOptionPane.showMessageDialog(this,
                    "✅ Purchase successful!\nYour change is ₱" + change);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Purchase failed!");
        }
    }
}


    // 🔍 Search
    private void searchBooks() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
            return;
        }

        List<Book> books = bookDAO.searchBooks(keyword);
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found!");
        }
        displayBooks(books);
    }

    // 📦 View My Orders
    private void viewMyOrders() {
        List<Order> orders = orderDAO.getOrdersByUserId(user.getId());
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no orders yet!");
            return;
        }

        String[] columns = {"Order ID", "Book Title", "Quantity", "Total Price", "Order Date"};
        Object[][] data = new Object[orders.size()][5];

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            data[i][0] = o.getId();
            data[i][1] = o.getBookTitle();
            data[i][2] = o.getQuantity();
            data[i][3] = o.getTotalPrice();
            data[i][4] = o.getOrderDate();
        }

        JTable orderTable = new JTable(data, columns);
        styleTable(orderTable);
        JScrollPane scrollPane = new JScrollPane(orderTable);

        JOptionPane.showMessageDialog(this, scrollPane, "My Orders", JOptionPane.PLAIN_MESSAGE);
    }

    // 🚪 Logout
    private void logout() {
        dispose();
        new MainUI().setVisible(true);
    }
}
