package gamePlay;

import engines.graphics.Scene;
import engines.kernel.Entity;
import engines.kernel.Kernel;


import java.awt.Color;
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

    static int ctpTours = 0;

    int score = 0;

    int remaningLife = 3;


    boolean isShooted = false;

    boolean isKilled = false;

    boolean pos1 = true;

    ArrayList<Entity> entitiesGame;

    int heightWorld;

    int liminteHeightWorld = 60;

    int widthWorld;

    int speedAliens;

    public Scene scene;

    boolean leftFlag = true;


    ArrayList<Castle[][]> castle;

    Saucer saucer;

    List<String> pathsAliens;

    List<String> pathsPlayer;

    boolean isSaucer = false;

    int ecartCastl = 55;

    Entity scoreEntity;

    Entity greenBar;

    public GamePlay() throws IOException {
        entitiesGame = new ArrayList<>();

        killedAlienPostion[0] = -1;

        killedAlienPostion[1] = -1;

        kernel = new Kernel();

        shoots = new ArrayList<>();

        castle = new ArrayList<>();

        pathsAliens = new ArrayList<>();
        pathsAliens.add("src/main/resources/assets/images/Aliens/alienHaut1.png");
        pathsAliens.add("src/main/resources/assets/images/Aliens/alienHaut2.png");
        pathsAliens.add("src/main/resources/assets/images/Aliens/alienMeurt.png");

        pathsPlayer = new ArrayList<>();
        pathsPlayer.add("src/main/resources/assets/images/Spacecraft/craft.png");
        pathsPlayer.add("src/main/resources/assets/images/Spacecraft/destroy.png");

        initEntities();

        heightWorld = 600;

        widthWorld = 600;

        // je bind la scene au Jframe
        scene = kernel.generateScene(heightWorld, widthWorld);
        kernel.bindScene(scene);
        // je rajoute un objet a la scene

        for (Entity entity : entitiesGame) {
            kernel.addToScene(scene, entity);
        }

        greenBar = kernel.creatEntityToDraw(17, 555, Color.GREEN, scene);
        kernel.paintRectangle(greenBar, Color.GREEN, 566, 8);

        scoreEntity = kernel.creatEntityToDraw(17, 30, Color.GREEN, scene);
        kernel.afficheTexte(scoreEntity, "SCORE : " + score);


        // j'affiche la scene

        kernel.enableKeyboardIO();
        kernel.start();

    }

    public void initEntities() throws IOException {


        //-------------------------------Player initialization-------------------------------------------------//

        player = new Player(32, 32);
        initEntity(player);

        player.setPyhsicalObjectPositions(300, 500);

        //----------------------------------------------------------------------------------------------------------//

        //-------------------------------Castel initialization-------------------------------------------------//

        for(int i = 0; i <4; i++) {
            castle.add(new Castle[Castle.nbLines][Castle.nbColumns]);
        }
        for(int k = 0; k <4; k++) {
            for(int i=0; i < Castle.nbLines; i++) {
                for(int j = 0; j < Castle.nbColumns; j++) {
                    castle.get(k)[i][j] = new Castle(80 + k * (Castle.widthCastle + ecartCastl), 400);
                    initEntity(castle.get(k)[i][j]);
                    castle.get(k)[i][j].setPyhsicalObjectPositions(castle.get(k)[i][j].xPos, castle.get(k)[i][j].yPos);
                }
            }
            initTabCastels(castle.get(k));
            drowCastle(castle.get(k), Castle.nbLines, Castle.nbColumns);
        }


        //----------------------------------------------------------------------------------------------------------//

        //-------------------------------Aliens initialization-----------------------------------------------------//

        aliens = new ArrayList<>();
        int x = 560;
        int y = 90;
        for (int i = 0; i < 5; i++) {
            aliens.add(new ArrayList<>());
            for (int j = 0; j < 11; j++) {
                aliens.get(i).add(new Aliens(32, 32));
                initEntity(aliens.get(i).get(j));
                aliens.get(i).get(j).setAiObjectPositions(x, y);
                x -= 40;
            }
            y += 40;
            x = 560;
        }
        speedAliens = aliens.get(0).get(0).aiObject.speed;

        //----------------------------------------------------------------------------------------------------------//

        //-------------------------------Saucer initialization-------------------------------------------------//

        saucer = new Saucer(32, 32);
        initEntity(saucer);
        saucer.setPyhsicalObjectPositions(widthWorld + 32, 40);
        isSaucer = true;

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

    public void generateSaucer(){
        saucer = new Saucer(32, 32);
        initEntity(saucer);
        saucer.setPyhsicalObjectPositions(widthWorld + 32, 30);
        isSaucer = true;
    }

    public void shoot(Entity entity, boolean isP) {
        Bullet bullet =  generateBulletAliens(entity.x + (entity.widthEntity/2) + 2, entity.y);
        bullet.isPressed = isP;
        if(isP) bullet.setImage("src/main/resources/assets/images/shot.png");
        else bullet.setImage("src/main/resources/assets/images/shotAlien.png");
        shoots.add(bullet);
        kernel.addToScene(scene, shoots.get(shoots.indexOf(bullet)));
    }

    public Bullet generateBulletAliens(int x, int y) {
        Bullet bullet = new Bullet(3, 1);
        bullet.setPyhsicalObjectPositions(x, y - bullet.heightEntity);
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
            ctpTours ++;
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                try {
                    updatePlayer();
                    updateAliens();
                    updateSaucer();
                    if (!isSaucer) generateSaucer();
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
                aliensShoot();
                drawCount = 0;
                timer = 0;
            }
        }

    }

    public void updatePlayer() throws IOException {
        if (kernel.getKeyHandler().leftPressed) {
            kernel.isCollide(heightWorld, widthWorld, player, player.physicalObject.x - player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame);
            if (!player.getAndResetCollision())
                kernel.move(player, "left");
        }

        if (kernel.getKeyHandler().rightPressed) {
            kernel.isCollide(heightWorld, widthWorld, player, player.physicalObject.x + player.physicalObject.speed,
                    player.physicalObject.y, this.entitiesGame);
            if (!player.getAndResetCollision())
                kernel.move(player, "right");

        }

        if (kernel.getKeyHandler().STyped) {
            if (!isShooted && !isKilled) {
                shoot(player, true);
                isShooted = true;
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
                if(bullet != null && bullet.y <= heightWorld - liminteHeightWorld){
                    killPlayer(bullet, bullet.x, bullet.y + bullet.physicalObject.speed, player);
                    bullet.tick();
                }
                else if (bullet != null) killBullet(bullet);
            }
            getBrickToEliminate(bullet);
            destroySaucer(saucer, bullet);
        }
        //if() kernel.spriteEntity(player, "src/main/resources/assets/images/Spacecraft/10.png");
    }

    public void chooseDirection (){
        if(kernel.isCollideWithRightdboard(aliens, widthWorld)){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null) kernel.move(aliens.get(i).get(j), "left");
                }
            }
            leftFlag = true;
        }
        else if(kernel.isCollideWithLefdboard(aliens)){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null) kernel.move(aliens.get(i).get(j), "down");
                }
            }
            leftFlag = false;
        }
    }
    public void updateAliens(){
        if(pos1) pos1 = false;
        else pos1 = true;
        if(killedAlienPostion[0] != -1){
            eliminateAlien(killedAlienPostion);
            killedAlienPostion[0] = -1;
            score = score + 10;
        }
        if(leftFlag){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if(aliens.get(i).get(j) != null) {
                        kernel.move(aliens.get(i).get(j), "left");
                        chooseImage(aliens.get(i).get(j), pos1, pathsAliens);
                    }
                }
            }
        }else{
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if(aliens.get(i).get(j) != null){
                        kernel.move(aliens.get(i).get(j), "right");
                        chooseImage(aliens.get(i).get(j), pos1, pathsAliens);
                    }
                     /* if(aliens.get(i).get(j) != null && aliens.get(i).get(j).aiObject.getSpeed() < 9)
                        aliens.get(i).get(j).aiObject.setSpeed(aliens.get(i).get(j).aiObject.getSpeed() + 1);*/

                }
            }
        }
        chooseDirection();
    }

    public void alienKilled(Entity bulletAlien, int newX, int newY, List<List<Entity>> aliens){
        killedAlienPostion[0] = -1;
        killedAlienPostion[1] = -1;
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {;
                if(aliens.get(i).get(j) != null &&
                        kernel.collideObjectToObject(bulletAlien, aliens.get(i).get(j), bulletAlien.x, bulletAlien.y - bulletAlien.physicalObject.speed)){
                    aliens.get(i).get(j).killed = true;
                    killBullet((Bullet) bulletAlien);
                    killedAlienPostion[0] = i;
                    killedAlienPostion[1] = j;
                    break;
                }
            }
        }
    }

    public void eliminateAlien(int[] alienKilledPostion){
       if (!aliens.isEmpty() && !aliens.get(alienKilledPostion[0]).isEmpty()) {
           player.score += this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]).value;
           kernel.afficheTexte(scoreEntity, "SCORE : " + Integer.toString(player.score));
           this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]).killed = true;
           chooseImage(this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]), pos1, pathsAliens);
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

    public void killPlayer(Entity bulletPlayer, int newX, int newY, Entity player){
        if(kernel.collideObjectToObject(bulletPlayer, player, newX, newY)){
            killBullet((Bullet) bulletPlayer);
            player.killed = true;
            chooseImage(player, true, pathsPlayer);
            kernel.erase(player);
            entitiesGame.remove(player);
            kernel.entities.remove(player);
            isKilled = true;
        }
    }


    public void initTabCastels(Castle[][] castle) {
        // On remplit toutes les cases du tableau avec true
        for(int i=0; i < Castle.nbLines; i++) {
            for(int j = 0; j < Castle.nbColumns; j++) {
               castle[i][j].isBrick = true;
                // castle.get(i).get(j). = true;
            }
        }
        // On remplit toutes les cases sans brique du tableau avec false
        // Biseautage du haut du château
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 6; j++) {
                castle[i][j].isBrick = false;
                castle[i][Castle.nbColumns - j - 1].isBrick = false;
            }
        }
        for(int i = 2; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                castle[i][j].isBrick = false;
                castle[i][Castle.nbColumns- j - 1].isBrick = false;
            }
        }
        for(int i = 4; i < 6; i++) {
            for(int j = 0; j < 2; j++) {
                castle[i][j].isBrick = false;
                castle[i][Castle.nbColumns - j -1].isBrick = false;
            }
        }
        // Entrée du château
        for(int i = 18; i < Castle.nbLines; i++) {
            for(int j = 10; j < Castle.nbColumns - 10; j++) {
                castle[i][j].isBrick = false;
            }
        }
        // Biseautage entrée du château
        for(int i = 16; i < 18; i++) {
            for(int j = 12; j < Castle.nbColumns-12; j++) {
                castle[i][j].isBrick = false;
                castle[i][Castle.nbColumns - j - 1].isBrick = false;
            }
        }
        for(int i = 14; i < 16; i++) {
            for(int j = 14; j < Castle.nbColumns-14; j++) {
                castle[i][j].isBrick = false;
                castle[i][Castle.nbColumns - j - 1].isBrick = false;
            }
        }
        for(int i = 4; i < 6; i++) {
            for(int j = 0; j < 2; j++) {
                castle[i][j].isBrick = false;
                castle[i][Castle.nbColumns - j -1].isBrick = false;
            }
        }
    }
    // Dessin du château
    public void drowCastle (Castle[][] castles, int nbLines, int nbColumns) {
        for(int i = 0; i < nbLines; i++) {
            for(int j = 0; j < nbColumns; j++) {
                if(castles[i][j].isBrick == true) {
                    kernel.paintRectangle(castles[i][j], Color.GREEN, Castle.dimention, Castle.dimention);
                    castles[i][j].setPyhsicalObjectPositions(castles[i][j].xPos + 2 * j, castles[i][j].yPos + 2 * i);
                } else {
                    kernel.paintRectangle(castles[i][j], Color.BLACK, Castle.dimention, Castle.dimention);
                    castles[i][j].setPyhsicalObjectPositions(castles[i][j].xPos + 2 * j, castles[i][j].yPos + 2 * i);
                }
            }
        }
    }
   public void destroyCastle(Castle castle) {
        kernel.rePaintRectangle(castle, Color.BLACK);
   }
    public void getBrickToEliminate(Bullet bullet){
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < Castle.nbLines; i++) {
                for (int j = 0; j < Castle.nbColumns; j++) {;
                    if(castle.get(k)[i][j].isBrick != false &&
                            kernel.collideObjectToObject(bullet, castle.get(k)[i][j], bullet.x, bullet.y - bullet.physicalObject.speed)){
                        castle.get(k)[i][j].isBrick = false;
                        destroyCastle(castle.get(k)[i][j]);
                        /*if(bullet.isPressed && i + 5 < Castle.nbColumns) {
                            for (int l = i; l < 6; l++) {
                                castle.get(k)[l][j].isBrick = false;
                                destroyCastle(castle.get(k)[l][j]);
                            }
                        }
                        else if(!bullet.isPressed && Castle.nbColumns - i - 5 > 0) {
                            for (int l = Castle.nbColumns - i; l < 6; l++) {
                                castle.get(k)[i][l].isBrick = false;
                                destroyCastle(castle.get(k)[i][l]);
                            }
                        }*/
                        killBullet(bullet);
                        entitiesGame.remove(castle.get(k)[i][j]);
                        break;
                    }
                }
            }
        }
    }

    public void updateSaucer(){
        if(saucer != null){
            kernel.isCollide(heightWorld, widthWorld, saucer, saucer.physicalObject.x + saucer.physicalObject.speed,
                    saucer.physicalObject.y, this.entitiesGame);
            if (!saucer.getAndResetCollision())
                kernel.move(saucer);

            if (kernel.isCollideRight(widthWorld, saucer.widthEntity, saucer.physicalObject.x + saucer.physicalObject.speed)){
                killSauser(saucer);
            }
        }
    }

    public void killSauser(Saucer saucer){
        kernel.erase(saucer);
        /*entitiesGame.remove(saucer);
        kernel.entities.remove(saucer);*/
        isSaucer = false;
    }

    public void destroySaucer(Entity saucer, Bullet bullet){
        if(kernel.collideObjectToObject(bullet, saucer, bullet.x, bullet.y - bullet.physicalObject.speed)){
            killBullet(bullet);
            killSauser((Saucer) saucer);
        }

    }

    public void chooseImage(Entity entity, boolean pos1, List<String> paths) {
        // Méthode qui charge l'image de l'alien selon son état et sa position (1 ou 2)
        if(!entity.killed) {
            if(pos1) entity.setImage(paths.get(0));
            else if (paths.get(1) != null) entity.setImage(paths.get(1));
        }
        else {
            if (paths.size() > 2) entity.setImage(paths.get(2));
            else entity.setImage(paths.get(1));
            try {Thread.sleep(30);}
            catch (InterruptedException e) {}
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
//        Scene scene = kernel1.graphicalEngine.generateScene(600,800);
//        // je bind la scene au Jframe
//        kernel1.graphicalEngine.bindScene(scene);
//        // je rajoute un objet a la scene
//        kernel1.graphicalEngine.addToScene(scene,palyer3);
//        // j'affiche la scene
//        kernel1.start();


    }
}

