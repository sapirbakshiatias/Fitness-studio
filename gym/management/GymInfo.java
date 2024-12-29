package gym.management;

import gym.customers.Client;
import gym.customers.Instructor;
import gym.management.Sessions.Session;

public class GymInfo {
    public static String outGymInfo(Gym gym) {
        StringBuilder gymInfo = new StringBuilder();

        // Gym Name
        gymInfo.append("Gym Name: ").append(gym.getName()).append("\r\n");

        // Gym Secretary
        gymInfo.append("Gym Secretary: ").append(
                gym.getSecretary() != null ? gym.getSecretary().toString() : "No active secretary"
        ).append("\r\n");

        // Gym Balance
        gymInfo.append("Gym Balance: ").append(gym.getBalanceGym().getBalance()).append("\r\n");

        // Clients Data
        gymInfo.append("\r\nClients Data:\r\n");
        for (Client client : gym.getClients()) {
            gymInfo.append(client.toString()).append("\r\n");
        }

        // Employees Data (Instructors)
        gymInfo.append("\r\nEmployees Data:\r\n");
        for (Instructor instructor : gym.getInstructors()) {
            gymInfo.append(instructor.toString()).append("\r\n");
        }
        gymInfo.append(gym.getSecretary().toString()).append("\r\n");

        // Sessions Data
        gymInfo.append("\r\nSessions Data:\r\n");
        for (Session session : gym.getSessions()) {
            gymInfo.append(session.toString()).append("\r\n");
        }

        return gymInfo.toString().trim();
    }
}