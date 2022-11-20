package engines.AI;

import kernel.Direction;
import kernel.Entity;

import java.awt.*;

public class AIEntity extends Entity {
    public Direction direction;
    public AIEntity(int x, int y) {
        super(x, y);
        speed = 1;
        direction = Direction.LEFT;
    }
    public AIEntity(Point point) {
        super(point);
        speed = 1;
        direction = Direction.LEFT;
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
        moveTo(newX, newY);
    }

    public Direction getDirection (){ return direction;}
}
