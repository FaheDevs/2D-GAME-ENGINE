package engines.kernel;


import engines.command.CommandEngine;
import engines.command.KeyHandler;
import engines.graphics.GraphicalEngine;
import engines.graphics.GraphicalObject;
import engines.physics.*;

import java.io.IOException;
import java.util.ArrayList;


public class Kernel implements Observer {

    public GraphicalEngine graphicalEngine;

    public PhysicalEngine physicalEngine;

    public CommandEngine commandEngine;

    public KeyHandler keyHandler;


    public ArrayList<Subject> entities;

    public Kernel() {
        this.entities = new ArrayList<>();
        graphicalEngine = new GraphicalEngine();
        physicalEngine = new PhysicalEngine();
        commandEngine = new CommandEngine();
    }


    @Override
    public void updateEntities() {
        for (Subject entity : entities) {
            if (entity != null) {
                entity.getPhysicalUpdate(this);
                entity.getGraphicalUpdate(this);
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

}
