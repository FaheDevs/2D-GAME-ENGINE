package sample;

import engines.graphics.GraphicalEngine;
import javax.swing.*;
import java.io.IOException;



/**
 * An extended version of javax.swing.JFrame containing a panel to draw images.
 */
public class MyJavaFrame {
    GraphicalEngine graphicalEngine = new GraphicalEngine();

    /**
     * Constructs a new visible frame.
     */

    public MyJavaFrame() throws IOException {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PACMAN GAME");
        window.add(graphicalEngine);
        window.pack();
        window.setLocationRelativeTo(null); // CENTER OF THE SCREEN
        window.setVisible(true);
        graphicalEngine.startGameThread();
    }

    public static void main(String[] args) throws IOException {
        new MyJavaFrame();
    }
}
