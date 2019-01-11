package Pieces;

import Game.Board;
import Game.Colour;
import Game.Player;

/**
 * This class represents the Pawn piece
 *
 * @author E
 */
public class Pawn extends Piece {

    public boolean canMove;
    public boolean hasMovedTwo;

    public Pawn(Colour colour) {
        super(PieceType.Pawn, colour, 1);
        hasMovedTwo = false;
    }

    @Override
    public int threats(Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        int threatened = 0;
        if (colour == Colour.White && row >= 1) {
            if (column >= 1) {
                toExamine = currentBoard[row - 1][column - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
            if (column <= 6) {
                toExamine = currentBoard[row - 1][column + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        } else if (colour == Colour.Black && row <= 6) {
            if (column >= 1) {
                toExamine = currentBoard[row + 1][column - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
            if (column <= 6) {
                toExamine = currentBoard[row + 1][column + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    threatened += toExamine.weight;
                }
            }
        }
        return threatened;
    }

    @Override
    public int[][] attacks(Board board, int row, int column) {
        int[][] attacked = new int[8][8];
        if (colour == Colour.White && row >= 1) {
            // check if there are pieces that can be taken
            if (column >= 1) {
                attacked[row - 1][column - 1]++;

            }
            if (column <= 6) {
                attacked[row - 1][column + 1]++;

            }
        } else if (colour == Colour.Black && row <= 6) {
            // check if there are pieces that can be taken
            if (column >= 1) {
                attacked[row + 1][column - 1]++;

            }
            if (column <= 6) {
                attacked[row + 1][column + 1]++;

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
        if (colour == Colour.White && row >= 1) {
            // check if it can move up
            toExamine = currentBoard[row - 1][column];
            if (toExamine == null) {
                validPositions[row - 1][column] = true;
                canMove = true;
            }
            // check if there are pieces that can be taken
            if (column >= 1) {
                // check if there are pieces that can be taken
                toExamine = currentBoard[row - 1][column - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[row - 1][column - 1] = true;
                    canMove = true;
                }
            }
            if (column <= 6) {
                toExamine = currentBoard[row - 1][column + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[row - 1][column + 1] = true;
                    canMove = true;
                }
            }
            // check if it can move up 2
            if (row == 6) {
                toExamine = currentBoard[row - 2][column];
                if (toExamine == null && currentBoard[row - 1][column] == null) {
                    validPositions[row - 2][column] = true;
                    canMove = true;
                }
            } // check if it can perform en passant
            else if (row == 3) {
                // use opponent info
                Piece lastMoved = opponent.getLastMoved();
                if (lastMoved.piece == PieceType.Pawn && lastMoved.validSpecial()) {
                    //checks if my pawn is in right position and moves to right space
                    if (opponent.getLastC() == column - 1
                            || opponent.getLastC() == column + 1) {
                        validPositions[row - 1][opponent.getLastC()] = true;
                        canMove = true;
                    }
                }
            }
        } else if (colour == Colour.Black && row <= 6) {
            // check if it can move up
            toExamine = currentBoard[row + 1][column];
            if (toExamine == null) {
                validPositions[row + 1][column] = true;
                canMove = true;
            }
            // check if there are pieces that can be taken
            if (column >= 1) {
                toExamine = currentBoard[row + 1][column - 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[row + 1][column - 1] = true;
                    canMove = true;
                }
            }
            if (column <= 6) {
                // check if there are pieces that can be taken
                toExamine = currentBoard[row + 1][column + 1];
                if (toExamine != null && isOppositeColour(toExamine)) {
                    validPositions[row + 1][column + 1] = true;
                    canMove = true;
                }
            }
            // check if it can move up 2
            if (row == 1) {
                toExamine = currentBoard[row + 2][column];
                if (toExamine == null && currentBoard[row + 1][column] == null) {
                    validPositions[row + 2][column] = true;
                    canMove = true;
                }
            } // check if it can perform en passant
            else if (row == 4) {
                // use opponent info
                Piece lastMoved = opponent.getLastMoved();
                if (lastMoved.piece == PieceType.Pawn && lastMoved.validSpecial()) {
                    //checks if my pawn is in right position and moves to right space
                    if (opponent.getLastC() == column - 1
                            || opponent.getLastC() == column + 1) {
                        validPositions[row + 1][opponent.getLastC()] = true;
                        canMove = true;
                    }
                }
            }
        }
        return validPositions;
    }

    @Override
    public boolean validSpecial() {
        // has moved two, thus can be en passant (other conditions elsewhere)
        return hasMovedTwo;
    }

    @Override
    public void modifySpecial() {
        // only call method if piece confirmed to move two
        hasMovedTwo = true;
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2659" : "\u265F";
    }

    @Override
    public String printToLog() {
        return "";
    }

    @Override
    public boolean getCanMove() {
        return canMove;
    }
}
