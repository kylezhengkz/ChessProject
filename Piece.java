import java.util.*;
public class Piece {

    public static final int WHITE = 1;
    public static final int BLACK = 2;

    private double value;
    private int[] square;
    private int color;
    private HashSet<int[]> possibleMoves;
    private HashSet<int[]> captures; // subset of possibleMoves
    private HashSet<SkewerTriplet> skewerThreats;

    Piece(double value, int[] square, int color) {
        this.color = color;
        this.value = value;
        this.square = square;
    }

    protected Double getValue() {
        return value;
    }

    protected int[] getSquare() {
        return square;
    }

    protected int getColor() {
        return color;
    }

    protected HashSet<int[]> getPossibleMoves() {
        return possibleMoves;
    }

    protected HashSet<int[]> getCaptures() {
        return captures;
    }

    protected HashSet<SkewerTriplet> getSkewerThreats() {
        return skewerThreats;
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

    protected boolean isVertical(int[] direction) {
        return (direction[0] == 1 && direction[1] == 0) || (direction[0] == -1 && direction[1] == 0);
    }

    protected boolean isHorizontal(int[] direction) {
        return (direction[0] == 0 && direction[1] == 1) || (direction[0] == 0 && direction[1] == -1);
    }
}