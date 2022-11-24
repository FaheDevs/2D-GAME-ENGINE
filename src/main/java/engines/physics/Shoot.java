package engines.physics;

import java.awt.*;

public class Shoot {
    public boolean moveLeft;
    public boolean moveRight;

     int x;
     int y;

    public int speed;

    public boolean goUp;

    public Point positon;

    public Shoot(int x, int y) {
        positon = new Point(x,y);
        this.x = x;
        this.y =  y;
        goUp = false;
        speed = 2;
    }
    public Shoot(Point positon) {
        this.positon = positon;
        this.x = positon.x;
        this.y =  positon.y;
        goUp = false;
        speed = 2;
    }

    public void setY(int y) {
        this.positon.y = y;
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
