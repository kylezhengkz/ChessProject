import java.util.*;
public class Queen extends Piece {
    
    Queen(double value, int square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<Integer, List<Piece>> controlledSquares) {
        Piece selectedQueen = positionNode.getMyPieces().get(getSquare());
        int[] directions = {UP, LEFT, DOWN, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT};

        for (int delta : directions) {
            int currentSquare = selectedQueen.getSquare();
            while (validMove(currentSquare, delta)) {
                currentSquare += delta;

                insertToList(selectedQueen, controlledSquares.get(currentSquare));
                
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
                            SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedQueen, skeweredPiece, delta);
                            positionNode.getOpponentPieces().get(currentSquare).addSkewerThreat(newSkewerThreat);
                        }
                    }
                    
                    break;
                }

            }
        }
    }
    
}