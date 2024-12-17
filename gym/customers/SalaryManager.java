package gym.customers;

    public class SalaryManager {
        public double calculateSalary(Instructor instructor, int numOfSessions) {
            return (instructor.getSalary() * numOfSessions) ;
        }
    }



