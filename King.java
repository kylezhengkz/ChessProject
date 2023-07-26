import java.util.*;
public class King extends Piece {

    private boolean stationaryStatus; // used to check if castling is allowed
    private HashSet<Integer> possibleCastles;

    King(double value, int square, int color) {
        super(value, square, color);
        stationaryStatus = true;
    }

    protected void addCastle(int square) {
        if (possibleCastles == null) {
            possibleCastles = new HashSet<>();
        }

        possibleCastles.add(square);
    }

    protected HashSet<Integer> getPossibleCastles() {
        return possibleCastles;
    }

    protected boolean getStationaryStatus() {
        return stationaryStatus;
    }

    void moveKing() {
        stationaryStatus = false;
    }

    protected void generateMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, HashMap<Integer, List<Piece>> controlledSquares, HashMap<Integer, List<Piece>> unsafeSquares) {
        Piece selectedKing = teamPieces.get(getSquare());
        int[] directions = {UP, LEFT, DOWN, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT};

        for (int delta : directions) {
            if (!validMove(getSquare(), delta)) {
                continue;
            }

            int newSquare = getSquare() + delta;
            if (unsafeSquares.containsKey(newSquare)) { // illegal move
                continue;
            }
            insertToList(selectedKing, controlledSquares.get(newSquare));
            controlledSquares.put(newSquare, controlledSquares.get(newSquare));

            if (teamPieces.containsKey(newSquare)) {
                continue;
            }

            addPossibleMove(newSquare);

            // if capture
            if (opponentPieces.containsKey(newSquare)) {
                addCapture(newSquare);
            }

        }
    }

    protected void checkCastle(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, HashMap<Integer, List<Piece>> unsafeSquares) {
        if (!getStationaryStatus()) {
            return;
        }

        int checkSquare = 0;

        // check left side
        if (getColor() == GlobalConstants.WHITE) {
            checkSquare = 1;
        } else {
            checkSquare = 8;
        }

        boolean castleStatus = false;
        if (teamPieces.containsKey(checkSquare) 
        && teamPieces.get(checkSquare) instanceof Rook
        && ((Rook) teamPieces.get(checkSquare)).getStationaryStatus()) {
            castleStatus = true;
            // check for pieces in the way or unsafe squares
            for (int i = getSquare() - 8; i >= getSquare() - 24; i -= 8) {
                System.out.println("CHECK LEFT: " + i);
                if (teamPieces.containsKey(i)
                || opponentPieces.containsKey(i)
                || unsafeSquares.containsKey(i)) {
                    castleStatus = false;
                }
            }
        }

        if (castleStatus) {
            addCastle(getSquare() - 16);
            addPossibleMove(getSquare() - 16);
        }
        
        // check right side
        if (getColor() == GlobalConstants.WHITE) {
            checkSquare = 57;
        } else {
            checkSquare = 64;
        }

        castleStatus = false;
        if (teamPieces.containsKey(checkSquare) 
        && teamPieces.get(checkSquare) instanceof Rook
        && ((Rook) teamPieces.get(checkSquare)).getStationaryStatus()) {
            System.out.println("CHECK 2 HERE");
            castleStatus = true;
            // check for pieces in the way or unsafe squares
            for (int i = getSquare() + 8; i <= getSquare() + 16; i += 8) {
                System.out.println("CHECK RIGHT: " + i);
                if (teamPieces.containsKey(i)
                || opponentPieces.containsKey(i)
                || unsafeSquares.containsKey(i)) {
                    castleStatus = false;
                }
            }
        }

        if (castleStatus) {
            addCastle(getSquare() + 16);
            addPossibleMove(getSquare() + 16);
        }
    }

}