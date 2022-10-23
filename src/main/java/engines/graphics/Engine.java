package engines.graphics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.text.MessageFormat;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class Engine extends JPanel implements KeyListener {
    @Serial
    private static final long serialVersionUID = 4242L;
    private transient BufferedImage image;
    Rectangle rectangle = new Rectangle(400,100,100,100);


    /**
     * Constructs a new panel that draw an image.
     */

    public Engine() {
        Logger logger = LogManager.getLogger(this.getClass());
        logger.debug("Construct a MyJavaPanel");
        String path = "src/main/resources/sample/image.png";
        if (logger.isDebugEnabled()) {
            String message = MessageFormat.format("Loading image at path {0}", path);
            logger.debug(message);
        }
        try {
            setImage(0,path);
            this.addKeyListener(this);
        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", path);
            logger.error(message, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       // g.drawImage(image, 50, 50, null);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width,rectangle.height);



    }


    public void setImage(int i , String path ) throws IOException {
        ObjectsManager objectsManager = new ObjectsManager();
        objectsManager.setObjectEImage(i,path);
        image = objectsManager.getGraphicalObjects(i);
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
            rectangle.x = rectangle.x + 10 ;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            rectangle.x = rectangle.x - 10 ;

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            rectangle.y = rectangle.y + 10 ;

        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            rectangle.y = rectangle.y - 10 ;

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
