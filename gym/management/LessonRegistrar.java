package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.customers.Client;
import gym.management.Sessions.Session;

import java.time.LocalDateTime;

public class LessonRegistrar {
    public void registerClientToLesson(Client client, Session session) throws NullPointerException, DuplicateClientException, ClientNotRegisteredException {
        Gym gym = Gym.getInstance();


        boolean hasError = false;

        // Check if the client is registered in the gym
        if (!isClientRegistered(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        // Check if the session exists
        if (!doesSessionExist(session)) {
            hasError = true;
        }

        // Check if the client is already registered for the session
        if (isClientAlreadyRegistered(client, session)) {
            hasError = true;
        }

        // Check if the client is double booked
        if (isClientDoubleBooked(client, session)) {
            hasError = true;
        }

        // Check if the session is full
        if (isSessionFull(session)) {
            hasError = true;
        }

        // Check if the session is in the future
        if (!isSessionInFuture(session)) {
            hasError = true;
        }

        // Check if the client's forum matches the session's forum
        if (!isForumCompatible(client, session)) {
            hasError = true;
        }

        // Check if the client has enough balance
        if (!hasSufficientBalance(client, session)) {
            hasError = true;
        }

        // If errors occurred, do not register the client
        if (hasError) {
            return;
        }

        // Register the client to the session
        registering(session, client);
    }

    private boolean isClientRegistered(Client client) {
        if (!Gym.getInstance().getClients().contains(client)) {
            return false;
        }
        return true;
    }

    //
    private boolean doesSessionExist(Session session) {
        if (!Gym.getInstance().getSessions().contains(session)) {
            System.out.println("This Session does not exist");
            return false;
        }
        return true;
    }

    private boolean isClientAlreadyRegistered(Client client, Session session) {
        if (session.isParticipantRegistered(client)) {
            System.out.println("Error: The client is already registered for this lesson");
            return true;
        }
        return false;
    }

    private boolean isClientDoubleBooked(Client client, Session session) {
        for (Session existingSession : client.getRegisteredSessions()) {
            if (existingSession.equals(session)) {
                continue;
            }
            if (existingSession.getDateTime().equals(session.getDateTime())) {
                System.out.println("Error: Client is already registered for another session at this time");
                return true;
            }
        }
        return false;
    }

    //
    private boolean isSessionFull(Session session) {
        if (session.isFull()) {
            GymActions.addAction("Failed registration: No available spots for session");
            return true;
        }
        return false;
    }

    //
    private boolean isSessionInFuture(Session session) {
        if (session.getDateTime().isBefore(LocalDateTime.now())) {
            GymActions.addAction("Failed registration: Session is not in the future");
            return false;
        }
        return true;
    }

    private boolean isForumCompatible(Client client, Session session) {
        // Check age compatibility first
        if (!session.isAgeCompatible(client)) {
            GymActions.addAction("Failed registration: Client doesn't meet the age requirements for this session (" + session.getForumType() + ")");
            return false;
        }

        // Check forum (gender) compatibility only if age compatibility is true
        if (!session.isForumCompatible(client)) {
            GymActions.addAction("Failed registration: Client's gender doesn't match the session's gender requirements");
            return false;
        }

        return true;
    }


    private boolean hasSufficientBalance(Client client, Session session) {
        if (session.getSessionType().getPrice() > client.getBalanceAccount().getBalance()) {
            GymActions.addAction("Failed registration: Client doesn't have enough balance");
            return false;
        }
        return true;
    }


    private void registering(Session session, Client client) {
        session.setParticipants(client);
        client.addRegisteredSession(session);
        GymActions.addAction("Registered client: " + client.getName() + " to session: " + session.getSessionType().getName() + " on " + session.getDateTime() + " for price: " + session.getSessionType().getPrice());
        client.getPerson().getBalanceAccount().withdraw(session.getSessionType().getPrice());
        Gym.getInstance().addToGymBalance(session.getSessionType().getPrice());
    }
}
