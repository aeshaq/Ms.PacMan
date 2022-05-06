package ui;

import model.PacManGame;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private static final String score = "Score: ";
    private static final String powers = "Powers: ";
    private static final String caspers = "Caspers ";
    private static final int width = 200;
    private static final int height = 30;
    private PacManGame pmg;
    private JLabel scoreLabel;
    private JLabel powersLabel;
    private JLabel caspersLabel;

    public InfoPanel(PacManGame pmg) {
        this.pmg = pmg;
        setBackground(new Color(180,180,180));
        scoreLabel = new JLabel(score + pmg.getScore());
        scoreLabel.setPreferredSize(new Dimension(width,height));
        powersLabel = new JLabel(powers + pmg.getInventory().getPowers().size());
        powersLabel.setPreferredSize(new Dimension(width,height));
        caspersLabel = new JLabel(caspers + pmg.getInventory().getGhosts().size());
        caspersLabel.setPreferredSize(new Dimension(width,height));
        add(scoreLabel);
        add(Box.createHorizontalStrut(10));
        add(powersLabel);
        add(Box.createHorizontalStrut(10));
        add(caspersLabel);
    }

    public void setGame(PacManGame pmg) {
        this.pmg = pmg;
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        scoreLabel.setText(score + pmg.getScore());
        powersLabel.setText(powers + pmg.getInventory().getPowers().size());
        caspersLabel.setText(caspers + pmg.getInventory().getGhosts().size());
        repaint();
    }
}
