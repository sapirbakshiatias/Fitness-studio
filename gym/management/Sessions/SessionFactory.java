package gym.management.Sessions;

import gym.customers.Instructor;

import java.time.LocalDateTime;
import java.util.Date;

public class SessionFactory {
    public static Session createSession(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        if (sessionType == SessionType.Ninja) {
            return new Ninja(sessionType, dateTimeStr, forumType, instructor);
        } else if (sessionType == SessionType.Pilates) {
            return new Pilates(sessionType, dateTimeStr, forumType, instructor);
        } else if (sessionType == SessionType.MachinePilates) {
            return new MachinePilates(sessionType, dateTimeStr, forumType, instructor);
        } else if (sessionType == SessionType.ThaiBoxing) {
            return new ThaiBoxing(sessionType, dateTimeStr, forumType, instructor);
        }
        throw new IllegalArgumentException("Invalid session type: " + sessionType);
    }
}







