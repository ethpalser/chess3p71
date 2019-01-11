package Game;

import Pieces.Piece;
import Pieces.PieceType;
import java.util.ArrayList;

/**
 * This class represents the player who will be playing chess
 *
 * @author Ethan Palser, Param Jansari
 */
public class Player {

    private final Colour colour; // Black or White
    private int piecesCentred; // number of pieces in center of board
    private int repeatedMoves; // counts to 3 (draw), resets if either doesn't repeat
    private Piece lastMoved; // the last piece moved
    private int lastR, lastC; // previous postion of the last piece moved
    private int[][] attacks; // where player can attack
    private boolean lostGame; // if they lost the game

    public Player(Colour c) {
        colour = c;
        piecesCentred = 0;
        lastR = -1; // defaults that indicate no piece
        lastC = -1; // defaults that indicate no piece
        attacks = new int[8][8];
        lostGame = false;
    }

    /**
     * This method moves the piece on the board
     *
     * @param opponent
     * @param board
     * @param move
     * @param promotionTo
     * @return
     */
    public Board movePiece(Player opponent, Board board, Move move, Piece promotionTo) {
        return movePiece(opponent, board, move.startR, move.startC, move.nextR, move.nextC, promotionTo);
    }

    /**
     * This method moves the piece on the board
     *
     * @param opponent
     * @param board
     * @param startR
     * @param startC
     * @param nextR
     * @param nextC
     * @param promotionTo
     * @return
     */
    public Board movePiece(
            Player opponent,
            Board board,
            int startR,
            int startC,
            int nextR,
            int nextC,
            Piece promotionTo) {
        Board next = new Board(board);
        Piece toMove = next.getBoard()[startR][startC];
        if (toMove == null || toMove.colour != colour) {
            System.out.println("Failed to Move Piece");
            return board; // nothing happens to board state or player states
        }
        boolean[][] validPositions = toMove.validMoves(opponent, next, startR, startC);
        if (validPositions[nextR][nextC] == false) {
            System.out.println("Invalid Move");
            return board; // invalid action
        } else {
            // May need to have repeated check before move is considered (maybe in board)
            // valid action occurs
            lastMoved = next.getBoard()[startR][startC];
            lastR = nextR;
            lastC = nextC;
            // check if rook or king moves, if so castling not possible unless performed this move
            if (toMove.piece == PieceType.Rook || toMove.piece == PieceType.King) {
                next.getBoard()[startR][startC].modifySpecial();
            } // check if pawn moved and if it can be en passant after
            else if (toMove.piece == PieceType.Pawn
                    && (toMove.colour == Colour.White && nextR == startR - 2
                    || toMove.colour == Colour.Black && nextR == startR + 2)) {
                next.getBoard()[startR][startC].modifySpecial();
            }
            // get a list of actions that will occur
            ArrayList<Action> actions = actionTaken(opponent, next, startR, startC, nextR, nextC);
            // update player state of attacks
            this.updateAttacks(next, startR, startC, nextR, nextC);
            // gets a copy of the board to modify
            next.getBoard()[nextR][nextC] = next.getBoard()[startR][startC];
            next.getBoard()[startR][startC] = null;
            // output results to board
            next.printToLog(toMove, nextR, nextC, actions, promotionTo);
            System.out.println("Move Complete!");
            return next; // returns new board state after applying move
        }
    }

    /**
     * This method determines what action did the player perform i.e. Castle, En
     * Passant, Capture ... etc.
     *
     * @param opponent
     * @param board
     * @param move
     * @return
     */
    public ArrayList<Action> actionTaken(Player opponent, Board board, Move move) {
        return actionTaken(opponent, board, move.startR, move.startC, move.nextR, move.nextC);
    }

