package com.mygdx.game.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.WATERFOWLLAKE;
import com.mygdx.game.entities.Uffe;
import com.mygdx.game.input.movement;
import com.mygdx.game.objects.Treespots;
import com.mygdx.game.objects.Water;
import com.mygdx.game.objects.mapObjects;

import java.util.List;

import static com.badlogic.gdx.Gdx.input;

public class tiles {

public static boolean tile(MapProperties mapProperties, Uffe player) {

    //MapProperties mapProperties = map.getProperties();
    int mapWidth = mapProperties.get("width", Integer.class);
    int mapHeight = mapProperties.get("height", Integer.class);
    int tilePixelWidth = mapProperties.get("tilewidth", Integer.class);
    int tilePixelHeight = mapProperties.get("tileheight", Integer.class);

    //int mapPixelWidth = mapWidth * tilePixelWidth;
    // ! int mapPixelHeight = mapHeight * tilePixelHeight;

    List<Treespots> list = mapObjects.getObjectX();
    List<Water> Waterlist = mapObjects.getWater();

//    for (int i = 0; i < Waterlist.size(); i++) {
//        System.out.println(Waterlist.get(i));
//    }

    if( isplayerontreecell(list,player)){
        return true;
    }
//
    return false;

}
    public static int playerPositionx(Uffe player){
        return (int) (player.b2body.getPosition().x* WATERFOWLLAKE.PPM/32);
    }
    public static int playerPositiony(Uffe player){
        return (int) (player.b2body.getPosition().y* WATERFOWLLAKE.PPM/32);
    }
    public static boolean isplayerontreecell(List<Treespots> list,Uffe player){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getTree_Y()==playerPositiony(player)){
                if(list.get(i).getTree_X() == playerPositionx(player)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isplayeronwatercell(List<Water> list, Uffe player){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getWater_Y()==playerPositiony(player)){
                if(list.get(i).getWater_X() == playerPositionx(player)){
                    return true;

                }
            }
        }
        return false;
    }


}
