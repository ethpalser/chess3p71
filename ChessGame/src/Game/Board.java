package Game;


import Pieces.Piece;

/**
 * 
 * 
 * @author Ethan Palser, Param Jansari
 */
public class Board {
    
    private Piece board[][];
    private int heuristicVal; // used to check value later without calculating again
    
    public Board(){
        // Empty
    }
    
    public int heristic(Colour playerColour){
        Piece piece;
        int result = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                piece = board[i][j];
                if(piece != null && piece.colour == playerColour){
                    result += board[i][j].heuristic(this, i, j);
                }
            }
        }
        heuristicVal = result;
        return result;
    }
        
    public void printBoard(){
            // Empty
    }
    
    public String printToLog(){
        return null;
    }
    
    public Piece[][] getBoard(){
        return board;
    }
    
    public static int boardToIndexX(int boardX){
        switch(boardX){
            case 1:
                return 7; // Bottom of Board in White's persepective
            case 2:
                return 6;
            case 3:
                return 5;
            case 4:
                return 4;
            case 5:
                return 3;
            case 6: 
                return 2;
            case 7:
                return 1;
            case 8:
                return 0; // Top of Board in White's persepctive
            default:
                return -1;
        }
    }
    
    public static int boardToIndexY(char boardY){
        switch(boardY){
            case 'A':
                return 0; // Left of Board in White's persepective
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F': 
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7; // Right of Board in White's persepctive
            default:
                return -1;
        }
    }
    
    public static int indexToBoardX(int indexX){
        switch(indexX){
            case 0:
                return 8; // Top of Board in White's persepective
            case 1:
                return 7;
            case 2:
                return 6;
            case 3:
                return 5;
            case 4:
                return 4;
            case 5: 
                return 3;
            case 6:
                return 2;
            case 7:
                return 1; // Bottom of Board in White's persepctive
            default:
                return -1;
        }
    }
    
    public static char indexToBoardY(int indexY){
        switch(indexY){
            case 0:
                return 'A'; // Left of Board in White's persepective
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5: 
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H'; // Right of Board in White's persepctive
            default:
                return '-';
        }
    }
}
