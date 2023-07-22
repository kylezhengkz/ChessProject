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
        Piece selectedRook = positionNode.getUserPieces().get(getSquare());
        int[] directions = {UP, LEFT, DOWN, RIGHT};

        outerloop:
        for (int delta : directions) {
            // check if piece is pinned in front of king (illegal move)
            if (getSkewerThreats() != null) {
                for (SkewerTriplet skewerThreat : getSkewerThreats()) {
                    if (skewerThreat.getSkeweredPiece() instanceof King
                    && (skewerThreat.getSkewerDirection() != delta || skewerThreat.getSkewerDirection() != -delta)) {
                        continue outerloop;
                    }
                }
            }
            
            int currentSquare = selectedRook.getSquare();
            while (validMove(currentSquare, delta)) {
                currentSquare += delta;

                insertToList(selectedRook, controlledSquares.get(currentSquare));
                
                // own piece
                if (positionNode.getUserPieces().containsKey(currentSquare)) {
                    break;
                }

                addPossibleMove(currentSquare);

                // if capture
                if (positionNode.getCpuPieces().containsKey(currentSquare)) {
                    addCapture(currentSquare);

                    int checkSquare = 0;
                    // check for pins/skewers
                    if (validMove(currentSquare, delta)) {
                        checkSquare = currentSquare + delta;
                        if (positionNode.getCpuPieces().containsKey(checkSquare)) {
                            Piece skeweredPiece = positionNode.getCpuPieces().get(checkSquare);
                            SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedRook, skeweredPiece, delta);
                            positionNode.getCpuPieces().get(currentSquare).addSkewerThreat(newSkewerThreat);
                        }
                    }
                    
                    break;
                }

            }
        }
    }
    
}