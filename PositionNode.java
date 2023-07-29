import java.util.*;

public class PositionNode {
    private HashMap<Integer, Piece> userPieces;
    private HashMap<Integer, Piece> cpuPieces;
    private List<PositionNode> children;
    private double moveOrderPriority;

    PositionNode(HashMap<Integer, Piece> myPieces, HashMap<Integer, Piece> opponentPieces, double moveOrderPriority) {
        this.userPieces = myPieces;
        this.cpuPieces = opponentPieces;
        this.moveOrderPriority = moveOrderPriority;
    }

    protected HashMap<Integer, Piece> getUserPieces() {
        return userPieces;
    }

    protected HashMap<Integer, Piece> getCpuPieces() {
        return cpuPieces;
    }

    protected void setUserPieces(HashMap<Integer, Piece> userPieces) {
        this.userPieces = userPieces;
    }

    protected void setCpuPieces(HashMap<Integer, Piece> cpuPieces) {
        this.cpuPieces = cpuPieces;
    }

    protected Double getMoveOrderPriority() {
        return moveOrderPriority;
    }

    protected List<PositionNode> getChildren() {
        return children;
    }

    protected void generateUserPossibleMoves(HashSet<Integer> controlledStuff) {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

        for (Piece cpuPiece : cpuPieces.values()) {
            if (cpuPiece instanceof Pawn) {
                ((Pawn) cpuPiece).generateMoves(cpuPieces, userPieces, unsafeSquares);
            } else if (cpuPiece instanceof Knight) {
                ((Knight) cpuPiece).generateMoves(cpuPieces, userPieces, unsafeSquares);
            } else if (cpuPiece instanceof Bishop) {
                ((Bishop) cpuPiece).generateMoves(cpuPieces, userPieces, unsafeSquares);
            } else if (cpuPiece instanceof Rook) {
                ((Rook) cpuPiece).generateMoves(cpuPieces, userPieces, unsafeSquares);
            } else if (cpuPiece instanceof Queen) {
                ((Queen) cpuPiece).generateMoves(cpuPieces, userPieces, unsafeSquares);
            } else if (cpuPiece instanceof King) {
                ((King) cpuPiece).generateMoves(cpuPieces, userPieces, unsafeSquares, controlledSquares);
            }
        }

        King userKing = null; // init
        for (Piece userPiece : userPieces.values()) {
            if (userPiece instanceof Pawn) {
                ((Pawn) userPiece).generateMoves(userPieces, cpuPieces, controlledSquares);
            } else if (userPiece instanceof Knight) {
                ((Knight) userPiece).generateMoves(userPieces, cpuPieces, controlledSquares);
            } else if (userPiece instanceof Bishop) {
                ((Bishop) userPiece).generateMoves(userPieces, cpuPieces, controlledSquares);
            } else if (userPiece instanceof Rook) {
                ((Rook) userPiece).generateMoves(userPieces, cpuPieces, controlledSquares);
            } else if (userPiece instanceof Queen) {
                ((Queen) userPiece).generateMoves(userPieces, cpuPieces, controlledSquares);
            } else if (userPiece instanceof King) {
                userKing = (King) userPiece;
                userKing.generateMoves(userPieces, cpuPieces, controlledSquares, unsafeSquares);
            }
        }

        // TEMPORARY
        for (int key : controlledSquares.keySet()) {
            if (controlledSquares.get(key) == null) {
                continue;
            }
            controlledStuff.add(key);
            ;
        }

        if (userKing != null && !unsafeSquares.containsKey(userKing.getSquare())) {
            userKing.checkCastle(userPieces, cpuPieces, unsafeSquares);
        }
    }

    protected void clearMoves() {
        for (int square : cpuPieces.keySet()) {
            Piece cpuPiece = cpuPieces.get(square);
            cpuPiece.clear();
        }

        for (int square : userPieces.keySet()) {
            Piece userPiece = userPieces.get(square);
            userPiece.clear();
        }
    }

    protected void searchCpuBestMove() {
        PositionNode bestPosition = null;
        double bestEval = 999;
        for (PositionNode child : getChildren()) {
            double eval = alphaBetaPruning(child, 1, 0, 0, false);
            if (eval < bestEval) {
                bestPosition = child;
            }
        }
    
        setUserPieces(bestPosition.getUserPieces());
        setCpuPieces(bestPosition.getCpuPieces());
    }

