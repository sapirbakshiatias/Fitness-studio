//constructor
//implements Session
//get and set

package gym.management.Sessions;

import gym.customers.Instructor;

import java.util.Date;

public class Ninja extends Session {

    public Ninja(SessionType sessionType, Date date, ForumType forumType, Instructor instructor) {
        super(SessionType.Ninja, date, forumType, instructor);
    }
}

