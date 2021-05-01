package test.model;

import main.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit tests for PuzzleGame class
 */
public class PuzzleGameTest {

    private PuzzleGame testGame;
    private PuzzleBoard testBoard;
    private PuzzlePiece testPiece;
    private static final int GRID_WIDTH = 8;
    private static final int GRID_HEIGHT = 14;
    private static final Color BACKGROUND = new Color(0, 0, 0);
    private static final int SCORE_PER_ROW = 50;
    private static final double ROW_SCORE_MULT = 0.15;
    private static final double LEVEL_SCORE_MULT = 0.1;

    @BeforeEach
    public void setUp() {
        testGame = new PuzzleGame(GRID_WIDTH, GRID_HEIGHT, BACKGROUND, new MockRandom());
        testBoard = testGame.getBoardState();
        testPiece = testGame.getCurrentPiece();
    }

    @Test
    public void testResetGame() {
        testBoard.setPos(1, 3, Color.WHITE);
        testGame.updateScore(20);
        testGame.resetGame();
        assertEquals(Color.BLACK, testBoard.getPos(1, 3));
        assertEquals(0, testGame.getScore());
        assertEquals(1, testGame.getLevel());
        assertEquals(0, testGame.getClearedRows());
    }

    @Test
    public void testNextStateIsClear() {
        testGame.userInput(KeyEvent.VK_DOWN);
        assertEquals(3, testPiece.getX0());
        assertEquals(1, testPiece.getY0());
    }

    @Test
    public void testNextStateIsFull() {
        Color pieceColor = testPiece.getColor();
        assertEquals(Color.GREEN, pieceColor);
        testPiece.setCurrentRotation(0);
        testBoard.setPos(3, 1, Color.WHITE);
        testBoard.setPos(4, 1, Color.WHITE);
        testBoard.setPos(5, 1, Color.WHITE);
        testGame.nextState();
        assertEquals(Color.GREEN, testBoard.getPos(3, 2));
        assertEquals(Color.GREEN, testBoard.getPos(4, 0));
        assertEquals(Color.GREEN, testBoard.getPos(4, 1));
        assertEquals(Color.GREEN, testBoard.getPos(4, 2));
        assertEquals(Color.WHITE, testBoard.getPos(3, 1));
        assertEquals(Color.WHITE, testBoard.getPos(5, 1));
        assertNotEquals(testPiece, testGame.getCurrentPiece());
        assertTrue(testGame.getCurrentPiece() instanceof LPiece);
    }

