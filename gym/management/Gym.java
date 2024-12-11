package gym.management;
import gym.customers.*;

public class Gym {
    private static Gym instance;
    private Gym() {
    }

    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }

    private gymSecretary secretary;

    public gymSecretary getSecretary() {
        return secretary;
    }

    public void setSecretary(Person p1, int i) {
        this.secretary = new gymSecretary();
    }
    public void setName(String name) {
    }
}

//todo: gym balnce
//package gym.management;
//        import gym.customers.*;
//
//public class Gym {
//    private static Gym instance;
//
//    // Adding balance field
//    private double balance;
//
//    // Private constructor to prevent direct instantiation
//    private Gym() {
//        this.balance = 0.0; // Default balance is 0.0 when the gym is created
//    }
//
//    // Singleton getInstance method
//    public static Gym getInstance() {
//        if (instance == null) {
//            instance = new Gym();
//        }
//        return instance;
//    }
//
//    private gymSecretary secretary;
//
//    public gymSecretary getSecretary() {
//        return secretary;
//    }
//
//    public void setSecretary(Person p1, int i) {
//        this.secretary = new gymSecretary();
//    }
//
//    // Getter for balance
//    public double getBalance() {
//        return balance;
//    }
//
//    // Setter for balance
//    public void setBalance(double balance) {
//        this.balance = balance;

