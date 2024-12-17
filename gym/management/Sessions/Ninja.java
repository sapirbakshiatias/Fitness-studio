//constructor
//implements Session
//get and set

package gym.management.Sessions;

import gym.customers.Instructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Ninja extends Session {

    public Ninja(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        super(SessionType.Ninja, dateTimeStr, forumType, instructor);
    }
}

