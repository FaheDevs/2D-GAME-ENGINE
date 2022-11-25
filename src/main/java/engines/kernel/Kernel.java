package engines.kernel;


import engines.graphics.GraphicalEngine;
import engines.physics.*;

import java.io.IOException;


public class Kernel implements Observer {

    public GraphicalEngine graphicalEngine;

    public PhysicalEngine physicalEngine;

    public Subject[] entities;
    int index = 0;


    public Kernel() {
        this.entities = new Subject[10];
        graphicalEngine = new GraphicalEngine();
        physicalEngine = new PhysicalEngine();
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
        entities[index++] = sub;

    }

}
