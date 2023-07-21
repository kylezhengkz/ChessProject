import java.util.*;
public class Pawn extends Piece {

    private boolean stationaryStatus; // true if pawn has not moved yet
    private boolean pawnJustMovedTwoSquares; // true if pawn just moved two squares forward

    Pawn(double value, int square, int color) {
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
    protected void generateMoves(PositionNode positionNode, HashMap<Integer, List<Piece>> controlledSquares) {

        Piece selectedPawn = positionNode.getMyPieces().get(getSquare());

        int directionMultiplier = 1;
        if (getColor() == BLACK) {
            directionMultiplier = -1;
        }

        int checkSquare = 0; // init

        // single square forward
        if (validMove(getSquare(), UP*directionMultiplier)) {
            checkSquare = getSquare() + UP*directionMultiplier;
            if (!positionNode.getMyPieces().containsKey(checkSquare) && !positionNode.getOpponentPieces().containsKey(checkSquare)) {
                addPossibleMove(checkSquare);
            }
        }

        // double squares forward
        if (validMove(getSquare(), 2*UP*directionMultiplier)) {
            checkSquare = getSquare() + 2*UP*directionMultiplier;
            if (getStationaryStatus() && !positionNode.getMyPieces().containsKey(checkSquare) && !positionNode.getOpponentPieces().containsKey(checkSquare)) {
                addPossibleMove(checkSquare);
            }
        }

        // check for captures
        if (validMove(getSquare(), UP_RIGHT*directionMultiplier)) {
            checkSquare = getSquare() + UP_RIGHT*directionMultiplier;
            insertToList(selectedPawn, controlledSquares.get(checkSquare));
            if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
                addPossibleMove(checkSquare);
                addCapture(checkSquare);
            }
        }

        // check for captures
        if (validMove(getSquare(), UP_LEFT*directionMultiplier)) {
            checkSquare = getSquare() + UP_LEFT*directionMultiplier;
            insertToList(selectedPawn, controlledSquares.get(checkSquare));
            if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
                addPossibleMove(checkSquare);
                addCapture(checkSquare);
            }
        }

        // check for en passant
        if (validMove(getSquare(), LEFT)) {
            checkSquare += getSquare() + LEFT;
            if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
                Pawn opponentPawn = (Pawn) positionNode.getOpponentPieces().get(checkSquare);
                if (opponentPawn.pawnJustMovedTwoSquares) {
                    addPossibleMove(checkSquare + UP*directionMultiplier);
                    addCapture(checkSquare + UP*directionMultiplier);
                }
            }
        }

        if (validMove(getSquare(), RIGHT)) {
            checkSquare += getSquare() + RIGHT;
            if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
                Pawn opponentPawn = (Pawn) positionNode.getOpponentPieces().get(checkSquare);
                if (opponentPawn.pawnJustMovedTwoSquares) {
                    addPossibleMove(checkSquare + UP*directionMultiplier);
                    addCapture(checkSquare + UP*directionMultiplier);
                }
            }
        }
    }
    
}