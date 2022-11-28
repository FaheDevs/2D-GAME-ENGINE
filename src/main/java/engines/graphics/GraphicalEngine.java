package engines.graphics;

import api.SwingRenderer;
import engines.kernel.Entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class GraphicalEngine   {


    public GraphicalEngine() {
   }




    public Scene generateScene(int height, int width) {
        return new Scene(this, height, width);
    }


    public void setSceneBackgroundColor(Scene scene, int r, int g, int b) {
        scene.setBackgroundColor(new Color(r,g,b));
    }


    public void refreshWindow() { Window.refresh(); }



    public void paint(Entity entity) {
        GraphicalObject graphicEntity = entity.getGraphicalObject();
        cover(graphicEntity);


    }


    public void cover (GraphicalObject graphicalObject){
        SwingRenderer.getInstance()
                .renderImage(graphicalObject.getImage(),
                        graphicalObject.position.x,
                        graphicalObject.position.y);
    }



    public void addToScene(Scene scene, Entity entity) {
        scene.addEntity(entity);
    }

    public void showWindow() {
        Window.show();
    }

    public void bindScene(Scene scene) {
        Window.bindScene(scene);
    }

    public void setSceneLocation(Scene scene, int x, int y) {
        scene.setLocation(x, y);
    }
    public void setSceneSize(Scene scene, int height, int width) {
        scene.setSize(height, width);
    }

    public void addToCurrentScene(Entity entity) {
        Window.getActualScene().addEntity(entity);
    }

    public void erase(Entity entity) {
        Scene scene = entity.getGraphicalObject().scene;
        if (scene != null)
            scene.removeEntity(entity);
    }


}
