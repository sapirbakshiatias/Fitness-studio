package gym.management;


import gym.Exception.DuplicateClientException;
import gym.customers.Client;
import gym.customers.Instructor;
import gym.customers.Person;
import gym.management.Sessions.Session;

import java.util.ArrayList;


public class gymSecretary {
    private ArrayList<Person> registeredClients = new ArrayList<>();


    public void unregisterClient(Client c2) {
        registeredClients.remove(c2);
    }

    public Client registerClient(Person newP) throws DuplicateClientException {

        if (isPersonClient(newP)) {
            throw new DuplicateClientException("The client is already registered");
        }
        Client newClient = new Client(newP.getName(), newP.getBalance(), newP.getGender(), newP.getBirthday());
        registeredClients.add(newClient);
        return newClient;
    }

    public Instructor hireInstructor(Person p4, int i, ArrayList<Object> objects) {
    }

    public Session addSession(Object pilates, String s, Object all, Instructor i2) {
    }

    public void registerClientToLesson(Client c1, Session s1) {
    }

    public void paySalaries() {
    }

    public boolean isPersonClient(Person p) {
        //todo: same name
        for (Person person : registeredClients) {
            if (person.getName().equals(p.getName())) return true;
        }
        return false;
    }

    public void notify(Session s4, String s) {
        //todo: observer
    }
}



//todo: balce gym
//    }
//
//    // Method to update the balance by adding an amount
//    public void addFunds(double amount) {
//        if (amount > 0) {
//            this.balance += amount;
//        }
//    }
//
//    // Method to subtract from the balance (e.g., for expenses)
//    public void deductFunds(double amount) {
//        if (amount > 0 && this.balance >= amount) {
//            this.balance -= amount;
//        }
//    }
//
//    // Optional: you can add any other necessary business logic related to the gym's balance here
//}
