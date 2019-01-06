package Pieces;

import Game.Board;
import Game.Colour;
import Game.Move;
import Game.Player;

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
    private Move bestMove;

    public Piece(PieceType piece, Colour colour, int weight) {
        this.piece = piece;
        this.colour = colour;
        this.weight = weight;
    }

    public abstract int heuristic(Board board, int row, int column);

    public abstract int threats(Board board, int row, int column);

    // could have attacks be changed to boolean[][] instead of int[][]
    public abstract int[][] attacks(Board board, int row, int column);

    public abstract boolean[][] validMoves(Player opponent, Board board, int row, int column);

    // check if an action it can perform a special action
    public abstract boolean validSpecial();

    // execute under condition a move confirms (pawn) or invalidates (king/rook) its special action
    public abstract void modifySpecial();

    public abstract String printToBoard(); // prints piece board

    public abstract String printToLog(); // prints piece to log

    public int isThreatened(Board board, int row, int column) {
        Piece[][] currentBoard = board.getBoard();
        int threatCounter = 0; // increment if opponent, decrement if own
        // Pawn - White
        if (column >= 1) {
            if (row >= 1) {
                threatCounter += checkPawnWhite(
                        currentBoard, row - 1, column - 1);
            }
            if (row <= 6) {
                threatCounter += checkPawnWhite(
                        currentBoard, row + 1, column - 1);

            }
        }
        // Pawn - Black
        if (column <= 6) {
            if (row >= 1) {
                threatCounter += checkPawnBlack(
                        currentBoard, row - 1, column - 1);
            }
            if (row <= 6) {
                threatCounter += checkPawnBlack(
                        currentBoard, row + 1, column + 1);

            }
        }
        // Queen + Rook
        int result;
        // check left
        for (int x = row - 1; x > 0; x--) {
            result = checkPiece(currentBoard, x, column,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // check right
        for (int x = row + 1; x < 8; x++) {
            result = checkPiece(currentBoard, x, column,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // check up
        for (int y = column - 1; y < 0; y--) {
            result = checkPiece(currentBoard, row, y,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // check down
        for (int y = column + 1; y < 8; y++) {
            result = checkPiece(currentBoard, row, y,
                    PieceType.Queen, PieceType.Rook);
            // piece encountered
            if (result != 0) {
                threatCounter += result;
                break;
            }
        }
        // Queen + Bishop
        int posx = row;
        int posy = column;
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
        posx = row;
        posy = column;
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
        posx = row;
        posy = column;
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
        posx = row;
        posy = column;
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
        if (row >= 2 && column >= 1) {
            threatCounter += checkPiece(
                    currentBoard, row - 2, column - 1, PieceType.Knight);
        }
        if (row >= 1 && column >= 2) {
            threatCounter += checkPiece(
                    currentBoard, row - 1, column - 2, PieceType.Knight);
        }
        // top-right
        if (row >= 2 && column <= 6) {
            threatCounter += checkPiece(
                    currentBoard, row - 2, column + 1, PieceType.Knight);
        }
        if (row >= 1 && column <= 5) {
            threatCounter += checkPiece(
                    currentBoard, row - 1, column + 2, PieceType.Knight);
        }
        // bottom-left
        if (row <= 5 && column >= 1) {
            threatCounter += checkPiece(
                    currentBoard, row + 2, column - 1, PieceType.Knight);
        }
        if (row <= 6 && column >= 2) {
            threatCounter += checkPiece(
                    currentBoard, row + 1, column - 2, PieceType.Knight);
        }
        // bottom-right
        if (row <= 5 && column <= 6) {
            threatCounter += checkPiece(
                    currentBoard, row + 2, column + 1, PieceType.Knight);
        }
        if (row <= 6 && column <= 5) {
            threatCounter += checkPiece(
                    currentBoard, row + 1, column + 2, PieceType.Knight);
        }
        // King
        if (column >= 1) {
            // top
            threatCounter += checkPiece(
                    currentBoard, row, column - 1, PieceType.King);
            if (row >= 1) {
                // top-left
                threatCounter += checkPiece(
                        currentBoard, row - 1, column - 1, PieceType.King);
            }
            if (row <= 6) {
                // top-right
                threatCounter += checkPiece(
                        currentBoard, row + 1, column - 1, PieceType.King);
            }
        }
        if (column <= 6) {
            // bottom
            threatCounter += checkPiece(
                    currentBoard, row, column + 1, PieceType.King);
            if (row >= 1) {
                // bottom-left
                threatCounter += checkPiece(
                        currentBoard, row - 1, column + 1, PieceType.King);
            }
            if (row <= 6) {
                // bottom-right
                threatCounter += checkPiece(
                        currentBoard, row + 1, column + 1, PieceType.King);
            }
        }
        if (row >= 1) {
            // right
            threatCounter += checkPiece(
                    currentBoard, row - 1, column, PieceType.King);
        }
        if (column <= 6) {// left
            threatCounter += checkPiece(
                    currentBoard, row + 1, column, PieceType.King);
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

    private int checkPiece(Piece[][] board, int row, int column,
            PieceType required) {
        Piece toExamine = board[row][column];
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

    private int checkPiece(Piece[][] board, int row, int column,
            PieceType required1, PieceType required2) {
        Piece toExamine = board[row][column];
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

    private int checkPawnWhite(Piece[][] board, int row, int column) {
        Piece toExamine = board[row][column];
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

    private int checkPawnBlack(Piece[][] board, int row, int column) {
        // ensure that for the current piece the piece to check is previous
        Piece toExamine = board[row][column];
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
    
    public void calcBestMove (Player opponent, Board board, int row, int column){
        boolean[][] validMoves = this.validMoves(opponent, board, row, column);
        for(int i = 0; i < validMoves.length; i++){
            for(int j = 0; j < validMoves[0].length; j++){
                if(validMoves[i][j]){
                    Move move = new Move(row, column, i, j);
                    if (heuristic(board, move.nextR, move.nextC) > heuristic(board, bestMove.nextR, bestMove.nextC)){
                        bestMove = move;
                    }
                }
            }
        }
    }

    public Move getBestMove() {
        return bestMove;
    }

    public abstract boolean getCanMove();
    
    public boolean equals(Piece p) {
        return this.colour == p.colour && this.piece == p.piece;
    }

}
