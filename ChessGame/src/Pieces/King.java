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
public class King extends Piece{

    public static PieceType piece;
    public static Colour colour;
    public static int weight;

    public King(Colour colour) {
        super(PieceType.King, colour, Integer.MAX_VALUE);
    }
    
    @Override
    public int heuristic(Board board) {
        return 0;
    }

    @Override
    public int threats(Board board) {
        return 0;
    }

    @Override
    public boolean[][] validMoves(int boardX, char boardY) {
        return null;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "wK" : "bK";
    }
    
    public boolean castle(Rook rook){
        return false; // need to check if rook has moved
    }
}
