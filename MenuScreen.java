import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends JFrame {

    public MenuScreen() {
        setTitle("My Chess Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Welcome to My Chess Engine!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(label);

        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        label.setPreferredSize(new Dimension(400, 100));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton playAsWhiteButton = new JButton("Play as White");
        playAsWhiteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAsWhiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Starting the game as White!");
                dispose();
                GUI gui = new GUI(GlobalConstants.WHITE);
                gui.setVisible(true);
            }
        });
        buttonPanel.add(playAsWhiteButton);

        JButton playAsBlackButton = new JButton("Play as Black");
        playAsBlackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAsBlackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Starting the game as Black!");
                dispose();
                GUI gui = new GUI(GlobalConstants.BLACK);
                gui.setVisible(true);
            }
        });
        buttonPanel.add(playAsBlackButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(buttonPanel);
        add(mainPanel);
        pack();
        setVisible(true);
    }

}