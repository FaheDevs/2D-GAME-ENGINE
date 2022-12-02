package engines.kernel;


import engines.AI.AIEngine;
import engines.AI.AIObject;
import engines.command.CommandEngine;
import engines.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.graphics.GraphicalObject;
import engines.graphics.Scene;
import engines.physics.*;
import gamePlay.GamePlay;

import java.io.IOException;
import java.util.ArrayList;


public class Kernel implements Observer {

    private GraphicalEngine graphicalEngine;

    private PhysicalEngine physicalEngine;

    private CommandEngine commandEngine;

    private AIEngine aiEngine;

    private KeyHandler keyHandler;


    public ArrayList<Subject> entities;

    public Kernel() {
        this.entities = new ArrayList<>();
        graphicalEngine = new GraphicalEngine();
        physicalEngine = new PhysicalEngine();
        commandEngine = new CommandEngine();
        aiEngine = new AIEngine();
    }


    @Override
    public void updateEntities() {
        for (Subject entity : entities) {
            if (entity != null) {
                entity.getPhysicalUpdate(this);
                entity.getGraphicalUpdate(this);
                entity.getAiUpdate(this);
            }
        }
    }

    @Override
    public void setSubject(Subject sub) {
        entities.add(sub);

    }
    public Entity generateEntity(GraphicalObject graphicalObject) {
        return new Entity(graphicalObject);
    }

    public void removeEntity(Entity entity) {

    }




    public void start(){
        graphicalEngine.showWindow();
    }

    public Scene generateScene(int height, int width) {
        return graphicalEngine.generateScene(height, width);
    }

    public void bindScene(Scene world) {
        graphicalEngine.bindScene(world);
    }

    public void addToScene(Scene world, Entity graphicalObject) {
        graphicalEngine.addToScene(world,graphicalObject.graphicalObject);

    }

    public void enableKeyboardIO() {
        commandEngine.enableKeyboardIO();
    }

    public void erase(Entity entity) {
        graphicalEngine.erase(entity.graphicalObject);
    }

    public void refreshWindow() {
        graphicalEngine.refreshWindow();
    }

    public void isCollide(Entity player, int i, int y, ArrayList<Entity> entitiesGame, Scene world) {
        physicalEngine.isCollide(player,i,y,entitiesGame,world);
    }

    public void move(Entity player, GamePlay.MoveDirection direction) {
        if (player.physicalObject != null) physicalEngine.move(player, direction);
    }
    public void moveAliens(Entity alien, GamePlay.MoveDirection direction) {
        if (alien.aiObject != null) aiEngine.move(alien, direction);
    }
    public boolean isCollideLeft(Entity entity, int i, int y, Scene world) {
        return physicalEngine.isCollideLeft(entity,i,y,world);
    }

    public boolean isCollideRight(Entity entity, int i, int y, Scene world) {
        return physicalEngine.isCollideRight(entity, i, y, world);
    }

    public boolean collideObjectToObject(Entity entity, Entity entity1, int x, int i) {
        return physicalEngine.collideObjectToObject(entity, entity1, x, i);
    }
    public CommandEngine getCommandEngine() {
        return commandEngine;
    }
    public KeyHandler getKeyHandler() {
        return commandEngine.keyHandler;
    }
}
