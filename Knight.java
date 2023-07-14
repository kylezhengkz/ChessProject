import java.util.*;
public class Knight extends Piece {

    Knight(double value, int[] square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {
        Piece selectedKnight = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, 1}
        };

        for (int[] deltaDirection : possibleDirections) {
            int[] currentSquare = getSquare();
            currentSquare[0] += deltaDirection[0];
            currentSquare[1] += deltaDirection[1];

            if (!validSquare(currentSquare)) {
                continue;
            }
            
            insertToList(selectedKnight, controlledSquares.get(currentSquare));

            if (positionNode.getMyPieces().containsKey(currentSquare)) {
                continue;
            }

            getPossibleMoves().add(currentSquare);

            // if capture
            if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                getCaptures().add(currentSquare);
            }

        }
    }
    
}