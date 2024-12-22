package gym.management.Sessions;

import java.util.Objects;

public class ForumType {
    //FIXME? ENUM
    public static final ForumType All = new ForumType("All");
    public static final ForumType Female = new ForumType("Female");
    public static final ForumType Seniors = new ForumType("Seniors");
    public static final ForumType Male = new ForumType("Male");
    private final String name;

    private ForumType(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // אותו אובייקט בדיוק
        if (o == null || getClass() != o.getClass()) return false; // סוג שונה או null
        ForumType forumType = (ForumType) o;
        return Objects.equals(name, forumType.name); // השוואת שם הפורום
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
