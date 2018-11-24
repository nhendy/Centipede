package centipede.objects;

import centipede.graphics.*;


public class Centipede extends Sprite {


    private boolean isVisible = true;


    public Centipede(Animation anim){
        super(anim);
        setVelocityX(-1);
    }



    //TODO
    /**
        Updates this Sprite's Animation and its position based
        on the velocity.
        makes sure it's within the bounds
    */
    @Override
    public void update(long elapsedTime) {

        float tmpX = getX() + getVelocityX() * elapsedTime;
        float tmpY = getY() + getVelocityY() * elapsedTime;


        System.out.printf("tmpx : %.2f tmpY: %.2f", tmpX, tmpY);

        float newX = tmpX < 0? 0: tmpX >= B_WIDTH - getWidth()? B_WIDTH - getWidth() : tmpX; 
        float newY = tmpY <= 0? 0: tmpY >= B_HEIGHT - getHeight()? B_HEIGHT - getHeight(): tmpY; 

        setX(newX);
        setY(newY);

        System.out.println("X: " + getX() + " Y: " + getY());
        super.anim.update(elapsedTime);
    }



    public void disappear(){
        isVisible = false;
    }


    public boolean isVisible(){
        return isVisible;
    }

}
