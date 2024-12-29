package gym.customers;



import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
//FIXME- האם צריך ROLE?
public class Person {
    private static int nextId = 1111;
    private final int id;
    private String name;
    private BalanceAccount balanceAccount;
    private Gender gender;
    private LocalDate birthDate;
    private String role;

    public Person(String name, int initialBalance, Gender gender, String birthDate) {
        this.name = name;
        this.balanceAccount = new BalanceAccount(initialBalance);
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.birthDate = LocalDate.parse(birthDate, formatter);
        this.id = nextId++;
        //this.role = "Person";
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BalanceAccount getBalanceAccount() {
        return balanceAccount;
    }

    public Gender getGender() {
        return gender;
    }


    public String getBirthDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return birthDate.format(formatter);
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    public String getRole() {
        return this.role;
    }
    public void setRole(String newRole) {
        this.role = newRole;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(balanceAccount, person.getBalanceAccount()) &&
                Objects.equals( birthDate.toString(), person.birthDate.toString()) &&
                Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, balanceAccount, gender);
    }

}
