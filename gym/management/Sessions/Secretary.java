//package gym.management.Sessions;
//
//import gym.customers.Gender;
//import gym.customers.Person;
//
//public class Secretary extends Person {
//    private int salary;
//    private String name;
//
//    private String originalRole;
//
//    //constructor
////    public GymSecretary(String nweS, int balance, Gender gender, String birthdayStr, int salary) {
////        super(nweS, balance, gender, birthdayStr);
////        this.salary = salary;
////        this.originalRole = "Client";
////        setRole("Secretary");
////    }
//
//    public Secretary(Person person, int salary) {
//        super(person);
//        this.salary = salary;
//        this.originalRole = person.getRole();
//        isActive = false;
//        setRole("Secretary");
//    }
//    public void revertToPreviousRole() {
//        if (this.getOriginalRole().equals("Client")) {
//            this.setRole("Client");
//        } else {
//            this.setRole("Person");
//            //מאמן
//            //אולי חוזרת להיות פשוט מזכירה רגילה שלא אקטיבית?
//        }
//    }
//
//    //Get and Set
//    public int getSalarySec() {
//        return this.salary;
//    }
//
//    public void setSalarySec(int salary) {
//        this.salary = salary;
//    }
//
//
//    public String getOriginalRole() {
//        return this.originalRole;
//    }
//}
//}
//
