package engines.physics;


import engines.graphics.GraphicsUtilities;
import sample.MyJavaFrame;

import java.awt.*;

import static engines.physics.PhysicsUtilities.*;

public class Entity {




    Point position = new Point();
    int vitesse = 0;

    int x;
    int y;
    int width;
    int height;


    Direction direction;


    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Entity() {

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void up() {
        position.move(0, vitesse);
    }

    public void down() {
        position.move(0, -vitesse);

    }

    public void right() {
        position.move(vitesse, 0);

    }

    public void left() {
        position.move(-vitesse, 0);

    }

    public void move(int movement) {
        int newX = x;
        int newY = y;
        if (movement == PhysicsUtilities.UP)
            newY -= Math.min(STEP, y);
        else if (movement == DOWN)
            newY += Math.min(STEP, MyJavaFrame.SCENE_HEIGHT - GraphicsUtilities.IMAGE_HEIGHT - y);
        moveTo(x, newY);

        if (movement == LEFT)
            newX -= Math.min(STEP, x);
        else if (movement == RIGHT)
            newX += Math.min(STEP, MyJavaFrame.SCENE_WIDTH - GraphicsUtilities.IMAGE_WIDTH - x);
        moveTo(newX, y);
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }


}
