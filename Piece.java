/**
 * 
 * 
 * @author Ethan Palser, Param Jansari
 */
public interface Piece {
    // Part 1
    public int getValue();
    public int getColour(); // could be managed elsewhere but is needed
    // Part 2 & 3 Ethan
    public int getWeigth();
    public int heuristic(Board board);
    public int threatsWeighted(Board board);
    // May not need for part 2 & 3 or may change
    public boolean isForked(Board board);
    public boolean isPinned(Board board);
    // May used for part 6 or other
    // May be useful earlier but can be costly depedning on implementation
    public boolean[][] validMoves();
    
    // Other notable actions that need to be checked
    // hasMoved for King and both Rooks
    // canEnPassant for Pawns (did it move 2 this turn)
    
}
