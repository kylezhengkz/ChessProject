import java.util.*;
public class Bishop extends Piece {
    
    Bishop(double value, int square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<Integer, List<Piece>> controlledSquares) {
        Piece selectedBishop = positionNode.getUserPieces().get(getSquare());
        int[] directions = {UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT};

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

            int currentSquare = selectedBishop.getSquare();
            while (validMove(currentSquare, delta)) {
                currentSquare += delta;

                insertToList(selectedBishop, controlledSquares.get(currentSquare));
                
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
                            SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedBishop, skeweredPiece, delta);
                            positionNode.getCpuPieces().get(currentSquare).addSkewerThreat(newSkewerThreat);
                        }
                    }
                    
                    break;
                }

            }
        }
    }

}