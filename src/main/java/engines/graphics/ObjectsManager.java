package engines.graphics;


import java.util.Arrays;

public class ObjectsManager {
    /**
     * contains a map to that sets the graphical objects name and image
     * graphical objects are in the hash map
     */

    public GraphicalObject[] graphicalObjectsArray = new GraphicalObject[10];
    public static int index = 0;



    public ObjectsManager() {
    }

    public void addGraphicalObject(GraphicalObject graphicalObject) {
        graphicalObjectsArray[index] = graphicalObject;
    }

    public int getIndex(GraphicalObject gp){
        for (int i = 0 ; i < graphicalObjectsArray.length ; i++) {
            if(graphicalObjectsArray[i] == gp){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return Arrays.toString(graphicalObjectsArray);
    }
}