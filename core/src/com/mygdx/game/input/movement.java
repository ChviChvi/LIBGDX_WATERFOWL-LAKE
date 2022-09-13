package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.map.tiles;

import com.mygdx.game.entities.Uffe;
import com.mygdx.game.objects.Water;
import com.mygdx.game.objects.mapObjects;

import java.util.List;

public class movement implements InputProcessor {

    static float speed;

    public static void handleInput(float dt, Uffe player, float speed){
        int horizontalForce = 0;
        int verticalForce = 0;

        List<Water> Waterlist = mapObjects.getWater();
        if( tiles.isplayeronwatercell(Waterlist,player)){
            speed = 1;
        }

        //speed = 3;

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            verticalForce += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)
        ){
            horizontalForce += 1;
//            player.b2body.applyLinearImpulse(new Vector2(speed,0f),player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)
        ){

            horizontalForce -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            verticalForce -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            speed = speed * 2;
        }
        player.b2body.setLinearVelocity(horizontalForce * speed, player.b2body.getLinearVelocity().y);
        player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x,verticalForce * speed );
    }





    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
