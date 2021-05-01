package main.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents Z shaped puzzle piece made of 4 vertices.  Shape has 4 possible rotations
 */
public class ZagPiece extends PuzzlePiece {
    public static final ArrayList<Integer[]> ZAG_SHAPE = new ArrayList<Integer[]>() {{
        add(0, new Integer[]{0, 0, 1, 0, 1, 1, 2, 1});
        add(1, new Integer[]{0, 1, 0, 2, 1, 0, 1, 1});
        add(2, new Integer[]{0, 0, 1, 0, 1, 1, 2, 1});
        add(3, new Integer[]{0, 1, 0, 2, 1, 0, 1, 1});
    }};

    /**
     * Constructs 4 Vertex Z Piece with random starting rotation
     *
     * @param x0 origin x coordinate for piece
     * @param y0 origin y coordinate for piece
     */
    public ZagPiece(int x0, int y0) {
        super(x0, y0);
        for (int i = 0; i < ZAG_SHAPE.size(); i++) {
            rotationMap.add(makeShape(ZAG_SHAPE.get(i)));
        }
        currentRotation = RANDOM.nextInt(ZAG_SHAPE.size());
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Z Piece Color - should be set different from BACKGROUND Color.
     */
    @Override
    public void setColor() {
        this.color = new Color(120, 50, 150);
    }
}
