package gym.customers;
import gym.management.Sessions.SessionType;

import java.util.List;

public class Instructor extends Person {
    private int salary;
    private List<SessionType> qualifiedSTypes;

    public Instructor(String name, double balance, Gender gender, String birthdayStr, int salary, List<SessionType> qualifiedSTypes) {
        super(name, balance, gender, birthdayStr);
        this.salary = salary;
        this.qualifiedSTypes = qualifiedSTypes;
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

    public void setQualifiedSTypes(List<SessionType> qualifiedSTypes) {
        this.qualifiedSTypes = qualifiedSTypes;
    }

    @Override
    public String getRole() {
        return "Instructor"; // Specific role for Instructor
    }
}