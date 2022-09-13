package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.WATERFOWLLAKE;
import com.mygdx.game.screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation.*;


public class Uffe extends Sprite {

    public enum State{ SITTING, RUNNNING};
    public State currentState,previousState;

    public World world;
    public Body b2body;
    private TextureRegion uffeSits;
    private TextureAtlas textureAtlas;

    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> uffeRun;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> uffeSit;

    private boolean runningRight;
    private boolean sittingRight;
    private float stateTimer;

    com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation;

    int layer;

    public Uffe(World world, PlayScreen screen){
        super();
        this.world = world;
        defineUffe();

        currentState = State.SITTING;
        previousState = State.SITTING;
        stateTimer = 0;
        runningRight = true;

        // ! this can be in a seperate class
        textureAtlas = new TextureAtlas(Gdx.files.internal("figure/uffeanimation/uffeanimations.atlas"));

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(textureAtlas.findRegion("uffesit1"));
        frames.add(textureAtlas.findRegion("uffesit2"));
        frames.add(textureAtlas.findRegion("uffesit3"));
        uffeSit = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.2f,frames);
        frames.clear();

        frames.add(textureAtlas.findRegion("uffewalk1"));
        frames.add(textureAtlas.findRegion("uffewalk2"));
        frames.add(textureAtlas.findRegion("uffewalk3"));
        uffeRun = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.2f,frames);
        frames.clear();





        //animation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1f/15f, textureAtlas.findRegions("uffesit"));


       //uffeSits = new TextureRegion(getTexture(),3,3,22,22);
        setBounds(0,0,25/WATERFOWLLAKE.PPM,23/WATERFOWLLAKE.PPM);
        //setRegion(uffeSits);

        this.layer = layer;

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case RUNNNING:
                region = uffeRun.getKeyFrame(stateTimer,true);
                break;
            default: // case SITTING:
                region = uffeSit.getKeyFrame(stateTimer,true);
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y != 0 || b2body.getLinearVelocity().x != 0 ){
            return State.RUNNNING;
        } else //if (b2body.getLinearVelocity().y == 0 && b2body.getLinearVelocity().x == 0)
        {
            return State.SITTING;
        }

    }

    private void defineUffe() {
        BodyDef bdef = new BodyDef();
        //bdef.position.set(500/ WATERFOWLLAKE.PPM,500/ WATERFOWLLAKE.PPM);
        bdef.position.set(5f/WATERFOWLLAKE.PPM,5f/WATERFOWLLAKE.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(9 / WATERFOWLLAKE.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();


    }


}