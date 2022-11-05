package engines.physics;


import engines.graphics.GraphicsUtilities;
import sample.MyJavaFrame;

import java.awt.*;

import static engines.physics.PhysicsUtilities.*;

public class PhysicalEntity {


    public Point position;
    int x;
    int y;



    public PhysicalEntity(int x, int y) {
        this.x = x;
        this.y = y;
        this.position = new Point(x, y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void move(int movement) {
        int newX = x;
        int newY = y;
        if (movement == PhysicsUtilities.UP)
            newY -= SPEED;
        else if (movement == DOWN)
            newY += SPEED;
        moveTo(x, newY);

        if (movement == LEFT)
            newX -= SPEED;
        else if (movement == RIGHT)
            newX += SPEED;
        moveTo(newX, y);
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
        this.position.setLocation(x, y);
    }


}
