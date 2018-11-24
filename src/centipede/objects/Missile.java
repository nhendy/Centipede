package centipede.objects;


import centipede.graphics.*;

public class Missile extends Sprite{



    private boolean isVisible = true;



    public Missile(Animation anim)
    {
        super(anim);
        setVelocityY(-10);
        setVelocityX(0);
    }


    /**
        Updates this Sprite's Animation and its position based
        on the velocity.
        makes sure it's within the bounds
    */
    @Override
    public void update(long elapsedTime) {

        float tmpX = getX() + getVelocityX() ;
        float tmpY = getY() + getVelocityY() ;

        isVisible = tmpX < 0? false: tmpX >= B_WIDTH ? false : true;  
        isVisible = tmpY < 0? false: tmpY >= B_HEIGHT - getHeight()? false : isVisible; 


        // System.out.println("X : " + tmpX + " Y: " + tmpY + " visible: " + isVisible);
        // System.out.println("vX : " + getVelocityX() + " vY: " + getVelocityY());
        // System.out.println("bH : " + B_HEIGHT);

        if(isVisible) {
            setX(tmpX);
            setY(tmpY);
        }
        
        super.anim.update(elapsedTime);
    }


    public void disappear(){
        isVisible = false;
    }

    public boolean isVisible()
    {
        return isVisible;
    }


    

}