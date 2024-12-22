package gym.management;

import gym.management.Sessions.*;

import gym.Exception.*;
import gym.customers.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

//FIXME להפריד בין יצירת מזיכרה, כמו CLIENT, לבין תפקיד המזכירה
public class GymSecretary extends Person {
    private int salary;
    private SalaryManager salaryManager = new SalaryManager();
    private static final Logger logger = Logger.getLogger(Gym.class.getName());
    private String originalRole;

//TODO מזכירה אקטיבית בלבד יכולה לבצע פעולות

    //constructor
//    public GymSecretary(String nweS, int balance, Gender gender, String birthdayStr, int salary) {
//        super(nweS, balance, gender, birthdayStr);
//        this.salary = salary;
//        this.originalRole = "Client";
//        setRole("Secretary");
//    }

    public GymSecretary(Person person, int salary) {
        super(person);
        this.salary = salary;
        this.originalRole = person.getRole();
        setRole("Secretary");
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
        System.out.println("Registered new client: " + newClient.getName());
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

            System.out.println("Unregistered client: " + c.getName());
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
        System.out.println("Hired new instructor: " + p.getName() + " with salary per hour: " + salary);
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
        System.out.println("Created new session: " + newSession.getSessionType().getName() + " on " + sessionDateTime + " with instructor: " + i.getName());

        return newSession;
    }


    public boolean canAddSession(LocalDateTime newStartTime, Instructor instructor) {
        //FIXME הרם לעבור על הרשימת שיעורים של המאמן עצמה.
        for (Session existingSession : Gym.getInstance().getSession()) {
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
        GymSecretary activeSecretary = Gym.getActiveSecretary();
        if (activeSecretary != null) {
            //TODO. log.
            //FIXME לבדוק שכל השגיאות מודפסות
            //FIXME לבדוק האם מאמן מאמן כבר שיעור באותו זמן ורוצה להירשם
            //not client
            try {
                //   if (!c.isRegistered()) {
                //     if (!registeredClients.contains(c)) {
                if (!c.getRole().equals("Client")) {
                    throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
                }
            } catch (ClientNotRegisteredException e) {
                System.out.println(e.getMessage());
                return;
            }
            //exist session
            if (!Gym.getInstance().getSession().contains(s)) {
                System.out.println("no such lesson");
                return;
            }
            // already registered
            try {
                if (s.isParticipantRegistered(c)) {
                    throw new DuplicateClientException("Error: The client is already registered for this lesson");
                }
            } catch (DuplicateClientException e) {
                System.out.println(e.getMessage());
                return;
            }
            //no place
            if (s.isFull()) {
                System.out.println("Failed registration: No available spots for session");
                return;
            }
            if (s.getDateTime().isBefore(LocalDateTime.now())) { //check time
                System.out.println("Failed registration: Session is not in the future");
                return;
            }
            //TODO not woerking
            //same forumType
//        if (!(s.getForumType() == ForumType.All)) {
//            boolean equalForum = false;
//            for (ForumType forumType : c.getForumC()) {
//                if (forumType == s.getForumType()) {
//                    System.out.println("good");
//                    equalForum = true;
//                }
//            }
//            if (!equalForum) {
//                System.out.println("Failed registration: Client's gender doesn't match the session's gender requirements (" + s.getForumType() + ")");
//                return;
//            }
//        }
            // enough money
            if (s.getSessionType().getPrice() > c.getBalanceInt()) {
                System.out.println("Failed registration: Client doesn't have enough balance");

            //everything is good
            } else {
                s.setParticipants(c);
                c.setMyRegSession(s);
                System.out.println("Registered client: " + c.getName() + " to session: " + s.getSessionType().getName() + " on " + s.getDateTime() + " for price: " + s.getSessionType().getPrice());
                chargeClient(c, s.getSessionType().getPrice());
                Gym.getInstance().getBalanceGym().deposit(s.getSessionType().getPrice());
            }
            //TODO אם הלקוח רשום לעוד שיעור באותה שעה
            //not activate secretary
        } else {
            throw new IllegalStateException("Former secretaries are not permitted to perform actions.");
        }
    }

    public void chargeClient(Client client, int amount) {
        if (client.getBalancePerson_Account().getBalance() >= amount) {
            client.getBalancePerson_Account().withdraw(amount);
            Gym.getInstance().getBalanceGym().deposit(amount);
        } else {
            //FIXME הדפסה נכונה?
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
                int salary = salaryManager.calculateSalary(instructor, numOfSessions);

                Gym.getInstance().getBalanceGym().withdraw(salary);
                instructor.getBalancePerson_Account().deposit(salary);
                //FIXME? לאפס את הרשימה של המאמן כדי לא לשלם לו פעמיים
            }
            System.out.println("Salaries have been paid to all employees");
        }
    }

    public void notify(Session s4, String s) {
        //todo: observer
    }

    public void printActions() {
        //TODO
    }

    public String getOriginalRole() {
        return this.originalRole;
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