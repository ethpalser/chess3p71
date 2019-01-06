
package Game;

import java.util.ArrayList;

/**
 *
 * @author E
 */
public class Log {
    
    public final boolean isMaster;
    private ArrayList<Move> moves;
    
    public Log(boolean master){
        isMaster = master; // master tracks actions (master only) and moves
        moves = new ArrayList<>(10);
    }
    
    public void addMove(Move validMove){
        moves.add(validMove);
    }
    
    // this only removes from stack and does not undo the board state
    public Move undoMove(){
        if(moves.size() < 1){
            return null;
        }
        Move removed = moves.remove(moves.size()-1);
        return removed;
    }
}
