package libraryManagementSystem;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDate;

public class LibraryApplication {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        
        initializeData(library);

        while (true) {
            printMenu();
            int choice = getInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1: addBook(library, scanner); break;
                case 2: addMember(library, scanner); break;
                case 3: borrowBook(library, scanner); break;
                case 4: returnBook(library, scanner); break;
                case 5: displayAvailableBooks(library); break;
                case 6: searchForBook(library, scanner); break;
                case 7:
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.err.println("Error: Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private static int getInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double getDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid input. Please enter a number.");
            }
        }
    }

    private static String getString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.err.println("Error: Input cannot be empty.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== Library Management System =====");
        System.out.println("1. Add Book");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Display Available Books");
        System.out.println("6. Search for a Book");
        System.out.println("7. Exit");
    }

    private static void displayBookTable(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Warning: No books found.");
            return;
        }
        
        String border = "+--------+------------------------------------------+----------------------+---------+";
        System.out.println(border);
        System.out.printf("| %-6s | %-40s | %-20s | %-7s |\n", "ID", "Title", "Author", "Stock");
        System.out.println(border);
        for (Book book : books) {
            System.out.printf("| %-6d | %-40s | %-20s | %-7d |\n", 
                book.getBookId(), book.getTitle(), book.getAuthor().getName(), book.getAvailableStock());
        }
        System.out.println(border);
    }
    
    private static void displayAvailableBooks(Library library) {
        System.out.println("\n===== Available Books =====");
        displayBookTable(library.getAvailableBooks());
    }

    private static void searchForBook(Library library, Scanner scanner) {
        System.out.println("\n===== Search for a Book =====");
        String query = getString(scanner, "Enter a title to search for: ");
        List<Book> results = library.searchBooksByTitle(query);
        System.out.println("\nSearch Results:");
        displayBookTable(results);
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.println("\n===== Add a New Book =====");
        String title = getString(scanner, "Enter Book Title: ");
        String authorName = getString(scanner, "Enter Author Name: ");
        String isbn = getString(scanner, "Enter ISBN: ");
        int stock = getInt(scanner, "Enter Total Stock: ");

        Author author = library.findOrCreateAuthor(authorName);
        Book book = new Book(library.getNextBookId(), title, author, isbn, stock);
        library.addBook(book);
        System.out.println("Success: Book '" + title + "' added successfully with ID " + book.getBookId());
    }

    private static void addMember(Library library, Scanner scanner) {
        System.out.println("\n===== Add a New Member =====");
        String name = getString(scanner, "Enter Member Name: ");
        int type;
        while (true) {
            type = getInt(scanner, "Enter Member Type (1 for Standard, 2 for Premium): ");
            if (type == 1 || type == 2) {
                break;
            } else {
                System.err.println("Error: Invalid type. Please enter 1 or 2.");
            }
        }
        
        Member member;
        if (type == 2) {
            double fee = getDouble(scanner, "Enter Monthly Fee: $"); 
            member = new PremiumMember(library.getNextMemberId(), name, fee);
        } else {
            member = new Member(library.getNextMemberId(), name);
        }
        library.addMember(member);
        System.out.println("Success: Member '" + name + "' added successfully with ID " + member.getMemberId());
    }

    private static void borrowBook(Library library, Scanner scanner) {
        System.out.println("\n===== Borrow a Book =====");
        try {
            int memberId = getInt(scanner, "Enter your Member ID: ");
            int bookId = getInt(scanner, "Enter the Book ID to borrow: ");
            library.borrowBook(memberId, bookId);
        } catch (LibraryException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void returnBook(Library library, Scanner scanner) {
        System.out.println("\n===== Return a Book =====");
        try {
            int memberId = getInt(scanner, "Enter your Member ID: ");
            int bookId = getInt(scanner, "Enter the Book ID to return: ");
            library.returnBook(memberId, bookId);
        } catch (LibraryException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void initializeData(Library library) {
        Author author1 = library.findOrCreateAuthor("George Orwell");
        Author author2 = library.findOrCreateAuthor("J.K. Rowling");
        Author author3 = library.findOrCreateAuthor("J.R.R. Tolkien");
        Author author4 = library.findOrCreateAuthor("Harper Lee");
        Author author5 = library.findOrCreateAuthor("F. Scott Fitzgerald");
        Author author6 = library.findOrCreateAuthor("Jane Austen");
        Author author7 = library.findOrCreateAuthor("J.D. Salinger");

        library.addBook(new Book(library.getNextBookId(), "1984", author1, "978-0451524935", 5));
        library.addBook(new Book(library.getNextBookId(), "Harry Potter and the Sorcerer's Stone", author2, "978-0747532743", 3));
        library.addBook(new Book(library.getNextBookId(), "The Hobbit", author3, "978-0345339683", 4));
        library.addBook(new Book(library.getNextBookId(), "To Kill a Mockingbird", author4, "978-0061120084", 2));
        library.addBook(new Book(library.getNextBookId(), "The Great Gatsby", author5, "978-0743273565", 3));
        library.addBook(new Book(library.getNextBookId(), "Pride and Prejudice", author6, "978-1503290563", 5));
        library.addBook(new Book(library.getNextBookId(), "The Catcher in the Rye", author7, "978-0316769488", 1));

        library.addMember(new Member(library.getNextMemberId(), "Alice Smith"));
        library.addMember(new PremiumMember(library.getNextMemberId(), "Bob Johnson", 9.99));
        
        System.out.println("Initial data loaded.");
    }
}