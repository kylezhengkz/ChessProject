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

    static private void printBoard() {
        int[] square = new int[2];
        for (int i = 8; i >= 1; i--) {
            for (int j = 1; j <= 8; j++) {
                square[0] = i;
                square[1] = j;
                Piece selectedPiece = null;
                if (currentPosition.getMyPieces().containsKey(square)) {
                    selectedPiece = currentPosition.getMyPieces().get(square);
                }

                if (currentPosition.getOpponentPieces().containsKey(square)) {
                    selectedPiece = currentPosition.getOpponentPieces().get(square);
                }

                if (selectedPiece instanceof Pawn) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("P ");
                    } else {
                        System.out.print("p ");
                    }
                } else if (selectedPiece instanceof Bishop) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("B ");
                    } else {
                        System.out.print("b ");
                    }
                } else if (selectedPiece instanceof Knight) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("K ");
                    } else {
                        System.out.print("k ");
                    }
                } else if (selectedPiece instanceof Rook) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("R ");
                    } else {
                        System.out.print("r ");
                    }
                } else if (selectedPiece instanceof Queen) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("Q ");
                    } else {
                        System.out.print("q ");
                    }
                } else if (selectedPiece instanceof King) {
                    if (selectedPiece.getColor() == WHITE) {
                        System.out.print("A ");
                    } else {
                        System.out.print("a");
                    }
                }
            }
            System.out.println();
        }
    }

    private static void initStartingPosition(int color) {
        HashMap<int[], Piece> myStartingPosition = new HashMap<>();
        HashMap<int[], Piece> opponentStartingPosition = new HashMap<>();

        int[] square = new int[2];

        // insert pawns
        if (color == WHITE) {
            square[1] = 2;
        } else {
            square[1] = 7;
        }
        for (int i = 1; i <= 8; i++) {
            square[0] = i;
            myStartingPosition.put(square.clone(), new Pawn(1, square, color));
        }

        if (color == BLACK) {
            square[1] = 7;
        } else {
            square[1] = 2;
        }
        for (int i = 1; i <= 8; i++) {
            square[0] = i;
            opponentStartingPosition.put(square.clone(), new Pawn(1, square, color));
        }

        // insert rooks
        square[0] = 1;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square, new Rook(5, square, WHITE));
            System.out.println(myStartingPosition.containsKey(square));
            myStartingPosition.put(square.clone(), new Rook(5, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new Rook(5, square, WHITE));
        }

        square[0] = 8;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square.clone(), new Rook(5, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new Rook(5, square, WHITE));
        }

        square[0] = 1;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new Rook(5, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new Rook(5, square, BLACK));
        }

        square[0] = 8;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new Rook(5, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new Rook(5, square, BLACK));
        }

        // insert knights
        square[0] = 2;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square.clone(), new Knight(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new Knight(3, square, WHITE));
        }

        square[0] = 7;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square.clone(), new Knight(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new Knight(3, square, WHITE));
        }

        square[0] = 2;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new Knight(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new Knight(3, square, BLACK));
        }

        square[0] = 7;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new Knight(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new Knight(3, square, BLACK));
        }

        // insert bishops
        square[0] = 3;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square.clone(), new Bishop(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new Bishop(3, square, WHITE));
        }

        square[0] = 6;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square.clone(), new Bishop(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new Bishop(3, square, WHITE));
        }

        square[0] = 3;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new Bishop(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new Bishop(3, square, BLACK));
        }

        square[0] = 6;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new Bishop(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new Bishop(3, square, BLACK));
        }

        // insert queens
        square[0] = 4;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square.clone(), new Queen(9, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new Queen(9, square, WHITE));
        }

        square[0] = 4;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new Queen(9, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new Queen(9, square, BLACK));
        }

        // insert kings
        square[0] = 5;
        square[1] = 1;
        if (color == WHITE) {
            myStartingPosition.put(square.clone(), new King(13, square, WHITE));
        } else {
            opponentStartingPosition.put(square.clone(), new King(13, square, WHITE));
        }

        square[0] = 5;
        square[1] = 8;
        if (color == BLACK) {
            myStartingPosition.put(square.clone(), new King(13, square, BLACK));
        } else {
            opponentStartingPosition.put(square.clone(), new King(13, square, BLACK));
        }

        currentPosition = new PositionNode(myStartingPosition, opponentStartingPosition);
    }

}
