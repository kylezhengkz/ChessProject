import java.util.*;

public class Game {

    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public static PositionNode currentPosition;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input;
        int color = -1;
        System.out.print("Enter 'W' to play as white, and 'B' to play as black: ");
        input = sc.nextLine();
        if (input.equals("W")) {
            color = WHITE;
        } else if (input.equals("B")) {
            color = BLACK;
        } else {
            System.out.println("Invalid input. Terminating program.");
            sc.close();
            System.exit(0);
        }

        initStartingPosition(color);
        printBoard();
    }

    public static String integerSquareToSquareName(int square) {
        int row = square % 8;
        if (row == 0) {
            row = 8;
        }
        
        int col = 0;
        if (square % 8 == 0) {
            col = square / 8 - 1;
        } else {
            col = square / 8;
        }

        char colName = (char) ('a' + col);
        return colName + Integer.toString(row);
    }

    private static void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < 15; i++) {
            System.out.print(".");
        }
        System.out.println();

        for (int i = 8; i >= 1; i--) {
            System.out.print(i + "|");
            for (int square = i; square <= i + 56; square += 8) {
                Piece selectedPiece = null;
                if (currentPosition.getUserPieces().containsKey(square)) {
                    selectedPiece = currentPosition.getUserPieces().get(square);
                }

                if (currentPosition.getCpuPieces().containsKey(square)) {
                    selectedPiece = currentPosition.getCpuPieces().get(square);
                }

                if (selectedPiece instanceof Pawn) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("P");
                    } else {
                        System.out.print("p");
                    }
                } else if (selectedPiece instanceof Bishop) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("B");
                    } else {
                        System.out.print("b");
                    }
                } else if (selectedPiece instanceof Knight) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("K");
                    } else {
                        System.out.print("k");
                    }
                } else if (selectedPiece instanceof Rook) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("R");
                    } else {
                        System.out.print("r");
                    }
                } else if (selectedPiece instanceof Queen) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("Q");
                    } else {
                        System.out.print("q");
                    }
                } else if (selectedPiece instanceof King) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("A");
                    } else {
                        System.out.print("a");
                    }
                } else {
                    System.out.print(" ");
                }

                if (square < i + 56) {
                    System.out.print("|");
                }
            }
            System.out.print("|");
            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < 15; i++) {
            System.out.print(".");
        }
        System.out.println();

        // print letters
        System.out.print("  ");
        for (int i = 65; i <= 72; i++) {
            System.out.print((char) i + " ");
        }
        System.out.println();
        
    }

    private static void initStartingPosition(int color) {
        HashMap<Integer, Piece> userStartingPosition = new HashMap<>();
        HashMap<Integer, Piece> cpuStartingPosition = new HashMap<>();

        // insert pawns
        for (int square = 2; square <= 58; square += 8) {
            if (color == WHITE) {
                userStartingPosition.put(square, new Pawn(1, square, WHITE));
            } else {
                cpuStartingPosition.put(square, new Pawn(1, square, WHITE));
            }
        }

        for (int square = 7; square <= 63; square += 8) {
            if (color == WHITE) {
                cpuStartingPosition.put(square, new Pawn(1, square, BLACK));
            } else {
                userStartingPosition.put(square, new Pawn(1, square, BLACK));
            }
        }

        // insert rooks
        if (color == WHITE) {
            userStartingPosition.put(1, new Rook(5, 1, WHITE));
            userStartingPosition.put(57, new Rook(5, 57, WHITE));
            cpuStartingPosition.put(8, new Rook(5, 8, BLACK));
            cpuStartingPosition.put(64, new Rook(5, 64, BLACK));
        } else {
            cpuStartingPosition.put(1, new Rook(5, 1, WHITE));
            cpuStartingPosition.put(57, new Rook(5, 57, WHITE));
            userStartingPosition.put(8, new Rook(5, 8, BLACK));
            userStartingPosition.put(64, new Rook(5, 64, BLACK));
        }

        // insert knights
        if (color == WHITE) {
            userStartingPosition.put(9, new Knight(3, 9, WHITE));
            userStartingPosition.put(49, new Knight(3, 49, WHITE));
            cpuStartingPosition.put(16, new Knight(3, 16, BLACK));
            cpuStartingPosition.put(56, new Knight(3, 56, BLACK));
        } else {
            cpuStartingPosition.put(9, new Knight(3, 9, WHITE));
            cpuStartingPosition.put(49, new Knight(3, 49, WHITE));
            userStartingPosition.put(16, new Knight(3, 16, BLACK));
            userStartingPosition.put(56, new Knight(3, 56, BLACK));
        }

        // insert bishops
        if (color == WHITE) {
            userStartingPosition.put(17, new Bishop(3, 17, WHITE));
            userStartingPosition.put(41, new Bishop(3, 41, WHITE));
            cpuStartingPosition.put(24, new Bishop(3, 24, BLACK));
            cpuStartingPosition.put(48, new Bishop(3, 48, BLACK));
        } else {
            cpuStartingPosition.put(17, new Bishop(3, 17, WHITE));
            cpuStartingPosition.put(41, new Bishop(3, 41, WHITE));
            userStartingPosition.put(24, new Bishop(3, 24, BLACK));
            userStartingPosition.put(48, new Bishop(3, 48, BLACK));
        }

        // insert queens
        if (color == WHITE) {
            userStartingPosition.put(25, new Queen(9, 25, WHITE));
            cpuStartingPosition.put(32, new Queen(9, 32, BLACK));
        } else {
            cpuStartingPosition.put(25, new Queen(9, 25, WHITE));
            userStartingPosition.put(32, new Queen(9, 32, BLACK));
        }

        // insert kings
        if (color == WHITE) {
            userStartingPosition.put(33, new King(13, 33, WHITE));
            cpuStartingPosition.put(40, new King(13, 40, BLACK));
        } else {
            cpuStartingPosition.put(33, new King(13, 33, WHITE));
            userStartingPosition.put(40, new King(13, 40, BLACK));
        }

        currentPosition = new PositionNode(userStartingPosition, cpuStartingPosition);
    }

}
