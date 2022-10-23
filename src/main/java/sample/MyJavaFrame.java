package sample;

import engines.graphics.Engine;
import engines.physics.Direction;

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

    Engine engine = new Engine();


    /**
     * Constructs a new visible frame.
     */

    public MyJavaFrame() throws IOException {
        this.addKeyListener(this);
        setTitle("Main window");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(engine);
        engine.setFocusable(true);
        setVisible(true);
    }


    public static void main(String[] args) throws IOException {
        new MyJavaFrame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

       engine.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        engine.keyPressed(e);


    }

    @Override
    public void keyReleased(KeyEvent e) {
        engine.keyReleased(e);


    }
}
