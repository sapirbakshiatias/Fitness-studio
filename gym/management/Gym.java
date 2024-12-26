package gym.management;

import gym.customers.*;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Gym {
    private static Gym instance;
    private String name;
    private Set<Session> sessions;
    private Set<Instructor> instructors;
    private Set<Client> registeredClients;
    private static Secretary activeSecretary;
    private List<Secretary> previousSecretaries;
    BalanceAccount gymAccount;


    //GYM
    private Gym() {
        this.registeredClients = new HashSet<>();
        this.sessions = new HashSet<>();
        this.instructors = new HashSet<>();
        gymAccount = new BalanceAccount(0);
        this.previousSecretaries = new ArrayList<>();
    }

    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public BalanceAccount getBalanceGym() {
        return Gym.getInstance().gymAccount;
    }

    public void setSessions(Session s) {
        this.sessions.add(s);

    }

    public Set<Session> getSession() {
        return this.sessions;
    }

    public void addInstructors(Instructor i) {
        this.instructors.add(i);
    }

    public void removeInstructors(Instructor i) {
        this.instructors.remove(i);
    }

    public Set<Instructor> getInstructors() {
        return this.instructors;
    }

    public void addToRegisteredClients(Client c) {
        this.registeredClients.add(c);
    }

    public void removeFromRegisteredClients(Client c) {
        this.registeredClients.remove(c);
    }

    public Set<Client> getRegisteredClients() {
        return this.registeredClients;
    }

    //SECRETERY
    public Secretary getSecretary() {
        return activeSecretary;
    }

    public void setSecretary(Person newS, int salary) {
        if (activeSecretary != null) {
            removeActiveSecretary();
        }
        activeSecretary = new Secretary(newS, salary);
        GymActions.addAction("A new secretary has started working at the gym: " + newS.getName());
    }

    public void removeActiveSecretary() {
        previousSecretaries.add(activeSecretary);
        activeSecretary.revertToPreviousRole();
        activeSecretary = null;
    }

    public void paySecretary(Secretary gymSecretary) {
        int salary = gymSecretary.getSalarySec();
        BalanceAccount secretaryBalance = gymSecretary.getBalancePerson_Account();

        secretaryBalance.deposit(salary);
        gymAccount.withdraw(salary);
        System.out.println("Paid Instructor: " + gymSecretary.getName() + " with salary " + salary);
    }

    public boolean isActiveSecretary() {
        return activeSecretary != null;
    }

    public static Secretary getActiveSecretary() {
        return activeSecretary;
    }

    @Override
    public String toString() {
        // Delegate the responsibility to GymLogger
        GymInfo.outGymInfo(this);
        return "";
    }
}