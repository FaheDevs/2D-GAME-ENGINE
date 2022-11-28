package api;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class SwingRenderer {
    private static SwingRenderer instance;

    private SwingRenderer() {}

    public static SwingRenderer getInstance() {
        if (instance == null) instance = new SwingRenderer();
        return instance;
    }


    public void renderRect(int height, int width, int x, int y, Color color) {
        Graphics2D graphics2D = getCurrentGraphics();
        if (graphics2D != null) {
            graphics2D.setColor(color);
            x += getCurrentScene().xLocation;
            y += getCurrentScene().yLocation;
            graphics2D.fillRect(x, y, width, height);
        }
    }


    public void renderText(String text, Color color, int fontSize, boolean center, int x, int y, int height, int width) {
        Graphics2D graphics2D = getCurrentGraphics();
        if (graphics2D != null) {
            graphics2D.setFont(new Font("Arial", Font.PLAIN, fontSize));
            if (center) {
                FontMetrics metrics = graphics2D.getFontMetrics();
                x = x + (width - metrics.stringWidth(text)) / 2;
                y = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
            }
            x += getCurrentScene().xLocation;
            y += getCurrentScene().yLocation;
            graphics2D.setColor(color);
            graphics2D.drawString(text, x, y);
        }
    }

    public void renderImage(BufferedImage image,int x , int y ){
        Graphics2D graphics2D = getCurrentGraphics();
        if (graphics2D != null) {
            x += getCurrentScene().xLocation;
            y += getCurrentScene().yLocation;
            graphics2D.drawImage(image,x,y,null);
        }
    }



    private BufferedImage getBufferedImage(String link) {
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(SwingRenderer.class.getResourceAsStream("/" + link));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return texture;
    }
    public Graphics2D getCurrentGraphics() {
        if (SwingWindow.getInstance().getCurrentScene() != null)
            return SwingWindow.getInstance().getCurrentScene().get2DGraphics();
        return null;
    }

    public SwingScene getCurrentScene() {
        return SwingWindow.getInstance().getCurrentScene();
    }
}
