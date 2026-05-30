import java.sql.*;
import java.util.*;

public class ClinicApp {
    public static void addPatient() throws Exception {
        Scanner sc = new Scanner(System.in);
        Connection con = DBConnection.getConnection();
        System.out.print("Enter patient name:");
        String name = sc.nextLine();
        System.out.print("Enter age:");
        int age = sc.nextInt();
        sc.nextLine();

        String sql = "INSERT INTO patients(name, age) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, name);
        ps.setInt(2, age);

        ps.executeUpdate();

        System.out.println("Patient Registered");
    }
    public static void addDoctor() throws Exception {
        Scanner sc = new Scanner(System.in);
        Connection con = DBConnection.getConnection();
        System.out.print("Enter doctor name:");
        String name = sc.nextLine();
        System.out.print("Enter specialty:");
        String spec = sc.nextLine();
        String sql = "INSERT INTO doctors(name, specialty) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, spec);
        ps.executeUpdate();
        System.out.println("Doctor Added");
    }
    public static void bookAppointment() throws Exception {
        Scanner sc = new Scanner(System.in);
        Connection con = DBConnection.getConnection();
        System.out.print("Patient ID: ");
        int pid = sc.nextInt();
        System.out.print("Doctor ID: ");
        int did = sc.nextInt();
        System.out.print("Fee: ");
        double fee = sc.nextDouble();
        String sql="INSERT INTO appointments(patient_id, doctor_id, fee) VALUES (?,?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setInt(1, pid);
        ps.setInt(2, did);
        ps.setDouble(3, fee);
        ps.executeUpdate();
        System.out.println("Appointment Booked");
    }

    public static void viewAppointments() throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM appointments";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        System.out.println("APPOINTMENTS");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt(1) + " Patient: " + rs.getInt(2) + " Doctor: " + rs.getInt(3) + " Fee: " + rs.getDouble(4));
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("CLINIC MENU");
            System.out.println("1.Add Patient");
            System.out.println("2.Add Doctor");
            System.out.println("3.Book Appointment");
            System.out.println("4.View Appointments");
            System.out.println("5.Exit");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    addDoctor();
                    break;
                case 3:
                    bookAppointment();
                    break;
                case 4:
                    viewAppointments();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
