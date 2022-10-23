package engines.physics;


import engines.graphics.GraphicsUtilities;
import sample.MyJavaFrame;

import static engines.physics.PhysicsUtilities.*;

public class Entity {


    int x;
    int y;
    int width;
    int height;


    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
            newY -= Math.min(SPEED, y);
        else if (movement == DOWN)
            newY += Math.min(SPEED, MyJavaFrame.SCENE_HEIGHT - GraphicsUtilities.IMAGE_HEIGHT - y);
        moveTo(x, newY);

        if (movement == LEFT)
            newX -= Math.min(SPEED, x);
        else if (movement == RIGHT)
            newX += Math.min(SPEED, MyJavaFrame.SCENE_WIDTH - GraphicsUtilities.IMAGE_WIDTH - x);
        moveTo(newX, y);
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }


}
