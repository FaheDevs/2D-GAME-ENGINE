package engines.graphics;

import engines.physics.PlayableEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine implements KeyListener {
    public Boolean delete = false;
    ObjectsManager objectsManager;
    PlayableEntity playableEntity;


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
            super.setSize(640, 640);
            System.out.println("POSITION " + playableEntity.getPosition());
            System.out.println("size :" + this.getSize());
            g.drawImage(objectsManager.getGraphicalObjects("pacman"), playableEntity.getX(), playableEntity.getY(), null);
        }

        @Override
        public void setFocusable(boolean b) {
            super.setFocusable(b);
        }

    };


    public GraphicalEngine() throws IOException {

        playableEntity = new PlayableEntity(0, 640);
        objectsManager = new ObjectsManager();
        jPanel.setSize(new Dimension(640, 640));

        try {
            jPanel.addKeyListener(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setDelete() {
        this.delete = true;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {

/*        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            physicalEntity.move(PhysicsUtilities.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            physicalEntity.move(PhysicsUtilities.LEFT);

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            physicalEntity.move(PhysicsUtilities.DOWN);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            physicalEntity.move(PhysicsUtilities.UP);
        }
        jPanel.repaint();

 */

    }

    @Override
    public void keyReleased(KeyEvent e) {


    }


}
