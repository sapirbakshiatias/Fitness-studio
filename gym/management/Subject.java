package gym.management;

import gym.customers.Client;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private static List<Observer> observers = new ArrayList<>();
     private static String messageHistory;
    public Subject(String messageHistory) {
        this.messageHistory = messageHistory;
    }

    public void attachObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detachObserver(Observer observer) {
        observers.remove(observer);
    }

    public static void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public static void sendNotification(Client client, String messageContent) {
        String message = new String(messageContent);
        client.addNotificationToHistory(message);
        // Log message sent
        GymActions.Info( messageContent);

        // Notify observers about the event (message sent)
        notifyObservers(messageContent);
    }
}
