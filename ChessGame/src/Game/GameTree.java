
package Game;

import Pieces.Piece;
import java.util.ArrayList;

/**
 * This class is used to perform a game tree algorithm on a current game state
 * to determine the next best move to be performed by each player and using a
 * Min and Max approach to determine the best-worst case action.<&nbsp>This
 * class performs alpha-beta pruning to reduce the nodes expanded on to reduce
 * memory and improve performance, and this is doing iterative deepening search.
 * 
 * @author Ep16fb
 */
public class GameTree {

    public final int depth;
    public final Node root;

    public GameTree(Game currentGame, int searchDepth) {
        depth = searchDepth;
        root = new Node();
        root.children = this.buildTree(currentGame, searchDepth, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * This method uses the expanded tree that is created to search through
     * the series of moves performed to determine which action resulted in the
     * best performing at a defined search depth, determined when the tree was
     * built.
     * 
     * @param player
     * @param currentTurn
     * @param node
     * @param branchMove
     * @return 
     */
    public Node findBestMove(Colour player, Colour currentTurn, Node node, Move branchMove) {
        Colour nextTurn = currentTurn == Colour.White ? Colour.Black : Colour.White;
        // store nodes so max/min can be found among them
        ArrayList<Node> childNodes = new ArrayList<>();

        if (node.children.isEmpty()) {
            return node;
        }
        // if true, then maximize
        if (player == currentTurn) {
            for (Node child : node.children) {
                // only care about the move that will be applied next
                if (branchMove == null) {
                    childNodes.add(findBestMove(player, nextTurn, child, node.move));
                } else {
                    childNodes.add(findBestMove(player, nextTurn, child, branchMove));
                }
            }
            return findMax(childNodes); // returns leaf node with best results
        } // otherwise, minimize
        else {
            for (Node child : node.children) {
                // only care about the move that will be applied next
                if (branchMove == null) {
                    childNodes.add(findBestMove(player, nextTurn, child, node.move));
                } else {
                    childNodes.add(findBestMove(player, nextTurn, child, branchMove));
                }
            }
            return findMin(childNodes); // returns leaf node with worst results
        }
    }

    /**
     * This method builds a tree of nodes which contain moves, in order to trace
     * the series of action performed, and is performing alpha-beta pruning to
     * reduce the amount of nodes to expand on.
     * 
     * @param game
     * @param searchDepth
     * @param currentDepth
     * @param parentVal
     * @param branchMax
     * @param branchMin
     * @return 
     */
    private ArrayList<Node> buildTree(
            Game game,
            int searchDepth,
            int currentDepth,
            int parentVal,
            int branchMax,
            int branchMin) {
        Piece[][] currentBoard = game.getBoard().getBoard();
        ArrayList<Node> nodeList = new ArrayList<>(1);
        // Iterative Deepening Search (Expand each depth at a time)
        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                Piece piece = currentBoard[i][j];
                // only check best move for current player's pieces
                if (piece != null && piece.colour == game.getCurrentTurn()) {
                    // get best move of piece
                    Move best = piece.calcBestMove(game.getOpponent(), game.getBoard(), i, j);
                    // ensure that a valid move could be perfomed
                    if (best != null) {
                        // change game state
                        game.nextBoard(best);
                        int boardValue;
                        // maximize
                        if(currentDepth % 2 == 0){
                            boardValue = parentVal + game.getBoard().heristic(game.getCurrentTurn());
                        } // minimize
                        else{
                            boardValue = parentVal - game.getBoard().heristic(game.getCurrentTurn());
                        }
                        // alpha beta pruning (not adding branches that wont be considered)

                        // if depth is odd then parent nodes are max player,
                        // will opponent ignores values greater than max (wants to minimize)
                        // if depth is even then parent nodes are min player,
                        // will ignore values less than min (wants to maximize)
                        if ((currentDepth % 2 != 0 && boardValue <= branchMax)
                                || (currentDepth % 2 != 0 && boardValue >= branchMin)) {
                            // evaluate game state using A* (sum previous + current) algorithm might NOT use A*
                            nodeList.add(new Node(best, boardValue));
                        }
                        // undo move and appy next
                        game.undoMove(best);
                    }
                }
            }
        }
        // escape condition to ensure it doesn't go endlessly deeper
        if (currentDepth == searchDepth) {
            return nodeList;
        }
        // copy current game, may already have local copy as parameter (need to avoid modifying global copy)
        Game tempGame = game;
        // expand each node, Depth First (depth 2+)
        // note: may need to change so all of depth 2 is expanded instead of going deeper
        for (Node node : nodeList) {
            tempGame.nextBoard(node.move);
            node.children = buildTree(
                    tempGame,
                    searchDepth,
                    currentDepth + 1,
                    node.value,
                    findMax(nodeList).value,
                    findMin(nodeList).value);
            // avoid having the next node in list expand on previous node
            tempGame.undoMove(node.move);
        }
        return nodeList;
    }

    /**
     * This method looks at an array list of nodes, which is also the children
     * of the nodes, to determine the highest evaluated move performed.
     * 
     * @param nodes
     * @return 
     */
    private Node findMax(ArrayList<Node> nodes) {
        Node max = new Node(null, Integer.MIN_VALUE);
        if (nodes.isEmpty()) {
            return max;
        }
        for (Node node : nodes) {
            if (node.value > max.value) {
                max = node;
            }
        }
        return max;
    }

    /**
     * This method looks at an array list of nodes, which is also the children
     * of the nodes, to determine the lowest evaluated move performed.
     * 
     * @param nodes
     * @return 
     */
    private Node findMin(ArrayList<Node> nodes) {
        Node min = new Node(null, Integer.MIN_VALUE);
        if (nodes.isEmpty()) {
            return min;
        }
        for (Node node : nodes) {
            if (node.value > min.value) {
                min = node;
            }
        }
        return min;
    }
}
