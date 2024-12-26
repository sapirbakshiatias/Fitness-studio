package gym.customers;

import gym.management.Sessions.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Person {

    private String name;
    private BalanceAccount balanceAccount;
    private Gender gender;
    private LocalDate birthday;
    private static int nextId = 1;
    private int id;
    private String role;
    private boolean isRegistered;


    public Person(String name, int initialBalance, Gender gender, String birthdayStr) {
        this.name = name;
        this.balanceAccount = new BalanceAccount(initialBalance);
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.birthday = LocalDate.parse(birthdayStr, formatter);
        this.id = nextId++;
        this.role = "Person";
        this.setRegistered(false);

    }
    public Person(Person other) {
        this.name = other.name;
        this.balanceAccount = new BalanceAccount(other.balanceAccount.getBalance());
        this.gender = other.gender;
        this.birthday = other.birthday;
        this.id = other.getId();
        this.role = "Person";
        this.isRegistered = false;
    }


    public String getName() {
        return this.name;   }

    public void setName(String name) {
        this.name = name;
    }
//TOFIX
    public BalanceAccount getBalancePerson_Account() {
        return balanceAccount;
    }
    public int getBalanceInt() {
        return balanceAccount.getBalance();
    }
    public void setBalancePerson(BalanceAccount balance) {
        this.balanceAccount = balance;
    }

    public Gender getGender() {
        return gender;
    }
//FIXME- להעביר ללקוח?
    public void depositToAccount(int amount) { //add
        balanceAccount.deposit(amount);
    }

//FIXME- להעביר ללקוח?
    public boolean withdrawFromAccount(int amount) { //להוריד
        return balanceAccount.withdraw(amount);
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

    public String getRole() {
        return this.role;
        // Default role, can be overridden by subclasses
    }
    public void setRole(String newRole) {
        this.role = newRole;
    }
    public int getId() {
        return this.id;
    }


    public boolean isRegistered() {
        return isRegistered;
    }
    public void setRegistered(boolean registered) {
        this.isRegistered = registered;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(balanceAccount, person.getBalancePerson_Account()) &&
                Objects.equals(birthday, person.birthday) &&
                Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, balanceAccount, gender);
    }
}