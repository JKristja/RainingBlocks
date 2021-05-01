package main.model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Random;

/**
 * represents the puzzle game; contains board and one active piece
 */
public class PuzzleGame extends Observable {
    private final int X0_NEW_PIECE;
    private static final int Y0_NEW_PIECE = 0;
    private int level;
    private int score;
    private int topScore;
    private int clearedRows;
    private static final int SCORE_PER_ROW = 50;
    private static final int ROWS_PER_LEVEL = 12;
    private static final double ROW_SCORE_MULT = 0.15;
    private static final double LEVEL_SCORE_MULT = 0.1;
    private static final int DEFAULT_SCORE = 2000;
    private PuzzleBoard board;
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final Color BACKGROUND;
    private PuzzlePiece currentPiece;
    private Random random;

    public PuzzleGame(int gridWidth, int gridHeight, Color background, Random random) {
        initScore();
        this.X0_NEW_PIECE = gridWidth / 2 - 1;
        this.GRID_WIDTH = gridWidth;
        this.GRID_HEIGHT = gridHeight;
        this.BACKGROUND = background;
        this.board = new PuzzleBoard(gridWidth, gridHeight, background);
        this.random = random;
        this.currentPiece = randomPiece();
    }

    /**
     * MODIFIES: this
     * Initializes level and score
     */
    private void initScore() {
        this.level = 1;
        this.score = 0;
        this.topScore = DEFAULT_SCORE;
        this.clearedRows = 0;
    }

    public int getLevel() {
        return this.level;
    }

    public int getScore() {
        return this.score;
    }

    public int getTopScore() {
        return this.topScore;
    }

    public int getClearedRows() {
        return this.clearedRows;
    }

    /**
     * MODIFIES: this
     * Updates TopScore if current Score is higher
     */
    public void setTopScore() {
        this.topScore = Math.max(this.score, this.topScore);
        this.setChanged();
        this.notifyObservers("TopScore");
    }

    /**
     * MODIFIES: this
     * Resets all board rows to BACKGROUND; resets score and level
     */
    public void resetGame() {
        this.board.setBlankBoard();
        this.score = 0;
        this.level = 1;
        this.clearedRows = 0;
        this.setChanged();
        notifyObservers("Score");
    }

    /**
     * MODIFIES: this
     * generate new random piece as current piece
     *
     * @return current puzzle piece
     */
    private PuzzlePiece randomPiece() {
        int numTypes = PuzzlePiece.PIECES.values().length;
        PuzzlePiece.PIECES piece = PuzzlePiece.PIECES.values()[random.nextInt(numTypes)];
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

    public void setCurrentPiece(PuzzlePiece puzzlePiece) {
        this.currentPiece = puzzlePiece;
    }

    /**
     * MODIFIES: this
     * Advances puzzle piece one unit down if able; Sets piece in place and generates new piece at top otherwise.
     */
    public void nextState() {
        if (isClearBelow(1)) {
            this.currentPiece.moveDown();
            this.setChanged();
            this.notifyObservers();
        } else {
            placePiece();
            nextPiece();
            this.setChanged();
            this.notifyObservers("Score");
        }
    }

    /**
     * MODIFIES: this
     * sets piece on board; checks if any rows completed; clears completed rows in sequential order from top
     * Updates Score for cleared rows;
     */
    public void placePiece() {
        int x = currentPiece.getX0();
        int y = currentPiece.getY0();
        int yMax = currentPiece.getY1();
        int rowsCleared = 0;
        for (Integer[] xy : currentPiece.getVertices()) {
            board.setPos(x + xy[0], y + xy[1], currentPiece.color);
        }
        for (int row = y; row <= yMax; row++) {
            if (board.isRowComplete(row)) {
                board.clearRow(row);
                rowsCleared++;
            }
        }
        updateScore(rowsCleared);
    }

    /**
     * Updates score with additional points for rows cleared;
     * Checks if next level reached; resets clearedRows counter if new level.
     *
     * @param rowsCleared number of rows cleared to be scored
     */
    public void updateScore(int rowsCleared) {
        score += (SCORE_PER_ROW * rowsCleared
                * (1 + (rowsCleared - 1) * ROW_SCORE_MULT + (level - 1) * LEVEL_SCORE_MULT));

        clearedRows += rowsCleared;
        if (clearedRows > ROWS_PER_LEVEL) {
            level++;
            clearedRows = 0;
        }
    }

    /**
     * MODIFIES: this
     * generates new random piece
     */
    private void nextPiece() {
        this.currentPiece = randomPiece();
    }

    /**
     * @return true if game is over (new piece with no clear space below)
     */
    public boolean isGameOver() {
        if (this.currentPiece.getX0() == X0_NEW_PIECE && this.currentPiece.getY0() == Y0_NEW_PIECE) {
            return !isClearBelow(1);
        }
        return false;
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
        } else if (keyCode == KeyEvent.VK_DOWN) {
            nextState();
        } else if (keyCode == KeyEvent.VK_A && isClearCounterCW()) {
            currentPiece.rotateCounterCW();
        } else if (keyCode == KeyEvent.VK_D && isClearCW()) {
            currentPiece.rotateCW();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            currentPiece.dropPiece(getDistToBottom());
            nextState();
        }
        this.setChanged();
        notifyObservers();
    }

    /**
     * @return true if current piece can move one grid space down (space is not occupied)
     */
    public boolean isClearBelow(int row) {
        int nextX = currentPiece.getX0();
        int nextY = currentPiece.getY0() + row;

        if (currentPiece.getY1() + row < GRID_HEIGHT) {
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

        if (currentPiece.getX1() + 1 < GRID_WIDTH) {
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

        if (currentPiece.getX1() < GRID_WIDTH && currentPiece.getY1() < GRID_HEIGHT) {
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

        if (currentPiece.getX1() < GRID_WIDTH && currentPiece.getY1() < GRID_HEIGHT) {
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
        int y_dist = 1;
        while (isClearBelow(y_dist)) {
            y_dist++;
        }
        return y_dist - 1;
    }
}
