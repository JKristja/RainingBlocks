package test.model;

import main.model.StraightPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static main.model.StraightPiece.ST_SHAPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests for StraightPiece class
 */
public class StraightPieceTest extends PuzzlePieceTest {
    private ArrayList<Integer[]> rotation0 = new ArrayList<Integer[]>() {
        {
            add(new Integer[]{0, 0});
            add(new Integer[]{0, 1});
            add(new Integer[]{0, 2});
            add(new Integer[]{0, 3});
        }
    };
    private ArrayList<Integer[]> rotation1 = new ArrayList<Integer[]>() {
        {
            add(new Integer[]{0, 0});
            add(new Integer[]{1, 0});
            add(new Integer[]{2, 0});
            add(new Integer[]{3, 0});
        }
    };
    private ArrayList<ArrayList<Integer[]>> rotationMap = new ArrayList<ArrayList<Integer[]>>() {{
        add(rotation0);
        add(rotation1);
        add(rotation0);
        add(rotation1);
    }};
    private ArrayList<Integer[]> xyMax = new ArrayList<Integer[]>() {{
        add(new Integer[]{0, 3});
        add(new Integer[]{3, 0});
        add(new Integer[]{0, 3});
        add(new Integer[]{3, 0});
    }};

    @BeforeEach
    public void setUp() {
        puzzlePiece = new StraightPiece(PIECE_X0, PIECE_Y0);
    }

    @Test
    public void testConstructor() {
        super.testConstructor();
        for (int i = 0; i < ST_SHAPE.size(); i++) {
            puzzlePiece.setCurrentRotation(i);
            testRotationMapHelper(rotationMap.get(i));
            testSetX1Y1(xyMax.get(i));
        }
    }

    @Test
    public void testColor() {
        assertEquals(ST_PIECE_COLOR, puzzlePiece.getColor());
    }

    @Test
    public void testCardinalMovement() {
        puzzlePiece.setCurrentRotation(1);
        super.testCardinalMovement();
    }

    @Test
    public void testCounterCWRotation() {
        super.testCounterCWRotation(xyMax);
    }

    @Test
    public void testCWRotation() {
        super.testCWRotation(xyMax);
    }
}
