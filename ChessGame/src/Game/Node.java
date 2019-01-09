/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;

/**
 *
 * @author E
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
