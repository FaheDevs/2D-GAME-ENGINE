package kernel;

import engines.graphics.GraphicsUtilities;
import engines.graphics.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.physics.Direction;
import engines.physics.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Kernel implements Runnable {


    // the game scene are set depending on the game state
    Thread gameThread;
    public GraphicalEngine graphicalEngine;
    Player player ;
    KeyHandler keyHandler;
    JFrame jFrame;
    Point position = new Point(600,600);

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
        jFrame.setFocusable(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.addKeyListener(keyHandler);
        graphicalEngine.setBackground(Color.black);
        initializePositions();
    }

    public void initializePositions(){
        position = new Point(600,600);
        var gp =  graphicalEngine.getTiles();
        gp[0].position = position;
        player = new Player(position);
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
        /**  @whereToDraw is a point who tell the GraphicalEngine where to draw
         *   TODO : add a BufferedImage to the Graphical Engine and set it eachTime we wanna draw
         */
            player.getPosition().move(position.x , position.y - 4);
    }


    public static void main(String[] args) throws IOException {
        Kernel kernel = new Kernel();
        kernel.startGameThread();
    }


}