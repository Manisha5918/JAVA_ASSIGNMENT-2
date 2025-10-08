package libraryManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberId;
    private String name;
    protected int maxBooksAllowed;
    protected List<BorrowTransaction> borrowedBooks;

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.maxBooksAllowed = 3;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }
    
    public List<BorrowTransaction> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(BorrowTransaction transaction) {
        borrowedBooks.add(transaction);
    }

    public void returnBook(BorrowTransaction transaction) {
        borrowedBooks.remove(transaction);
    }
    
    @Override
    public String toString() {
        return "ID: " + memberId + ", Name: " + name + ", Type: Standard Member";
    }
}