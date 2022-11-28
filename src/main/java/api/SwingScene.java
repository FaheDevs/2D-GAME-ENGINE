package api;

import javax.swing.*;
import java.awt.*;

public class SwingScene extends JPanel {
    protected int height;

    protected int width;

    protected int xLocation;

    protected int yLocation;
    protected Graphics2D graphics;

    protected Color backgroundColor;
    protected SwingScene(int height, int width) {
        this.height = height;
        this.width = width;
        this.xLocation = 0;
        this.yLocation = 0;
        this.backgroundColor = Color.black;
        setBackground(backgroundColor);
    }
    public void setLocation(int x, int y) {
        xLocation = x;
        yLocation = y;
    }
    public void setSize(int height, int width) {
        this.height = height;
        this.width = width;
    }
    public void setBackgroundColor(int red, int green, int blue) {
        backgroundColor = new Color(red, green, blue);
        SwingWindow.getInstance().setBackgroundColor(backgroundColor);
        setBackground(backgroundColor);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics = (Graphics2D) g;
    }


    public Graphics2D get2DGraphics() { return graphics; }

    @Override
    public int getHeight() { return height; }

    @Override
    public int getWidth() { return width; }

}
