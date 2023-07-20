import java.util.*;
public class Rook extends Piece {

    private boolean stationaryStatus; // used to check if castling is allowed
    
    Rook(double value, ArrayWrapper square, int color) {
        super(value, square, color);
        this.stationaryStatus = true;
    }

    protected boolean getStationaryStatus() {
        return stationaryStatus;
    }

    void moveRook() {
        stationaryStatus = false;
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<ArrayWrapper, List<Piece>> controlledSquares) {
        Piece selectedRook = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };

        for (int[] deltaDirection : possibleDirections) {
            ArrayWrapper currentSquare = new ArrayWrapper(getSquare().getArray().clone());
            currentSquare.getArray()[0] += deltaDirection[0];
            currentSquare.getArray()[1] += deltaDirection[1];

            while (validSquare(currentSquare)) {
                insertToList(selectedRook, controlledSquares.get(currentSquare));
                
                // own piece
                if (positionNode.getMyPieces().containsKey(currentSquare)) {
                    break;
                }

                addPossibleMove(currentSquare);

                // if capture
                if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                    addCapture(currentSquare);

                    // check for pins/skewers
                    int[] checkSquareArr = new int[2];
                    ArrayWrapper checkSquare = new ArrayWrapper(checkSquareArr);
                    checkSquare.getArray()[0] = currentSquare.getArray()[0];
                    checkSquare.getArray()[1] = currentSquare.getArray()[1];
                    checkSquare.getArray()[0] += deltaDirection[0];
                    checkSquare.getArray()[1] += deltaDirection[1];
                    if (validSquare(checkSquare) && positionNode.getOpponentPieces().containsKey(checkSquare)) {
                        Piece skeweredPiece = positionNode.getOpponentPieces().get(checkSquare);
                        SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedRook, skeweredPiece, deltaDirection);
                        positionNode.getOpponentPieces().get(currentSquare).addSkewerThreat(newSkewerThreat);
                    }
                    
                    break;
                }

                currentSquare.getArray()[0] += deltaDirection[0];
                currentSquare.getArray()[1] += deltaDirection[1];
            }
        }
    }
    
}