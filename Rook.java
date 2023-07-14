import java.util.*;
public class Rook extends Piece {
    
    Rook(double value, int[] square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {
        Piece selectedRook = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };

        for (int[] deltaDirection : possibleDirections) {
            int[] currentSquare = getSquare();
            currentSquare[0] += deltaDirection[0];
            currentSquare[1] += deltaDirection[1];
            while (validSquare(currentSquare)) {
                insertToList(selectedRook, controlledSquares.get(currentSquare));
                
                // own piece
                if (positionNode.getMyPieces().containsKey(currentSquare)) {
                    break;
                }

                getPossibleMoves().add(currentSquare);

                // opponent piece
                if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                    getCaptures().add(currentSquare);

                    // check for pins/skewers
                    int[] checkSquare = new int[2];
                    checkSquare[0] = currentSquare[0];
                    checkSquare[1] = currentSquare[1];
                    checkSquare[0] += deltaDirection[0];
                    checkSquare[1] += deltaDirection[1];
                    if (validSquare(checkSquare) && positionNode.getOpponentPieces().containsKey(checkSquare)) {
                        Piece skeweredPiece = positionNode.getOpponentPieces().get(checkSquare);
                        SkewerTriplet newSkewerThreat = new SkewerTriplet(selectedRook, skeweredPiece, deltaDirection);
                        positionNode.getOpponentPieces().get(currentSquare).getSkewerThreats().add(newSkewerThreat);
                    }
                    
                    break;
                }
            }
        }
    }
    
}