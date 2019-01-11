package Pieces;

import Game.Board;
import Game.Colour;
import Game.Player;

/**
 * This class represents the Rook piece
 *
 * @author E
 */
public class Rook extends Piece {

    public boolean canMove;
    private boolean hasMoved;

    public Rook(Colour colour) {
        super(PieceType.Rook, colour, 5);
        hasMoved = false;
    }

    @Override
    public int threats(Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        int threatened = 0;
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            return threatened;
        }
        // check up
        for (int x = row - 1; x >= 0; x--) {
            toExamine = currentBoard[x][column];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        // check down
        for (int x = row + 1; x < 8; x++) {
            toExamine = currentBoard[x][column];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        // check left
        for (int y = column - 1; y >= 0; y--) {
            toExamine = currentBoard[row][y];
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
                break;
            }
        }
        // check right
        for (int y = column + 1; y < 8; y++) {
            toExamine = currentBoard[row][y];
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
          if (row < 0 || row > 7 || column < 0 || column > 7) {
            return attacked;
        }
        // check up
        for (int x = row - 1; x >= 0; x--) {
            toExamine = currentBoard[x][column];
            attacked[x][column]++;
            if (toExamine != null) {
                attacked[x][column]--;
                break;
            }
        }
        // check down
        for (int x = row + 1; x < 8; x++) {
            toExamine = currentBoard[x][column];
            attacked[x][column]++;
            if (toExamine != null) {
                attacked[x][column]--;
                break;
            }
        }
        // check left
        for (int y = column - 1; y >= 0; y--) {
            toExamine = currentBoard[row][y];
            attacked[row][y]++;
            if (toExamine != null) {
                attacked[row][y]--;
                break;
            }
        }
        // check right
        for (int y = column + 1; y < 8; y++) {
            toExamine = currentBoard[row][y];
            attacked[row][y]++;
            if (toExamine != null) {
                attacked[row][y]--;
                break;
            }
        }
        return attacked;
    }

    @Override
    public boolean[][] validMoves(Player opponent, Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        // reset to false and check
        canMove = false;
        boolean[][] validPositions = new boolean[8][8];
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            return validPositions;
        }
        // check up
        for (int x = row - 1; x >= 0; x--) {
            toExamine = currentBoard[x][column];
            validPositions[x][column] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[x][column] = false;
                    break;
                }
                canMove = true;
                break;
            }
            canMove = true;
        }
        // check down
        for (int x = row + 1; x < 8; x++) {
            toExamine = currentBoard[x][column];
            validPositions[x][column] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[x][column] = false;
                    break;
                }
                canMove = true;
                break;
            }
            canMove = true;
        }
        // check left
        for (int y = column - 1; y >= 0; y--) {
            toExamine = currentBoard[row][y];
            validPositions[row][y] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[row][y] = false;
                    break;
                }
                canMove = true;
                break;
            }
            canMove = true;
        }
        // check right
        for (int y = column + 1; y < 8; y++) {
            toExamine = currentBoard[row][y];
            validPositions[row][y] = true;
            if (toExamine != null) {
                if (this.isOppositeColour(toExamine)) {
                    validPositions[row][y] = false;
                    break;
                }
                canMove = true;
                break;
            }
            canMove = true;
        }
        return validPositions;
    }

    @Override
    public boolean validSpecial() {
        return !hasMoved;
    }

    @Override
    public void modifySpecial() {
        hasMoved = true;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2656" : "\u265C";
    }

    @Override
    public String printToLog() {
        return "R";
    }

    @Override
    public boolean getCanMove() {
        return canMove;
    }

}
