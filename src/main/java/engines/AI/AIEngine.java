package engines.AI;

import engines.graphics.Scene;
import engines.kernel.Entity;
import engines.physics.CollisionTools;
import gamePlay.GamePlay;

import java.util.ArrayList;

public class AIEngine {
    public void movable(Entity entity, int x , int y) {
        entity.setPhysicalPositions(x, y);
    }
    public void move(Entity entity, GamePlay.MoveDirection direction){
        int newX, newY;
        if (direction == GamePlay.MoveDirection.LEFT) {
            newX = entity.aiObject.x - entity.aiObject.speed;
            entity.setAiObjectPositions(newX, entity.aiObject.y);
        }
        if (direction == GamePlay.MoveDirection.RIGHT) {
            newX = entity.aiObject.x + entity.aiObject.speed;
            entity.setAiObjectPositions(newX, entity.aiObject.y);
        }
        if (direction == GamePlay.MoveDirection.UP) {
            newY = entity.aiObject.y - 20*entity.aiObject.speed;
            entity.setAiObjectPositions(entity.aiObject.x, newY);

        }
        if (direction == GamePlay.MoveDirection.DOWN) {
            newY = entity.aiObject.y + entity.aiObject.speed;
            entity.setAiObjectPositions(entity.aiObject.x, newY);

        }
    }
}
