package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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


public class Slime extends Sprite {

    static int timer = 0;

    public enum State{ SITTING, RUNNNING};
    public State currentState,previousState;

    public World world;
    public Body b2body;
    private TextureRegion uffeSits;
    private TextureAtlas textureAtlas;

    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> Slimestand;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> Slimemove;

    private boolean runningRight;
    private boolean sittingRight;
    private float stateTimer;

    TextureRegion textureRegion;
    Texture texture;

    int layer;

    public Slime(World world, PlayScreen screen){
        super();
        this.world = world;
        defineUffe();

        currentState = State.SITTING;
        previousState = State.SITTING;
        stateTimer = 0;
        runningRight = true;

        // ! this can be in a seperate class
        textureAtlas = new TextureAtlas(Gdx.files.internal("figure/slime/sprite-slime.atlas"));

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(textureAtlas.findRegion("Sprite-Slime1"));
        frames.add(textureAtlas.findRegion("Sprite-Slime2"));
        frames.add(textureAtlas.findRegion("Sprite-Slime3"));
        frames.add(textureAtlas.findRegion("Sprite-Slime4"));
        frames.add(textureAtlas.findRegion("Sprite-Slime5"));
        frames.add(textureAtlas.findRegion("Sprite-Slime6"));
        frames.add(textureAtlas.findRegion("Sprite-Slime7"));
        frames.add(textureAtlas.findRegion("Sprite-Slime8"));
        Slimestand = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        frames.add(textureAtlas.findRegion("Sprite-Slime1"));
        frames.add(textureAtlas.findRegion("Sprite-Slime2"));
        frames.add(textureAtlas.findRegion("Sprite-Slime3"));
        frames.add(textureAtlas.findRegion("Sprite-Slime4"));
        frames.add(textureAtlas.findRegion("Sprite-Slime5"));
        frames.add(textureAtlas.findRegion("Sprite-Slime6"));
        frames.add(textureAtlas.findRegion("Sprite-Slime7"));
        frames.add(textureAtlas.findRegion("Sprite-Slime8"));

        Slimemove = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.4f,frames);
        frames.clear();





        //animation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(1f/15f, textureAtlas.findRegions("uffesit"));


        //uffeSits = new TextureRegion(getTexture(),3,3,22,22);
        //setBounds(0,0,32/WATERFOWLLAKE.PPM,32/WATERFOWLLAKE.PPM);
        setBounds(0,0,32/WATERFOWLLAKE.PPM,32/WATERFOWLLAKE.PPM);

        setSize(1,1);

        b2body.setLinearVelocity(1f,1f);

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
                region = Slimestand.getKeyFrame(stateTimer,true);
                break;
            default: // case SITTING:
                region = Slimemove.getKeyFrame(stateTimer,true);
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
        //bdef.position.set(500f/WATERFOWLLAKE.PPM,500f/WATERFOWLLAKE.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(25 / WATERFOWLLAKE.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();


    }
    public static void movetoplayer(Uffe uffe, Slime slime){
        int horizontalForce = 0;
        int verticalForce = 0;

        float speed = 0.2f;
        int range = 10;



        int uffeX = 0;
        int uffeY= 0;

        uffeX = (int) (uffe.b2body.getPosition().x* WATERFOWLLAKE.PPM/32);
        uffeY = (int) (uffe.b2body.getPosition().y* WATERFOWLLAKE.PPM/32);

        int slimeX;
        int slimeY;

        slimeX = (int) (slime.b2body.getPosition().x* WATERFOWLLAKE.PPM/32);
        slimeY = (int) (slime.b2body.getPosition().y* WATERFOWLLAKE.PPM/32);

        int Xdif = 0;
        Xdif = uffeX - slimeX;
        //System.out.println(Xdif);
        int Ydif = 0;
        Ydif = uffeY - slimeY;


        if((Xdif <= range) && (Xdif > -1)){
            if(!(Ydif >= range) && !(Ydif <= -5)) {
                horizontalForce += 1;
                timer =0;
            }
        }
        if((Ydif <= range) && (Ydif > -1)){
            if(!(Xdif >= range) && !(Xdif <= -range)) {
                verticalForce += 1;
                timer =0;
            }
        }
        //!
        if((Xdif >= -range) && (Xdif < 1)){
            if(!(Ydif >= range) && !(Ydif <= -range)) {
                horizontalForce -= 1;
                timer =0;
            }

        }
        if((Ydif >= -range) && (Ydif < 1)){
            if(!(Xdif >= range) && !(Xdif <= -range)) {
                verticalForce -= 1;
                timer =0;
            }
        }
        slime.b2body.setLinearVelocity( horizontalForce* speed, slime.b2body.getLinearVelocity().y);
        slime.b2body.setLinearVelocity(slime.b2body.getLinearVelocity().x,verticalForce * speed );


        timer++;

        if(timer == 4200){
            slime.b2body.setTransform(12/WATERFOWLLAKE.PPM*32,12/WATERFOWLLAKE.PPM*32,0);
            timer = 0;
            System.out.println("back to base");
        }



    }


}
//! vanna vakantie
//! 15/12 2022
//! 20/12 2022