package main.ui;

import main.model.PuzzleGame;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static main.ui.Main.BOARD_COLOR;

/**
 * ScorePanel displayed above game area; Observes game
 */
public class ScorePanel implements Observer {

    private PuzzleGame game;
    private JPanel scorePanel = new JPanel();
    private JLabel levelLabel;
    private JLabel scoreLabel;
    private JLabel topScoreLabel;
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final Font TEXT_FONT = new Font("Serif", Font.BOLD, 14);

    public ScorePanel(PuzzleGame game) {
        this.game = game;
        this.scorePanel.setLayout(new GridLayout(1, 3));
        setLevelLabel();
        setScoreLabel();
        setTopScoreLabel();
        this.scorePanel.setBackground(BOARD_COLOR);
        this.scorePanel.add(levelLabel);
        this.scorePanel.add(scoreLabel);
        this.scorePanel.add(topScoreLabel);
    }

    private void setLevelLabel() {
        this.levelLabel = new JLabel("<html><div style='text-align:center;'>CURRENT LEVEL<br>"
                + game.getLevel() + "</div></html>");
        this.levelLabel.setFont(TEXT_FONT);
        this.levelLabel.setForeground(TEXT_COLOR);
        this.levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setScoreLabel() {
        this.scoreLabel = new JLabel("<html><div style='text-align:center;'>CURRENT SCORE<br>"
                + game.getScore() + "</div></html>");
        this.scoreLabel.setFont(TEXT_FONT);
        this.scoreLabel.setForeground(TEXT_COLOR);
        this.scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setTopScoreLabel() {
        this.topScoreLabel = new JLabel("<html><div style='text-align:center;'>TOP SCORE<br>"
                + game.getTopScore() + "</div></html>");
        this.topScoreLabel.setFont(TEXT_FONT);
        this.topScoreLabel.setForeground(TEXT_COLOR);
        this.topScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            if (arg.equals("TopScore")) {
                topScoreLabel.setText("<html><div style='text-align:center;'>TOP SCORE<br>"
                        + game.getTopScore() + "</div></html>");
            } else {
                levelLabel.setText("<html><div style='text-align:center;'>CURRENT LEVEL<br>"
                        + game.getLevel() + "</div></html>");
                scoreLabel.setText("<html><div style='text-align:center;'>CURRENT SCORE<br>"
                        + game.getScore() + "</div></html>");
            }
        }
    }

    public JPanel getScorePanel() {
        return this.scorePanel;
    }
}
