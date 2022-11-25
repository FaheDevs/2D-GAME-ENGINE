package engines.physics;

import engines.kernel.Entity;

import java.awt.*;

public class PhysicalEngine {


    public PhysicalEngine() {

    }


    public void movable(Entity entity,int x , int y) {
        entity.setPhysicalPositions(x, y);

    }
}
