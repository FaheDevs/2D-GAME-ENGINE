package engines.physics.entities;

import engines.physics.Direction;

import java.awt.Point;

public abstract class PhysicalEntity {
    protected Point position ;

    /** l'attribut pixelposition represente la position d'une entité (à partir du centre de l'objet + la taille/2)  */

    protected Point PixelPosition;

    protected int speed;

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




