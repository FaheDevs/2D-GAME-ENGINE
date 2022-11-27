package gamePlay;

import engines.graphics.GraphicalEngine;
import engines.graphics.Scene;
import engines.kernel.Entity;
import engines.kernel.Kernel;
import engines.kernel.Subject;
import engines.physics.PhysicalEngine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GamePlay implements Runnable {

    Kernel kernel;

    Thread gameThread;

    Entity player;

    List<List<Entity>> aliens;

    ArrayList<Bullet> shoots;

    ArrayList<Entity> entitiesGame;

    Scene world;

    int speedAliens;
    boolean leftFlag = true;

    public enum MoveDirection {
        RIGHT, LEFT, UP, DOWN
    }

    public GamePlay() throws IOException {
        entitiesGame = new ArrayList<>();

        kernel = new Kernel();

        shoots = new ArrayList<>();

        initEntities();

        world = kernel.graphicalEngine.generateScene(800, 800);

        // je bind la scene au Jframe
        kernel.graphicalEngine.bindScene(world);
        // je rajoute un objet a la scene

        for (Entity entity : entitiesGame) {

            kernel.graphicalEngine.addToScene(world, entity);

        }


        // j'affiche la scene

        kernel.commandEngine.enableKeyboardIO();
        kernel.start();

    }

    public void initEntities() throws IOException {


        //-------------------------------Player initialization-------------------------------------------------//

        player = new Player(32, 32);
        initEntity(player);

        player.setPositions(400, 700);

        //----------------------------------------------------------------------------------------------------------//

        //-------------------------------Aliens initialization-----------------------------------------------------//

        aliens = new ArrayList<>();
        int x = 760;
        int y = 100;
        for (int i = 0; i < 5; i++) {
            aliens.add(new ArrayList<>());
            for (int j = 0; j < 11; j++) {
                aliens.get(i).add(new Aliens(32, 32));
                initEntity(aliens.get(i).get(j));
                aliens.get(i).get(j).setAiObjectPositions(x, y);
                x -= 50;
            }
            y += 50;
            x = 760;
        }
        System.out.println(entitiesGame.size());
        speedAliens = aliens.get(0).get(0).aiObject.speed;

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

    public void shoot(Entity entity, boolean IsUp) {
        Bullet bullet =  generateBullet(entity.x, entity.y);
        shoots.add(bullet);
        kernel.graphicalEngine.addToScene(world,bullet);
    }


    public Bullet generateBullet(int x, int y) {
        Bullet bullet = new Bullet(2, 2);
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
                    updatePlayer();
                    updateAliens();
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

    public void updatePlayer() throws IOException {
        if (kernel.commandEngine.keyHandler.leftPressed) {
            kernel.physicalEngine.isCollide(player, player.physicalObject.x - player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame, this.world);
            if (!player.getAndResetCollision())
                kernel.physicalEngine.move(player, MoveDirection.LEFT);
        }

        if (kernel.commandEngine.keyHandler.rightPressed) {
            kernel.physicalEngine.isCollide(player, player.physicalObject.x + player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame, this.world);
            if (!player.getAndResetCollision())
                kernel.physicalEngine.move(player, MoveDirection.RIGHT);
        }

        if (kernel.commandEngine.keyHandler.STyped) {
            shoot(player,true);
        }
        for (int i = 0; i < shoots.size(); i++) {
            Bullet bullet = shoots.get(i);
            if(bullet.y < -5){
                shoots.remove(i);
                world.getEntities().remove(bullet);
            }else {
                bullet.tick();
            }

        }

        }
    public boolean isCollideWithLefdboard(){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                if (!kernel.physicalEngine.isCollideLeft(aliens.get(j).get(i), aliens.get(j).get(i).x - speedAliens, aliens.get(j).get(i).y, world)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isCollideWithRightdboard(){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                if (!kernel.physicalEngine.isCollideRight(aliens.get(j).get(i), aliens.get(j).get(i).x + speedAliens, aliens.get(j).get(i).y, world)){
                    return true;
                }
            }
        }
        return false;
    }
    public void chooseDirection (){
        if(isCollideWithRightdboard()){
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 11; j++) {
                    kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.LEFT);
                }
            }
            leftFlag = true;
        }
        else if(isCollideWithLefdboard()){
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 11; j++) {
                    kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.DOWN);
                }
            }
            leftFlag = false;
        }
    }
    public void updateAliens(){
        if(leftFlag){
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 11; j++) {
                    kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.LEFT);
                }
            }
        }else{
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 11; j++) {
                    kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.RIGHT);
                }
            }
        }
        chooseDirection();
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
//        Scene world = kernel1.graphicalEngine.generateScene(600,800);
//        // je bind la scene au Jframe
//        kernel1.graphicalEngine.bindScene(world);
//        // je rajoute un objet a la scene
//        kernel1.graphicalEngine.addToScene(world,palyer3);
//        // j'affiche la scene
//        kernel1.start();


    }
}

