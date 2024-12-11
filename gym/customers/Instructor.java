package gym.customers;
import gym.management.Sessions.SessionType;

import java.util.List;

public class Instructor extends Person {
    private int salary;
    private List<SessionType> sessionTypes;

    public Instructor(String name, double balance, Gender gender, String birthdayStr, int salary, List<SessionType> sessionTypes) {
        super(name, balance, gender, birthdayStr);
        this.salary = salary;
        this.sessionTypes = sessionTypes;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<SessionType> getSessionTypes() {
        return sessionTypes;
    }

    public void setSessionTypes(List<SessionType> sessionTypes) {
        this.sessionTypes = sessionTypes;
    }

    @Override
    public String getRole() {
        return "Instructor"; // Specific role for Instructor
    }
}
