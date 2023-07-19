import java.util.*;
public class Queen extends Piece {
    
    Queen(double value, ArrayWrapper square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<ArrayWrapper, List<Piece>> controlledSquares) {
        Piece selectedQueen = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };

        for (int[] deltaDirection : possibleDirections) {
            ArrayWrapper currentSquare = getSquare();
            currentSquare.getArray()[0] += deltaDirection[0];
            currentSquare.getArray()[1] += deltaDirection[1];
            while (validSquare(currentSquare)) {
                insertToList(selectedQueen, controlledSquares.get(currentSquare));
                
                // own piece
                if (positionNode.getMyPieces().containsKey(currentSquare)) {
                    break;
                }

                getPossibleMoves().add(currentSquare);

                // if capture
                if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                    getCaptures().add(currentSquare);

                    // check for pins/skewers
                    int[] checkSquareArr = new int[2];
                    ArrayWrapper checkSquare = new ArrayWrapper(checkSquareArr);
                    checkSquare.getArray()[0] = currentSquare.getArray()[0];
                    checkSquare.getArray()[1] = currentSquare.getArray()[1];
                    checkSquare.getArray()[0] += deltaDirection[0];
                    checkSquare.getArray()[1] += deltaDirection[1];
                    if (validSquare(checkSquare) && positionNode.getOpponentPieces().containsKey(checkSquare)) {
                        Piece skeweredPiece = positionNode.getOpponentPieces().get(checkSquare);
                        SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedQueen, skeweredPiece, deltaDirection);
                        positionNode.getOpponentPieces().get(currentSquare).getSkewerThreats().add(newSkewerThreat);
                    }
                    
                    break;
                }
            }
        }
    }
    
}