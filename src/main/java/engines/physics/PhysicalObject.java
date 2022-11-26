package engines.physics;

import java.awt.*;

public class PhysicalObject {


    public int x ;

    public int y ;

    public Point position;

    public String name;


    public Point getPosition() {
        return position;
    }

    public void setPosition(String name ,Point position) {
        this.name = name;
        this.position = position;
        this.x=position.x;
        this.y=position.y;
    }

    public int width;

    public int height;



    public int speed ;


    public PhysicalObject(String name, int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.position = new Point(x,y);
    }

    @Override
    public String toString() {
        return "PhysicalObject{" +
                "position=" + position +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
