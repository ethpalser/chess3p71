package Game;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author Ethan Palser, Param Jansari
 */
public class Board {

    private Piece board[][];
    private int heuristicVal; // used to check value later without calculating again
    private File log;

    public Board() {
        log = new File("log.txt");
        
        //Initializ White Pieces
        board[7][0] = new Rook(Colour.White);
        board[7][1] = new Knight(Colour.White);
        board[7][2] = new Bishop(Colour.White);
        board[7][3] = new King(Colour.White);
        board[7][4] = new Queen(Colour.White);
        board[7][5] = new Bishop(Colour.White);
        board[7][6] = new Knight(Colour.White);
        board[7][7] = new Rook(Colour.White);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(Colour.White);
        }
        
        //initailize Black Pieces
        board = new Piece[8][8];
        board[0][0] = new Rook(Colour.Black);
        board[0][1] = new Knight(Colour.Black);
        board[0][2] = new Bishop(Colour.Black);
        board[0][3] = new King(Colour.Black);
        board[0][4] = new Queen(Colour.Black);
        board[0][5] = new Bishop(Colour.Black);
        board[0][6] = new Knight(Colour.Black);
        board[0][7] = new Rook(Colour.Black);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Colour.Black);
        }
    }

    public int heristic(Colour playerColour) {
        Piece piece;
        int result = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piece = board[i][j];
                if (piece != null && piece.colour == playerColour) {
                    result += board[i][j].heuristic(this, i, j);
                }
            }
        }
        heuristicVal = result;
        return result;
    }

    public void printBoard() {
        System.out.println("+---+---+---+---+---+---+---+---+");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    System.out.print("   |");
                } else {
                    String piece = "\u2006\u2006" + board[i][j].printToBoard() + "\u2006\u2006\u2006|";
                    System.out.print(piece);
                }
            }
            System.out.println("\n+---+---+---+---+---+---+---+---+");
        }
    }

    public String printToLog() {
        try {
            BufferedReader bR = new BufferedReader(new FileReader(log));
            String line = bR.readLine();
            String output = "";
            while(line != null){
                output += line;
                line = bR.readLine();
            }
        } catch (Exception e) {}
        return null;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public static int boardToIndexX(int boardX) {
        switch (boardX) {
            case 1:
                return 7; // Bottom of Board in White's persepective
            case 2:
                return 6;
            case 3:
                return 5;
            case 4:
                return 4;
            case 5:
                return 3;
            case 6:
                return 2;
            case 7:
                return 1;
            case 8:
                return 0; // Top of Board in White's persepctive
            default:
                return -1;
        }
    }

    public static int boardToIndexY(char boardY) {
        switch (boardY) {
            case 'A':
                return 0; // Left of Board in White's persepective
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7; // Right of Board in White's persepctive
            default:
                return -1;
        }
    }

    public static int indexToBoardX(int indexX) {
        switch (indexX) {
            case 0:
                return 8; // Top of Board in White's persepective
            case 1:
                return 7;
            case 2:
                return 6;
            case 3:
                return 5;
            case 4:
                return 4;
            case 5:
                return 3;
            case 6:
                return 2;
            case 7:
                return 1; // Bottom of Board in White's persepctive
            default:
                return -1;
        }
    }

    public static char indexToBoardY(int indexY) {
        switch (indexY) {
            case 0:
                return 'A'; // Left of Board in White's persepective
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H'; // Right of Board in White's persepctive
            default:
                return '-';
        }
    }
}
