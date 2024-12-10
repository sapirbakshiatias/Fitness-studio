package gym.customers;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String name;
    private double balance;
    private Gender gender;
    private LocalDate birthday;


    protected Person(String name, double balance, Gender gender, LocalDate birthday) {
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        this.birthday = birthday;
    }

    protected String getName() {
        return name;
    }
    protected void setName(String name) {
        this.name = name;
    }

    protected double getBalance() {
        return balance;
    }
    protected void setBalance(double balance){
        this.balance = balance;
    }

    protected Gender getGender() {
        return gender;
    }
    protected void setGender(Gender gender){
        this.gender = gender;
    }
    protected LocalDate getBirthdate() {
        return birthday;
    }
    protected void setBirthday(LocalDate birthday){
        this.birthday = birthday;
    }
    protected int age(LocalDate birthday) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthday, currentDate);
        return period.getYears();
    }

}


