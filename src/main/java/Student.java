public class Student {
    public String firstName;
    public String lastName;
    public Bank scholarBank;
    public BankCard scholarCard;
    public String password;

    public Student(String firstName, String lastName, Bank scholarBank, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.scholarBank = scholarBank;
        this.scholarCard = scholarBank.issueCard(this.fullName());
        this.password = MD5Utils.getMd5(password);
        System.out.println(this.password + this.firstName);
    }

    public String fullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public TransactionStatus transferMoney(Student toStudent, Double amount, String suppliedPassword) {
        if (!password.equals(MD5Utils.getMd5(suppliedPassword))) return TransactionStatus.FAILED;
        return this.scholarBank.c2c(this.scholarCard, toStudent.scholarCard, amount);
    }

    @Override
    public String toString() {
        return String.format("Student(%s: %s)", this.fullName(), this.scholarCard);
    }
}
