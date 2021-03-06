import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class MazeGenerator {
    int x;
    int y;
    Cell[][] maze;
    int tmp1;
    int tmp2;
    private boolean created;
    ArrayList<Cell> nodes;
    ArrayList<Edge> tree;
    HashMap<Cell, ArrayList<Cell>> adjacencyList;

    public MazeGenerator() {
        this.created = false;
    }

    public void generate(int height, int width) {
        this.x = width;
        this.y = height;
        this.maze = new Cell[width][height];
        this.created = true;
        Random random = new Random();
        nodes = new ArrayList<>();
        tree = new ArrayList<>();
        adjacencyList = new HashMap<>();
        ArrayList<Edge> edges = new ArrayList<>();


        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                maze[i][j] = new Cell(i,j);
            }
        }

        //create entry and exit
        while (true) {
            tmp1 = random.nextInt(this.x -2)+1;

            if (tmp1 % 2 != 0) {
                break;
            }
        }

        while (true) {
            tmp2 = random.nextInt(this.x -2)+1;

            if (tmp2 % 2 != 0) {
                break;
            }
        }


        nodes.add(maze[tmp1][0]);
        nodes.add(maze[tmp2][y-1]);

        edges.add(new Edge(maze[tmp1][0], maze[tmp1][1], 1));

        // solution to the problem of maze not solved if width % 2 == 0
        // the path was cut off near the end of the maze
        if (y % 2 == 0) {
            edges.add(new Edge(maze[tmp2][y-1], maze[tmp2][y-3], 1));
        } else {
            edges.add(new Edge(maze[tmp2][y-1], maze[tmp2][y-2], 1));
        }


        adjacencyList.computeIfAbsent(maze[tmp1][0], k -> new ArrayList<>()).add(maze[tmp1][1]);
        adjacencyList.computeIfAbsent(maze[tmp2][y-1], k -> new ArrayList<>()).add(maze[tmp2][y-2]);



        // create rest of the nodes
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++){
                if(i != 0 && j != 0 && i != x -1 && j != y-1) {
                    if(i*2-1< x -1 && j*2-1<y -1) {
                        nodes.add(maze[i * 2 - 1][j * 2 - 1]);
                    }
                }
            }
        }

        for(int i = 0; i < nodes.size(); i++) {
            nodes.get(i).setId(i);
        }


        for(Cell a: nodes) {
            int xPosition = a.getX();
            int yPosition = a.getY();
            for (Cell b: nodes) {
                if (xPosition == b.getX() && yPosition == b.getY() + 2 ||
                        xPosition == b.getX() && yPosition == b.getY() - 2 ||
                        xPosition == b.getX() + 2 && yPosition == b.getY() ||
                        xPosition == b.getX() - 2 && yPosition == b.getY()) {
                    edges.add(new Edge(a, b));

                }
            }
        }

        // sort edges by value (weight of the edges in graph
        Collections.sort(edges);

        UnionFind unionFind = new UnionFind(nodes.size());
        for (Edge a: edges) {

            if(unionFind.ifConnected(a.getStart().getId(), a.getEnd().getId())) {
                continue;
            }

            unionFind.unify(a.getStart().getId(), a.getEnd().getId());
            tree.add(a);


            adjacencyList.computeIfAbsent(a.getStart(), k -> new ArrayList<>()).add(a.getEnd());
            adjacencyList.computeIfAbsent(a.getEnd(), k -> new ArrayList<>()).add(a.getStart());
        }


        for (Edge a: tree) {
            int row = (a.getStart().getX() + a.getEnd().getX())/2;
            int column = (a.getStart().getY() + a.getEnd().getY())/2;

            for (Cell[] c: maze) {
                for (Cell b : c) {

                    if(b.getX() == row & b.getY() == column) {
                        nodes.add(b);
                    }
                }
            }
        }
        for (Cell a: nodes) {
            a.createPath();
        }




    }

    public String load(String loadedFile) {
        ArrayList<String[]> mazeList = new ArrayList<>();
        adjacencyList = new HashMap<>();
        nodes = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get(loadedFile)))  {
            while (scanner.hasNext()) {
                mazeList.add(scanner.nextLine().split("(?<=\\G..)"));
            }

            this.maze = new Cell[mazeList.size()][mazeList.get(0).length];


            for(int i = 0; i < mazeList.size(); i++) {
                for (int j = 0; j < mazeList.get(0).length; j++) {
                    maze[i][j] = new Cell(i,j,mazeList.get(i)[j]);
                }
            }

            for(int i = 0; i < mazeList.size(); i++) {
                if (maze[i][0].getContent().equals("  ")) {
                    nodes.add(maze[i][0]);
                }
            }

            for(int i = 0; i < mazeList.size(); i++) {
                if (maze[i][mazeList.get(0).length -1].getContent().equals("  ")) {
                    nodes.add(maze[i][mazeList.get(0).length -1]);
                }
            }


            for(int i = 0; i < mazeList.size(); i++) {
                for (int j = 0; j < mazeList.get(0).length; j++) {
                    if (i > 0 && maze[i-1][j].getContent().equals("  ")) {
                        adjacencyList.computeIfAbsent(maze[i][j], k -> new ArrayList<>()).add(maze[i-1][j]);
                    }
                    if (i < mazeList.size() - 1 && maze[i+1][j].getContent().equals("  ")) {
                        adjacencyList.computeIfAbsent(maze[i][j], k -> new ArrayList<>()).add(maze[i+1][j]);
                    }
                    if (j > 0 && maze[i][j-1].getContent().equals("  ")) {
                        adjacencyList.computeIfAbsent(maze[i][j], k -> new ArrayList<>()).add(maze[i][j-1]);
                    }
                    if (j < mazeList.get(0).length - 1 && maze[i][j+1].getContent().equals("  ")) {
                        adjacencyList.computeIfAbsent(maze[i][j], k -> new ArrayList<>()).add(maze[i][j+1]);
                    }
                }
            }






            this.created = true;
            return "File loaded";
        } catch (FileNotFoundException e) {
            return "The file " + loadedFile + " does not exist";
        } catch (IOException e) {
            return "Cannot load the maze. It has an invalid format";
        }
    }

    public boolean ifCreated() {
        return this.created;
    }



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                result.append(maze[i][j]);

            }
            result.append("\n");
        }
        return result.toString();


    }
}