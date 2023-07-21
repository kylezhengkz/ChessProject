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
                if (currentPosition.getMyPieces().containsKey(square)) {
                    selectedPiece = currentPosition.getMyPieces().get(square);
                }

                if (currentPosition.getOpponentPieces().containsKey(square)) {
                    selectedPiece = currentPosition.getOpponentPieces().get(square);
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

        // analysis
        HashMap<Integer, List<Piece>> controlledSquares = new HashMap<>();
        for (int squarePos : currentPosition.getMyPieces().keySet()) {
            Piece piece = currentPosition.getMyPieces().get(squarePos);
            if (piece instanceof Pawn) {
                ((Pawn) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Knight) {
                ((Knight) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Pawn) {
                ((Pawn) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Bishop) {
                ((Bishop) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Queen) {
                ((Queen) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof King) {
                ((King) piece).generateMoves(currentPosition, controlledSquares);
            }
        }

        for (int squarePos : currentPosition.getMyPieces().keySet()) {
            Piece piece = currentPosition.getMyPieces().get(squarePos);
            System.out.print(piece.getClass().toString() + ": ");
            System.out.println(integerSquareToSquareName(piece.getSquare()));
            System.out.println("Possible Moves");
            if (piece.getPossibleMoves() != null) {
                for (int possibleSquare : piece.getPossibleMoves()) {
                    System.out.println(integerSquareToSquareName(possibleSquare));
                }
            }
            System.out.println("Possible Captures");
            if (piece.getCaptures() != null) {
                for (int captureSquare : piece.getCaptures()) {
                    System.out.println(integerSquareToSquareName(captureSquare));
                }
            }
            System.out.println("Skewer Threats");
            if (piece.getSkewerThreats() != null) {
                for (SkewerTriplet skewerTriplet : piece.getSkewerThreats()) {
                    Piece skewerThreat = skewerTriplet.getSkewerThreat();
                    System.out.print("Threat: " + skewerThreat.getClass().toString() + ": ");
                    System.out.println(integerSquareToSquareName(skewerThreat.getSquare()));
                    Piece protection = skewerTriplet.getMyProtectedPiece();
                    System.out.print("Protection: " + protection.getClass().toString() + ": ");
                    System.out.println(integerSquareToSquareName(protection.getSquare()));
                }
            }
        }
        
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

    private static void initStartingPosition(int color) {
        HashMap<Integer, Piece> myStartingPosition = new HashMap<>();
        HashMap<Integer, Piece> opponentStartingPosition = new HashMap<>();

        // insert pawns
        for (int square = 2; square <= 58; square += 8) {
            if (color == WHITE) {
                myStartingPosition.put(square, new Pawn(1, square, WHITE));
            } else {
                opponentStartingPosition.put(square, new Pawn(1, square, WHITE));
            }
        }

        for (int square = 7; square <= 63; square += 8) {
            if (color == WHITE) {
                opponentStartingPosition.put(square, new Pawn(1, square, BLACK));
            } else {
                myStartingPosition.put(square, new Pawn(1, square, BLACK));
            }
        }

        // insert rooks
        if (color == WHITE) {
            myStartingPosition.put(1, new Rook(5, 1, WHITE));
            myStartingPosition.put(57, new Rook(5, 57, WHITE));
            opponentStartingPosition.put(8, new Rook(5, 8, BLACK));
            opponentStartingPosition.put(64, new Rook(5, 64, BLACK));
        } else {
            opponentStartingPosition.put(1, new Rook(5, 1, WHITE));
            opponentStartingPosition.put(57, new Rook(5, 57, WHITE));
            myStartingPosition.put(8, new Rook(5, 8, BLACK));
            myStartingPosition.put(64, new Rook(5, 64, BLACK));
        }

        // insert knights
        if (color == WHITE) {
            myStartingPosition.put(9, new Knight(3, 9, WHITE));
            myStartingPosition.put(49, new Knight(3, 49, WHITE));
            opponentStartingPosition.put(16, new Knight(3, 16, BLACK));
            opponentStartingPosition.put(56, new Knight(3, 56, BLACK));
        } else {
            opponentStartingPosition.put(9, new Knight(3, 9, WHITE));
            opponentStartingPosition.put(49, new Knight(3, 49, WHITE));
            myStartingPosition.put(16, new Knight(3, 16, BLACK));
            myStartingPosition.put(56, new Knight(3, 56, BLACK));
        }

        // insert bishops
        if (color == WHITE) {
            myStartingPosition.put(17, new Bishop(3, 17, WHITE));
            myStartingPosition.put(41, new Bishop(3, 41, WHITE));
            opponentStartingPosition.put(24, new Bishop(3, 24, BLACK));
            opponentStartingPosition.put(48, new Bishop(3, 48, BLACK));
        } else {
            opponentStartingPosition.put(17, new Bishop(3, 17, WHITE));
            opponentStartingPosition.put(41, new Bishop(3, 41, WHITE));
            myStartingPosition.put(24, new Bishop(3, 24, BLACK));
            myStartingPosition.put(48, new Bishop(3, 48, BLACK));
        }

        // insert queens
        if (color == WHITE) {
            myStartingPosition.put(25, new Queen(9, 25, WHITE));
            opponentStartingPosition.put(32, new Queen(9, 32, BLACK));
        } else {
            opponentStartingPosition.put(25, new Queen(9, 25, WHITE));
            myStartingPosition.put(32, new Queen(9, 32, BLACK));
        }

        // insert kings
        if (color == WHITE) {
            myStartingPosition.put(33, new King(13, 33, WHITE));
            opponentStartingPosition.put(40, new King(13, 40, BLACK));
        } else {
            opponentStartingPosition.put(33, new King(13, 33, WHITE));
            myStartingPosition.put(40, new King(13, 40, BLACK));
        }

        currentPosition = new PositionNode(myStartingPosition, opponentStartingPosition);
    }

}
