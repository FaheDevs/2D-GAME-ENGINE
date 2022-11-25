package engines.graphics;

import engines.kernel.Entity;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine  {



    GraphicsUtilities graphicsUtilities = new GraphicsUtilities();
    public GraphicalEngine() throws IOException {
//        graphicsUtilitiesk.initializeGraphicalObjects();
//        setPreferredSize(new Dimension(GraphicsUtilities.SCENE_WIDTH, GraphicsUtilities.SCENE_HEIGHT));
//        setDoubleBuffered(true);
//        setFocusable(true);
    }


    public GraphicalObject[] getTiles(){
        return graphicsUtilities.objectsManager.graphicalObjectsArray;
    }


    public void paint(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        graphicsUtilities.paint(g);
//        g2.dispose();
    }


    public void movable(Entity entity , int x , int y  ){
        entity.setGraphicalPositions(x, y);
    }

}
