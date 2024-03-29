package centipede.objects;

import centipede.graphics.*;
import centipede.Input.*;

public class Player extends Sprite {


    private int livesLeft = 3;
    
    public static final float SPEED = 5f;

   



    public Player(Animation anim)
    {
        super(anim);


    }

    /**
        Updates this Sprite's Animation and its position based
        on the velocity.
        makes sure it's within the bounds
    */
    @Override
    public void update(long elapsedTime) {

        float tmpX = getX() + getVelocityX()  ;
        float tmpY = getY() + getVelocityY()  ;

        float newX = tmpX <= 0? 0: tmpX >= B_WIDTH - getWidth()? B_WIDTH - getWidth() : tmpX; 
        float newY = tmpY <= 0? 0: tmpY >= B_HEIGHT - getHeight()? B_HEIGHT - getHeight(): tmpY; 

        setX(newX);
        setY(newY);

        // System.out.println("X: " + getX() + " Y: " + getY());
        super.anim.update(elapsedTime);
    }


    public void inflictDamage(){
        livesLeft -- ;
    }


    public int getLives(){
        return livesLeft;
    }


    
}
