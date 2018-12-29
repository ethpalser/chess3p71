package Game;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.PieceType;
import Pieces.Queen;
import Pieces.Rook;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

        //Empties file for new log
        PrintWriter pW;
        try {
            pW = new PrintWriter(log);
            pW.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Initializ White Pieces
        board[7][0] = new Rook(Colour.White); // Bottom left square black
        board[7][1] = new Knight(Colour.White);
        board[7][2] = new Bishop(Colour.White);
        board[7][3] = new Queen(Colour.White);
        board[7][4] = new King(Colour.White); // On black square
        board[7][5] = new Bishop(Colour.White);
        board[7][6] = new Knight(Colour.White);
        board[7][7] = new Rook(Colour.White); // Bottom right square white
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(Colour.White);
        }

        //initailize Black Pieces
        board = new Piece[8][8];
        board[0][0] = new Rook(Colour.Black); // Top left square white
        board[0][1] = new Knight(Colour.Black);
        board[0][2] = new Bishop(Colour.Black);
        board[0][3] = new Queen(Colour.Black);
        board[0][4] = new King(Colour.Black); // On white squre
        board[0][5] = new Bishop(Colour.Black);
        board[0][6] = new Knight(Colour.Black);
        board[0][7] = new Rook(Colour.Black); // Top right square black
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

    public void printToLog(Piece piece, int nextX, int nextY, Action action, Piece promotionTo, boolean castleKingSide) {
        FileWriter fR;
        // Hopefully ensures that the move is valid before logging
        if (piece == null) {
            return; // could log invalid move, but actual games do not
        }
        try {
            //Write to log
            fR = new FileWriter(log);
            String s = "";
            switch (action) {
                case Move:
                    s = piece.printToLog() + indexToBoardX(nextX) + indexToBoardY(nextY);
                    break;
                case Capture:
                    s = piece.printToLog() + "x" + indexToBoardX(nextX) + indexToBoardY(nextY);
                    break;
                case Promotion:
                    s = piece.printToLog() + indexToBoardX(nextX) + indexToBoardY(nextY) + promotionTo.printToLog();
                    break;
//                case Check:
//                     s = piece.printToLog() + indexToBoardX(nextX) + indexToBoardY(nextY) + "+";
//                    break;
//                case Checkmate:
//                     s = piece.printToLog() + indexToBoardX(nextX) + indexToBoardY(nextY) + "#";
//                    break;
                case Castle:
                    if (castleKingSide)
                        s = "0-0";
                    else
                        s = "0-0-0";
                    break;
                case EnPassant:
                    s = piece.printToLog() + indexToBoardX(nextX) + indexToBoardY(nextY) + "e.p.";
                    break;
                default:
                    s = "Unknown Move";
                    break;
            }
            fR.write(s);
            fR.close();
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printLog() {
        BufferedReader bR;
        try {
            bR = new BufferedReader(new FileReader(log));
            String line = bR.readLine();
            String output = "";
            while (line != null) {
                output += line;
                line = bR.readLine();
            }
            bR.close();
        } catch (Exception e) {
        }

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
            case 'a':
                return 0; // Left of Board in White's persepective
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
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
                return 'a'; // Left of Board in White's persepective
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            case 4:
                return 'e';
            case 5:
                return 'f';
            case 6:
                return 'g';
            case 7:
                return 'h'; // Right of Board in White's persepctive
            default:
                return '-';
        }
    }
}
