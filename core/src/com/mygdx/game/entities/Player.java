package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Player extends Sprite implements InputProcessor {

    public enum State {SITTING, RUNNING}
    public State currentState,previousState;
    private Animation<TextureRegion> uffeRun,uffeSit,animation;
    private float stateTimer;
    private boolean runningRight;

    TextureAtlas uffeatlas;

    Sprite uffe;

    Texture texture;


    //private Animation still, left, right;

    // the movement velocity ( it holds x and y)
    private Vector2 velocity = new Vector2();

    // how fast the player moves around and graivty
    private float speed = 60 * 2, gravity = 60 * 1.8f,animationTime = 0;


    private TiledMapTileLayer collisionlayer;


    public Sprite UffesSprite(){
        uffe = new Sprite();
        uffeatlas = new TextureAtlas(Gdx.files.internal("figure/uffeanimation/uffeanimations.atlas"));
        animation = new Animation<TextureRegion>(1f/15f,uffeatlas.getRegions());

        return uffe;
    }

    public Texture uffeanimation (){
        //batch = new SpriteBatch();


        currentState = State.SITTING;
        previousState = State.SITTING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        //frames.add((TextureRegion) uffeatlas.getRegions());
        frames.add(uffeatlas.getRegions().get(1));
        frames.add(uffeatlas.getRegions().get(2));
        //frames.add(uffeatlas.findRegion("uffsit2"));
        //frames.add(uffeatlas.findRegion("uffsit3"));
        //uffeSit = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        //frames.add(uffeatlas.findRegion("uffwalk1"));
        //frames.add(uffeatlas.findRegion("uffwalk2"));
        //frames.add(uffeatlas.findRegion("uffwalk3"));
        frames.add(uffeatlas.getRegions().get(3));
        frames.add(uffeatlas.getRegions().get(4));
        frames.add(uffeatlas.getRegions().get(5));
        uffeRun = new Animation<TextureRegion>(0.1f,frames);

        texture = new Texture("figure/uffe_right.png");

        return texture;



        //frames.clear();


        //uffeSit = new Animation<TextureRegion>(1f/15f,uffeatlas.getRegions());

    }



    //public Player(Sprite sprite, TiledMapTileLayer collisionplayer){
    public Player(
            Sprite sprite,
            //Animation still, Animation left, Animation right,
            TiledMapTileLayer collisionLayer) {
        /**Dit zorgt voor een bind() anders werkt het niet */
        super(sprite);

        //this.uffeanimation();


        //batch = new SpriteBatch();
        //new Texture("figure/uffe_right.png");
        //super((Texture) still.getKeyFrame(0));
//        this.still = still;
//        this.left = left;
//        this.right = right;
        this.collisionlayer = collisionLayer;
        //this.collisionLayer = collisionplayer;

        //sprite.setRegionHeight(10);
        //sprite.getWidth();

    }



    // he has SpritBatch spireBatch here.
    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }


    public TiledMapTileLayer getCollisionlayer() {
        return collisionlayer;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setCollisionlayer(TiledMapTileLayer collisionlayer) {
        this.collisionlayer = collisionlayer;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.W:
                velocity.y = speed;
                break;
            case Input.Keys.S:
                velocity.y = -speed;

                break;
            case Input.Keys.A:
//                setTexture(new Texture("figure/uffe8bit1.png"));
                velocity.x = -speed;
//                animationTime = 0;
                break;
            case Input.Keys.D:
                //setTexture(new Texture("figure/uffe_right.png"));
                velocity.x = speed;
                //animationTime = 0;
                break;
        }

        return true;
    }



    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.W:
                velocity.y = 0;
                break;
            case Input.Keys.S:
                velocity.y = 0;
                break;
            case Input.Keys.A:
                velocity.x = 0;
                //animationTime = 0;
                break;
            case Input.Keys.D:
                velocity.x = 0;
                //animationTime = 0;
                break;
        }
        return true;

    }




    public void update(float delta) {
        // save old position
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        // move on x
        setX(getX() + velocity.x * delta* 1.5f);
        if(velocity.x < 0) {
            collisionX = collidesLeft();
        } else if (velocity.x > 0){
            collisionX =collidesRight();
        }
        if(collisionX){
            setX(oldX);
            velocity.x=0;
        }
        // move on y
        setY(getY() + velocity.y * delta * 1.5f);
        if(velocity.y < 0){
            collisionY = collidesBottom();
        } else if (velocity.y > 0){
            collisionY = collidesTop();
        }
        if(collisionY){
            setY(oldY);
            velocity.y=0;
        }
        // update animation
        //animationTime += delta;
        //setRegion( (velocity.x < 0 ? getFrame(animationTime) : velocity.x > 0 ? getFrame(animationTime) : getFrame(animationTime)));

        //setRegion(getFrame(delta));

    }
    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = uffeRun.getKeyFrame(stateTimer, true);
                break;
            case SITTING:
                region = uffeSit.getKeyFrame(stateTimer,true);
                break;
            default:
                region = uffeSit.getKeyFrame(stateTimer,true);
                break;

        }
        if((velocity.x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if ((velocity.x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(velocity.x != 0){
            return State.RUNNING;
        }
        else {
            return State.SITTING;
        }
    }


    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = null;
        boolean blocked = false;

        try {
            cell = collisionlayer.getCell((int) (x / collisionlayer.getTileWidth()), (int) (y / collisionlayer.getTileHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cell != null && cell.getTile() != null) {

            if (cell.getTile().getProperties().containsKey("blocked")) {
                System.out.println("this happened?2");
                blocked = true;
            }
        }
        return blocked;
    }
    public boolean collidesRight(){
        boolean collides = false;

        for(float step = 0; step < getHeight(); step += (collisionlayer.getTileHeight()) /2){
            collides = isCellBlocked(getX() +getWidth() ,getY()+ step);
            if(collides)
                break;
        }

        return collides;
    }
    public boolean collidesLeft(){
        boolean collides = false;

        for(float step = 0; step < getHeight(); step += collisionlayer.getTileHeight() /2){
            collides = isCellBlocked(getX() + step,getY());
            if(collides)
                break;
        }

        return collides;
    }
    public boolean collidesBottom(){
        boolean collides = false;

        for(float step = 0; step < getWidth(); step += collisionlayer.getTileWidth() /2){
            collides = isCellBlocked(getX() + step,getY());
            if(collides)
                break;
        }

        return collides;
    }
    public boolean collidesTop(){
        boolean collides = false;

        for(float step = 0; step < getWidth(); step += collisionlayer.getTileWidth() /2){
            collides = isCellBlocked(getX() + step,getY() + getHeight());
            if(collides)
                break;
        }

        return collides;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

