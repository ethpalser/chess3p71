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
    int[][] attacks;

    public Player(Colour c) {
        colour = c;
        piecesCentred = 0;
        lastX = -1; // defaults that indicate no piece
        lastY = -1; // defaults that indicate no piece
        attacks = new int[8][8];
    }

    public Board movePiece(Board board, int startX, int startY, int nextX, int nextY, ArrayList<Action> actions, Piece promotionTo, boolean castleKingSide) {
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
            if(toMove.piece == PieceType.Rook || toMove.piece == PieceType.King){
                board.getBoard()[startX][startY].modifySpecial();
            }
            else if(toMove.piece == PieceType.Pawn &&
                    (toMove.colour == Colour.White && nextX == startX - 2 ||
                    toMove.colour == Colour.Black && nextX == startX + 2)){
                board.getBoard()[startX][startY].modifySpecial();
            }
            // gets a copy of the board to modify
            Board nextBoard = board;
            nextBoard.getBoard()[nextX][nextY] = nextBoard.getBoard()[startX][startY];
            nextBoard.getBoard()[startX][startY] = null;

            nextBoard.printToLog(toMove, nextX, nextY, actions, promotionTo, castleKingSide);
            return nextBoard; // returns new board state after applying move
        }
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

    public void setupAttacks(Board board, int startX, int startY){
        Piece toExamine = board.getBoard()[startX][startY];
        int[][] examinedAttacks = toExamine.attacks(board, startX, startY);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                // could have attacks be changed to boolean
               if(examinedAttacks[i][j] > 0){
                   attacks[i][j]++;
               }
            }
        }
    }
    
    private void updateAttacks(Board board, int startX, int startY, int nextX, int nextY){
        Piece toExamine = board.getBoard()[startX][startY];
        int[][] examinedAttacks = toExamine.attacks(board, startX, startY);
        int[][] nextAttacks = toExamine.attacks(board, nextX, nextY);
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

}
