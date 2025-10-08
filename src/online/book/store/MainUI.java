package online.book.store;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin, btnSignup;
    private UserDAO userDAO = new UserDAO();

    // 🎨 Color palette
    private final Color BG_COLOR = new Color(0xE0E0A5);   // background
    private final Color BTN_COLOR = new Color(0x348494);  // buttons
    private final Color BTN_HOVER = new Color(0x985C54);  // hover color
    private final Color TEXT_COLOR = new Color(0x645038); // text
    private final Color FIELD_COLOR = new Color(0xAFC0CF);// input fields

    public MainUI() {
        setTitle("Online Book Store");
        setSize(420, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background Panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("📚 Online Book Store", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(TEXT_COLOR); // changed to 0x645038 (brown)

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username Label
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(TEXT_COLOR);
        panel.add(lblUser, gbc);

        // Username Field
        gbc.gridx = 1;
        txtUser = new JTextField(15);
        txtUser.setBackground(FIELD_COLOR);
        txtUser.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));
        panel.add(txtUser, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(TEXT_COLOR);
        panel.add(lblPass, gbc);

        // Password Field
        gbc.gridx = 1;
        txtPass = new JPasswordField(15);
        txtPass.setBackground(FIELD_COLOR);
        txtPass.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));
        panel.add(txtPass, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        btnLogin = createStyledButton("Login");
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        btnSignup = createStyledButton("Sign Up");
        panel.add(btnSignup, gbc);

        add(panel);

        // Event handlers
        btnLogin.addActionListener(e -> login());
        btnSignup.addActionListener(e -> signup());
    }

    // 🟦 Custom button styling
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(BTN_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(BTN_COLOR);
            }
        });

        return btn;
    }

    private void login() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userDAO.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome, " + user.getUsername() + "!");
            if (user.isAdmin()) {
                new AdminUI(user).setVisible(true);
            } else {
                new UserUI(user).setVisible(true);
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void signup() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters long!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (userDAO.createUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Account created successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Signup failed! Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }
}
