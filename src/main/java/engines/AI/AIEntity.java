package engines.AI;

import engines.physics.entities.Collision;
import kernel.Direction;
import kernel.Entity;

import java.awt.*;

public class AIEntity extends Entity {
    public AIEntity(int x, int y) {
        super(x, y);
        speed = 1;
    }
    public AIEntity(Point point) {
        super(point);
        speed = 1;
    }


    @Override
    public void move(Direction direction) {
        int newX = getX();
        int newY = getY();
        switch (direction) {
            case UP -> newY -= speed;
            case DOWN -> newY += 20*speed;
            case RIGHT -> newX += speed;
            case LEFT -> newX -= speed;
        }
        if (Collision.checkCollisionWorld(newX,newY) ) {
            moveTo(newX, newY);
        } else {

            System.out.println("NOT POSSIBLE");
        }
    }

}
