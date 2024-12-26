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
import java.util.Set;

//FIXME להפריד בין יצירת מזיכרה, כמו CLIENT, לבין תפקיד המזכירה
public class Secretary extends Person {
    private int salary;
    private SalaryManager salaryManager = new SalaryManager();
    private String originalRole;
    //**
    Secretary activeSecretary = Gym.getActiveSecretary();
    private Notify notifier;
    private NotificationService notificationSender = new NotificationService("");


//TODO מזכירה אקטיבית בלבד יכולה לבצע פעולות

    public Secretary(Person person, int salary) {
        super(person);
        this.salary = salary;
        this.originalRole = person.getRole();
        setRole("Secretary");
        this.notifier = new Notify();
    }

    public void revertToPreviousRole() {
        if (this.getOriginalRole().equals("Client")) {
            this.setRole("Client");
        } else {
            this.setRole("Person");
            //מאמן
            //אולי חוזרת להיות פשוט מזכירה רגילה שלא אקטיבית?
        }
    }

    //Get and Set
    public int getSalarySec() {
        return this.salary;
    }

    public void setSalarySec(int salary) {
        this.salary = salary;
    }

    //Methods
    public Client registerClient(Person newC) throws DuplicateClientException, InvalidAgeException {
        // fixme לא הדפסה, צריך להכניס ללוגר של הPERSON והמכון
        if (newC.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }
        for (Client client : Gym.getInstance().getRegisteredClients()) { //already client
            if (client.getId() == newC.getId()) {
                throw new DuplicateClientException("Error: The client is already registered");
            }
        }
        Client newClient = new Client(newC);
        Gym.getInstance().addToRegisteredClients(newClient);
        newC.setRegistered(true);
        GymActions.addAction("Registered new client: " + newClient.getName());
        notificationSender.attachObserver(newClient);

        return newClient;
    }


    public void unregisterClient(Client c) throws ClientNotRegisteredException {
        boolean SameId = false;
        for (Client client : Gym.getInstance().getRegisteredClients()) { //already client
            if (client.getId() == c.getId()) {
                SameId = true;
                break;
            }
        }
        if (!SameId) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        } else {
            Gym.getInstance().removeFromRegisteredClients(c);
            c.clearClientData();
            notificationSender.detachObserver(c);
            GymActions.addAction("Unregistered client: " + c.getName());
        }
    }

    public Instructor hireInstructor(Person p, int salary, ArrayList<SessionType> qualifiedSTypes) {
        //TODO האם הוא מוגדר קודם כלקוח?
        //TODO לבדוק אם כבר קיים
        //no qualifiedSTypes
        if (qualifiedSTypes.isEmpty()) {
            System.out.println("Cannot hire instructor without valid session types");
            return null;
        }
        // already instructor
        for (Instructor instructor : Gym.getInstance().getInstructors()) {
            if (instructor.getId() == p.getId()) {
                System.out.println("Instructor already exists: " + p.getName());
                return null;
            }
        }
        Instructor newInstructor = new Instructor(p, salary, qualifiedSTypes);
        Gym.getInstance().addInstructors(newInstructor);
        //FIXME logger + dateToString
        GymActions.addAction("Hired new instructor: " + p.getName() + " with salary per hour: " + salary);
        return newInstructor;
    }
    //TODO function fired Instructor

    public Session addSession(SessionType s, String dateTimeStr, ForumType f, Instructor i) throws InstructorNotQualifiedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime sessionDateTime = LocalDateTime.parse(dateTimeStr, formatter);

        //not getQualifiedS to this type
        if (!i.getQualifiedSTypes().contains(s)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        //already teaching a lesson
        if (!canAddSession(sessionDateTime, i)) {
            System.out.println("Error: Cannot schedule session at " + sessionDateTime +
                    ". Instructor is already teaching at this time.");
            return null;
        }
        Session newSession = SessionFactory.createSession(s, dateTimeStr, f, i);
        i.incrementClassCount();
        Gym.getInstance().setSessions(newSession);
        i.addSessionsOfInstructor(newSession);
        GymActions.addAction("Created new session: " + newSession.getSessionType().getName() + " on " + sessionDateTime + " with instructor: " + i.getName());

        return newSession;
    }

    public boolean canAddSession(LocalDateTime newStartTime, Instructor instructor) {
        //FIXME הרם לעבור על הרשימת שיעורים של המאמן עצמה.
        for (Session existingSession : Gym.getInstance().getSession()) {
            LocalDateTime existingStart = existingSession.getDateTime();
            LocalDateTime existingEnd = existingStart.plusHours(1); //1 hour per session

            if (existingSession.getInstructor().equals(instructor) &&
                    !newStartTime.isBefore(existingStart) && newStartTime.isBefore(existingEnd)) {
                GymActions.addAction("Failed registration: Instructor " + instructor.getName() +
                        " already has a session at this time");
                return false;
            }
        }
        return true;
    }

    //FIXME לבדוק שכל השגיאות מודפסות
    //FIXME לבדוק האם מאמן מאמן כבר שיעור באותו זמן ורוצה להירשם
    public void registerClientToLesson(Client client, Session session)
            throws DuplicateClientException, ClientNotRegisteredException {
        // Check if the client is registered
        if (!client.getRole().equals("Client")) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }
        // Check if the session exists
        if (!Gym.getInstance().getSession().contains(session)) {
            System.out.println("This Session does not exist");
            return;
        }
        // Check if the session is full
        if (session.isFull()) {
            GymActions.addAction("Failed registration: No available spots for session");
            return;
        }
        // Check if the session is in the past
        if (session.getDateTime().isBefore(LocalDateTime.now())) {
            GymActions.addAction("Failed registration: Session is not in the future");
            return;
        }
        // Check for matching forum type
        if (!(session.getForumType() == ForumType.All)) {
            boolean isForumMatch = false;
            for (ForumType forumType : client.getForumC()) {
                if (forumType.equals(session.getForumType())) {
                    isForumMatch = true;
                    break;
                }
            }
            if (!isForumMatch) {
                GymActions.addAction("Failed registration: Client's gender doesn't match the session's gender requirements (" + session.getForumType() + ")");
                return;
            }
        }
        // Check if the client has enough funds
        if (session.getSessionType().getPrice() > client.getBalanceInt()) {
            GymActions.addAction("Failed registration: Client doesn't have enough balance");
            return;
        }
        //fixme throws a problem

