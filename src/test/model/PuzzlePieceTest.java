package test.model;

import main.model.PuzzlePiece;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests for PuzzlePiece class
 */
public abstract class PuzzlePieceTest {

    protected PuzzlePiece puzzlePiece;
    protected static final int PIECE_X0 = 8;
    protected static final int PIECE_Y0 = 0;
    protected static final Color J_PIECE_COLOR = new Color(0, 255, 0);
    protected static final Color L_PIECE_COLOR = new Color(255, 0, 0);
    protected static final Color SQ_PIECE_COLOR = new Color(255, 255, 0);
    protected static final Color ST_PIECE_COLOR = new Color(0, 255, 255);
    protected static final Color T_PIECE_COLOR = new Color(255, 128, 0);
    protected static final Color ZAG_PIECE_COLOR = new Color(120, 50, 150);
    protected static final Color ZIG_PIECE_COLOR = new Color(0, 0, 255);

    @Test
    public void testConstructor() {
        assertEquals(PIECE_X0, puzzlePiece.getX0());
        assertEquals(PIECE_Y0, puzzlePiece.getY0());
        this.testColor();
    }

    @Test
    public abstract void testColor();

    protected void testRotationMapHelper(ArrayList<Integer[]> expectedVertices) {
        ArrayList<Integer[]> computedVertices = puzzlePiece.getVertices();
        assertEquals(expectedVertices.size(), computedVertices.size());
        for (int i = 0; i < expectedVertices.size(); i++) {
            assertEquals(expectedVertices.get(i)[0], computedVertices.get(i)[0]);
            assertEquals(expectedVertices.get(i)[1], computedVertices.get(i)[1]);
        }
    }

    protected void testSetX1Y1(Integer[] xyExpected) {
        assertEquals(PIECE_X0 + xyExpected[0], puzzlePiece.getX1());
        assertEquals(PIECE_Y0 + xyExpected[1], puzzlePiece.getY1());
    }

    @Test
    public void testCardinalMovement() {
        int x1 = puzzlePiece.getX1();
        int y1 = puzzlePiece.getY1();

        puzzlePiece.moveLeft();
        assertEquals(PIECE_X0 - 1, puzzlePiece.getX0());
        assertEquals(PIECE_Y0, puzzlePiece.getY0());
        assertEquals(x1 - 1, puzzlePiece.getX1());
        assertEquals(y1, puzzlePiece.getY1());

        puzzlePiece.moveRight();
        assertEquals(PIECE_X0, puzzlePiece.getX0());
        assertEquals(PIECE_Y0, puzzlePiece.getY0());
        assertEquals(x1, puzzlePiece.getX1());
        assertEquals(y1, puzzlePiece.getY1());

        puzzlePiece.moveDown();
        assertEquals(PIECE_X0, puzzlePiece.getX0());
        assertEquals(PIECE_Y0 + 1, puzzlePiece.getY0());
        assertEquals(x1, puzzlePiece.getX1());
        assertEquals(y1 + 1, puzzlePiece.getY1());

        puzzlePiece.dropPiece(5);
        assertEquals(PIECE_X0, puzzlePiece.getX0());
        assertEquals(PIECE_Y0 + 6, puzzlePiece.getY0());
        assertEquals(x1, puzzlePiece.getX1());
        assertEquals(y1 + 6, puzzlePiece.getY1());
    }

    protected void testCounterCWRotation(ArrayList<Integer[]> xyMax) {
        puzzlePiece.setCurrentRotation(0);
        puzzlePiece.rotateCounterCW();
        assertEquals(xyMax.size() - 1, puzzlePiece.getCurrentRotation());
        testSetX1Y1(xyMax.get(xyMax.size() - 1));
        for (int i = 3; i > 0; i--) {
            puzzlePiece.rotateCounterCW();
            assertEquals(i - 1, puzzlePiece.getCurrentRotation());
            testSetX1Y1(xyMax.get(i - 1));
        }
    }

    protected void testCWRotation(ArrayList<Integer[]> xyMax) {
        puzzlePiece.setCurrentRotation(0);
        for (int i = 0; i < xyMax.size() - 1; i++) {
            puzzlePiece.rotateCW();
            assertEquals(i + 1, puzzlePiece.getCurrentRotation());
            testSetX1Y1(xyMax.get(i + 1));
        }
        puzzlePiece.rotateCW();
        assertEquals(0, puzzlePiece.getCurrentRotation());
        testSetX1Y1(xyMax.get(0));
    }
}
