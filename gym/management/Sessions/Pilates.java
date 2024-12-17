//constructor
//implements Session
//get and set

package gym.management.Sessions;
import gym.customers.Instructor;
import java.util.Date;

public class Pilates extends Session {

    public Pilates(SessionType sessionType, String dateTimeStr, ForumType forumType, Instructor instructor) {
        super(SessionType.Pilates, dateTimeStr, forumType, instructor);
    }

}