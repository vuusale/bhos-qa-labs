import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public record Bank(String bankName) {
    private static final String[] bins = {"44930685916161","47465495098013","44080036909741","49254625961843","47122747363234"};
    private static final HashMap<String, BankCard> cards = new HashMap<>();
    private static final List<Transaction> transactions = new ArrayList<>();

    public String generateLuhn() {
        Random rand = new Random();
        String bin = bins[rand.nextInt(bins.length)];
        for (int i = 10; i < 99; i++) {
            String stringCardNumber = bin + i;
            if (!cards.containsKey(stringCardNumber) && checkLuhn(stringCardNumber) == 0) {
                return stringCardNumber;
            }
        }
        return "Error";
    }

    public int checkLuhn(String pan) {
        int temp = 0;
        for (int i = 0; i < pan.length(); i++) {
            int number = Integer.parseInt(String.valueOf(pan.charAt(i)));
            if (i % 2 == 0) {
                number *= 2;
                if (number >= 10) {
                    number = number / 10 + number % 10;
                }
            }
            temp += number;
        }
        return temp % 10;
    }

    public BankCard issueCard(String fullName) {
        BankCard newCard = new BankCard(generateLuhn(), fullName, 100.0);
        cards.put(newCard.pan, newCard);
        return newCard;
    }

    public TransactionStatus c2c(BankCard fromCard, BankCard toCard, double amount) {
        int error = 0;
        if (amount < 1.0 && amount > 100) {
            error = 1;
        }
        if (fromCard == null || toCard == null) {
            error = 1;
        }
        // if fromCard is null, below statement will throw error
        if (fromCard.balance < amount) {
            error = 1;
        }
        if (error == 1) {
            Transaction transaction = new Transaction(fromCard.pan, toCard.pan, amount, TransactionStatus.FAILED);
            transactions.add(transaction);
            return TransactionStatus.FAILED;
        }
        toCard.setBalance(amount);
        fromCard.setBalance(-amount);
        Transaction transaction = new Transaction(fromCard.pan, toCard.pan, amount, TransactionStatus.SUCCESS);
        transactions.add(transaction);
        return TransactionStatus.SUCCESS;
    }

    public void viewTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
