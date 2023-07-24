import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame implements MouseListener, MouseMotionListener {

    private static final int BOARDSIZE = 8;
    private static final int TILESIZE = 80;
    private static final int IMAGEHEIGHT = 60;
    private static final int IMAGEWIDTH = 60;
    private static final int ADJUSTIMAGE = 8;
    private static final Color myDarkGreen = new Color(0, 102, 51);
    private static final Color myWhiteYellow = new Color(255, 255, 204);

    private static PositionNode currentPosition;

    private static int mouseX;
    private static int mouseY;

    public GUI(int userColor) {
        setTitle("My Chess Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentPosition = StartGame.initStartingPosition(userColor);

        JPanel chessBoardPanel = new ChessboardPanel();
        add(chessBoardPanel);
        addMouseListener(this);
        addMouseMotionListener(this);

        int padding = 30;
        setSize(BOARDSIZE * TILESIZE + padding, BOARDSIZE * TILESIZE + padding);
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
                        g.setColor(myWhiteYellow);
                    } else {
                        g.setColor(myDarkGreen);
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
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackPawn.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Knight) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Bishop) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Rook) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Queen) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH - 6, IMAGEHEIGHT - 6, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH - 6, IMAGEHEIGHT - 6, this);
                    }
                } else if (userPiece instanceof King) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1]  + ADJUSTIMAGE, IMAGEWIDTH - 8, IMAGEHEIGHT - 8, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH - 8, IMAGEHEIGHT - 8, this);
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
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackPawn.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Knight) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKnight.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Bishop) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackBishop.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Rook) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackRook.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                    }
                } else if (userPiece instanceof Queen) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH - 6, IMAGEHEIGHT - 6, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackQueen.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH - 6, IMAGEHEIGHT - 6, this);
                    }
                } else if (userPiece instanceof King) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH - 10, IMAGEHEIGHT - 10, this);
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKing.png");
                        g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH - 10, IMAGEHEIGHT - 10, this);
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

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("PRESS");
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("DRAG");
        mouseX = e.getX();
        mouseY = e.getY();
        System.out.println(mouseX);
        System.out.println(mouseY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("RELEASE");
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

}