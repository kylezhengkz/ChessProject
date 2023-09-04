public class MoveGeneration {

    public static PositionNode searchCpuBestMove(PositionNode position) {
        System.out.println("BEFORE CPU SEARCH:");
        DebugPrint.printPosition(position);
        PositionNode bestPosition = null;
        double bestEval = 999;

        position.branchNewMoves(position.getCpuPieces(),position.getUserPieces(), true);
        for (PositionNode child : position.getChildren()) {
            System.out.println("CONSIDER CHILD:");
            DebugPrint.printPosition(child);
            double eval = alphaBetaPruning(child, 2, 0, 0, true);
            if (eval < bestEval) {
                bestPosition = child;
                bestEval = eval;
            }
        }

        System.out.println("AFTER CPU SEARCH:");
        DebugPrint.printPosition(bestPosition);
        return bestPosition;
    }

    private static Double alphaBetaPruning(PositionNode position, int depth, double alpha, double beta, boolean cpuTurn) {
        if (depth == 0) {
            if (cpuTurn) {
                double staticEval = Evaluation.staticEvaluation(position, true); // NEGATIVE
                return staticEval;
            } else {
                double staticEval = Evaluation.staticEvaluation(position, false); // POSITIVE
                return staticEval;
            }
        }

        if (cpuTurn) {
            double maxEval = -999;
            position.branchNewMoves(position.getCpuPieces(), position.getUserPieces(), true);
            for (PositionNode child : position.getChildren()) {
                double eval = alphaBetaPruning(child, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            double minEval = 999;
            position.branchNewMoves(position.getUserPieces(), position.getCpuPieces(), false);
            for (PositionNode child : position.getChildren()) {
                double eval = alphaBetaPruning(child, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }
    
}