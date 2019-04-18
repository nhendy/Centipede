package centipede.objects;

import centipede.graphics.*;
import java.util.Random;

public class Spider extends Sprite {


    private int numLives = 2;
    private boolean isVisible = true;
    private float MAX_Y;

    private static final float SPEED = 5f;

    private int [] directions = {-1 , 1, 0};


    public Spider (Animation anim){
        super(anim);
    }

    public void hit(){
        numLives --;
    }

    public int getLives(){
        return numLives;
    }

    public void disappear(){
        isVisible = false;
    }

    public boolean isVisible(){
        return isVisible;
    }


    public void restoreHealth(){
        numLives = 2;
    }


    public void setMaxY(float y) {
        MAX_Y = y;
    }


    /**
     * Updates this Sprite's Animation and its position based on the velocity. makes
     * sure it's within the bounds
     */
    @Override
    public void update(long elapsedTime) {

        Random randomizer = new Random();
        int directionX     = directions[randomizer.nextInt(3)];
        int directionY     = directions[randomizer.nextInt(3)];
        
        setVelocityX(directionX * SPEED);
        setVelocityY(directionY * SPEED);
        

        float tmpX = getX() + getVelocityX();
        float tmpY = getY() + getVelocityY();

        // System.out.printf("tmpx : %.2f tmpY: %.2f\n", tmpX, tmpY);

        float newX = tmpX <= 0 ? 0 : tmpX >= B_WIDTH - getWidth() ? B_WIDTH - getWidth() : tmpX;
        float newY = tmpY <= 0 ? 0 : tmpY >= MAX_Y - getHeight() ? MAX_Y - getHeight() : tmpY;

        setX(newX);
        setY(newY);


        super.anim.update(elapsedTime);
    }


}
