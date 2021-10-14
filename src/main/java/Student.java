public class Student {
    public String firstName;
    public String lastName;
    public Bank scholarBank;
    public BankCard scholarCard;

    public Student(String firstName, String lastName, Bank scholarBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.scholarBank = scholarBank;
        this.scholarCard = scholarBank.issueCard(this.fullName());
    }

    public String fullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public TransactionStatus transferMoney(Student toStudent, Double amount) {
        return this.scholarBank.c2c(this.scholarCard, toStudent.scholarCard, amount);
    }

    @Override
    public String toString() {
        return String.format("Student(%s: %s)", this.fullName(), this.scholarCard);
    }
}
