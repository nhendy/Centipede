package centipede.objects;

import centipede.graphics.*;

public class Spider extends Sprite {


    private int numLives = 2;
    private boolean isVisible = true;



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


}
