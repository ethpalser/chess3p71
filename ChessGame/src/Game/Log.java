package Game;

import java.util.ArrayList;

/**
 * This class is used to keep track of all moves (done my any piece) on the
 * board
 *
 * @author Ethan Palser, Param Jansari
 */
public class Log {

    public final boolean isMaster; // check if this is the master log file
    private ArrayList<Move> moves; // list of moves

    public Log(boolean master) {
        isMaster = master; // master tracks actions (master only) and moves
        moves = new ArrayList<>(10);
    }

    public Log() {
        this(false);
    }

    /**
     * This method adds a move to the list of moves to be logged
     *
     * @param validMove
     */
    public void addMove(Move validMove) {
        moves.add(validMove);
    }

    /**
     * This method removes the last from added to the list of moves to be logged
     *
     * @return
     */
    public Move undoMove() {
        if (moves.size() < 1) {
            return null;
        }
        Move removed = moves.remove(moves.size() - 1);
        return removed;
    }
}
