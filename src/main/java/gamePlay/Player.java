package gamePlay;


import engines.kernel.Entity;
import engines.kernel.Kernel;

public class Player extends Entity {


    public MoveDirection currentDirection ;


    public Player(Kernel kernel){
        register(kernel);
    }
    public Player(){
    }



    public void move(Entity entity, GamePlay.MoveDirection direction){
        int newX;
        if(direction == GamePlay.MoveDirection.LEFT){
            newX = entity.x - 2;
            entity.move(newX, entity.y);
        }
        if(direction == GamePlay.MoveDirection.RIGHT){
            newX = entity.x + 2;
            entity.move(newX , entity.y);
        }

    }






}
