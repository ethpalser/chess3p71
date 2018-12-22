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
    
    public Board movePiece(Piece toMove, Board prevBoard){
        return null;
    }
    
}
