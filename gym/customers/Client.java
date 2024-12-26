package gym.customers;

import gym.Exception.InvalidAgeException;
import gym.management.Observer;
import gym.management.Sessions.ForumType;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class Client extends Person implements Observer {
    private static final Logger logger = Logger.getLogger(Client.class.getName());  // Create a Logger instance
    private List<ForumType> clientForum;
    private List<Session> myRegisteredSession;
    // **
    private List<String> notifications;


    public Client(Person newC) throws InvalidAgeException {
        super(newC);
        if (getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register ");
        }
        this.notifications = new ArrayList<>();
        this.clientForum = new ArrayList<>();
        this.myRegisteredSession = new ArrayList<>();
        this.setRole("Client");
        this.setRegistered(true);
        // **
        this.notifications = new ArrayList<>();
        setForumC();
    }


    public void setForumC() {
        if (this.getAge() >= 65) {
            clientForum.add(ForumType.Seniors);
        }
        if (this.getGender() == Gender.Male) {
            clientForum.add(ForumType.Male);
        }
        if (this.getGender() == Gender.Female) {
            clientForum.add(ForumType.Female);
        }
        clientForum.add(ForumType.All);
    }

    public void clearClientData() {
        this.clientForum.clear();
        //   this.myRegisteredSession.clear();
        this.setRegistered(false);
        this.setRole("Person");
        //**fixme
        this.notifications.clear();
    }


    @Override
    public String getRole() {
        return super.getRole();
    }

    public List<ForumType> getForumC() {
        return this.clientForum;
    }

    public List<Session> getMyRegSession() {
        return this.myRegisteredSession;
    }

    public void setMyRegSession(Session s) {
        this.myRegisteredSession.add(s);
    }

    @Override
    public String toString() {
        return "ID: " + super.getId() + " | Name: " + super.getName() + " | Gender: " + super.getGender() +
                " | Birthday: " + super.getBirthday() + " | Age: " + getAge() + " | Balance: " + getBalanceInt();
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