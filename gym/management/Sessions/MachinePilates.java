//constructor
//implements Session
//get and set

package gym.management.Sessions;

import gym.customers.Instructor;

import java.util.Date;

public class MachinePilates extends Session {
    public MachinePilates(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        super(sessionType, dateTimeStr, forumType, instructor);
    }
}

