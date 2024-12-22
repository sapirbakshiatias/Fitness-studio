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

    public boolean withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Not enough balance");
            return false;
        }
    }

    public int getBalance() {
        return balance;
    }
}
