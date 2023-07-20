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

    protected boolean getStationaryStatus() {
        return stationaryStatus;
    }

    void moveKing() {
        stationaryStatus = false;
    }

    @Override
    protected void generateMoves(PositionNode positionNode, HashMap<Integer, List<Piece>> controlledSquares) {
        Piece selectedKing = positionNode.getMyPieces().get(getSquare());
        int[] directions = {UP, LEFT, DOWN, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT};

        for (int delta : directions) {
            if (!validMove(getSquare(), delta)) {
                continue;
            }

            int newSquare = getSquare() + delta;
            insertToList(selectedKing, controlledSquares.get(newSquare));

            if (positionNode.getMyPieces().containsKey(newSquare)) {
                continue;
            }

            addPossibleMove(newSquare);

            // if capture
            if (positionNode.getOpponentPieces().containsKey(newSquare)) {
                addCapture(newSquare);
            }

        }
    }

    protected void checkCastle(PositionNode positionNode, HashMap<Integer, List<Piece>> unsafeSquares) {
        if (!getStationaryStatus()) {
            return;
        }

        int checkSquare = 0;

        // check left side
        if (getColor() == WHITE) {
            checkSquare = 1;
        } else {
            checkSquare = 8;
        }

        boolean castleStatus = true;
        if (positionNode.getMyPieces().containsKey(checkSquare) 
        && positionNode.getMyPieces().get(checkSquare) instanceof Rook
        && ((Rook) positionNode.getMyPieces().get(checkSquare)).getStationaryStatus()) {
            // check for pieces in the way or unsafe squares
            for (int i = checkSquare; i <= checkSquare + 32; i += 8) {
                if (positionNode.getMyPieces().containsKey(i)
                && positionNode.getOpponentPieces().containsKey(i)
                && unsafeSquares.containsKey(i)) {
                    castleStatus = false;
                }
            }
        }

        if (castleStatus) {
            addCastle(getSquare() - 16);
        }
        
        // check right side
        if (getColor() == WHITE) {
            checkSquare = 64;
        } else {
            checkSquare = 57;
        }

        castleStatus = true;
        if (positionNode.getMyPieces().containsKey(checkSquare) 
        && positionNode.getMyPieces().get(checkSquare) instanceof Rook
        && ((Rook) positionNode.getMyPieces().get(checkSquare)).getStationaryStatus()) {
            // check for pieces in the way or unsafe squares
            for (int i = checkSquare; i >= checkSquare - 16; i -= 8) {
                if (positionNode.getMyPieces().containsKey(i)
                && positionNode.getOpponentPieces().containsKey(i)
                && unsafeSquares.containsKey(i)) {
                    castleStatus = false;
                }
            }
        }

        if (castleStatus) {
            addCastle(getSquare() + 16);
        }
    }

}