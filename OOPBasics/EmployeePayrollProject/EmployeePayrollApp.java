import java.util.Scanner;

public class EmployeePayrollApp {

    static class Employee {
        private String username;
        private String password;
        private double salary;
        private double tax;
        private double deduction;
        Employee(String username,String password,double salary, double tax, double deduction) {
            this.username = username;
            this.password = password;
            this.salary = salary;
            this.tax = tax;
            this.deduction = deduction;
        }
        public boolean authenticate(String user, String pass) {
            if(user==null || pass==null){
                return false;
            }
            if(username.equals(user) && password.equals(pass)){
                return true;
            }
            return false;
        }
        public double calculateNetPay() {
            return salary-tax-deduction;
        }
        public void viewPayDetails() {
            System.out.println("Salary: " + salary);
            System.out.println("Tax: " + tax);
            System.out.println("Deduction: " + deduction);
            System.out.println("Net Pay: " + calculateNetPay());
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Employee emp1=new Employee("rahul","1234",50000,5000,2000);
        System.out.println("Employee Login");
        System.out.print("Enter Username: ");
        String user=sc.nextLine();
        System.out.print("Enter Password: ");
        String pass=sc.nextLine();
        if (emp1.authenticate(user, pass)) {
            System.out.println("Login Successful");
            emp1.viewPayDetails();
        } else {
            System.out.println("Login Failed");
        }
    }
}
