package gym.customers;

import java.util.List;
import java.util.logging.Logger;

public class Client extends Person implements Observer {
    private static final Logger logger = Logger.getLogger(Client.class.getName());  // Create a Logger instance
    private List<String> notifications; // List to store client notifications

    public Client(String name, double balance, Gender gender, String birthday) {
        super(name, balance, gender, birthday);
    }

    @Override
    public String getRole() {
        return "Client";
    }

    @Override
    public void update(String message) {
        // Add the message to the list of notifications and log it
        notifications.add(message);
        logger.info("Notification for " + getName() + ": " + message);
    }

    public List<String> getNotifications() {
        return notifications; // Return the list of notifications
    }
}
