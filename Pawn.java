import java.util.*;
public class Pawn extends Piece {

    private boolean stationaryStatus; // true if pawn has not moved yet
    private boolean pawnJustMovedTwoSquares; // true if pawn just moved two squares forward

    Pawn(double value, int[] square, int color) {
        super(value, square, color);
        stationaryStatus = true;
        pawnJustMovedTwoSquares = false;
    }

    protected boolean getStationaryStatus() {
        return stationaryStatus;
    }

    protected boolean didPawnJustMoveTwoSquares() {
        return pawnJustMovedTwoSquares;
    }

    protected void movePawn() {
        stationaryStatus = false;
    }

    protected void moveTwoSquares() {
        pawnJustMovedTwoSquares = true;
    }

    protected void changeJustMoveStatus() {
        pawnJustMovedTwoSquares = false;
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {
        Piece selectedPawn = positionNode.getMyPieces().get(getSquare());

        int directionMultiplier = 1;
        if (getColor() == BLACK) {
            directionMultiplier = -1;
        }

        // single square forward
        int[] checkSquare = getSquare();
        checkSquare[1] += directionMultiplier;
        if (validSquare(getSquare()) && !positionNode.getMyPieces().containsKey(checkSquare) && !positionNode.getOpponentPieces().containsKey(checkSquare)) {
            getPossibleMoves().add(checkSquare);
        }

        // double squares forward
        checkSquare = getSquare();
        checkSquare[1] += 2*directionMultiplier;
        if (!getStationaryStatus() && validSquare(getSquare()) && !positionNode.getMyPieces().containsKey(checkSquare) && !positionNode.getOpponentPieces().containsKey(checkSquare)) {
            getPossibleMoves().add(checkSquare);
        }

        // check for captures
        checkSquare = getSquare();
        checkSquare[1] += directionMultiplier;
        checkSquare[0] += directionMultiplier;
        if (validSquare(getSquare())) {
            insertToList(selectedPawn, controlledSquares.get(checkSquare));
            if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
                getPossibleMoves().add(checkSquare);
                getCaptures().add(checkSquare);
            }
        }

        checkSquare = getSquare();
        checkSquare[1] += directionMultiplier;
        checkSquare[0] -= directionMultiplier;
        if (validSquare(getSquare())) {
            insertToList(selectedPawn, controlledSquares.get(checkSquare));
            if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
                getPossibleMoves().add(checkSquare);
                getCaptures().add(checkSquare);
            }
        }

        // check for en passant
        checkSquare = getSquare();
        checkSquare[0] += directionMultiplier;
        if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
            Pawn opponentPawn = (Pawn) positionNode.getOpponentPieces().get(checkSquare);
            if (opponentPawn.pawnJustMovedTwoSquares) {
                getPossibleMoves().add(checkSquare);
                getCaptures().add(checkSquare);
            }
        }

        checkSquare = getSquare();
        checkSquare[0] -= directionMultiplier;
        if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
            Pawn opponentPawn = (Pawn) positionNode.getOpponentPieces().get(checkSquare);
            if (opponentPawn.pawnJustMovedTwoSquares) {
                getPossibleMoves().add(checkSquare);
                getCaptures().add(checkSquare);
            }
        }
    
    }
    
}