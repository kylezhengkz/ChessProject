import java.util.*;
public class PositionNode {
    private HashMap<Integer, Piece> myPieces;
    private HashMap<Integer, Piece> opponentPieces;
    private List<PositionNode> children;

    PositionNode(HashMap<Integer, Piece> myPieces, HashMap<Integer, Piece> opponentPieces) {
        this.myPieces = myPieces;
        this.opponentPieces = opponentPieces;
    }

    public HashMap<Integer, Piece> getMyPieces() {
        return myPieces;
    }

    public HashMap<Integer, Piece> getOpponentPieces() {
        return opponentPieces;
    }

    public List<PositionNode> getChildren() {
        return children;
    }
}