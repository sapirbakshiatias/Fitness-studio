package gym.management;

import gym.management.Sessions.*;

import gym.Exception.*;
import gym.customers.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;


public class GymSecretary extends Person {
    private double salary;
    private SalaryManager salaryManager = new SalaryManager();
    private static final Logger logger = Logger.getLogger(Gym.class.getName());
    Set<Session> sessions = Gym.getInstance().getSession();
    Set<Instructor> instructors = Gym.getInstance().getInstructors();
    Set<Client> registeredClients = Gym.getInstance().getRegisteredClients();
    Set<Person> allPeople = Gym.getInstance().getAllPeople();
//TODO מזכירה אקטיבית בלבד יכולה לבצע פעולות

    //constructor
    public GymSecretary(String nweS, double balance, Gender gender, String birthdayStr, double salary) {
        super(nweS, balance, gender, birthdayStr);
        this.salary = salary;
    }

    public GymSecretary(Person nweS, double salary) {
        super(nweS);
        this.salary = salary;
    }

    //Get and Set
    public double getSalarySec() {
        return this.salary;
    }

    public void setSalarySec(double salary) {
        this.salary = salary;
    }


    //Methods
    public Client registerClient(Person newC) throws DuplicateClientException, InvalidAgeException {
        // fixme לא הדפסה, צריך להכניס ללוגר של הPERSON והמכון
        if (newC.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }
        for (Client client : registeredClients) { //already client
            if (client.getId() == newC.getId()) {
                throw new DuplicateClientException("Error: The client is already registered");
            }
        }
        //Client newClient = new Client(newC.getName(), newC.getBalancePerson(), newC.getGender(), newC.getBirthday().toString());
        Client newClient = new Client(newC);
        registeredClients.add(newClient);
        System.out.println("Registered new client: " + newClient.getName());
        return newClient;
    }

