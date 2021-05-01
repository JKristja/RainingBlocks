package main.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents puzzle board with blank and colored grid squares
 */
public class PuzzleBoard {
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final Color BACKGROUND;
    private ArrayList<Color> boardState;

    public PuzzleBoard(int gridWidth, int gridHeight, Color background) {
        this.GRID_WIDTH = gridWidth;
        this.GRID_HEIGHT = gridHeight;
        this.BACKGROUND = background;
        setBlankBoard();
    }

    /**
     * MODIFIES: this
     * Creates new ArrayList representation of board state with all elements set to BACKGROUND
     */
    public void setBlankBoard() {
        this.boardState = new ArrayList<>();
        Color[] blankBoard = new Color[this.GRID_WIDTH * this.GRID_HEIGHT];
        Arrays.fill(blankBoard, this.BACKGROUND);
        this.boardState.addAll(Arrays.asList(blankBoard));
    }

    /**
     * returns Color of puzzle board at x, y coordinate
     *
     * @param x horizontal coordinate from 0 to GRID_WIDTH - 1
     * @param y vertical coordinate from 0 to GRID_HEIGHT - 1
     * @return Color of x,y position
     * @throws IndexOutOfBoundsException if x or y out of bounds
     */
    public Color getPos(int x, int y) throws IndexOutOfBoundsException {
        isOutOfBounds(x, y);
        return this.boardState.get(x + y * this.GRID_WIDTH);
    }

    /**
     * MODIFIES: this
     * sets Color of puzzle board at x, y coordinate
     *
     * @param x horizontal coordinate from 0 to GRID_WIDTH - 1
     * @param y vertical coordinate from 0 to GRID_HEIGHT - 1
     * @throws IndexOutOfBoundsException if x or y out of bounds
     */
    public void setPos(int x, int y, Color foreground) throws IndexOutOfBoundsException {
        isOutOfBounds(x, y);
        this.boardState.set(x + y * this.GRID_WIDTH, foreground);
    }

    /**
     * throws exception if x,y out of bounds
     *
     * @param x horizontal coordinate from 0 to GRID_WIDTH - 1
     * @param y vertical coordinate from 0 to GRID_HEIGHT - 1
     * @throws IndexOutOfBoundsException if x or y out of bounds
     */
    private void isOutOfBounds(int x, int y) throws IndexOutOfBoundsException {
        if (x < 0 || x >= GRID_WIDTH) {
            throw new IndexOutOfBoundsException("x must be between 0 and GRID_WIDTH - 1");
        }
        if (y < 0 || y >= GRID_HEIGHT) {
            throw new IndexOutOfBoundsException("y must be between 0 and GRID_HEIGHT - 1");
        }
    }

    /**
     * Checks that all elements in row are not BACKGROUND
     *
     * @return true if row is full
     */
    public boolean isRowComplete(int y) {
        for (int x = 0; x < GRID_WIDTH; x++) {
            if (getPos(x, y) == BACKGROUND) {
                return false;
            }
        }
        return true;
    }

    /**
     * MODIFIES: this
     * updates board state by removing row from PuzzleBoard and adding new blank row at top
     */
    public void clearRow(int rowIndex) {
        Color[] blankRow = new Color[GRID_WIDTH];
        Arrays.fill(blankRow, BACKGROUND);
        for (int x = 0; x < GRID_WIDTH; x++) {
            this.boardState.remove(rowIndex * GRID_WIDTH);
        }
        this.boardState.addAll(0, Arrays.asList(blankRow));
    }
}
