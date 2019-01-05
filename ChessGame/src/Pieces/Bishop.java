/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pieces;

import Game.Board;
import Game.Colour;
import Game.Player;

/**
 *
 * @author Param
 */
public class Bishop extends Piece {

    public Bishop(Colour colour) {
        super(PieceType.Bishop, colour, 3);
    }

    @Override
    public int heuristic(Board board, int row, int column) {
        return threats(board, row, column)
                + this.isThreatened(board, row, column);
    }

    @Override
    public int threats(Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        int threatened = 0;
        int posx = row;
        int posy = column;
        // diagonal top-left
        while (posx > 0 && posy > 0) {
            toExamine = currentBoard[posx--][posy--];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal top-right
        while (posx < 8 && posy > 0) {
            toExamine = currentBoard[posx--][posy++];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal bottom-left
        while (posx > 0 && posy < 8) {
            toExamine = currentBoard[posx++][posy--];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal bottom-right
        while (posx < 8 && posy < 8) {
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
    public int[][] attacks(Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        int[][] attacked = new int[8][8];
        int posx = row;
        int posy = column;
        // diagonal top-left
        while (posx > 0 && posy > 0) {
            toExamine = currentBoard[posx--][posy--];
            attacked[posx][posy]++;
            if (toExamine != null) {
                attacked[posx][posy]--;
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal top-right
        while (posx < 8 && posy > 0) {
            toExamine = currentBoard[posx--][posy++];
            attacked[posx][posy]++;
            if (toExamine != null) {
                attacked[posx][posy]--;
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal bottom-left
        while (posx > 0 && posy < 8) {
            toExamine = currentBoard[posx++][posy--];
            attacked[posx][posy]++;
            if (toExamine != null) {
                attacked[posx][posy]--;
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal bottom-right
        while (posx < 8 && posy < 8) {
            toExamine = currentBoard[posx++][posy++];
            attacked[posx][posy]++;
            if (toExamine != null) {
                attacked[posx][posy]--;
                break;
            }
        }
        return attacked;
    }

    @Override
    public boolean[][] validMoves(Player opponent, Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        boolean[][] validPositions = new boolean[8][8];
        int posx = row;
        int posy = column;
        // diagonal top-left
        while (posx > 0 && posy > 0) {
            toExamine = currentBoard[posx--][posy--];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal top-right
        while (posx < 8 && posy > 0) {
            toExamine = currentBoard[posx--][posy++];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal bottom-left
        while (posx > 0 && posy < 8) {
            toExamine = currentBoard[posx++][posy--];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        posx = row;
        posy = column;
        // diagonal bottom-right
        while (posx < 8 && posy < 8) {
            toExamine = currentBoard[posx++][posy++];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                }
                break;
            }
        }
        return validPositions;
    }
    
    @Override
    public boolean validSpecial(){
        return false;
    }
    
    @Override
    public void modifySpecial(){
        // nothing
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2657" : "\u265D";
    }
    
    @Override
    public String printToLog(){
        return "B";
    }

}
