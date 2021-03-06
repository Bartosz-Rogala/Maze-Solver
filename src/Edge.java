import java.util.Random;

public class Edge implements Comparable<Edge> {
    private Cell start;
    private Cell end;
    private int value;

    public Edge(Cell start, Cell end) {
        Random random = new Random();
        this.start = start;
        this.end = end;
        this.value = random.nextInt(100) + 1;
    }

    public Edge(Cell start, Cell end, int value) {;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public Cell getStart() {
        return this.start;
    }

    public Cell getEnd() {
        return this.end;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return this.start.getX() + ", " + this.start.getY() + "; " + this.end.getX() + ", " + this.end.getY() + ": " + this.value;
    }

    @Override
    public int compareTo(Edge edge) {
        return this.value - edge.value;
    }
}

