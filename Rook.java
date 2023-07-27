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
    protected void generateMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, HashMap<Integer, List<Piece>> controlledSquares) {
        Piece selectedRook = teamPieces.get(getSquare());
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

                if (controlledSquares.get(currentSquare) == null) {
                    controlledSquares.put(currentSquare, new ArrayList<>());
                }
                insertToList(selectedRook, controlledSquares.get(currentSquare));
                controlledSquares.put(currentSquare, controlledSquares.get(currentSquare));
                
                // own piece
                if (teamPieces.containsKey(currentSquare)) {
                    break;
                }

                addPossibleMove(currentSquare, 0);

                // if capture
                if (opponentPieces.containsKey(currentSquare)) {
                    addCapture(currentSquare);

                    int checkSquare = 0;
                    // check for pins/skewers
                    if (validMove(currentSquare, delta)) {
                        checkSquare = currentSquare + delta;
                        if (opponentPieces.containsKey(checkSquare)) {
                            Piece skeweredPiece = opponentPieces.get(checkSquare);
                            SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedRook, skeweredPiece, delta);
                            opponentPieces.get(currentSquare).addSkewerThreat(newSkewerThreat);
                        }
                    }
                    
                    break;
                }

            }
        }
    }
    
}