package gym.customers;

import gym.management.Observer;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.List;

public class Instructor implements Observer {
    private Person person;
    private String Role;

    private int hourlySalary;
    private List<SessionType> qualifiedSTypes;
    private int numberOfClasses;
    private List<Session> sessionsOfInstructor;
    private List<String> notifications;


//FIXME- האם המאמן הוא גם לקוח?
    //אם כן צריך לוודא שלא מתאמן ומאמן באותו זמן


//    public Instructor(String name, int balance, Gender gender, String birthdaySrt, int salary, List<SessionType> qualifiedSTypes) {
//        super(name, balance, gender, birthdaySrt);
//        this.salary = salary;
//        this.qualifiedSTypes = qualifiedSTypes;
//        this.numberOfClasses = 0;
//
//    }

    public Instructor(Person person, int hourlySalary, List<SessionType> qualifiedSTypes) {
        this.person = person;
        this.hourlySalary = hourlySalary;
        this.qualifiedSTypes = qualifiedSTypes;
        this.sessionsOfInstructor = new ArrayList<>();
        this.numberOfClasses = 0;
        this.notifications = new ArrayList<>();
        this.Role = "Instructor";
    }

    public Person getPerson() {
        return person;
    }

    public BalanceAccount getBalanceAccount() {
        return person.getBalanceAccount();
    }


    public int getHourlySalary() {
        return hourlySalary;
    }

    public void setHourlySalary(int hourlySalary) {
        this.hourlySalary = hourlySalary;
    }

    public List<SessionType> getQualifiedSTypes() {
        return qualifiedSTypes;
    }

    public List<Session> getSessionsOfInstructor() {
        return sessionsOfInstructor;
    }

    public void addSessionsOfInstructor(Session session) {
        sessionsOfInstructor.add(session);
        numberOfClasses++;
    }

    public void removeSessionsOfInstructor(Session session) {
        sessionsOfInstructor.remove(session);
    }


    public void setQualifiedSTypes(List<SessionType> qualifiedSTypes) {
        this.qualifiedSTypes = qualifiedSTypes;
    }

    public void incrementClassCount() {
        numberOfClasses++;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public String getName() {
        return person.getName();
    }

    @Override
    public String toString() {
        // Create a string to hold the list of qualified session types
        StringBuilder qualifiedTypesName = new StringBuilder();
        for (SessionType type : qualifiedSTypes) {
            qualifiedTypesName.append(type.getName()).append(", ");
        } // Adding each session type name

        return "ID: " + person.getId() + " | Name: " + person.getName() + " | Gender: " + person.getGender() +
                " | Birthday: " + person.getBirthDate() + " | Age: " + person.getAge() + " | Balance: " + person.getBalanceAccount().getBalance() +
                " | Role: " + Role + " | Salary per Hour: " + getHourlySalary() +
                " | Certified Classes: " + qualifiedTypesName;
    }

    public Session[] getSessions() {
        return sessionsOfInstructor.toArray(new Session[0]);

    }

    public void addNotificationToHistory(String message) {
        notifications.add(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public void update(String message) {
        notifications.add(message);
    }

}
