package gym.customers;
public class BalanceAccount {
    private double balance;

    public BalanceAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Amount must be positive");
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Not enough balance");
            return false;
        }
    }

    public double getBalance() {
        return balance;
    }
}
