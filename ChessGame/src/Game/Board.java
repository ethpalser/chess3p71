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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the chess board
 *
 * @author Ethan Palser, Param Jansari
 */
public class Board {

    private Piece board[][]; // the chess board
    private int heuristicVal; // used to check value later without calculating again
    private File log; // used to log moves

    public Board(Board copy) {
        board = copy.board;
        heuristicVal = copy.heuristicVal;
        log = copy.log;
    }

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

        board = new Piece[8][8];
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

    /**
     * This method determines the board value of a player (colour) As in how
     * well does the board look for the player
     *
     * @param playerColour
     * @return
     */
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

    /**
     * This method output the board to console
     */
    public void printBoard() {
        System.out.println("   a   b   c   d   e   f   g   h");
        System.out.println(" +---+---+---+---+---+---+---+---+");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print((8-i) + "|");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    System.out.print("   |");
                } else {
                    String piece = "\u2006\u2006" + board[i][j].printToBoard() + "\u2006\u2006\u2006|";
                    System.out.print(piece);
                }
            }
            System.out.println("\n +---+---+---+---+---+---+---+---+");
        }
    }

    /**
     * This method prints the moves to log
     *
     * @param piece
     * @param nextR
     * @param nextC
     * @param actions
     * @param promotionTo
     */
    public void printToLog(Piece piece, int nextR, int nextC, ArrayList<Action> actions, Piece promotionTo) {
        FileWriter fR;
        // Hopefully ensures that the move is valid before logging
        if (piece == null) {
            return; // could log invalid move, but actual games do not
        }
        try {
            //Write to log
            fR = new FileWriter(log, true);
            String s = "";

            for (Action action : actions) {
                switch (action) {
                    case Move:
                        s += piece.printToLog() + indexToBoardR(nextR) + indexToBoardC(nextC);
                        break;
                    case Capture:
                        s += piece.printToLog() + "x" + indexToBoardR(nextR) + indexToBoardC(nextC);
                        break;
                    case Promotion:
                        s += "=" + promotionTo.printToLog();
                        break;
                    case CastleKingSide:
                        s += "0-0";
                        break;
                    case CastleQueenSide:
                        s += "0-0-0";
                        break;
//                    case EnPassant:
//                        s += "e.p.";
//                        break;
                    case Check:
                        s += "+";
                        break;
                    case Checkmate:
                        s += "#";
                        break;
                    default:
                        s = "Unknown Move";
                        break;

                }
            }
            fR.write(s);
            fR.close();
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method prints the final outcome to log
     *
     * @param winner
     */
    public void printToLogfinalOutcome(Colour winner) {
        FileWriter fR;
        try {
            //Write to log
            fR = new FileWriter(log, true);
            String s = "";

            if (null == winner) {
                s = "1/2 - 1/2";
            } else {
                switch (winner) {
                    case White:
                        s = "1-0";
                        break;
                    case Black:
                        s = "0-1";
                        break;
                    default:
                        s = "unknown outcome";
                        break;
                }
            }
            fR.write(s);
            fR.close();
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method output the log to console
     */
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

    /**
     * This method returns the board
     *
     * @return
     */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * This method converts the board row index to code index
     *
     * @param boardR
     * @return
     */
    public static int boardToIndexR(int boardR) {
        switch (boardR) {
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

    /**
     * This method converts the board column to code index
     *
     * @param boardC
     * @return
     */
    public static int boardToIndexC(char boardC) {
        switch (boardC) {
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

    /**
     * This method convert the code index value to row on board
     *
     * @param indexR
     * @return
     */
    public static int indexToBoardR(int indexR) {
        switch (indexR) {
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

    /**
     * This method convert the code index input to column on board
     *
     * @param indexC
     * @return
     */
    public static char indexToBoardC(int indexC) {
        switch (indexC) {
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
