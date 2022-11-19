package engines.graphics;

import javax.imageio.ImageIO;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GraphicsUtilities {
    public static final int ORIGINAL_TILE_SIZE = 32; // 64 x 64 TILES
    public static final int SCALE = 1;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCENE_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCENE_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    ObjectsManager objectsManager = new ObjectsManager(30);

    /**
     * utility class for the graphics engine
     * initialize the paths to the pictures into a hashMap
     * helps access to path of image based on image name .
     * helps with creation of a graphical object
     * helps with uploading and scaling the image
     */


    public void initializeGraphicalObjects() throws IOException {
        BufferedImage image ;
        image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/00.png"));
        objectsManager.addGraphicalObject(new GraphicalObject(image, "spacecraft"));
        image = ImageIO.read(new File("src/main/resources/assets/images/enemigo1.png"));
        for (int i = 0; i < 10; i++) {
            objectsManager.addGraphicalObject(new GraphicalObject(image, "monster"));
        }


    }

    public void paint(Graphics g2) {
        for (GraphicalObject gp : objectsManager.graphicalObjectsArray) {
            if (gp != null) {
                g2.drawImage(gp.image, gp.position.x, gp.position.y, null);
            }

        }

    }

    public static void main(String[] args) throws IOException {


    }
}


