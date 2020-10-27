import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UI {
    // Scanner is used for reading user input.
    private Scanner scanner;
    // The current maze. It is null when the game starts.
    private MazeGenerator newMaze;

    public void startUI() {
        scanner = new Scanner(System.in);
        newMaze = new MazeGenerator();

        //Endless loop printing all available options to the user
        // saving, displaying and solving the maze are available only after loading or generating a maze
        while (true) {
            System.out.println("=== Menu ===");
            System.out.println("(Enter number)");
            System.out.println("1. Generate a new maze");
            System.out.println("2. Load a maze");

            // ifCreated indicates whether the maze exists
            if (newMaze.ifCreated()) {
                System.out.println("3. Save the maze");
                System.out.println("4. Display the maze");
                System.out.println("5. Find the escape");
            }
            System.out.println("0. Exit");
            int input = Integer.valueOf(scanner.nextLine());
            switch (input) {
                case 1: //generate new maze
                    generate();
                    break;
                case 2: // load a maze
                    load();
                    break;
                case 3: //save the maze
                    save();
                    break;
                case 4: //display the maze
                    display();
                    break;
                case 5: //solve and print the maze
                    solve();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect option. Please try again");
            }
        }



    }

    private void generate() {
//        scanner = new Scanner(System.in);
        System.out.println("Please, enter the size of a maze (in the [size] or [width height] format)");
        //enter to ints separated by a space
        String[] mazeSize = scanner.nextLine().split(" ");
        switch (mazeSize.length) {
            case 1:
                newMaze.generate(Integer.valueOf(mazeSize[0]), Integer.valueOf(mazeSize[0]));
                System.out.println("Maze generated");
                break;
            case 2:
                newMaze.generate(Integer.valueOf(mazeSize[0]),Integer.valueOf(mazeSize[1]));
                System.out.println("Maze generated");
                break;
            default:
                System.out.println("Cannot generate a maze. Invalid size");
        }
    }

    private void load() {
        System.out.println("Please, provide the name of the file");
        String loadedFile = scanner.nextLine();
        System.out.println(newMaze.load("./" + loadedFile + ".txt"));
    }

    private void save() {
        if (newMaze.ifCreated()) {
            System.out.println("Please, provide the name of the file");
            String fileName = scanner.nextLine();
            File file = new File("./" + fileName + ".txt");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(newMaze.toString());
            } catch (FileNotFoundException e) {
                System.out.println("The file " + fileName + " does not exist");
            } catch (IOException e) {
                System.err.print("\"Cannot load the maze. It has an invalid format");
            }
        } else {
            System.out.println("Incorrect option. Please try again");
        }
    }

    private void display() {
        if (newMaze.ifCreated()) {
            System.out.println(newMaze);
        } else {
            System.out.println("Incorrect option. Please try again");
        }
    }

    private void solve() {
        if (newMaze.ifCreated()) {
            MazeSolver solver = new MazeSolver(newMaze);
            solver.solve();
            System.out.println(solver);
        }
    }
}

