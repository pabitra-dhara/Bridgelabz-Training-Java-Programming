import java.util.*;
import java.util.regex.*;
import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BookingValidation {
    boolean required() default true;
}

class NoRoomAvailableException extends Exception {
    public NoRoomAvailableException(String message) {
        super(message);
    }
}

class Room<T> {
    private T roomNumber;
    private String roomType;

    public Room(T roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return roomType + " Room - " + roomNumber;
    }
}

class Guest {
    private String name;
    private String email;
    public Guest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}

class BookingService {
    private List<Guest> bookings = new ArrayList<>();
    private Map<String, Integer> inventory = new HashMap<>();
    public BookingService() {
        inventory.put("DELUXE", 2);
    }
    @BookingValidation
    public void bookRoom(Guest guest) throws NoRoomAvailableException {
        int available = inventory.get("DELUXE");
        if (available == 0) {
            throw new NoRoomAvailableException("No Room Available");
        }
        bookings.add(guest);
        inventory.put("DELUXE", available - 1);
        System.out.println("Room Booked for " + guest.getName());
    }

    public void displayBookings() {
        bookings.sort(Comparator.comparing(Guest::getName));
        for (Guest guest : bookings) {
            System.out.println(guest);
        }
    }
}

public class BookMyStayApp {
    public static boolean validateEmail(String email) {
        Pattern pattern=Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        return pattern.matcher(email).matches();
    }

    public static void checkAnnotations() {
        for (Method method : BookingService.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BookingValidation.class)) {
                System.out.println(method.getName()+" -> Validation Enabled");
            }
        }
    }

    public static void main(String[] args) {
        BookingService service = new BookingService();
        Guest guest1=new Guest("Rahul", "rahul@gmail.com");
        try {
            if (!validateEmail(guest1.getEmail())) {
                throw new RuntimeException("Invalid Email");
            }
            service.bookRoom(guest1);
            List<String> names=Arrays.asList("Zara", "Amit", "Rohan");
            Collections.sort(names);
            System.out.println("\nSorted Names:");
            for (String name : names) {
                System.out.println(name);
            }
            System.out.println("\nBookings:");
            service.displayBookings();

            System.out.println("\nAnnotation Check:");
            checkAnnotations();

        } catch (NoRoomAvailableException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("\nBooking Process Completed");
        }
    }
}