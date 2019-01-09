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
 *
 * @author E
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
    
    // get opponent
    public Player getOpponent() {
        return currentTurn == Colour.White ? black : white;
    }

    public Board getBoard() {
        return currentBoard;
    }

    public void parseUserInput(String userInput) {
        String columns = userInput.replaceAll("[^a-g]", "");
        String rows = userInput.replaceAll("[^1-8]", "");
        int startC = Board.boardToIndexC(columns.charAt(0));
        int startR = Character.getNumericValue(rows.charAt(0));
        int nextC = Board.boardToIndexC(columns.charAt(1));
        int nextR = Character.getNumericValue(rows.charAt(1));
        nextBoard(startC, startR, nextC, nextR);
    }

    public Board nextBoard(String log) {
        //exf8=Q+
        // Parse log input
        // Check if piece colour matches piece colour on board
        // Check if piece type matches piece type on board
        // Convert boardX and boardY into usable indecies for array access
        // Call other nextBoard method
        return currentBoard;
    }

    public Board nextBoard(Move move) {
        return nextBoard(move.startR, move.startC, move.nextR, move.nextC);
    }

    public Board nextBoard(int startR, int startC, int nextR, int nextC) {
        Piece toMove = currentBoard.getBoard()[startR][startC];
        Board next;
        if (currentTurn == Colour.White) {
            // ensure a move is not applied on wrong turn
            if (toMove.colour == Colour.Black) {
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            next = white.movePiece(black, currentBoard, startR, startC, nextR, nextC, promotionTo);
            if (!currentBoard.equals(next)) {
                setBoard(next);
            }
            return next;
        } else {
            if (toMove.colour == Colour.White) {
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            next = black.movePiece(white, currentBoard, startR, startC, nextR, nextC, promotionTo);
            if (!currentBoard.equals(next)) {
                setBoard(next);
            }
            return next;
        }
    }

    public Board undoMove(Log moveStack) {
        Move toUndo = moveStack.undoMove();
        return undoMove(toUndo);
    }

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
        return previousBoard;
    }

    public void setBoard(Board board) {
        // Ensure the next board is changed using nextBoard before setting
        // changes turn to next player
        currentTurn = currentTurn == Colour.Black ? Colour.White : Colour.Black;
        currentBoard = board;
    }

    // need a means to efficiently check this for main loop
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
        if (!kingMove && kingThreatened) {
            currentBoard.printToLogfinalOutcome(currentTurn);
            return true;
        }
        gameOver = kingMove == false && otherMove == false; // gameOver if both false;
        if (gameOver) {
            currentBoard.printToLogfinalOutcome(null);
        }
        return gameOver;
    }

}
