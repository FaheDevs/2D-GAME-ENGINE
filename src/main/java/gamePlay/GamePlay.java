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
import java.util.Random;


public class GamePlay implements Runnable {

    Kernel kernel;

    Thread gameThread;

    Entity player;

    List<List<Entity>> aliens;

    static int[] killedAlienPostion = new int[2];

    static int[] choosedAlienPostion = new int[2];


    ArrayList<Bullet> shoots;
    Bullet bulletPlayer;


    boolean isShooted = false;

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

    public void shootPlayer(Entity entity) {
        /*Bullet bulletPlayer =  generateBullet(entity.x + (entity.widthEntity/2) - 1, entity.y);
        shoots.add(bulletPlayer);*/
        bulletPlayer = new Bullet(3, 1);
        bulletPlayer.setPositions(entity.x + (entity.widthEntity/2) - 1, entity.y);
        bulletPlayer.isPressed = true;
        initEntity(bulletPlayer);
        kernel.graphicalEngine.addToScene(world, bulletPlayer);
    }

    public void shootAliens(Entity entity) {
        Bullet bullet =  generateBulletAliens(entity.x + (entity.widthEntity/2) - 1, entity.y);
        shoots.add(bullet);
        kernel.graphicalEngine.addToScene(world,shoots.get(shoots.indexOf(bullet)));


    }
    public Bullet generateBulletAliens(int x, int y) {
        Bullet bullet = new Bullet(3, 1);
        bullet.setPositions(x, y - bullet.heightEntity);
        initEntity(bullet);
        return bullet;
    }


    public void killBullet(Bullet bullet){
        kernel.graphicalEngine.erase(bullet);
        entitiesGame.remove(bullet);
        kernel.entities.remove(bullet);
        isShooted = false;
        bullet.isPressed = false;
        if(shoots != null && shoots.contains(bullet)) shoots.remove(bullet);
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
                //if(shoots.isEmpty()) aliensShoot();
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
            if (!isShooted) {
                shootPlayer(player);
                isShooted = true;
            }
        }
        if(bulletPlayer != null && bulletPlayer.y < -5) {
            killBullet(bulletPlayer);
        }
        else if (bulletPlayer != null){
            if(bulletPlayer.isPressed) {
                alienKilled(bulletPlayer, bulletPlayer.x, bulletPlayer.y - bulletPlayer.physicalObject.speed, aliens);
                if (isShooted) bulletPlayer.tick();
            }
            else  bulletPlayer.tick();
        }
        if(shoots != null && !shoots.isEmpty()){
            for (int i = 0; i < shoots.size(); i++) {
                Bullet bullet = shoots.get(i);
                if(bullet != null && bullet.y <= world.getHeight()) bullet.tick();
                else if (bullet != null) killBullet(bullet);
            }
        }
        }
    public boolean isCollideWithLefdboard(){
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {
                if (aliens.get(i).get(j) != null && !kernel.physicalEngine.isCollideLeft(aliens.get(i).get(j), aliens.get(i).get(j).x - speedAliens, aliens.get(i).get(j).y, world)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isCollideWithRightdboard(){
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {
                if (aliens.get(i).get(j) != null && !kernel.physicalEngine.isCollideRight(aliens.get(i).get(j), aliens.get(i).get(j).x + speedAliens, aliens.get(i).get(j).y, world)){
                    return true;
                }
            }
        }
        return false;
    }
    public void chooseDirection (){
        if(isCollideWithRightdboard()){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null) kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.LEFT);
                }
            }
            leftFlag = true;
        }
        else if(isCollideWithLefdboard()){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null) kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.DOWN);
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
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if(aliens.get(i).get(j) != null) kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.LEFT);
                }
            }
        }else{
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if(aliens.get(i).get(j) != null) kernel.aiEngine.move(aliens.get(i).get(j), MoveDirection.RIGHT);
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
                if(aliens.get(i).get(j) != null && kernel.physicalEngine.collideObjectToObject(entity, aliens.get(i).get(j), tempEntity.x, tempEntity.y - entity.physicalObject.speed)){
                    aliens.get(i).get(j).killed = true;
                    killBullet((Bullet) entity);
                    killedAlienPostion[0] = i;
                    killedAlienPostion[1] = j;
                    break;
                }
            }
        }
    }

    public void eliminateAlien(int[] alienKilledPostion){
       if (!aliens.isEmpty() && !aliens.get(alienKilledPostion[0]).isEmpty()) {
           //this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]).setImage("src/main/resources/assets/images/enemigo1prov.png");
           kernel.graphicalEngine.erase(this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]));
           this.aliens.get(alienKilledPostion[0]).set(alienKilledPostion[1], null);
       }

    }
    public void chooseAlien(){
        Random random = new Random();
        choosedAlienPostion[0] = -1;
        choosedAlienPostion[1] = -1;
        if(!aliens.isEmpty()){
            do{
                int colonneChoosed = random.nextInt(11);
                for (int i = 0; i < 5 ; i++) {
                    if (aliens.get(i).get(colonneChoosed) != null){
                        choosedAlienPostion[0] = i;
                        choosedAlienPostion[1] = colonneChoosed;
                    }
                    
                }
            }while (choosedAlienPostion[0] == -1);
        }
    }
    public void aliensShoot(){
        chooseAlien();
        shootAliens(aliens.get(choosedAlienPostion[0]).get(choosedAlienPostion[1]));
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