//            if (session.getParticipants().contains(client))
//                throw new DuplicateClientException("Error: The client is already registered for this lesson");
        // Check for duplicate sessions at the same time
        for (Session sessions : client.getMyRegSession()) {
            if (sessions.getDateTime() == session.getDateTime()) {
                System.out.println("Error: Client is already registered for another session at the same time");
                return;
            }
        }
        // All checks pass, register the client
        client.setMyRegSession(session);
        session.addParticipant(client);
        GymActions.addAction("Registered client: " + client.getName() + " to session: " + session.getSessionType().getName() + " on " + session.getDateTime() + " for price: " + session.getSessionType().getPrice());
        chargeClient(client, session.getSessionType().getPrice());
        Gym.getInstance().getBalanceGym().deposit(session.getSessionType().getPrice());
    }


    public void chargeClient(Client client, int amount) {
        if (client.getBalancePerson_Account().getBalance() >= amount) {
            client.getBalancePerson_Account().withdraw(amount);
            Gym.getInstance().getBalanceGym().deposit(amount);
        } else {
            GymActions.addAction("Failed registration: Client doesn't have enough balance");
        }
    }

    public void paySalaries() {
        Set<Instructor> instructors = Gym.getInstance().getInstructors();
        if (instructors.isEmpty()) {
            System.out.println("No salaries to pay");
        } else {
            for (Instructor instructor : instructors) {
                int numOfSessions = instructor.getNumberOfClasses();
                int salary = salaryManager.calculateSalary(instructor, numOfSessions);
                Gym.getInstance().getBalanceGym().withdraw(salary);
                instructor.getBalancePerson_Account().deposit(salary);
                //FIXME? לאפס את הרשימה של המאמן כדי לא לשלם לו פעמיים
            }
        }
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

    public String getOriginalRole() {
        return this.originalRole;
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
        return "ID: " + super.getId() + " | Name: " + super.getName() + " | Gender: " + super.getGender() +
                " | Birthday: " + super.getBirthday() + " | Age: " + getAge() + " | Balance: " + getBalanceInt() + " | Role: " + getRole() +
                " | Salary per Month: " + getSalarySec();
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
