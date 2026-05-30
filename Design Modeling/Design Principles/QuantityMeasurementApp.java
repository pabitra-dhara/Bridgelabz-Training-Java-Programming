import java.util.Scanner;

interface Measurable {
    double toMeter();
}
class Length implements Measurable {
    private double value;
    private String unit;
    public Length(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }
    public double toMeter() {
        if (unit.equalsIgnoreCase("cm"))
            return value/100;
        return value;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter value:");
        double value=sc.nextDouble();
        System.out.print("Enter unit m or cm:");
        String unit=sc.next();
        Length length=new Length(value, unit);
        System.out.println("Length in Meter="+length.toMeter());
    }
}