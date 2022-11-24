package kernel;

public interface EventListener {

    void onEvent(String event);

    /**
     * Notifier un évènement sur une entité
     * @param entity entité
     * @param eventName nom de l'évènement
     */
    void onEntityEvent(Entity entity, String eventName);

    /**
     * Notifier une mise à jour d'entité
     * @param entity entité
     */
    void onEntityUpdate(EngineEntity entity);
}
