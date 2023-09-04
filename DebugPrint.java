import java.util.HashMap;

public class DebugPrint {

    private static void printPiece(Piece piece) {
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

    public static void printPosition(PositionNode position) {
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
            System.out.println();
        }
    }

    public static void printPositionV2(HashMap<Integer, Piece> userPieces, HashMap<Integer, Piece> cpuPieces) {
        for (int i = 8; i >= 1; i--) {
            for (int j = i; j <= i + 56; j+=8) {
                if (userPieces.containsKey(j)) {
                    printPiece(userPieces.get(j));
                } else if (cpuPieces.containsKey(j)) {
                    printPiece(cpuPieces.get(j));
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

}