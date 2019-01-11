package Pieces;

import Game.Board;
import Game.Colour;
import Game.Move;
import Game.Player;

/**
 * This class represents a chess piece and all it can do on the board.
 *
 * @author Ethan Palser, Param Jansari
 */
public abstract class Piece {

    public final PieceType piece; // what kind of piece it is
    public final Colour colour; // what colour is it
    public final int weight; // what's its value
    private Move bestMove; // the best move it can do from its current position

    /**
     * Creates Chess piece
     *
     * @param piece
     * @param colour
     * @param weight
     */
    public Piece(PieceType piece, Colour colour, int weight) {
        this.piece = piece;
        this.colour = colour;
        this.weight = weight;
    }

    /**
     * This method checks how good the position of the piece on the board is.
     *
     * @param board
     * @param row
     * @param column
     * @return heuristic value
     */
    public int heuristic(Board board, int row, int column) {
        int heurVal = 0;
        Piece toExamine = board.getBoard()[row][column];
        if (toExamine != null) {
            heurVal += toExamine.weight;
            heurVal += (threats(board, row, column)
                    - this.isThreatened(board, row, column));
        }
        return heurVal;
    }

    /**
     * This method checks how good the position of the piece on the board is.
     *
     * @param board
     * @param move
     * @return
     */
    public int heuristic(Board board, Move move) {
        if (move == null) {
            return -9999;
        }
        return heuristic(board, move.nextR, move.nextC);
    }

    /**
     * This method calculates the number of threats the piece has based on its
     * position
     *
     * @param board
     * @param row
     * @param column
     * @return
     */
    public abstract int threats(Board board, int row, int column);

    /**
     * This method calculates the number of attacks the piece can make based on
     * its position
     *
     * @param board
     * @param row
     * @param column
     * @return
     */
    public abstract int[][] attacks(Board board, int row, int column);

    /**
     * This method calculate all the valid positions the piece can move to based
     * on its current position
     *
     * @param opponent
     * @param board
     * @param row
     * @param column
     * @return
     */
    public abstract boolean[][] validMoves(Player opponent, Board board, int row, int column);

    /**
     * This method checks if a piece has a special rule that applies to it Such
     * a promotion for pawns, or castling for king/rook
     *
     * @return
     */
    public abstract boolean validSpecial();

    /**
     * This method executes the special rule of the piece, in the case it has a
     * special move i.e. execute under condition a move confirms (pawn) or
     * invalidates (king/rook) its special action
     */
    public abstract void modifySpecial();

    /**
     * This method prints piece to board
     *
     * @return
     */
    public abstract String printToBoard(); // prints piece board

    /**
     * This method prints piece to log
     *
     * @return
     */
    public abstract String printToLog();

