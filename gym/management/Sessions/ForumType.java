package gym.management.Sessions;

import java.util.Objects;

public class ForumType {
    public static final ForumType All = new ForumType();
    public static final ForumType Female = new ForumType();
    public static final ForumType Seniors = new ForumType();
    public static final ForumType Male = new ForumType();
    private ForumType() {}
    private String name;

    private ForumType(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // אותו אובייקט בדיוק
        if (o == null || getClass() != o.getClass()) return false; // סוג שונה או null
        ForumType forumType = (ForumType) o;
        return Objects.equals(name, forumType.name); // השוואת שם הפורום
    }

    // מחושב כך שאובייקטים עם אותו שם יקבלו אותו hashCode
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // נציג את שם הפורום במקום הכתובת בזיכרון
    @Override
    public String toString() {
        return name;
    }
}
