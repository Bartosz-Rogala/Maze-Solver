public class Cell {
    private int x;
    private int y;
    private int id;
    private String content = "\u2588\u2588";

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y, String content) {
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getContent() {
        return this.content;
    }

    void createPath() {
        this.content = "  ";
    }

    @Override
    public String toString() {
        return this.content;
//        return this.x + " " + this.y;
    }


    public boolean equals(Cell cell) {
        // If the object is compared with itself then return true
        if (cell == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(cell instanceof Cell)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Cell c = (Cell) cell;

        // Compare the data members and return accordingly
        if (this.x == c.x && this.y == c.y) {
            return true;
        } else {
            return false;
        }
    }
}