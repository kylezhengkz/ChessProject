import java.util.*;

public class PositionNode {
    private HashMap<Integer, Piece> userPieces;
    private HashMap<Integer, Piece> cpuPieces;
    private List<PositionNode> children;
    private double moveOrderPriority;

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

    protected void branchNewMoves(HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, boolean cpuTurn) {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

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
                addChild(teamPiece, newSquare, moveVal, teamPieces, opponentPieces, cpuTurn);
            }
        }
    }

    private void addChild(Piece teamPieceToMove, int newSquare, double moveOrderPriority, HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, boolean cpuTurn) {
        Piece newTeamPieceToMove = teamPieceToMove.clone();
        HashMap<Integer, Piece> newTeamPieces = deepCopyPieces(teamPieces);
        HashMap<Integer, Piece> newOpponentPieces = deepCopyPieces(opponentPieces);

        // update statuses
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
        if (cpuTurn) {
            newChild = new PositionNode(newOpponentPieces, newTeamPieces, moveOrderPriority);
        } else {
            newChild = new PositionNode(newTeamPieces, newOpponentPieces, moveOrderPriority);
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

}