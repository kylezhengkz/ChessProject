import java.util.*;
public class Knight extends Piece {

    Knight(double value, int square, int color) {
        super(value, square, color);
    }

    @Override
    protected void generateMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, HashMap<Integer, List<Piece>> controlledSquares) {
        Piece selectedKnight = teamPieces.get(getSquare());
        // check if piece is pinned in front of king (illegal move)
        if (getSkewerThreats() != null) {
            for (SkewerTriplet skewerThreat : getSkewerThreats()) {
                if (skewerThreat.getSkeweredPiece() instanceof King) {
                    return;
                }
            }
        }

        int[] possibleDirections = {K_UP_RIGHT, K_RIGHT_UP, K_UP_LEFT, K_LEFT_UP, K_DOWN_LEFT, K_LEFT_DOWN, K_DOWN_RIGHT, K_RIGHT_DOWN};

        for (int delta : possibleDirections) {
            if (!validKnightMove(getSquare(), delta)) {
                continue;
            }

            int newSquare = getSquare() + delta;
            insertToList(selectedKnight, controlledSquares.get(newSquare));
            controlledSquares.put(newSquare, controlledSquares.get(newSquare));

            if (teamPieces.containsKey(newSquare)) {
                continue;
            }

            addPossibleMove(newSquare, 0);

            // if capture
            if (opponentPieces.containsKey(newSquare)) {
                addCapture(newSquare);
            }

        }
    }

    protected boolean validKnightMove(int square, int delta) {
        if (delta == K_UP_RIGHT) {
            if (square % 8 == 7 || square % 8 == 0 || (square >= 57 && square <= 64)) {
                return false;
            } else {
                return true;
            }
        } else if (delta == K_RIGHT_UP) {
            if (square % 8 == 0 || (square >= 49 && square <= 64)) {
                return false;
            } else {
                return true;
            }
        } else if (delta == K_LEFT_UP) {
            if (square % 8 == 0 || (square >= 1 && square <= 16)) {
                return false;
            } else {
                return true;
            }
        } else if (delta == K_UP_LEFT) {
            if (square % 8 == 7 || square % 8 == 0 || (square >= 1 && square <= 8)) {
                return false;
            } else {
                return true;
            }
        } else if (delta == K_DOWN_LEFT) {
            if (square % 8 == 1 || square % 8 == 2 || (square >= 1 && square <= 8)) {
                return false;
            } else {
                return true;
            }
        } else if (delta == K_LEFT_DOWN) {
            if (square % 8 == 1 || (square >= 1 && square <= 16)) {
                return false;
            } else {
                return true;
            }
        } else if (delta == K_DOWN_RIGHT) {
            if (square % 8 == 1 || square % 8 == 2 || (square >= 57 && square <= 64)) {
                return false;
            } else {
                return true;
            }
        } else if (delta == K_RIGHT_DOWN) {
            if (square % 8 == 1 || (square >= 49 && square <= 64)) {
                return false;
            } else {
                return true;
            }
        }

        // should be unreachable if used correctly
        System.out.println("ERROR");
        return false;
    }
    
}