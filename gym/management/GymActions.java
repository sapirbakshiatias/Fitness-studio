package gym.management;

import java.util.ArrayList;
import java.util.List;

public class GymActions {
    private static List<String> actionsHistory  = new ArrayList<>();;

    // Log general information
    public static void Info(String message) {
    }

//*--Actions history--
    // Add a static method for adding actions
    public static void addAction(String action) {
        actionsHistory.add(action);
    }

    // Add a static method for printing actions
    public static void printActions() {
        for (String action : actionsHistory) {
            System.out.println(action);
        }
    }

}
