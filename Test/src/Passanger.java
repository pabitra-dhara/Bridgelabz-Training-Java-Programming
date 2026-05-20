import java.util.ArrayList;
public class Passanger {
    int passengerId;
    String pName;
    int age;
    double wBalance;
    String source;
    String destination;


    public void setPassengerId(int passengerId) {
        try{
            if(passengerId<=0){
                System.out.println("Passenger Id can't be less than 0");
            }else{
                this.passengerId = passengerId;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setpName(String pName) {
        try {
            if (pName.isEmpty()) {
                System.out.println("Name cannot be empty");
            } else {
                this.pName = pName;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setAge(int age) {
        try{
            if(age<=0){
                System.out.println("Age cannot be negative");
            }else{
                this.age = age;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setwBalance(double wBalance) {
        try{
            if(wBalance<=0){
                System.out.println("Negative Wallet Balance is not Allowed!");
            }else{
                this.wBalance = wBalance;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setSource(String source) {
        try{
            if(source.isEmpty()){
                System.out.println("Source cannot be empty");
            }else{
                this.source = source;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setDestination(String destination) {
        try{
            if(destination.isEmpty()){
                System.out.println("Destination cannot be empty");
            }else{
                this.destination = destination;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getPassengerId() {
        return passengerId;
    }

    public String getpName() {
        return pName;
    }

    public int getAge() {
        return age;
    }

    public double getwBalance() {
        return wBalance;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
