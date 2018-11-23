package centipede;

import centipede.Input.*;
import centipede.gamecore.*;
import centipede.graphics.*;
import centipede.objects.*;
import java.awt.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        _spiders  = new ArrayList<Spider>();



        // use these lines for relative mouse mode
        // inputManager.setRelativeMouseMode(true);
        // inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        createGameActions();
        createPlayer();
        // createMushrooms();
        // paused = false;
    }

    /**
     * 
     */
    public void draw(Graphics2D g) {
        // draw background
        g.drawImage(_bgImage, 0, 0, null);

        // draw player
        g.drawImage(_player.getImage(), Math.round(_player.getX()), Math.round(_player.getY()), null);

        // g.drawString("Aliens left: " , 5, 50);

        // draw missiles
        if (_missiles != null) {
            for (Missile m : _missiles) {
                g.drawImage(m.getImage(), Math.round(m.getX()), Math.round(m.getY()), null);
            }
        }




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

    private static final int NUM_MUSHROOMS = 10;

    /**
     * background image
     */
    private Image _bgImage;

    /**
     * Is the game paused or not
     */
    private boolean _paused;

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

    /**
     * 
     */
    public void update(long elapsedTime) {
        // check input that can happen whether paused or not
        // checkSystemInput();

        // if (!isPaused()) {
        // check game input
        checkGameInput();

        // update sprite
        _player.update(elapsedTime);

        if (_missiles != null) {
            for (int i = 0; i < _missiles.size(); i++) {

                Missile missile = _missiles.get(i);

                if (missile.isVisible()) {

                    missile.update(elapsedTime);
                } else {

                    _missiles.remove(i);
                }
            }
        }

        // }

    }

    /**
     * Checks input from GameActions that can be pressed only when the game is not
     * paused.
     */
    public void checkGameInput() {
        float velocityX = 0;
        float velocityY = 0;

        if (moveLeft.isPressed()) {
            velocityX -= Player.SPEED;
        }
        if (moveRight.isPressed()) {
            velocityX += Player.SPEED;
        }

        if (moveDown.isPressed()) {
            velocityY += Player.SPEED;
        }
        if (moveUp.isPressed()) {
            velocityY -= Player.SPEED;
        }
        _player.setVelocityX(velocityX);
        _player.setVelocityY(velocityY);

        if (fire.isPressed()) {
            createMissile();
        }

    }


    public void createMushrooms(){

        Set<Point> locations = new HashSet<Point>();
        Point  sample;

        do{
            sample = new Point();
            sample.x= (int) Math.random() * screen.getWidth() + 1;
            sample.y= (int) Math.random() * screen.getWidth() + 1;   
            //xx and yy are the random number limits called from another part of the code
            locations.add(sample);     
        }
        while (locations.size()<  NUM_MUSHROOMS);

        System.out.println(locations);

    }

    public void createMissile() {
        Image missile_img = loadImage("images/missile.png");

        Animation anim = new Animation();
        anim.addFrame(missile_img, 250);

        Missile missile = new Missile(anim);
        _missiles.add(missile);

        missile.setX(_player.getX());
        missile.setY(_player.getY() + _player.getHeight() / 2);

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
        // load images
        _bgImage = loadImage("images/bgrd.png");

        Image player1 = loadImage("images/craft.png");
        // Image player2 = loadImage("../images/player2.png");
        // Image player3 = loadImage("../images/player3.png");

        // // create animation
        Animation anim = new Animation();
        anim.addFrame(player1, 250);
        // anim.addFrame(player2, 150);
        // anim.addFrame(player1, 150);
        // anim.addFrame(player2, 150);
        // anim.addFrame(player3, 200);
        // anim.addFrame(player2, 150);

        _player = new Player(anim);
        _player.setBdims(screen.getWidth(), screen.getHeight());
        _player.setY(screen.getHeight() - _player.getHeight());


    }

}