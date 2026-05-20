import java.util.ArrayList;


public class TicketBookingQueue{
    ArrayList<Passanger> bookingQueue = new ArrayList<>();
    public void addBooking(Passanger p){
        bookingQueue.add(p);
        System.out.println(p.getpName()+"booking successfully");
        //processBooking();
    }
    public void processBooking(){

        if(bookingQueue.isEmpty()){
            System.out.println("No booking requests available.");
            return;
        }

        Passanger p=bookingQueue.remove(bookingQueue.size()-1);
        double fare=50;
        if(p.getwBalance()>=fare){
            p.setwBalance(p.getwBalance()-fare);
            System.out.println("Ticket Booked Successfully");
            System.out.println("Passenger Name:"+p.getpName());
            System.out.println("Source:"+p.getSource());
            System.out.println("Destination:"+p.getDestination());
            System.out.println("Remaining Wallet Balance:"+p.getwBalance());
        }else{
            System.out.println("Insufficient Wallet Balance for"+p.getpName());
        }
    }

    public void displayQueue(){
        if (bookingQueue.isEmpty()){
            System.out.println("Queue is Empty");
            return;
        }
        System.out.println("All Booking detail is:");
        for (Passanger p : bookingQueue) {
            System.out.println(p.getPassengerId()+"-"+p.getpName()+"-"+p.getSource()+"-to-"+p.getDestination());
        }
    }

    public void searchPassengerById(int id){
        for (Passanger p : bookingQueue) {
            if(p.getPassengerId()==id) {
                System.out.println(p.getPassengerId() + "-" + p.getpName() + "-" + p.getSource() + "-to-" + p.getDestination());
                return;
            }
        }
        System.out.println("Passenger not found");
    }
    public void removePassenger(int id){
        for(Passanger p:bookingQueue){
            if (p.getPassengerId()==id) {
                bookingQueue.remove(p);
                System.out.println("Passenger Removed Successfully");
                return;
            }
        }
        System.out.println("Passenger not found");
    }
    public void updatePassengerName(int id,String name){
        for(Passanger p:bookingQueue){
            if(p.getPassengerId()==id) {
                p.setpName(name);
                System.out.println("Passenger Updated Successfully");
                return;
            }
        }
        System.out.println("Passenger not found");
    }

    public void updatePassengerAge(int id,int age){
        for(Passanger p:bookingQueue){
            if(p.getPassengerId()==id) {
                p.setAge(age);
                System.out.println("Passenger Updated Successfully");
                return;
            }
        }
        System.out.println("Passenger not found");
    }
}
