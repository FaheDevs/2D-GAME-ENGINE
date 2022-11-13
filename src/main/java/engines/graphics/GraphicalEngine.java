package engines.graphics;



import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine extends JPanel  {
    ObjectsManager objectsManager;

    public Point whereToDraw = new Point();


    public GraphicalEngine() throws IOException {
        objectsManager = new ObjectsManager("pacman");
        setPreferredSize(new Dimension(GraphicsUtilities.SCENE_WIDTH, GraphicsUtilities.SCENE_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
    }


    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(objectsManager.getGraphicalObjects("pacman"),whereToDraw.x,whereToDraw.y,null);
        g2.dispose();
    }

}
