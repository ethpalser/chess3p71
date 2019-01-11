
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
        int searchDepth = 1; // need user input to change
        Colour player, opponent;
        sc = new Scanner(System.in);
        // Get start values for game
        while (true) {
            System.out.print("Input Player Colour (w/b): ");
            String playerColour = sc.next();
            if (playerColour.equalsIgnoreCase("w")
                    || playerColour.equalsIgnoreCase("white")) {
                player = Colour.White;
                opponent = Colour.Black;
                break;
            } else if (playerColour.equalsIgnoreCase("b")
                    || playerColour.equalsIgnoreCase("black")) {
                player = Colour.Black;
                opponent = Colour.White;
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
        // create game
        Game game = new Game();

        System.out.println(game.isGameEnd());
        while (!game.isGameEnd()) {
            game.getBoard().printBoard();
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

                    try {
                        cStart = columnSplit[0].equals("") ? 1 : 0;
                        columnsA = columnSplit[cStart].replaceAll("[^a-h]", "");
                        columnsB = columnSplit[cStart + 1].replaceAll("[^a-h]", "");

                        rowSplit = userInput.split("[^0-9]+");
                        rStart = rowSplit[0].equals("") ? 1 : 0;
                        rowsA = rowSplit[rStart].replaceAll("[^1-8]", "");
                        rowsB = rowSplit[rStart + 1].replaceAll("[^1-8]", "");

                        startC = Board.boardToIndexC(columnsA.charAt(0));
                        startR = Character.getNumericValue(rowsA.charAt(0));
                        nextC = Board.boardToIndexC(columnsB.charAt(0));
                        nextR = Character.getNumericValue(rowsB.charAt(0));

//                        System.out.println("Start Position: " + columnsA.charAt(0) + "" + rowsA.charAt(0));
//                        System.out.println("Next Position: " + columnsA.charAt(1) + "" + rowsA.charAt(1));
                        /*
                        System.out.print("Start and Next Indecies: ");
                        System.out.println(startC + "" + startR + "   " + nextC + "" + nextR);
                         */
                        // Set the next board and change turn, if move is valid
                        game.setBoard(game.nextBoard(startR, startC, nextR, nextC));
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
//                        System.out.println(rowsA.length() + "  " + columnsA.length());
                    }
                }
            } else {
                // GAME TREE ALGORITHM
                // ai determines next move to perform (may be slow)
                GameTree gameTree = new GameTree(game, searchDepth);
                Node bestMove = gameTree.findBestMove(opponent, opponent, gameTree.root, null);
                game.nextBoard(bestMove.move);
            }
        }

    }

    public static void main(String args[]) {
        Main m = new Main();
    }
}
