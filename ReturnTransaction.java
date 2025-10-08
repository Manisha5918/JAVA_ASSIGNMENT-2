package libraryManagementSystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnTransaction {
    private int returnTransactionId;
    private BorrowTransaction borrowTransaction;
    private LocalDate returnDate;
    private double fine;

    public ReturnTransaction(int returnTransactionId, BorrowTransaction borrowTransaction) {
        this.returnTransactionId = returnTransactionId;
        this.borrowTransaction = borrowTransaction;
        this.returnDate = LocalDate.now();
        this.fine = calculateFine();
    }

    private double calculateFine() {
        long daysLate = ChronoUnit.DAYS.between(borrowTransaction.getDueDate(), returnDate);
        if (daysLate > 0) {
            return daysLate * 1.0;
        }
        return 0.0;
    }

    public double getFine() {
        return fine;
    }

    @Override
    public String toString() {
        String fineMessage = fine > 0 ? "Fine Applied: $" + String.format("%.2f", fine) : "No fine.";
        
        return "--- Return Receipt ---\n" +
               "Return Transaction ID: " + returnTransactionId + "\n" +
               "Original Borrow ID: " + borrowTransaction.getTransactionId() + "\n" +
               "Member: " + borrowTransaction.getMember().getName() + "\n" +
               "Book: '" + borrowTransaction.getBook().getTitle() + "'\n" +
               "Return Date: " + returnDate + "\n" +
               fineMessage + "\n" +
               "----------------------";
    }
}