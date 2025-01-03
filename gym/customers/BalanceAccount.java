package gym.customers;
public class BalanceAccount {
    private int balance;

    public BalanceAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Amount must be positive");
        }
    }

    public void withdraw(int amount) {
            balance -= amount;
    }


    public int getBalance() {
        return balance;
    }
}
