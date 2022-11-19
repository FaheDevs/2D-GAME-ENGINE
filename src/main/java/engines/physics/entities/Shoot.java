package engines.physics.entities;

import engines.graphics.GraphicsUtilities;
import engines.physics.Direction;

import java.awt.*;

public class Shoot {
    public boolean moveLeft;
    public boolean moveRight;

    public int x;
    public int y;

    public int speed;

    public boolean goUp;

    public Shoot(int x, int y) {
        this.x = x;
        this.y =  y;
        goUp = false;
        speed = 2;
    }
    public Shoot(Point positon) {
        this.x = positon.x;
        this.y =  positon.y;
        goUp = false;
        speed = 2;

    }

    public void setY(int y) {
        this.y = y;
    }

    public void move(int d) {
        if (y < 0) {
            goUp = false;
            setY(-100);
        }
        if (goUp)
            setY(y - speed);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setLeftRight(int d) {
        // d ->
        if (d == 37) {
            moveLeft = true;
        }

        if (d == 39) {
            moveRight = true;
        }

    }

    public void stop() {
        moveLeft = false;
        moveRight = false;

    }


}
