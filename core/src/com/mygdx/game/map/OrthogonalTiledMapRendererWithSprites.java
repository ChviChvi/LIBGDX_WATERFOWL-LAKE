package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.entities.Uffe;

import java.util.ArrayList;
import java.util.List;


    public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {

        public OrthogonalTiledMapRendererWithSprites(TiledMap map) {
            super(map);
        }

        @Override
        public void renderObject(MapObject object) {
            if(object instanceof TextureMapObject) {
                TextureMapObject textureObj = (TextureMapObject) object;
                batch.draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY());
            }
        }
    }

