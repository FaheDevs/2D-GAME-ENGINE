package engines.AI;

import java.awt.Point;

public class AIObject{
    public int x ;

    public int y ;

    public Point position;



    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        this.x=position.x;
        this.y=position.y;
    }

    public int width;

    public String name;

    public int height;



    public int speed ;

    public AIObject(){}
    public AIObject(String name, int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.position = new Point(x,y);
    }

    @Override
    public String toString() {
        return "AIObject{" +
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