package Pieces;

import Game.Board;
import Game.Colour;
import Game.Player;

/**
 * This class represents the Queen piece
 *
 * @author Param
 */
public class Queen extends Piece {

    public boolean canMove;

    public Queen(Colour colour) {
        super(PieceType.Queen, colour, 9);
    }

    @Override
    public int threats(Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        Piece toExamine;
        int threatened = 0;
        // check up
        if (row > 0) {
            for (int x = row - 1; x >= 0; x--) {
                toExamine = currentBoard[x][column];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                    break;
                }
            }
        }
        // check down
        if (row < 7) {
            for (int x = row + 1; x <= 7; x++) {
                toExamine = currentBoard[x][column];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                    break;
                }
            }
        }
        // check left
        if (column > 0) {
            for (int y = column - 1; y >= 0; y--) {
                toExamine = currentBoard[row][y];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                    break;
                }
            }
        }
        // check right
        if (column < 7) {
            for (int y = column + 1; y <= 7; y++) {
                toExamine = currentBoard[row][y];
                if (toExamine != null) {
                    if (this.isOppositeColour(toExamine)) {
                        threatened += toExamine.weight;
                    }
                    break;
                }
            }
        }
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
        while (posx > 0 && posy < 7) {
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
        while (posx < 7 && posy > 0) {
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
        while (posx < 7 && posy < 7) {
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
        if (row > 0) {
            // check up
            for (int x = row - 1; x > 0; x--) {
                toExamine = currentBoard[x][column];
                attacked[x][column]++;
                if (toExamine != null) {
                    attacked[x][column]--;
                    break;
                }
            }
        }
        if (row < 7) {
            // check down
            for (int x = row + 1; x < 8; x++) {
                toExamine = currentBoard[x][column];
                attacked[x][column]++;
                if (toExamine != null) {
                    attacked[x][column]--;
                    break;
                }
            }
        }
        if (column > 0) {
            // check left
            for (int y = column - 1; y > 0; y--) {
                toExamine = currentBoard[row][y];
                attacked[row][y]++;
                if (toExamine != null) {
                    attacked[row][y]--;
                    break;
                }
            }
        }
        if (column < 7) {
            // check right
            for (int y = column + 1; y < 8; y++) {
                toExamine = currentBoard[row][y];
                attacked[row][y]++;
                if (toExamine != null) {
                    attacked[row][y]--;
                    break;
                }
            }
        }
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
        while (posx > 0 && posy < 7) {
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
        while (posx < 7 && posy > 0) {
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
        while (posx < 7 && posy < 7) {
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
        // reset to false and check
        canMove = false;
        boolean[][] validPositions = new boolean[8][8];
        if (row > 0) {
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
        }
        if (row < 7) {
            // check down
            for (int x = row + 1; x <= 7; x++) {
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
        }
        if (column > 0) {
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
        }
        if (column < 7) {
            // check right
            for (int y = column + 1; y <= 7; y++) {
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
        }
        int posx = row;
        int posy = column;
        // diagonal top-left
        while (posx > 0 && posy > 0) {
            toExamine = currentBoard[posx--][posy--];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                    break;
                }
                canMove = true;
                break;
            }
            canMove = true;
        }
        posx = row;
        posy = column;
        // diagonal top-right
        while (posx > 0 && posy < 7) {
            toExamine = currentBoard[posx--][posy++];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                    break;
                }
                canMove = true;
                break;
            }
            canMove = true;
        }
        posx = row;
        posy = column;
        // diagonal bottom-left
        while (posx < 7 && posy > 0) {
            toExamine = currentBoard[posx++][posy--];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
                    break;
                }
                canMove = true;
                break;
            }
            canMove = true;
        }
        posx = row;
        posy = column;
        // diagonal bottom-right
        while (posx < 7 && posy < 7) {
            toExamine = currentBoard[posx++][posy++];
            validPositions[posx][posy] = true;
            if (toExamine != null) {
                if (!this.isOppositeColour(toExamine)) {
                    validPositions[posx][posy] = false;
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
        return false;
    }

    @Override
    public void modifySpecial() {
        // nothing
    }

    @Override
    public String printToBoard() {
        return this.colour == Colour.White ? "\u2655" : "\u265B";
    }

    @Override
    public String printToLog() {
        return "Q";
    }

    @Override
    public boolean getCanMove() {
        return canMove;
    }
}
