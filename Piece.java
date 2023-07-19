import java.util.*;
public class Piece {

    public static final int WHITE = 1;
    public static final int BLACK = 2;

    private double value;
    private ArrayWrapper square;
    private int color;
    private HashSet<ArrayWrapper> possibleMoves;
    private HashSet<ArrayWrapper> captures; // subset of possibleMoves
    private HashSet<SkewerTriplet> skewerThreats;

    Piece(double value, ArrayWrapper square, int color) {
        this.color = color;
        this.value = value;
        this.square = square;
    }

    protected Double getValue() {
        return value;
    }

    protected ArrayWrapper getSquare() {
        return square;
    }

    protected int getColor() {
        return color;
    }

    protected HashSet<ArrayWrapper> getPossibleMoves() {
        return possibleMoves;
    }

    protected HashSet<ArrayWrapper> getCaptures() {
        return captures;
    }

    protected HashSet<SkewerTriplet> getSkewerThreats() {
        return skewerThreats;
    }

    protected void generateMoves(PositionNode positionNode, HashMap<ArrayWrapper, List<Piece>> controlledSquares) {}

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

    protected boolean validSquare(ArrayWrapper square) {
        return square.getArray()[0] >= 1 && square.getArray()[1] <= 64 && square.getArray()[1] >= 1 && square.getArray()[1] <= 64;
    }

    protected boolean isVertical(ArrayWrapper direction) {
        return (direction.getArray()[0] == 1 && direction.getArray()[1] == 0) || (direction.getArray()[0] == -1 && direction.getArray()[1] == 0);
    }

    protected boolean isHorizontal(ArrayWrapper direction) {
        return (direction.getArray()[0] == 0 && direction.getArray()[1] == 1) || (direction.getArray()[0] == 0 && direction.getArray()[1] == -1);
    }
}