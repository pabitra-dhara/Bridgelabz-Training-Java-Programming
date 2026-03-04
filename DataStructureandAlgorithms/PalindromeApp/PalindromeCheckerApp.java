package DataStructureandAlgorithms.PalindromeApp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class PalindromeCheckerApp {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Input:");
        String w=sc.nextLine();
        w=w.toLowerCase().replace(" ", "");
        Stack<Character> st=new Stack<>();
        Queue<Character> qe=new LinkedList<>();
        for(int i=0;i<w.length();i++){
            char ch=w.charAt(i);
            st.push(ch);
            qe.add(ch);
        }
        while(st.size()!=0){
            char ch=st.pop();
            if(ch!=qe.remove()){
                System.out.println("Your Input is Not Palindrome");
                return;
            }
        }    

        System.out.println("You Input is Palindrome");

    }
}
