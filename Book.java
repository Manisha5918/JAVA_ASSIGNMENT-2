package libraryManagementSystem;
public class Book {
    private int bookId;
    private String title;
    private Author author;
    private int totalStock;
    private int availableStock;

    public Book(int bookId, String title, Author author, String isbn, int totalStock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.totalStock = totalStock;
        this.availableStock = totalStock;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }
    
    public int getAvailableStock() {
        return availableStock;
    }

    public boolean isAvailable() {
        return availableStock > 0;
    }

    public void borrowBook() {
        if (isAvailable()) {
            availableStock--;
        }
    }

    public void returnBook() {
        if (availableStock < totalStock) {
            availableStock++;
        }
    }

    @Override
    public String toString() {
        return "ID: " + bookId + ", Title: '" + title + "', Author: " + author.getName() +
               ", Available Stock: " + availableStock;
    }
}