package engines.physics;

import java.awt.*;

public abstract class PhysicalEntity {
    private Point position = new Point();

    public PhysicalEntity(Point position) {
        this.position = position;
    }

    public PhysicalEntity(int x, int y) {
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

    public abstract void move(int x, int y);

    public  void moveTo(int x, int y){
        this.position.move(x,y);
    }
}




