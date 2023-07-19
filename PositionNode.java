import java.util.*;
public class PositionNode {
    private HashMap<ArrayWrapper, Piece> myPieces;
    private HashMap<ArrayWrapper, Piece> opponentPieces;
    private List<PositionNode> children;

    PositionNode(HashMap<ArrayWrapper, Piece> myPieces, HashMap<ArrayWrapper, Piece> opponentPieces) {
        this.myPieces = myPieces;
        this.opponentPieces = opponentPieces;
    }

    public HashMap<ArrayWrapper, Piece> getMyPieces() {
        return myPieces;
    }

    public HashMap<ArrayWrapper, Piece> getOpponentPieces() {
        return opponentPieces;
    }

    public List<PositionNode> getChildren() {
        return children;
    }
}