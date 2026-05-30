import java.io.*;
import java.util.*;

public class IPLAnalyserApp{
    public static void writeCSV(String file, List<Match> matches) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (Match m : matches) {
            bw.write(m.team1 + "," + m.team2 + "," + m.score1 + "," + m.score2 + "," + m.winner);
            bw.newLine();
        }
        bw.close();
    }
    public static List<Match> readCSV(String file) throws Exception {
        List<Match> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            Match m = new Match(data[0],data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3]),data[4]);
            list.add(m);
        }
        br.close();
        return list;
    }

    public static void display(List<Match> matches) {
        for (Match m : matches) {
            System.out.println(m);
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        List<Match> matches = new ArrayList<>();
        System.out.print("Enter number of matches: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("Enter Match " + (i + 1));
            System.out.print("Team 1:");
            String t1 = sc.nextLine();
            System.out.print("Team 2:");
            String t2 = sc.nextLine();
            System.out.print("Score 1:");
            int s1 = sc.nextInt();
            System.out.print("Score 2:");
            int s2 = sc.nextInt();
            sc.nextLine();
            System.out.print("Winner:");
            String w = sc.nextLine();
            matches.add(new Match(t1, t2, s1, s2, w));
        }
        String file = "ipl_data.csv";
        writeCSV(file, matches);
        List<Match> loaded = readCSV(file);
        display(loaded);
    }
}