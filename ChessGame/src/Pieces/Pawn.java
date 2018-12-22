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
public class Pawn extends Piece{

    public static PieceType piece;
    public static Colour colour;
    public static int weight;
    
    public Pawn(Colour c) {
        super(c);
        piece = PieceType.Pawn;
        weight = 1;
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
      return colour == Colour.White ? "wP" : "bP";
    }
    
    public boolean canEnPassant(Pawn pawn){
        return false; // need to check if target pawn has moved 2 last turn
    }
    
}
