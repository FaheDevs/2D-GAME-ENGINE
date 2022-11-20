package engines.physics.entities;

import engines.physics.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {

    public int x;
    public int y;
    public int speed;

    public boolean moveLeft = false;
    public boolean moveRight = false;
    Point positon;
    Rectangle solideArea = new Rectangle();

    boolean collision = false;



    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 1;
        positon = new Point(x,y);
        solideArea.setSize(32,32);
        solideArea = new Rectangle(positon);

    }


    public Player(Point p) {
        this.positon = p;
        this.x = p.x;
        this.y = p.y;
        speed = 1;
        solideArea.setSize(32,32);
        solideArea.setLocation(p);
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void move(Direction direction) {
        int newX = x;
        int newY = y;
        switch (direction) {
            case UP -> newY -= speed;
            case DOWN -> newY += speed;
            case RIGHT -> newX += speed;
            case LEFT -> newX -= speed;
        }
        //TODO  CHECK IF MOVEMENT IS POSSIBLE
        if (checkCollision(newX,newY)) {
            moveTo(newX, newY);
        } else {
            System.out.println("NOT POSSIBLE");
        }
    }

    public boolean checkCollision(int newX,int newY){
        return  !((newY >= 500 -32 || newY < 0) || (newX > 528 -64  || newX < 0));
        }

    public void moveTo(int x, int y) {
        this.positon.setLocation(x,y);
        this.x = x;
        this.y = y;

    }

}

// deux tirs qui se rencontrent s'annulent
// une classe pour les monstres
// une classe pour les rochers
// une classe pour les vaisseaux
// une classe pour les balles

