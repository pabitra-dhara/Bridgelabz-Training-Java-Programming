import java.util.ArrayList;
import java.util.HashMap;

public class Train {

    public static void main(String[] args) {

        ArrayList<String> bogies = new ArrayList<>();
        HashMap<String, String> details = new HashMap<>();
        bogies.add("B1");
        details.put("B1_type", "Passenger");
        details.put("B1_class", TrainClass.SLEEPER.toString());
        details.put("B1_capacity", "72");
        details.put("B1_shape", Shape.RECTANGULAR.toString());
        bogies.add("B2");
        details.put("B2_type", "Cargo");
        details.put("B2_cargo", "petroleum");
        details.put("B2_shape", Shape.CYLINDRICAL.toString());
        String shape = Shape.RECTANGULAR.toString();
        String cargo = "petroleum";
        if (cargo.equals("petroleum") && shape.equals("RECTANGULAR")) {
            System.out.println("Petroleum allowed only in Cylindrical bogie");
        } else {
            bogies.add("B3");
        }
        for (String bogie : bogies) {
            System.out.println("Type : "+details.get(bogie + "_type"));

            System.out.println("Class : "+details.get(bogie + "_class"));

            System.out.println("Capacity : "+details.get(bogie + "_capacity"));

            System.out.println("Cargo : "+details.get(bogie + "_cargo"));

            System.out.println("Shape : "+details.get(bogie + "_shape"));
        }
    }
}