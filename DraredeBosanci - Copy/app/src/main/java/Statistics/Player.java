package Statistics;

public class Player implements Comparable<Player> {
    private String name;
    private int points;

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(Player otherPlayer) {
        return Integer.compare(points, otherPlayer.points);
    }
}

