import java.util.*;
public class Piece {
    private double value;
    private int[] square;
    private HashSet<int[]> possibleMoves;
    private HashSet<int[]> captures; // subset of possibleMoves

    Piece(double value, int[] square) {
        this.value = value;
        this.square = square;
    }

    protected Double getValue() {
        return value;
    }

    protected int[] getSquare() {
        return square;
    }

    protected HashSet<int[]> getPossibleMoves() {
        return possibleMoves;
    }

    protected HashSet<int[]> getCaptures() {
        return captures;
    }

    protected void generateMoves(PositionNode positionNode, HashMap<int[], List<Piece>> controlledSquares) {}

    protected void insertToList(Piece element, List<Piece> pieceList) {
        int index = 0;
        for (Piece piece : pieceList) {
            if (element.getValue() < piece.getValue()) {
                break;
            }
            index++;
        }
        pieceList.add(index, element);
    }

    protected boolean validSquare(int[] square) {
        return square[0] >= 1 && square[1] <= 64 && square[1] >= 1 && square[1] <= 64;
    }
}