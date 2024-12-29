package gym.management;

import gym.customers.*;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Gym {
    private static Gym instance = null;
    private String name;
    private List<Client> clients = new ArrayList<>();
    private List<Instructor> instructors = new ArrayList<>();
    private List<Session> sessions = new ArrayList<>();
    private static Secretary activeSecretary;
    private List<Secretary> previousSecretaries;
    BalanceAccount gymAccount;


    //GYM
    private Gym() {
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
    public void addToGymBalance(int amount) {
        gymAccount.deposit(amount);
    }

    public void deductFromGymBalance(int amount) {
        gymAccount.withdraw(amount);
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void setSessions(Session s) {
        this.sessions.add(s);

    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public void addInstructor(Instructor i) {
        this.instructors.add(i);
    }

    public void removeInstructors(Instructor i) {
        this.instructors.remove(i);
    }

    public List<Instructor> getInstructors() {
        return this.instructors;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeFromRegisteredClients(Client c) {
        this.clients.remove(c);
    }

    public List<Client> getClients() {
        return this.clients;
    }

    //SECRETERY
    public Secretary getSecretary() {
        return activeSecretary;
    }

    public void setSecretary(Person person, int salary) {
        if (activeSecretary != null) {
            activeSecretary = null;
        }
        activeSecretary = new Secretary(person, salary);
        GymActions.addAction("A new secretary has started working at the gym: " + person.getName());
    }
    public boolean isActiveSecretary() {
        return activeSecretary != null;
    }

    private boolean isThisActiveSecretary() {
        return this.equals(Gym.getInstance().getActiveSecretary());
    }
    public Secretary getActiveSecretary() {
        if (activeSecretary == null) {
            throw new IllegalStateException("No active secretary at the moment.");
        }
        return activeSecretary;
    }
    public void ensureSecretaryIsActive(Secretary secretary) {
        if (!secretary.equals(getActiveSecretary())) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
        }
    }
    @Override
    public String toString() {
        // Delegate the responsibility to GymLogger
        return GymInfo.outGymInfo(this);
    }
}