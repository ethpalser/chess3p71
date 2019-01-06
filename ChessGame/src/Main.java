
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
        while (true) {
            sc = new Scanner(System.in);
            String userInput = sc.next();
            String columns = userInput.replaceAll("[^a-g]", "");
            String rows = userInput.replaceAll("[^1-8]", "");
            try {
                int startC = Board.boardToIndexC(columns.charAt(0));
                int startR = Character.getNumericValue(rows.charAt(0));
                int nextC = Board.boardToIndexC(columns.charAt(1));
                int nextR = Character.getNumericValue(rows.charAt(1));
                
                System.out.println(startC + "" + startR + "   " + nextC + "" + nextR);
                break;
            } catch (Exception e) {
                System.out.println("invalid input");
            }
        }
    }

    public static void main(String args[]) {
        Main m = new Main();
    }
}
