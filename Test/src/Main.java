import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        TicketBookingQueue queue=new TicketBookingQueue();
        while(true){
            System.out.println("\n1.Booking");
            System.out.println("2.Search");
            System.out.println("3.Remove");
            System.out.println("4.Display All");
            System.out.println("5 Update Name");
            System.out.println("6 Update Age");
            System.out.println("7.Exit");
            System.out.print("Enter Choice:");
            int key=sc.nextInt();

            if(key==1){
                Passanger p1=new Passanger();
                System.out.print("Enter Passenger ID:");
                int id=sc.nextInt();
                System.out.print("Enter Passenger Name:");
                String name=sc.next();
                System.out.print("Enter Passenger Age:");
                int age=sc.nextInt();

                System.out.print("Enter Wallet Balance:");
                double wBalance=sc.nextDouble();

                System.out.print("Enter Source Station:");
                String sStation=sc.next();

                System.out.print("Enter Destination Station:");
                String dStation=sc.next();

                p1.setPassengerId(id);
                p1.setpName(name);
                p1.setAge(age);
                p1.setwBalance(wBalance);
                p1.setSource(sStation);
                p1.setDestination(dStation);

                queue.addBooking(p1);

            }else if(key==2){
                System.out.print("Enter Passenger ID:");
                int id=sc.nextInt();
                queue.searchPassengerById(id);
            }else if(key==3){
                System.out.print("Enter Passenger ID to Remove:");
                int id = sc.nextInt();
                queue.removePassenger(id);
            }else if(key==4){
                queue.displayQueue();
            }else if(key==5){
                System.out.print("Enter id and Updated name:");
                int id=sc.nextInt();
                String upName=sc.next();
                queue.updatePassengerName(id,upName);
            }else if(key==6){
                System.out.println("Enter id and Updated age:");
                int id=sc.nextInt();
                int upAge=sc.nextInt();
                queue.updatePassengerAge(id,upAge);
            }else if(key==7){
                System.out.println("Program Ended");
                break;
            }else{
                System.out.println("Invalid Choice");
            }
        }
    }
}