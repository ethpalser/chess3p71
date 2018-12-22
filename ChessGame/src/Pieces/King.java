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

    public King(Colour c) {
        super(c);
        piece = PieceType.King;
        weight = 13; // will change
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
    public boolean[][] validMoves() {
        return null;
    }

    @Override
    public String printToBoard() {
        return "K";
    }
    
    
    
}
