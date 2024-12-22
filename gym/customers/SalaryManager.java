package gym.customers;

    public class SalaryManager {
        public int calculateSalary(Instructor instructor, int numOfSessions) {
            return (instructor.getSalary() * numOfSessions) ;
        }
    }



