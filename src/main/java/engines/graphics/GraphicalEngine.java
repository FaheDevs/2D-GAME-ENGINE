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
    GraphicalObject graphicalObject;

    GraphicalObject graphicalObject2 ;
    public Point whereToDraw = new Point();


    public GraphicalEngine() throws IOException {
//        objectsManager = new ObjectsManager("pacman");
        graphicalObject = new GraphicalObject(GraphicsUtilities.upload("src/main/resources/pacman/pac man & life counter & death/pac man/pac_man_0.png"),"pacman",whereToDraw);
        graphicalObject2 = new GraphicalObject(GraphicsUtilities.upload("src/main/resources/pacman/pac man & life counter & death/pac man/pac_man_2.png"),"pacman2",new Point(100,60));

        setPreferredSize(new Dimension(GraphicsUtilities.SCENE_WIDTH, GraphicsUtilities.SCENE_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
    }


    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        graphicalObject2.paint(g);
        graphicalObject.paint(g);
        //        g2.drawImage(objectsManager.getGraphicalObjects("pacman"),whereToDraw.x,whereToDraw.y,null);

        g2.dispose();
    }

}
