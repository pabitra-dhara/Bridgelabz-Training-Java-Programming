import java.util.Scanner;

public class TicTacToeApp {

    public static void print(char box[][]){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(box[i][j]+" | ");
            }
            System.out.println();
        }
    }
    public static boolean winCheck(char box[][],char player){
        for(int i=0;i<3;i++){
            if(box[i][0]==player && box[i][1]==player && box[i][2]==player){
                return true;
            }
        }

        for(int j=0;j<3;j++){
            if(box[0][j]==player && box[1][j]==player && box[2][j]==player){
                return true;
            }
        }

        if(box[0][0]==player && box[1][1]==player && box[2][2]==player){
            return true;
        }

        if(box[0][2]==player && box[1][1]==player && box[2][0]==player){
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        char box[][]=new char[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                box[i][j]=' ';
            }
        }

        char player='X';
        boolean gameWon=false;
        while(!gameWon){
            print(box);
            System.out.println("Player "+player +",enter your move(row and column):");
            int row;
            int col;
            try{
            row=sc.nextInt();
            col=sc.nextInt();
            }catch(Exception e){
                System.out.println("Invalid input! Please enter numbers only.");
                sc.nextLine();
                continue;
            }
            if (row<0||row>2||col<0||col>2) {
                System.out.println("Row and column must be between 0 and 2.");
                continue;
            }
            if(box[row][col]==' '){
                box[row][col]=player;
                gameWon=winCheck(box,player);
                if(gameWon){
                    print(box);
                    System.out.println("Player "+player+" wins!");
                }else{
                    if(player=='X'){
                        player='O';
                    }else{
                        player='X';
                    }
                }
            }else{
                System.out.println("Invalid move! Try again.");
            }
        }
        print(box);
    }
}