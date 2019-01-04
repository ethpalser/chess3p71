/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Pieces.Piece;
import Pieces.PieceType;
import java.util.ArrayList;

/**
 *
 * @author E
 */
public class Game {

    private final Player white;
    private final Player black;
    private Board currentBoard;
    private Colour currentTurn;
    int repeatedMoves; // counts to 3 (draw), resets if either doesn't repeat

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

    public Board getBoard() {
        return currentBoard;
    }

    public Board nextBoard(String log) {
        // Parse log input
        // Check if piece colour matches piece colour on board
        // Check if piece type matches piece type on board
        // Convert boardX and boardY into usable indecies for array access
        // Call other nextBoard method
        return currentBoard;
    }

    public Board nextBoard(int startX, int startY, int nextX, int nextY) {
        Piece toMove = currentBoard.getBoard()[startX][startY];
        if (currentTurn == Colour.White) {
            // ensure a move is not applied on wrong turn
            if (toMove.colour == Colour.Black) {
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            return white.movePiece(currentBoard, startX, startY, nextX, nextY);
        } else {
            if (toMove.colour == Colour.White) {
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            return black.movePiece(currentBoard, startX, startY, nextX, nextY);
        }
    }

    public void setBoard(Board board) {
        // Ensure the next board is changed using nextBoard before setting
        // changes turn to next player
        currentTurn = currentTurn == Colour.Black ? Colour.White : Colour.Black;
        currentBoard = board;
    }

    // need a means to efficiently check this for main loop
    public boolean checkmate(Player opponent) {
        return false;
    }

    //check repeated moves
    public boolean checkRepeat(Board board, int startX, int startY, int nextX, int nextY) {
        Player p = black;
        if (currentTurn == Colour.White) {
            p = white;
        }
        Piece toMove = board.getBoard()[startX][startY];
        if (p.getLastMoved().equals(toMove) && p.getLastX() == nextX && p.getLastY() == nextY && repeatedMoves < 3) {
            repeatedMoves++;
        } else {
            repeatedMoves = 0;
        }
        return repeatedMoves == 6;
    }

    public ArrayList<Action> actionTaken(Board board, int startX, int startY, int nextX, int nextY) {
        Piece pieceMoved = board.getBoard()[startX][startY];
        Piece pieceAt = board.getBoard()[nextX][nextY];
        ArrayList<Action> actions = new ArrayList<>();

        //Move
        if (pieceAt == null) {
            actions.add(Action.Move);
        } else {
            //Capture
            if (pieceAt.getColour() != currentTurn) {
                //Checkmate
                if (pieceAt.getType() == PieceType.King) {
                    actions.add(Action.Checkmate);
                }
                actions.add(Action.Capture);
            } else {
                //Castling
                if (pieceAt.getType() == PieceType.Rook) {
                    actions.add(Action.Castle);
                    if (startX < nextX) {
                        castleKingSide = true;
                    }
                }
            }
        }
        if (pieceMoved.getType() == PieceType.Pawn) {
            //Promotion
            if (currentTurn == Colour.White && nextY == 0 || currentTurn == Colour.Black && nextY == 7) {
                actions.add(Action.Promotion);
            }
            //En Passant
//            if (currentTurn == Colour.White && startY == 3 || currentTurn == Colour.Black && startY == 4) {
//                
//                int incre = (currentTurn == Colour.White ? -1: 1);
//                Piece beside = board.getBoard()[nextX][nextY + incre];
//                if(beside.getType() == PieceType.Pawn && beside.getColour() != currentTurn){
//                    actions.add(Action.EnPassant);
//                }
//            }
            Player player = white;
            Player opponent = black;
            if (currentTurn != Colour.White) {
                player = black;
                opponent = white;
            }
            //check if opponent last moved pawn by two spaces
            Piece lastMoved = opponent.lastMoved;
            if (lastMoved == PieceType.Pawn && lastMoved.movedTwo()) {
                //checks if my pawn is in right position and moves to right space
                if (currentTurn == Colour.White && startY == 3 && (player.lastX == startX - 1 || player.lastX == startX + 1)) {
                    if (nextY == player.lastY) {
                        actions.add(Action.EnPassant);
                    }
                } else if (currentTurn == Colour.Black && startY == 4 && (player.lastX == startX - 1 || player.lastX == startX + 1)) {
                    if (nextY == player.lastY) {
                        actions.add(Action.EnPassant);
                    }
                }
            }
        }

        return actions;
    }
}
