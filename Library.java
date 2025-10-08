package libraryManagementSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {
    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();
    private List<Author> authors = new ArrayList<>();
    private List<BorrowTransaction> activeBorrows = new ArrayList<>();
    
    private int bookIdCounter = 1;
    private int memberIdCounter = 1;
    private int authorIdCounter = 1;
    private int borrowTxCounter = 1;
    private int returnTxCounter = 1;

    public int getNextBookId() { return bookIdCounter++; }
    public int getNextMemberId() { return memberIdCounter++; }
    public int getNextAuthorId() { return authorIdCounter++; }

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Author findOrCreateAuthor(String name) {
        Optional<Author> existingAuthor = authors.stream()
            .filter(a -> a.getName().equalsIgnoreCase(name))
            .findFirst();

        if (existingAuthor.isPresent()) {
            return existingAuthor.get();
        } else {
            Author newAuthor = new Author(getNextAuthorId(), name);
            authors.add(newAuthor);
            return newAuthor;
        }
    }

    public void borrowBook(int memberId, int bookId) throws LibraryException {
        Member member = findMemberById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("No member found with ID " + memberId));

        Book book = findBookById(bookId)
            .orElseThrow(() -> new BookNotFoundException("No book found with ID " + bookId));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book '" + book.getTitle() + "' is currently out of stock.");
        }

        if (member.getBorrowedBooks().size() >= member.getMaxBooksAllowed()) {
            throw new BorrowLimitExceededException("Member '" + member.getName() + "' has reached the maximum borrow limit.");
        }
        
        boolean alreadyBorrowed = member.getBorrowedBooks().stream()
                .anyMatch(tx -> tx.getBook().getBookId() == bookId);
        if (alreadyBorrowed) {
            throw new BookNotAvailableException("Member has already borrowed a copy of this book.");
        }
        
        LocalDate dueDate = (member instanceof PremiumMember) ? LocalDate.now().plusDays(30) : LocalDate.now().plusDays(14);
        
        book.borrowBook();
        BorrowTransaction transaction = new BorrowTransaction(borrowTxCounter++, member, book, dueDate);
        activeBorrows.add(transaction);
        member.borrowBook(transaction);

        System.out.println("\nBorrow successful!");
        System.out.println(transaction);
        System.out.println("Updated Stock for '" + book.getTitle() + "': " + book.getAvailableStock());
    }

    public void returnBook(int memberId, int bookId) throws LibraryException {
        BorrowTransaction borrowTransaction = activeBorrows.stream()
            .filter(bt -> bt.getBook().getBookId() == bookId && bt.getMember().getMemberId() == memberId)
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException("No active borrow record found for Member ID " + memberId + " and Book ID " + bookId));

        Book book = borrowTransaction.getBook();
        Member member = borrowTransaction.getMember();
        
        book.returnBook();
        ReturnTransaction returnTx = new ReturnTransaction(returnTxCounter++, borrowTransaction);
        
        activeBorrows.remove(borrowTransaction);
        member.returnBook(borrowTransaction);

        System.out.println("\nReturn successful!");
        System.out.println(returnTx);
        System.out.println("Updated Stock for '" + book.getTitle() + "': " + book.getAvailableStock());
    }

    public List<Book> getAvailableBooks() {
        return books.values().stream()
            .filter(Book::isAvailable)
            .collect(Collectors.toList());
    }

    public List<Book> searchBooksByTitle(String query) {
        return books.values().stream()
            .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public Optional<Member> findMemberById(int memberId) {
        return Optional.ofNullable(members.get(memberId));
    }

    private Optional<Book> findBookById(int bookId) {
        return Optional.ofNullable(books.get(bookId));
    }
}
