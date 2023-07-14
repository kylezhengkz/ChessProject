import java.util.*;
public class Pawn extends Piece {

    Pawn(double value, int[] square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {
        Piece selectedPawn = positionNode.getMyPieces().get(getSquare());

        int directionMultiplier = 1;
        if (getColor() == BLACK) {
            directionMultiplier = -1;
        }

        // single square
        int[] currentSquare = getSquare();
        currentSquare[1] += directionMultiplier;
        if (validSquare(getSquare()) && (!positionNode.getMyPieces().containsKey(currentSquare) || !positionNode.getOpponentPieces().containsKey(currentSquare))) {
            getPossibleMoves().add(currentSquare);
        }

        // check for captures
        currentSquare = getSquare();
        currentSquare[1] += directionMultiplier;
        currentSquare[0] += directionMultiplier;
        if (validSquare(getSquare()) && positionNode.getOpponentPieces().containsKey(currentSquare)) {

        }


        currentSquare[0] -= directionMultiplier;
        if (positionNode.getMyPieces().containsKey(currentSquare) || positionNode.getOpponentPieces().containsKey(currentSquare)) {
            getPossibleMoves().add(currentSquare);
        }

        // double move
        currentSquare[1] += directionMultiplier;
        if (positionNode.getMyPieces().containsKey(currentSquare) || positionNode.getOpponentPieces().containsKey(currentSquare)) {
            getPossibleMoves().add(currentSquare);
        }
    
    }
    
}