package centipede.objects;

import centipede.graphics.*;
import centipede.Input.*;

public class Player extends Sprite {


    private int _livesLeft = 10;
    

    public static final float SPEED = 0.5f;

   



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

        float tmpX = getX() + getVelocityX() * elapsedTime;
        float tmpY = getY() + getVelocityY() * elapsedTime;

        float newX = tmpX <= 0? 0: tmpX >= B_WIDTH - getWidth()? B_WIDTH - getWidth() : tmpX; 
        float newY = tmpY <= 0? 0: tmpY >= B_HEIGHT - getHeight()? B_HEIGHT - getHeight(): tmpY; 

        setX(newX);
        setY(newY);

        // System.out.println("X: " + getX() + " Y: " + getY());
        super.anim.update(elapsedTime);
    }


    
}
