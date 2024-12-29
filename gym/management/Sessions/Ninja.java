//constructor
//implements Session
//get and set

package gym.management.Sessions;

import gym.customers.Instructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class Ninja extends Session {
    public Ninja(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        super(sessionType, dateTimeStr, forumType, instructor);
    }
}

