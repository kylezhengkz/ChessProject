import java.util.*;

public class PositionNode {
    private HashMap<Integer, Piece> userPieces;
    private HashMap<Integer, Piece> cpuPieces;
    private List<PositionNode> children;

    PositionNode(HashMap<Integer, Piece> myPieces, HashMap<Integer, Piece> opponentPieces) {
        this.userPieces = myPieces;
        this.cpuPieces = opponentPieces;
    }

    protected HashMap<Integer, Piece> getUserPieces() {
        return userPieces;
    }

    protected HashMap<Integer, Piece> getCpuPieces() {
        return cpuPieces;
    }

    protected List<PositionNode> getChildren() {
        return children;
    }

    protected void generateUserPossibleMoves(HashSet<Integer> controlledStuff) {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

        for (int square : cpuPieces.keySet()) {
            Piece cpuPiece = cpuPieces.get(square);
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
        for (int square : userPieces.keySet()) {
            Piece userPiece = userPieces.get(square);

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
;       }
        
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

    protected int[] searchCpuBestMove() {
        HashMap<Integer, List<Piece>> unsafeSquares = new HashMap<>();
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();

        for (int square : userPieces.keySet()) {
            Piece userPiece = userPieces.get(square);
            if (userPiece instanceof Pawn) {
                ((Pawn) userPiece).generateMoves(userPieces, cpuPieces, unsafeSquares);
            } else if (userPiece instanceof Knight) {
                ((Knight) userPiece).generateMoves(userPieces, cpuPieces, unsafeSquares);
            } else if (userPiece instanceof Bishop) {
                ((Bishop) userPiece).generateMoves(userPieces, cpuPieces, unsafeSquares);
            } else if (userPiece instanceof Rook) {
                ((Rook) userPiece).generateMoves(userPieces, cpuPieces, unsafeSquares);
            } else if (userPiece instanceof Queen) {
                ((Queen) userPiece).generateMoves(userPieces, cpuPieces, unsafeSquares);
            } else if (userPiece instanceof King) {
                ((King) userPiece).generateMoves(userPieces, cpuPieces, unsafeSquares, controlledSquares);
            }
        }

        King cpuKing = null; // init
        for (int square : cpuPieces.keySet()) {
            Piece cpuPiece = cpuPieces.get(square);

            if (cpuPiece instanceof Pawn) {
                ((Pawn) cpuPiece).generateMoves(cpuPieces, userPieces, controlledSquares);
            } else if (cpuPiece instanceof Knight) {
                ((Knight) cpuPiece).generateMoves(cpuPieces, userPieces, controlledSquares);
            } else if (cpuPiece instanceof Bishop) {
                ((Bishop) cpuPiece).generateMoves(cpuPieces, userPieces, controlledSquares);
            } else if (cpuPiece instanceof Rook) {
                ((Rook) cpuPiece).generateMoves(cpuPieces, userPieces, controlledSquares);
            } else if (cpuPiece instanceof Queen) {
                ((Queen) cpuPiece).generateMoves(cpuPieces, userPieces, controlledSquares);
            } else if (cpuPiece instanceof King) {
                cpuKing = (King) cpuPiece;
                cpuKing.generateMoves(cpuPieces, userPieces, controlledSquares, unsafeSquares);
            }
        }

        if (cpuKing != null && !unsafeSquares.containsKey(cpuKing.getSquare())) {
            cpuKing.checkCastle(cpuPieces, userPieces, unsafeSquares);
        }

        // evaluate captures
        for (int square : cpuPieces.keySet()) {
            Piece cpuPiece = cpuPieces.get(square);
            if (cpuPiece.getCaptures() == null) {
                continue;
            }
            for (int captureSquare : cpuPiece.getCaptures()) {
                double initialCaptureVal = 0;
                if (userPieces.containsKey(captureSquare)) {
                    initialCaptureVal = userPieces.get(captureSquare).getValue();
                } else {
                    int diff = Math.abs(captureSquare - square);
                    int directionMultiplier = 1;
                    if (diff < 0) {
                        directionMultiplier = -1;
                    }

                    if (diff == 7) {
                        initialCaptureVal = userPieces.get(captureSquare + directionMultiplier).getValue();
                    } else if (diff == 9) {
                        initialCaptureVal = userPieces.get(captureSquare - directionMultiplier).getValue();
                    }
                }
                double captureVal = 0;
                if (unsafeSquares.get(captureSquare) == null) {
                    captureVal = initialCaptureVal;
                } else {
                    captureVal = evaluateTrade(captureSquare, initialCaptureVal, controlledSquares.get(captureSquare), unsafeSquares.get(captureSquare)) + 0.1;
                }
                cpuPiece.getPossibleMoves().put(captureSquare, captureVal);
            }
        }
        
        /*
        HashSet<Piece> unsafePieces;
        // evaluate attacks and generate unsafe pieces
        for (int opponentSquare : userPieces.keySet()) {
            Piece userPiece = userPieces.get(opponentSquare);
            userPiece.searchLongRangeThreats(); // add value
            userPiece.searchShortRangeThreats(); // add value
            if (userPiece.getCaptures() == null) {
                continue;
            }

            for (int captureSquare : userPiece.getCaptures()) {
                // evaluate capture and add unsafe piece if captureVal > 0
            }
        }
        */

        // unsafe piece evasions

        // defend unsafe pieces

        // subtract moves for pinned pieces (except if it leads to a check)

        // add children nodes

        // TEMPORARY
        int[] moveArr = new int[2];
        double max = -1;
        for (int square : cpuPieces.keySet()) {
            Piece cpuPiece = cpuPieces.get(square);
            if (cpuPiece.getPossibleMoves() == null) {
                continue;
            }
            for (int newSquare : cpuPiece.getPossibleMoves().keySet()) {
                double moveVal = cpuPiece.getPossibleMoves().get(newSquare);
                if (moveVal > max) {
                    moveArr[0] = square;
                    moveArr[1] = newSquare;
                    max = moveVal;
                }
            }
        }

        return moveArr;
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

            if (opponentPiecesCopy.size() == 1 || (teamPiecesCopy.get(1).getValue() <= opponentPiecesCopy.get(1).getValue())) {
                captureVal += opponentPiecesCopy.get(0).getValue();
                opponentPiecesCopy.remove(0);
            } else { // team should not capture back
                break;
            }
        }

        return captureVal;

    }

}
