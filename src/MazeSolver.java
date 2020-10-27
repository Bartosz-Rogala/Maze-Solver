import java.util.*;

public class MazeSolver {
    MazeGenerator maze;
    ArrayList<Cell> nodes;
    ArrayList<Edge> tree;
    HashMap<Cell, ArrayList<Cell>> adjacencyList;
    ArrayList<Cell> visited;
    Cell end;
    List<Cell> solvedPath;

    public MazeSolver(MazeGenerator maze) {
        this.maze = maze;


    }

    public List<Cell> solve() {
        Deque<Cell> stack = new ArrayDeque<>();
        visited = new ArrayList<>();
        solvedPath = new ArrayList<>();


        end = maze.nodes.get(1);
        Cell current = maze.nodes.get(0);


        if (explore(maze, current, solvedPath)) {
            ArrayList<Cell> newPath = new ArrayList<>();
            for(int i  = 0; i < solvedPath.size() -1; i++) {
                newPath.add(maze.maze[(solvedPath.get(i).getX() + solvedPath.get(i+1).getX()) / 2][(solvedPath.get(i).getY() + solvedPath.get(i+1).getY()) / 2]);
            }
            solvedPath.addAll(newPath);
            return solvedPath;
        } else {
            return Collections.emptyList();
        }
    }


    public boolean explore(MazeGenerator maze, Cell current, List<Cell> solvedPath) {

        if (visited.contains(current)) {
            return false;
        }

        solvedPath.add(current);
        visited.add(current);

        if (current.equals(end)) {
            // Found the exit!
            return true;
        }




        for (Cell a: maze.adjacencyList.get(current)) {
            current = a;
            if (explore(maze, current, solvedPath)) {
                return true;
            }
        }

        solvedPath.remove(solvedPath.size()-1);
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();


        for (int i = 0; i < maze.maze.length; i++) {
            for (int j = 0; j < maze.maze[i].length; j++) {
                if(this.solvedPath.contains(maze.maze[i][j])) {
                    result.append("##");
                } else {
                    result.append(maze.maze[i][j]);
                }

            }
            result.append("\n");
        }
        return result.toString();

    }
}
