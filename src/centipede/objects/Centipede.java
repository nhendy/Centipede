package centipede.objects;

import centipede.graphics.*;

public class Centipede extends Sprite {

    private boolean isVisible = true;
    private static final float SPEED = 10f;

    private int  lives = 2;

    private int direction = -1;
    private float MAX_Y;

    private enum State {
        MOVING_HORIZ, MOVING_VERITCAL;
    }

    private State state = State.MOVING_HORIZ;
    private State nextState = State.MOVING_HORIZ;

    public Centipede(Animation anim) {
        super(anim);
        setVelocityX(direction * SPEED);
    }

    /**
     * Updates this Sprite's Animation and its position based on the velocity. makes
     * sure it's within the bounds
     */
    @Override
    public void update(long elapsedTime) {

        if(getState() == State.MOVING_VERITCAL && nextState == State.MOVING_HORIZ){
            direction = -1 * direction;
        }

        state = nextState;

        switch (state) {
        case MOVING_HORIZ:
            moveHorizontal();
            break;
        case MOVING_VERITCAL:
            moveVertical();
            break;

        }

        float tmpX = getX() + getVelocityX();
        float tmpY = getY() + getVelocityY();

        // System.out.printf("tmpx : %.2f tmpY: %.2f\n", tmpX, tmpY);

        float newX = tmpX <= 0 ? 0 : tmpX >= B_WIDTH - getWidth() ? B_WIDTH - getWidth() : tmpX;
        float newY = tmpY <= 0 ? 0 : tmpY >= MAX_Y - getHeight() ? MAX_Y - getHeight() : tmpY;

        setX(newX);
        setY(newY);

        switch (state) {
        case MOVING_HORIZ:
            if (getX() == 0 || getX() == B_WIDTH - getWidth()) {
                setNextState(State.MOVING_VERITCAL);
            }
            break;
        case MOVING_VERITCAL:
            setNextState(State.MOVING_HORIZ);
            break;

        }

        super.anim.update(elapsedTime);
    }


    public boolean isDirectionVertical(){
        return getState() == State.MOVING_VERITCAL;
    }

    public void setNextState(State state) {
        this.nextState = state;
    }

    public void setMaxY(float y) {
        MAX_Y = y;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void moveVertical() {
        setVelocityY(SPEED);
        setVelocityX(0);
    }

    public void moveHorizontal() {
        setVelocityY(0);
        setVelocityX(direction * SPEED);
    }

    public void hitMushroom() {
        setNextState(State.MOVING_VERITCAL);
    }


    public void disappear() {
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }


    public void hit(){
        lives -- ;
    }

    public int getLives(){
        return lives;
    }
}
