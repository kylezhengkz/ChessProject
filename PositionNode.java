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

    protected void addChild(Piece teamPieceToMove, int newSquare, double moveOrderPriority, HashMap<Integer, Piece> teamPieces, HashMap<Integer, Piece> opponentPieces, boolean cpuTurn) {
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
            if (cpuPiece instanceof King && ((King) cpuPiece).getPossibleCastles() != null) {
                ((King) cpuPiece).getPossibleCastles().clear();
            }
        }

        for (int square : userPieces.keySet()) {
            Piece userPiece = userPieces.get(square);
            userPiece.clear();
            if (userPiece instanceof King && ((King) userPiece).getPossibleCastles() != null) {
                ((King) userPiece).getPossibleCastles().clear();
            }
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