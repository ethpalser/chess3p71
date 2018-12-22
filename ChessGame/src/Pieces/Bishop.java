/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Game.Board;
import Game.Colour;

/**
 *
 * @author Param
 */
public class Bishop extends Piece{

    public Bishop(Colour colour) {
        super(PieceType.Bishop, colour, 3);
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
       return this.colour == Colour.Black ? "bB" : "wB";
    }
    
}
