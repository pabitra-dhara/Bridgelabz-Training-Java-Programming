import java.util.Scanner;

public class BasicIODifferentDatatypes {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Integer:");
        int a = sc.nextInt();
        System.out.print("Enter Float:");
        float b = sc.nextFloat();
        System.out.print("Enter Double:");
        double c = sc.nextDouble();
        System.out.print("Enter Long:");
        long d = sc.nextLong();
        System.out.print("Enter a Charecter:");
        char e = sc.next().charAt(0);
        System.out.print("Enter Boolean:");
        boolean f = sc.nextBoolean();
        sc.nextLine();
        System.out.print("Enter String:");
        String g = sc.nextLine();

        System.out.println("Integer:" + a);
        System.out.println("Float:" + b);
        System.out.println("Double:" + c);
        System.out.println("Long:" + d);
        System.out.println("Charecter:" + e);
        System.out.println("Boolean:" + f);
        System.out.println("String:" + g);
    }
}
