package gym.customers;
import gym.management.Sessions.SessionType;

import java.time.LocalDate;
import java.util.List;

public class Instructor extends Person {
    private int salary;
    private List<SessionType> qualifiedSTypes;
    private int numberOfClasses;


    public Instructor(String name, double balance, Gender gender, String birthdaySrt, int salary, List<SessionType> qualifiedSTypes) {
        super(name, balance, gender, birthdaySrt);
        this.salary = salary;
        this.qualifiedSTypes = qualifiedSTypes;
        this.numberOfClasses = 0;

    }

    public Instructor(Person newI, int salary, List<SessionType> qualifiedSTypes) {
        super(newI);
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

}