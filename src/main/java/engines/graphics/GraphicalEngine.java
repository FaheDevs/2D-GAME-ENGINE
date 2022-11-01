package engines.graphics;

import engines.physics.PhysicalEntity;
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
public class GraphicalEngine implements KeyListener {
    @Serial
    private static final long serialVersionUID = 4242L;
    public Boolean delete = false;
    ObjectsManager objectsManager;
    PhysicalEntity physicalEntity;


    /**
     * Constructs a new panel that draw an image.
     *
     * this panel uses the objectsmanager to extract the image that we want to draw into the panel
     *
     * relies the image into the physicalEntity
     *
     * moves the image
     */


     public JPanel tester = new JPanel(true) {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!delete) {
                g.drawImage(objectsManager.getGraphicalObjects("ubuntu"), physicalEntity.getX(), physicalEntity.getY(), null);
            } else {
                g.drawImage(objectsManager.getGraphicalObjects("delete"), physicalEntity.getX(), physicalEntity.getX(), null);
            }
        }

        @Override
        public void setFocusable(boolean b) {
            super.setFocusable(b);
        }
    };


    public GraphicalEngine() throws IOException {

        physicalEntity = new PhysicalEntity(400, 100);
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
            tester.addKeyListener(this);
        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", path);
            logger.error(message, ex);
        }
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (!delete) {
//            g.drawImage(objectsManager.getGraphicalObjects("ubuntu"), physicalEntity.getX(), physicalEntity.getY(), null);
//        } else {
//            g.drawImage(objectsManager.getGraphicalObjects("delete"), physicalEntity.getX(), physicalEntity.getX(), null);
//        }
//
//    }

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

//    @Override
//    public void setFocusable(boolean b) {
//        super.setFocusable(b);
//    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
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

        tester.repaint();

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
