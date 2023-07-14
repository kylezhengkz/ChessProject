import java.util.*;
public class King extends Piece {

    private boolean stationaryStatus; // used to check if castling is allowed
    private HashSet<int[]> possibleCastles;

    King(double value, int[] square, int color) {
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
    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {
        Piece selectedKing = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, 1}
        };

        for (int[] deltaDirection : possibleDirections) {
            int[] currentSquare = getSquare();
            currentSquare[0] += deltaDirection[0];
            currentSquare[1] += deltaDirection[1];

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

        int[] checkSquare = new int[2];
        if (getColor() == BLACK) {
            checkSquare[1] = 8;
        } else {
            checkSquare[1] = 1;
        }

        // check if rook exists and satisfies the conditions to castle
        checkSquare[0] = 1;
        if (positionNode.getMyPieces().containsKey(checkSquare) 
        && positionNode.getMyPieces().get(checkSquare) instanceof Rook
        && ((Rook) positionNode.getMyPieces().get(checkSquare)).getStationaryStatus()
        && !unsafeSquares.containsKey(checkSquare)) {
            // check for pieces in the way or unsafe squares
            for (int i = 2; i <= 4; i++) {
                checkSquare[0] = i;
                if (!positionNode.getMyPieces().containsKey(checkSquare) 
                && !positionNode.getOpponentPieces().containsKey(checkSquare)
                && !unsafeSquares.containsKey(checkSquare)) {
                    int[] castlePosition = {3, checkSquare[1]};
                    possibleCastles.add(castlePosition);
                }
            }
        }

        // check if rook exists and satisfies the conditions to castle
        checkSquare[0] = 8;
        if (positionNode.getMyPieces().containsKey(checkSquare) 
        && positionNode.getMyPieces().get(checkSquare) instanceof Rook
        && ((Rook) positionNode.getMyPieces().get(checkSquare)).getStationaryStatus()
        && !unsafeSquares.containsKey(checkSquare)) {
            // check for pieces in the way or unsafe squares
            for (int i = 6; i <= 7; i++) {
                checkSquare[0] = i;
                if (!positionNode.getMyPieces().containsKey(checkSquare) 
                && !positionNode.getOpponentPieces().containsKey(checkSquare)
                && !unsafeSquares.containsKey(checkSquare)) {
                    int[] castlePosition = {7, checkSquare[1]};
                    possibleCastles.add(castlePosition);
                }
            }
        }

    }

}