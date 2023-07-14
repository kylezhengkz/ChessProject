import java.util.*;
public class Pawn extends Piece {

    Pawn(double value, int[] square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {
        Piece selectedPawn = positionNode.getMyPieces().get(getSquare());
        int[][] possibleDirections = {
            {0, 1}, {0, 2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, 1}
        };

        for (int[] deltaDirection : possibleDirections) {
            int[] currentSquare = getSquare();
            currentSquare[0] += deltaDirection[0];
            currentSquare[1] += deltaDirection[1];

            if (validSquare(currentSquare)) {
                continue;
            }

            insertToList(selectedPawn, controlledSquares.get(currentSquare));

            // opponent piece
            if (positionNode.getOpponentPieces().containsKey(currentSquare)) {
                getCaptures().add(currentSquare);
            }
            
        }
    }
    
}