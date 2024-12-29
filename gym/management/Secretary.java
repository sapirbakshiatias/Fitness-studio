package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.Client;
import gym.customers.Instructor;
import gym.customers.Person;
import gym.customers.SalaryManager;
import gym.management.Sessions.ForumType;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionFactory;
import gym.management.Sessions.SessionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//FIXME להפריד בין יצירת מזיכרה, כמו CLIENT, לבין תפקיד המזכירה
public class Secretary extends Subject {
    private Person person;
    private int salary;
    //**
    private Notify notifier;
    private NotificationService notificationSender = new NotificationService("");


//TODO מזכירה אקטיבית בלבד יכולה לבצע פעולות

    public Secretary(Person person, int salary) {
        this.person = person;
        this.salary = salary;
        this.notifier = new Notify();
    }
    public String getName() {
        return person.getName();
    }

    //Get and Set
    //FIXME
    public int getSalarySec() {
        return this.salary;
    }

    public void setSalarySec(int salary) {
        this.salary = salary;
    }

    //Methods
    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {
        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }
        for (Client client : Gym.getInstance().getClients()) {
            if (client.getPerson().equals(person)) {
                throw new DuplicateClientException("Error: The client is already registered");
            }
        }
        Client client = new Client(person);
        Gym.getInstance().addClient(client);
        attach(client); // Attach client as observer
        String action = "Registered new client: " + person.getName();
        GymActions.addAction(action);
        //FIXME
     //   notificationSender.attachObserver(person);

        return client;
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        if (!Gym.getInstance().getClients().remove(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        detach(client); // Detach client from observers
        Gym.getInstance().removeFromRegisteredClients(client);
        client.clearClientData();
        notificationSender.detachObserver(client);
        String action = "Unregistered client: " + client.getPerson().getName();
        GymActions.addAction(action);

    }

    public Instructor hireInstructor(Person person, int hourlyRate, List<SessionType> qualifications) {
        if (qualifications.isEmpty()) {
            System.out.println("Cannot hire instructor without valid session types");
            return null;
        }
        for (Instructor instructor : Gym.getInstance().getInstructors()) {
            if (instructor.getPerson().getId() == person.getId()) {
                System.out.println("Instructor already exists: " + person.getName());
                return null;
            }
        }
        Instructor instructor = new Instructor(person, hourlyRate, qualifications);
        Gym.getInstance().addInstructor(instructor);
        String action = "Hired new instructor: " + person.getName() + " with salary per hour: " + hourlyRate;
        GymActions.addAction(action);
        return instructor;
    }
    public void fireInstructor(Instructor instructor) {

    }
    public Session addSession(SessionType type, String date, ForumType forum, Instructor instructor) throws InstructorNotQualifiedException {
        LocalDateTime sessionDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        if (!instructor.getQualifiedSTypes().contains(type)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        if (!canAddSession(sessionDateTime, instructor)) {
            System.out.println("Error: Cannot schedule session at " + sessionDateTime + ". Instructor is already teaching at this time.");
            return null;
        }
        Session session = SessionFactory.createSession(type, date, forum, instructor);
        Gym.getInstance().addSession(session);
        //FIXME- כשמוסיפים שיעור בפונקציה, להוסיף ב1
        instructor.incrementClassCount();
        instructor.addSessionsOfInstructor(session);
        String action = "Created new session: " + type.getName() + " on " + date + " with instructor: " + instructor.getPerson().getName();
        GymActions.addAction(action);
        return session;
    }

    public static boolean canAddSession(LocalDateTime newStartTime, Instructor instructor) {
        for (Session existingSession : instructor.getSessions()) {
            LocalDateTime existingStart = existingSession.getDateTime();
            LocalDateTime existingEnd = existingStart.plusHours(1);
            if (existingSession.getInstructor().equals(instructor) &&
                    !newStartTime.isBefore(existingStart) && newStartTime.isBefore(existingEnd)) {
                GymActions.addAction("Failed registration: Instructor " + instructor.getName() +
                        " already has a session at this time");
                return false;
            }
        }
        return true;
    }

    public void registerClientToLesson(Client client, Session session) throws InvalidAgeException, DuplicateClientException, ClientNotRegisteredException {
//        if (!isThisActiveSecretary()) {
//            throw new IllegalStateException("Error: Only the active secretary can perform this action.");
//        }
        boolean hasError = false;

        // Check if the client is registered in the gym
        if (!isClientRegistered(client)) {
            hasError = true;
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
            //FIXME
            System.out.println("Error: The client is not registered with the gym and cannot enroll in lessons");
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
            //FIXME
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
    //
    private boolean isForumCompatible(Client client, Session session) {
        //FIXME
        if (!session.isForumCompatible(client)) {
            GymActions.addAction("Failed registration: Client's gender doesn't match the session's gender requirements (" + session.getForumType() + ")");
            return false;
        }
        return true;
    }
    //
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


    public void paySalaries() {
        SalaryManager salaryManager = new SalaryManager();
        int totalPayment = 0;

        // Pay salaries to instructors
        for (Instructor instructor : Gym.getInstance().getInstructors()) {
            int payment = salaryManager.calculateSalary(instructor, instructor.getNumberOfClasses());
            instructor.getBalanceAccount().deposit(payment);
            totalPayment += payment;
        }

        // Pay salary to the secretary
        person.getBalanceAccount().deposit(salary);
        totalPayment += salary;

        // Update gym balance and log action
        Gym.getInstance().deductFromGymBalance(totalPayment);
        String action = "Salaries have been paid to all employees, including the secretary.";
        GymActions.addAction(action);
    }
    private boolean isThisActiveSecretary() {
        return this.equals(Gym.getInstance().getActiveSecretary());
    }

    public void notify(Session session, String notification) {
        notifier.notifyBySession(session, notification);
    }

    public void notify(String date, String message) {
        notifier.notifyByDate(date, message);
    }

    public void notify(String message) {
        notifier.notifyByString(message);
    }



//    }
//
//    // Method to update the balance by adding an amount
//    public void addFunds(double amount) {
//        if (amount > 0) {
//            this.balance += amount;
//        }
//    }
//
//    // Method to subtract from the balance (e.g., for expenses)
//    public void deductFunds(double amount) {
//        if (amount > 0 && this.balance >= amount) {
//            this.balance -= amount;
//        }
//    }
//
//    // Optional: you can add any other necessary business logic related to the gym's balance here
//}



    @Override
    public String toString() {
        return "ID: " + person.getId() + " | Name: " + person.getName() + " | Gender: " + person.getGender() +
                " | Birthday: " + person.getBirthDate() + " | Age: " + person.getAge() + " | Balance: " + person.getBalanceAccount().getBalance() +
                " | Role: " + person.getRole() + " | Salary per Month: " + getSalarySec();
    }
    public void printActions() {
        GymActions.printActions();
    }

    public boolean isThisTheActiveSec(Person person) {
        // Log the error message without throwing an exception
        System.out.println("Error: Former secretaries are not permitted to perform actions.");
        return false; // Indicate an error occurred without halting the program
    }
}
