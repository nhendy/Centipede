package centipede;

import centipede.Input.*;
import centipede.gamecore.*;
import centipede.graphics.*;
import centipede.objects.*;
// import centipede.score.*;


import java.lang.Thread;
import centipede.score.*;

import centipede.sound.*;
import java.awt.Graphics;
import java.awt.*;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;


public class Game extends GameCore {

    public static void main(String[] args) {
        // Path currentRelativePath = Paths.get("");
        // String s = currentRelativePath.toAbsolutePath().toString();
        // System.out.println("Current relative path is: " + s);

        _seed = Integer.parseInt(args[0]);
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
        _scorer = new ScoreManager();

        // use these lines for relative mouse mode
        // inputManager.setRelativeMouseMode(true);
        // inputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        loadImages();
        loadSounds();


        createGameActions();
        createPlayer();
        createMushrooms();
        createCentipede();
        createSpider();

        paused = false;
    }


    public void drawGameOver(Graphics2D g){
        String msg = "Game Over";
        String score = "Score is " + _scorer.getScore();
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = g.getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (screen.getWidth() - fm.stringWidth(msg)) / 2,
        screen.getHeight() / 2);

        g.drawString(score, (screen.getWidth() - fm.stringWidth(msg)) / 2,
        screen.getHeight() / 2   + g.getFont().getSize());

       
    }

    /**
     * 
     */
    public void draw(Graphics2D g) {
        // draw background
        g.drawImage(_bgImg, 0, 0, null);

        // draw player
        g.drawImage(_player.getImage(), Math.round(_player.getX()), Math.round(_player.getY()), null);

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


        if(_spider.isVisible()){
            g.drawImage(_spider.getImage(), Math.round(_spider.getX()), Math.round(_spider.getY()), null);
        }

        g.setColor(Color.white);


        g.drawString("Player Area ends here", 5, (int) ((screen.getHeight() - _player.getHeight()) * (1 - MAX_Y_PCT)));

        String scoreMssg = "Score: " + _scorer.getScore();
        g.drawString(scoreMssg, screen.getWidth() / 2, screen.getHeight() - g.getFontMetrics(g.getFont()).getHeight());
        String livesMssg = "Lives: " + _player.getLives();
        g.drawString(livesMssg, screen.getWidth() - g.getFontMetrics(g.getFont()).stringWidth(livesMssg), screen.getHeight() - g.getFont().getSize());

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
    private Spider _spider;
    private ArrayList<Missile> _missiles;
    private ScoreManager _scorer;

    /**
     * Constants
     */
    private static final int NUM_MUSHROOMS = 10;
    private static final float MAX_Y_PCT = 0.03f;
    private int _numSegments = 5;
    private int _visibleSegments = 5;

    /**
     * background image
     */
    private Image _bgImg;
    private Image _playerImg;
    private Image _centipedeImg;
    private Image _spiderImg;
    private Image _missileImg;
    private List<Image> _mushroomImgs;
    private SoundTrial _sound;
    private static int _seed;

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


    public void restoreGame(){

        if(_player.getLives() > 0){
            _centipedes.clear();
            _missiles.clear();
            createCentipede();
            createSpider();
            restoreMushrooms();
            recenterPlayer();

        } else {
            restoreMushrooms();
            stop();

        }
    }


    public void recenterPlayer(){
        _player.setX(0);
        _player.setY(screen.getHeight() - _player.getHeight());
    }


    public void restoreMushrooms(){
        for(Mushroom mushroom : _mushrooms){
            if(mushroom.isVisible() && mushroom.getLives() < 3){
                _scorer.playerRestoresMushroom();
                mushroom.restoreHealth();
                changeMushroomImg(mushroom);
            }
        }
    }

    /**
     * 
     */
    public void loadSounds(){
        _sound = new SoundTrial();
    }

    /**
     * 
     */
    public void loadImages() {
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
     * 
     */
    public void update(long elapsedTime) {
        // check input that can happen whether paused or not
        checkSystemInput();

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

        // update spider
        updateSpider(elapsedTime);

        // check collisions
        checkCollisions();

        
    }



    /**
     * 
     * @param elapsedTime
     */
    public void updateSpider(long elapsedTime){

        if(_spider.getLives() > 0 ){
            _spider.update(elapsedTime);
        } else {
            _spider.disappear();
        }
    }

    /**
     * 
     *  
     */
    public void updateCentipede(long elapsedTime) {

        // Translate each segment except for the head to the position of the
        // segment in front of it


        if(_visibleSegments > 0) {
            for (int i = 0; i < _numSegments; i++) {

                Rectangle r1 = _centipedes.get(i).getBounds();
    
                for (Mushroom m : _mushrooms) {
                    Rectangle r2 = m.getBounds();
                    if (r1.intersects(r2)) {
                        _centipedes.get(i).hitMushroom();
                    }
                }
    
                _centipedes.get(i).update(elapsedTime);
            }
        } else {   //if all are dead just create a new one

            _scorer.playerKillsCentipede();
            _centipedes.clear();
            createCentipede();
        }
        
        return;
    }


    public void updatePlayer(long elapsedTime) {
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

        Rectangle playerBds = _player.getBounds();




        Rectangle spiderBds = _spider.getBounds();


        //player intersecting with a spider
        if (playerBds.intersects(spiderBds)){
           
            // lose one life
            _player.inflictDamage();


            // restore game
            restoreGame();

            //ignore everything
            return ;
        }

        // missile hitting a spider
        for(Missile missile: _missiles){
            Rectangle missileBds = missile.getBounds();

            if (spiderBds.intersects(missileBds)) {

                // remove missile
                missile.disappear();


                // hit the spider
                _spider.hit();


                if(_spider.getLives() > 0){
                    _scorer.playerHitsSpider();
                } 
                else if(_spider.getLives() == 0){
                    _scorer.playerKillsSpider();
                    _spider.disappear();
                }




            }


        }



        //
        for (Centipede centipede : _centipedes) {

            Rectangle centBds = centipede.getBounds();

            //centipede intersects with player
            if (centBds.intersects(playerBds)) {


                // lose one life
                _player.inflictDamage();

                // restore game
                restoreGame();

                //ignore everything
                return ;

            }

            //missile hitting a centipede
            for(Missile missile: _missiles){
                Rectangle missileBds = missile.getBounds();
                if (centBds.intersects(missileBds)) {

                    // remove missile
                    missile.disappear();

                    // only remove the centipede
                    centipede.hit();

                    if(centipede.getLives() > 0){
                        _scorer.playerHitsCentipede();
                    } else if (centipede.getLives() == 0){
                        _visibleSegments --  ;
                        centipede.disappear();

                        if(_visibleSegments > 0) {
                            _scorer.playerKillsCentipedeSegment();
                        }
                    }

                }


            }


            
        }

        for (Mushroom mushroom : _mushrooms) {

            Rectangle mushroomBds = mushroom.getBounds();

            for(Missile missile: _missiles){
                Rectangle missileBds = missile.getBounds();
                if (mushroomBds.intersects(missileBds)) {

                    // hit the mushroom
                    mushroom.hit();

                    // remove missile
                    missile.disappear();

                   if (mushroom.getLives() > 0) {
                        changeMushroomImg(mushroom);
                        _scorer.playerHitsMushroom();
                        
                    } else {
                        mushroom.disappear();
                        _scorer.playerKillsMushroom();
                    }

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

        if (lives != 0) {
            Image newImg = _mushroomImgs.get(lives - 1);
            mushroom.getAnimation().setFrame(newImg, 0);
        }

    }


    /**
     * Check if exit is pressed
     */
    public void checkSystemInput() {
        if (exit.isPressed()) {
            stop();
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
            playSound();
            createMissile();
        }

    }

    public void playSound(){
        new Thread(_sound).start();
    }


    // ======================================== Creation    ================================== //

    public void createSpider(){
        Animation anim = new Animation();
        anim.addFrame(_spiderImg, 250);


        _spider = new Spider(anim);

        _spider.setBdims(screen.getWidth(), screen.getHeight());

        _spider.setX(screen.getWidth() / 2);
        _spider.setY(screen.getHeight() / 2);

        _spider.setMaxY((int) ((screen.getHeight() - _player.getHeight()) * (1 - MAX_Y_PCT)));
    }



    public void createCentipede() {

        for (int i = 0; i < _numSegments; i++) {

            Animation anim = new Animation();
            anim.addFrame(_centipedeImg, 250);
            Centipede centipede = new Centipede(anim);

            centipede.setBdims(screen.getWidth(), screen.getHeight());

            centipede.setY(centipede.getHeight() / 2);
            centipede.setX(screen.getWidth() - centipede.getWidth() * (i + 1));
            centipede.setMaxY((int) ((screen.getHeight() - _player.getHeight()) * (1 - MAX_Y_PCT)));

            _centipedes.add(centipede);
        }

        _visibleSegments = _numSegments;
    }

    public void createMushrooms() {

        
        Image mushImg = _mushroomImgs.get(2);

        int DIM = Math.max(mushImg.getWidth(null), mushImg.getHeight(null));
        // 1% of the screen height is for player
        int maxY = (int) ((screen.getHeight() - _player.getHeight()) * (1 - MAX_Y_PCT - 0.01));
        int maxX = (int) screen.getWidth() -  _centipedeImg.getWidth(null);

        int minY = 2 * DIM;
        int minX = DIM;

        int numTrials = 0 ;
        Set<Point> locations = new HashSet<Point>();
        Point sample;
        Random randomizer = new Random(_seed);


        do {
            sample = new Point();

            // 20% of width not used
            // sample.x = (int) (Math.random() * (screen.getWidth() - 2 * DIM) + DIM);
            sample.x   = randomizer.nextInt(maxX - minX + 1) + minX;
            sample.y   = randomizer.nextInt(maxY - minY + 1) + minY;

            // starts from 10 up to MAX_Y - 10
            // sample.y = (int) (Math.random() * (MAX_Y - 2 * DIM) + DIM);

            if (emptyPerimeter(locations, sample, DIM)) {
                locations.add(sample);
            } else {
                numTrials ++;
            }

            if(numTrials == NUM_MUSHROOMS){
                numTrials = 0;
                locations.clear();
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
     * 
     *                              (x , y - 1.5DIM)
     * (x - 1.5DIM, y - 1.5DIM) *-------------------* (x + 1.5DIM, y - 1.5DIM)
     *                          |         |         |
     *                          |         |         |
     *                          |         |         |
     *         (x - 1.5DIM, y ) |-------(x,y)-------| (x + 1.5DIM, y)
     *                          |         |         | 
     *                          |         |         |  
     *                          |         |         | 
     * (x - 1.5DIM, y + 1.5DIM) *-------------------* (x + 1.5DIM, y + 1.5DIM)
     *                              (x , y + 1.5DIM) 
     */
    public boolean emptyPerimeter(Set<Point> locations, Point sample, int dim) {


        double minX = sample.x - 1.5 * dim;
        double maxX = sample.x + 1.5 * dim;



        double minY = sample.y - 1.5 * dim;
        double maxY = sample.y + 1.5 * dim;




        // System.out.printf("( %f, %f) ---------------- (%f, %f)\n( %f, %f)
        // ---------------- (%f, %f)\n\n",
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

        moveLeft  = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        moveDown  = new GameAction("moveDown");
        moveUp    = new GameAction("moveUp");

        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToMouse(fire, InputManager.MOUSE_BUTTON_1);
        inputManager.mapToMouse(moveLeft, InputManager.MOUSE_MOVE_LEFT);
        inputManager.mapToMouse(moveRight, InputManager.MOUSE_MOVE_RIGHT);
        inputManager.mapToMouse(moveUp, InputManager.MOUSE_MOVE_UP);
        inputManager.mapToMouse(moveDown, InputManager.MOUSE_MOVE_DOWN);
    }

    /**
     * create player
     */
    private void createPlayer() {

        Animation anim = new Animation();
        anim.addFrame(_playerImg, 250);

        _player = new Player(anim);
        _player.setBdims(screen.getWidth(), screen.getHeight());
        _player.setY(screen.getHeight() - _player.getHeight());

    }

}