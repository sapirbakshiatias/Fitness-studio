package gym.management;

import gym.customers.Client;
import gym.management.Sessions.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Notify {
    public void notifyBySession(Session session, String notification) {
        if (session != null && notification != null) {

            for (Client client : session.getParticipants()) {
                //fixme
                //session.sendNotificationToParticipants(notification);
                NotificationService.sendNotification(client, notification);
                client.addNotificationToHistory(notification);


            }
            GymActions.addAction("A message was sent to everyone registered for session" + session + session.getDateTime() + ":" + notification);// Optionally store message in history
        }
    }

    public void notifyByDate(String date, String message) {
        if (date != null && message != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");  // Adjust the format as needed
            LocalDate notificationDate = LocalDate.parse(date, formatter);

            for (Session session : Gym.getInstance().getSessions()) {
                if (session.getDateTime().toLocalDate().equals(notificationDate)) {  // Check if session is on the specified date
                    for (Client client : session.getParticipants()) {
                        NotificationService.sendNotification(client, message);
                        client.addNotificationToHistory(message);  // Optionally store message in history
                    }
                }
            }
            GymActions.addAction("A message was sent to everyone registered for a session on" + date + ":" + message);// Optionally store message in history
        }
    }


    public void notifyByString(String message) {
        if (message != null) {
            for (Client client : Gym.getInstance().getClients()) {
                NotificationService.sendNotification(client, message);
                client.addNotificationToHistory(message);
            }
            GymActions.addAction("A message was sent to all gym clients:" + message);// Optionally store message in history
        }
    }
}
