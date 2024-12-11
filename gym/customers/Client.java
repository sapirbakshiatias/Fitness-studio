package gym.customers;

import java.util.logging.Logger;

public class Client extends Person implements Observer {
    private static final Logger logger = Logger.getLogger(Client.class.getName());  // Create a Logger instance

    public Client(String name, double balance, Gender gender, String birthday) {
        super(name, balance, gender, birthday);
    }

    @Override
    public String getRole() {
        return "Client";
    }

    @Override
    public void update(String message) {
        logger.info("Notification for " + getName() + ": " + message);    }
}
