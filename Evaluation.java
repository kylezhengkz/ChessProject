import java.util.*;

public class Evaluation {

    public static Double staticEvaluation(PositionNode position, boolean cpuTurn) {
        double staticEval = 0;
        if (cpuTurn) {
            for (Piece cpuPiece : position.getCpuPieces().values()) {
                staticEval -= cpuPiece.getValue();
            }
            for (Piece opponentPiece : position.getUserPieces().values()) {
                staticEval += opponentPiece.getValue();
            }
        } else {
            for (Piece cpuPiece : position.getCpuPieces().values()) {
                staticEval += cpuPiece.getValue();
            }
            for (Piece opponentPiece : position.getUserPieces().values()) {
                staticEval -= opponentPiece.getValue();
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
                break;
            }

            if (opponentPiecesCopy.get(0).getValue() <= teamPiecesCopy.get(1).getValue()) {
                captureVal -= teamPiecesCopy.get(0).getValue();
                teamPiecesCopy.remove(0);
            } else { // opponent should not capture back
                break;
            }

            if (opponentPiecesCopy.size() == 1 || (teamPiecesCopy.get(0).getValue() <= opponentPiecesCopy.get(1).getValue())) {
                captureVal += opponentPiecesCopy.get(0).getValue();
                opponentPiecesCopy.remove(0);
            } else { // team should not capture back
                break;
            }

        }

        return captureVal;
    }

}