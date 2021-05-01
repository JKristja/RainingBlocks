package main.ui;

import main.model.PuzzleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Main Window of Puzzle Game
 */
public class Main extends JFrame {

    public static final int GRID_SCALE = 30;
    public static final int GRID_WIDTH = 12;
    public static final int GRID_HEIGHT = (int) (1.75 * GRID_WIDTH);
    public static final Color BOARD_COLOR = new Color(0, 0, 0);
    private static final int START_INTERVAL = 1600;
    private static final int INTERVAL_REDUCTION = 300;
    private static final int MIN_INTERVAL = 100;
    private int dropInterval;
    private PuzzleGame game;
    private DisplayPanel dp;
    private ScorePanel sp;
    private boolean gameStarted = false;

    public Main() {
        super("Raining Blocks!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.game = new PuzzleGame(GRID_WIDTH, GRID_HEIGHT, BOARD_COLOR, new Random());
        this.dp = new DisplayPanel(game);
        this.sp = new ScorePanel(game);
        this.game.addObserver(dp);
        this.game.addObserver(sp);

        JPanel display = new JPanel();
        display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));
        display.add(sp.getScorePanel(), BorderLayout.NORTH);
        display.add(dp, BorderLayout.CENTER);
        display.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        display.setBackground(BOARD_COLOR);
        this.add(display);
        pack();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
        setResizable(false);

        addKeyListener(new UserInput());

        this.dropInterval = START_INTERVAL;
    }

    /**
     * MODIFIES: this, dp, game
     * Timer for managing drop rate of pieces
     */
    private void setTimers() {
        Timer dropTimer = new Timer(dropInterval, null);
        dropTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.isGameOver()) {
                    dropTimer.stop();
                    gameStarted = false;
                    dp.setGameOver();
                    game.setTopScore();
                } else {
                    game.nextState();
                    dropTimer.setDelay(Math.max(START_INTERVAL - INTERVAL_REDUCTION * game.getLevel(), MIN_INTERVAL));
                }
                dp.repaint();
            }
        });

        dropTimer.start();
    }

    /**
     * MODIFIES: this, game, dp
     * passes user input to game keyhandler if game is running, otherwise brings up welcome screen or starts new game
     */
    private class UserInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameStarted) {
                game.userInput(e.getKeyCode());
            } else if (e.getKeyCode() == KeyEvent.VK_N) {
                setTimers();
                gameStarted = true;
                game.resetGame();
                dp.setGameRunning();
                dp.repaint();
            } else {
                dp.setWelcome();
                dp.repaint();
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
