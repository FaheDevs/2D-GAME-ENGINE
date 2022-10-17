package engines.physics;


import java.awt.Point;

public class Entity {
    Point position = new Point();
    int vitesse = 0;

    Direction direction;


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public Entity() {

    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void up() {
        position.move(0, vitesse);
    }

    public void down() {
        position.move(0, -vitesse);

    }

    public void right() {
        position.move(vitesse, 0);

    }

    public void left() {
        position.move(-vitesse, 0);

    }


    public void move(Direction direction) {
        switch (direction) {
            case UP -> {
                up();

            }
            case DOWN -> {
                down();

            }
            case RIGHT -> {
                right();

            }
            case LEFT -> {
                left();
            }

        }
    }


}
