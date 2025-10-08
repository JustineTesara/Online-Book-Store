# 📚 Online Book Store (GUI) using Java

This is a **Java Swing-based Online Book Store** application with a **Graphical User Interface (GUI)**.  
It allows **Admins** to manage books and view orders, while **Users** can browse, search, and buy books.  
The project connects to a **MySQL database** using **JDBC**.

---

## 🚀 Features

### 👨‍💼 Admin
- Add, update, and delete books  
- View all available books  
- View all customer orders  
- Logout and return to login screen  

### 👤 User
- Browse and search for books  
- Buy a book (adds order to database)  
- View personal orders  
- Logout safely  

---

## 🎨 Design and UI

The GUI is built using **Java Swing** and styled with a custom color palette for a soft and modern look.

**Color Palette**
| Element | Color | Description |
|----------|--------|-------------|
| Background | `#E0E0A5` | Soft beige tone |
| Button | `#348494` | Teal blue buttons |
| Button Hover | `#985C54` | Reddish hover effect |
| Text | `#645038` | Dark brown text |
| Input Fields | `#AFC0CF` | Light blue-gray input fields |

Each button has hover effects and custom fonts to make the interface look clean and user-friendly.

---

## 🗄️ Database Setup (MySQL)

### Database Name:

### Tables:

```sql
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  is_admin TINYINT(1) DEFAULT 0
);

CREATE TABLE books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  author VARCHAR(100),
  category VARCHAR(50),
  price DOUBLE,
  stock INT
);
CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  book_title VARCHAR(100),
  quantity INT,
  total_price DOUBLE,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
⚙️ How to Run
1. Clone this repository:
   ```git clone https://github.com/yourusername/OnlineBookStoreGUI.git```
2. Open the project in IntelliJ IDEA or NetBeans.

3. Import the MySQL JDBC Driver (Connector/J).
  Example JAR: ```mysql-connector-j-8.0.33.jar```
4. Update your database credentials in ConnectDB.java:
  ```  
      private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
      private static final String USER = "root";
      private static final String PASS = "";
  ```
5. Run the main program:
   ``` MainUI.java```
6.  Login as:
  Admin: manage books and orders
  User: browse and buy books

## 🧩 Tech Stack
- Language: Java
- GUI Framework: Swing
- Database: MySQL
- Connector: JDBC

## 📷 Preview (Optional)

### Log in/Sign up Dashboard
<img width="508" height="331" alt="Screenshot 2025-10-08 163840" src="https://github.com/user-attachments/assets/69991ef9-bac3-4f33-9e47-83c771872897" />

### User Dashboard
<img width="1045" height="609" alt="Screenshot 2025-10-08 163902" src="https://github.com/user-attachments/assets/dfd2c9d8-caed-4ae1-ac0c-dc4cbc4f0a37" />

### Purchasing a book
<img width="1045" height="609" alt="Screenshot 2025-10-08 163902" src="https://github.com/user-attachments/assets/9aad2a6d-c9c1-4061-957b-f6a68bdd657b" />
<img width="1043" height="611" alt="Screenshot 2025-10-08 163942" src="https://github.com/user-attachments/assets/537ce58b-f141-499d-adfa-84acce03367c" />

### View orders of user
<img width="1045" height="615" alt="Screenshot 2025-10-08 164011" src="https://github.com/user-attachments/assets/e5a56d1c-3e4c-4524-bdec-f27c9c631dfc" />

### Admin Dashboard
<img width="1049" height="612" alt="Screenshot 2025-10-08 164043" src="https://github.com/user-attachments/assets/80a8effb-1927-4f94-a411-a708ea75205b" />

### Adding new book
<img width="1047" height="612" alt="Screenshot 2025-10-08 164124" src="https://github.com/user-attachments/assets/a3f5e609-56cc-4658-9ed3-0ba8e24f9230" />

### View all orders
<img width="1046" height="643" alt="Screenshot 2025-10-08 164146" src="https://github.com/user-attachments/assets/d19d9edf-877e-4c0a-a2d6-4b3c76b08cbc" />


## 👨‍💻 Author
Justine Tesara
📧 justine.tesara0907@gmail.com
💾 GitHub: https://github.com/JustineTesara
