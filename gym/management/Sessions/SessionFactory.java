package gym.management.Sessions;

import gym.customers.Instructor;
import gym.management.Sessions.*;


import java.util.Map;

public class SessionFactory {
    private static final Map<SessionType, Class<? extends Session>> sessionMap = Map.of(
            SessionType.Ninja, Ninja.class,
            SessionType.Pilates, Pilates.class,
            SessionType.MachinePilates, MachinePilates.class,
            SessionType.ThaiBoxing, ThaiBoxing.class
    );

    public static Session createSession(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        Class<? extends Session> sessionClass = sessionMap.get(sessionType);
        if (sessionClass == null) {
            throw new IllegalArgumentException("Invalid session type: " + sessionType.getName());
        }
        try {
            var constructor = sessionClass.getConstructor(SessionType.class, String.class, ForumType.class, Instructor.class);
            return constructor.newInstance(sessionType, dateTimeStr, forumType, instructor);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create session", e);
        }
    }
}