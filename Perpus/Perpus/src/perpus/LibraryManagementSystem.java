/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package perpus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Item {
    private String id;
    private String title;

    public Item(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

class Book extends Item {
    private String author;
    private int pageCount;

    public Book(String id, String title, String author, int pageCount) {
        super(id, title);
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }
}

class User {
    private String id;
    private String name;
    private List<String> borrowedItems;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        borrowedItems = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void borrowItem(String itemId) {
        borrowedItems.add(itemId);
    }

    public void returnItem(String itemId) {
        borrowedItems.remove(itemId);
    }

    public boolean hasBorrowedItem(String itemId) {
        return borrowedItems.contains(itemId);
    }

    public List<String> getBorrowedItems() {
        return borrowedItems;
    }
}

public class LibraryManagementSystem {
    private Map<String, Item> items;
    private List<User> users;
    private User loggedInUser;

    public LibraryManagementSystem() {
        items = new HashMap<>();
        users = new ArrayList<>();
        loggedInUser = null;
    }

    public void addItem(Item item) {
        items.put(item.getId(), item);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void displayItems() {
        int choice;
        do {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Display All Items");
            System.out.println("2. Borrow Item");
            System.out.println("3. Return Item");
            System.out.println("4. Exit");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n=== Displaying All Items ===");
                    for (Item item : items.values()) {
                        System.out.println("ID: " + item.getId() + ", Title: " + item.getTitle());
                    }
                    break;
                case 2:
                    if (loggedInUser != null) {
                        System.out.print("Enter the ID of the item to borrow: ");
                        scanner.nextLine();
                        String itemIdToBorrow = scanner.nextLine();
                        Item itemToBorrow = items.get(itemIdToBorrow);
                        if (itemToBorrow != null) {
                            if (loggedInUser.hasBorrowedItem(itemIdToBorrow)) {
                                System.out.println("You have already borrowed this item.");
                            } else {
                                boolean isItemBorrowed = false;
                                for (User user : users) {
                                    if (user.hasBorrowedItem(itemIdToBorrow)) {
                                        isItemBorrowed = true;
                                        break;
                                    }
                                }
                                if (isItemBorrowed) {
                                    System.out.println("The item is already borrowed by another user.");
                                } else {
                                    loggedInUser.borrowItem(itemIdToBorrow);
                                    System.out.println("Item borrowed successfully!");
                                }
                            }
                        } else {
                            System.out.println("Item not found.");
                        }
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;
                case 3:
                    if (loggedInUser != null) {
                        System.out.print("Enter the ID of the item to return: ");
                        scanner.nextLine();
                        String itemIdToReturn = scanner.nextLine();
                        Item itemToReturn = items.get(itemIdToReturn);
                        if (itemToReturn != null) {
                            if (loggedInUser.hasBorrowedItem(itemIdToReturn)) {
                                loggedInUser.returnItem(itemIdToReturn);
                                System.out.println("Item returned successfully!");
                            } else {
                                System.out.println("You haven't borrowed this item.");
                            }
                        } else {
                            System.out.println("Item not found.");
                        }
                    } else {
                        System.out.println("Please log in first.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        } while (choice != 4);
    }

    public void signIn() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        User newUser = new User(id, name);
        addUser(newUser);

        System.out.println("Sign in successful!");
    }

    public boolean login(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                System.out.println("Welcome, " + user.getName() + "!");
                loggedInUser = user;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();

        // Menambahkan contoh buku
        Book book1 = new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", 180);
        Book book2 = new Book("B002", "To Kill a Mockingbird", "Harper Lee", 320);
        Book book3 = new Book("B003", "1984", "George Orwell", 328);
        Book book4 = new Book("B004", "Pride and Prejudice", "Jane Austen", 432);
        Book book5 = new Book("B005", "The Catcher in the Rye", "J.D. Salinger", 224);

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(book3);
        library.addItem(book4);
        library.addItem(book5);

        Scanner scanner = new Scanner(System.in);

        // Menu utama
        int choice;
        do {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Sign In");
            System.out.println("2. Log In");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.signIn();
                    break;
                case 2:
                    System.out.print("Enter your ID: ");
                    String userId = scanner.next();
                    boolean isLoggedIn = library.login(userId);
                    if (isLoggedIn) {
                        // Menampilkan menu jika login berhasil
                        library.displayItems();
                    } else {
                        System.out.println("Invalid ID. Please sign in first.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        } while (choice != 3);
    }
}




