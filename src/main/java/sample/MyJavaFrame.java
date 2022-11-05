package sample;

import engines.graphics.GraphicalEngine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;


/**
 * An extended version of javax.swing.JFrame containing a panel to draw images.
 */
public class MyJavaFrame extends JFrame implements KeyListener {


    public static final int ORIGINAL_TILE_SIZE = 64 ; // 64 x 64 TILES
    public static final int SCALE = 1;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COL = 10;
    public static final int MAX_SCREEN_ROW = 10;
    public static final int SCENE_WIDTH = TILE_SIZE * MAX_SCREEN_COL ;
    public static final int SCENE_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW ;

    GraphicalEngine graphicalEngine = new GraphicalEngine();


    /**
     * Constructs a new visible frame.
     */

    public MyJavaFrame() throws IOException {
        setSize(new Dimension(SCENE_WIDTH,SCENE_HEIGHT));
        addKeyListener(this);
        setTitle("Game Windows ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphicalEngine.jPanel.setSize(new Dimension(SCENE_WIDTH,SCENE_HEIGHT));
        add(graphicalEngine.jPanel);
        setVisible(true);
        System.out.println("SCENE_HEIGHT = " + SCENE_HEIGHT);
        System.out.println("SCENE_WIDTH = " + SCENE_WIDTH);
    }


    public static void main(String[] args) throws IOException {
        new MyJavaFrame();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        graphicalEngine.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        graphicalEngine.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        graphicalEngine.keyReleased(e);

    }
}
