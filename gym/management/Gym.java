package gym.management;

import gym.customers.*;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;

import java.util.HashSet;
import java.util.Set;

public class Gym {
    private static Gym instance;
    private String name;
    private Set<Person> allPeople;
    private Set<Session> sessions;
    private Set<Instructor> instructors;
    private Set<Client> registeredClients;
    private static GymSecretary activeSecretary;
    BalanceAccount gymAccount;

    //GYM
    private Gym() {
        this.registeredClients = new HashSet<>();
        this.sessions = new HashSet<>();
        this.instructors = new HashSet<>();
        gymAccount = new BalanceAccount(0);
    }

    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("Gym Name: " + name);
    }
    public String getName() {
        return this.name;
    }

    public BalanceAccount getBalanceGym() {
        return Gym.getInstance().getBalanceGym();}

    public void setSessions(Session s) {
        this.sessions.add(s);
    }
    public Set<Session> getSession() {
        return this.sessions;
    }

    public void setAllPeople(Person p) {
        this.allPeople.add(p);
    }
    public Set<Person> getAllPeople() {
        return this.allPeople;
    }
    public void setInstructors(Instructor i) {
        this.instructors.add(i);
    }
    public Set<Instructor> getInstructors() {
        return this.instructors;
    }
    public void setRegisteredClients(Client c) {
        this.registeredClients.add(c);
    }
    public Set<Client> getRegisteredClients() {
        return this.registeredClients;
    }

    //SECRETERY
    public GymSecretary getSecretary() {
        return activeSecretary;
    }

    public void setSecretary(Person newS, int salary) {
        if (activeSecretary != null) {
            System.out.println("There is already an active gym secretary.");
            return;
        }
        activeSecretary = new GymSecretary(newS, salary);
        System.out.println("");
        //FIXME logger
    }

    public void removeSecretary() {
        activeSecretary = null;
    }
    public void paySecretary(GymSecretary gymSecretary) {
        double salary = gymSecretary.getSalarySec();
        BalanceAccount secretaryBalance = gymSecretary.getBalanceAccount();

        secretaryBalance.deposit(salary); // העבר את המשכורת לחשבון של המאמן
        gymAccount.withdraw(salary); // הורד את המשכורת מהמאזן של המכון
        System.out.println("Paid Instructor: " + gymSecretary.getName() + " with salary " + salary);
    }
}

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

