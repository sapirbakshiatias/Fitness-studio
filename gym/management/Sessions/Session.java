package gym.management.Sessions;

import gym.customers.*;

import java.util.Date;

public abstract class Session {
    private SessionType sessionType;
    private Date date;
    private ForumType forumType;
    private Instructor instructor;
    private double price;
    private int maxParticipants;

    public Session(SessionType sessionType, Date date, ForumType forumType, Instructor instructor) {
        this.date = date;
        this.forumType = forumType;
        this.instructor = instructor;
        this.price = sessionType.getPrice();
        this.maxParticipants = sessionType.getMaxParticipants();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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


}








