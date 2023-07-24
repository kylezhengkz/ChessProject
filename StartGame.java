import java.util.*;

public class StartGame {

    public static void main(String[] args) {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.setVisible(true);
    }

    public static PositionNode initStartingPosition(int color) {
        HashMap<Integer, Piece> userStartingPosition = new HashMap<>();
        HashMap<Integer, Piece> cpuStartingPosition = new HashMap<>();

        // insert pawns
        for (int square = 2; square <= 58; square += 8) {
            if (color == GlobalConstants.WHITE) {
                userStartingPosition.put(square, new Pawn(1, square, GlobalConstants.WHITE));
            } else {
                cpuStartingPosition.put(square, new Pawn(1, square, GlobalConstants.WHITE));
            }
        }

        for (int square = 7; square <= 63; square += 8) {
            if (color == GlobalConstants.WHITE) {
                cpuStartingPosition.put(square, new Pawn(1, square, GlobalConstants.BLACK));
            } else {
                userStartingPosition.put(square, new Pawn(1, square, GlobalConstants.BLACK));
            }
        }

        // insert rooks
        if (color == GlobalConstants.WHITE) {
            userStartingPosition.put(1, new Rook(5, 1, GlobalConstants.WHITE));
            userStartingPosition.put(57, new Rook(5, 57, GlobalConstants.WHITE));
            cpuStartingPosition.put(8, new Rook(5, 8, GlobalConstants.BLACK));
            cpuStartingPosition.put(64, new Rook(5, 64, GlobalConstants.BLACK));
        } else {
            cpuStartingPosition.put(1, new Rook(5, 1, GlobalConstants.WHITE));
            cpuStartingPosition.put(57, new Rook(5, 57, GlobalConstants.WHITE));
            userStartingPosition.put(8, new Rook(5, 8, GlobalConstants.BLACK));
            userStartingPosition.put(64, new Rook(5, 64, GlobalConstants.BLACK));
        }

        // insert knights
        if (color == GlobalConstants.WHITE) {
            userStartingPosition.put(9, new Knight(3, 9, GlobalConstants.WHITE));
            userStartingPosition.put(49, new Knight(3, 49, GlobalConstants.WHITE));
            cpuStartingPosition.put(16, new Knight(3, 16, GlobalConstants.BLACK));
            cpuStartingPosition.put(56, new Knight(3, 56, GlobalConstants.BLACK));
        } else {
            cpuStartingPosition.put(9, new Knight(3, 9, GlobalConstants.WHITE));
            cpuStartingPosition.put(49, new Knight(3, 49, GlobalConstants.WHITE));
            userStartingPosition.put(16, new Knight(3, 16, GlobalConstants.BLACK));
            userStartingPosition.put(56, new Knight(3, 56, GlobalConstants.BLACK));
        }

        // insert bishops
        if (color == GlobalConstants.WHITE) {
            userStartingPosition.put(17, new Bishop(3, 17, GlobalConstants.WHITE));
            userStartingPosition.put(41, new Bishop(3, 41, GlobalConstants.WHITE));
            cpuStartingPosition.put(24, new Bishop(3, 24, GlobalConstants.BLACK));
            cpuStartingPosition.put(48, new Bishop(3, 48, GlobalConstants.BLACK));
        } else {
            cpuStartingPosition.put(17, new Bishop(3, 17, GlobalConstants.WHITE));
            cpuStartingPosition.put(41, new Bishop(3, 41, GlobalConstants.WHITE));
            userStartingPosition.put(24, new Bishop(3, 24, GlobalConstants.BLACK));
            userStartingPosition.put(48, new Bishop(3, 48, GlobalConstants.BLACK));
        }

        // insert queens
        if (color == GlobalConstants.WHITE) {
            userStartingPosition.put(25, new Queen(9, 25, GlobalConstants.WHITE));
            cpuStartingPosition.put(32, new Queen(9, 32, GlobalConstants.BLACK));
        } else {
            cpuStartingPosition.put(25, new Queen(9, 25, GlobalConstants.WHITE));
            userStartingPosition.put(32, new Queen(9, 32, GlobalConstants.BLACK));
        }

        // insert kings
        if (color == GlobalConstants.WHITE) {
            userStartingPosition.put(33, new King(13, 33, GlobalConstants.WHITE));
            cpuStartingPosition.put(40, new King(13, 40, GlobalConstants.BLACK));
        } else {
            cpuStartingPosition.put(33, new King(13, 33, GlobalConstants.WHITE));
            userStartingPosition.put(40, new King(13, 40, GlobalConstants.BLACK));
        }

        PositionNode startingPosition = new PositionNode(userStartingPosition, cpuStartingPosition);
        return startingPosition;
    }

}
