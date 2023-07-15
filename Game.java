import java.util.*;
import java.util.Scanner;
public class Game {

    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public static HashMap<int[], Piece> myPieces = new HashMap<>();
    public static HashMap<int[], Piece> opponentPieces = new HashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String input;
        int color = - 1;
        System.out.print("Enter 'W' to play as white, and 'B' to play as black:");
        input = sc.nextLine();
        if (input == "W") {
            color = WHITE;
        } else if (input == "B") {
            color = BLACK;
        } else {
            System.out.println("Invalid input. Terminating program.");
            System.exit(0);
        }


        int[] square = new int[2];

        // insert pawns
        if (color == WHITE) {
            square[1] = 2;
        } else {
            square[1] = 7;
        }
        for (int i = 1; i <= 8; i++) {
            square[0] = i;
            myPieces.put(square, new Pawn(1, square, color));
        }

        if (color == BLACK) {
            square[1] = 7;
        } else {
            square[1] = 2;
        }
        for (int i = 1; i <= 8; i++) {
            square[0] = i;
            opponentPieces.put(square, new Pawn(1, square, color));
        }

        // insert rooks
        square[0] = 1;
        square[1] = 1;
        if (color == WHITE) {
            myPieces.put(square, new Rook(5, square, WHITE));
        } else {
            opponentPieces.put(square, new Rook(5, square, WHITE));
        }

        square[0] = 8;
        square[1] = 1;
        if (color == WHITE) {
            myPieces.put(square, new Rook(5, square, WHITE));
        } else {
            opponentPieces.put(square, new Rook(5, square, WHITE));
        }

        square[0] = 1;
        square[1] = 8;
        if (color == BLACK) {
            myPieces.put(square, new Rook(5, square, BLACK));
        } else {
            opponentPieces.put(square, new Rook(5, square, BLACK));
        }

        square[0] = 8;
        square[1] = 8;
        if (color == BLACK) {
            myPieces.put(square, new Rook(5, square, BLACK));
        } else {
            opponentPieces.put(square, new Rook(5, square, BLACK));
        }

        // insert knights
        square[0] = 2;
        square[1] = 1;
        if (color == WHITE) {
            myPieces.put(square, new Knight(3, square, WHITE));
        } else {
            opponentPieces.put(square, new Knight(3, square, WHITE));
        }

        square[0] = 7;
        square[1] = 1;
        if (color == WHITE) {
            myPieces.put(square, new Knight(3, square, WHITE));
        } else {
            opponentPieces.put(square, new Knight(3, square, WHITE));
        }

        square[0] = 2;
        square[1] = 8;
        if (color == BLACK) {
            myPieces.put(square, new Knight(3, square, BLACK));
        } else {
            opponentPieces.put(square, new Knight(3, square, BLACK));
        }

        square[0] = 7;
        square[1] = 8;
        if (color == BLACK) {
            myPieces.put(square, new Knight(3, square, BLACK));
        } else {
            opponentPieces.put(square, new Knight(3, square, BLACK));
        }

        // insert bishops
        square[0] = 3;
        square[1] = 1;
        if (color == WHITE) {
            myPieces.put(square, new Bishop(3, square, WHITE));
        } else {
            opponentPieces.put(square, new Bishop(3, square, WHITE));
        }

        square[0] = 6;
        square[1] = 1;
        if (color == WHITE) {
            myPieces.put(square, new Bishop(3, square, WHITE));
        } else {
            opponentPieces.put(square, new Bishop(3, square, WHITE));
        }

        square[0] = 3;
        square[1] = 8;
        if (color == BLACK) {
            myPieces.put(square, new Bishop(3, square, BLACK));
        } else {
            opponentPieces.put(square, new Bishop(3, square, BLACK));
        }

        square[0] = 6;
        square[1] = 8;
        if (color == BLACK) {
            myPieces.put(square, new Bishop(3, square, BLACK));
        } else {
            opponentPieces.put(square, new Bishop(3, square, BLACK));
        }
        
    }
}