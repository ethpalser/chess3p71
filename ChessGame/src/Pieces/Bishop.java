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

    public Bishop(Colour c) {
        super(c);
        Piece.piece = PieceType.Bishop;
        Piece.weight = 3;
    }

    @Override
    public int heuristic(Board board) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int threats(Board board) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean[][] validMoves() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String printToBoard() {
       return Piece.colour == Colour.Black ? "bB" : "wB";
    }
    
}
