package gym.customers;

import gym.Exception.InvalidAgeException;
import gym.management.Observer;
import gym.management.Sessions.ForumType;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class Client implements Observer {
    private Person person;
    private static final Logger logger = Logger.getLogger(Client.class.getName());  // Create a Logger instance
    private List<ForumType> clientForum;
    private List<Session> myRegisteredSession;
    // **
    private List<String> notifications;
    public Client(Person person) throws InvalidAgeException {
        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register ");
        }
        this.person = person;
        this.notifications = new ArrayList<>();
        this.clientForum = new ArrayList<>();
        this.myRegisteredSession = new ArrayList<>();
        //this.setRole("Client");
        // **
        this.notifications = new ArrayList<>();
        setForumC();
    }
    public Person getPerson() {
        return person;
    }

    public void setForumC() {
        if (person.getAge() >= 65) {
            clientForum.add(ForumType.Seniors);
        }
        if (person.getGender() == Gender.Male) {
            clientForum.add(ForumType.Male);
        }
        if (person.getGender() == Gender.Female) {
            clientForum.add(ForumType.Female);
        }
        clientForum.add(ForumType.All);
    }

    public void clearClientData() {
        this.clientForum.clear();
        //   this.myRegisteredSession.clear();
        this.myRegisteredSession.clear();
      //  this.setRegistered(false);
        //this.setRole("Person");
        //**fixme
        this.notifications.clear();
    }

    public String getName() {
        return person.getName();
    }
//    public String getRole() {
//        return person.getRole();
//    }
public BalanceAccount getBalanceAccount() {
    return person.getBalanceAccount();
}

    public List<ForumType> getForumC() {
        return this.clientForum;
    }

    public List<Session> getRegisteredSessions() {
        return this.myRegisteredSession;
    }

    public void addRegisteredSession(Session s) {
        this.myRegisteredSession.add(s);
    }

    @Override
    public String toString() {
        return "ID: " + person.getId() + " | Name: " + person.getName() + " | Gender: " + person.getGender() +
                " | Birthday: " + person.getBirthDate() + " | Age: " + person.getAge() + " | Balance: " + person.getBalanceAccount().getBalance();
    }

    public void addNotificationToHistory(String message) {
        notifications.add(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public void update(String message) {
        notifications.add(message);
    }
}