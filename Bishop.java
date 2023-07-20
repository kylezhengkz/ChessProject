import java.util.*;
public class Bishop extends Piece {
    
    Bishop(double value, int square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<Integer, List<Piece>> controlledSquares) {
        Piece selectedBishop = positionNode.getMyPieces().get(getSquare());
        int[] directions = {UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT};

        for (int delta : directions) {
            int currentSquare = selectedBishop.getSquare();
            while (validMove(currentSquare, delta)) {
                currentSquare += delta;

                insertToList(selectedBishop, controlledSquares.get(currentSquare));
                
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
                            SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedBishop, skeweredPiece, delta);
                            positionNode.getOpponentPieces().get(currentSquare).addSkewerThreat(newSkewerThreat);
                        }
                    }
                    
                    break;
                }

            }
        }
    }

}