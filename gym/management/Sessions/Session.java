package gym.management.Sessions;

import gym.customers.*;
import gym.management.NotificationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Session {
    private SessionType sessionType;
    private LocalDateTime sessionDateTime;
    private ForumType forumType;
    private Instructor instructor;
    private int price;
    private int maxParticipants;
    private List<Client> participants;
    private NotificationService messageSender;



    public Session(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        this.sessionType = sessionType;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.sessionDateTime = LocalDateTime.parse(dateTimeStr, formatter); // המרה ממחרוזת ל-LocalDateTime
        this.forumType = forumType;
        this.instructor = instructor;
        this.price = sessionType.getPrice();
        this.maxParticipants = sessionType.getMaxParticipants();
        this.participants =  new ArrayList<>();
        this.messageSender = messageSender;
    }

    public LocalDateTime getDateTime() {
        return sessionDateTime;
    }

    public void setDateTime(LocalDateTime sessionDateTime) {
        this.sessionDateTime = sessionDateTime;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public void setForumType(ForumType forumType) {
        this.forumType = forumType;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public SessionType getSessionType() {
        return this.sessionType;
    }
    public List<Client> getParticipants() {
        return this.participants;
    }

    public boolean isFull() {
        return participants.size() >= maxParticipants;
    }

    public void addParticipant(Client client) {
            participants.add(client);
        }
    public int numOfParticipant() {
        return participants.size();
    }
    @Override
    public String toString() {
        return "Session Type: " + getSessionType().getName() + " | Date: " + sessionDateTime + " | Forum: " + getForumType() +
                " | Instructor: " + getInstructor().getName() + " | Participants: " + numOfParticipant() + "/" + maxParticipants;
    }
    public void sendNotificationToParticipants(String messageContent) {
        for (Client client : participants) {
            // Send message to each participant
            messageSender.sendNotification(client, messageContent);

        }
    }
}








