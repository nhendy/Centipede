package centipede.objects;

import centipede.graphics.*;



public class Mushroom extends Sprite {


    private int numLives = 3;
    private boolean isVisible = true;

    public Mushroom(Animation anim)
    {
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

    



}
