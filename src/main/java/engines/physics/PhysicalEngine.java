package engines.physics;

import kernel.EventListener;

import java.util.ArrayList;

public class PhysicalEngine implements CollisionEvent{




    private final ArrayList<EventListener> eventsListeners = new ArrayList<>();


    @Override
    public void notifyCollision(String event) {

    }

    @Override
    public void notifyEntityUpdate(PhysicalEntity entity) {
        for (EventListener listener : eventsListeners)
            listener.onEntityUpdate(entity);
    }

    @Override
    public void subscribeEvents(EventListener listener) {
        eventsListeners.add(listener);
    }
}
