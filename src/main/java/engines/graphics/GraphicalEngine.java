package engines.graphics;

import engines.kernel.Entity;

import javax.swing.*;
import java.awt.*;

import java.io.IOException;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine   {
    JPanel jPanel = new JPanel();
    public GraphicalEngine() {
   }




    public void paint(Graphics g) {
            jPanel.paint(g);
//        Graphics2D g2 = (Graphics2D) g;
//        graphicsUtilities.paint(g);
//        g2.dispose();
    }


    public void movable(Entity entity , int x , int y  ){
        entity.setGraphicalPositions(x, y);
    }



}
