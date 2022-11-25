package engines.physics;

import engines.kernel.Entity;

import java.awt.*;

public class PhysicalEngine {


    public PhysicalEngine() {

    }


    public void movable(Entity entity,int x , int y) {
        System.out.println("enntreeddd");
        entity.setPhysicalPositions(x, y);
        System.out.println(entity.physicalObject.hashCode());

    }
}
