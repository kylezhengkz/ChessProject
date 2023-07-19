import java.util.*;
public class King extends Piece {

    private boolean stationaryStatus; // used to check if castling is allowed
    private HashSet<ArrayWrapper> possibleCastles;

    King(double value, ArrayWrapper square, int color) {
        super(value, square, color);
        stationaryStatus = true;
    }

    protected boolean getStationaryStatus() {
        return stationaryStatus;
    }

    void moveKing() {
        stationaryStatus = false;
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<ArrayWrapper, List<Piece>> controlledSquares) {
        Piece selectedKing = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, 1}
        };

        for (int[] deltaDirection : possibleDirections) {
            ArrayWrapper currentSquare = getSquare();
            currentSquare.getArray()[0] += deltaDirection[0];
            currentSquare.getArray()[1] += deltaDirection[1];

            if (!validSquare(currentSquare)) {
                continue;
            }
            
            insertToList(selectedKing, controlledSquares.get(currentSquare));

            if (positionNode.getMyPieces().containsKey(currentSquare)) {
                continue;
            }

            getPossibleMoves().add(currentSquare);

            // if capture
            if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                getCaptures().add(currentSquare);
            }

        }
    }

    protected void checkCastle(PositionNode positionNode, HashMap<int[], List<Piece>> unsafeSquares) {
        if (!getStationaryStatus()) {
            return;
        }

        int[] checkSquareArr = new int[2];
        ArrayWrapper checkSquare = new ArrayWrapper(checkSquareArr);
        if (getColor() == BLACK) {
            checkSquare.getArray()[1] = 8;
        } else {
            checkSquare.getArray()[1] = 1;
        }

        // check if rook exists and satisfies the conditions to castle
        checkSquare.getArray()[0] = 1;
        if (positionNode.getMyPieces().containsKey(checkSquare) 
        && positionNode.getMyPieces().get(checkSquare) instanceof Rook
        && ((Rook) positionNode.getMyPieces().get(checkSquare)).getStationaryStatus()
        && !unsafeSquares.containsKey(checkSquare.getArray())) {
            // check for pieces in the way or unsafe squares
            for (int i = 2; i <= 4; i++) {
                checkSquare.getArray()[0] = i;
                if (!positionNode.getMyPieces().containsKey(checkSquare) 
                && !positionNode.getOpponentPieces().containsKey(checkSquare)
                && !unsafeSquares.containsKey(checkSquare.getArray())) {
                    int[] castlePositionArr = {3, checkSquare.getArray()[1]};
                    ArrayWrapper castlePosition = new ArrayWrapper(castlePositionArr);
                    possibleCastles.add(castlePosition);
                }
            }
        }

        // check if rook exists and satisfies the conditions to castle
        checkSquare.getArray()[0] = 8;
        if (positionNode.getMyPieces().containsKey(checkSquare) 
        && positionNode.getMyPieces().get(checkSquare) instanceof Rook
        && ((Rook) positionNode.getMyPieces().get(checkSquare)).getStationaryStatus()
        && !unsafeSquares.containsKey(checkSquare.getArray())) {
            // check for pieces in the way or unsafe squares
            for (int i = 6; i <= 7; i++) {
                checkSquare.getArray()[0] = i;
                if (!positionNode.getMyPieces().containsKey(checkSquare) 
                && !positionNode.getOpponentPieces().containsKey(checkSquare)
                && !unsafeSquares.containsKey(checkSquare.getArray())) {
                    int[] castlePositionArr = {7, checkSquare.getArray()[1]};
                    ArrayWrapper castlePosition = new ArrayWrapper(castlePositionArr);
                    possibleCastles.add(castlePosition);
                }
            }
        }

    }

}