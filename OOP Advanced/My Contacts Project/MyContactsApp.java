import java.util.*;

class Contact{
    String name;
    String phone;
    Contact(String name,String phone){
        this.name=name;
        this.phone = phone;
    }
    void show(){
        System.out.println(name + " - " + phone);
    }
}

public class MyContactsApp{
    public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    ArrayList<Contact> contacts = new ArrayList<>();
        while(true){
            System.out.println("\n1.Add 2.View 3.Search 4.Delete 5.Exit");
            int choice = sc.nextInt();
            
            if(choice==1){
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Phone: ");
                String phone = sc.nextLine();
                Contact c = new Contact(name,phone);
                contacts.add(c);
            }
            else if(choice==2){
                for(int i=0; i<contacts.size(); i++){
                    Contact c = contacts.get(i);
                    c.show();
                }
            }else if(choice==3){
                System.out.print("Search Name: ");
                String searchName = sc.nextLine();
                for(int i=0; i<contacts.size(); i++){
                    Contact c = contacts.get(i);
                    if(searchName.equals(c.name)){
                        c.show();
                    }
                }
            }else if(choice==4){
                System.out.print("Delete Name: ");
                String deleteName = sc.nextLine();
                for(int i=0; i<contacts.size(); i++){
                    Contact c = contacts.get(i);
                    if(deleteName.equals(c.name)){
                        contacts.remove(i);
                        break;
                    }
                }
            }else{
                break;
            }
        }
    }
}
