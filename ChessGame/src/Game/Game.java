/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Pieces.Piece;

/**
 *
 * @author E
 */
public class Game {
    
    private final Player white;
    private final Player black;
    private Board currentBoard;
    private Colour currentTurn; 
    int repeatedMoves; // counts to 3 (draw), resets if either doesn't repeat
    
        
    public Game(){
        white = new Player(Colour.White);
        black = new Player(Colour.Black);
        currentBoard = new Board();
        currentTurn = Colour.White;
    }
    
    public Player getWhite(){
        return white;
    }
    
    public Player getBlack(){
        return black;
    }
    
    public Board getBoard(){
        return currentBoard;
    }
    
    public Board nextBoard(String log){
        // Parse log input
        // Check if piece colour matches piece colour on board
        // Check if piece type matches piece type on board
        // Convert boardX and boardY into usable indecies for array access
        // Call other nextBoard method
        return currentBoard;
    }
    
    public Board nextBoard(int startX, int startY, int nextX, int nextY){
        Piece toMove = currentBoard.getBoard()[startX][startY];
        if(currentTurn == Colour.White){
            // ensure a move is not applied on wrong turn
            if(toMove.colour == Colour.Black){
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            return white.movePiece(currentBoard, startX, startY, nextX, nextY);
        }
        else{
            if(toMove.colour == Colour.White){
                return currentBoard;
            }
            // will check if move is valid, otherwise does nothing
            return black.movePiece(currentBoard, startX, startY, nextX, nextY);
        }
    }
    
    public void setBoard(Board board){
        // Ensure the next board is changed using nextBoard before setting
        // changes turn to next player
        currentTurn = currentTurn == Colour.Black ? Colour.White : Colour.Black;
        currentBoard = board;
    }
    
    // need a means to efficiently check this for main loop
    public boolean checkmate(Player opponent){
        return false;
    }
    
    //check repeated moves
    public boolean checkRepeat(Board board, int startX, int startY, int nextX, int nextY) {
        Player p = black;
        if(currentTurn == Colour.White)
            p = white;
        Piece toMove = board.getBoard()[startX][startY];
        if (p.getLastMoved().equals(toMove) && p.getLastX() == nextX && p.getLastY() == nextY && repeatedMoves < 3) {
            repeatedMoves++;
        }else{
            repeatedMoves = 0;
        }
        return repeatedMoves == 6;
    }
}
