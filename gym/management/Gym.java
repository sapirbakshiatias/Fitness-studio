package gym.management;

import gym.customers.*;
import gym.management.Sessions.Secretary;
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
    private GymSecretary activeSecretary;
    private List<GymSecretary> previousSecretaries;
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
        return Gym.getInstance().gymAccount;}

    public void setSessions(Session s) {
        this.sessions.add(s);
    }
    public Set<Session> getSession() {
        return this.sessions;
    }

    public void addInstructors(Instructor i) {
        this.instructors.add(i);
    } public void removeInstructors(Instructor i) {
        this.instructors.remove(i);
    }
    public Set<Instructor> getInstructors() {
        return this.instructors;
    }
    public void addToRegisteredClients(Client c) {
        this.registeredClients.add(c);
    }    public void removeFromRegisteredClients(Client c) {
        this.registeredClients.remove(c);
    }
    public Set<Client> getRegisteredClients() {
        return this.registeredClients;
    }

    //SECRETERY
    public GymSecretary getSecretary() {
        return activeSecretary;
    }

    public GymSecretary setSecretary(Person person, int salary){
        if (previousSecretaries.contains(person)) {
            if (activeSecretary != null) {
                activeSecretary.setActive(false);}
            removeActiveSecretary();

        }

        activeSecretary = new GymSecretary(new Secretary(person, salary));
        activeSecretary.activate();
        System.out.println("A new secretary has started working at the gym: " + person.getName());
        //FIXME logger
    }


    public void removeActiveSecretary() {
            previousSecretaries.add(activeSecretary);
            activeSecretary.revertToPreviousRole();
            activeSecretary = null;
    }

    public void paySecretary(GymSecretary gymSecretary) {
        int salary = gymSecretary.getSalarySec();
        BalanceAccount secretaryBalance = gymSecretary.getBalancePerson_Account();

        secretaryBalance.deposit(salary);
        gymAccount.withdraw(salary);
        System.out.println("Paid Instructor: " + gymSecretary.getName() + " with salary " + salary);
    }
    public boolean isActiveSecretary() {
        return activeSecretary != null;
    }
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
    public static GymSecretary getActiveSecretary() {
        return activeSecretary;
    }
}
