package main.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents T shaped puzzle piece made of 4 vertices.  Shape has 4 possible rotations
 */
public class TPiece extends PuzzlePiece {
    public static final ArrayList<Integer[]> T_SHAPE = new ArrayList<Integer[]>() {{
        add(0, new Integer[]{0, 1, 1, 0, 1, 1, 2, 1});
        add(1, new Integer[]{0, 0, 0, 1, 0, 2, 1, 1});
        add(2, new Integer[]{0, 0, 1, 0, 1, 1, 2, 0});
        add(3, new Integer[]{0, 1, 1, 0, 1, 1, 1, 2});
    }};

    /**
     * Constructs 4 Vertex T Piece with random starting rotation
     *
     * @param x0 origin x coordinate for piece
     * @param y0 origin y coordinate for piece
     */
    public TPiece(int x0, int y0) {
        super(x0, y0);
        for (int i = 0; i < T_SHAPE.size(); i++) {
            rotationMap.add(makeShape(T_SHAPE.get(i)));
        }
        currentRotation = RANDOM.nextInt(T_SHAPE.size());
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * T Piece Color - should be set different from BACKGROUND Color.
     */
    @Override
    public void setColor() {
        this.color = new Color(255, 128, 0);
    }
}
