package gym.customers;

import gym.Exception.InvalidAgeException;
import gym.management.Sessions.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private BalanceAccount balanceAccount;
    private Gender gender;
    private LocalDate birthday;
    private static int nextId = 1;
    private int id;
    private List<Session> myRegisteredSession;

    public Person(String name,  double initialBalance, Gender gender, String birthdayStr) {
        this.name = name;
        this.balanceAccount = new BalanceAccount(initialBalance);
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.birthday = LocalDate.parse(birthdayStr, formatter);
        this.id = nextId++;
        this.myRegisteredSession = new ArrayList<>();
    }
    public Person(Person other) {
        this.name = other.name;
        this.balanceAccount = new BalanceAccount(other.balanceAccount.getBalance());
        this.gender = other.gender;
        this.birthday = other.birthday;
        this.id = other.getId();
        this.myRegisteredSession = new ArrayList<>();

    }

        public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BalanceAccount getBalancePerson() {
        return balanceAccount;
    }

    public void setBalancePerson(BalanceAccount balance) {
        this.balanceAccount = balance;
    }
    public BalanceAccount getBalanceAccount() {
        return balanceAccount;}

    public Gender getGender() {
        return gender;
    }

    public void depositToAccount(double amount) {
        balanceAccount.deposit(amount);
    }

    public boolean withdrawFromAccount(double amount) {
        return balanceAccount.withdraw(amount);
    }

    public double getBalance() {
        return balanceAccount.getBalance();
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public String getBirthdayStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return birthday.format(formatter);}

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge()  {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears();
    }
//    public int getAge() throws InvalidAgeException {
//        LocalDate currentDate = LocalDate.now();
//        int age = Period.between(birthday, currentDate).getYears();
//        if (age < 18) {
//            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
//        }
//        return age;
//    }
    public String getRole() {
        return "Person"; // Default role, can be overridden by subclasses
    }
    public int getId() {
        return this.id;
    }

    public List<Session> getMyRegSession() {
        return this.myRegisteredSession;
    }
    public void setMyRegSession(Session s) {
         this.myRegisteredSession.add(s);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(balanceAccount, person.getBalancePerson()) &&
                Objects.equals(birthday, person.birthday) &&
                Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, balanceAccount, gender);
    }
}