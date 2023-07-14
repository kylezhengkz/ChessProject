import java.util.*;
public class Bishop extends Piece {

    private HashSet<int[]> skewerSquares;
    
    Bishop(double value, int[] square) {
        super(value, square);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {
        Piece selectedBishop = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] deltaDirection : possibleDirections) {
            int[] currentSquare = getSquare();
            currentSquare[0] += deltaDirection[0];
            currentSquare[1] += deltaDirection[1];
            while (validSquare(currentSquare)) {
                insertToList(selectedBishop, controlledSquares.get(currentSquare));
                
                if (positionNode.getMyPieces().containsKey(currentSquare)) {
                    break;
                }

                if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                    getCaptures().add(currentSquare);

                    // check for skewers
                    int[] checkSquare = new int[2];
                    currentSquare[0] += deltaDirection[0];
                    currentSquare[1] += deltaDirection[1];
                    if (validSquare(currentSquare) && positionNode.getOpponentPieces().containsKey(currentSquare)) {
                        Piece pinnedPiece = positionNode.getOpponentPieces().containsKey(currentSquare);
                        Piece skeweredPiece = positionNode.getOpponentPieces().containsKey(currentSquare);
                    }
                    
                    break;
                }
            }
        }
    }

}