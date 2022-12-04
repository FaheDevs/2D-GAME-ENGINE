package engines.graphics;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicalObject {

    /**
     * a graphical object represented by his name ( description of the image )
     * the image representing the object
     */
    public BufferedImage image ;
    public String name ;
    public Point position ;

    public Color color;
    public Rectangle coloredRectangle;
    public JButton clickable;
    public String text;

    public Scene scene ;

    //GraphicalObject rectRouge = new GraphicalObject(new Reactangle(4,4) , COLOR.RED)


    public void paintRectangle(int x, int y, int h, int w, Color color){
        coloredRectangle = new Rectangle(x, y, h, w);
        this.color = color;
    }
    public void rePaintRectangle(Color color){
        this.color = color;
    }

    public GraphicalObject(String name, Point position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return "GraphicalObject{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public GraphicalObject(Point position) {
        this.position = position;
    }
    public GraphicalObject(BufferedImage image, String name) {
        this.image = image;
        this.name = name;
        this.position = new Point();
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, position.x, position.y, null);
    }


    public void setScene(Scene scene) {
        this.scene = scene;
    }


    public Rectangle getColoredRectangle() {
        return coloredRectangle;
    }
}
