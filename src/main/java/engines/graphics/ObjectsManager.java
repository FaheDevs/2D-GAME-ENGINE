package engines.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ObjectsManager {


    /**
     *
     * contains a map to that sets the graphical objects name and image
     *
     * graphical objects are in the hash map
     *
     *
     *
     * */

    public HashMap<String, BufferedImage> graphicalObjectsMap;


    public ObjectsManager() throws IOException {
        GraphicsUtilities.setAssetsPaths();
        GraphicalObject graphicalObject = GraphicsUtilities.createGraphicalObject("ubuntu", GraphicsUtilities.assetsPaths.get("ubuntu"));
//        GraphicalObject graphicalObjectDeleted = GraphicsUtilities.createGraphicalObject("delete",GraphicsUtilities.assetsPaths.get("no"));

        setObjectImage(graphicalObject.getName(), graphicalObject.getImage());
//        setObjectImage(graphicalObjectDeleted.getName(), graphicalObjectDeleted.getImage());
    }

    public static void main(String[] args) throws IOException {
        ObjectsManager objectsManager = new ObjectsManager();
        System.out.println();
    }

    public void setObjectImage(String name, BufferedImage image) throws IOException {
        graphicalObjectsMap = new HashMap<>();
        graphicalObjectsMap.put(name, image);
    }

    public BufferedImage getGraphicalObjects(String name) {
        return graphicalObjectsMap.get(name);
    }
}
