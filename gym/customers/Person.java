package gym.customers;

import gym.Exception.InvalidAgeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class Person {
    private String name;
    private double balance;
    private Gender gender;
    private LocalDate birthday;

    public Person(String name, double balance, Gender gender, String birthdayStr) {
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.birthday = LocalDate.parse(birthdayStr, formatter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge() throws InvalidAgeException {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthday, currentDate).getYears();
        if (age < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register ");
        }
        return age;
    }

    public String getRole() {
        return "Person"; // Default role, can be overridden by subclasses
    }
}