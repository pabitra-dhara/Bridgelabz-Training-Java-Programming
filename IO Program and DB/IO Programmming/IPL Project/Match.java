public class Match {
    String team1, team2, winner;
    int score1, score2;

    Match(String t1, String t2, int s1, int s2, String w) {
        team1 = t1;
        team2 = t2;
        score1 = s1;
        score2 = s2;
        winner = w;
    }

    public String toString() {
        return team1 + " vs " + team2 + " -> Winner: " + winner;
    }
}
