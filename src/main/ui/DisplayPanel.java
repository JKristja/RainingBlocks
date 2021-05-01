package main.ui;

import main.model.PuzzleBoard;
import main.model.PuzzleGame;
import main.model.PuzzlePiece;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import static main.ui.Main.*;

/**
 * Display panel for the game; contains welcome and game over screens.
 */
public class DisplayPanel extends JPanel implements Observer {
    private PuzzleGame game;
    private boolean gameOver = false;
    private boolean welcome = true;
    private boolean running = false;
    private static final int panelWidth = GRID_WIDTH * GRID_SCALE;
    private static final int panelHeight = GRID_HEIGHT * GRID_SCALE;
    private static final Font TEXT_FONT = new Font("Serif", Font.PLAIN, 24);
    private static final Color WELCOME_COLOR = new Color(255, 255, 255);
    private static final Color GAME_OVER_COLOR = new Color(255, 0, 0);

    public DisplayPanel(PuzzleGame game) {
        this.game = game;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setMaximumSize(new Dimension(panelWidth, panelHeight));
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
    }

    /**
     * MODIFIES: this
     * Draws game graphics to display panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(BOARD_COLOR);
        if (welcome) {
            drawWelcome(g);
        } else if (gameOver) {
            drawGameOver(g);
        } else {
            drawGame(g);
        }
    }

    /**
     * Welcome Screen
     */
    private void drawWelcome(Graphics g) {
        String[] welcome = new String[]{
                "Welcome",
                "<-, down, -> arrows to move",
                "'A', 'D' Rotate",
                "Space bar to drop",
                "'N' for New Game"};

        g.setFont(TEXT_FONT);
        g.setColor(WELCOME_COLOR);
        FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();

        Rectangle2D welcomeBounds;
        int messageWidth;
        int messageHeight;
        int lineNum = 0;

        for (String s : welcome) {
            welcomeBounds = TEXT_FONT.getStringBounds(s, frc);
            messageWidth = (int) welcomeBounds.getWidth();
            messageHeight = (int) welcomeBounds.getHeight();
            g.drawString(s, (panelWidth - messageWidth) / 2, panelHeight / 3 + messageHeight * lineNum);
            lineNum++;
        }
    }

    /**
     * Game Over Screen
     */
    private void drawGameOver(Graphics g) {
        String gameOver = "GAME OVER";
        String gameOver2 = "Press N for New Game";
        g.setFont(TEXT_FONT);
        g.setColor(GAME_OVER_COLOR);
        FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();

        Rectangle2D gameOverBounds = TEXT_FONT.getStringBounds(gameOver, frc);
        int messageWidth = (int) gameOverBounds.getWidth();
        g.drawString(gameOver, (panelWidth - messageWidth) / 2, panelHeight / 3);

        gameOverBounds = TEXT_FONT.getStringBounds(gameOver2, frc);
        messageWidth = (int) gameOverBounds.getWidth();
        int messageHeight = (int) gameOverBounds.getHeight();
        g.drawString(gameOver2, (panelWidth - messageWidth) / 2, panelHeight / 3 + messageHeight);
    }

    /**
     * MODIFIES: this
     * Draws board state and current piece location to display panel
     */
    private void drawGame(Graphics g) {
        PuzzleBoard board = game.getBoardState();
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                g.setColor(board.getPos(x, y));
                g.fillRect(x * GRID_SCALE, y * GRID_SCALE, GRID_SCALE, GRID_SCALE);
            }
        }
        PuzzlePiece piece = game.getCurrentPiece();
        int pieceX0 = piece.getX0();
        int pieceY0 = piece.getY0();
        g.setColor(piece.getColor());
        for (Integer[] xy : piece.getVertices()) {
            int rectX0 = (pieceX0 + xy[0]) * GRID_SCALE;
            int rectY0 = (pieceY0 + xy[1]) * GRID_SCALE;
            g.fillRect(rectX0, rectY0, GRID_SCALE, GRID_SCALE);
        }
    }

    public void setGameOver() {
        this.gameOver = true;
        this.welcome = false;
        this.running = false;
    }

    public void setWelcome() {
        this.welcome = true;
        this.gameOver = false;
        this.running = false;
    }

    public void setGameRunning() {
        this.running = true;
        this.welcome = false;
        this.gameOver = false;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
