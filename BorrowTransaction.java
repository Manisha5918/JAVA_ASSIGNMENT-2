package libraryManagementSystem;

import java.time.LocalDate;

public class BorrowTransaction {
    private int transactionId;
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public BorrowTransaction(int transactionId, Member member, Book book, LocalDate dueDate) {
        this.transactionId = transactionId;
        this.member = member;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.dueDate = dueDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "--- Borrow Receipt ---\n" +
               "Transaction ID: " + transactionId + "\n" +
               "Member: " + member.getName() + " (ID: " + member.getMemberId() + ")\n" +
               "Book: '" + book.getTitle() + "' (ID: " + book.getBookId() + ")\n" +
               "Borrow Date: " + borrowDate + "\n" +
               "Due Date: " + dueDate + "\n" +
               "----------------------";
    }
}
 
