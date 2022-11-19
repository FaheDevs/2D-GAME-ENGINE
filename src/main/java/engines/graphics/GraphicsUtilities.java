package engines.graphics;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsUtilities {
    public static final int SCENE_WIDTH = 500;
    public static final int SCENE_HEIGHT = 500;

    public static int nbOfTiles = 30;

    ObjectsManager objectsManager = new ObjectsManager(nbOfTiles);


    /**
     * utility class for the graphics engine
     * initialize the paths to the pictures into a hashMap
     * helps access to path of image based on image name .
     * helps with creation of a graphical object
     * helps with uploading and scaling the image
     */


    public void initializeGraphicalObjects() throws IOException {
        BufferedImage image;
        image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/00.png"));
        objectsManager.addGraphicalObject(new GraphicalObject(image, "spacecraft"));
        image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/10.png"));
        objectsManager.addGraphicalObject(new GraphicalObject(image, "shoot"));

       /*
       image = ImageIO.read(new File("src/main/resources/assets/images/enemigo1.png"));
        for (int i = 0; i < 20; i++) {
            objectsManager.addGraphicalObject(new GraphicalObject(image, "monster"));
        }

        */


    }

    public void paint(Graphics g2) {
        for (GraphicalObject gp : objectsManager.graphicalObjectsArray) {
            if (gp != null) {
                gp.paint(g2);
            }
        }

    }

}


