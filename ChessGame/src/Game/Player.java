package Game;

import Pieces.Piece;
import Pieces.PieceType;
import Pieces.Queen;
import java.util.ArrayList;

/**
 *
 *
 * @author Ethan Palser, Param Jansari
 */
public class Player {

    Colour colour; // Black or White
    // Part 3 Param
    int piecesCentred;
    int repeatedMoves; // counts to 3 (draw), resets if either doesn't repeat
    Piece lastMoved;
    int lastX, lastY;

    Piece promotionTo;
    boolean castleKingSide;

    public Player(Colour c) {
        colour = c;
    }

    public Board movePiece(Board board, int startX, int startY, int nextX, int nextY) {
        Piece toMove = board.getBoard()[startX][startY];
        if (toMove.colour != colour) {
            return board; // nothing happens to board state or player states
        }
        if (toMove.validMoves(board, startX, startY)[nextX][nextY] == false) {
            return board; // invalid action
        } else {
            // May need to have repeated check before move is considered (maybe in board)
            // valid action occurs
            lastMoved = toMove;
            lastX = nextX;
            lastY = nextY;
            // gets a copy of the board to modify
            Board nextBoard = board;
            nextBoard.getBoard()[nextX][nextY] = nextBoard.getBoard()[startX][startY];
            nextBoard.getBoard()[startX][startY] = null;

            //variables 
            Action action = Action.Move;
            Piece promotionTo = new Queen(colour);
            boolean castleKingSide = false;

            // check if castling is king side or queen side
            // check action
            nextBoard.printToLog(toMove, nextX, nextY, action, promotionTo, castleKingSide);
            return nextBoard; // returns new board state after applying move
        }
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
            if (pieceAt.getColour() != colour) {
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
            if (colour == Colour.White && nextY == 0 || colour == Colour.Black && nextY == 7) {
                actions.add(Action.Promotion);
            }
            //En Passant
            if (colour == Colour.White && startY == 3 || colour == Colour.Black && startY == 4) {
                int incre = (colour == Colour.White ? -1: 1);
                Piece beside = board.getBoard()[nextX][nextY + incre];
                if(beside.getType() == PieceType.Pawn && beside.getColour() != colour){
                    actions.add(Action.EnPassant);
                }
            }


        }

        return actions;
    }

    public boolean checkRepeat(Board board, int startX, int startY, int nextX, int nextY) {
        Piece toMove = board.getBoard()[startX][startY];
        if (lastMoved.equals(toMove) && lastX == nextX && lastY == nextY && repeatedMoves < 3) {
            repeatedMoves++;
        } else {
            repeatedMoves = 0;
        }
        return repeatedMoves == 3;
    }

    public int piecesCentered(Board board) {
        for (int i = 2; i < 6; i++) {
            for (int j = 2; j < 6; j++) {
                if (board.getBoard()[i][j].getColour() == colour) {
                    piecesCentred++;
                }
            }
        }
        return piecesCentred;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public Piece getLastMoved() {
        return lastMoved;
    }

}
