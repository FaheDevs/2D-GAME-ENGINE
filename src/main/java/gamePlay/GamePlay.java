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

    static int[] killedAlienPostion = new int[2];

    static int[] choosedAlienPostion = new int[2];


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

        killedAlienPostion[0] = -1;

        killedAlienPostion[1] = -1;

        kernel = new Kernel();

        shoots = new ArrayList<>();

        initEntities();

        world = kernel.generateScene(800, 800);

        // je bind la scene au Jframe
        kernel.bindScene(world);
        // je rajoute un objet a la scene

        for (Entity entity : entitiesGame) {

            kernel.addToScene(world, entity);

        }


        // j'affiche la scene

        kernel.enableKeyboardIO();
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
        Bullet bullet =  generateBullet(entity.x + entity.widthEntity/2, entity.y);
        shoots.add(bullet);
        kernel.addToScene(world,bullet);
    }


    public Bullet generateBullet(int x, int y) {
        Bullet bullet = new Bullet(3, 1);
        bullet.setPositions(x, y - bullet.heightEntity);
        initEntity(bullet);
        return bullet;
    }

    public void KillBullet(Bullet bullet){
        bullet.setPhysicalPositions(bullet.x, 0);
        kernel.erase(bullet);
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
                kernel.refreshWindow();
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
            // il ne faut pas utiliser les moteurs de kernel
            kernel.isCollide(player, player.physicalObject.x - player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame, this.world);
            if (!player.getAndResetCollision())
                kernel.move(player, MoveDirection.LEFT);
        }

        if (kernel.commandEngine.keyHandler.rightPressed) {
            kernel.isCollide(player, player.physicalObject.x + player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame, this.world);
            if (!player.getAndResetCollision())
                kernel.move(player, MoveDirection.RIGHT);
        }

        if (kernel.commandEngine.keyHandler.STyped) {
            if (shoots.isEmpty()) shoot(player,true);
        }
        for (int i = 0; i < shoots.size(); i++) {
            Bullet bullet = shoots.get(i);
            if(bullet.y < -5){
                shoots.remove(i);
                world.getGraphicalObjects().remove(bullet);
            }else {
                alienKilled(bullet, bullet.x, bullet.y - bullet.physicalObject.speed, aliens);
                bullet.tick();
            }
        }

        }
    public boolean isCollideWithLefdboard(){
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {
                if (aliens.get(i).get(j) != null && !kernel.isCollideLeft(aliens.get(i).get(j), aliens.get(i).get(j).x - speedAliens, aliens.get(i).get(j).y, world)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isCollideWithRightdboard(){
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {
                if (aliens.get(i).get(j) != null && !kernel.isCollideRight(aliens.get(i).get(j), aliens.get(i).get(j).x + speedAliens, aliens.get(i).get(j).y, world)){
                    return true;
                }
            }
        }
        return false;
    }
    public void chooseDirection (){
        if(isCollideWithRightdboard()){
            for (List<Entity> alien : aliens) {
                for (Entity entity : alien) {
                    kernel.aiEngine.move(entity, MoveDirection.LEFT);
                }
            }
            leftFlag = true;
        }
        else if(isCollideWithLefdboard()){
            for (List<Entity> alien : aliens) {
                for (Entity entity : alien) {
                    kernel.aiEngine.move(entity, MoveDirection.DOWN);
                }
            }
            leftFlag = false;
        }
    }
    public void updateAliens(){
        if(killedAlienPostion[0] != -1){
            eliminateAlien(killedAlienPostion);
            killedAlienPostion[0] = -1;
        }
        if(leftFlag){
            for (List<Entity> alien : aliens) {
                for (Entity entity : alien) {
                    if (entity != null) kernel.aiEngine.move(entity, MoveDirection.LEFT);
                }
            }
        }else{
            for (List<Entity> alien : aliens) {
                for (Entity entity : alien) {
                    if (entity != null) kernel.aiEngine.move(entity, MoveDirection.RIGHT);
                }
            }
        }
        chooseDirection();
    }

    public void alienKilled(Entity entity, int newX, int newY, List<List<Entity>> aliens){
        Entity tempEntity = new Entity(entity.heightEntity, entity.widthEntity, Entity.Type.Physical, 0);
        tempEntity.x = newX;
        tempEntity.y = newY;
        killedAlienPostion[0] = -1;
        killedAlienPostion[1] = -1;
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {;
                if(kernel.collideObjectToObject(entity, aliens.get(i).get(j), tempEntity.x, tempEntity.y - entity.physicalObject.speed)){
                    aliens.get(i).get(j).killed = true;
                    KillBullet((Bullet) entity);
                    killedAlienPostion[0] = i;
                    killedAlienPostion[1] = j;
                    break;
                }
            }
        }
    }

    public void eliminateAlien(int[] alienKilledPostion){
       if (!aliens.isEmpty() && !aliens.get(alienKilledPostion[0]).isEmpty()) {
           kernel.erase(this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]));
           this.aliens.get(alienKilledPostion[0]).remove(alienKilledPostion[1]);
       }

    }

    public static void main(String[] args) throws IOException {
        GamePlay game = new GamePlay();
        game.startGameThread();

    }
}

