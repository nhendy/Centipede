package centipede;

import centipede.Input.*;
import centipede.gamecore.*;
import centipede.graphics.*;
import centipede.objects.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Game extends GameCore {

    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        new Game().run();

    }

    /**
     * 
     */
    public void init() {
        super.init();
        Window window = screen.getFullScreenWindow();
        inputManager = new InputManager(window);
        _missiles = new ArrayList<Missile>();
        _mushrooms = new ArrayList<Mushroom>();
        _centipedes = new ArrayList<Centipede>();
        _spiders = new ArrayList<Spider>();

        // use these lines for relative mouse mode
        // inputManager.setRelativeMouseMode(true);
        // inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        loadImages();
        createGameActions();
        createPlayer();
        createMushrooms();
        createCentipede();

        paused = false;
    }

    /**
     * 
     */
    public void draw(Graphics2D g) {
        // draw background
        g.drawImage(_bgImg, 0, 0, null);

        // draw player
        g.drawImage(_player.getImage(), Math.round(_player.getX()), Math.round(_player.getY()), null);

        // g.drawString("Aliens left: " , 5, 50);

        // draw missiles
        for (Missile m : _missiles) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), Math.round(m.getX()), Math.round(m.getY()), null);
            }
        }

        // draw mushrooms
        for (Mushroom m : _mushrooms) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), Math.round(m.getX()), Math.round(m.getY()), null);
            }
        }

        // draw centipedes
        for (Centipede c : _centipedes) {
            if (c.isVisible()) {
                g.drawImage(c.getImage(), Math.round(c.getX()), Math.round(c.getY()), null);
            }
        }

        g.setColor(Color.white);
        g.drawString("Player Area ends here" , 5, (int) ((screen.getHeight() - _player.getHeight()) * (1 - MAX_Y_PCT)));
        
    }

    /**
     * Input manager for mouse movements
     */
    protected InputManager inputManager;

    /**
     * Game objects
     */
    private Player _player;
    private ArrayList<Mushroom> _mushrooms;
    private ArrayList<Centipede> _centipedes;
    private ArrayList<Spider> _spiders;
    private ArrayList<Missile> _missiles;

    /**
     * Constants
     */
    private static final int NUM_MUSHROOMS = 20;
    private static final float MAX_Y_PCT = 0.05f;
    private int numSegments = 5;



    /**
     * background image
     */
    private Image _bgImg;
    private Image _playerImg;
    private Image _centipedeImg;
    private Image _spiderImg;
    private Image _missileImg;
    private List<Image> _mushroomImgs;


    /**
     * Is the game paused or not
     */
    private boolean paused;

    /**
     * Game Actions associated with user input
     */
    protected GameAction exit;
    protected GameAction fire;
    protected GameAction moveLeft;
    protected GameAction moveRight;
    protected GameAction moveDown;
    protected GameAction moveUp;
    protected GameAction pause;


    public void loadImages(){
         // load images
        _bgImg        = loadImage("images/bgrd.png");
        _playerImg    = loadImage("images/craft.png");
        _centipedeImg = loadImage("images/centipede.png");
        _missileImg   = loadImage("images/missile.png");
        _spiderImg    = loadImage("images/Spider1.png");
        _mushroomImgs = Arrays.asList(loadImage("images/mushroom3.png"),
                                      loadImage("images/mushroom2.png"),
                                      loadImage("images/mushroom1.png"));


    }   
    /**
     * Tests whether the game is paused or not.
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the paused state.
     */
    public void setPaused(boolean p) {
        if (paused != p) {
            this.paused = p;
            inputManager.resetAllGameActions();
        }
    }

    /**
     * 
     */
    public void update(long elapsedTime) {
        // check input that can happen whether paused or not
        // checkSystemInput();

        if (!isPaused()) {
            // check game input
            checkGameInput();

            // update sprite
            updatePlayer(elapsedTime);

            // update missiles
            updateMissiles(elapsedTime);

            // update Mushrooms
            updateMushrooms(elapsedTime);

            // update centipedes
            updateCentipede(elapsedTime);

            // update spiders

            // check collisions
            checkCollisions();
        }

    }



    /**
     * 
     *  
     */
    public void updateCentipede(long elapsedTime){

        // for(Centipede centipede : _centipedes){
        //     centipede.update(elapsedTime);
        // }


        //head is at the end of the array
        // Centipede head = _centipedes.get(numSegments - 1);
        //default vY = 0
        // head.setVelocityY(0);
        // Rectangle r1   = head.getBounds();


        // for(Mushroom m: _mushrooms)
        // {
        //     Rectangle r2 = m.getBounds();
        //     if (r1.intersects(r2)) {
        //         // if(!head.isDirectionVertical()){
        //         head.hitMushroom();
        //         // System.out.printf("head is %s\n\n",head.getState());
        //         // }
        //     }
        // }

        
// 

        // Translate each segment except for the head to the position of the 
        // segment in front of it
        for(int i = 0; i < numSegments; i ++){

            
            // _centipedes.get(i).setNextState(_centipedes.get(i + 1).getState());
            // System.out.printf("seg %d is %s", i, _centipedes.get(i + 1).getState());
            // _centipedes.get(i).setVelocityY(_centipedes.get(i + 1).getVelocityY());
            // _centipedes.get(i).update(elapsedTime);
            // _centipedes.get(i).setVelocityX(_centipedes.get(i + 1).getVelocityX());
            // _centipedes.get(i).setVelocityY(_centipedes.get(i + 1).getVelocityY());
            Rectangle r1 = _centipedes.get(i).getBounds();

            for(Mushroom m: _mushrooms)
            {
                Rectangle r2 = m.getBounds();
                if (r1.intersects(r2)) {
                    // if(!head.isDirectionVertical()){
                    _centipedes.get(i).hitMushroom();
                    // System.out.printf("head is %s\n\n",head.getState());
                    // }
                }
            }

            _centipedes.get(i).update(elapsedTime);
        }
        // System.out.println();


        // head.update(elapsedTime);


        

        

       
        return;
    }

    public void updatePlayer(long elapsedTime){
        _player.update(elapsedTime);

    }


    public void updateMushrooms(long elapsedTime) {

        for (int i = 0; i < _mushrooms.size(); i++) {

            Mushroom mushroom = _mushrooms.get(i);

            if (mushroom.isVisible()) {

                mushroom.update(elapsedTime);
            } else {

                _mushrooms.remove(i);
            }
        }

    }



    public void updateMissiles(long elapsedTime) {

        for (int i = 0; i < _missiles.size(); i++) {

            Missile missile = _missiles.get(i);

            if (missile.isVisible()) {

                missile.update(elapsedTime);
            } else {

                _missiles.remove(i);
            }
        }

    }

    /**
     * Check if missiles collide with mushrooms Check if player collide with
     * mushrooms Check if player collide with spider Check if player collide with
     * centipede
     */
    public void checkCollisions() {

    

        // check if missile hit any mushroom
        // check if missile hit any centipede
        // check if missile hit any spider
        // check if player hit any mushroom
        // check if player hit any spider
        // check if player hit any centipede




        
        for (Missile m : _missiles) {

            Rectangle r1 = m.getBounds();

            for (Mushroom mushroom : _mushrooms) {

                Rectangle r2 = mushroom.getBounds();

                if (r1.intersects(r2)) {


                    // hit the mushroom
                    mushroom.hit();

                    // remove missile
                    m.disappear();

                    if (mushroom.getLives() > 0) {
                        changeMushroomImg(mushroom);
                    } else {
                        mushroom.disappear();
                    }

                }
            }



            for(Centipede centipede: _centipedes){
                Rectangle r2 = centipede.getBounds();
                if (r1.intersects(r2)) {


                    // hit the mushroom
                    // centipede.hit();

                    // remove missile
                    m.disappear();

                    //only remove the centipede
                    centipede.disappear();

                }


            }
        }
    }

    /**
     * 
     * @param mushroom
     */
    public void changeMushroomImg(Mushroom mushroom) {

        int lives = mushroom.getLives();

        // if (lives == 3) {
        //     newImg = loadImage("images/mushroom1.png");
        //     mushroom.getAnimation().setFrame(newImg, 0);
        // } else if (lives == 2) {
        //     newImg = loadImage("images/mushroom2.png");
        //     mushroom.getAnimation().setFrame(newImg, 0);
        // } else if (lives == 1) {
        //     newImg = _mushroomImgs.get(lives + 1);
        //     mushroom.getAnimation().setFrame(newImg, 0);
        // }

        if(lives != 0){
            Image newImg = _mushroomImgs.get(lives - 1);
            mushroom.getAnimation().setFrame(newImg, 0);
        }
        
    }

    /**
     * Checks input from GameActions that can be pressed only when the game is not
     * paused.
     */
    public void checkGameInput() {
        float velocityX = 0;
        float velocityY = 0;

        if (moveLeft.isPressed()) {
            velocityX = -1 * Player.SPEED;
        }
        if (moveRight.isPressed()) {
            velocityX = Player.SPEED;
        }

        if (moveDown.isPressed()) {
            velocityY = Player.SPEED;
        }
        if (moveUp.isPressed()) {
            velocityY = -1 * Player.SPEED;
        }
        _player.setVelocityX(velocityX);
        _player.setVelocityY(velocityY);

        if (fire.isPressed()) {
            createMissile();
        }

    }

    // ======================================== Creation
    // ================================== //

    public void createCentipede() {

        for (int i = 0; i < numSegments; i++) {

            Animation anim = new Animation();
            anim.addFrame(_centipedeImg, 250);
            Centipede centipede = new Centipede(anim);

            centipede.setBdims(screen.getWidth(), screen.getHeight());

            centipede.setY(centipede.getHeight() / 2);
            centipede.setX(screen.getWidth() - centipede.getWidth() * (i + 1));
            centipede.setMaxY((int) ((screen.getHeight() - _player.getHeight()) * (1 - MAX_Y_PCT)));

            _centipedes.add(centipede);
        }

    }

    public void createMushrooms() {

        // 1% of the screen height is for player
        int MAX_Y = (int) ((screen.getHeight() - _player.getHeight()) * (1 - MAX_Y_PCT));

        Image mushImg = _mushroomImgs.get(0);

        int DIM = Math.max(mushImg.getWidth(null), mushImg.getHeight(null));

        Set<Point> locations = new HashSet<Point>();
        Point sample;

        do {
            sample = new Point();

            // 20% of width not used
            sample.x = (int) (Math.random() * (screen.getWidth() - 2* DIM) + DIM);

            // starts from 10 up to MAX_Y - 10
            sample.y = (int) (Math.random() * (MAX_Y - 2 * DIM) + DIM);

            if (emptyPerimeter(locations, sample, DIM)) {
                locations.add(sample);
            }

            // System.out.println(locations);

        } while (locations.size() < NUM_MUSHROOMS);

        System.out.println(locations);

        for (Point location : locations) {

            Animation anim = new Animation();
            anim.addFrame(mushImg, 250);

            Mushroom mushroom = new Mushroom(anim);

            mushroom.setBdims(screen.getWidth(), screen.getHeight());
            mushroom.setY((float) location.getY());
            mushroom.setX((float) location.getX());

            _mushrooms.add(mushroom);

        }

    }

    /**
     * (x - 1.5DIM, y - 1.5DIM) *-------------------* (x + 1.5DIM, y - 1.5DIM)
     *                          |                   |
     *                          |                   |
     *                          |                   |
     *                          |       (x,y)       | 
     *                          |                   | 
     *                          |                   |  
     *                          |                   | 
     * (x - 1.5DIM, y + 1.5DIM) *-------------------* (x + 1.5DIM, y + 1.5DIM)
     *
     */
    public boolean emptyPerimeter(Set<Point> locations, Point sample, int dim) {

        double x = sample.x;
        double y = sample.y;

        System.out.println("DIM: " + dim);

        double minX = sample.x - 1 * dim;
        double maxX = sample.x + 1 * dim;

        double minY = sample.y - 1 * dim;
        double maxY = sample.y + 1 * dim;




        // System.out.printf("( %f, %f)  ----------------  (%f, %f)\n( %f, %f)  ----------------  (%f, %f)\n\n", 
                                                    // minX, minY, maxX, minY, minX, maxY, maxX, maxY);
        for (Point point : locations) {
            if (point.x > minX && point.x < maxX) {

                System.out.printf("%.2f < pointx: %d < %.2f\n", minX, point.x, maxX);
                return false;
            }
            if (point.y > minY && point.y < maxY) {

                System.out.printf("%.2f < pointy: %d < %.2f\n", minY, point.y, maxY);
                return false;
            }
        }

        return true;
    }

    public void createMissile() {

        Animation anim = new Animation();
        anim.addFrame(_missileImg, 250);

        Missile missile = new Missile(anim);
        _missiles.add(missile);

        missile.setX(_player.getX());
        missile.setY(_player.getY() + _player.getHeight() / 2);

        System.out.println("X: " + _player.getX() + " Y: " + _player.getY());

        missile.setBdims(screen.getWidth(), screen.getHeight());

    }

    /**
     * attach mouse clicks to game actions
     */
    public void createGameActions() {

        fire = new GameAction("fire", GameAction.DETECT_INITAL_PRESS_ONLY);

        exit = new GameAction("exit", GameAction.DETECT_INITAL_PRESS_ONLY);

        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        moveDown = new GameAction("moveDown");
        moveUp = new GameAction("moveUp");

        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);

        inputManager.mapToMouse(fire, InputManager.MOUSE_BUTTON_1);

        inputManager.mapToMouse(moveLeft, InputManager.MOUSE_MOVE_LEFT);
        inputManager.mapToMouse(moveRight, InputManager.MOUSE_MOVE_RIGHT);
        inputManager.mapToMouse(moveUp, InputManager.MOUSE_MOVE_UP);
        inputManager.mapToMouse(moveDown, InputManager.MOUSE_MOVE_DOWN);
    }

    /**
     * Load Images.
     */
    private void createPlayer() {
       
        Animation anim = new Animation();
        anim.addFrame(_playerImg, 250);

        _player = new Player(anim);
        _player.setBdims(screen.getWidth(), screen.getHeight());
        _player.setY(screen.getHeight() - _player.getHeight());

    }

}