package kernel;

import java.awt.Point;

public abstract class Entity {
    protected Point position ;


    protected Point PixelPosition;

    protected int speed;

    public Entity(Point position) {
        this.position = position;
    }

    public Entity(int x, int y) {
        this.position = new Point(x, y);
    }

    public Point getPosition(){
        return position;
    }
    public int getX() {
        return position.x;
    }
    public int getY(){
        return position.y;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    public void setX(int x ){
        this.position.x = x ;
    }
    public void setY(int y){
        this.position.y = y ;
    }

    public int getSpeed() {
        return speed;
    }

    public abstract void move(Direction direction);

    public  void moveTo(int x, int y){
        position.setLocation(x,y);
    }

}




