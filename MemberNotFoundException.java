package libraryManagementSystem;

public class MemberNotFoundException extends LibraryException {
	private static final long serialVersionUID = 1L;
    public MemberNotFoundException(String message) {
    	
        super(message);
    }
}
