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
    protected ArrayList<ArrayList<Integer[]>> rotationMap = new ArrayList<>();
    protected static final Random RANDOM = new Random();
    protected int currentRotation;
    protected Color color;

    public enum PIECES {J, L, SQ, ST, T, ZI, ZA}

    public PuzzlePiece(int x0, int y0) {
        this.x0 = x0;
        this.y0 = y0;
        this.setColor();
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

        ArrayList<Integer[]> shape = new ArrayList<>();
        for (int i = 0; i < xyPairs.length; i += 2) {
            shape.add(new Integer[]{xyPairs[i], xyPairs[i + 1]});
        }
        return shape;
    }

    /**
     * MODIFIES: this
     * Color of Puzzle Piece
     */
    protected abstract void setColor();

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
     * MODIFIES: this
     * Sets current rotation index of piece; updates X1,Y1 accordingly.
     *
     * @param rotationIndex index corresponding
     * @throws IndexOutOfBoundsException if rotationIndex < 0 || rotationIndex >= rotationMap.size()
     */
    public void setCurrentRotation(int rotationIndex) throws IndexOutOfBoundsException {
        if (rotationIndex < 0 || rotationIndex >= this.rotationMap.size()) {
            throw new IndexOutOfBoundsException("Invalid Rotation Index");
        }
        this.currentRotation = rotationIndex;
        setX1Y1();
    }

    public int getCurrentRotation() {
        return this.currentRotation;
    }

    /**
     * MODIFIES: this
     * sets x1,y1 values for puzzle piece for current rotation;
     * x1,y1 are relative to current x0,y0
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

    /**
     * MODIFIES: this
     * Moves piece one unit left; updates X1,Y1
     */
    public void moveLeft() {
        this.x0 -= 1;
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Moves piece one unit right; updates X1,Y1
     */
    public void moveRight() {
        this.x0 += 1;
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Moves piece one unit down; updates X1,Y1
     */
    public void moveDown() {
        this.y0 += 1;
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Drops piece dropDistance units down; updates X1, Y1
     *
     * @param dropDistance number of units to drop piece
     */
    public void dropPiece(int dropDistance) {
        this.y0 += dropDistance;
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Rotates puzzle piece counter clockwise by changing vertex order and position; updates X1,Y1
     */
    public void rotateCounterCW() {
        if (this.currentRotation == 0) {
            this.currentRotation = this.rotationMap.size() - 1;
        } else {
            this.currentRotation -= 1;
        }
        setX1Y1();
    }

    /**
     * MODIFIES: this
     * Rotates puzzle piece clockwise by changing vertex order and position; updates X1,Y1
     */
    public void rotateCW() {
        if (this.currentRotation == this.rotationMap.size() - 1) {
            this.currentRotation = 0;
        } else {
            this.currentRotation += 1;
        }
        setX1Y1();
    }
}
