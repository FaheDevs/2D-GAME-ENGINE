package engines.graphics;

import api.SwingRenderer;
import engines.kernel.Entity;
import java.awt.*;



/**
 * An extension of javax.swing.JFrame that can draw images.
 */
public class GraphicalEngine   {


    public GraphicalEngine() {
   }




    public Scene generateScene(int height, int width) {
        return new Scene(this, height, width);
    }


    public void setSceneBackgroundColor(Scene scene, int r, int g, int b) {
        scene.setBackgroundColor(new Color(r,g,b));
    }


    public void refreshWindow() {
        Window.refresh();
    }



    public void paint(GraphicalObject graphicEntity) {
        cover(graphicEntity);
    }


    public void cover (GraphicalObject graphicalObject){
        SwingRenderer.getInstance().renderImage(graphicalObject.getImage(),graphicalObject.position.x,graphicalObject.position.y);
    }

    public void paintRect(GraphicalObject graphicalentity){
        coverRect(graphicalentity);
    }



    public void coverRect(GraphicalObject graphicalObject){
        if (graphicalObject.getColoredRectangle() != null)
            SwingRenderer.getInstance().renderRect(graphicalObject.getColoredRectangle().height, graphicalObject.getColoredRectangle().width, graphicalObject.position.x, graphicalObject.position.y, graphicalObject.color);

    }

    public void addToScene(Scene scene, GraphicalObject entity) {
        scene.addEntity(entity);
    }

    public void showWindow() {
        Window.show();
    }

    public void bindScene(Scene scene) {
        Window.bindScene(scene);
    }

    public void movable(GraphicalObject graphicalObject , int x , int y  ){
        graphicalObject.setPosition(new Point(x, y));
    }

    public void erase(GraphicalObject entity) {

        Scene scene = entity.scene;
        if (scene != null)
            scene.removeEntity(entity);
    }
}
