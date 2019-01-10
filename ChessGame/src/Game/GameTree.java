
package Game;

import Pieces.Piece;
import java.util.ArrayList;

/**
 *
 * @author E
 */
public class GameTree {

    public final int depth;
    private Log moves; // may not have been used in build tree
    private Node root;

    public GameTree(Game currentGame, int searchDepth) {
        depth = searchDepth;
        moves = new Log();
        root = new Node();
        root.children = this.buildTree(currentGame, searchDepth, 0, 0, 0, 0);
    }

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
                        int boardValue = game.getBoard().heristic(game.getCurrentTurn()) + parentVal;
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
        }
        return nodeList;
    }

    // find max value of nodes in current depth of tree
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

    // find min value of nodes in current depth of tree
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
