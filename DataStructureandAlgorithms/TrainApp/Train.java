package TrainApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

enum TrainClass {
    SLEEPER, AC, FIRST
}
enum Shape {
    RECTANGULAR, CYLINDRICAL
}

public class Train {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> bogies = new ArrayList<String>();
        HashMap<String,String> details = new HashMap<String,String>();
        System.out.print("Enter number of bogies: ");
        int n = sc.nextInt();
        sc.nextLine();
        for(int i=0;i<n;i++){
            System.out.print("Enter bogie id: ");
            String id = sc.nextLine();
            System.out.print("Enter type (Passenger/Cargo): ");
            String type = sc.nextLine();
            System.out.print("Enter shape (RECTANGULAR/CYLINDRICAL): ");
            String shape = sc.nextLine();
            if(type.equals("Passenger")){
                System.out.print("Enter class (SLEEPER/AC/FIRST): ");
                String cls = sc.nextLine();
                System.out.print("Enter capacity: ");
                String cap = sc.nextLine();
                bogies.add(id);
                details.put(id+"_type",type);
                details.put(id+"_class",cls);
                details.put(id+"_capacity",cap);
                details.put(id+"_shape",shape);
            }else{
                System.out.print("Enter cargo: ");
                String cargo = sc.nextLine();
                if(cargo.equals("petroleum") && shape.equals("RECTANGULAR")){
                    System.out.println("Petroleum allowed only in CYLINDRICAL bogie");
                }else{
                    bogies.add(id);
                    details.put(id+"_type",type);
                    details.put(id+"_cargo",cargo);
                    details.put(id+"_shape",shape);
                }
            }
        }

        System.out.println("Train Details");

        for(int i=0;i<bogies.size();i++){
            String id = bogies.get(i);
            System.out.println("Bogie: "+id);
            System.out.println("Type: "+details.get(id+"_type"));
            System.out.println("Class: "+details.get(id+"_class"));
            System.out.println("Capacity: "+details.get(id+"_capacity"));
            System.out.println("Cargo: "+details.get(id+"_cargo"));
            System.out.println("Shape: "+details.get(id+"_shape"));
            System.out.println();
        }
    }
}