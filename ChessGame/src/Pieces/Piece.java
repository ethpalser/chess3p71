package Pieces;

import Game.Board;
import Game.Colour;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author E
 */
public abstract class Piece {
    
    public static PieceType piece;
    public static Colour colour;
    public static int weight;
    
    public Piece(Colour c){
        colour = c;
    }
    
    // Part 2 & 3 Ethan
    public abstract int heuristic(Board board);
    public abstract int threats(Board board);
    // May not need for part 2 & 3 or may change
    public boolean isForked(Board board){
        return false;
    }
    public boolean isPinned(Board board){
        return false;
    }
    // May used for part 6 or other
    // May be useful earlier but can be costly depedning on implementation
    public abstract boolean[][] validMoves();
    public abstract String printToBoard(); // prints piece board
    
    // Other notable actions that need to be checked
    // hasMoved for King and both Rooks
    // canEnPassant for Pawns (did it move 2 this turn)
    
}
