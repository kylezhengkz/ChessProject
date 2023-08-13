import java.util.*;

public class PositionNode {
    private HashMap<Integer, Piece> userPieces;
    private HashMap<Integer, Piece> cpuPieces;
    private List<PositionNode> children;
    private double moveOrderPriority;

    private static int tracker = 0;

    PositionNode(HashMap<Integer, Piece> userPieces, HashMap<Integer, Piece> cpuPieces, double moveOrderPriority) {
        this.userPieces = userPieces;
        this.cpuPieces = cpuPieces;
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

    protected void generateUserPossibleMoves() {
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
        tracker = 0;
        PositionNode bestPosition = null;
        double bestEval = 999;
        if (children != null) {
            children.clear();
        }
        branchNewMoves(cpuPieces, userPieces, false);
        for (PositionNode child : getChildren()) {
            double eval = alphaBetaPruning(child, 2, 0, 0, true);
            if (eval < bestEval) {
                bestPosition = child;
            }
        }

        setUserPieces(bestPosition.getUserPieces());
        setCpuPieces(bestPosition.getCpuPieces());

        /*
        System.out.println("RESET");
        for (int square : cpuPieces.keySet()) {
            System.out.println(square);
        }
        */
    }

    private void printPiece(Piece piece) {
        if (piece instanceof Pawn) {
            if (piece.getColor() == GlobalConstants.WHITE) {
                System.out.print("P ");
            } else {
                System.out.print("p ");
            }
        } else if (piece instanceof Bishop) {
            if (piece.getColor() == GlobalConstants.WHITE) {
                System.out.print("B ");
            } else {
                System.out.print("b ");
            }
        } else if (piece instanceof Knight) {
            if (piece.getColor() == GlobalConstants.WHITE) {
                System.out.print("K ");
            } else {
                System.out.print("k ");
            }
        } else if (piece instanceof Rook) {
            if (piece.getColor() == GlobalConstants.WHITE) {
                System.out.print("R ");
            } else {
                System.out.print("r ");
            }
        } else if (piece instanceof Queen) {
            if (piece.getColor() == GlobalConstants.WHITE) {
                System.out.print("Q ");
            } else {
                System.out.print("q ");
            }
        } else if (piece instanceof King) {
            if (piece.getColor() == GlobalConstants.WHITE) {
                System.out.print("K ");
            } else {
                System.out.print("k ");
            }
        }
    }

    private void printPosition(PositionNode position) {
        for (int i = 8; i >= 1; i--) {
            for (int j = i; j <= i + 56; j+=8) {
                if (position.getUserPieces().containsKey(j)) {
                    printPiece(position.getUserPieces().get(j));
                } else if (position.getCpuPieces().containsKey(j)) {
                    printPiece(position.getCpuPieces().get(j));
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("NEW LINE");
        }
    }

    private Double alphaBetaPruning(PositionNode currentPosition, int depth, double alpha, double beta, boolean maximizingUser) {
        if (depth == 0) {
            if (maximizingUser) {
                double staticEval = staticEvaluation(currentPosition.getUserPieces(), currentPosition.getCpuPieces());
                return staticEval;
            } else {
                double staticEval = staticEvaluation(currentPosition.getCpuPieces(), currentPosition.getUserPieces());
                return staticEval;
            }
        }

        if (tracker < 1) {
            System.out.println("CHECK PARENT POSITION");
            printPosition(currentPosition); 
            tracker++;
        }
        if (maximizingUser) {
            double maxEval = -999;
            currentPosition.branchNewMoves(userPieces, cpuPieces, maximizingUser);
            for (PositionNode child : currentPosition.getChildren()) {
                if (tracker <= 1) {
                    System.out.println("CHECK CHILD POSITION");
                    printPosition(child);
                }
;               double eval = alphaBetaPruning(child, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            double minEval = 999;
            currentPosition.branchNewMoves(cpuPieces, userPieces, maximizingUser);
            for (PositionNode child : currentPosition.getChildren()) {
                if (tracker <= 1) {
                    System.out.println("CHECK CHILD POSITION");
                    printPosition(child);
                }
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

    protected void branchNewMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, boolean maximizingUser) {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

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
                    int diff = Math.abs(captureSquare - square);
                    int directionMultiplier = 1;
                    if (diff < 0) {
                        directionMultiplier = -1;
                    }

                    if (diff == 7) {
                        initialCaptureVal = opponentPieces.get(captureSquare + directionMultiplier).getValue();
                    } else if (diff == 9) {
                        initialCaptureVal = opponentPieces.get(captureSquare - directionMultiplier).getValue();
                    }
                }
                double captureVal = 0;
                if (unsafeSquares.get(captureSquare) == null) {
                    captureVal = initialCaptureVal;
                } else {
                    if (controlledSquares.get(captureSquare) == null) {
                        System.out.println("piece that can capture: " + teamPiece.getClass().toString() + " at " + square);
                        System.out.println("capture at: " + captureSquare);
                        System.out.println("HUHHH");
                    }
                    captureVal = evaluateTrade(captureSquare, initialCaptureVal, controlledSquares.get(captureSquare), unsafeSquares.get(captureSquare)) + 0.1;
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
                addChild(teamPiece, newSquare, moveVal, teamPieces, opponentPieces, maximizingUser);
            }
        }

    }

    private Double evaluateTrade(int tradingSquare, double initialCaptureVal, List<Piece> teamPieces, List<Piece> opponentPieces) {
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

    private HashMap<Integer, Piece> deepCopyPieces(HashMap<Integer, Piece> pieces) {
        HashMap<Integer, Piece> newPieces = new HashMap<>();
        for (Map.Entry<Integer, Piece> entry : pieces.entrySet()) {
            int key = entry.getKey();
            Piece originalPiece = entry.getValue();
            Piece newPiece = originalPiece.clone();
            newPieces.put(key, newPiece);
        }
        return newPieces;
    }

    private void addChild(Piece teamPieceToMove, int newSquare, double moveOrderPriority, HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, boolean maximizingUser) {
        Piece newTeamPieceToMove = teamPieceToMove.clone();
        HashMap<Integer, Piece> newTeamPieces = deepCopyPieces(teamPieces);
        HashMap<Integer, Piece> newOpponentPieces = deepCopyPieces(opponentPieces);

        if (newTeamPieceToMove instanceof Pawn) {
            ((Pawn) newTeamPieceToMove).movePawn();
            if ((newSquare - newTeamPieceToMove.getSquare()) == 2) {
                ((Pawn) newTeamPieceToMove).moveTwoSquares();
                int checkOpponentSquare = newSquare + Piece.LEFT;
                if (newOpponentPieces.containsKey(checkOpponentSquare) && newOpponentPieces.get(checkOpponentSquare) instanceof Pawn) {
                    ((Pawn) newOpponentPieces.get(checkOpponentSquare)).setEnPassantLeft(true);
                }
                checkOpponentSquare = newSquare + Piece.RIGHT;
                if (newOpponentPieces.containsKey(checkOpponentSquare) && newOpponentPieces.get(checkOpponentSquare) instanceof Pawn) {
                    ((Pawn) newOpponentPieces.get(checkOpponentSquare)).setEnPassantRight(true);
                }
            }
        } else if (newTeamPieceToMove instanceof King) {
            ((King) newTeamPieceToMove).moveKing();
        } else if (newTeamPieceToMove instanceof Rook) {
            ((Rook) newTeamPieceToMove).moveRook();
        }

        if (children == null) {
            children = new ArrayList<>();
        }

        PositionNode newChild = null;
        if (maximizingUser) {
            newChild = new PositionNode(newTeamPieces, newOpponentPieces, moveOrderPriority);
        } else {
            newChild = new PositionNode(newOpponentPieces, newTeamPieces, moveOrderPriority);
        }

        newTeamPieces.put(newSquare, newTeamPieceToMove);
        newTeamPieces.remove(newTeamPieceToMove.getSquare());

        if (newTeamPieceToMove.getCaptures() != null && newTeamPieceToMove.getCaptures().contains(newSquare)) {
            if (newOpponentPieces.containsKey(newSquare)) {
                newOpponentPieces.remove(newSquare);
            } else { // en passant
                int diff = Math.abs(newSquare - newTeamPieceToMove.getSquare());
                int directionMultiplier = 1;
                if (diff < 0) {
                    directionMultiplier = -1;
                }

                if (diff == 7) {
                    newOpponentPieces.remove(newSquare + directionMultiplier);
                } else if (diff == 9) {
                    newOpponentPieces.remove(newSquare - directionMultiplier);
                }
            }
        }

        newTeamPieceToMove.setSquare(newSquare);

        // castle
        if (newTeamPieceToMove instanceof King && ((King) newTeamPieceToMove).getPossibleCastles() != null && ((King) newTeamPieceToMove).getPossibleCastles().contains(newSquare)) {
            Rook selectedRook = null;
            if (newSquare == 17 || newSquare == 24) {
                selectedRook = (Rook) newTeamPieces.get(newSquare - 16);
                newTeamPieces.put(newSquare + 8, selectedRook);
                newTeamPieces.remove(newSquare - 16);
                selectedRook.setSquare(newSquare + 8);
            } else if (newSquare == 49 || newSquare == 56) {
                selectedRook = (Rook) newTeamPieces.get(newSquare + 8);
                newTeamPieces.put(newSquare - 8, selectedRook);
                newTeamPieces.remove(newSquare + 8);
                selectedRook.setSquare(newSquare - 8);
            }
        }

        newChild.clearMoves();

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

        if (!inserted) {
            children.add(low, newChild);
        }
    }

}