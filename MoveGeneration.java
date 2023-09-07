import java.util.HashMap;
import java.util.List;

public class MoveGeneration {

    public static PositionNode searchCpuBestMove(PositionNode position) {
        PositionNode bestPosition = null;
        double bestEval = -999;

        if (position.getChildren() != null) {
            position.getChildren().clear();
        }
        branchNewMoves(position, true);
        for (PositionNode child : position.getChildren()) {
            double eval = alphaBetaPruning(child, 3, -999, 999, true);
            if (eval > bestEval) {
                bestPosition = child;
                bestEval = eval;
            }
        }
        return bestPosition;
    }

    private static Double alphaBetaPruning(PositionNode position, int depth, double alpha, double beta, boolean cpuTurn) {
        if (depth == 0) {
            double staticEval = Evaluation.staticEvaluation(position, true);
            return staticEval;
        }

        if (cpuTurn) {
            double maxEval = -999;
            branchNewMoves(position, true);
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
            branchNewMoves(position, false);
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

    private static void branchNewMoves(PositionNode position, boolean cpuTurn) {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

        HashMap<Integer, Piece> teamPieces;
        HashMap<Integer, Piece> opponentPieces;

        if (cpuTurn) {
            teamPieces = position.getCpuPieces();
            opponentPieces = position.getUserPieces();
        } else {
            teamPieces = position.getUserPieces();
            opponentPieces = position.getCpuPieces();
        }

        // identify all possible moves and controlled squares
        for (int square : opponentPieces.keySet()) {
            Piece opponentPiece = opponentPieces.get(square);
            if (opponentPiece instanceof Pawn) {
                ((Pawn) opponentPiece).generateMoves(opponentPieces, teamPieces, unsafeSquares);
            } else if (opponentPiece instanceof Knight) {
                ((Knight) opponentPiece).generateMoves(opponentPieces, teamPieces, unsafeSquares);
            } else if (opponentPiece instanceof Bishop) {
                ((Bishop) opponentPiece).generateMoves(opponentPieces, teamPieces, unsafeSquares);
            } else if (opponentPiece instanceof Rook) {
                ((Rook) opponentPiece).generateMoves(opponentPieces, teamPieces, unsafeSquares);
            } else if (opponentPiece instanceof Queen) {
                ((Queen) opponentPiece).generateMoves(opponentPieces, teamPieces, unsafeSquares);
            } else if (opponentPiece instanceof King) {
                ((King) opponentPiece).generateMoves(opponentPieces, teamPieces, unsafeSquares, controlledSquares);
            }
        }

        King teamKing = null; // init

        // identify all possible moves and controlled squares
        for (int square : teamPieces.keySet()) {
            Piece teamPiece = teamPieces.get(square);

            if (teamPiece instanceof Pawn) {
                ((Pawn) teamPiece).generateMoves(teamPieces, opponentPieces, controlledSquares);
            } else if (teamPiece instanceof Knight) {
                ((Knight) teamPiece).generateMoves(teamPieces, opponentPieces, controlledSquares);
            } else if (teamPiece instanceof Bishop) {
                ((Bishop) teamPiece).generateMoves(teamPieces, opponentPieces, controlledSquares);
            } else if (teamPiece instanceof Rook) {
                ((Rook) teamPiece).generateMoves(teamPieces, opponentPieces, controlledSquares);
            } else if (teamPiece instanceof Queen) {
                ((Queen) teamPiece).generateMoves(teamPieces, opponentPieces, controlledSquares);
            } else if (teamPiece instanceof King) {
                teamKing = (King) teamPiece;
                teamKing.generateMoves(teamPieces, opponentPieces, controlledSquares, unsafeSquares);
            }
        }

        // check for castling
        if (teamKing != null && !unsafeSquares.containsKey(teamKing.getSquare())) {
            teamKing.checkCastle(teamPieces, opponentPieces, unsafeSquares);
        }

        // evaluate captures
        for (int square : teamPieces.keySet()) {
            Piece teamPiece = teamPieces.get(square);
            if (teamPiece.getCaptures() == null) {
                continue;
            }
            for (int captureSquare : teamPiece.getCaptures()) {
                double initialCaptureVal = 0;
                if (opponentPieces.containsKey(captureSquare)) {
                    initialCaptureVal = opponentPieces.get(captureSquare).getValue();
                } else {
                    int diff = captureSquare - square;
                    int directionMultiplier = 1;
                    if (diff < 0) {
                        directionMultiplier = -1;
                    }

                    if (Math.abs(diff) == 7) {
                        initialCaptureVal = opponentPieces.get(captureSquare + directionMultiplier).getValue();
                    } else if (Math.abs(diff) == 9) {
                        initialCaptureVal = opponentPieces.get(captureSquare - directionMultiplier).getValue();
                    }
                }
                double captureVal = 0;
                if (unsafeSquares.get(captureSquare) == null) {
                    captureVal = initialCaptureVal;
                } else {
                    // if (controlledSquares.get(captureSquare) == null) // BUG
                    captureVal = Evaluation.evaluateTrade(captureSquare, initialCaptureVal, controlledSquares.get(captureSquare), unsafeSquares.get(captureSquare)) + 0.1;
                }
                teamPiece.getPossibleMoves().put(captureSquare, captureVal);
            }
        }

        // short range and long range attacks

        // unsafe piece evasions

        // defend unsafe pieces

        // subtract moves for pinned pieces (except if it leads to a check)

        // add children nodes
        for (Piece teamPiece : teamPieces.values()) {
            if (teamPiece.getPossibleMoves() == null) {
                continue;
            }
            for (int newSquare : teamPiece.getPossibleMoves().keySet()) {
                double moveVal = teamPiece.getPossibleMoves().get(newSquare);
                position.addChild(teamPiece, newSquare, moveVal, teamPieces, opponentPieces, cpuTurn);
            }
        }
    }
    
}