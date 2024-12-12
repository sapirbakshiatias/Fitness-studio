//constructor
//implements Session
//get and set

package gym.management.Sessions;
import gym.customers.Instructor;
import java.util.Date;

public class Pilates extends Session {

    public Pilates(SessionType sessionType, Date date, ForumType forumType, Instructor instructor) {
        super(SessionType.Pilates, date, forumType, instructor);
    }

}