public class Classroom {
    public static void main(String[] args) {
        Bank bank = new Bank("Fictious Bank");
        Student john = new Student("John", "Smith", bank);
        Student jane = new Student("Jane", "Williams", bank);
        Student david = new Student("David", "Taylor", bank);
        System.out.println(jane);
        System.out.println(bank);
        john.transferMoney(jane, 100.0);
        bank.viewTransactions();
    }
}
