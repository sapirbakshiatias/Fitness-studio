package gym.customers;

import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {
    private int salary;
    private List<SessionType> qualifiedSTypes;
    private int numberOfClasses;
    private List<Session> sessionsOfInstructor;

//FIXME- האם המאמן הוא גם לקוח?
    //אם כן צריך לוודא שלא מתאמן ומאמן באותו זמן


//    public Instructor(String name, int balance, Gender gender, String birthdaySrt, int salary, List<SessionType> qualifiedSTypes) {
//        super(name, balance, gender, birthdaySrt);
//        this.salary = salary;
//        this.qualifiedSTypes = qualifiedSTypes;
//        this.numberOfClasses = 0;
//
//    }

    public Instructor(Person newI, int salary, List<SessionType> qualifiedSTypes) {
        super(newI);
        this.salary = salary;
        this.qualifiedSTypes = qualifiedSTypes;
        this.sessionsOfInstructor = new ArrayList<>();
        this.numberOfClasses = 0;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<SessionType> getQualifiedSTypes() {
        return qualifiedSTypes;
    }

    public List<Session> getSessionsOfInstructor() {
        return sessionsOfInstructor;
    }

    public void addSessionsOfInstructor(Session session) {
        sessionsOfInstructor.add(session);
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

    @Override
    public String getRole() {
        return "Instructor"; // Specific role for Instructor
    }

    public int getSalaryPerHour() {
            if (salary == 0) {
                return 0;
            }
            if(getNumberOfClasses() == 0)
                return 0;
        return salary / getNumberOfClasses();
    }

    @Override
    public String toString() {
        // Create a string to hold the list of qualified session types
        StringBuilder qualifiedTypesName = new StringBuilder();
        for (SessionType type : qualifiedSTypes) {
            qualifiedTypesName.append(type.getName()).append(", ");} // Adding each session type name


            return "ID: " + super.getId() + " | Name: " + super.getName() + " | Gender: " + super.getGender() +
                    " | Birthday: " + super.getBirthday() + " | Age: " + super.getAge() + " | Balance: " + getBalanceInt() +
                    " | Role: " + super.getRole() + " | Salary per Hour: " + getSalaryPerHour() +
                    " | Certified Classes: " + qualifiedTypesName;
        }
    }
