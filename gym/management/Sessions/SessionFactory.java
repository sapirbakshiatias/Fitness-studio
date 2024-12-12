package gym.management.Sessions;

import gym.customers.Instructor;

import java.util.Date;

public class SessionFactory {
    public static Session createSession(SessionType sessionType, Date date, ForumType forumType, Instructor instructor) {
        if (sessionType == SessionType.Ninja) {
            return new Ninja(sessionType, date, forumType, instructor);
        } else if (sessionType == SessionType.Pilates) {
            return new Pilates(sessionType, date, forumType, instructor);
        } else if (sessionType == SessionType.MachinePilates) {
            return new MachinePilates(sessionType, date, forumType, instructor);
        } else if (sessionType == SessionType.ThaiBoxing) {
            return new ThaiBoxing(sessionType, date, forumType, instructor);
        }
        throw new IllegalArgumentException("Invalid session type: " + sessionType);
    }
}







