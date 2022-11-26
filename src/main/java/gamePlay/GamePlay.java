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

public class GamePlay {

    Kernel kernel ;

    Player player;
    ArrayList<Entity> entitiesGame ;

    public enum MoveDirection {
        RIGHT,LEFT
    }

    public GamePlay(){
        entitiesGame = new ArrayList<>();
        kernel = new Kernel();
        initEntities();
    }

    public void initEntities(){

        player = new Player(kernel);

        Entity monster = new Entity();

        initEntity(monster);

    }

    public void initEntity(Entity entity){
        entity.register(kernel);
        kernel.setSubject(entity);
        entitiesGame.add(entity);
    }

    public void move(Entity entity,MoveDirection direction){
        int newX;
        if(direction == MoveDirection.LEFT){
            newX = entity.x - 2;
            entity.move(newX, entity.y);
        }
        if(direction == MoveDirection.RIGHT){
             newX = entity.x + 2;
            entity.move(newX , entity.y);
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

    public static void main(String[] args) throws IOException {

//        GamePlay game = new GamePlay();
//
//        System.out.println(game.kernel.entities);
//
//        Entity player = game.entitiesGame.get(0);
//
//        Entity monster =game.entitiesGame.get(1);
//
//
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


        Kernel kernel1 = new Kernel();
        BufferedImage image;
        image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/00.png"));
        GraphicalObject graphicalObject = new GraphicalObject(image,"spaceCraft ");
        Entity palyer3 = new Entity(graphicalObject);
        palyer3.setGraphicalPositions(400,400);

        Scene menu = kernel1.graphicalEngine.generateScene(600,800);
        kernel1.graphicalEngine.bindScene(menu);
        kernel1.graphicalEngine.addToScene(menu,palyer3);
        kernel1.graphicalEngine.refreshWindow();
        kernel1.start();






    }
    }

