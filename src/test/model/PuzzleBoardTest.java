package test.model;

import main.model.PuzzleBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit tests for PuzzleBoard class
 */
public class PuzzleBoardTest {

    private static final Color BOARD_COLOR = new Color(0, 0, 0);
    private static final Color PIECE_COLOR = new Color(255, 0, 0);
    private static final int WIDTH = 8;
    private static final int HEIGHT = 12;
    private PuzzleBoard board;

    @BeforeEach
    public void setUp() {
        board = new PuzzleBoard(WIDTH, HEIGHT, BOARD_COLOR);
    }

    @Test
    public void testSetBlankBoard() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                assertEquals(BOARD_COLOR, board.getPos(x, y));
            }
        }
    }

    @Test
    public void testGetSetPos() {
        try {
            board.setPos(7, 11, PIECE_COLOR);
            assertEquals(PIECE_COLOR, board.getPos(7, 11));
        } catch (IndexOutOfBoundsException e) {
            fail("Exception not expected");
        }
        try {
            board.setPos(7, 12, PIECE_COLOR);
            fail("Exception expected");
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
        try {
            board.getPos(7, 12);
            fail("Exception expected");
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
        try {
            board.setPos(8, 11, PIECE_COLOR);
            fail("Exception expected");
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
        try {
            board.getPos(8, 11);
            fail("Exception expected");
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
    }

    @Test
    public void testRowComplete() {
        for (int x = 0; x < WIDTH; x++) {
            board.setPos(x, 1, PIECE_COLOR);
        }
        assertTrue(board.isRowComplete(1));
        board.setPos(1, 1, BOARD_COLOR);
        assertFalse(board.isRowComplete(1));
    }

    @Test
    public void testClearRow() {
        for (int x = 0; x < WIDTH; x++) {
            board.setPos(x, x, PIECE_COLOR);
            assertEquals(PIECE_COLOR, board.getPos(x, x));
        }

        board.clearRow(0);
        assertEquals(BOARD_COLOR, board.getPos(0, 0));
        for (int x = 1; x < WIDTH; x++) {
            assertEquals(PIECE_COLOR, board.getPos(x, x));
        }

        board.clearRow(2);
        assertEquals(BOARD_COLOR, board.getPos(1, 1));
        assertEquals(PIECE_COLOR, board.getPos(1, 2));
        for (int x = 3; x < WIDTH; x++) {
            assertEquals(PIECE_COLOR, board.getPos(x, x));
        }

        board.setPos(0, HEIGHT - 1, PIECE_COLOR);
        board.setPos(WIDTH - 1, HEIGHT - 2, PIECE_COLOR);
        board.clearRow(HEIGHT - 1);
        assertEquals(BOARD_COLOR, board.getPos(0, HEIGHT - 1));
        assertEquals(PIECE_COLOR, board.getPos(WIDTH - 1, HEIGHT - 1));
        assertEquals(PIECE_COLOR, board.getPos(1, 3));
        for (int x = 4; x < WIDTH; x++) {
            assertEquals(PIECE_COLOR, board.getPos(x, x + 1));
        }
    }
}
