package kernel;


import engines.AI.AIEntity;
import engines.graphics.GraphicsUtilities;
import engines.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.physics.entities.Collision;
import engines.physics.entities.Player;

import javax.swing.*;

import java.awt.Point;
import java.awt.Color;
import java.io.IOException;


public class Kernel implements Runnable {


    // the game scene are set depending on the game state
    Thread gameThread;
    public GraphicalEngine graphicalEngine;
    Player player;
    KeyHandler keyHandler;
    JFrame jFrame;

    static int nbOfTiles = GraphicsUtilities.nbOfTiles;
    AIEntity aiEntity;
    Point[] positions = new Point[nbOfTiles];
    static Direction directionAIEntity = Direction.LEFT;
    //
    static boolean leftFlag = false;
    static boolean rigtFlag = false;
    static boolean downFlag = false;
    //
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
        System.out.println(jFrame.getHeight());
        System.out.println(jFrame.getWidth());
    }

    public void initializePositions()  {
        initializePlayerPosition();
        initializeAIEtityPositions();
    }

    public void initializePlayerPosition()  {
        positions[0] = new Point(GraphicsUtilities.SCENE_WIDTH / 2, GraphicsUtilities.SCENE_HEIGHT - 50);
        var gp = graphicalEngine.getTiles();
        gp[0].position = positions[0];
        player = new Player(positions[0]);
       /*
        positions[1] = new Point(player.x, player.y);
        gp[1].position = positions[1];
        GraphicalObject shootGp = new GraphicalObject(ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/20.png")),"shoot",new Point(positions[0]));
        GraphicsUtilities.objectsManager.addGraphicalObject(shootGp);
        shoot = new Shoot(shootGp.position);

        */
    }

    public void initializeAIEtityPositions() {
            positions[1] = new Point(445, 200);
            aiEntity = new AIEntity(positions[1]);
            var graphicalObjects = graphicalEngine.getTiles();
            graphicalObjects[1].position = positions[1];
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
                updateIAEntity();
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

    public boolean checkCollisiont(int newX, int newY){
        boolean collision = false;
        Point newPosition = new Point(newX, newY);
        for (int i = 1; i < positions.length; i++) {
            System.out.println("la");
            if(!Collision.checkCollisionObject(newPosition, positions[i])){
                System.out.println("de");
                collision = true;
            }
        }


        return (collision && Collision.checkCollisionWorld(newX, newY));
    }
    public boolean checkCollisiontObjects(int newX, int newY){
        return !(Collision.checkCollisionWorld(newX, newY));
    }

    public void update() {
        if (keyHandler.downPressed) {
            if(checkCollisiont(positions[0].x , positions[0].y + player.speed)) {
                positions[0].move(positions[0].x, positions[0].y + player.speed);
            }
        }
        if (keyHandler.UpPressed) {
            if(checkCollisiont(positions[0].x, positions[0].y - player.speed)) {
                positions[0].move(positions[0].x, positions[0].y - player.speed);
            }
        }
        if (keyHandler.rightPressed) {
            if(checkCollisiont(positions[0].x + player.speed, positions[0].y)){
                positions[0].move(positions[0].x + player.speed, positions[0].y);
            }

        }
        if (keyHandler.leftPressed) {
            if(checkCollisiont(positions[0].x - player.speed, positions[0].y)){
                positions[0].move(positions[0].x - player.speed, positions[0].y);
            }
        }


    }

    public  void updateIAEntity() {
        if(Collision.checkCollisionWorld(positions[1].x - aiEntity.speed, positions[1].y) && !leftFlag){
            //aiEntity.move(directionAIEntity);
            directionAIEntity = Direction.LEFT;
        }else if(!leftFlag) {
            leftFlag = true;

        }
        if(Collision.checkCollisionWorld(positions[1].x + aiEntity.speed, positions[1].y) && downFlag){
            directionAIEntity = Direction.RIGHT;
            //aiEntity.move(directionAIEntity);

        }else if(!rigtFlag) {
            rigtFlag = true;
            directionAIEntity = Direction.LEFT;
            leftFlag = false;
            downFlag = false;
        }
        if(leftFlag && !downFlag) {
            directionAIEntity = Direction.DOWN;
            downFlag = true;
            rigtFlag = false;
        }
        aiEntity.move(directionAIEntity);
    }

   /* public void shoot(){
        GraphicalObject shootgp = graphicalEngine.getTiles()[1];
        //Shoot shoot = new Shoot()
        shoot.goUp = true;
        shoot.setY(positions[0].y);
        shoot.setX(positions[0].x);
        positions[1].move(positions[0].x, positions[0].y);
    }

    */




    public static void main(String[] args) throws IOException {
        Kernel kernel = new Kernel();
        kernel.startGameThread();
    }


}
