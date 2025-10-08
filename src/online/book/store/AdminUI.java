package online.book.store;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminUI extends JFrame {
    private BookDAO bookDAO = new BookDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private JTable table;
    private JButton btnAdd, btnDelete, btnRefresh, btnUpdate, btnViewOrders, btnLogout, btnSearch;
    private JTextField txtSearch;

    // 🎨 Colors
    private final Color BG_COLOR = new Color(0xE0E0A5);
    private final Color BTN_COLOR = new Color(0x348494);
    private final Color BTN_HOVER = new Color(0x985C54);
    private final Color TEXT_COLOR = new Color(0x645038);

    public AdminUI(User admin) {
        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        // === 🔍 Search Panel (Top) ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(BG_COLOR);

        JLabel lblSearch = new JLabel("Search Book:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 13));
        lblSearch.setForeground(TEXT_COLOR);

        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 13));
        txtSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnSearch = createStyledButton("🔍 Search");

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.NORTH);

        // === 📚 Book Table ===
        table = new JTable();
        refreshTable();
        styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // === ⚙️ Bottom Buttons ===
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(BG_COLOR);

        btnAdd = createStyledButton("➕ Add Book");
        btnUpdate = createStyledButton("✏️ Update");
        btnDelete = createStyledButton("🗑 Delete");
        btnViewOrders = createStyledButton("📦 View Orders");
        btnRefresh = createStyledButton("🔄 Refresh");
        btnLogout = createStyledButton("🚪 Logout");

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnViewOrders);
        panel.add(btnRefresh);
        panel.add(btnLogout);

        add(panel, BorderLayout.SOUTH);

        // === Button Actions ===
        btnAdd.addActionListener(e -> addBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnViewOrders.addActionListener(e -> viewAllOrders());
        btnRefresh.addActionListener(e -> refreshTable());
        btnLogout.addActionListener(e -> logout());
        btnSearch.addActionListener(e -> searchBooks());
    }

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

    private void styleTable(JTable table) {
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(BTN_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
    }

    private void refreshTable() {
        List<Book> books = bookDAO.getAllBooks();
        displayBooks(books);
    }

    // 🔍 Display Books in Table
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

    // 🔍 Search Books by Keyword
    private void searchBooks() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword!");
            return;
        }

        List<Book> books = bookDAO.searchBooks(keyword);
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching books found!");
        }
        displayBooks(books);
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog("Title:");
        String author = JOptionPane.showInputDialog("Author:");
        String category = JOptionPane.showInputDialog("Category:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));
        Book b = new Book(0, title, author, category, price, stock);

        if (bookDAO.addBook(b)) {
            JOptionPane.showMessageDialog(this, "✅ Book added!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to add book!");
        }
    }

    private void updateBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first!");
            return;
        }

        int id = (int) table.getValueAt(row, 0);
        String title = JOptionPane.showInputDialog("New Title:", table.getValueAt(row, 1));
        String author = JOptionPane.showInputDialog("New Author:", table.getValueAt(row, 2));
        String category = JOptionPane.showInputDialog("New Category:", table.getValueAt(row, 3));
        double price = Double.parseDouble(JOptionPane.showInputDialog("New Price:", table.getValueAt(row, 4)));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("New Stock:", table.getValueAt(row, 5)));

        Book updatedBook = new Book(id, title, author, category, price, stock);
        if (bookDAO.updateBook(updatedBook)) {
            JOptionPane.showMessageDialog(this, "✅ Book updated!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Update failed!");
        }
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first!");
            return;
        }

        int id = (int) table.getValueAt(row, 0);
        if (bookDAO.deleteBook(id)) {
            JOptionPane.showMessageDialog(this, "🗑 Book deleted!");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to delete!");
        }
    }

    private void viewAllOrders() {
        List<Order> orders = orderDAO.getAllOrders();
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders found!");
            return;
        }

        String[] columns = {"Order ID", "User ID", "Book Title", "Quantity", "Total Price", "Order Date"};
        Object[][] data = new Object[orders.size()][6];

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            data[i][0] = o.getId();
            data[i][1] = o.getUserId();
            data[i][2] = o.getBookTitle();
            data[i][3] = o.getQuantity();
            data[i][4] = o.getTotalPrice();
            data[i][5] = o.getOrderDate();
        }

        JTable orderTable = new JTable(data, columns);
        styleTable(orderTable);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        JOptionPane.showMessageDialog(this, scrollPane, "All Orders", JOptionPane.PLAIN_MESSAGE);
    }

    private void logout() {
        dispose();
        new MainUI().setVisible(true);
    }
}
