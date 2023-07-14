import java.util.*;
public class PositionNode {
    private HashMap<int[], Piece> myPieces;
    private HashMap<int[], Piece> opponentPieces;
    private List<PositionNode> children;

    public HashMap<int[], Piece> getMyPieces() {
        return myPieces;
    }

    public HashMap<int[], Piece> getOpponentPieces() {
        return opponentPieces;
    }

    public List<PositionNode> getChildren() {
        return children;
    }
}