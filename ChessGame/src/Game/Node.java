
package Game;

import java.util.ArrayList;

/**
 * This class is used to store a move that is performed during a chess game
 * and a value associated with that action, which the value is determined
 * by the move itself or cumulative actions for the GameTree A* expansion.<&nbsp>
 * This has a variable amount of children which depends on how many valid actions
 * can occur at a given board state.
 * 
 * @author Ep16fb
 */
public class Node {
    
    public final Move move;
    public final int value;
    public ArrayList<Node> children;
    
    public Node(Move move, int value){
        this.move = move;
        this.value = value;
        children = new ArrayList<>();
    }
    
    public Node(){
        this(null, 0);
    }
    
}
