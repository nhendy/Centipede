package centipede.objects;


import centipede.graphics.*;

public class Missile extends Sprite{



    private boolean _isVisible = true;



    public Missile(Animation anim)
    {
        super(anim);
        setVelocityY(-1);
        setVelocityX(0);
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

        _isVisible = tmpX <= 0? false: tmpX >= B_WIDTH - getWidth() ? false : true;  
        _isVisible = tmpY <= 0? false: tmpY >= B_HEIGHT - getHeight()? false : _isVisible; 


        // System.out.println("X : " + tmpX + " Y: " + tmpY + " visible: " + _isVisible);
        // System.out.println("vX : " + getVelocityX() + " vY: " + getVelocityY());
        // System.out.println("bH : " + B_HEIGHT);

        if(_isVisible) {
            setX(tmpX);
            setY(tmpY);
        }
        
        super.anim.update(elapsedTime);
    }

    public boolean isVisible()
    {
        return _isVisible;
    }


    

}