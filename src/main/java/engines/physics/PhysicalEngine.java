package engines.physics;

import engines.graphics.Scene;
import engines.kernel.Entity;
import gamePlay.GamePlay;
import gamePlay.Player;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhysicalEngine {


    public void movable(Entity entity,int x , int y) {
        entity.setPhysicalPositions(x, y);
    }

    public void isCollide(Entity entity, int newX, int newY, ArrayList<Entity> entitiesGame, Scene world) {
        entity.setCollision(this.isCollide(entity, newX, newY, entitiesGame) || !this.isCollide(entity, newX, newY, world));
    }

    public boolean isCollide(Entity entity, int newX, int newY, ArrayList<Entity> entitiesGame) {
        Entity tempEntity = new Entity(entity.heightEntity, entity.widthEntity, Entity.Type.Physical, 0);
        tempEntity.x = newX;
        tempEntity.y = newY;
        for (Entity e : entitiesGame) {
            if(e != entity && CollisionTools.checkCollisionObject(tempEntity, e)){
                return true;
            }
        }
        return false;
    }
    public boolean isCollide(Entity entity, int newX, int newY, Scene world) {
        return CollisionTools.checkCollisionWorld(world, entity, newX, newY);
    }
    public boolean isCollideRight(Entity entity, int newX, int newY, Scene world) {
        return CollisionTools.checkCollisionRight(world, entity, newX, newY);
    }
    public boolean isCollideLeft(Entity entity, int newX, int newY, Scene world) {
        return CollisionTools.checkCollisionLeft(world, entity, newX, newY);
    }
    public boolean collideObjectToObject(Entity entity, Entity entity1, int newX, int newY) {
        Entity tempEntity = new Entity(entity.heightEntity, entity.widthEntity, Entity.Type.Physical, 0);
        tempEntity.x = newX;
        tempEntity.y = newY;
        return CollisionTools.checkCollisionObject(tempEntity, entity1);
    }
    public void move(Entity entity, GamePlay.MoveDirection direction) {
        int newX,newY;
        if (direction == GamePlay.MoveDirection.LEFT) {
            newX = entity.physicalObject.x - entity.physicalObject.speed;
            entity.setPositions(newX, entity.physicalObject.y);
        }
        if (direction == GamePlay.MoveDirection.RIGHT) {
            newX = entity.physicalObject.x + entity.physicalObject.speed;
            entity.setPositions(newX, entity.physicalObject.y);
        }
        if (direction == GamePlay.MoveDirection.UP) {
            newY = entity.physicalObject.y - entity.physicalObject.speed;
            entity.setPositions(entity.physicalObject.x, newY);
        }
        if (direction == GamePlay.MoveDirection.DOWN) {
            newY = entity.physicalObject.y + entity.physicalObject.speed;
            entity.setPositions(entity.physicalObject.x, newY);
        }

    }
}
