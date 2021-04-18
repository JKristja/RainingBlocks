package main.ui;

import main.model.PuzzleGame;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class DisplayPanel extends JPanel implements Observer {
    private PuzzleGame game;

    public DisplayPanel(PuzzleGame game) {
        this.game = game;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
