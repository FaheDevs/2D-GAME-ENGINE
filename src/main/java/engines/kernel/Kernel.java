package engines.kernel;


import engines.AI.AIEngine;
import engines.AI.AIObject;
import engines.command.CommandEngine;
import engines.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.graphics.GraphicalObject;
import engines.graphics.Scene;
import engines.physics.*;
import gamePlay.Castle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Kernel implements Observer {

    private GraphicalEngine graphicalEngine;

    private PhysicalEngine physicalEngine;

    private CommandEngine commandEngine;

    private AIEngine aiEngine;

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



    public void start(){
        graphicalEngine.showWindow();
    }

    public Scene generateScene(int height, int width) {
        return graphicalEngine.generateScene(height, width);
    }

    public void bindScene(Scene scene) {
        graphicalEngine.bindScene(scene);
    }

    public void addToScene(Scene world, Entity graphicalObject) {
        graphicalEngine.addToScene(world,graphicalObject.graphicalObject);

    }

    public void paintRectangle(Entity entity, Color color, int height, int width){
        entity.graphicalObject.paintRectangle(entity.x, entity.y, height, width, color);
    }
    public Entity creatEntityToDrow(int x, int y, Color color, Scene scene){
        Entity entity = new Entity(new GraphicalObject(new Point(x, y)));
        entity.setGraphicalPositions(x, y);
        entity.graphicalObject.color = color;
        entities.add(entity);
        graphicalEngine.addToScene(scene, entity.graphicalObject);
        return entity;
    }
    public void afficheTexte(Entity entity, String texte) {
        entity.graphicalObject.setAfficheTexte(texte);
    }

    public void rePaintRectangle(Castle castle, Color color) {
        castle.graphicalObject.rePaintRectangle(color);
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


    public void move(Entity entity, String direction) {
        int[] newPos = {entity.x, entity.y};
        if(entity.type == Entity.Type.Physical){
            if (entity.physicalObject != null && physicalEngine.move(entity.physicalObject, direction) != null) {
                newPos = physicalEngine.move(entity.physicalObject, direction);
                entity.setPyhsicalObjectPositions(newPos[0], newPos[1]);
            }
        }
        else{
            if (entity.aiObject != null && aiEngine.move(entity.aiObject, direction) != null)
                newPos = aiEngine.move(entity.aiObject, direction);
            entity.setAiObjectPositions(newPos[0], newPos[1]);
        }

    }

    public void move(Entity saucer) {
        int[] newPos = {saucer.x, saucer.y};
        if (saucer.physicalObject != null && !saucer.killed) {
            newPos = physicalEngine.move(saucer.physicalObject, "right");
            saucer.setPyhsicalObjectPositions(newPos[0], newPos[1]);
        }
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

    public boolean isCollideWithLefdboard(List<List<Entity>> entities){
        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.get(i).size(); j++) {
                if (entities.get(i).get(j) != null && !isCollideLeft(entities.get(i).get(j).x - entities.get(i).get(j).getAiObject().speed)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollideWithRightdboard(List<List<Entity>> entities, int widthW){
        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.get(i).size(); j++) {
                if (entities.get(i).get(j) != null && !isCollideRight(widthW, entities.get(i).get(j).widthEntity, entities.get(i).get(j).x + entities.get(i).get(j).getAiObject().speed)){
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
