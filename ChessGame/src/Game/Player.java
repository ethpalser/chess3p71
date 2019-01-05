package Game;

import Pieces.Piece;
import Pieces.PieceType;
import java.util.ArrayList;

/**
 *
 *
 * @author Ethan Palser, Param Jansari
 */
public class Player {

    private final Colour colour; // Black or White
    // Part 3 Param
    private int piecesCentred;
    private int repeatedMoves; // counts to 3 (draw), resets if either doesn't repeat
    private Piece lastMoved;
    private int lastX, lastY;
    private int[][] attacks;

    public Player(Colour c) {
        colour = c;
        piecesCentred = 0;
        lastX = -1; // defaults that indicate no piece
        lastY = -1; // defaults that indicate no piece
        attacks = new int[8][8];
    }

    public Board movePiece(
            Player opponent,
            Board board,
            int startR,
            int startC,
            int nextR,
            int nextC,
            Piece promotionTo) {
        Piece toMove = board.getBoard()[startR][startC];
        if (toMove.colour != colour) {
            return board; // nothing happens to board state or player states
        }
        if (toMove.validMoves(opponent, board, startR, startC)[nextR][nextC] == false) {
            return board; // invalid action
        } else {
            // May need to have repeated check before move is considered (maybe in board)
            // valid action occurs
            lastMoved = board.getBoard()[startR][startC];
            lastX = nextR;
            lastY = nextC;
            // check if rook or king moves, if so castling not possible unless performed this move
            if (toMove.piece == PieceType.Rook || toMove.piece == PieceType.King) {
                board.getBoard()[startR][startC].modifySpecial();
            } // check if pawn moved and if it can be en passant after
            else if (toMove.piece == PieceType.Pawn
                    && (toMove.colour == Colour.White && nextR == startR - 2
                    || toMove.colour == Colour.Black && nextR == startR + 2)) {
                board.getBoard()[startR][startC].modifySpecial();
            }
            // gets a copy of the board to modify
            Board nextBoard = board;
            nextBoard.getBoard()[nextR][nextC] = nextBoard.getBoard()[startR][startC];
            nextBoard.getBoard()[startR][startC] = null;
            // get a list of actions
            ArrayList<Action> actions = actionTaken(opponent, board, startR, startC, nextR, nextC);
            // update player state of attacks
            this.updateAttacks(board, startR, startC, nextR, nextC);
            // output results to board
            nextBoard.printToLog(toMove, nextR, nextC, actions, promotionTo);
            return nextBoard; // returns new board state after applying move
        }
    }

    public ArrayList<Action> actionTaken(
            Player opponent,
            Board board,
            int startX,
            int startY,
            int nextX,
            int nextY) {
        Piece pieceMoved = board.getBoard()[startX][startY];
        Piece pieceAt = board.getBoard()[nextX][nextY];
        ArrayList<Action> actions = new ArrayList<>();
        int backRow = colour == Colour.White ? 7 : 0;
        //Move
        if (pieceAt == null) {
            actions.add(Action.Move);
            //Castling (assume move has been validated already)
            if (pieceMoved.getType() == PieceType.King) {
                // can castle
                if (pieceMoved.validSpecial()) {
                    // check queen side
                    if (nextX == backRow && nextY == 2) {
                        // check if rook has moved
                        if (board.getBoard()[backRow][0] != null
                                && board.getBoard()[backRow][0].validSpecial()) {
                            actions.add(Action.CastleQueenSide);
                        }
                    } // check king side
                    else if (nextX == backRow && nextY == 6) {
                        // check if rook has moved
                        if (board.getBoard()[backRow][7] != null
                                && board.getBoard()[backRow][7].validSpecial()) {
                            actions.add(Action.CastleKingSide);
                        }
                    }
                }
            }
        } else {
            //Capture
            if (pieceAt.getColour() != colour) {
                actions.add(Action.Capture);
                //Checkmate
                if (pieceAt.getType() == PieceType.King) {
                    actions.add(Action.Checkmate);
                }
            } else {
                // Invalid action
            }
        }
        if (pieceMoved.getType() == PieceType.Pawn) {
            //Promotion
            if (colour == Colour.White && nextX == 0 || colour == Colour.Black && nextX == 7) {
                actions.add(Action.Promotion);
            }
            //check if opponent last moved pawn by two spaces
            Piece lastMoved = opponent.getLastMoved();
            if (lastMoved.getType() == PieceType.Pawn && lastMoved.validSpecial()) {
                //checks if my pawn is in right position and moves to right space

                if (colour == Colour.White && startY == 3
                        && (opponent.getLastX() == startX - 1
                        || opponent.getLastX() == startX + 1)) {
                    if (nextY == opponent.getLastY()) {
                        actions.add(Action.EnPassant);
                    }
                } else if (colour == Colour.Black && startY == 4
                        && (opponent.getLastX() == startX - 1
                        || opponent.getLastX() == startX + 1)) {
                    if (nextY == opponent.getLastY()) {
                        actions.add(Action.EnPassant);
                    }
                }
            }
        }
        return actions;
    }

    public boolean checkRepeat(Player opponent, Board board, int startR, int startC, int nextR, int nextC) {
        Piece toMove = board.getBoard()[startR][startC];
        if (lastMoved.equals(toMove) && lastX == nextR && lastY == nextC) {
            this.updateRepeat(false);
        } else {
            this.updateRepeat(true);
            opponent.updateRepeat(true);
        }
        return repeatedMoves == 3 && opponent.repeatedMoves == 3;
    }
    
    public void updateRepeat(boolean reset){
        if(reset){
            repeatedMoves = 0;
        }else{
            repeatedMoves++;
        }
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

    public void setupAttacks(Board board, int startR, int startC) {
        Piece toExamine = board.getBoard()[startR][startC];
        int[][] examinedAttacks = toExamine.attacks(board, startR, startC);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // could have attacks be changed to boolean
                if (examinedAttacks[i][j] > 0) {
                    attacks[i][j]++;
                }
            }
        }
    }

    private void updateAttacks(Board board, int startR, int startC, int nextR, int nextC) {
        Piece toExamine = board.getBoard()[startR][startC];
        int[][] examinedAttacks = toExamine.attacks(board, startR, startC);
        int[][] nextAttacks = toExamine.attacks(board, nextR, nextC);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // could have attacks be changed to boolean
                if (examinedAttacks[i][j] > 0) {
                    attacks[i][j]--;
                }
                // could have attacks be changed to boolean
                if (nextAttacks[i][j] > 0) {
                    attacks[i][j]++;
                }
            }
        }
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

    public int[][] getAttacks() {
        return attacks;
    }

}
