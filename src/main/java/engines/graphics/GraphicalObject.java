package engines.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GraphicalObject {

    /**
     * a graphical object represented by his name ( description of the image )
     * the image representing the object
     */
    public BufferedImage image;
    public String name;

    public GraphicalObject(BufferedImage image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void paint(Graphics g){

    }


}
