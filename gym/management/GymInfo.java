package gym.management;

import gym.customers.Client;
import gym.customers.Instructor;
import gym.management.Sessions.Session;

public class GymInfo {
    public static String outGymInfo(Gym gym) {
        StringBuilder gymInfo = new StringBuilder();

        // Gym Name
        gymInfo.append("Gym Name: ").append(gym.getName()).append("\n");

        // Gym Secretary
        gymInfo.append("Gym Secretary: ").append(
                gym.getSecretary() != null ? gym.getSecretary().toString() : "No active secretary"
        ).append("\n");

        // Gym Balance
        gymInfo.append("Gym Balance: ").append(gym.getBalanceGym().getBalance()).append("\n");

        // Clients Data
        gymInfo.append("\nClients Data:\n");
        for (Client client : gym.getClients()) {
            gymInfo.append(client.toString()).append("\n");
        }

        // Employees Data (Instructors)
        gymInfo.append("\nEmployees Data:\n");
        for (Instructor instructor : gym.getInstructors()) {
            gymInfo.append(instructor.toString()).append("\n");
        }
        gymInfo.append(gym.getSecretary().toString()).append("\n");

        // Sessions Data
        gymInfo.append("\nSessions Data:\n");
        for (int i = 0; i < gym.getSessions().size(); i++) {
            Session session = gym.getSessions().get(i);
            gymInfo.append(session.toString());
            if (i < gym.getSessions().size() - 1) {
                gymInfo.append("\n");
            }
        }

        // Directly print gym information
        return gymInfo.toString().trim();

    }
}
