package engines.physics.entities;

import engines.physics.Direction;

import java.awt.*;

public class Player {

    public int x;

    public int y;

    public int speed;

    public boolean moveLeft = false;
    public boolean moveRight = false;


    Point positon;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 1;
    }


    public Player(Point p) {
        this.positon = p;
        this.x = p.x;
        this.y = p.y;
        speed = 1;
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
        if (!((newY >= 576 || newY < 0) || (newX > 576 || newX < 0))) {
            moveTo(newX, newY);
        } else {
            System.out.println("NOT POSSIBLE");
        }
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

