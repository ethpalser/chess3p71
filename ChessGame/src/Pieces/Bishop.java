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
public class Bishop extends Piece {

    public Bishop(Colour colour) {
        super(PieceType.Bishop, colour, 3);
    }

    @Override
    public int heuristic(Board board, int indexX, int indexY) {
        return threats(board, indexX, indexY)
                + this.isThreatened(board, indexX, indexY);
    }

    @Override
    public int threats(Board board, int indexX, int indexY) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        int threatened = 0;
        int posx = indexX;
        int posy = indexY;
        // diagonal top-left
        while (posx < 0 && posy < 0) {
            toExamine = currentBoard[posx--][posy--];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal top-right
        while (posx > 8 && posy < 0) {
            toExamine = currentBoard[posx++][posy--];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-left
        while (posx < 0 && posy > 8) {
            toExamine = currentBoard[posx--][posy++];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-right
        while (posx > 8 && posy > 8) {
            toExamine = currentBoard[posx++][posy++];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        return threatened;
    }

    @Override
    public boolean[][] validMoves(Board board, int indexX, int indexY) {
        return null;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.Black ? "bB" : "wB";
    }

}
