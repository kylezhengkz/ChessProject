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
        int[] squareArr = new int[2];
        ArrayWrapper square = new ArrayWrapper(squareArr);

        System.out.print("  ");
        for (int i = 0; i < 15; i++) {
            System.out.print(".");
        }
        System.out.println();

        for (int i = 8; i >= 1; i--) {
            System.out.print((9 - i) + "|");
            for (int j = 1; j <= 8; j++) {
                square.getArray()[1] = i;
                square.getArray()[0] = j;
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

                if (j <= 7) {
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

        HashMap<ArrayWrapper, List<Piece>> controlledSquares = new HashMap<>();
        // analysis
        for (ArrayWrapper squarePos : currentPosition.getMyPieces().keySet()) {
            Piece piece = currentPosition.getMyPieces().get(squarePos);
            if (piece instanceof Pawn) {
                ((Pawn) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Knight) {
                ((Knight) piece).generateMoves(currentPosition, controlledSquares);
            }  else if (piece instanceof King) {
                ((King) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Bishop) {
                ((Bishop) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Queen) {
                ((Queen) piece).generateMoves(currentPosition, controlledSquares);
            } else if (piece instanceof Rook) {
                ((Rook) piece).generateMoves(currentPosition, controlledSquares);
            }
        }

        for (ArrayWrapper squarePos : currentPosition.getMyPieces().keySet()) {
            Piece piece = currentPosition.getMyPieces().get(squarePos);
            System.out.println(piece == null);
        }

        /* 
        for (ArrayWrapper squarePos : currentPosition.getMyPieces().keySet()) {
            System.out.println(squarePos.getArray()[0] + ", " + squarePos.getArray()[1]);
            Piece piece = currentPosition.getMyPieces().get(squarePos);
            System.out.println(piece.getClass().toString());
            System.out.println("Possible Moves");
            for (ArrayWrapper possibleSquare : piece.getPossibleMoves()) {
                System.out.println(possibleSquare.getArray()[0] + ", " + possibleSquare.getArray()[1]);
            }
            System.out.println("Possible Captures");
            for (ArrayWrapper captureSquare : piece.getCaptures()) {
                System.out.println(captureSquare.getArray()[0] + ", " + captureSquare.getArray()[1]);
            }
            System.out.println("Skewer Threats");
            for (SkewerTriplet skewerTriplet : piece.getSkewerThreats()) {
                Piece skewerThreat = skewerTriplet.getSkewerThreat();
                System.out.print("Threat: " + skewerThreat.getClass().toString() + ": ");
                System.out.println(skewerThreat.getSquare().getArray()[0] + ", " + skewerThreat.getSquare().getArray()[1]);
                Piece protection = skewerTriplet.getMyProtectedPiece();
                System.out.print("Protection: " + protection.getClass().toString() + ": ");
                System.out.println(protection.getSquare().getArray()[0] + ", " + protection.getSquare().getArray()[1]);
            }    
        }
        */
        
    }






































    private static void initStartingPosition(int color) {
        HashMap<ArrayWrapper, Piece> myStartingPosition = new HashMap<>();
        HashMap<ArrayWrapper, Piece> opponentStartingPosition = new HashMap<>();

        int[] squareArr = new int[2];

        ArrayWrapper square;

        // insert pawns
        if (color == WHITE) {
            squareArr[1] = 2;
        } else {
            squareArr[1] = 7;
        }

        squareArr[1] = 2;
        for (int i = 1; i <= 8; i++) {
            squareArr[0] = i;
            square = new ArrayWrapper(squareArr.clone());
            if (color == WHITE) {
                myStartingPosition.put(square, new Pawn(1, square, WHITE));
            } else {
                opponentStartingPosition.put(square, new Pawn(1, square, WHITE));
            }
        }

        squareArr[1] = 7;
        for (int i = 1; i <= 8; i++) {
            squareArr[0] = i;
            square = new ArrayWrapper(squareArr.clone());
            if (color == WHITE) {
                opponentStartingPosition.put(square, new Pawn(1, square, BLACK));
            } else {
                myStartingPosition.put(square, new Pawn(1, square, BLACK));
            }
        }

        if (color == BLACK) {
            squareArr[1] = 7;
        } else {
            squareArr[1] = 2;
        }
        for (int i = 1; i <= 8; i++) {
            squareArr[0] = i;
            square = new ArrayWrapper(squareArr.clone());
            opponentStartingPosition.put(square, new Pawn(1, square, color));
        }

        // insert rooks
        squareArr[0] = 1;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new Rook(5, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new Rook(5, square, WHITE));
        }

        squareArr[0] = 8;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new Rook(5, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new Rook(5, square, WHITE));
        }

        squareArr[0] = 1;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new Rook(5, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new Rook(5, square, BLACK));
        }

        squareArr[0] = 8;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new Rook(5, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new Rook(5, square, BLACK));
        }

        // insert knights
        squareArr[0] = 2;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new Knight(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new Knight(3, square, WHITE));
        }

        squareArr[0] = 7;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new Knight(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new Knight(3, square, WHITE));
        }

        squareArr[0] = 2;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new Knight(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new Knight(3, square, BLACK));
        }

        squareArr[0] = 7;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new Knight(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new Knight(3, square, BLACK));
        }

        // insert bishops
        squareArr[0] = 3;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new Bishop(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new Bishop(3, square, WHITE));
        }

        squareArr[0] = 6;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new Bishop(3, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new Bishop(3, square, WHITE));
        }

        squareArr[0] = 3;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new Bishop(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new Bishop(3, square, BLACK));
        }

        squareArr[0] = 6;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new Bishop(3, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new Bishop(3, square, BLACK));
        }

        // insert queens
        squareArr[0] = 4;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new Queen(9, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new Queen(9, square, WHITE));
        }

        squareArr[0] = 4;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new Queen(9, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new Queen(9, square, BLACK));
        }

        // insert kings
        squareArr[0] = 5;
        squareArr[1] = 1;
        square = new ArrayWrapper(squareArr.clone());
        if (color == WHITE) {
            myStartingPosition.put(square, new King(13, square, WHITE));
        } else {
            opponentStartingPosition.put(square, new King(13, square, WHITE));
        }

        squareArr[0] = 5;
        squareArr[1] = 8;
        square = new ArrayWrapper(squareArr.clone());
        if (color == BLACK) {
            myStartingPosition.put(square, new King(13, square, BLACK));
        } else {
            opponentStartingPosition.put(square, new King(13, square, BLACK));
        }

        currentPosition = new PositionNode(myStartingPosition, opponentStartingPosition);
    }

}
