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
import java.util.List;

public class Secretary extends Subject {
    private Person person;
    private int salary;
    //**
    private Notify notifier;
    private Subject notificationSender = new Subject("");
    private String Role;


    public Secretary(Person person, int salary) {
        super("");
        this.person = person;
        this.salary = salary;
        this.notifier = new Notify();
        this.Role = "Secretary";
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
    public Client registerClient(Person person) throws NullPointerException, InvalidAgeException, DuplicateClientException {
        Gym.getInstance().ensureSecretaryIsActive(this);

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
        attachObserver(client); // Attach client as observer
        String action = "Registered new client: " + person.getName();
        GymActions.addAction(action);
        //FIXME
        //   notificationSender.attachObserver(person);
        return client;
    }

    public void unregisterClient(Client client) throws NullPointerException, ClientNotRegisteredException {
        Gym.getInstance().ensureSecretaryIsActive(this);

        if (!Gym.getInstance().getClients().remove(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        detachObserver(client); // Detach client from observers
        Gym.getInstance().removeFromRegisteredClients(client);
        client.clearClientData();
        notificationSender.detachObserver(client);
        String action = "Unregistered client: " + client.getPerson().getName();
        GymActions.addAction(action);

    }

    public Instructor hireInstructor(Person person, int hourlyRate, List<SessionType> qualifications) throws NullPointerException {
        Gym.getInstance().ensureSecretaryIsActive(this);

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

    public Session addSession(SessionType type, String date, ForumType forum, Instructor instructor) throws NullPointerException, InstructorNotQualifiedException {
        Gym.getInstance().ensureSecretaryIsActive(this);
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
        instructor.addSessionsOfInstructor(session);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDate = sessionDateTime.format(formatter);
        String action = "Created new session: " + type.getName() + " on " + formattedDate + " with instructor: " + instructor.getPerson().getName();
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
    public void registerClientToLesson(Client client, Session session) throws NullPointerException, DuplicateClientException, ClientNotRegisteredException {
        Gym.getInstance().ensureSecretaryIsActive(this);
        LessonRegistrar lessonRegistrar = new LessonRegistrar();
        lessonRegistrar.registerClientToLesson(client, session);
    }
    public void paySalaries() throws NullPointerException {
        Gym.getInstance().ensureSecretaryIsActive(this);
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
        String action = "Salaries have been paid to all employees";
        GymActions.addAction(action);
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


    @Override
    public  String toString() {
        return "ID: " + person.getId() + " | Name: " + person.getName() + " | Gender: " + person.getGender() +
                " | Birthday: " + person.getBirthDate() + " | Age: " + person.getAge() + " | Balance: " + person.getBalanceAccount().getBalance()
                + " | Role: " + Role + " | Salary per Month: " + getSalarySec();
    }

    public void printActions() {
        GymActions.printActions();
    }

    public boolean isThisTheActiveSec(Person person) {
        // Log the error message without throwing an exception
        System.out.println("Error: Former secretaries are not permitted to perform actions.");
        return false; // Indicate an error occurred without halting the program
    }
    private boolean isThisActiveSecretary() {
        return this.equals(Gym.getInstance().getActiveSecretary());
    }
}

