package kernel;


import engines.AI.AIEntity;
import engines.graphics.GraphicsUtilities;
import engines.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.physics.Collision;
import engines.physics.PhysicalEngine;
import engines.physics.PhysicalEntity;
import engines.physics.Player;

import javax.swing.*;

import java.awt.Point;
import java.awt.Color;
import java.io.IOException;


public class Kernel implements EventListener {


    // the game scene are set depending on the game state
    Thread gameThread;
    public GraphicalEngine graphicalEngine;

    public PhysicalEngine physicalEngine;
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
//        gameThread = new Thread(this);
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
    }

    public void initializeAIEtityPositions() {
            positions[1] = new Point(445, 200);
            aiEntity = new AIEntity(positions[1]);
            var graphicalObjects = graphicalEngine.getTiles();
            graphicalObjects[1].position = positions[1];
    }

//    public void startGameThread() {
//        gameThread = new Thread(this);
//        gameThread.start();
//    }


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
            if(!Collision.checkCollisionObject(newPosition, positions[i])){
                collision = true;
            }
        }
        return (collision && Collision.checkCollisionWorld(newX, newY));
    }
    public boolean checkCollisiontObjects(int newX, int newY){
        Point newPosition = new Point(newX, newY);
        for (int i = 0; i < positions.length; i++) {
            if(i != 1 && Collision.checkCollisionObject(newPosition, positions[i])){
                return false;
            }
        }
        return true;
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
        if(checkCollisiontObjects(positions[1].x - aiEntity.speed, positions[1].y)) {
            if (Collision.checkCollisionWorld(positions[1].x - aiEntity.speed, positions[1].y) && !leftFlag) {
                //aiEntity.move(directionAIEntity);
                directionAIEntity = Direction.LEFT;
            } else if (!leftFlag) {
                leftFlag = true;

            }
            if (Collision.checkCollisionWorld(positions[1].x + aiEntity.speed, positions[1].y) && downFlag) {
                directionAIEntity = Direction.RIGHT;
                //aiEntity.move(directionAIEntity);

            } else if (!rigtFlag) {
                rigtFlag = true;
                directionAIEntity = Direction.LEFT;
                leftFlag = false;
                downFlag = false;
            }
            if (leftFlag && !downFlag) {
                directionAIEntity = Direction.DOWN;
                downFlag = true;
                rigtFlag = false;
            }
            aiEntity.move(directionAIEntity);
        }
        else System.out.println("OBJECT DETECTED");
    }


    public static void main(String[] args) throws IOException {
        Kernel kernel = new Kernel();
//        kernel.startGameThread();
    }


    @Override
    public void onEvent(String event) {

    }

    @Override
    public void onEntityEvent(Entity entity, String eventName) {

    }

    @Override
    public void onEntityUpdate(EngineEntity entity) {

        if (entity instanceof PhysicalEntity){
            PhysicalEntity physicalEntity = (PhysicalEntity) entity;

        }


    }

    public void initListeners(){
        graphicalEngine.subscribeEvents(this);
        physicalEngine.subscribeEvents(this);

    }
}
