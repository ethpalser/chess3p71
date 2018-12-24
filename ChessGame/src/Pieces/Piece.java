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
public abstract class Piece {

    public final PieceType piece;
    public final Colour colour;
    public final int weight;

    public Piece(PieceType piece, Colour colour, int weight) {
        this.piece = piece;
        this.colour = colour;
        this.weight = weight;
    }

    public abstract int heuristic(Board board, int indexX, int indexY);

    public abstract int threats(Board board, int indexX, int indexY);

    public abstract boolean[][] validMoves(Board board, int indexX, int indexY);

    public abstract String printToBoard(); // prints piece board

    public int isThreatened(Board board, int indexX, int indexY) {
        Piece[][] currentBoard = board.getBoard();
        int threatCounter = 0; // increment if opponent, decrement if own
        // Pawn - White
        if (indexY >= 1) {
            if (indexX >= 1) {
                threatCounter += checkPawnWhite(
                        currentBoard, indexX - 1, indexY - 1);
            }
            if (indexX <= 6) {
                threatCounter += checkPawnWhite(
                        currentBoard, indexX + 1, indexY - 1);

            }
        }
        // Pawn - Black
        if (indexY <= 6) {
            if (indexX >= 1) {
                threatCounter += checkPawnBlack(
                        currentBoard, indexX - 1, indexY - 1);
            }
            if (indexX <= 6) {
                threatCounter += checkPawnBlack(
                        currentBoard, indexX + 1, indexY + 1);

            }
        }
        // Queen + Rook
        int result;
        // check left
        for (int x = indexX - 1; x > 0; x--) {
            result = checkPiece(currentBoard, x, indexY,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // check right
        for (int x = indexX + 1; x < 8; x++) {
            result = checkPiece(currentBoard, x, indexY,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // check up
        for (int y = indexY - 1; y < 0; y--) {
            result = checkPiece(currentBoard, indexX, y,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // check down
        for (int y = indexY + 1; y < 8; y++) {
            result = checkPiece(currentBoard, indexX, y,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // Queen + Bishop
        int posx = indexX;
        int posy = indexY;
        // diagonal top-left
        while (posx < 0 && posy < 0) {
            posx--;
            posy--;
            result = checkPiece(currentBoard, posx, posy,
                    PieceType.Queen, PieceType.Bishop);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal top-right
        while (posx > 8 && posy < 0) {
            posx++;
            posy--;
            result = checkPiece(currentBoard, posx, posy,
                    PieceType.Queen, PieceType.Bishop);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-left
        while (posx < 0 && posy > 8) {
            posx--;
            posy++;
            result = checkPiece(currentBoard, posx, posy,
                    PieceType.Queen, PieceType.Bishop);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        posx = indexX;
        posy = indexY;
        // diagonal bottom-right
        while (posx > 8 && posy > 8) {
            posx++;
            posy++;
            result = checkPiece(currentBoard, posx, posy,
                    PieceType.Queen, PieceType.Bishop);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // Knight
        // top-left
        if (indexX >= 2 && indexY >= 1) {
            threatCounter += checkPiece(
                    currentBoard, indexX - 2, indexY - 1, PieceType.Knight);
        }
        if (indexX >= 1 && indexY >= 2) {
            threatCounter += checkPiece(
                    currentBoard, indexX - 1, indexY - 2, PieceType.Knight);
        }
        // top-right
        if (indexX >= 2 && indexY <= 6) {
            threatCounter += checkPiece(
                    currentBoard, indexX - 2, indexY + 1, PieceType.Knight);
        }
        if (indexX >= 1 && indexY <= 5) {
            threatCounter += checkPiece(
                    currentBoard, indexX - 1, indexY + 2, PieceType.Knight);
        }
        // bottom-left
        if (indexX <= 5 && indexY >= 1) {
            threatCounter += checkPiece(
                    currentBoard, indexX + 2, indexY - 1, PieceType.Knight);
        }
        if (indexX <= 6 && indexY >= 2) {
            threatCounter += checkPiece(
                    currentBoard, indexX + 1, indexY - 2, PieceType.Knight);
        }
        // bottom-right
        if (indexX <= 5 && indexY <= 6) {
            threatCounter += checkPiece(
                    currentBoard, indexX + 2, indexY + 1, PieceType.Knight);
        }
        if (indexX <= 6 && indexY <= 5) {
            threatCounter += checkPiece(
                    currentBoard, indexX + 1, indexY + 2, PieceType.Knight);
        }
        // King
        if (indexY >= 1) {
            // top
            threatCounter += checkPiece(
                    currentBoard, indexX, indexY - 1, PieceType.King);
            if (indexX >= 1) {
                // top-left
                threatCounter += checkPiece(
                        currentBoard, indexX - 1, indexY - 1, PieceType.King);
            }
            if (indexX <= 6) {
                // top-right
                threatCounter += checkPiece(
                        currentBoard, indexX + 1, indexY - 1, PieceType.King);
            }
        }
        if (indexY <= 6) {
            // bottom
            threatCounter += checkPiece(
                    currentBoard, indexX, indexY + 1, PieceType.King);
            if (indexX >= 1) {
                // bottom-left
                threatCounter += checkPiece(
                        currentBoard, indexX - 1, indexY + 1, PieceType.King);
            }
            if (indexX <= 6) {
                // bottom-right
                threatCounter += checkPiece(
                        currentBoard, indexX + 1, indexY + 1, PieceType.King);
            }
        }
        if (indexX >= 1) {
            // right
            threatCounter += checkPiece(
                    currentBoard, indexX - 1, indexY, PieceType.King);
        }
        if (indexY <= 6) {// left
            threatCounter += checkPiece(
                    currentBoard, indexX + 1, indexY, PieceType.King);
        }
        return threatCounter;
    }

    // isThreatened considers both of these worst cases
    /*
    public boolean isForked(Board board, int indexX, int indexY) {
        return isThreatened(board, indexX, indexY) > 1;
    }

    public boolean isPinned(Board board, int indexX, int indexY) {
        return false;
    }
     */
    public boolean isOppositeColour(Piece otherPiece) {
        if (this.colour == Colour.White) {
            return this.colour == Colour.Black;
        } else {
            return this.colour == Colour.White;
        }
    }

    private int checkPiece(Piece[][] board, int indexX, int indexY,
            PieceType required) {
        Piece toExamine = board[indexX][indexY];
        if (toExamine != null) {
            if (toExamine.piece == required) {
                if (this.isOppositeColour(toExamine)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

    private int checkPiece(Piece[][] board, int indexX, int indexY,
            PieceType required1, PieceType required2) {
        Piece toExamine = board[indexX][indexY];
        if (toExamine != null) {
            if (toExamine.piece == required1
                    || toExamine.piece == required2) {
                if (this.isOppositeColour(toExamine)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

    private int checkPawnWhite(Piece[][] board, int indexX, int indexY) {
        Piece toExamine = board[indexX][indexY];
        if (toExamine != null) {
            if (toExamine.piece == PieceType.Pawn) {
                if (this.colour == Colour.White
                        && this.isOppositeColour(toExamine)) {
                    return 1;
                } else if (this.colour == Colour.Black
                        && !this.isOppositeColour(toExamine)) {
                    return -1;
                }
            }
        }
        return 0;
    }

    private int checkPawnBlack(Piece[][] board, int indexX, int indexY) {
        // ensure that for the current piece the piece to check is previous
        Piece toExamine = board[indexX][indexY];
        if (toExamine != null) {
            if (toExamine.piece == PieceType.Pawn) {
                if (this.colour == Colour.Black
                        && this.isOppositeColour(toExamine)) {
                    return 1;
                } else if (this.colour == Colour.White
                        && !this.isOppositeColour(toExamine)) {
                    return -1;
                }
            }
        }
        return 0;
    }

}
