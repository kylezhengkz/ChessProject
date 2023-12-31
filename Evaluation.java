import java.util.*;

public class Evaluation {

    public static Map<Integer, Double> squareValues = new HashMap<>();
    public static Map<Integer, Double> pawnPosVal = new HashMap<>();

    public static Double staticEvaluation(PositionNode position, boolean cpuPerspective) {
        double staticEval = 0;
        if (cpuPerspective) {
            for (Piece cpuPiece : position.getCpuPieces().values()) {
                staticEval += cpuPiece.getValue();
            }
            for (Piece opponentPiece : position.getUserPieces().values()) {
                staticEval -= opponentPiece.getValue();
            }
        } else {
            for (Piece cpuPiece : position.getCpuPieces().values()) {
                staticEval -= cpuPiece.getValue();
            }
            for (Piece opponentPiece : position.getUserPieces().values()) {
                staticEval += opponentPiece.getValue();
            }
        }
        return staticEval;
    }

    public static Double evaluateTrade(int tradingSquare, double initialCaptureVal, List<Piece> teamPieces, List<Piece> opponentPieces) {
        List<Piece> teamPiecesCopy = new ArrayList<>(teamPieces);
        List<Piece> opponentPiecesCopy = new ArrayList<>(opponentPieces);

        double captureVal = initialCaptureVal;
        while (true) {

            if (opponentPiecesCopy.size() == 0) { // opponent cannot capture back
                break;
            }

            if (teamPiecesCopy.size() == 1) { // final capture
                captureVal -= teamPiecesCopy.get(0).getValue();
                teamPiecesCopy.remove(0);
                break;
            } else if (opponentPiecesCopy.get(0).getValue() <= teamPiecesCopy.get(1).getValue() && opponentPiecesCopy.size() > 1) {
                captureVal -= teamPiecesCopy.get(0).getValue();
                teamPiecesCopy.remove(0);
            } else { // opponent should not capture back
                break;
            }

            if (opponentPiecesCopy.size() == 1) {
                captureVal += opponentPiecesCopy.get(0).getValue();
                opponentPiecesCopy.remove(0);
                break;
            } else if (teamPiecesCopy.get(0).getValue() <= opponentPiecesCopy.get(1).getValue() && teamPiecesCopy.size() > 1) {
                captureVal += opponentPiecesCopy.get(0).getValue();
                opponentPiecesCopy.remove(0);
            } else { // team should not capture back
                break;
            }

        }

        return captureVal;
    }

}