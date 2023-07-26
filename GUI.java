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
    private static final int ADJUSTDRAGX = 30;
    private static final int ADJUSTDRAGY = 45;
    private static final Color myDarkGreen = new Color(0, 102, 51);
    private static final Color myWhiteYellow = new Color(255, 255, 204);

    private static PositionNode currentPosition;

    private static int mouseX;
    private static int mouseY;
    private static boolean drag;
    private static int dragX;
    private static int dragY;
    private static int dragSquare;
    private static Piece dragPiece;

    private static boolean userTurn;

    // TO-DO
    private static boolean temporaryStatus = false;
    private static Pawn prevPawn;

    public GUI(int userColor) {
        setTitle("My Chess Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        currentPosition = StartGame.initStartingPosition(userColor);
        if (userColor == GlobalConstants.WHITE) {
            userTurn = true;
        } else {
            userTurn = false;
        }

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
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackPawn.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Knight) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKnight.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKnight.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Bishop) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteBishop.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackBishop.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Rook) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteRook.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackRook.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Queen) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteQueen.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackQueen.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof King) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKing.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKing.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
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
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackPawn.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Knight) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKnight.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKnight.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Bishop) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteBishop.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackBishop.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Rook) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteRook.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackRook.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof Queen) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteQueen.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackQueen.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                } else if (userPiece instanceof King) {
                    if (userPiece.getColor() == GlobalConstants.WHITE) {
                        ImageIcon imageIcon = new ImageIcon("Images/WhiteKing.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    } else {
                        ImageIcon imageIcon = new ImageIcon("Images/BlackKing.png");
                        if (drag && (square == dragSquare)) {
                            g.drawImage(imageIcon.getImage(), dragX - ADJUSTDRAGX, dragY - ADJUSTDRAGY, IMAGEWIDTH, IMAGEHEIGHT, this);
                        } else {
                            g.drawImage(imageIcon.getImage(), coor[0] + ADJUSTIMAGE, coor[1] + ADJUSTIMAGE, IMAGEWIDTH, IMAGEHEIGHT, this);
                        }
                    }
                }
            }

        }
    }

    private static int[] squareToCoor(int square) {
        int[] coor = new int[2];
        coor[0] = (square / 8) * 80;
        if (square % 8 == 0) {
            coor[0] -= 80;
        }

        coor[1] = 640 - (square % 8) * 80;
        if (coor[1] == 640) {
            coor[1] = 0;
        }

        return coor;
    }

    private static int coorToSquare(int x, int y) {
        int square = 1;
        square += (x / 80) * 8;
        square += 7 - (y / 80);

        return square;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (userTurn) {
            mouseX = e.getX();
            mouseY = e.getY();
            dragSquare = coorToSquare(mouseX, mouseY);
            if (currentPosition.getUserPieces().containsKey(dragSquare)) {
                dragPiece = currentPosition.getUserPieces().get(dragSquare);
                drag = true;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (drag) {
            dragX = e.getX();
            dragY = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drag) {
            mouseX = e.getX();
            mouseY = e.getY();
            int newSquare = coorToSquare(mouseX, mouseY);
            currentPosition.generateUserPossibleMoves();

            if (dragPiece instanceof King) {
                if (dragPiece.getPossibleMoves() != null) {
                    for (int square : dragPiece.getPossibleMoves()) {
                        System.out.println(square);
                    }
                }
            }

            if ((newSquare != dragSquare) && (dragPiece.getPossibleMoves() != null) && (dragPiece.getPossibleMoves().contains(newSquare))) {
                if (dragPiece instanceof Pawn) {
                    ((Pawn) dragPiece).movePawn();
                    if ((newSquare - dragSquare) == 2) {
                        ((Pawn) dragPiece).moveTwoSquares();
                        temporaryStatus = true;
                        prevPawn = ((Pawn) dragPiece);
                    }
                } else if (dragPiece instanceof King) {
                    ((King) dragPiece).moveKing();
                } else if (dragPiece instanceof Rook) {
                    ((Rook) dragPiece).moveRook();
                }
                currentPosition.getUserPieces().put(newSquare, dragPiece);
                dragPiece.setSquare(newSquare);
                currentPosition.getUserPieces().remove(dragSquare);
                if (currentPosition.getCpuPieces().containsKey(newSquare)) {
                    currentPosition.getCpuPieces().remove(newSquare);
                }

                // castle
                if (dragPiece instanceof King && ((King) dragPiece).getPossibleCastles().contains(newSquare)) {
                    Rook selectedRook;
                    if (newSquare == 17 || newSquare == 24) {
                        selectedRook = (Rook) currentPosition.getUserPieces().get(newSquare - 16);
                        currentPosition.getUserPieces().put(newSquare + 8, selectedRook);
                        currentPosition.getUserPieces().remove(newSquare - 16);
                    } else if (newSquare == 49 || newSquare == 56) {
                        selectedRook = (Rook) currentPosition.getUserPieces().get(newSquare + 8);
                        currentPosition.getUserPieces().put(newSquare - 8, selectedRook);
                        currentPosition.getUserPieces().remove(newSquare + 8);
                    }
                }

                currentPosition.clearMoves();
            }
        }
        dragPiece = null;
        dragSquare = -1;
        drag = false;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}