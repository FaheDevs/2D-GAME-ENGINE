package engines.AI;

public class AIEngine {

    public void movable(AIObject entity, int x , int y) {
        entity.setPosition(x, y);
    }
    public int[] move(AIObject entity,String direction){
        int newX, newY;
        if (direction.equals("left")) {
            newX = entity.x - entity.speed;
            newY = entity.y;
            entity.setPosition(newX, newY);
            return new int[]{newX, newY};
        }
        if (direction.equals("right")) {
            newX = entity.x + entity.speed;
            newY = entity.y;
            entity.setPosition(newX, newY);
            return new int[]{newX, newY};
        }
        if (direction.equals("up")) {
            newY = entity.y - entity.speed;
            newX = entity.x;
            entity.setPosition(newX, newY);
            return new int[]{newX, newY};
        }
        if (direction.equals("down")) {
            newY = entity.y + 3 * entity.speed;
            newX = entity.x;
            entity.setPosition(newX, newY);
            return new int[]{newX, newY};
        }
        return null;
    }
}
