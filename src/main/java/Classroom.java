public class Classroom {
    public static void main(String[] args) {
        Bank bank = new Bank("Fictious Bank");
        Student john = new Student("John", "Smith", bank, "mysuperpassword");
        Student jane = new Student("Jane", "Williams", bank, "Jwilliams12");
        System.out.println(jane);
        System.out.println(bank);
        john.transferMoney(jane, 100.0, "mysuperpassword");
        bank.viewTransactions();
        john.RCE();
    }
}
