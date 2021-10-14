import java.time.LocalDate;

public class BankCard {
    public String pan;
    public String fullName;
    public String expireDate;
    public Double balance;
    public Integer cvv;

    public BankCard(String pan, String fullName, Double balance) {
        this.pan = pan;
        this.cvv = (int) ((Math.random() * (999 - 100)) + 100);
        this.fullName = fullName;
        StringBuilder temp = new StringBuilder();
        LocalDate currentDate = LocalDate.now();
        temp.append(currentDate.getMonthValue());
        temp.append("/");
        temp.append(currentDate.getYear() % 100 + 2);
        this.expireDate = temp.toString();
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("BankCard<full name: %s, PAN: %s, CVV: %s, expire date: %s>", this.fullName, this.pan, this.cvv, this.expireDate);
    }

    public double getBalance() {
        return balance;
    }

    public String getPan() {
        return pan;
    }

    public void setBalance(double amount) {
        this.balance += amount;
    }
}
