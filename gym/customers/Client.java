package gym.customers;

import java.time.LocalDate;

public class Client extends Person{
    protected Client(String name, double balance, Gender gender, LocalDate birthday) {
        super(name, balance, gender, birthday);
    }
}
