package gym.management.Sessions;

public class SessionType {
    public static final SessionType MachinePilates = new SessionType("Machine Pilates", 80, 10);
    public static final SessionType ThaiBoxing = new SessionType("Thai Boxing", 100, 20);
    public static final SessionType Ninja = new SessionType("Ninja", 150, 5);
    public static final SessionType Pilates = new SessionType("Pilates", 60, 30);

    private String name;
    private int price;
    private int maxParticipants;

    private SessionType(String name, int price, int maxParticipants) {
        this.name = name;
        this.price = price;
        this.maxParticipants = maxParticipants;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getMaxParticipants() {
        return this.maxParticipants;
    }

    @Override
    public String toString() {
        return "SessionType{name='" + name + "', price=" + price + ", maxParticipants=" + maxParticipants + '}';
    }
}