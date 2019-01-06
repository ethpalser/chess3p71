/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author E
 */
public class Move {
    
    public final int startR, startC, nextR, nextC;
    private String logMove;
    private String logMoveWithActions;
    
    public Move(String logWithActions, String log, int startRow, int startColumn, int nextRow, int nextColumn){
        this(log, startRow, startColumn, nextRow, nextColumn);
        logMoveWithActions = logWithActions;
    }
    
    public Move(String log, int startRow, int startColumn, int nextRow, int nextColumn){
        this(startRow, startColumn, nextRow, nextColumn);
        logMove = log;
    }
    
    public Move(int startRow, int startColumn, int nextRow, int nextColumn){
        startR = startRow;
        startC = startColumn;
        nextR = nextRow;
        nextC = nextColumn;
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
            log = this.getMove();
        }
        return log;
    }
}
