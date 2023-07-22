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

    protected void branchNewCPUMoves(PositionNode positionNode) {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        for (int square : positionNode.getUserPieces().keySet()) {
            Piece userPiece = positionNode.getUserPieces().get(square);
            if (userPiece instanceof Pawn) {
                ((Pawn) userPiece).generateMoves(positionNode, unsafeSquares);
            } else if (userPiece instanceof Knight) {
                ((Knight) userPiece).generateMoves(positionNode, unsafeSquares);
            } else if (userPiece instanceof Pawn) {
                ((Pawn) userPiece).generateMoves(positionNode, unsafeSquares);
            } else if (userPiece instanceof Bishop) {
                ((Bishop) userPiece).generateMoves(positionNode, unsafeSquares);
            } else if (userPiece instanceof Queen) {
                ((Queen) userPiece).generateMoves(positionNode, unsafeSquares);
            } else if (userPiece instanceof King) {
                ((King) userPiece).generateMoves(positionNode, unsafeSquares);
            }
        }

        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();
        for (int square : positionNode.getCpuPieces().keySet()) {
            Piece cpuPiece = positionNode.getCpuPieces().get(square);

            if (cpuPiece instanceof Pawn) {
                ((Pawn) cpuPiece).generateMoves(positionNode, controlledSquares);
            } else if (cpuPiece instanceof Knight) {
                ((Knight) cpuPiece).generateMoves(positionNode, controlledSquares);
            } else if (cpuPiece instanceof Pawn) {
                ((Pawn) cpuPiece).generateMoves(positionNode, controlledSquares);
            } else if (cpuPiece instanceof Bishop) {
                ((Bishop) cpuPiece).generateMoves(positionNode, controlledSquares);
            } else if (cpuPiece instanceof Queen) {
                ((Queen) cpuPiece).generateMoves(positionNode, controlledSquares);
            } else if (cpuPiece instanceof King) {
                ((King) cpuPiece).generateMoves(positionNode, controlledSquares);
            }
        }
    }

}