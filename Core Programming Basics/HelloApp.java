import java.util.Scanner;

public class HelloApp {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello, I am Pabitra Dhara");
        System.out.print("If you would like to know more about me enter 1 otherwise enter 2:");
        int a = sc.nextInt();
        if (a == 1) {
            System.out.print("I am form West Bengal");
        } else {
            System.out.print("Thank You");
        }
    }
}