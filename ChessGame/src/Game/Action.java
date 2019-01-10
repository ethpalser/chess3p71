
package Game;

/**
 * This is used to differentiate the different actions that a piece can perform.
 * 
 * @author Ep16fb
 */
public enum Action {
    Move,
    Capture,
    Promotion,
    Check,
    Checkmate,
    Stalemate,
    CastleKingSide,
    CastleQueenSide,
    EnPassant
}
