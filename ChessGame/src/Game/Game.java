/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Pieces.King;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.PieceType;
import Pieces.Rook;

/**
 * This class manages the full game state containing both players, the board,
 * and the turn order to ensure the game operates according to the game rules.
 *
 * @author Ep16fb
 */
public class Game {

    private final Player white;
    private final Player black;
    private Board currentBoard;
    private Colour currentTurn;

    Piece promotionTo;
    boolean castleKingSide;

    public Game() {
        white = new Player(Colour.White);
        black = new Player(Colour.Black);
        currentBoard = new Board();
        currentTurn = Colour.White;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = currentBoard.getBoard()[i][j];
                if (piece != null) {
                    if (piece.colour == Colour.White) {
                        white.setupAttacks(currentBoard, i, j);
                    }
                    if (piece.colour == Colour.Black) {
                        black.setupAttacks(currentBoard, i, j);
                    }
                }
            }
        }
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public Colour getCurrentTurn() {
        return currentTurn;
    }

    public Player getOpponent() {
        return currentTurn == Colour.White ? black : white;
    }

    public Board getBoard() {
        return currentBoard;
    }

    /**
     * This method retrieves a string that has been inputted by a user and
     * determines the first and second part of the move by removing invalid
     * characters and then splitting the string.
     *
     * @param userInput
     */
    public void parseUserInput(String userInput) {
        String columns = userInput.replaceAll("[^a-g]", "");
        String rows = userInput.replaceAll("[^1-8]", "");
        int startC = Board.boardToIndexC(columns.charAt(0));
        int startR = Character.getNumericValue(rows.charAt(0));
        int nextC = Board.boardToIndexC(columns.charAt(1));
        int nextR = Character.getNumericValue(rows.charAt(1));
        nextBoard(startC, startR, nextC, nextR);
    }

    /**
     * Depreciated
     *
     * @param log
     * @return
     */
    public Board nextBoard(String log) {
        //exf8=Q+
        // Parse log input
        // Check if piece colour matches piece colour on board
        // Check if piece type matches piece type on board
        // Convert boardX and boardY into usable indecies for array access
        // Call other nextBoard method
        return currentBoard;
    }

    /**
     * See nextBoard(int startR, int startC, int nextR, int nextC)
     *
     * @param move
     * @return
     */
    public Board nextBoard(Move move) {
        return nextBoard(move.startR, move.startC, move.nextR, move.nextC);
    }

    /**
     * This method checks that the piece moved on the board is valid and then
     * applies the move and changes the board state, as there is no other
     * situation the board needs to be overridden.
     *
     * @param startR
     * @param startC
     * @param nextR
     * @param nextC
     * @return
     */
    public Board nextBoard(int startR, int startC, int nextR, int nextC) {
        Piece toMove = currentBoard.getBoard()[startR][startC];
        Board next;
        if (currentTurn == Colour.White) {
            // ensure a move is not applied on wrong turn
            if (toMove == null || toMove.colour == Colour.Black) {
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            next = white.movePiece(black, currentBoard, startR, startC, nextR, nextC, promotionTo);
            if (currentBoard.equals(next)) {
                return currentBoard;
            }
            //setBoard(next);
            return next;
        } else {
            if (toMove == null || toMove.colour == Colour.White) {
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            next = black.movePiece(white, currentBoard, startR, startC, nextR, nextC, promotionTo);
            if (currentBoard.equals(next)) {
                return currentBoard;
            }
            //setBoard(next);
            return next;
        }
    }

    /**
     * This ensured that a log defining a proper order of actions is used to
     * ensure that the board is undone in the reverse order it was applied See
     * undoMove(Move move).
     *
     * @param moveStack
     * @return
     */
    public Board undoMove(Log moveStack) {
        Move toUndo = moveStack.undoMove();
        return undoMove(toUndo);
    }

    /**
     * This takes the move and reverts the action applied on the board and
     * replaces the previous position with a piece it may have captured, and it
     * also uses the log to check if any special action was performed.
     *
     * @param move
     * @return
     */
    public Board undoMove(Move move) {
        Board previousBoard = new Board(currentBoard);
        Piece previous = previousBoard.getBoard()[move.nextR][move.nextC];
        int row = previous.colour == Colour.White ? 7 : 0;
        // checks if castle (queen side) was performed (O-O-O) not optional
        if (move.getLog().matches("[A-Ga-g1-8BKNPQR]+(x)*[A-Ga-g1-8BKNPQR]+(O-O-O)(=[BNQR])*[+#]*")) {
            previousBoard.getBoard()[row][2] = null; // king 'next'
            previousBoard.getBoard()[row][3] = null; // rook 'next'
            previousBoard.getBoard()[row][4] = new King(previous.colour); // king previous
            previousBoard.getBoard()[row][0] = new Rook(previous.colour); // rook previous
            return previousBoard;
        } // check if castle (king side) was performed (O-O) not optional
        else if (move.getLog().matches("[A-Ga-g1-8BKNPQR]+(x)*[A-Ga-g1-8BKNPQR]+(O-O)(=[BNQR])*[+#]*")) {
            previousBoard.getBoard()[row][6] = null; // king 'next'
            previousBoard.getBoard()[row][5] = null; // rook 'next'
            previousBoard.getBoard()[row][4] = new King(previous.colour); // king previous
            previousBoard.getBoard()[row][7] = new Rook(previous.colour); // rook previous
            return previousBoard;
        } // checks if promotion was performed (=[BNQR]) not optional
        else if (move.getLog().matches("[A-Ga-g1-8BKNPQR]+(x)*[A-Ga-g1-8BKNPQR]+(O-O|O-O-O)*(=[BNQR])[+#]*")) {
            previous = new Pawn(previous.colour);
        }
        previousBoard.getBoard()[move.startR][move.startC] = previous;
        previousBoard.getBoard()[move.nextR][move.nextC] = move.getCaptured();
        setBoard(previousBoard); // will also change currentTurn colour
        System.out.println("Undo Complete!");
        return previousBoard;
    }

    public void setBoard(Board board) {
        // Ensure the next board is changed using nextBoard before setting
        currentBoard = board;
    }

    public boolean changeTurn() {
        // changes turn to next player
        if (currentTurn == Colour.Black) {
            currentTurn = Colour.White;
        } else if (currentTurn == Colour.White) {
            currentTurn = Colour.Black;
        }
        return true;
    }

    /**
     * This checks if a king has been captured or if the king cannot move and is
     * in check or stalemate to end the game.
     *
     * @return
     */
    public boolean isGameEnd() {
        boolean gameOver = white.getLoss() || black.getLoss();
        if (gameOver) {
            currentBoard.printToLogfinalOutcome(currentTurn);
            return gameOver;
        }
        Player opponent = currentTurn == Colour.White ? black : white;
        Piece toExamine;
        boolean kingMove = false; // default cannot move
        boolean otherMove = false; // default cannot move
        boolean kingThreatened = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // check to see if there is a piece that can move
                toExamine = currentBoard.getBoard()[i][j];
                if (toExamine != null) {
                    if (toExamine.colour == currentTurn) {
                        toExamine.validMoves(opponent, currentBoard, i, j);
                        if (toExamine.piece == PieceType.King) {
                            kingMove = toExamine.getCanMove();
                            if (toExamine.isThreatened(currentBoard, i, j) > 0) {
                                kingThreatened = true;
                            }
                        } else {
                            otherMove = toExamine.getCanMove();
                        }
                        if (otherMove == true || kingMove == true) {
                            break;
                        }
                    }
                }
            }
        }
        // check if it is checkmate
        if (!kingMove && kingThreatened) {
            currentBoard.printToLogfinalOutcome(currentTurn);
            return true;
        }
        // stalemate occurs if both false
        gameOver = kingMove == false && otherMove == false;
        if (gameOver) {
            currentBoard.printToLogfinalOutcome(null);
        }
        return gameOver;
    }

}
