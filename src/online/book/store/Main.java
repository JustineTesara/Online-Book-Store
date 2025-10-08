package online.book.store;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static BookDAO bookDAO = new BookDAO();
    private static OrderDAO orderDAO = new OrderDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Online Book Store ===");
            System.out.println("1) Sign up");
            System.out.println("2) Login");
            System.out.println("3) Exit");
            System.out.print("choose: ");
            String c = sc.nextLine();
            if (c.equals("1")) signUp();
            else if (c.equals("2")) login();
            else if (c.equals("3")) { System.out.println("bye"); break; }
            else System.out.println("invalid");
        }
    }

    private static void signUp() {
        System.out.print("username: ");
        String u = sc.nextLine();
        System.out.print("password: ");
        String p = sc.nextLine();
        if (userDAO.createUser(u,p)) System.out.println("account created");
        else System.out.println("failed (maybe username exists)");
    }

    private static void login() {
        System.out.print("username: ");
        String u = sc.nextLine();
        System.out.print("password: ");
        String p = sc.nextLine();
        User user = userDAO.login(u,p);
        if (user == null) { System.out.println("login failed"); return; }
        System.out.println("welcome, " + user.getUsername());
        if (user.isAdmin()) adminMenu(user);
        else userMenu(user);
    }

    private static void adminMenu(User admin) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1) Add book");
            System.out.println("2) Update book");
            System.out.println("3) Delete book");
            System.out.println("4) List books");
            System.out.println("5) View all orders");
            System.out.println("6) Logout");
            System.out.print("choose: ");
            String c = sc.nextLine();
            if (c.equals("1")) addBook();
            else if (c.equals("2")) updateBook();
            else if (c.equals("3")) deleteBook();
            else if (c.equals("4")) listBooks();
            else if (c.equals("5")) {
                List<OrderRecords> orders = orderDAO.getAllOrders();
                orders.forEach(System.out::println);
            }
            else if (c.equals("6")) break;
            else System.out.println("invalid");
        }
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1) List books");
            System.out.println("2) Search book");
            System.out.println("3) Buy book");
            System.out.println("4) My orders");
            System.out.println("5) Logout");
            System.out.print("choose: ");
            String c = sc.nextLine();
            if (c.equals("1")) listBooks();
            else if (c.equals("2")) {
                System.out.print("search term: ");
                String q = sc.nextLine();
                List<Book> res = bookDAO.searchByTitle(q);
                res.forEach(System.out::println);
            }
            else if (c.equals("3")) buyBook(user);
            else if (c.equals("4")) {
                List<OrderRecords> orders = orderDAO.getOrdersByUser(user.getId());
                orders.forEach(System.out::println);
            }
            else if (c.equals("5")) break;
            else System.out.println("invalid");
        }
    }

    private static void addBook() {
        try {
            System.out.print("title: "); String t = sc.nextLine();
            System.out.print("author: "); String a = sc.nextLine();
            System.out.print("category: "); String c = sc.nextLine();
            System.out.print("price: "); double p = Double.parseDouble(sc.nextLine());
            System.out.print("stock: "); int s = Integer.parseInt(sc.nextLine());
            Book b = new Book(0,t,a,c,p,s);
            if (bookDAO.addBook(b)) System.out.println("book added");
            else System.out.println("failed");
        } catch (Exception e) { System.out.println("invalid input"); }
    }

    private static void updateBook() {
        try {
            System.out.print("book id: "); int id = Integer.parseInt(sc.nextLine());
            Book b = bookDAO.getById(id);
            if (b == null) { System.out.println("not found"); return; }
            System.out.print("new title ("+b.getTitle()+"): "); String t = sc.nextLine(); if (!t.isEmpty()) b.setTitle(t);
            System.out.print("new author ("+b.getAuthor()+"): "); String a = sc.nextLine(); if (!a.isEmpty()) b.setAuthor(a);
            System.out.print("new category ("+b.getCategory()+"): "); String c = sc.nextLine(); if (!c.isEmpty()) b.setCategory(c);
            System.out.print("new price ("+b.getPrice()+"): "); String p = sc.nextLine(); if (!p.isEmpty()) b.setPrice(Double.parseDouble(p));
            System.out.print("new stock ("+b.getStock()+"): "); String s = sc.nextLine(); if (!s.isEmpty()) b.setStock(Integer.parseInt(s));
            if (bookDAO.updateBook(b)) System.out.println("updated");
            else System.out.println("failed");
        } catch (Exception e) { System.out.println("invalid input"); }
    }

    private static void deleteBook() {
        try {
            System.out.print("book id: "); int id = Integer.parseInt(sc.nextLine());
            if (bookDAO.deleteBook(id)) System.out.println("deleted");
            else System.out.println("failed");
        } catch (Exception e) { System.out.println("invalid input"); }
    }

    private static void listBooks() {
        List<Book> list = bookDAO.getAllBooks();
        list.forEach(System.out::println);
    }

    private static void buyBook(User user) {
        try {
            listBooks();
            System.out.print("book id to buy: "); int id = Integer.parseInt(sc.nextLine());
            System.out.print("quantity: "); int qty = Integer.parseInt(sc.nextLine());
            // Simulate payment
            System.out.print("card number (simulate): "); sc.nextLine();
            System.out.print("card name (simulate): "); sc.nextLine();
            // create order
            boolean ok = orderDAO.createOrder(user.getId(), id, qty);
            if (ok) System.out.println("purchase successful!");
            else System.out.println("purchase failed (maybe not enough stock)");
        } catch (Exception e) { System.out.println("invalid input"); }
    }
    
}