    @Test
    public void testPlacePieceIncompleteRow() {
        assertTrue(testPiece instanceof JPiece);
        testPiece.setCurrentRotation(1);
        testGame.userInput(KeyEvent.VK_SPACE);
        Color expectedColor = new Color(0, 255, 0);
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 2));
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 1));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 1));
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 1));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof LPiece);
        testPiece.setCurrentRotation(3);
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(255, 0, 0);
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 4));
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 3));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 3));
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 3));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof SquarePiece);
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(255, 255, 0);
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 5));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 5));
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 4));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 4));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof StraightPiece);
        testPiece.setCurrentRotation(1);
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(0, 255, 255);
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 6));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 6));
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 6));
        assertEquals(expectedColor, testBoard.getPos(6, GRID_HEIGHT - 6));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof TPiece);
        testPiece.setCurrentRotation(2);
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(255, 128, 0);
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 8));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 8));
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 8));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 7));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof ZigPiece);
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(0, 0, 255);
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 9));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 9));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 10));
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 10));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof ZagPiece);
        testPiece.setCurrentRotation(2);
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(120, 50, 150);
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 12));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 12));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 11));
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 11));
    }

    @Test
    public void testPlacePieceCompleteRow() {
        assertTrue(testPiece instanceof JPiece);
        testPiece.setCurrentRotation(1);
        testPiece.moveRight();
        testPiece.moveRight();
        testGame.userInput(KeyEvent.VK_SPACE);
        Color expectedColor = new Color(0, 255, 0);
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 2));
        assertEquals(expectedColor, testBoard.getPos(5, GRID_HEIGHT - 1));
        assertEquals(expectedColor, testBoard.getPos(6, GRID_HEIGHT - 1));
        assertEquals(expectedColor, testBoard.getPos(7, GRID_HEIGHT - 1));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof LPiece);
        testPiece.setCurrentRotation(3);
        testPiece.moveLeft();
        testPiece.moveLeft();
        testPiece.moveLeft();
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(255, 0, 0);
        assertEquals(expectedColor, testBoard.getPos(2, GRID_HEIGHT - 2));
        assertEquals(expectedColor, testBoard.getPos(0, GRID_HEIGHT - 1));
        assertEquals(expectedColor, testBoard.getPos(1, GRID_HEIGHT - 1));
        assertEquals(expectedColor, testBoard.getPos(2, GRID_HEIGHT - 1));

        testPiece = testGame.getCurrentPiece();
        assertTrue(testPiece instanceof SquarePiece);
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_SPACE);
        expectedColor = new Color(255, 255, 0);
        assertEquals(BACKGROUND, testBoard.getPos(3, GRID_HEIGHT - 2));
        assertEquals(BACKGROUND, testBoard.getPos(4, GRID_HEIGHT - 2));
        assertEquals(expectedColor, testBoard.getPos(3, GRID_HEIGHT - 1));
        assertEquals(expectedColor, testBoard.getPos(4, GRID_HEIGHT - 1));
        assertEquals(BACKGROUND, testBoard.getPos(0, GRID_HEIGHT - 1));
        assertEquals(BACKGROUND, testBoard.getPos(1, GRID_HEIGHT - 1));
        assertEquals(Color.RED, testBoard.getPos(2, GRID_HEIGHT - 1));
        assertEquals(Color.GREEN, testBoard.getPos(5, GRID_HEIGHT - 1));
        assertEquals(BACKGROUND, testBoard.getPos(6, GRID_HEIGHT - 1));
        assertEquals(BACKGROUND, testBoard.getPos(7, GRID_HEIGHT - 1));

        for (int x = 0; x < GRID_WIDTH - 1; x++) {
            testGame.setCurrentPiece(new StraightPiece(x, 0));
            testPiece = testGame.getCurrentPiece();
            testPiece.setCurrentRotation(0);
            testGame.userInput(KeyEvent.VK_SPACE);
            expectedColor = new Color(0, 255, 255);
            if (x == 2 || x == 3 || x == 4 || x == 5) {
                for (int y = GRID_HEIGHT - 5; y < GRID_HEIGHT - 1; y++) {
                    assertEquals(expectedColor, testBoard.getPos(x, y));
                }
            } else {
                for (int y = GRID_HEIGHT - 4; y < GRID_HEIGHT; y++) {
                    assertEquals(expectedColor, testBoard.getPos(x, y));
                }
            }
        }

        testGame.setCurrentPiece(new StraightPiece(GRID_WIDTH - 1, 0));
        testPiece = testGame.getCurrentPiece();
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_SPACE);
        for (int x = 0; x < GRID_WIDTH; x++) {
            if (x == 2 || x == 3 || x == 4 || x == 5) {
                for (int y = GRID_HEIGHT - 5; y < GRID_HEIGHT - 1; y++) {
                    assertEquals(BACKGROUND, testBoard.getPos(x, y));
                }
            } else {
                for (int y = GRID_HEIGHT - 4; y < GRID_HEIGHT; y++) {
                    assertEquals(BACKGROUND, testBoard.getPos(x, y));
                }
            }
        }
    }

    @Test
    public void testUpdateScore() {
        int clearedRows = 0;
        Integer[] expectedScores = new Integer[]{
                SCORE_PER_ROW,
                (int) (50 + SCORE_PER_ROW * 2 * (1 + 1 * ROW_SCORE_MULT)),
                (int) (165 + SCORE_PER_ROW * 3 * (1 + 2 * ROW_SCORE_MULT)),
                (int) (360 + SCORE_PER_ROW * 4 * (1 + 3 * ROW_SCORE_MULT))};
        for (int i = 1; i < 5; i++) {
            testGame.updateScore(i);
            assertEquals(expectedScores[i - 1], testGame.getScore());
            assertEquals(clearedRows += i, testGame.getClearedRows());
            assertEquals(1, testGame.getLevel());
        }
        testGame.updateScore(4);
        assertEquals(650 + (int) (SCORE_PER_ROW * 4 * (1 + 3 * ROW_SCORE_MULT)),
                testGame.getScore());
        assertEquals(0, testGame.getClearedRows());
        assertEquals(2, testGame.getLevel());
        testGame.updateScore(3);
        assertEquals(940 + (int) (SCORE_PER_ROW * 3 * (1 + 2 * ROW_SCORE_MULT + LEVEL_SCORE_MULT)),
                testGame.getScore());
        assertEquals(3, testGame.getClearedRows());
        assertEquals(2, testGame.getLevel());

        assertEquals(2000, testGame.getTopScore());
        testGame.setTopScore();
        assertEquals(2000, testGame.getTopScore());

        testGame.updateScore(10);
        assertEquals((int) (1150 + SCORE_PER_ROW * 10 * (1 + 9 * ROW_SCORE_MULT + LEVEL_SCORE_MULT)),
                testGame.getScore());
        testGame.setTopScore();
        assertEquals(2375, testGame.getTopScore());
    }

    @Test
    public void testGameOver() {
        assertFalse(testGame.isGameOver());
        testGame.userInput(KeyEvent.VK_DOWN);
        assertFalse(testGame.isGameOver());
        testGame.setCurrentPiece(new LPiece(3, 0));
        testPiece = testGame.getCurrentPiece();
        testPiece.setCurrentRotation(0);
        testBoard.setPos(3, 3, Color.WHITE);
        testBoard.setPos(4, 3, Color.WHITE);
        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testIsClearLeft() {
        testGame.userInput(KeyEvent.VK_LEFT);
        assertEquals(2, testPiece.getX0());
        assertEquals(0, testPiece.getY0());

        testBoard.setPos(2, 2, Color.WHITE);
        testGame.setCurrentPiece(new JPiece(3, 0));
        testPiece = testGame.getCurrentPiece();
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_LEFT);
        assertEquals(3, testPiece.getX0());
        assertEquals(0, testPiece.getY0());

        testGame.userInput(KeyEvent.VK_DOWN);
        testGame.userInput(KeyEvent.VK_LEFT);
        assertEquals(2, testPiece.getX0());
        assertEquals(1, testPiece.getY0());

        testBoard.setPos(2, 2, BACKGROUND);
        testGame.userInput(KeyEvent.VK_LEFT);
        testGame.userInput(KeyEvent.VK_LEFT);
        assertEquals(0, testPiece.getX0());
        testGame.userInput(KeyEvent.VK_LEFT);
        assertEquals(0, testPiece.getX0());
    }

    @Test
    public void testIsClearRight() {
        testGame.userInput(KeyEvent.VK_RIGHT);
        assertEquals(4, testPiece.getX0());
        assertEquals(0, testPiece.getY0());

        testBoard.setPos(5, 2, Color.WHITE);
        testGame.setCurrentPiece(new LPiece(3, 0));
        testPiece = testGame.getCurrentPiece();
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_RIGHT);
        assertEquals(3, testPiece.getX0());
        assertEquals(0, testPiece.getY0());

        testGame.userInput(KeyEvent.VK_DOWN);
        testGame.userInput(KeyEvent.VK_RIGHT);
        assertEquals(4, testPiece.getX0());
        assertEquals(1, testPiece.getY0());

        testBoard.setPos(5, 2, BACKGROUND);
        testGame.userInput(KeyEvent.VK_RIGHT);
        testGame.userInput(KeyEvent.VK_RIGHT);
        assertEquals(GRID_WIDTH - 1, testPiece.getX1());
        testGame.userInput(KeyEvent.VK_RIGHT);
        assertEquals(GRID_WIDTH - 1, testPiece.getX1());
    }

    @Test
    public void testIsClearCounterCW() {
        testPiece.setCurrentRotation(1);
        testGame.userInput(KeyEvent.VK_A);
        assertEquals(0, testPiece.getCurrentRotation());

        for (int i = 0; i < 3; i++) {
            testGame.userInput(KeyEvent.VK_RIGHT);
        }
        assertEquals(GRID_WIDTH - 1, testPiece.getX1());
        testGame.userInput(KeyEvent.VK_A);
        assertEquals(0, testPiece.getCurrentRotation());

        testGame.userInput(KeyEvent.VK_LEFT);
        assertTrue(testGame.isClearCounterCW());
        testBoard.setPos(GRID_WIDTH - 1, 0, Color.WHITE);
        assertFalse(testGame.isClearCounterCW());

        testGame.setCurrentPiece(new TPiece(5, GRID_HEIGHT - 2));
        testPiece = testGame.getCurrentPiece();
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_A);
        assertEquals(0, testPiece.getCurrentRotation());
    }

    @Test
    public void testIsClearCW() {
        testPiece.setCurrentRotation(1);
        testGame.userInput(KeyEvent.VK_D);
        assertEquals(2, testPiece.getCurrentRotation());

        for (int i = 0; i < 3; i++) {
            testGame.userInput(KeyEvent.VK_RIGHT);
        }
        assertEquals(GRID_WIDTH - 1, testPiece.getX1());
        testGame.userInput(KeyEvent.VK_D);
        assertEquals(2, testPiece.getCurrentRotation());

        testGame.userInput(KeyEvent.VK_LEFT);
        assertTrue(testGame.isClearCW());
        testBoard.setPos(GRID_WIDTH - 1, 1, Color.WHITE);
        assertFalse(testGame.isClearCW());

        testGame.setCurrentPiece(new ZigPiece(5, GRID_HEIGHT - 2));
        testPiece = testGame.getCurrentPiece();
        testPiece.setCurrentRotation(0);
        testGame.userInput(KeyEvent.VK_D);
        assertEquals(0, testPiece.getCurrentRotation());
    }
}
