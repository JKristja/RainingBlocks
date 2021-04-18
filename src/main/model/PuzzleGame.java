package main.model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;

/**
 * represents the puzzle game
 */
public class PuzzleGame extends Observable {
    private final int X0_NEW_PIECE;
    private static final int Y0_NEW_PIECE = 0;
    private int level;
    private int score;
    private PuzzleBoard board;
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final Color BACKGROUND;
    private PuzzlePiece currentPiece;
    private Random random;

    public PuzzleGame(int gridWidth, int gridHeight, Color background) {
        this.level = 0;
        this.score = 0;
        this.X0_NEW_PIECE = gridWidth / 2 - 1;
        this.GRID_WIDTH = gridWidth;
        this.GRID_HEIGHT = gridHeight;
        this.BACKGROUND = background;
        this.board = new PuzzleBoard(gridWidth, gridHeight, background);
        this.random = new Random();
        this.currentPiece = randomPiece();
    }

    public int getLevel() {
        return this.level;
    }

    /**
     * MDIFIES: this
     * generate new random piece as current piece
     *
     * @return current puzzle piece
     */
    private PuzzlePiece randomPiece() {
        List<PuzzlePiece.PIECES> pieces = Arrays.asList(PuzzlePiece.PIECES.values());
        int numTypes = pieces.size();
        PuzzlePiece.PIECES piece = pieces.get(random.nextInt(numTypes));
        switch (piece) {
            case J:
                return new JPiece(X0_NEW_PIECE, Y0_NEW_PIECE);
            case L:
                return new LPiece(X0_NEW_PIECE, Y0_NEW_PIECE);
            case T:
                return new TPiece(X0_NEW_PIECE, Y0_NEW_PIECE);
            case SQ:
                return new SquarePiece(X0_NEW_PIECE, Y0_NEW_PIECE);
            case ST:
                return new StraightPiece(X0_NEW_PIECE, Y0_NEW_PIECE);
            case ZA:
                return new ZagPiece(X0_NEW_PIECE, Y0_NEW_PIECE);
            case ZI:
                return new ZigPiece(X0_NEW_PIECE, Y0_NEW_PIECE);
        }
        return null;
    }

    public PuzzleBoard getBoardState() {
        return this.board;
    }

    public PuzzlePiece getCurrentPiece() {
        return this.currentPiece;
    }

    /**
     * MODIFIES: this
     * Advances PuzzleGame one step and checks for contact with bottom or other blocks;
     * updates board state;
     */
    public void nextState() {
        if (isClearBelow(1)) {
            currentPiece.moveDown();
        } else {
            placePiece();
            nextPiece();
        }
    }

    /**
     * MODIFIES: this
     * sets piece into background, checks if any rows completed, and clears rows in sequential order from top
     * notifies observers that rows are being cleared
     */
    private void placePiece() {
        int x = currentPiece.getX0();
        int y = currentPiece.getY0();
        ArrayList<Integer> completeRows = new ArrayList<Integer>();
        for (Integer[] xy : currentPiece.getVertices()) {
            board.setPos(x + xy[0], y + xy[1], currentPiece.color);
            if (board.isRowComplete(y + xy[1]) && !completeRows.contains(y + xy[1])) {
                completeRows.add(y + xy[1]);
            }
        }
        if (completeRows.size() > 0) {
            for (int rowIndex : completeRows) {
                board.clearRow(rowIndex);
            }
            this.hasChanged();
            this.notifyObservers();
        }
    }

    /**
     * MODIFIES: this
     * generates new piece at top of screen
     */
    private void nextPiece() {
        this.currentPiece = randomPiece();
    }

    /**
     * @return true if game is over (no clear space below)
     */
    public boolean isGameOver() {
        return !isClearBelow(1);
    }

    /**
     * MODIFIES: this
     * KeyHandler for user input
     *
     * @param keyCode Key Event of user input
     */
    public void userInput(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT && isClearLeft()) {
            currentPiece.moveLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT && isClearRight()) {
            currentPiece.moveRight();
        } else if (keyCode == KeyEvent.VK_DOWN && isClearBelow(1)) {
            currentPiece.moveDown();
        } else if (keyCode == KeyEvent.VK_A && isClearCounterCW()) {
            currentPiece.rotateCounterCW();
        } else if (keyCode == KeyEvent.VK_D && isClearCW()) {
            currentPiece.rotateCW();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            currentPiece.dropPiece(getDistToBottom());
        }
    }

    /**
     * @return true if current piece can move one grid space down (space is not occupied)
     */
    private boolean isClearBelow(int row) {
        int nextX = currentPiece.getX0();
        int nextY = currentPiece.getY0() + row;

        if (currentPiece.getY1() < GRID_HEIGHT) {
            for (Integer[] xy : currentPiece.getVertices()) {
                if (board.getPos(nextX + xy[0], nextY + xy[1]) != BACKGROUND) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if current piece can move one grid space left (space is not occupied)
     */
    public boolean isClearLeft() {
        int nextX = currentPiece.getX0() - 1;
        int nextY = currentPiece.getY0();

        if (nextX >= 0) {
            for (Integer[] xy : currentPiece.getVertices()) {
                if (board.getPos(nextX + xy[0], nextY + xy[1]) != BACKGROUND) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if current piece can move one grid space right (space is not occupied)
     */
    public boolean isClearRight() {
        int nextX = currentPiece.getX0() + 1;
        int nextY = currentPiece.getY0();

        if (currentPiece.getX1() < GRID_WIDTH) {
            for (Integer[] xy : currentPiece.getVertices()) {
                if (board.getPos(nextX + xy[0], nextY + xy[1]) != BACKGROUND) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if current piece can rotate counter cw at current x0,y0 (space is not occupied)
     */
    public boolean isClearCounterCW() {
        int nextX = currentPiece.getX0();
        int nextY = currentPiece.getY0();
        this.currentPiece.rotateCounterCW();

        if (currentPiece.getX1() <= GRID_WIDTH && currentPiece.getY1() <= GRID_HEIGHT) {
            for (Integer[] xy : currentPiece.getVertices()) {
                if (board.getPos(nextX + xy[0], nextY + xy[1]) != BACKGROUND) {
                    this.currentPiece.rotateCW();
                    return false;
                }
            }
            this.currentPiece.rotateCW();
            return true;
        } else {
            this.currentPiece.rotateCW();
            return false;
        }
    }

    /**
     * @return true if current piece can rotate cw at current x0,y0 (space is not occupied)
     */
    public boolean isClearCW() {
        int nextX = currentPiece.getX0();
        int nextY = currentPiece.getY0();
        this.currentPiece.rotateCW();

        if (currentPiece.getX1() <= GRID_WIDTH && currentPiece.getY1() <= GRID_HEIGHT) {
            for (Integer[] xy : currentPiece.getVertices()) {
                if (board.getPos(nextX + xy[0], nextY + xy[1]) != BACKGROUND) {
                    this.currentPiece.rotateCounterCW();
                    return false;
                }
            }
            this.currentPiece.rotateCounterCW();
            return true;
        } else {
            this.currentPiece.rotateCounterCW();
            return false;
        }
    }

    /**
     * @return clear distance that current piece can travel down
     */
    public int getDistToBottom() {
        int y_dist = 0;
        while (isClearBelow(y_dist)) {
            y_dist++;
        }
        return y_dist;
    }
}
