package engines.graphics;

import api.SwingScene;
import engines.kernel.Entity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Scène
 */
public class Scene extends SwingScene {
    /**
     * Moteur graphique
     */
    private final GraphicalEngine graphicsEngine;

    /**
     * Liste des entités présentes dans la scène
     */
    private final ArrayList<Entity> entities = new ArrayList<>();

    /**
     * Consctructeur par défaut
     * @param height hateur
     * @param width largeur
     */
    protected Scene(GraphicalEngine graphicsEngine, int height, int width) {
        super(height, width);
        this.graphicsEngine = graphicsEngine;
    }



    /**
     * Ajouter une entité à la scène
     * @param entity entité
     */
    protected void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
            entity.graphicalObject.setScene(this);
        }
    }



    /**
     * Supprimer une entité présente sur la scène
     * @param entity entité
     */
    protected void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Définir la couleur de fond
     * @param color couleur
     */
    protected void setBackgroundColor(Color color) {
        super.setBackgroundColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics = (Graphics2D) g.create();
        graphics.setColor(backgroundColor);
        graphics.fillRect(xLocation, yLocation, width, height);
        for (Entity entity : entities)
            graphicsEngine.paint(entity);
        graphics.dispose();
    }

    // GETTERS //

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public GraphicalEngine getGraphicsEngine() {
        return graphicsEngine;
    }
}
