import java.util.Scanner;

public class GuessingApp {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int number = (int) (Math.random() * 10) + 1;
        int guess;
        while (true) {
            System.out.print("Guess a number between 1 and 10:");
            guess = sc.nextInt();
            if (guess == number) {
                System.out.println("Correct Guess!");
                break;
            } else if (guess > number) {
                System.out.println("Too High!");
            } else {
                System.out.println("Too Low!");
            }
        }
    }
}
