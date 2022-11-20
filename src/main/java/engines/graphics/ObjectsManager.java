package engines.graphics;


import java.util.Arrays;

public class ObjectsManager {
    /**
     * contains a map to that sets the graphical objects name and image
     * graphical objects are in the hash map
     */

    public GraphicalObject[] graphicalObjectsArray;
    public int index = 0;

    public int size;

    public ObjectsManager(int size) {
        this.graphicalObjectsArray = new GraphicalObject[size];
        this.size = size;
    }

    public void addGraphicalObject(GraphicalObject graphicalObject) {
        graphicalObjectsArray[index++] = graphicalObject;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex(GraphicalObject gp) {
        for (int i = 0; i < graphicalObjectsArray.length; i++) {
            if (graphicalObjectsArray[i] == gp) {
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