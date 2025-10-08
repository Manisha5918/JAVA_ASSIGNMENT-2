package libraryManagementSystem;

import java.time.LocalDate;

public class PremiumMember extends Member {
    private double monthlyFee;
    private LocalDate membershipExpiryDate;

    public PremiumMember(int memberId, String name, double monthlyFee) {
        super(memberId, name);
        this.maxBooksAllowed = 10;
        this.monthlyFee = monthlyFee;
        this.membershipExpiryDate = LocalDate.now().plusYears(1); // Membership is valid for one year
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public LocalDate getMembershipExpiryDate() {
        return membershipExpiryDate;
    }

    @Override
    public String toString() {
        return "ID: " + getMemberId() + ", Name: " + getName() + ", Type: Premium Member (Expires: " + membershipExpiryDate + ")";
    }
}