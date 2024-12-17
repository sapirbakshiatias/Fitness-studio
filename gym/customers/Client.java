package gym.customers;

import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Sessions.ForumType;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class Client extends Person implements Observer {
    private static final Logger logger = Logger.getLogger(Client.class.getName());  // Create a Logger instance
    private List<ForumType> clientForum;
    private  List<String> notifications; // List to store client notifications
    private List<Session> enrolledSessions;
    public Client(Person newC) throws InvalidAgeException {
        super(newC);
        if (getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register ");
        }
        this.notifications = new ArrayList<>();
        this.clientForum = new ArrayList<>();
        this.enrolledSessions = new ArrayList<>();

        setForumC();
    }

    public void setForumC() throws InvalidAgeException {
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

    @Override
    public String getRole() {
        return "Client";
    }

    @Override
    public void update(String message) {
        // Add the message to the list of notifications and log it
        this.notifications.add(message);
        logger.info("Notification for " + getName() + ": " + message);
    }

    public List<String> getNotifications() {
        return notifications; // Return the list of notifications
    }


    public List<ForumType> getForumC() {
        return this.clientForum;
    }
}