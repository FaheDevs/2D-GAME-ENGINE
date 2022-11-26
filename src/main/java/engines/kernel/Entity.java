package engines.kernel;

import engines.graphics.GraphicalObject;
import engines.physics.PhysicalObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Entity implements Subject{
//    protected Point position ;


    //
    public GraphicalObject graphicalObject;
    public PhysicalObject physicalObject;

    public int x ;

    public int y ;
    private List<Observer> observers;


    public String name;

    private boolean changed;

    private final Object MUTEX= new Object();


    public Entity(GraphicalObject graphicalObject, PhysicalObject physicalObject) {
        this.graphicalObject = graphicalObject;
        this.physicalObject = physicalObject;
        this.observers=new ArrayList<>();

    }

    public Entity(GraphicalObject graphicalObject){
        this.graphicalObject = graphicalObject;

        this.physicalObject = new PhysicalObject(0,0,0,0,0);
        this.observers=new ArrayList<>();

    }

    public Entity() {
        this.x =  0;
        this.y =  0;
        this.graphicalObject = new GraphicalObject("named",new Point(x,y));
        this.physicalObject = new PhysicalObject(x,y,0,0,0);
        this.observers=new ArrayList<>();

    }



    public GraphicalObject getGraphicalObject() {
        return graphicalObject;
    }

    public void setGraphicalObject(GraphicalObject graphicalObject) {
        this.graphicalObject = graphicalObject;
    }

    public PhysicalObject getPhysicalObject() {
        return physicalObject;
    }

    public void setPhysicalObject(PhysicalObject physicalObject) {
        this.physicalObject = physicalObject;
    }

    @Override
    public void register(Observer obj) {
        if(obj == null) throw new NullPointerException("Null Observer");
        synchronized (MUTEX) {
            if(!observers.contains(obj)) observers.add(obj);
        }
    }

    @Override
    public void unregister(Observer obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        List<Observer> observersLocal = null;
        //synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (MUTEX) {
            if (!changed)
                return;
            observersLocal = new ArrayList<>(this.observers);
            this.changed=false;
        }
        for (Observer obj : observersLocal) {
            obj.updateEntities();
            // y aura que le kernel
        }
    }

    @Override
    public String toString() {
        return "Entity{" +
                "graphicalObject=" + graphicalObject +
                ", physicalObject=" + physicalObject +
                '}';
    }

    @Override
    public PhysicalObject getPhysicalUpdate(Observer obj) {
        return this.physicalObject;
    }

    @Override
    public GraphicalObject getGraphicalUpdate(Observer obj) {
        return this.graphicalObject;
    }


    public void move(int x, int y){
        this.x =x ;
        this.y = y;
        notifyObservers();
    }

    public void setPhysicalPositions(int x,int y ){
        this.physicalObject.setPosition(new Point(x,y));
        notifyObservers();

    }

    public void setGraphicalPositions(int x , int y ){
        this.graphicalObject.setPosition(new Point(x,y));
        notifyObservers();
    }

    public void setPositions(int x , int y ){
        setPhysicalPositions(x, y);
        setGraphicalPositions(x, y);
    }

//
}


