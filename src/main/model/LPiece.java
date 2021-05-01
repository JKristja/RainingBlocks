package main.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents L shaped puzzle piece made of 4 vertices.  Shape has 4 possible rotations
 */
public class LPiece extends PuzzlePiece {
    public static final ArrayList<Integer[]> L_SHAPE = new ArrayList<Integer[]>() {{
        add(0, new Integer[]{0, 0, 0, 1, 0, 2, 1, 2});
        add(1, new Integer[]{0, 0, 0, 1, 1, 0, 2, 0});
        add(2, new Integer[]{0, 0, 1, 0, 1, 1, 1, 2});
        add(3, new Integer[]{0, 1, 1, 1, 2, 0, 2, 1});
    }};

    /**
     * Constructs 4 Vertex L Piece with random starting rotation
     *
     * @param x0 origin x coordinate for piece
     * @param y0 origin y coordinate for piece
     */
    public LPiece(int x0, int y0) {
        super(x0, y0);
        for (int i = 0; i < L_SHAPE.size(); i++) {
            rotationMap.add(makeShape(L_SHAPE.get(i)));
        }
        currentRotation = RANDOM.nextInt(L_SHAPE.size());
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * L Piece Color - should be set different from BACKGROUND Color.
     */
    @Override
    public void setColor() {
        color = new Color(255, 0, 0);
    }

}
