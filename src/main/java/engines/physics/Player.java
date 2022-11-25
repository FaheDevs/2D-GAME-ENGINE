package engines.physics;

import java.awt.*;

public class Player  {

    public boolean moveLeft = false;
    public boolean moveRight = false;
    Point positon;
    Rectangle solideArea = new Rectangle();

    boolean collision = false;



//    public Player(int x, int y) {
//        super(x, y);
//        speed = 1;
//        solideArea.setSize(32,32);
//        solideArea = new Rectangle(getPosition());
//
//    }


//    public Player(Point p) {
//        super(p.x, p.y);
//        speed = 1;
//        solideArea.setSize(32,32);
//        solideArea.setLocation(p);
//    }


//    public void setSpeed(int speed) {
//        this.speed = speed;
//    }

    public Point getPositon(){
        return positon;
    }


//    public void move(Direction direction) {
//        int newX = getX();
//        int newY = getY();
//        switch (direction) {
//            case UP -> newY -= speed;
//            case DOWN -> newY += speed;
//            case RIGHT -> newX += speed;
//            case LEFT -> newX -= speed;
//        }
//        TODO  CHECK IF MOVEMENT IS POSSIBLE
//        if (Collision.checkCollisionWorld(newX,newY) ) {
//            moveTo(newX, newY);
//        } else {
//            System.out.println("NOT POSSIBLE");
//        }
//    }
//


//    public void moveTo(int x, int y) {
//        this.positon.setLocation(x,y);
//        setX(x);
//        setY(y);
//
//    }

}

// deux tirs qui se rencontrent s'annulent
// une classe pour les monstres
// une classe pour les rochers
// une classe pour les vaisseaux
// une classe pour les balles

