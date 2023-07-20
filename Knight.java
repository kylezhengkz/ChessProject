import java.util.*;
public class Knight extends Piece {

    Knight(double value, ArrayWrapper square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<ArrayWrapper, List<Piece>> controlledSquares) {
        Piece selectedKnight = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, 1}
        };

        for (int[] deltaDirection : possibleDirections) {
            ArrayWrapper currentSquare = new ArrayWrapper(getSquare().getArray().clone());
            currentSquare.getArray()[0] += deltaDirection[0];
            currentSquare.getArray()[1] += deltaDirection[1];

            if (!validSquare(currentSquare)) {
                continue;
            }
            
            insertToList(selectedKnight, controlledSquares.get(currentSquare));

            if (positionNode.getMyPieces().containsKey(currentSquare)) {
                continue;
            }

            addPossibleMove(currentSquare);

            // if capture
            if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                addCapture(currentSquare);
            }

        }
    }
    
}