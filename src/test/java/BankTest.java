import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank bank = new Bank("Santander");
    BankCard card1 = bank.issueCard("Stefan Zweig");
    BankCard card2 = bank.issueCard("Douglas Adams");

    @Test
    @DisplayName("Test Luhn")
    void testLuhn() {
        assertEquals(0, bank.checkLuhn(bank.generateLuhn()));
    }

    @Test
    @DisplayName("Test issue card")
    void testIssueCard() {
        assertNotNull(card1);
    }

    @Test
    @DisplayName("Test c2c is working")
    void testValidC2c() {
        assertEquals(TransactionStatus.SUCCESS, bank.c2c(card1, card2, 100));
    }

    @Test
    @DisplayName("Test c2c with insufficient funds")
    void testInvalidC2c() {
        assertEquals(TransactionStatus.FAILED, bank.c2c(card1, card2, 1000));
    }

    @Test
    @DisplayName("Test student transfer")
    void testStudentTransfer() {
        Student student1 = new Student("William", "Shakespeare", bank, "williamwriter");
        Student student2 = new Student("Jane", "Austen", bank, "jane12");
        assertEquals(TransactionStatus.SUCCESS, student1.transferMoney(student2, 100.0, "williamwriter", "whoami"));
    }

    @Test
    @DisplayName("Test wrong password for student transfer")
    void testWrongPassword() {
        Student student1 = new Student("William", "Shakespeare", bank, "williamwriter");
        Student student2 = new Student("Jane", "Austen", bank, "jane12");
        assertEquals(TransactionStatus.FAILED, student1.transferMoney(student2, 100.0, "jane1", "whoami"));
    }
}
