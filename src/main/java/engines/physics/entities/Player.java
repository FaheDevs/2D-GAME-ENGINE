package engines.physics.entities;

import  engines.physics.Direction;

import java.awt.Point;


public class Player extends PhysicalEntity {
    public Player(int x, int y) {
        super(x, y);
        speed = 1;
    }
    public Player(Point p) {
        super(p.x, p.y);
        speed = 4;
    }

    @Override
    public void move(Direction direction) {
        int newX = getX();
        int newY = getY();
        switch (direction) {
            case UP -> newY -= speed;
            case DOWN -> newY += speed;
            case RIGHT -> newX += speed;
            case LEFT -> newX -= speed;
        }
        //TODO  CHECK IF MOVEMENT IS POSSIBLE
        if(!((newY >= 576  || newY < 0) || (newX > 576 || newX  < 0) )){
            moveTo(newX, newY);
        }
        else{
            System.out.println("NOT POSSIBLE");
        }
    }
    public void attack(){

    }

    // deux tirs qui se rencontrent s'annulent
    // une classe pour les monstres
    // une classe pour les rochers
    // une classe pour les vaisseaux
    // une classe pour les balles

}
