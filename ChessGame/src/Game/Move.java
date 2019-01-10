
package Game;

import Pieces.Piece;
import java.util.ArrayList;

/**
 * This class stores the information related to performing an action on the
 * board that can be used in place of managing the individual start and next
 * positions a piece moves on the board.<&nbsp>This class is also used to
 * store the full action as a log string in chess notation, and it stores the
 * piece it captured for undoing actions.
 * 
 * @author Ep16fb
 */
public class Move {
    
    public final int startR, startC, nextR, nextC;
    private String logMove;
    private String logMoveWithActions;
    private Piece captured;
    
    // Constructor
    public Move(Piece captured, String logWithActions, String log,
            int startRow, int startColumn, int nextRow, int nextColumn){
        this(captured, log, startRow, startColumn, nextRow, nextColumn);
        logMoveWithActions = logWithActions;
    }
    
    // Constructor
    public Move(Piece captured, String log,
            int startRow, int startColumn, int nextRow, int nextColumn){
        this(startRow, startColumn, nextRow, nextColumn);
        logMove = log;
    }   
    
    // Constructor
    public Move(Piece captured,
            int startRow, int startColumn, int nextRow, int nextColumn){
        this(startRow, startColumn, nextRow, nextColumn);
        this.captured = captured;
    }
    
    // Constructor
    public Move(int startRow, int startColumn, int nextRow, int nextColumn){
        startR = startRow;
        startC = startColumn;
        nextR = nextRow;
        nextC = nextColumn;
        captured = null;
    }
    
    public String getMove(){
        String move = logMove;
        if(move == null || move.equals("")){
            move = startR + "" + startC + " " + nextR + "" + nextC;
        }
        return move;
    }
    
    public String getLog(){
        String log = logMoveWithActions;
        if(log == null || log.equals("")){
            // could be adjusted to include capture
            log = this.getMove();
        }
        return log;
    }
    
    public Piece getCaptured(){
        return captured;
    }
}
