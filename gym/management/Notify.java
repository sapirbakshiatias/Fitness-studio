package gym.management;

import gym.customers.Client;
import gym.management.Sessions.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Notify {
    public void notifyBySession(Session session, String notification) {
        if (session != null && notification != null) {
            for (Client client : session.getParticipants()) {
                NotificationService.sendNotification(client, notification);
            }
            GymActions.addAction("A message was sent to everyone registered for session " + session.getSessionType().getName() + " on "
                    + session.getDateTime() + " : " + notification);// Optionally store message in history
        }
    }

    public void notifyByDate(String date, String message) {
        if (date != null && message != null) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate notificationDate = LocalDate.parse(date, inputFormatter);
            for (Session session : Gym.getInstance().getSessions()) {
                if (session.getDateTime().toLocalDate().equals(notificationDate)) {  // Check if session is on the specified date
                    for (Client client : session.getParticipants()) {
                        NotificationService.sendNotification(client, message);
                    }
                }
            }
            GymActions.addAction("A message was sent to everyone registered for a session on " + notificationDate + " : " + message);// Optionally store message in history
        }
    }


    public void notifyByString(String message) {
        if (message != null) {
            for (Client client : Gym.getInstance().getClients()) {
                NotificationService.sendNotification(client, message);
            }
            GymActions.addAction("A message was sent to all gym clients: " + message);// Optionally store message in history
        }
    }
}