    /**
     * This method calculates the number of pieces currently threaten it
     *
     * @param board
     * @param row
     * @param column
     * @return
     */
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
        if (column <= 6 && column > 0) {
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
        if (row > 0) {
            for (int x = row - 1; x >= 0; x--) {
                result = checkPiece(currentBoard, x, column,
                        PieceType.Queen, PieceType.Rook);
                // piece encountered
                if (result != 0) {
                    threatCounter += result;
                    break;
                }
            }
        }
        // check right
        if (row < 7) {
            for (int x = row + 1; x <= 7; x++) {
                result = checkPiece(currentBoard, x, column,
                        PieceType.Queen, PieceType.Rook);
                // piece encountered
                if (result != 0) {
                    threatCounter += result;
                    break;
                }
            }
        }
        // check up
        if (column > 0) {
            for (int y = column; y <= 0; y--) {
                result = checkPiece(currentBoard, row, y,
                        PieceType.Queen, PieceType.Rook);
                // piece encountered
                if (result != 0) {
                    threatCounter += result;
                    break;
                }
            }
        }
        // check down
        if (column < 7) {
            for (int y = column + 1; y <= 7; y++) {
                result = checkPiece(currentBoard, row, y,
                        PieceType.Queen, PieceType.Rook);
                // piece encountered
                if (result != 0) {
                    threatCounter += result;
                    break;
                }
            }
        }
        // Queen + Bishop
        int posx = row;
        int posy = column;
        // diagonal top-left
        while (posx > 0 && posy > 0) {
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
        while (posx < 7 && posy > 0) {
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
        while (posx > 0 && posy < 7) {
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
        while (posx < 7 && posy < 7) {
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
        if (row >= 1) {
            // top
            threatCounter += checkPiece(
                    currentBoard, row - 1, column, PieceType.King);
            if (column >= 1) {
                // top-left
                threatCounter += checkPiece(
                        currentBoard, row - 1, column - 1, PieceType.King);
            }
            if (column <= 6) {
                // top-right
                threatCounter += checkPiece(
                        currentBoard, row - 1, column + 1, PieceType.King);
            }
        }
        if (row <= 6) {
            // bottom
            threatCounter += checkPiece(
                    currentBoard, row + 1, column, PieceType.King);
            if (column >= 1) {
                // bottom-left
                threatCounter += checkPiece(
                        currentBoard, row + 1, column - 1, PieceType.King);
            }
            if (column <= 6) {
                // bottom-right
                threatCounter += checkPiece(
                        currentBoard, row + 1, column + 1, PieceType.King);
            }
        }
        if (column >= 1) {
            // left
            threatCounter += checkPiece(
                    currentBoard, row, column - 1, PieceType.King);
        }
        if (column <= 6) {
            // right
            threatCounter += checkPiece(
                    currentBoard, row, column + 1, PieceType.King);
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
    /**
     * This method is used to check if a piece is opposite to the current piece
     *
     * @param otherPiece
     * @return
     */
    public boolean isOppositeColour(Piece otherPiece) {
        if (this.colour == Colour.White) {
            return this.colour == Colour.Black;
        } else {
            return this.colour == Colour.White;
        }
    }

    /**
     * This method checks all horizonal, vertical and knight-shaped positions,
     * and if that spot corresponds to the PieceType required
     *
     * @param board
     * @param row
     * @param column
     * @param required
     * @return
     */
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

    /**
     * This method checks all horizonal, vertical and knight-shaped positions,
     * and if that spot corresponds to the PieceType required or required2
     *
     * @param board
     * @param row
     * @param column
     * @param required1
     * @param required2
     * @return
     */
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

    /**
     * This method checks if white pawn is at location on board
     *
     * @param board
     * @param row
     * @param column
     * @return
     */
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

    /**
     * This method checks if black pawn is at location on board
     *
     * @param board
     * @param row
     * @param column
     * @return
     */
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

    /**
     * This method calculate the best move the piece can make based on its
     * current position
     *
     * @param opponent
     * @param board
     * @param row
     * @param column
     * @return
     */
    public Move calcBestMove(Player opponent, Board board, int row, int column) {
        boolean[][] validMoves = this.validMoves(opponent, board, row, column);
        Move currentBest = null;
        for (int i = 0; i < validMoves.length; i++) {
            for (int j = 0; j < validMoves[0].length; j++) {
                if (validMoves[i][j]) {
                    Move move = new Move(row, column, i, j);
                    if (bestMove == null) {
                        currentBest = move;
                        bestMove = currentBest;
                    } else if (heuristic(board, move.nextR, move.nextC)
                            > heuristic(board, bestMove.nextR, bestMove.nextC)) {
                        currentBest = move;
                        bestMove = move;
                    }
                }
            }
        }
        return currentBest;
    }

    /**
     * This method returns the best move which the piece can perform
     *
     * @return
     */
    public Move getBestMove() {
        return bestMove;
    }

    /**
     * This method determines if the piece can move
     *
     * @return
     */
    public abstract boolean getCanMove();

    /**
     * This method compares this piece to another piece
     *
     * @param p
     * @return
     */
    public boolean equals(Piece p) {
        return this.colour == p.colour && this.piece == p.piece;
    }

    public void printValidMoves(boolean[][] positions) {
        System.out.println("+---+---+---+---+---+---+---+---+");
        for (int i = 0; i < positions.length; i++) {
            System.out.print("|");
            for (int j = 0; j < positions[i].length; j++) {
                if (positions[i][j] == false) {
                    System.out.print("   |");
                } else {
                    System.out.print(" T |");
                }
            }
            System.out.println("\n+---+---+---+---+---+---+---+---+");
        }
    }

}
