package engines.graphics;
import kernel.EventListener;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine extends JPanel implements GraphicEvent  {


    private final ArrayList<EventListener> eventsListeners = new ArrayList<>();

    GraphicsUtilities graphicsUtilities = new GraphicsUtilities();
    public GraphicalEngine() throws IOException {
        graphicsUtilities.initializeGraphicalObjects();
        setPreferredSize(new Dimension(GraphicsUtilities.SCENE_WIDTH, GraphicsUtilities.SCENE_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
    }

    public GraphicalObject[] getTiles(){
        return graphicsUtilities.objectsManager.graphicalObjectsArray;
    }


    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        graphicsUtilities.paint(g);
        g2.dispose();
    }


    @Override
    public void notifyEvent(String event) {

    }

    @Override
    public void notifyEntityUpdate(GraphicalEntity entity) {
        for (EventListener listener : eventsListeners)
            listener.onEntityUpdate(entity);

    }

    @Override
    public void subscribeEvents(EventListener listener) {
        eventsListeners.add(listener);
    }
}
