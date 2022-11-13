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
    public static final int ORIGINAL_TILE_SIZE = 64; // 64 x 64 TILES
    public static final int SCALE = 1;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COL = 10;
    public static final int MAX_SCREEN_ROW = 10;
    public static final int SCENE_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCENE_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    public Point whereToDraw = new Point();


    /**
     * Constructs a new panel that draw an image.
     * <p>
     * this panel uses the objects manager to extract the image that we want to draw into the panel
     * <p>
     * relies on the image into the physicalEntity
     * <p>
     * moves the image
     */



    public GraphicalEngine() throws IOException {
        objectsManager = new ObjectsManager("pacman");
        setPreferredSize(new Dimension(SCENE_WIDTH, SCENE_HEIGHT));
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
