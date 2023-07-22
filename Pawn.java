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

        Piece selectedPawn = positionNode.getUserPieces().get(getSquare());

        int directionMultiplier = 1;
        if (getColor() == BLACK) {
            directionMultiplier = -1;
        }

        // check if piece is pinned in front of king (illegal move)
        int legalSkewerDirection = 0;
        if (getSkewerThreats() != null) {
            for (SkewerTriplet skewerThreat : getSkewerThreats()) {
                if (skewerThreat.getSkeweredPiece() instanceof King) {
                    legalSkewerDirection = skewerThreat.getSkewerDirection();
                }
            }
        }

        int checkSquare = 0; // init

        if ((legalSkewerDirection != 0 || legalSkewerDirection == UP || legalSkewerDirection != UP)) {
            // single square forward
            if (validMove(getSquare(), UP * directionMultiplier)) {
                checkSquare = getSquare() + UP * directionMultiplier;
                if (!positionNode.getUserPieces().containsKey(checkSquare)
                        && !positionNode.getCpuPieces().containsKey(checkSquare)) {
                    addPossibleMove(checkSquare);
                }
            }

            // double squares forward
            if (validMove(getSquare(), 2 * UP * directionMultiplier)) {
                checkSquare = getSquare() + 2 * UP * directionMultiplier;
                if (getStationaryStatus() && !positionNode.getUserPieces().containsKey(checkSquare)
                        && !positionNode.getCpuPieces().containsKey(checkSquare)) {
                    addPossibleMove(checkSquare);
                }
            }
        }

        if ((legalSkewerDirection != 0 || legalSkewerDirection == UP_LEFT || legalSkewerDirection == -UP_LEFT)) {
            // regular capture
            if (validMove(getSquare(), UP_LEFT * directionMultiplier)) {
                checkSquare = getSquare() + UP_LEFT * directionMultiplier;
                insertToList(selectedPawn, controlledSquares.get(checkSquare));
                if (positionNode.getCpuPieces().containsKey(checkSquare)) {
                    addPossibleMove(checkSquare);
                    addCapture(checkSquare);
                }
            }

            // en passant
            if (validMove(getSquare(), LEFT * directionMultiplier)) {
                checkSquare += getSquare() + LEFT * directionMultiplier;
                if (positionNode.getCpuPieces().containsKey(checkSquare)) {
                    Pawn opponentPawn = (Pawn) positionNode.getCpuPieces().get(checkSquare);
                    if (opponentPawn.pawnJustMovedTwoSquares) {
                        addPossibleMove(checkSquare + UP * directionMultiplier);
                        addCapture(checkSquare + UP * directionMultiplier);
                    }
                }
            }
        }

        if ((legalSkewerDirection != 0 || legalSkewerDirection == UP_RIGHT || legalSkewerDirection == -UP_RIGHT)) {
            // regular capture
            if (validMove(getSquare(), UP_RIGHT * directionMultiplier)) {
                checkSquare = getSquare() + UP_RIGHT * directionMultiplier;
                insertToList(selectedPawn, controlledSquares.get(checkSquare));
                if (positionNode.getCpuPieces().containsKey(checkSquare)) {
                    addPossibleMove(checkSquare);
                    addCapture(checkSquare);
                }
            }

            // en passant
            if (validMove(getSquare(), RIGHT * directionMultiplier)) {
                checkSquare += getSquare() + RIGHT * directionMultiplier;
                if (positionNode.getCpuPieces().containsKey(checkSquare)) {
                    Pawn opponentPawn = (Pawn) positionNode.getCpuPieces().get(checkSquare);
                    if (opponentPawn.pawnJustMovedTwoSquares) {
                        addPossibleMove(checkSquare + UP * directionMultiplier);
                        addCapture(checkSquare + UP * directionMultiplier);
                    }
                }
            }
        }
    }
}