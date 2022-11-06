package engines.graphics;

import Kernel.Kernel;
import engines.graphics.Command.KeyHandler;
import engines.physics.Direction;
import engines.physics.Entities.Player;
import sample.MyJavaFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine extends JPanel implements Runnable {
    ObjectsManager objectsManager;
    public static final int ORIGINAL_TILE_SIZE = 64; // 64 x 64 TILES
    public static final int SCALE = 1;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COL = 10;
    public static final int MAX_SCREEN_ROW = 10;
    public static final int SCENE_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCENE_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    Thread gameThread;

    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();


    /**
     * Constructs a new panel that draw an image.
     * <p>
     * this panel uses the objects manager to extract the image that we want to draw into the panel
     * <p>
     * relies on the image into the physicalEntity
     * <p>
     * moves the image
     */

    // SET PLAYER POSITION



    int playerX = 100;
    int playerY = 100;
    int speed = 4;



    public GraphicalEngine() throws IOException {

        objectsManager = new ObjectsManager("pacman");
        setPreferredSize(new Dimension(SCENE_WIDTH, SCENE_HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }

    public void update() {
        int newX = playerX;
        int newY = playerY;
        if (keyHandler.UpPressed) newY -= speed;
        if (keyHandler.downPressed) newY += speed;
        if (keyHandler.rightPressed) newX += speed;
        if (keyHandler.leftPressed) newX -= speed;

        playerX = newX;
        playerY = newY;

    }

    public void paintComponent(Graphics g) {
        paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);
        g2.dispose();
    }

}
