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


## 👨‍💻 Author
Justine Tesara
📧 justine.tesara0907@gmail.com
💾 GitHub: https://github.com/JustineTesara
