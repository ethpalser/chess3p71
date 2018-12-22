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
    
    private Player white;
    private Player black;
    private Board currentBoard;
    private Colour currentTurn;
        
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
    
    public Board nextBoard(Piece toMove){
        if(currentTurn == Colour.White){
            if(toMove.colour == Colour.Black){
                
            }
            currentTurn = Colour.Black;
            return white.movePiece(toMove, currentBoard);
        }
        else{
            currentTurn = Colour.White;
            return black.movePiece(toMove, currentBoard);
        }
    }
    
    public void setBoard(Board board){
        currentBoard = board;
    }
}
