package engines.kernel;


import engines.AI.AIEngine;
import engines.AI.AIObject;
import engines.command.CommandEngine;
import engines.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.graphics.GraphicalObject;
import engines.graphics.Scene;
import engines.physics.*;

import java.util.ArrayList;
import java.util.List;


public class Kernel implements Observer {

    private GraphicalEngine graphicalEngine;

    private PhysicalEngine physicalEngine;

    private CommandEngine commandEngine;

    private AIEngine aiEngine;

    private KeyHandler keyHandler;


    public Scene world;


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
        this.world = world;
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

    public ArrayList<PhysicalObject> getPhyObjectsEntities(ArrayList<Entity> entities){
        ArrayList<PhysicalObject> physicalObjects = new ArrayList<>();
        for (Entity e : entities) {
            if(e.type == Entity.Type.Physical && e.physicalObject != null)
                 physicalObjects.add(e.physicalObject);
        }
        return physicalObjects;
    }

    public ArrayList<AIObject> getaiObjects(ArrayList<Entity> entities){
        ArrayList<AIObject> aiObjects = new ArrayList<>();
        for (Entity e : entities) {
            if(e.type == Entity.Type.Ai && e.aiObject != null)
                 aiObjects.add(e.aiObject);
        }
        return aiObjects;
    }

    public ArrayList<PhysicalObject> setaiObjectsPositionementPhy(ArrayList<AIObject> entities){
        ArrayList<PhysicalObject> aiObjectsPosi = new ArrayList<>();
        int i =0;
        for (AIObject e : entities) {
            aiObjectsPosi.add(new PhysicalObject("Virtuel Object"+i, e.x, e.y, e.width, e.height, e.speed));
            i++;
        }
        return aiObjectsPosi;
    }

    public void isCollide(int heightW, int widthW, Entity player, int newX, int newY, ArrayList<Entity> entitiesGame) {
        boolean whithPhy = physicalEngine.isCollide(player.physicalObject, newX, newY, getPhyObjectsEntities(entitiesGame));
        boolean whithAi = physicalEngine.isCollide(player.physicalObject, newX, newY, setaiObjectsPositionementPhy(getaiObjects(entitiesGame)));
        player.setCollision( (whithPhy || whithAi) || !physicalEngine.isCollide(heightW, widthW, player.physicalObject, newX, newY));
    }


    public void move(Entity player, String direction) {
        int newX = player.x;
        int newY = player.y;
        if (player.physicalObject != null && physicalEngine.move(player.physicalObject, direction) != null) {
            newX = physicalEngine.move(player.physicalObject, direction)[0];
            newY = physicalEngine.move(player.physicalObject, direction)[1];
            player.setPyhsicalObjectPositions(newX, newY);
        }
    }
    public void moveAliens(Entity alien, String direction) {
        int newX = alien.x;
        int newY = alien.y;
        if (alien.aiObject != null && aiEngine.move(alien.aiObject, direction) != null)
            newX = aiEngine.move(alien.aiObject, direction)[0];
            newY = aiEngine.move(alien.aiObject, direction)[1];
            alien.setAiObjectPositions(newX, newY);
    }
    public boolean isCollideLeft(int newX) {
        return physicalEngine.isCollideLeft(newX);
    }

    public boolean isCollideRight(int widthW, int widthObject, int newX) {
        return physicalEngine.isCollideRight(widthW, widthObject, newX);
    }

    public boolean collideObjectToObject(Entity entity, Entity entity1, int newX, int newY) {
        if(entity1.type == Entity.Type.Ai )
            return physicalEngine.collideObjectToObject(entity.physicalObject, entity1.aiObject.x, entity1.aiObject.y,
                entity1.aiObject.height, entity1.aiObject.width, newX, newY);

        else return physicalEngine.collideObjectToObject(entity.physicalObject, entity1.physicalObject.x, entity1.physicalObject.y,
                entity1.physicalObject.height, entity1.physicalObject.width, newX, newY);
    }

    public boolean isCollideWithLefdboard(List<List<Entity>> aliens){
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {
                if (aliens.get(i).get(j) != null && !isCollideLeft(aliens.get(i).get(j).x - aliens.get(i).get(j).getAiObject().speed)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollideWithRightdboard(List<List<Entity>> aliens, int widthW){
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).size(); j++) {
                if (aliens.get(i).get(j) != null && !isCollideRight(widthW, aliens.get(i).get(j).widthEntity, aliens.get(i).get(j).x + aliens.get(i).get(j).getAiObject().speed)){
                    return true;
                }
            }
        }
        return false;
    }
    public CommandEngine getCommandEngine() {
        return commandEngine;
    }
    public KeyHandler getKeyHandler() {
        return commandEngine.keyHandler;
    }
}
