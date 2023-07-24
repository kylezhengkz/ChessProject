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

    protected void branchNewCPUMoves() {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        for (int square : getUserPieces().keySet()) {
            Piece userPiece = getUserPieces().get(square);
            if (userPiece instanceof Pawn) {
                ((Pawn) userPiece).generateMoves(this, unsafeSquares);
            } else if (userPiece instanceof Knight) {
                ((Knight) userPiece).generateMoves(this, unsafeSquares);
            } else if (userPiece instanceof Pawn) {
                ((Pawn) userPiece).generateMoves(this, unsafeSquares);
            } else if (userPiece instanceof Bishop) {
                ((Bishop) userPiece).generateMoves(this, unsafeSquares);
            } else if (userPiece instanceof Queen) {
                ((Queen) userPiece).generateMoves(this, unsafeSquares);
            } else if (userPiece instanceof King) {
                ((King) userPiece).generateMoves(this, unsafeSquares);
            }
        }

        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();
        for (int square : getCpuPieces().keySet()) {
            Piece cpuPiece = getCpuPieces().get(square);

            if (cpuPiece instanceof Pawn) {
                ((Pawn) cpuPiece).generateMoves(this, controlledSquares);
            } else if (cpuPiece instanceof Knight) {
                ((Knight) cpuPiece).generateMoves(this, controlledSquares);
            } else if (cpuPiece instanceof Pawn) {
                ((Pawn) cpuPiece).generateMoves(this, controlledSquares);
            } else if (cpuPiece instanceof Bishop) {
                ((Bishop) cpuPiece).generateMoves(this, controlledSquares);
            } else if (cpuPiece instanceof Queen) {
                ((Queen) cpuPiece).generateMoves(this, controlledSquares);
            } else if (cpuPiece instanceof King) {
                ((King) cpuPiece).generateMoves(this, controlledSquares);
            }
        }
    }

}