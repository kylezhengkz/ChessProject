import java.util.*;
public class Rook extends Piece {

    private boolean stationaryStatus; // used to check if castling is allowed
    
    Rook(double value, int square, int color) {
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
    protected void generateMoves(PositionNode positionNode, HashMap<Integer, List<Piece>> controlledSquares) {
        Piece selectedRook = positionNode.getMyPieces().get(getSquare());
        int[] directions = {UP, LEFT, DOWN, RIGHT};

        for (int delta : directions) {
            int currentSquare = selectedRook.getSquare();
            while (validMove(currentSquare, delta)) {
                currentSquare += delta;

                insertToList(selectedRook, controlledSquares.get(currentSquare));
                
                // own piece
                if (positionNode.getMyPieces().containsKey(currentSquare)) {
                    break;
                }

                addPossibleMove(currentSquare);

                // if capture
                if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                    addCapture(currentSquare);

                    int checkSquare = 0;
                    // check for pins/skewers
                    if (validMove(currentSquare, delta)) {
                        checkSquare = currentSquare + delta;
                        if (positionNode.getOpponentPieces().containsKey(checkSquare)) {
                            Piece skeweredPiece = positionNode.getOpponentPieces().get(checkSquare);
                            SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedRook, skeweredPiece, delta);
                            positionNode.getOpponentPieces().get(currentSquare).addSkewerThreat(newSkewerThreat);
                        }
                    }
                    
                    break;
                }

            }
        }
    }
    
}