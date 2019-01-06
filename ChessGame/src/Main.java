
import Game.*;
import Pieces.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author E
 */
public class Main {

    public Main() {
        Scanner sc;
        String[] columnSplit, rowSplit;
        String columnsA, columnsB, rowsA, rowsB;
        int cStart, rStart;
        int startR, startC, nextR, nextC;
        Colour player;
        sc = new Scanner(System.in);
        // Get start values for game
        while (true) {
            System.out.print("Input Player Colour (w/b): ");
            String playerColour = sc.next();
            if (playerColour.equalsIgnoreCase("w")
                    || playerColour.equalsIgnoreCase("white")) {
                player = Colour.White;
                break;
            } else if (playerColour.equalsIgnoreCase("b")
                    || playerColour.equalsIgnoreCase("black")) {
                player = Colour.Black;
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
        // create game
        Game game = new Game();

        while (!game.isGameEnd()) {
            // could have ai determine next move first on a separate thread
            // get player input
            if (game.getCurrentTurn() == player) {
                // Get input from the user
                while (true) {
                    System.out.print("Input Position: ");
                    String userInput = sc.next();
                    /*
                    String columns = userInput.replaceAll("[^a-g]", "");
                    String rows = userInput.replaceAll("[^1-8]", "");
                     */
                    columnSplit = userInput.split("[^A-Za-z]+");
                    cStart = columnSplit[0].equals("") ? 1 : 0;
                    columnsA = columnSplit[cStart].replaceAll("[^a-g]", "");
                    columnsB = columnSplit[cStart + 1].replaceAll("[^a-g]", "");

                    rowSplit = userInput.split("[^0-9]+");
                    rStart = rowSplit[0].equals("") ? 1 : 0;
                    rowsA = rowSplit[rStart].replaceAll("[^1-8]", "");
                    rowsB = rowSplit[rStart + 1].replaceAll("[^1-8]", "");

                    try {
                        startC = Board.boardToIndexC(columnsA.charAt(0));
                        startR = Character.getNumericValue(rowsA.charAt(0));
                        nextC = Board.boardToIndexC(columnsB.charAt(0));
                        nextR = Character.getNumericValue(rowsB.charAt(0));

                        System.out.println("Start Position: " + columnsA.charAt(0) + "" + rowsA.charAt(0));
                        System.out.println("Next Position: " + columnsA.charAt(1) + "" + rowsA.charAt(1));
                        /*
                        System.out.print("Start and Next Indecies: ");
                        System.out.println(startC + "" + startR + "   " + nextC + "" + nextR);
                         */
                        // Set the next board and change turn, if move is valid
                        game.nextBoard(startR, startC, nextR, nextC);
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                    }
                }
            }else{
                // GAME TREE ALGORITHM
                // ai determines next move to perform
                startR = 0;
                startC = 0;
                nextR = 0;
                nextC = 0;
                game.nextBoard(startR, startC, nextR, nextC);
            }
        }

    }

    public static void main(String args[]) {
        Main m = new Main();
    }
}
