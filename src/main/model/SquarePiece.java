package main.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents Square puzzle piece made of 4 vertices.  Shape has 4 possible equal rotations
 */
public class SquarePiece extends PuzzlePiece {
    public static final ArrayList<Integer[]> SQ_SHAPE = new ArrayList<Integer[]>() {{
        add(0, new Integer[]{0, 0, 0, 1, 1, 0, 1, 1});
        add(1, new Integer[]{0, 0, 0, 1, 1, 0, 1, 1});
        add(2, new Integer[]{0, 0, 0, 1, 1, 0, 1, 1});
        add(3, new Integer[]{0, 0, 0, 1, 1, 0, 1, 1});
    }};

    /**
     * Constructs 4 Vertex Square Piece with random starting rotation
     *
     * @param x0 origin x coordinate for piece
     * @param y0 origin y coordinate for piece
     */
    public SquarePiece(int x0, int y0) {
        super(x0, y0);
        for (int i = 0; i < SQ_SHAPE.size(); i++) {
            rotationMap.add(makeShape(SQ_SHAPE.get(i)));
        }
        currentRotation = RANDOM.nextInt(SQ_SHAPE.size());
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Square Piece Color - should be set different from BACKGROUND Color.
     */
    @Override
    public void setColor() {
        this.color = new Color(255, 255, 0);
    }
}