    /**
     * This method determines what action did the player perform i.e. Castle, En
     * Passant, Capture ... etc.
     *
     * @param opponent
     * @param board
     * @param startR
     * @param startC
     * @param nextR
     * @param nextC
     * @return
     */
    public ArrayList<Action> actionTaken(
            Player opponent,
            Board board,
            int startR,
            int startC,
            int nextR,
            int nextC) {
        //System.out.println(startR + "  " + startC);
        Piece pieceMoved = board.getBoard()[startR][startC];
        //System.out.println(pieceMoved == null);
        Piece pieceAt = board.getBoard()[nextR][nextC];
        ArrayList<Action> actions = new ArrayList<>();
        int backRow = colour == Colour.White ? 7 : 0;
        //Move
        if (pieceAt == null) {
            actions.add(Action.Move);
            //Castling (assume move has been validated already)
            if (pieceMoved.piece == PieceType.King) {
                // can castle
                if (pieceMoved.validSpecial()) {
                    // check queen side
                    if (nextR == backRow && nextC == 2) {
                        // check if rook has moved
                        if (board.getBoard()[backRow][0] != null
                                && board.getBoard()[backRow][0].validSpecial()) {
                            actions.add(Action.CastleQueenSide);
                        }
                    } // check king side
                    else if (nextR == backRow && nextC == 6) {
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
            if (pieceAt.colour != colour) {
                actions.add(Action.Capture);
                //Checkmate
                if (pieceAt.piece == PieceType.King) {
                    actions.add(Action.Checkmate);
                    opponent.setLoss();
                }
            } else {
                // Invalid action
            }
        }
        if (pieceMoved.piece == PieceType.Pawn) {
            //Promotion
            if (colour == Colour.White && nextR == 0 || colour == Colour.Black && nextR == 7) {
                actions.add(Action.Promotion);
            }
            //check if opponent last moved pawn by two spaces
            Piece lastMoved = opponent.getLastMoved();
            if (lastMoved != null && lastMoved.piece == PieceType.Pawn && lastMoved.validSpecial()) {
                //checks if my pawn is in right position and moves to right space
                if (colour == Colour.White && startR == 3
                        && (opponent.getLastC() == startC - 1
                        || opponent.getLastC() == startC + 1)) {
                    if (nextC == opponent.getLastC()) {
                        actions.add(Action.EnPassant);
                    }
                } else if (colour == Colour.Black && startR == 4
                        && (opponent.getLastC() == startC - 1
                        || opponent.getLastC() == startC + 1)) {
                    if (nextC == opponent.getLastC()) {
                        actions.add(Action.EnPassant);
                    }
                }
            }
        }
        return actions;
    }

    /**
     * This method check if the Player has repeated the same move 3 times
     *
     * @param opponent
     * @param board
     * @param move
     * @return
     */
    public boolean checkRepeat(Player opponent, Board board, Move move) {
        return checkRepeat(opponent, board, move.startR, move.startC, move.nextR, move.nextC);
    }

    /**
     * This method check if the Player has repeated the same move 3 times
     *
     * @param opponent
     * @param board
     * @param startR
     * @param startC
     * @param nextR
     * @param nextC
     * @return
     */
    public boolean checkRepeat(Player opponent, Board board, int startR, int startC, int nextR, int nextC) {
        Piece toMove = board.getBoard()[startR][startC];
        if (lastMoved.equals(toMove) && lastR == nextR && lastC == nextC) {
            this.updateRepeat(false);
        } else {
            this.updateRepeat(true);
            opponent.updateRepeat(true);
        }
        return repeatedMoves == 3 && opponent.repeatedMoves == 3;
    }

    /**
     * This method updates the counter for the number of times the player has
     * repeated a move
     *
     * @param reset
     */
    public void updateRepeat(boolean reset) {
        if (reset) {
            repeatedMoves = 0;
        } else {
            repeatedMoves++;
        }
    }

    /**
     * This method calculates the number of pieces the player has in the center
     * of the board
     *
     * @param board
     * @return
     */
    public int piecesCentered(Board board) {
        for (int i = 2; i < 6; i++) {
            for (int j = 2; j < 6; j++) {
                if (board.getBoard()[i][j].colour == colour) {
                    piecesCentred++;
                }
            }
        }
        return piecesCentred;
    }

    /**
     * This method determines where the player can attack
     *
     * @param board
     * @param startR
     * @param startC
     */
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

    /**
     * This method updates where the player can attack
     *
     * @param board
     * @param move
     */
    private void updateAttacks(Board board, Move move) {
        updateAttacks(board, move.startR, move.startC, move.nextR, move.nextC);
    }

    /**
     * This method updates where the player can attack
     *
     * @param board
     * @param startR
     * @param startC
     * @param nextR
     * @param nextC
     */
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

    /**
     * This method returns last row position of the last piece moved by player
     *
     * @return
     */
    public int getLastR() {
        return lastR;
    }

    /**
     * This method returns last column position of the last piece moved by
     * player
     *
     * @return
     */
    public int getLastC() {
        return lastC;
    }

    /**
     * This method returns the last piece moved by player
     *
     * @return
     */
    public Piece getLastMoved() {
        return lastMoved;
    }

    /**
     * This method returns where the player can attack
     *
     * @return
     */
    public int[][] getAttacks() {
        return attacks;
    }

    /**
     * This method gives the loss to the player
     */
    public void setLoss() {
        lostGame = true;
    }

    /**
     * This method determines if the player has lost
     *
     * @return
     */
    public boolean getLoss() {
        return lostGame;
    }
}
