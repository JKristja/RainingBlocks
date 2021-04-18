package main.model;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract class representing generic puzzle piece
 */
public abstract class PuzzlePiece {
    private int x0;
    private int y0;
    private int x1;
    private int y1;
    private ArrayList<Integer[]> shape;
    protected ArrayList<ArrayList<Integer[]>> rotationMap;
    protected Random rand;
    protected int currentRotation;
    protected Color color;

    public enum PIECES {J, L, SQ, ST, T, ZI, ZA}

    public PuzzlePiece(int x0, int y0) {
        this.x0 = x0;
        this.y0 = y0;
        setColor();
        this.rotationMap = new ArrayList<>();
        this.rand = new Random();
    }

    /**
     * Creates Hashmap of xy points representing the vertices of the shape
     *
     * @param xyPairs xy paired points corresponding to relative location of each vertex.  0,0 for top left
     * @return numbered vertices with corresponding integer xy pairs
     * @throws InvalidParameterException if xyPairs is 0 or not even length
     */
    protected ArrayList<Integer[]> makeShape(Integer[] xyPairs) throws InvalidParameterException {
        if (xyPairs.length == 0 || xyPairs.length % 2 != 0) {
            throw new InvalidParameterException("xyPairs expects even length parameter list > 0");
        }

        this.shape = new ArrayList<>();
        for (int i = 0; i < xyPairs.length; i += 2) {
            this.shape.add(i / 2 + 1, new Integer[]{i, i + 1});
        }
        return this.shape;
    }

    abstract void setColor();

    public Color getColor() {
        return this.color;
    }

    public int getX0() {
        return this.x0;
    }

    public int getY0() {
        return this.y0;
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
    }

    /**
     * sets x1,y1 values for puzzle piece for current rotation
     */
    protected void setX1Y1() {
        this.x1 = 0;
        this.y1 = 0;
        for (Integer[] xy : this.getVertices()) {
            this.x1 = Math.max(this.x1, xy[0]);
            this.y1 = Math.max(this.y1, xy[1]);
        }
        this.x1 += this.x0;
        this.y1 += this.y0;
    }

    /**
     * @return vertices of current shape rotation
     */
    public ArrayList<Integer[]> getVertices() {
        return rotationMap.get(currentRotation);
    }

    public void moveLeft() {
        this.x0 -= 1;
    }

    public void moveRight() {
        this.x0 += 1;
    }

    public void moveDown() {
        this.y0 += 1;
    }

    public void dropPiece(int yToFixedPiece) {
        this.y0 += yToFixedPiece;
    }

    /**
     * MODIFIES: this
     * Rotates puzzle piece counter clockwise by changing vertex order and position
     */
    public void rotateCounterCW() {
        if (currentRotation == 0) {
            currentRotation = rotationMap.size();
        } else {
            currentRotation -= 1;
        }
    }

    /**
     * MODIFIES: this
     * Rotates puzzle piece clockwise by changing vertex order and position
     */
    public void rotateCW() {
        if (currentRotation == rotationMap.size()) {
            currentRotation = 0;
        } else {
            currentRotation += 1;
        }
    }
}
