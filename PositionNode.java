import java.util.*;
public class PositionNode {
    private HashMap<Integer, Piece> userPieces;
    private HashMap<Integer, Piece> cpuPieces;
    private List<PositionNode> children;

    PositionNode(HashMap<Integer, Piece> myPieces, HashMap<Integer, Piece> opponentPieces) {
        this.userPieces = myPieces;
        this.cpuPieces = opponentPieces;
    }

    protected HashMap<Integer, Piece> getUserPieces() {
        return userPieces;
    }

    protected HashMap<Integer, Piece> getCpuPieces() {
        return cpuPieces;
    }

    protected List<PositionNode> getChildren() {
        return children;
    }

    protected void generatePossibleMoves() {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

        for (int square : getCpuPieces().keySet()) {
            Piece cpuPiece = getCpuPieces().get(square);
            if (cpuPiece instanceof Pawn) {
                ((Pawn) cpuPiece).generateMoves(getCpuPieces(), getUserPieces(), unsafeSquares);
            } else if (cpuPiece instanceof Knight) {
                ((Knight) cpuPiece).generateMoves(getCpuPieces(), getUserPieces(), unsafeSquares);
            } else if (cpuPiece instanceof Bishop) {
                ((Bishop) cpuPiece).generateMoves(getCpuPieces(), getUserPieces(), unsafeSquares);
            } else if (cpuPiece instanceof Rook) {
                ((Rook) cpuPiece).generateMoves(getCpuPieces(), getUserPieces(), unsafeSquares);
            } else if (cpuPiece instanceof Queen) {
                ((Queen) cpuPiece).generateMoves(getCpuPieces(), getUserPieces(), unsafeSquares);
            } else if (cpuPiece instanceof King) {
                ((King) cpuPiece).generateMoves(getCpuPieces(), getUserPieces(), unsafeSquares, controlledSquares);
            }
        }

        for (int square : getUserPieces().keySet()) {
            Piece userPiece = getUserPieces().get(square);

            if (userPiece instanceof Pawn) {
                ((Pawn) userPiece).generateMoves(getUserPieces(), getCpuPieces(), controlledSquares);
            } else if (userPiece instanceof Knight) {
                ((Knight) userPiece).generateMoves(getUserPieces(), getCpuPieces(), controlledSquares);
            } else if (userPiece instanceof Bishop) {
                ((Bishop) userPiece).generateMoves(getUserPieces(), getCpuPieces(), controlledSquares);
            } else if (userPiece instanceof Rook) {
                ((Rook) userPiece).generateMoves(getUserPieces(), getCpuPieces(), controlledSquares);
            } else if (userPiece instanceof Queen) {
                ((Queen) userPiece).generateMoves(getUserPieces(), getCpuPieces(), controlledSquares);
            } else if (userPiece instanceof King) {
                ((King) userPiece).generateMoves(getUserPieces(), getCpuPieces(), controlledSquares, unsafeSquares);
            }
        }
    }

    protected void clearMoves() {
        for (int square : getCpuPieces().keySet()) {
            Piece cpuPiece = getCpuPieces().get(square);
            cpuPiece.clear();
        }

        for (int square : getUserPieces().keySet()) {
            Piece userPiece = getUserPieces().get(square);
            userPiece.clear();
        }
    }

}