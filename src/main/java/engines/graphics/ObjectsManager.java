package engines.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ObjectsManager {


    /**
     * contains a map to that sets the graphical objects name and image
     * graphical objects are in the hash map
     * */

    public HashMap<String, BufferedImage> graphicalObjectsMap;


    public ObjectsManager() throws IOException {
        GraphicsUtilities.setAssetsPaths();
        GraphicalObject graphicalObject = GraphicsUtilities.createGraphicalObject("pacman", GraphicsUtilities.assetsPaths.get("pacman"));
        setObjectImage(graphicalObject.getName(), graphicalObject.getImage());
    }


    public void setObjectImage(String name, BufferedImage image) {
        graphicalObjectsMap = new HashMap<>();
        graphicalObjectsMap.put(name, image);
    }

    public BufferedImage getGraphicalObjects(String name) {
        return graphicalObjectsMap.get(name);
    }
}
