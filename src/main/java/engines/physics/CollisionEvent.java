package engines.physics;


import kernel.EventListener;

/**
 * Évènements de collision
 */
public interface CollisionEvent {
    /**
     * Notifier une collision
     * @param event évènement
     */
    void notifyCollision(String event);

    /**
     * Notifier une mise à jour d'entité
     * @param entity entité
     */
    void notifyEntityUpdate(PhysicalEntity entity);

    /**
     * S'abonner aux évènements
     * @param listener écouteur
     */
    void subscribeEvents(EventListener listener);
}
