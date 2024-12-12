package gym.management;

import gym.management.Sessions.*;

import gym.Exception.*;
import gym.customers.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class gymSecretary {
    private ArrayList<Person> registeredClients = new ArrayList<>();


    public void unregisterClient(Client c2) throws ClientNotRegisteredException {
        if (!registeredClients.contains(c2)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        registeredClients.remove(c2);
    }

    public Client registerClient(Person newC) throws DuplicateClientException, InvalidAgeException {
        try {
            int age = newC.getAge();
        } catch (InvalidAgeException e) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }
        if (isPersonClient(newC)) {
            throw new DuplicateClientException("Error: The client is already registered");
        }
        Client newClient = new Client(newC.getName(), newC.getBalance(), newC.getGender(), newC.getBirthday());
        registeredClients.add(newClient);
        return newClient;
    }

    public Instructor hireInstructor(Person p4, int i, ArrayList<Object> objects) {
    }

    public Session addSession(SessionType s, Date d, ForumType f, Instructor i2) throws InstructorNotQualifiedException {
        if (i2.getQualifiedSTypes().contains(s)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        Session newSession = SessionFactory.createSession(s, d, f, i2);
        newSession.setInstructor(i2);
        return newSession;
    }

    public void registerClientToLesson(Client c1, Session s1) throws DuplicateClientException, ClientNotRegisteredException {
        //TODO.  forom/ אם יש מקום. / האם השיעור כבר היה.
        if (!c1.getRole().equals("Client")) { //not client
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }
        if (s1.getParticipants().contains(c1)) { //registered
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }
        if (s1.getParticipants().size() < s1.getSessionType().getMaxParticipants()) { //no place

        }
        if (s1.getDate() < LocalDate) { //not in the future

        }
        if(s1.getForumType().equals(c1.getGender())){ //gender != match

        }

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