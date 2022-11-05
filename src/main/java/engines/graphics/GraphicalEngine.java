package engines.graphics;

import engines.physics.Direction;
import engines.physics.Entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine implements KeyListener {
    public boolean delete = false;
    ObjectsManager objectsManager;
    Player player;


    /**
     * Constructs a new panel that draw an image.
     * <p>
     * this panel uses the objects manager to extract the image that we want to draw into the panel
     * <p>
     * relies on the image into the physicalEntity
     * <p>
     * moves the image
     */


    public JPanel jPanel = new JPanel(true) {
        @Override
        public void paintComponent(Graphics g) {
            System.out.println("player Position  = " + player.getPosition());
            g.drawImage(objectsManager.getGraphicalObjects("pacman"), player.getX(), player.getY(), null);
        }
        @Override
        public void setFocusable(boolean b) {
            super.setFocusable(b);
        }

    };


    public GraphicalEngine() throws IOException {

        player = new Player(0, 0);
        objectsManager = new ObjectsManager();
        jPanel.setSize(new Dimension(640, 640));

        try {
            jPanel.addKeyListener(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {

      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.move(Direction.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.move(Direction.LEFT);

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.move(Direction.DOWN);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.move(Direction.UP);
        }
        jPanel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {


    }


}
