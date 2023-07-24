import java.util.*;
public class Piece {

    public static final int WHITE = 1;
    public static final int BLACK = 2;

    private double value;
    private int square;
    private int color;
    private HashSet<Integer> possibleMoves;
    private HashSet<Integer> captures; // subset of possibleMoves
    private HashSet<SkewerTriplet> skewerThreats;

    protected static final int UP = 1;
    protected static final int DOWN = -1;
    protected static final int RIGHT = 8;
    protected static final int LEFT = -8;

    protected static final int UP_RIGHT = 9;
    protected static final int DOWN_LEFT = -9;
    protected static final int UP_LEFT = -7;
    protected static final int DOWN_RIGHT = 7;

    // knight moves
    protected static final int K_UP_RIGHT = 10;
    protected static final int K_RIGHT_UP = 17;
    protected static final int K_UP_LEFT = -6;
    protected static final int K_LEFT_UP = -15;
    protected static final int K_DOWN_LEFT = -10;
    protected static final int K_LEFT_DOWN = -17;
    protected static final int K_DOWN_RIGHT = 6;
    protected static final int K_RIGHT_DOWN = 15;

    Piece(double value, int square, int color) {
        this.color = color;
        this.value = value;
        this.square = square;
    }

    protected void addPossibleMove(int square) {
        if (possibleMoves == null) {
            possibleMoves = new HashSet<>();
        }
        
        possibleMoves.add(square);
    }

    protected void addCapture(int square) {
        if (captures == null) {
            captures = new HashSet<>();
        }

        captures.add(square);
    }

    protected void addSkewerThreat(SkewerTriplet skewerTriplet) {
        if (skewerThreats == null) {
            skewerThreats = new HashSet<>();
        }
        
        skewerThreats.add(skewerTriplet);
    }

    protected Double getValue() {
        return value;
    }

    protected Integer getSquare() {
        return square;
    }

    protected int getColor() {
        return color;
    }

    protected HashSet<Integer> getPossibleMoves() {
        return possibleMoves;
    }

    protected HashSet<Integer> getCaptures() {
        return captures;
    }

    protected HashSet<SkewerTriplet> getSkewerThreats() {
        return skewerThreats;
    }

    protected void generateMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, HashMap<Integer, List<Piece>> controlledSquares) {}

    protected void insertToList(Piece element, List<Piece> pieceList) {
        if (pieceList == null) {
            pieceList = new ArrayList<>();
        }

        int index = 0;
        for (Piece piece : pieceList) {
            if (element.getValue() < piece.getValue()) {
                break;
            }
            index++;
        }
        pieceList.add(index, element);
    }

    protected boolean validMove(int initialSquare, int delta) {
        if (initialSquare % 8 == 1) {
            if (delta == DOWN || delta == DOWN_LEFT || delta == DOWN_RIGHT) {
                return false;
            }
        } 
        
        if (initialSquare % 8 == 0) {
            if (delta == UP || delta == UP_LEFT || delta == UP_RIGHT) {
                return false;
            }
        } 
        
        if (initialSquare >= 1 && initialSquare <= 8) {
            if (delta == LEFT || delta == UP_LEFT || delta == DOWN_LEFT) {
                return false;
            }
        } 
        
        if (initialSquare >= 57 && initialSquare <= 64) {
            if (delta == RIGHT || delta == UP_RIGHT || delta == DOWN_RIGHT) {
                return false;
            }
        }
        return true;
    }

}