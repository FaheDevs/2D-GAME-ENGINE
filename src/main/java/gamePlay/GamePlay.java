package gamePlay;

import engines.graphics.GraphicalEngine;
import engines.graphics.Scene;
import engines.kernel.Entity;
import engines.kernel.Kernel;
import engines.kernel.Subject;
import engines.physics.PhysicalEngine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class GamePlay implements Runnable {

    Kernel kernel;

    Thread gameThread;

    Entity player;

    ArrayList<Bullet> shoots;

    ArrayList<Entity> entitiesGame;

    Scene menu;


    public enum MoveDirection {
        RIGHT, LEFT, UP
    }

    public GamePlay() throws IOException {
        entitiesGame = new ArrayList<>();

        kernel = new Kernel();

        shoots = new ArrayList<>();

        initEntities();

        menu = kernel.graphicalEngine.generateScene(600, 800);

        // je bind la scene au Jframe
        kernel.graphicalEngine.bindScene(menu);
        // je rajoute un objet a la scene

        for (Entity entity : entitiesGame) {

            kernel.graphicalEngine.addToScene(menu, entity);

        }


        // j'affiche la scene

        kernel.commandEngine.enableKeyboardIO();
        kernel.start();

    }

    public void initEntities() throws IOException {


        //-------------------------------Player initialization-------------------------------------------------//

        player = new Player();
        initEntity(player);

        player.setPositions(400, 400);

        //----------------------------------------------------------------------------------------------------------//

        //-------------------------------Monster initialization-----------------------------------------------------//

        Monster monster = new Monster();

        initEntity(monster);

        monster.setPositions(400, 300);


        //----------------------------------------------------------------------------------------------------------//



    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void initEntity(Entity entity) {
        entity.register(kernel);
        kernel.setSubject(entity);
        entitiesGame.add(entity);

    }

    public void move(Entity entity, MoveDirection direction) {
        int newX;
        if (direction == MoveDirection.LEFT) {
            newX = entity.physicalObject.x - 2;
            entity.setPositions(newX, entity.physicalObject.y);
        }
        if (direction == MoveDirection.RIGHT) {
            newX = entity.physicalObject.x + 2;
            entity.setPositions(newX, entity.physicalObject.y);
        }


    }

    public void shoot(Entity entity) {
        Bullet bullet =  generateBullet(entity.x, entity.y);
        shoots.add(bullet);
        kernel.graphicalEngine.addToScene(menu,bullet);
    }


    public Bullet generateBullet(int x, int y) {
        Bullet bullet = new Bullet();
        bullet.setPositions(x, y-5);
        initEntity(bullet);
        return bullet;
    }

    public void KillBullet(Bullet bullet){
        shoots.remove(bullet);
        entitiesGame.remove(bullet);
        kernel.entities.remove(bullet);
    }


    public Kernel getKernel() {
        return kernel;
    }

    public ArrayList<Subject> getEntities() {
        return kernel.entities;
    }

    public GraphicalEngine graphicalEngine() {
        return kernel.graphicalEngine;
    }

    public PhysicalEngine physicalEngine() {
        return kernel.physicalEngine;
    }


    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / 60;
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
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                kernel.updateEntities();
                kernel.graphicalEngine.refreshWindow();
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

    public void  update() throws IOException {
        if (kernel.commandEngine.keyHandler.leftPressed) {
            move(player, MoveDirection.LEFT);
        }

        if (kernel.commandEngine.keyHandler.rightPressed) {
            move(player, MoveDirection.RIGHT);
        }

        if (kernel.commandEngine.keyHandler.STyped) {
            shoot(player);
        }
        for (int i = 0; i < shoots.size(); i++) {
            Bullet bullet = shoots.get(i);
            if(bullet.y < -5){
                shoots.remove(i);
            }else {
                bullet.tick();
            }

        }



        }





    public static void main(String[] args) throws IOException {

        GamePlay game = new GamePlay();
        game.startGameThread();


//        Kernel kernel1 = new Kernel();
//        BufferedImage image;
//        image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/00.png"));
//        GraphicalObject graphicalObject = new GraphicalObject(image,"spaceCraft ");
//        Entity palyer3 = new Entity(graphicalObject);
//        palyer3.setPositions(400,400);
//        // creation d'une scene ( jpanel )
//        Scene menu = kernel1.graphicalEngine.generateScene(600,800);
//        // je bind la scene au Jframe
//        kernel1.graphicalEngine.bindScene(menu);
//        // je rajoute un objet a la scene
//        kernel1.graphicalEngine.addToScene(menu,palyer3);
//        // j'affiche la scene
//        kernel1.start();


    }
}

