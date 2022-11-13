package Kernel;

import engines.graphics.Command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.physics.Entities.Player;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Kernel implements Runnable {

    int FPS = 60;


    // the game scene are set depending on the game state
    enum GameState {
        PLAY, PAUSE, STOP, GAMEOVER, VICTORY
    }

    // LAMBDA EXPRESSION A LA PLACE DE EXTENDS THREAD

    Thread gameThread;

    public GraphicalEngine graphicalEngine;

    Player player;
    KeyHandler keyHandler;


    JFrame jFrame ;

    public Kernel() throws IOException {
        keyHandler = new KeyHandler();
        graphicalEngine = new GraphicalEngine();
        player = new Player(100, 100);
        gameThread = new Thread(this);
        jFrame = new JFrame("2D GAME");
        jFrame.add(graphicalEngine);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    /**
     * BackLog → changer le nom sans final → ce qu'on a changé pas assez d'US
     * il faut etre general id6 à refaire
     * i7 c'est le concepteur qui souhaite
     * comment les elements sonores fonctionnent s'arrête, démarre
     * Moteur IA
     * rendre les sprints backlog precedent
     * taches insatisfaisantes
     * les états dans le noyau
     * gérer, remplacer par détecter,
     * mettre une page de garde
     * package pas de majuscule
     * plus l'etoile dans l'import
     */

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
        if(keyHandler.downPressed){
            System.out.println("here");
            graphicalEngine.whereToDraw.x--;
        }
        else {
            graphicalEngine.whereToDraw.x++;
        }


    }

    public static void main(String[] args) throws IOException {
        Kernel kernel = new Kernel();
        kernel.startGameThread();
    }




}
