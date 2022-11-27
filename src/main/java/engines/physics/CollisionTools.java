package engines.physics;

import engines.graphics.Scene;
import engines.kernel.Entity;

import java.awt.Point;

public class CollisionTools {

    public static boolean checkCollisionWorld(Scene world, Entity entity, int newX, int newY){
        return  !((newY >= world.getHeight() - entity.heightEntity || newY < 0) || (newX > world.getWidth() - entity.widthEntity  || newX < 0));
    }
    public static boolean checkCollisionRight(Scene world, Entity entity, int newX, int newY){
        return  !(newX > world.getWidth() - entity.widthEntity);
    }
    public static boolean checkCollisionLeft(Scene world, Entity entity, int newX, int newY){
        return  !(newX < 0);
    }
    public static boolean checkCollisionObject(Entity E1, Entity E2){
        return  !((E1.y > E2.y + E2.heightEntity || E1.y < E2.y - E2.heightEntity)
                || (E1.x + E1.widthEntity < E2.x || E1.x > E2.x + E2.widthEntity));
    }

}
