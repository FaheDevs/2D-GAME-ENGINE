package engines.physics.entities;

import engines.physics.Direction;

import java.awt.Point;

public class Balles extends PhysicalEntity {

    public Balles(Point position){
        super(position);
    }
    public Balles(int x , int y  ){
        super(new Point(x,y));
    }

    @Override
    public void move(Direction direction) {

    }
}
