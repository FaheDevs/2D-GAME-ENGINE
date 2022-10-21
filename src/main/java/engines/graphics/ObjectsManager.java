package engines.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

public class ObjectsManager {

    public GraphicalObject[] graphicalObjects;

    private static final int ARRAY_SIZE = 10;


    public ObjectsManager() {
          graphicalObjects = new GraphicalObject[ARRAY_SIZE] ;
//          setObjectEImage();
    }


    public BufferedImage upload(String path ) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(new File(path));
        return image;
    }

    public void setObjectEImage(int i,String path ) throws IOException {
        graphicalObjects[i] = new GraphicalObject(upload(path));
    }

    public BufferedImage getGraphicalObjects(int i) {
        return graphicalObjects[i].image;
    }
}
