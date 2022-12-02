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


    boolean isShooted = false;

    boolean isKilled = false;

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

    public void shoot(Entity entity, boolean isP) {
        Bullet bullet =  generateBulletAliens(entity.x + (entity.widthEntity/2) - 1, entity.y);
        bullet.isPressed = isP;
        shoots.add(bullet);
        kernel.addToScene(world,shoots.get(shoots.indexOf(bullet)));
    }

    public Bullet generateBulletAliens(int x, int y) {
        Bullet bullet = new Bullet(3, 1);
        bullet.setPositions(x, y - bullet.heightEntity);
        initEntity(bullet);
        return bullet;
    }


    public void killBullet(Bullet bullet){
        kernel.erase(bullet);
        entitiesGame.remove(bullet);
        kernel.entities.remove(bullet);
        if(!bullet.isPressed){
            shoots.remove(bullet);
        }
        else{
            shoots.remove(bullet);
            isShooted = false;
        }
    }




    public Kernel getKernel() {
        return kernel;
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
                if(shoots.isEmpty()) aliensShoot();
                drawCount = 0;
                timer = 0;
            }
        }

    }

    public void updatePlayer() throws IOException {
        if (kernel.getKeyHandler().leftPressed) {
            kernel.isCollide(player, player.physicalObject.x - player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame, this.world);
            if (!player.getAndResetCollision())
                kernel.move(player, MoveDirection.LEFT);
        }

        if (kernel.getKeyHandler().rightPressed) {
            kernel.isCollide(player, player.physicalObject.x + player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame, this.world);
            if (!player.getAndResetCollision())
                kernel.move(player, MoveDirection.RIGHT);
        }

        if (kernel.getKeyHandler().STyped) {
            if (!isShooted && !isKilled) {
                shoot(player, true);
                isShooted = true;
                System.out.println("im");
            }
        }
        for (int i = 0; i < shoots.size(); i++) {
            Bullet bullet = shoots.get(i);
            if (bullet.isPressed){
                if (bullet != null && bullet.y < -5){
                    killBullet(bullet);
                }else {
                    alienKilled(bullet, bullet.x, bullet.y - bullet.physicalObject.speed, aliens);
                    if (isShooted) bullet.tick();
                }
            }
            if (!bullet.isPressed){
                if(bullet != null && bullet.y <= world.getHeight()){
                    killPlayer(bullet, bullet.x, bullet.y + bullet.physicalObject.speed, player);
                    bullet.tick();
                }
                else if (bullet != null) killBullet(bullet);
            }
        }
    }

    public void chooseDirection (){
        if(kernel.isCollideWithRightdboard(aliens, world)){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null) kernel.moveAliens(aliens.get(i).get(j), MoveDirection.LEFT);
                }
            }
            leftFlag = true;
        }
        else if(kernel.isCollideWithLefdboard(aliens, world)){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null) kernel.moveAliens(aliens.get(i).get(j), MoveDirection.DOWN);
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
                    if(aliens.get(i).get(j) != null) kernel.moveAliens(aliens.get(i).get(j), MoveDirection.LEFT);
                }
            }
        }else{
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if(aliens.get(i).get(j) != null) kernel.moveAliens(aliens.get(i).get(j), MoveDirection.RIGHT);
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
                if(aliens.get(i).get(j) != null && kernel.collideObjectToObject(entity, aliens.get(i).get(j), tempEntity.x, tempEntity.y - entity.physicalObject.speed)){
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
           kernel.erase(this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]));
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
        if(!isKilled){
            chooseAlien();
            shoot(aliens.get(choosedAlienPostion[0]).get(choosedAlienPostion[1]), false);
        }
    }

    public void killPlayer(Entity entity, int newX, int newY, Entity player){
        Entity tempEntity = new Entity(entity.heightEntity, entity.widthEntity, Entity.Type.Ai, 0);
        tempEntity.x = newX;
        tempEntity.y = newY;
        if(kernel.collideObjectToObject(tempEntity, player, newX, newY)){
            killBullet((Bullet) entity);
            kernel.erase(player);
            entitiesGame.remove(player);
            kernel.entities.remove(player);
            isKilled = true;
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
//        Scene world = kernel1.graphicalEngine.generateScene(600,800);
//        // je bind la scene au Jframe
//        kernel1.graphicalEngine.bindScene(world);
//        // je rajoute un objet a la scene
//        kernel1.graphicalEngine.addToScene(world,palyer3);
//        // j'affiche la scene
//        kernel1.start();


    }
}

