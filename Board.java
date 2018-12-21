/**
 * 
 * 
 * @author Ethan Palser, Param Jansari
 */
public class Board {
    
    private Piece board[][];
    private int[] pieceCount; // should be size 7, for weighting by piece quicker
    private int heuristicVal; // used to check value later without calculating again
    
    public Board(){
        pieceCount = new int[7];
    }
    
    // Part 3 Ethan
    /**
     * Uses each piece heuristic to calculate total value.
     * 
     * @return 
     */
    public int heristic(){
        int result = 0;
        heuristicVal = result;
        return result;
    }
    
    // Part 2 Param
    public void printBoard(){
        // Empty
    }
    
}
