/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Pieces.Piece;
import java.util.ArrayList;

/**
 *
 * @author E
 */
public class Move {
    
    public final int startR, startC, nextR, nextC;
    private String logMove;
    private String logMoveWithActions;
    private Piece captured;
    
    public Move(Piece captured, String logWithActions, String log, int startRow, int startColumn, int nextRow, int nextColumn){
        this(captured, log, startRow, startColumn, nextRow, nextColumn);
        logMoveWithActions = logWithActions;
    }
    
    public Move(Piece captured, String log, int startRow, int startColumn, int nextRow, int nextColumn){
        this(startRow, startColumn, nextRow, nextColumn);
        logMove = log;
    }   
    
    public Move(Piece captured, int startRow, int startColumn, int nextRow, int nextColumn){
        this(startRow, startColumn, nextRow, nextColumn);
        this.captured = captured;
    }
    
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
