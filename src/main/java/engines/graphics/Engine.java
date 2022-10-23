package engines.graphics;

import engines.physics.Entity;
import engines.physics.PhysicsUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.Serial;
import java.text.MessageFormat;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class Engine extends JPanel implements KeyListener {
    @Serial
    private static final long serialVersionUID = 4242L;
    public Boolean delete = false;
    ObjectsManager objectsManager;
    Entity entity;


    /**
     * Constructs a new panel that draw an image.
     *
     * this panel uses the objectsmanager to extract the image that we want to draw into the panel
     *
     * relies the image into the entity
     *
     * moves the image
     */

    public Engine() throws IOException {

        entity = new Entity(400, 100);
        this.objectsManager = new ObjectsManager();

        Logger logger = LogManager.getLogger(this.getClass());
        logger.debug("Construct a MyJavaPanel");
        String path = GraphicsUtilities.assetsPaths.get("ubuntu");
        if (logger.isDebugEnabled()) {
            String message = MessageFormat.format("Loading image at path {0}", path);
            logger.debug(message);
        }
        try {
//            setDelete();
            this.addKeyListener(this);
        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", path);
            logger.error(message, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!delete) {
            g.drawImage(objectsManager.getGraphicalObjects("ubuntu"), entity.getX(), entity.getY(), null);
        } else {
            g.drawImage(objectsManager.getGraphicalObjects("delete"), entity.getX(), entity.getX(), null);
        }

    }

    public void setDelete() {
        this.delete = true;
    }


    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key typed");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key typed");
        }

    }

    @Override
    public void setFocusable(boolean b) {
        super.setFocusable(b);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            entity.move(PhysicsUtilities.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            entity.move(PhysicsUtilities.LEFT);

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            entity.move(PhysicsUtilities.DOWN);

        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            entity.move(PhysicsUtilities.UP);
        }

        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key Released");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key Released");
        }

    }


}
