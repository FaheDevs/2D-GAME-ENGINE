package kernel;

import engines.graphics.GraphicsUtilities;
import engines.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.physics.entities.Player;
import engines.physics.entities.Shoot;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Kernel implements Runnable {


    // the game scene are set depending on the game state
    Thread gameThread;
    public GraphicalEngine graphicalEngine;
    Player player;
    KeyHandler keyHandler;
    JFrame jFrame;

    static int nbOfTiles = GraphicsUtilities.nbOfTiles;

    Point[] positions = new Point[nbOfTiles];


    Shoot shoot;

    static int FPS = 60;

    public Kernel() throws IOException {
        keyHandler = new KeyHandler();
        graphicalEngine = new GraphicalEngine();
        gameThread = new Thread(this);
        jFrame = new JFrame("GAME ENGINE");
        jFrame.add(graphicalEngine);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setFocusable(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.addKeyListener(keyHandler);
        graphicalEngine.setBackground(Color.black);
        initializePositions();
    }

    public void initializePositions() {
        initializePlayerPosition();
        initializeMonsterPositions();
    }

    public void initializePlayerPosition() {
        positions[0] = new Point(GraphicsUtilities.SCENE_WIDTH / 2, GraphicsUtilities.SCENE_HEIGHT - 50);
        var gp = graphicalEngine.getTiles();
        gp[0].position = positions[0];
        player = new Player(positions[0]);
        positions[1] = new Point(player.x, player.y);
        gp[1].position = positions[1];
        shoot = new Shoot(positions[1]);
    }

    public void initializeMonsterPositions() {
        /*int x = 50;
        for (int i = 1; i < 11; i++) {
            positions[i] = new Point(x, 200);
            physicalEntities[i] = new NPC(positions[i]);
            graphicalObjects[i] = graphicalEngine.getTiles()[i];
            graphicalObjects[i].position = positions[i];
            x = x + 50;
        }
        x = 50;
        for (int i = 11; i < 21 ; i++) {
            positions[i] = new Point(x, 250);
            physicalEntities[i] = new NPC(positions[i]);
            graphicalObjects[i] = graphicalEngine.getTiles()[i];
            graphicalObjects[i].position = positions[i];
            x = x + 50;
        }
         */
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
                shoot.move(0);
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
        if (keyHandler.downPressed) {
            positions[0].move(positions[0].x, positions[0].y + player.speed);
        }
        if (keyHandler.UpPressed) {
            positions[0].move(positions[0].x, positions[0].y - player.speed);
        }
        if (keyHandler.rightPressed) {
            positions[0].move(positions[0].x + player.speed, positions[0].y);

        }
        if (keyHandler.leftPressed) {
            positions[0].move(positions[0].x - player.speed, positions[0].y);
        }
        if (keyHandler.spacePressed) {
            shoot.setY(positions[0].y);
            shoot.setX(positions[0].x);
            positions[1].move(positions[0].x, positions[0].y);
            shoot.goUp = true;
        }

    }

    public void moveMonsters(){
        for (int i = 1; i < 21; i++) {
            positions[i].move(positions[i].x - 5 , positions[i].y);
        }
    }





    public static void main(String[] args) throws IOException {
        Kernel kernel = new Kernel();
        kernel.startGameThread();
    }


}
