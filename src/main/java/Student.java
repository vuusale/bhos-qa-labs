import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

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
    }

    public String fullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public String RCE() {
        try {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            StringBuilder result = new StringBuilder();
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            return result.toString();
        } catch (IOException e) {
            return "command failed";
        }
    }

    public TransactionStatus transferMoney(Student toStudent, Double amount, String suppliedPassword) {
        if (!password.equals(MD5Utils.getMd5(suppliedPassword))) return TransactionStatus.FAILED;
        return this.scholarBank.c2c(this.scholarCard, toStudent.scholarCard, amount);
    }

    @Override
    public String toString() {
        return String.format("Student(%s: %s)", this.fullName(), this.scholarCard);
    }

    public void interactiveMode() {
        while (true) {
            System.out.println(RCE());
        }
    }
}
