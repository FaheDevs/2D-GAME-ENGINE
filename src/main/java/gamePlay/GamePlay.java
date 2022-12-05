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

    boolean inGame = false;

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

    int nbDown = 0;

    public Scene menuView;

    private Scene winView;

    private Scene looseView;

    public boolean iCanDown = true;

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
        initMenuView();
        scene = kernel.generateScene(heightWorld, widthWorld);
        kernel.bindScene(scene);
        // je rajoute un objet a la scene

        for (Entity entity : entitiesGame) {
            kernel.addToScene(scene, entity);
        }

        greenBar = kernel.creatEntityToDrow(17, 555, Color.GREEN, scene);
        kernel.paintRectangle(greenBar, Color.GREEN, 566, 8);

        scoreEntity = kernel.creatEntityToDrow(17, 30, Color.GREEN, scene);
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
                    castle.get(k)[i][j].setAiObjectPositions(castle.get(k)[i][j].xPos, castle.get(k)[i][j].yPos);
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
        saucer.setAiObjectPositions(widthWorld + 32, 40);
        isSaucer = true;

        //----------------------------------------------------------------------------------------------------------//
    }


    public void startGameThread() {
        kernel.switchScene(menuView);
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
        saucer.setAiObjectPositions(widthWorld + 32, 30);
        isSaucer = true;
        System.out.println("creed");
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
        double drawInterval = 1_800 / 60;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.currentTimeMillis();
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
                if(ctpTours % 20 == 0){
                    if(pos1) pos1 = false;
                    else pos1 = true;
                }

                if(ctpTours % 250 == 0){
                    aliensShoot();
                }
                if(ctpTours % 100 == 0){
                    if (!isSaucer) generateSaucer();
                }

                if(ctpTours % 200 == 0){
                    updateSaucer();
                }

                kernel.updateEntities();
                kernel.refreshWindow();
                delta--;
                drawCount++;
                ctpTours ++;
            }
            if (timer >= 1000) {
                System.out.println("FPS:" + drawCount);
                aliensShoot();
                drawCount = 0;
                timer = 0;
            }
        }

    }

    public void updatePlayer() throws IOException {
        if(inGame){
            if (kernel.getKeyHandler().leftPressed) {
                kernel.isCollide(heightWorld, widthWorld, player, player.x - player.getSpeed(),
                        player.y, this.entitiesGame);
                if (!player.getAndResetCollision())
                    kernel.move(player, "left");
            }

            if (kernel.getKeyHandler().rightPressed) {
                kernel.isCollide(heightWorld, widthWorld, player, player.x + player.getSpeed(),
                        player.y, this.entitiesGame);
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
                        alienKilled(bullet, aliens);
                        getBrickToEliminate(bullet);
                        destroySaucer(saucer, bullet);
                        if (isShooted) bullet.tick();
                    }
                }
                if (!bullet.isPressed){
                    if(bullet != null && bullet.y <= heightWorld - liminteHeightWorld){
                        killPlayerBullet(bullet, bullet.x, bullet.y + bullet.getSpeed(), player);
                        getBrickToEliminate(bullet);
                        bullet.tick();
                    }
                    else if (bullet != null) killBullet(bullet);
                }
            }
        }

        if (kernel.getKeyHandler().enterPressed && !isKilled && !inGame) {
            kernel.switchScene(scene);
            inGame = true;
        }
       /* if (kernel.getKeyHandler().Rtyped && isKilled) {
            kernel.switchScene(menuView);
            isKilled = false;
        }*/
    }

    public void chooseDirection () throws IOException {
        if(kernel.isCollideWithRightdboard(aliens, widthWorld)){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null) {
                        killPlayerWithCollision(aliens.get(i).get(j), aliens.get(i).get(j).x - aliens.get(i).get(j).getSpeed(),
                                aliens.get(i).get(j).y, player);
                        kernel.move(aliens.get(i).get(j), "left");
                        chooseImage(aliens.get(i).get(j), pos1, pathsAliens);
                    }
                }
            }
            leftFlag = true;
        }
        else if(kernel.isCollideWithLefdboard(aliens)){
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = 0; j < aliens.get(i).size(); j++) {
                    if (aliens.get(i).get(j) != null && !player.killed && iCanDown){
                        if(iCanDown && aliensToucheCastle(aliens.get(i).get(j), aliens.get(i).get(j).x, ((aliens.size() - 1 - i) * 40) + aliens.get(i).get(j).y + aliens.get(i).get(j).getSpeed() + 12)){
                            kernel.move(aliens.get(0).get(0), "up");
                            kernel.move(aliens.get(0).get(1), "up");
                            iCanDown = false;
                        }
                        killPlayerWithCollision(aliens.get(i).get(j), aliens.get(i).get(j).x,
                                aliens.get(i).get(j).y + aliens.get(i).get(j).getSpeed(), player);
                        kernel.move(aliens.get(i).get(j), "down");
                        chooseImage(aliens.get(i).get(j), pos1, pathsAliens);
                    }
                }
            }
            nbDown ++;
            leftFlag = false;
        }
    }
    public void updateAliens() throws IOException {
        if(inGame){
            if(killedAlienPostion[0] != -1){
                eliminateAlien(killedAlienPostion);
                killedAlienPostion[0] = -1;
                score = score + 10;
            }
            if(leftFlag){
                for (int i = 0; i < aliens.size(); i++) {
                    for (int j = 0; j < aliens.get(i).size(); j++) {
                        if(aliens.get(i).get(j) != null) {
                            killPlayerWithCollision(aliens.get(i).get(j), aliens.get(i).get(j).x - aliens.get(i).get(j).getSpeed(),
                                    aliens.get(i).get(j).y, player);
                            kernel.move(aliens.get(i).get(j), "left");
                            chooseImage(aliens.get(i).get(j), pos1, pathsAliens);
                        }
                    }
                }
            }else{
                for (int i = 0; i < aliens.size(); i++) {
                    for (int j = 0; j < aliens.get(i).size(); j++) {
                        if(aliens.get(i).get(j) != null){
                            killPlayerWithCollision(aliens.get(i).get(j), aliens.get(i).get(j).x + aliens.get(i).get(j).getSpeed(),
                                    aliens.get(i).get(j).y, player);
                            kernel.move(aliens.get(i).get(j), "right");
                            chooseImage(aliens.get(i).get(j), pos1, pathsAliens);
                            if(aliens.get(i).get(j) != null && aliens.get(i).get(j).getSpeed() <= 3 && nbDown % 9 == 0){
                                aliens.get(i).get(j).aiObject.speed += 1;
                            }
                        }


                    }
                }
            }
            chooseDirection();
        }
    }

    public void alienKilled(Entity bulletAlien, List<List<Entity>> aliens){
        killedAlienPostion[0] = -1;
        killedAlienPostion[1] = -1;
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {;
                if(aliens.get(i).get(j) != null &&
                        kernel.collideObjectToObject(bulletAlien, aliens.get(i).get(j), bulletAlien.x, bulletAlien.y - bulletAlien.physicalObject.speed)){
                    killBullet((Bullet) bulletAlien);
                    aliens.get(i).get(j).killed = true;
                    killedAlienPostion[0] = i;
                    killedAlienPostion[1] = j;
                    return;
                }
            }
        }
    }

    public void eliminateAlien(int[] alienKilledPostion){
       if (!aliens.isEmpty() && !aliens.get(alienKilledPostion[0]).isEmpty()) {
           chooseImage(this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]), false, pathsAliens);
           player.score += this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]).value;
           kernel.afficheTexte(scoreEntity, "SCORE : " + Integer.toString(player.score));
           this.aliens.get(alienKilledPostion[0]).get(alienKilledPostion[1]).killed = true;
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

    public void killPlayerBullet(Entity bulletPlayer, int newX, int newY, Entity player) throws IOException {
        if(kernel.collideObjectToObject(bulletPlayer, player, newX, newY)){
            killBullet((Bullet) bulletPlayer);
            player.killed = true;
            chooseImage(player, true, pathsPlayer);
            kernel.erase(player);
            entitiesGame.remove(player);
            kernel.entities.remove(player);
            isKilled = true;
            inGame = false;
            initLooseView(score);
            kernel.switchScene(looseView);
        }
    }

    public void killPlayerWithCollision(Entity killer, int newX, int newY, Entity player) throws IOException {
        if(kernel.collideObjectToObject(killer, player, newX, newY)){
            player.killed = true;
            chooseImage(player, true, pathsPlayer);
            kernel.erase(player);
            entitiesGame.remove(player);
            kernel.entities.remove(player);
            isKilled = true;
            inGame = false;
            initLooseView(score);
            kernel.switchScene(looseView);
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
                    castles[i][j].setAiObjectPositions(castles[i][j].xPos + 2 * j, castles[i][j].yPos + 2 * i);
                } else {
                    kernel.paintRectangle(castles[i][j], Color.BLACK, Castle.dimention, Castle.dimention);
                    castles[i][j].setAiObjectPositions(castles[i][j].xPos + 2 * j, castles[i][j].yPos + 2 * i);
                }
            }
        }
    }
   public void destroyCastle(Castle castle) {
        kernel.rePaintRectangle(castle, Color.BLACK);
   }


   public boolean aliensToucheCastle (Entity alien, int newX, int newY){
       for (int i = 0; i < 4; i++) {
           for (int j = 0; j < Castle.nbLines; j++) {
               for (int k = 0; k < Castle.nbColumns; k++) {
                   if(castle.get(i)[j][k].isBrick && kernel.collideObjectToObject(alien, castle.get(i)[j][k], newX, newY)) return true;
                   else iCanDown = true;
               }
           }
       }
       return false;
   }

   public void getBrickToEliminate(Bullet bullet){
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < Castle.nbLines; i++) {
                for (int j = 0; j < Castle.nbColumns; j++) {;
                    if(castle.get(k)[i][j].isBrick && kernel.getColorRect((Entity)castle.get(k)[i][j]) != Color.BLACK &&
                            kernel.collideObjectToObject(bullet, castle.get(k)[i][j], bullet.x, bullet.y - bullet.physicalObject.speed)){
                        destroyCastle(castle.get(k)[i][j]);

                        if(i + 1 < Castle.nbLines && castle.get(k)[i + 1][j].isBrick){
                            destroyCastle(castle.get(k)[i + 1][j]);
                            castle.get(k)[i][j].isBrick = false;
                        }
                        if(j + 1 < Castle.nbColumns && castle.get(k)[i][j + 1].isBrick){
                            destroyCastle(castle.get(k)[i][j + 1]);
                            castle.get(k)[i][j].isBrick = false;
                        }
                        if((j + 1 < Castle.nbColumns && i + 1 < Castle.nbLines) && castle.get(k)[i + 1][j + 1].isBrick){
                            destroyCastle(castle.get(k)[i + 1][j + 1]);
                            castle.get(k)[i][j].isBrick = false;
                        }


                        if(i - 1 > 0 && castle.get(k)[i - 1][j].isBrick){
                            destroyCastle(castle.get(k)[i - 1][j]);
                            castle.get(k)[i][j].isBrick = false;
                        }
                        if((j + 1 < Castle.nbColumns && i - 1 > 0) && castle.get(k)[i - 1][j + 1].isBrick){
                            destroyCastle(castle.get(k)[i - 1][j + 1]);
                            castle.get(k)[i][j].isBrick = false;
                        }
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
            if (! kernel.isCollideLeft(saucer.x + saucer.getSpeed()))
                kernel.move(saucer, "right");

            if (kernel.isCollideRight(widthWorld, saucer.widthEntity, saucer.x + saucer.getSpeed())){
                killSauser(saucer);
            }
        }
    }

    public void killSauser(Saucer saucer){
        kernel.erase(saucer);
        entitiesGame.remove(saucer);
        kernel.entities.remove(saucer);
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
            if (paths.size() > 2) {
                System.out.println(paths.get(2));
                entity.setImage(paths.get(2));
            }

            else entity.setImage(paths.get(1));
            try {Thread.sleep(30);}
            catch (InterruptedException e) {}
        }
    }

    public void initMenuView() throws IOException {
        menuView = kernel.menuViewParams();
    }

    public void initWinView(int score ) throws IOException {
        winView = kernel.winViewParams(score);
    }
    public void initLooseView(int score ) throws IOException {
        looseView = kernel.looseViewParams(score);
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

