package sample;

import engines.graphics.GraphicalEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.Serial;
import javax.swing.JFrame;


/**
 * An extended version of javax.swing.JFrame containing a panel to draw images.
 */
public class MyJavaFrame extends JFrame implements KeyListener {

    @Serial
    private static final long serialVersionUID = 42L;

    public static final int SCENE_WIDTH=600;
    public static final int SCENE_HEIGHT=600;

    GraphicalEngine graphicalEngine = new GraphicalEngine();



    /**
     * Constructs a new visible frame.
     */

    public MyJavaFrame() throws IOException {
        this.addKeyListener(this);
        setTitle("Main window");
        setSize(SCENE_WIDTH, SCENE_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(graphicalEngine.tester);
        graphicalEngine.tester.setFocusable(true);
        setVisible(true);
        setResizable(false);
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