    private Double alphaBetaPruning(PositionNode currentPosition, int depth, double alpha, double beta, boolean maximizingUser) {
        if (depth == 0) {
            if (maximizingUser) {
                return staticEvaluation(currentPosition.getUserPieces(), currentPosition.getCpuPieces());
            } else {
                return staticEvaluation(currentPosition.getCpuPieces(), currentPosition.getUserPieces());
            }
        }

        if (maximizingUser) {
            double maxEval = -999;
            currentPosition.branchNewMoves(userPieces, cpuPieces);
            for (PositionNode child : currentPosition.getChildren()) {
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
            currentPosition.branchNewMoves(cpuPieces, userPieces);
            for (PositionNode child : currentPosition.getChildren()) {
                double eval = alphaBetaPruning(child, depth - 1, alpha, beta, true);
                beta = Math.max(minEval, eval);
                alpha = Math.max(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    protected void branchNewMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces) {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

        for (int square : teamPieces.keySet()) {
            Piece teamPiece = teamPieces.get(square);
            if (teamPiece instanceof Pawn) {
                ((Pawn) teamPiece).generateMoves(teamPieces, opponentPieces, unsafeSquares);
            } else if (teamPiece instanceof Knight) {
                ((Knight) teamPiece).generateMoves(teamPieces, opponentPieces, unsafeSquares);
            } else if (teamPiece instanceof Bishop) {
                ((Bishop) teamPiece).generateMoves(teamPieces, opponentPieces, unsafeSquares);
            } else if (teamPiece instanceof Rook) {
                ((Rook) teamPiece).generateMoves(teamPieces, opponentPieces, unsafeSquares);
            } else if (teamPiece instanceof Queen) {
                ((Queen) teamPiece).generateMoves(teamPieces, opponentPieces, unsafeSquares);
            } else if (teamPiece instanceof King) {
                ((King) teamPiece).generateMoves(teamPieces, opponentPieces, unsafeSquares, controlledSquares);
            }
        }

        King teamKing = null; // init
        for (int square : opponentPieces.keySet()) {
            Piece opponentPiece = opponentPieces.get(square);

            if (opponentPiece instanceof Pawn) {
                ((Pawn) opponentPiece).generateMoves(opponentPieces, teamPieces, controlledSquares);
            } else if (opponentPiece instanceof Knight) {
                ((Knight) opponentPiece).generateMoves(opponentPieces, teamPieces, controlledSquares);
            } else if (opponentPiece instanceof Bishop) {
                ((Bishop) opponentPiece).generateMoves(opponentPieces, teamPieces, controlledSquares);
            } else if (opponentPiece instanceof Rook) {
                ((Rook) opponentPiece).generateMoves(opponentPieces, teamPieces, controlledSquares);
            } else if (opponentPiece instanceof Queen) {
                ((Queen) opponentPiece).generateMoves(opponentPieces, teamPieces, controlledSquares);
            } else if (opponentPiece instanceof King) {
                teamKing = (King) opponentPiece;
                teamKing.generateMoves(opponentPieces, teamPieces, controlledSquares, unsafeSquares);
            }
        }

        if (teamKing != null && !unsafeSquares.containsKey(teamKing.getSquare())) {
            teamKing.checkCastle(opponentPieces, teamPieces, unsafeSquares);
        }

        // evaluate captures
        for (int square : opponentPieces.keySet()) {
            Piece opponentPiece = opponentPieces.get(square);
            if (opponentPiece.getCaptures() == null) {
                continue;
            }
            for (int captureSquare : opponentPiece.getCaptures()) {
                double initialCaptureVal = 0;
                if (teamPieces.containsKey(captureSquare)) {
                    initialCaptureVal = teamPieces.get(captureSquare).getValue();
                } else {
                    int diff = Math.abs(captureSquare - square);
                    int directionMultiplier = 1;
                    if (diff < 0) {
                        directionMultiplier = -1;
                    }

                    if (diff == 7) {
                        initialCaptureVal = teamPieces.get(captureSquare + directionMultiplier).getValue();
                    } else if (diff == 9) {
                        initialCaptureVal = teamPieces.get(captureSquare - directionMultiplier).getValue();
                    }
                }
                double captureVal = 0;
                if (unsafeSquares.get(captureSquare) == null) {
                    captureVal = initialCaptureVal;
                } else {
                    captureVal = evaluateTrade(captureSquare, initialCaptureVal, controlledSquares.get(captureSquare),
                            unsafeSquares.get(captureSquare)) + 0.1;
                }
                opponentPiece.getPossibleMoves().put(captureSquare, captureVal);
            }
        }
        /*
         * HashSet<Piece> unsafePieces;
         * // evaluate attacks and generate unsafe pieces
         * for (int opponentSquare : teamPieces.keySet()) {
         * Piece teamPiece = teamPieces.get(opponentSquare);
         * teamPiece.searchLongRangeThreats(); // add value
         * teamPiece.searchShortRangeThreats(); // add value
         * if (teamPiece.getCaptures() == null) {
         * continue;
         * }
         * 
         * 
         * for (int captureSquare : teamPiece.getCaptures()) {
         * // evaluate capture and add unsafe piece if captureVal > 0
         * }
         * }
         */

        // unsafe piece evasions

        // defend unsafe pieces

        // subtract moves for pinned pieces (except if it leads to a check)

        // add children nodes

        for (Piece opponentPiece : opponentPieces.values()) {
            if (opponentPiece.getPossibleMoves() == null) {
                continue;
            }

            for (int newSquare : opponentPiece.getPossibleMoves().keySet()) {
                double moveVal = opponentPiece.getPossibleMoves().get(newSquare);
                addChild(opponentPiece, newSquare, moveVal, teamPieces, opponentPieces);
            }
        }
    }

    private Double evaluateTrade(int tradingSquare, double initialCaptureVal, List<Piece> teamPieces,
            List<Piece> opponentPieces) {
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

            if (opponentPiecesCopy.size() == 1 || (teamPiecesCopy.get(1).getValue() <= opponentPiecesCopy.get(1).getValue())) {
                captureVal += opponentPiecesCopy.get(0).getValue();
                opponentPiecesCopy.remove(0);
            } else { // team should not capture back
                break;
            }
        }

        return captureVal;
    }

    private Double staticEvaluation(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces) {
        double staticEval = 0;
        for (Piece teamPiece : teamPieces.values()) {
            staticEval += teamPiece.getValue();
        }

        for (Piece opponentPiece : opponentPieces.values()) {
            staticEval -= opponentPiece.getValue();
        }

        return staticEval;
    }

    private void addChild(Piece cpuPieceToMove, int newSquare, double moveOrderPriority, HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces) {
        teamPieces.put(newSquare, cpuPieceToMove);
        teamPieces.remove(cpuPieceToMove.getSquare());
        cpuPieceToMove.setSquare(newSquare);
        if (cpuPieceToMove.getCaptures() != null && cpuPieceToMove.getCaptures().contains(newSquare)) {
            if (opponentPieces.containsKey(newSquare)) {
                opponentPieces.remove(newSquare);
            } else { // en passant
                int diff = Math.abs(newSquare - cpuPieceToMove.getSquare());
                int directionMultiplier = 1;
                if (diff < 0) {
                    directionMultiplier = -1;
                }

                if (diff == 7) {
                    opponentPieces.remove(newSquare + directionMultiplier);
                } else if (diff == 9) {
                    opponentPieces.remove(newSquare - directionMultiplier);
                }
            }
        }

        // castle
        if (cpuPieceToMove instanceof King && ((King) cpuPieceToMove).getPossibleCastles() != null
                && ((King) cpuPieceToMove).getPossibleCastles().contains(newSquare)) {
            Rook selectedRook = null;
            if (newSquare == 17 || newSquare == 24) {
                selectedRook = (Rook) teamPieces.get(newSquare - 16);
                teamPieces.put(newSquare + 8, selectedRook);
                teamPieces.remove(newSquare - 16);
                selectedRook.setSquare(newSquare + 8);
            } else if (newSquare == 49 || newSquare == 56) {
                selectedRook = (Rook) teamPieces.get(newSquare + 8);
                teamPieces.put(newSquare - 8, selectedRook);
                teamPieces.remove(newSquare + 8);
                selectedRook.setSquare(newSquare - 8);
            }
        }

        PositionNode newChild = new PositionNode(teamPieces, opponentPieces, moveOrderPriority);

        int low = 0;
        int high = children.size() - 1;
        boolean inserted = false;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            double midValue = children.get(mid).getMoveOrderPriority();
            if (midValue == moveOrderPriority) {
                children.add(mid, newChild);
                inserted = true;
                break;
            } else if (midValue < moveOrderPriority) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (inserted) {
            children.add(low, newChild);
        }
    }

}
