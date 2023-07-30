import java.util.*;

public class Pawn extends Piece {

    private boolean stationaryStatus; // true if pawn has not moved yet
    private boolean doubleSquareStart; // true if pawn moved two squares forward
    private boolean enPassantLeft; // true if pawn is eligible to perform en passant (left)
    private boolean enPassantRight; // true if pawn is eligible to perform en passant (right)

    Pawn(double value, int square, int color) {
        super(value, square, color);
        stationaryStatus = true;
        doubleSquareStart = false;
        enPassantLeft = false;
        enPassantRight = false;
    }

    protected boolean getStationaryStatus() {
        return stationaryStatus;
    }

    protected boolean didPawnMoveTwoSquares() {
        return doubleSquareStart;
    }

    protected void movePawn() {
        stationaryStatus = false;
    }

    protected void moveTwoSquares() {
        doubleSquareStart = true;
    }

    protected void setEnPassantLeft(boolean status) {
        enPassantLeft = status;
    }

    protected void setEnPassantRight(boolean status) {
        enPassantRight = status;
    }

    protected boolean getEnPassantLeft() {
        return enPassantLeft;
    }

    protected boolean getEnPassantRight() {
        return enPassantRight;
    }

    @Override
    protected void generateMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, HashMap<Integer, List<Piece>> controlledSquares) {

        Piece selectedPawn = teamPieces.get(getSquare());

        int directionMultiplier = 1;
        if (getColor() == GlobalConstants.BLACK) {
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
                if (!teamPieces.containsKey(checkSquare)
                && !opponentPieces.containsKey(checkSquare)) {
                    addPossibleMove(checkSquare, 0);
                }
            }

            // double squares forward
            if (validMove(getSquare(), 2 * UP * directionMultiplier)) {
                checkSquare = getSquare() + 2 * UP * directionMultiplier;
                if (getStationaryStatus() && !teamPieces.containsKey(checkSquare)
                && !opponentPieces.containsKey(checkSquare)) {
                    addPossibleMove(checkSquare, 0);
                }
            }
        }

        if ((legalSkewerDirection == 0 || legalSkewerDirection == UP_LEFT || legalSkewerDirection == -UP_LEFT)) {
            // regular capture
            if (validMove(getSquare(), UP_LEFT * directionMultiplier)) {
                checkSquare = getSquare() + UP_LEFT * directionMultiplier;
                if (controlledSquares.get(checkSquare) == null) {
                    controlledSquares.put(checkSquare, new ArrayList<>());
                }
                insertToList(selectedPawn, controlledSquares.get(checkSquare));
                controlledSquares.put(checkSquare, controlledSquares.get(checkSquare));
                if (opponentPieces.containsKey(checkSquare)) {
                    addPossibleMove(checkSquare, 0);
                    addCapture(checkSquare);
                }
            }

            // en passant left
            if (enPassantLeft) {
                addPossibleMove(checkSquare + UP_LEFT*directionMultiplier, 0);
                addCapture(checkSquare + UP_LEFT*directionMultiplier);
                setEnPassantLeft(false);
            }
        }

        if ((legalSkewerDirection == 0 || legalSkewerDirection == UP_RIGHT || legalSkewerDirection == -UP_RIGHT)) {
            // regular capture
            if (validMove(getSquare(), UP_RIGHT * directionMultiplier)) {
                checkSquare = getSquare() + UP_RIGHT * directionMultiplier;
                if (controlledSquares.get(checkSquare) == null) {
                    controlledSquares.put(checkSquare, new ArrayList<>());
                }
                insertToList(selectedPawn, controlledSquares.get(checkSquare));
                controlledSquares.put(checkSquare, controlledSquares.get(checkSquare));
                if (opponentPieces.containsKey(checkSquare)) {
                    addPossibleMove(checkSquare, 0);
                    addCapture(checkSquare);
                }
            }

            // en passant right
            if (enPassantRight) {
                addPossibleMove(checkSquare + UP_RIGHT*directionMultiplier, 0);
                addCapture(checkSquare + UP_RIGHT*directionMultiplier);
                setEnPassantRight(false);
            }
        }
    }
}