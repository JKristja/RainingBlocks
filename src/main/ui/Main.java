package main.ui;

import main.model.PuzzleGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

    public static final int GRID_SCALE = 20;
    private static final int GRID_WIDTH = 16;
    private static final int GRID_HEIGHT = 4 * GRID_WIDTH;
    private static final Color BOARD_COLOR = new Color(0, 0, 0);
    private static final int START_INTERVAL = 1500;
    private static final int INTERVAL_REDUCTION = 100;
    private static final int MIN_INTERVAL = 500;
    private int dropInterval;
    private PuzzleGame game;
    private DisplayPanel panel;

    public Main() {
        super("Raining Blocks!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.game = new PuzzleGame(GRID_WIDTH, GRID_HEIGHT, BOARD_COLOR);
        this.panel = new DisplayPanel(game);
        this.game.addObserver(panel);

        setSize(GRID_WIDTH * GRID_SCALE, GRID_HEIGHT * GRID_SCALE);
        setVisible(true);

        addKeyListener(new UserInput());

        this.dropInterval = START_INTERVAL;
        setTimers();
    }

    /**
     * Timer for managing drop rate of pieces
     */
    private void setTimers() {
        Timer dropTimer = new Timer(dropInterval, null);
        dropTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.isGameOver()) {
                    dropTimer.stop();
                } else {
                    game.nextState();
                    dropInterval = Math.max(START_INTERVAL - INTERVAL_REDUCTION * game.getLevel(), MIN_INTERVAL);
                }
                panel.repaint();
            }
        });

        dropTimer.start();
    }

    public static void main(String[] args) {

    }

    private class UserInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.userInput(e.getKeyCode());
        }
    }
}
