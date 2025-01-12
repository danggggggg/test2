import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    public void testInitialBalance() {
        BankAccount account = new BankAccount("12345", 100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount("12345", 50.0);
        account.deposit(50.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testWithdraw() {
        BankAccount account = new BankAccount("12345", 100.0);
        account.withdraw(30.0);
        assertEquals(70.0, account.getBalance());
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        BankAccount account = new BankAccount("12345", 50.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(60.0));
    }

    @Test
    public void testNegativeDeposit() {
        BankAccount account = new BankAccount("12345", 50.0);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10.0));
    }

    @Test
    public void testNegativeWithdraw() {
        BankAccount account = new BankAccount("12345", 50.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10.0));
    }

    @Test
    public void testZeroDeposit() {
        BankAccount account = new BankAccount("12345", 50.0);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0));
    }

    @Test
    public void testZeroWithdraw() {
        BankAccount account = new BankAccount("12345", 50.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        BankAccount account = new BankAccount("12345", 100.0);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(1);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.withdraw(1);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(100.0, account.getBalance());
    }
}
