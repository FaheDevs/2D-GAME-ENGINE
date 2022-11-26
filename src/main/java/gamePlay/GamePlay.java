package gamePlay;

import engines.graphics.GraphicalEngine;
import engines.graphics.GraphicalObject;
import engines.graphics.Scene;
import engines.kernel.Entity;
import engines.kernel.Kernel;
import engines.kernel.Subject;
import engines.physics.PhysicalEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GamePlay implements Runnable {

    Kernel kernel ;

    Thread gameThread ;

    Entity player;
    ArrayList<Entity> entitiesGame ;



    public enum MoveDirection {
        RIGHT,LEFT
    }

    public GamePlay() throws IOException {
        entitiesGame = new ArrayList<>();
        kernel = new Kernel();
        initEntities();

        Scene menu = kernel.graphicalEngine.generateScene(600,800);
        // je bind la scene au Jframe
        kernel.graphicalEngine.bindScene(menu);
        // je rajoute un objet a la scene
        kernel.graphicalEngine.addToScene(menu,entitiesGame.get(0));
        // j'affiche la scene
        kernel.start();

    }

    public void initEntities() throws IOException {
        BufferedImage image;
        image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/00.png"));
        GraphicalObject graphicalObject = new GraphicalObject(image,"spaceCraft ");

//        player = new Entity(graphicalObject);

        player = new Entity(graphicalObject);

        initEntity(player);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void initEntity(Entity entity){
        entity.register(kernel);
        kernel.setSubject(entity);
        entitiesGame.add(entity);
    }

    public void move(Entity entity,MoveDirection direction){
        int newX;
        if(direction == MoveDirection.LEFT){
            newX = entity.physicalObject.x - 2;
            entity.setPositions(newX, entity.physicalObject.y);
        }
        if(direction == MoveDirection.RIGHT){
             newX = entity.physicalObject.x + 2;
            entity.setPositions(newX , entity.physicalObject.y);
        }



    }



    public Kernel getKernel() {
        return kernel;
    }

    public ArrayList<Subject> getEntities(){
        return kernel.entities;
    }

    public GraphicalEngine graphicalEngine(){
        return kernel.graphicalEngine;
    }

    public PhysicalEngine physicalEngine(){
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
                update();
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

    public void update() {
        if (kernel.commandEngine.keyHandler.leftPressed) {
            move(player,MoveDirection.LEFT);
        }

        if (kernel.commandEngine.keyHandler.rightPressed) {
            move(player,MoveDirection.RIGHT);
        }



    }


    public static void main(String[] args) throws IOException {

        GamePlay game = new GamePlay();
        game.startGameThread();

        System.out.println(game.kernel.entities);

//        Entity player = game.entitiesGame.get(0);



//        Scanner scanner = new Scanner(System.in);
//        String input ;
//        while (true) {
//            input = scanner.nextLine();
//            if (Objects.equals(input, "L"))
//                game.move(player,MoveDirection.LEFT);
//            if (Objects.equals(input, "R"))
//                game.move(player,MoveDirection.RIGHT);
//
//            if (Objects.equals(input, "Q"))
//                game.move(monster,MoveDirection.LEFT);
//
//            if (Objects.equals(input, "D"))
//                game.move(monster,MoveDirection.RIGHT);
//
//            System.out.println("Monster position : x : "+ monster.x + " y : " + monster.y  );
//            System.out.println("Player position : x : "+ player.x + " y : " + player.y  );
//
//        Thread gameThread = new Thread();
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        }
//

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

