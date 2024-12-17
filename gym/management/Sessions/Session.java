package gym.management.Sessions;

import gym.customers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Session {
    private SessionType sessionType;
    private LocalDateTime sessionDateTime;
    private ForumType forumType;
    private Instructor instructor;
    private double price;
    private int maxParticipants;
    private List<Client> participants;



    public Session(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.sessionDateTime = LocalDateTime.parse(dateTimeStr, formatter); // המרה ממחרוזת ל-LocalDateTime
        this.forumType = forumType;
        this.instructor = instructor;
        this.price = sessionType.getPrice();
        this.maxParticipants = sessionType.getMaxParticipants();
        List<Client> participants = new ArrayList<>();
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
        return sessionType;
    }
    public List<Client> getParticipants() {
        return participants;
    }

    // Setter for participants
    public void setParticipants(Client c) {
        participants.add(c);
    }
    public boolean isParticipantRegistered(Client client) {
        return participants.contains(client);
    }

    public boolean isFull() {
        return participants.size() >= maxParticipants;
    }

    public void addParticipant(Client client) {
        if (!isFull() && !isParticipantRegistered(client)) {
            participants.add(client);
        }
    }
}








