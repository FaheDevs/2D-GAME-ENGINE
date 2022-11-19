package engines.physics.entities;

import engines.physics.Direction;

import java.awt.*;

public class NPC extends PhysicalEntity {
    public NPC(int x, int y) {
        super(x, y);
    }
    public NPC(Point point) {
        super(point);
    }

    @Override
    public void move(Direction direction) {

    }
}
