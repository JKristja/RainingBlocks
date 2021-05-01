package main.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents Straight puzzle piece made of 4 vertices.  Shape has 4 possible rotations
 */
public class StraightPiece extends PuzzlePiece {
    public static final ArrayList<Integer[]> ST_SHAPE = new ArrayList<Integer[]>() {{
        add(0, new Integer[]{0, 0, 0, 1, 0, 2, 0, 3});
        add(1, new Integer[]{0, 0, 1, 0, 2, 0, 3, 0});
        add(2, new Integer[]{0, 0, 0, 1, 0, 2, 0, 3});
        add(3, new Integer[]{0, 0, 1, 0, 2, 0, 3, 0});
    }};

    /**
     * Constructs 4 Vertex Straight Piece with random starting rotation
     *
     * @param x0 origin x coordinate for piece
     * @param y0 origin y coordinate for piece
     */
    public StraightPiece(int x0, int y0) {
        super(x0, y0);
        for (int i = 0; i < ST_SHAPE.size(); i++) {
            rotationMap.add(makeShape(ST_SHAPE.get(i)));
        }
        currentRotation = RANDOM.nextInt(ST_SHAPE.size());
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Straight Piece Color - should be set different from BACKGROUND Color.
     */
    @Override
    public void setColor() {
        this.color = new Color(0, 255, 255);
    }
}
