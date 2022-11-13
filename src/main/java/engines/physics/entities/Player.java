package engines.physics.entities;

import  engines.physics.Direction;


public class Player extends PhysicalEntity {

    public Player(int x, int y) {
        super(x, y);
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



    


}
