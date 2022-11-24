package engines.graphics;

import kernel.EventListener;

/**
 * Évènements de graphismes
 */
public interface GraphicEvent {
    /**
     * Notifier une collision
     * @param event évènement
     */
    void notifyEvent(String event);

    void notifyEntityUpdate(GraphicalEntity entity);

    /**
     * S'abonner aux évènements
     * @param listener écouteur
     */
    void subscribeEvents(EventListener listener);
}
