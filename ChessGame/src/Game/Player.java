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
    private int lastR, lastC;
    private int[][] attacks;
    private boolean lostGame;
    

    public Player(Colour c) {
        colour = c;
        piecesCentred = 0;
        lastR = -1; // defaults that indicate no piece
        lastC = -1; // defaults that indicate no piece
        attacks = new int[8][8];
        lostGame = false;
    }

    public Board movePiece(Player opponent, Board board, Move move, Piece promotionTo){
        return movePiece(opponent, board, move.startR, move.startC, move.nextR, move.nextC, promotionTo);
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
        if (toMove == null || toMove.colour != colour) {
            return board; // nothing happens to board state or player states
        }
        if (toMove.validMoves(opponent, board, startR, startC)[nextR][nextC] == false) {
            return board; // invalid action
        } else {
            // May need to have repeated check before move is considered (maybe in board)
            // valid action occurs
            lastMoved = board.getBoard()[startR][startC];
            lastR = nextR;
            lastC = nextC;
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

    public ArrayList<Action> actionTaken(Player opponent, Board board, Move move){
        return actionTaken(opponent, board, move.startR, move.startC, move.nextR, move.nextC);
    }
    
    public ArrayList<Action> actionTaken(
            Player opponent,
            Board board,
            int startR,
            int startC,
            int nextR,
            int nextC) {
        Piece pieceMoved = board.getBoard()[startR][startC];
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
            if (pieceAt.getColour() != colour) {
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
            if (lastMoved.piece == PieceType.Pawn && lastMoved.validSpecial()) {
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

    public boolean checkRepeat(Player opponent, Board board, Move move){
        return checkRepeat(opponent, board, move.startR, move.startC, move.nextR, move.nextC);
    }
    
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
                if (board.getBoard()[i][j].colour == colour) {
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

    private void updateAttacks(Board board, Move move){
        updateAttacks(board, move.startR, move.startC, move.nextR, move.nextC);
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

    public int getLastR() {
        return lastR;
    }

    public int getLastC() {
        return lastC;
    }

    public Piece getLastMoved() {
        return lastMoved;
    }

    public int[][] getAttacks() {
        return attacks;
    }

    public void setLoss(){
        lostGame = true;
    }
    
    public boolean getLoss(){
        return lostGame;
    }
}
