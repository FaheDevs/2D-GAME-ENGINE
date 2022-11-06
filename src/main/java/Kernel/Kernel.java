package Kernel;

import engines.graphics.GraphicalEngine;
import engines.physics.Entities.Player;

import java.awt.*;
import java.io.IOException;

public class Kernel implements Runnable {

    int FPS = 60;


    GraphicalEngine graphicalEngine;

    // the game scene are set depending on the game state
    enum GameState {
        PLAY, PAUSE, STOP, GAMEOVER, VICTORY
    }

    Thread gameThread;

    Player player;


    public Kernel() throws IOException {
        graphicalEngine = new GraphicalEngine();
        player = new Player(100, 100);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;
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
                graphicalEngine.repaint();
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
        /*
        int newX = playerX;
        int newY = playerY;
        if (keyHandler.UpPressed) newY -= speed;
        if (keyHandler.downPressed) newY += speed;
        if (keyHandler.rightPressed) newX += speed;
        if (keyHandler.leftPressed) newX -= speed;

        playerX = newX;
        playerY = newY;


         */
    }

    public void paintComponent(Graphics g) {


    }
}
