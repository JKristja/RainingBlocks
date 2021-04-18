package main.model;

import java.awt.*;
import java.util.ArrayList;

public class LPiece extends PuzzlePiece {
    private static final ArrayList<Integer[]> SHAPE = new ArrayList<Integer[]>() {{
        add(0, new Integer[]{0, 0, 0, 1, 0, 2, 1, 2});
        add(1, new Integer[]{0, 0, 0, 1, 1, 0, 2, 0});
        add(2, new Integer[]{0, 0, 1, 0, 1, 1, 1, 2});
        add(3, new Integer[]{0, 1, 1, 1, 2, 0, 2, 1});
    }};

    public LPiece(int x0, int y0) {
        super(x0, y0);
        for (int i = 0; i < SHAPE.size(); i++) {
            rotationMap.set(i, makeShape(SHAPE.get(i)));
        }
        currentRotation = rand.nextInt(SHAPE.size());
        setX1Y1();
    }

    @Override
    void setColor() {
        color = new Color(255, 0, 0);
    }

}
