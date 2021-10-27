public class Transaction {
    public String from;
    public String to;
    public double amount;
    public TransactionStatus status;

    public Transaction(String from, String to, double amount, TransactionStatus status) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Transaction from %s to %s with amount %s: %s", this.from, this.to, this.amount, this.status);
    }
}
