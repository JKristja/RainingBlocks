package test.model;

import java.util.Random;

public class MockRandom extends Random {

    int currentInt = 0;

    public MockRandom() {
        super();
    }

    @Override
    public int nextInt(int maxInt) {
        if (currentInt >= maxInt) {
            currentInt = 0;
        }
        return currentInt++;
    }
}
