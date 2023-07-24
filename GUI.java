import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static final int BOARDSIZE = 8;
    private static final int TILESIZE = 80;
    private static final int IMAGEHEIGHT = 50;
    private static final int IMAGEWIDTH = 50;

    private static PositionNode currentPosition;

    public GUI(int userColor) {
        setTitle("My Chess Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentPosition = StartGame.initStartingPosition(userColor);

        JPanel chessBoardPanel = new ChessboardPanel();
        add(chessBoardPanel);
        setSize(BOARDSIZE * TILESIZE, BOARDSIZE * TILESIZE);
        setVisible(true);
    }

    private class ChessboardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // display chessboard
            for (int row = 0; row < BOARDSIZE; row++) {
                for (int col = 0; col < BOARDSIZE; col++) {
                    int x = col * TILESIZE;
                    int y = row * TILESIZE;

                    if ((row + col) % 2 == 0) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.BLACK);
                    }

                    g.fillRect(x, y, TILESIZE, TILESIZE);
                }
            }

            // display pieces
            for (int square : currentPosition.getUserPieces().keySet()) {
                if (!currentPosition.getUserPieces().containsKey(square)) {
                    continue;
                }

                Piece userPiece = currentPosition.getUserPieces().get(square);

                int[] coor = squareToCoor(square);
                if (userPiece instanceof Pawn) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhitePawn.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackPawn.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Knight) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Bishop) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Rook) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Queen) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof King) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                }
            }

            for (int square : currentPosition.getCpuPieces().keySet()) {
                if (!currentPosition.getCpuPieces().containsKey(square)) {
                    continue;
                }

                Piece userPiece = currentPosition.getCpuPieces().get(square);

                int[] coor = squareToCoor(square);
                if (userPiece instanceof Pawn) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhitePawn.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackPawn.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Knight) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Bishop) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Rook) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Queen) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof King) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0], coor[1], IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                }
            }

        }
    }

    private static int[] squareToCoor(int square) {
        int[] coor = new int[2];
        coor[0] = (square / 8)*80;
        if (square % 8 == 0) {
            coor[0] -= 80;
        }
        
        coor[1] = 640 - (square % 8)*80;
        if (coor[1] == 640) {
            coor[1] = 0;
        }

        return coor;
    }

    private static int coorToSquare(int[] coor) {
        int square = 1;
        square += (coor[0] / 80) * 8;
        square += 7 - (coor[1] / 80);

        return square;
    }

}