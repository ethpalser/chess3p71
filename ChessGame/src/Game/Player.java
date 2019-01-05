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

    public Board movePiece(Player opponent, Board board, int startR, int startC, int nextR, int nextC, ArrayList<Action> actions, Piece promotionTo, boolean castleKingSide) {
        Piece toMove = board.getBoard()[startR][startC];
        if (toMove.colour != colour) {
            return board; // nothing happens to board state or player states
        }
        if (toMove.validMoves(opponent, board, startR, startC)[nextR][nextC] == false) {
            return board; // invalid action
        } else {
            // May need to have repeated check before move is considered (maybe in board)
            // valid action occurs
            lastMoved = toMove;
            lastX = nextR;
            lastY = nextC;
            if(toMove.piece == PieceType.Rook || toMove.piece == PieceType.King){
                board.getBoard()[startR][startC].modifySpecial();
            }
            else if(toMove.piece == PieceType.Pawn &&
                    (toMove.colour == Colour.White && nextR == startR - 2 ||
                    toMove.colour == Colour.Black && nextR == startR + 2)){
                board.getBoard()[startR][startC].modifySpecial();
            }
            // gets a copy of the board to modify
            Board nextBoard = board;
            nextBoard.getBoard()[nextR][nextC] = nextBoard.getBoard()[startR][startC];
            nextBoard.getBoard()[startR][startC] = null;

            nextBoard.printToLog(toMove, nextR, nextC, actions, promotionTo, castleKingSide);
            return nextBoard; // returns new board state after applying move
        }
    }

    public boolean checkRepeat(Board board, int startR, int startC, int nextR, int nextC) {
        Piece toMove = board.getBoard()[startR][startC];
        if (lastMoved.equals(toMove) && lastX == nextR && lastY == nextC && repeatedMoves < 3) {
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

    public void setupAttacks(Board board, int startR, int startC){
        Piece toExamine = board.getBoard()[startR][startC];
        int[][] examinedAttacks = toExamine.attacks(board, startR, startC);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                // could have attacks be changed to boolean
               if(examinedAttacks[i][j] > 0){
                   attacks[i][j]++;
               }
            }
        }
    }
    
    private void updateAttacks(Board board, int startR, int startC, int nextR, int nextC){
        Piece toExamine = board.getBoard()[startR][startC];
        int[][] examinedAttacks = toExamine.attacks(board, startR, startC);
        int[][] nextAttacks = toExamine.attacks(board, nextR, nextC);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                // could have attacks be changed to boolean
               if(examinedAttacks[i][j] > 0){
                   attacks[i][j]--;
               }
                // could have attacks be changed to boolean
               if(nextAttacks[i][j] > 0){
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
    
    public int[][] getAttacks(){
        return attacks;
    }
            

}
