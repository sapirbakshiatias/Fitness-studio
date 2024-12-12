//constructor
//implements Session
//get and set

package gym.management.Sessions;
import gym.customers.Instructor;
import java.util.Date;

public class ThaiBoxing extends Session {

    public ThaiBoxing(SessionType sessionType, Date date, ForumType forumType, Instructor instructor) {
        super(SessionType.ThaiBoxing, date, forumType, instructor);
    }
}

