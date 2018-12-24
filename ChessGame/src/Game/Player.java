package Game;

import Pieces.Piece;
import Pieces.Pawn;

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
    
    public Player(Colour c){
        colour = c;
    }
    
    public Board movePiece(Board board, int startX, int startY, int nextX, int nextY){
        Piece toMove = board.getBoard()[startX][startY];
        if(toMove.colour != colour){
            return board; // nothing happens to board state or player states
        }
        if(toMove.validMoves(board, startX, startY)[nextX][nextY] == false){
            return board; // invalid action
        }
        else{
            // valid action occurs
            lastMoved = toMove;
            // gets a copy of the board to modify
            Board nextBoard = board;
            nextBoard.getBoard()[nextX][nextY] = nextBoard.getBoard()[startX][startY];
            nextBoard.getBoard()[startX][startY] = null;
            return nextBoard; // returns new board state after applying move
        }
    }
    
}
