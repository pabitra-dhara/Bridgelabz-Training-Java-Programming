package DataStructureandAlgorithms.BookMyStayApp;
import java.util.*;

class Booking {
    String name;
    String roomType;

    Booking(String name, String roomType) {
        this.name = name;
        this.roomType = roomType;
    }
}

public class BookMyStay {
    static HashMap<String, Integer> rooms = new HashMap<>();
    static HashMap<String, Integer> prices = new HashMap<>();
    static PriorityQueue<String> bookingQueue = new PriorityQueue<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        rooms.put("Single", 3);
        rooms.put("Double", 2);
        prices.put("Single", 2000);
        prices.put("Double", 3500);

        System.out.println();
        while (true) {

            System.out.println("1. Show Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                showRooms();
            }else if (choice == 2) {

                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Room Type Single or Double: ");
                String type = sc.nextLine();

                System.out.print("Add Breakfast true or false: ");
                boolean breakfast = sc.nextBoolean();

                bookRoom(name, type, breakfast);
            }else {
                break;
            }
        }
    }

    public static synchronized void bookRoom(String name,String type,boolean breakfast) {

        if (!rooms.containsKey(type)) {
            System.out.println("Room type not available");
            return;
        }

        int available = rooms.get(type);

        if (available <= 0) {
            System.out.println("No rooms available");
            return;
        }
        rooms.put(type, available - 1);
        int total = prices.get(type);
        if (breakfast==true) total += 500;
        bookingQueue.add(name + " booked " + type);
        System.out.println("Booking Successful!");
        System.out.println("Total Price:" + total);
    }

    public static void showRooms() {
        System.out.println("Available Rooms:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " " + rooms.get(type) + " rooms left");
        }
    }
}