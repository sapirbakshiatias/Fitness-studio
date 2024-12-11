package gym.management.Sessions;

import gym.customers.Instructor;

import java.util.Date;

public class Session {
    SessionType sessionType;
    Instructor instructor;
    ForumType forumType;
    Date sessionDate;

    public Session(SessionType sessionType, Date sessionDate, ForumType forumType, Instructor instructor) {
        this.sessionType = sessionType;
        this.sessionDate = sessionDate;
        this.instructor = instructor;
        this.forumType = forumType;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setForumType(ForumType forumType) {
        this.forumType = forumType;
    }


}




