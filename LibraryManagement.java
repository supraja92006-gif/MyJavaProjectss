// File: LibraryManagement.java
import java.io.*;
import java.util.*;

class Book implements Serializable {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }

    @Override
    public String toString() {
        return "Title: " + title + " | Author: " + author + " | ISBN: " + isbn;
    }
}

class Library {
    private static final String FILE_NAME = "books.txt";

    // Add a book
    public void addBook(Book book) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(book.getTitle() + "," + book.getAuthor() + "," + book.getIsbn());
            System.out.println("✅ Book added successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error adding book: " + e.getMessage());
        }
    }

    // View all books
    public void viewBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n📚 All Books in Library:");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Title: " + data[0] + " | Author: " + data[1] + " | ISBN: " + data[2]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ No books found. Please add some first.");
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    // Search book by title
    public void searchBook(String title) {
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(title)) {
                    System.out.println("✅ Book Found: " + line);
                    found = true;
                }
            }
            if (!found) System.out.println("❌ Book not found!");
        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // Delete book by title
    public void deleteBook(String title) {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        boolean deleted = false;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equalsIgnoreCase(title)) {
                    pw.println(line);
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error deleting book: " + e.getMessage());
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);

        if (deleted) System.out.println("✅ Book deleted successfully!");
        else System.out.println("❌ Book not found!");
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        int choice;
        do {
            System.out.println("\n===== 📘 Library Book Tracker =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    library.addBook(new Book(title, author, isbn));
                    break;

                case 2:
                    library.viewBooks();
                    break;

                case 3:
                    System.out.print("Enter Title to Search: ");
                    String searchTitle = sc.nextLine();
                    library.searchBook(searchTitle);
                    break;

                case 4:
                    System.out.print("Enter Title to Delete: ");
                    String delTitle = sc.nextLine();
                    library.deleteBook(delTitle);
                    break;

                case 5:
                    System.out.println("👋 Exiting Library Tracker. Goodbye!");
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again!");
            }
        } while (choice != 5);

        sc.close();
    }
}