    public void unregisterClient(Client c) throws ClientNotRegisteredException {
        boolean SameId = false;
        for (Client client : registeredClients) { //already client
            if (client.getId() == c.getId()) {
                SameId = true;
                break;
            }
        }
        if (!SameId) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        } else {
            registeredClients.remove(c);
            System.out.println("Unregistered client: " + c.getName());
        }
    }

    public Instructor hireInstructor(Person p, int salary, ArrayList<SessionType> qualifiedSTypes) {
        //TODO האם הוא מוגדר קודם כלקוח?
        //TODO לבדוק אם כבר קיים

        if (qualifiedSTypes.isEmpty()) { //no qualifiedSTypes
            System.out.println("Cannot hire instructor without valid session types");
            return null;
        }
        for (Instructor instructor : instructors) {
            if (instructor.getId() == p.getId()) { // already instructor
                System.out.println("Instructor already exists: " + p.getName());
                return null;
            }
        }
        Instructor newInstructor = new Instructor(p.getName(), p.getBalance(), p.getGender(), p.getBirthdayStr(), salary, qualifiedSTypes);
        instructors.add(newInstructor);
        //FIXME logger + dateToString
        System.out.println("Hired new instructor: " + p.getName() + " with salary per hour: " + salary);
        return newInstructor;
    }


    public Session addSession(SessionType s, String dateTimeStr, ForumType f, Instructor i2) throws InstructorNotQualifiedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime sessionDateTime = LocalDateTime.parse(dateTimeStr, formatter);

        if (!i2.getQualifiedSTypes().contains(s)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        if (!canAddSession(sessionDateTime, i2)) {
            System.out.println("Error: Cannot schedule session at " + sessionDateTime +
                    ". Instructor is already teaching at this time.");
            return null;
        }
        Session newSession = SessionFactory.createSession(s, dateTimeStr, f, i2);
        newSession.setInstructor(i2);
        i2.incrementClassCount();
        Gym.getInstance().setSessions(newSession);
        System.out.println("Created new session: " + newSession.getSessionType().getName() + " on " + sessionDateTime + " with instructor: " + i2.getName());

        return newSession;
    }


    public boolean canAddSession(LocalDateTime newStartTime, Instructor instructor) {
        for (Session existingSession : sessions) {
            LocalDateTime existingStart = existingSession.getDateTime();
            LocalDateTime existingEnd = existingStart.plusHours(1); //1 hour per session

            if (existingSession.getInstructor().equals(instructor) &&
                    !newStartTime.isBefore(existingStart) && newStartTime.isBefore(existingEnd)) {
                System.out.println("Failed registration: Instructor " + instructor.getName() +
                        " already has a session at this time");
                return false;
            }
        }
        return true;
    }

    public void registerClientToLesson(Client c, Session s)
            throws DuplicateClientException, ClientNotRegisteredException {
        //TODO. log.
        //FIXME לבדוק שכל השגיאות מודפסות

        try {
            if (!c.getRole().equals("Client")) { //not client}
                throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
            }
        } catch (ClientNotRegisteredException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (s.isParticipantRegistered(c)) { // already registered
                throw new DuplicateClientException("Error: The client is already registered for this lesson");
            }
        } catch (DuplicateClientException e) {
            System.out.println(e.getMessage());
        }
        if (!Gym.getInstance().getSession().contains(s)) {
            System.out.println("no such lesson");
            return;
        }
        if (s.isFull()) { //no place
            System.out.println("Failed registration: No available spots for session");
        }
        //TODO localDATE
        if (s.getDateTime().isBefore(LocalDateTime.now())) { //check time
            System.out.println("Failed registration: Session is not in the future");
            return;
        }
        //TODO if this work
        if (!(s.getForumType() == ForumType.All)) {
          //  if (s.getForumType()  == ForumType.Seniors)
            for (ForumType forumType : c.getForumC()) {
                if (forumType == s.getForumType()) {
                    System.out.println("Failed registration: Client's gender doesn't match the session's gender requirements (" + s.getForumType() + ")");
                }
            }
        }
//            if (!c.getForumC().contains(s.getSessionType())) {// forum
//                System.out.println("Failed registration: Client's gender doesn't match the session's gender requirements (" + s.getForumType() + ")");

        if (s.getSessionType().getPrice() > c.getBalance()) { //not enough money
            System.out.println("Failed registration: Client doesn't have enough balance");
        } else {
            s.setParticipants(c);
            c.setMyRegSession(s);
            System.out.println("Registered client: " + c.getName() + " to session: " + s.getSessionType().getName() + " on " + s.getDateTime() + " for price: " + s.getSessionType().getPrice());
            chargeClient(c, s.getSessionType().getPrice());
            addToGym(s.getSessionType().getPrice());
        }
        //TODO אם הלקוח רשום לעוד שיעור באותה שעה
    }

    public void chargeClient(Client client, double amount) {
        if (client.getBalanceAccount().getBalance() >= amount) {
            client.getBalanceAccount().withdraw(amount);
            Gym.getInstance().getBalanceGym().deposit(amount);

        } else {
            System.out.println("Insufficient funds in client's account");
        }
    }

    public void paySalaries() {
        Set<Instructor> instructors = Gym.getInstance().getInstructors();
        if (instructors.isEmpty()) {
            System.out.println("No salaries to pay");
        } else {
            for (Instructor instructor : instructors) {
                int numOfSessions = instructor.getNumberOfClasses();
                double salary = salaryManager.calculateSalary(instructor, numOfSessions);

                reduceGym(salary);
                instructor.getBalanceAccount().deposit(salary);
            }
            System.out.println("Salaries have been paid to all employees");
        }
    }

    public void addToGym(double price) {
        Gym.getInstance().gymAccount.deposit(price);
    }

    public void reduceGym(double price) {
        Gym.getInstance().gymAccount.withdraw(price);
    }


    public void notify(Session s4, String s) {
        //todo: observer
    }

    public void printActions() {
    }
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