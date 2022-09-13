package com.mygdx.game.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.WATERFOWLLAKE;
import com.mygdx.game.entities.Uffe;


import java.util.ArrayList;
import java.util.List;

public class mapObjects {

    static int hoi = 0;

    public static List<Treespots> objectX = new ArrayList<>();
    public static List<Water> Water = new ArrayList<>();


    public static boolean Layering(Uffe uffe){

        return false;
    }

    public static void loadObjects (TiledMap map, World world) {

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

                for (
                        MapObject object : new Array.ArrayIterator<>(map.getLayers().get("Trees").getObjects().getByType(RectangleMapObject.class))) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();


                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((rect.getX() + rect.getWidth() / 2) / WATERFOWLLAKE.PPM, (rect.getY() + rect.getHeight() / 2) / WATERFOWLLAKE.PPM);

//                    objectXY.add(bdef.position.set((rect.getX() + rect.getWidth() / 2) / WATERFOWLLAKE.PPM,
//                            (rect.getY() + rect.getHeight() / 2) / WATERFOWLLAKE.PPM));

                    body = world.createBody(bdef);


                    hoi++;

                    shape.setAsBox(rect.getWidth() / 2 / WATERFOWLLAKE.PPM, rect.getHeight() / 2 / WATERFOWLLAKE.PPM);
                    fdef.shape = shape;
                    body.createFixture(fdef);

                    int bodyx = (int) (body.getPosition().x*WATERFOWLLAKE.PPM/32);
                    int bodyy = (int) (body.getPosition().y*WATERFOWLLAKE.PPM/32);



                    // Adding the whole tree
                    //objectX.add(new Treespots(bodyx,bodyy));
                    //objectX.add(new Treespots(bodyx+1,bodyy));
                    objectX.add(new Treespots(bodyx,bodyy+1));
                    objectX.add(new Treespots(bodyx+1,bodyy+1));
                    objectX.add(new Treespots(bodyx,bodyy+2));
                    objectX.add(new Treespots(bodyx+1,bodyy+2));
                    objectX.add(new Treespots(bodyx,bodyy+3));
                    objectX.add(new Treespots(bodyx+1,bodyy+3));

                    //System.out.println(objectX.get(hoi-1).getTree_X());
                    //System.out.println("There are this " + hoi + " many rectangles.");

                }
        for (
                MapObject object : new Array.ArrayIterator<>(map.getLayers().get("TreeStump").getObjects().getByType(RectangleMapObject.class))) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / WATERFOWLLAKE.PPM, (rect.getY() + rect.getHeight() / 2) / WATERFOWLLAKE.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / WATERFOWLLAKE.PPM, rect.getHeight() / 2 / WATERFOWLLAKE.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }
        for (
                MapObject object : new Array.ArrayIterator<>(map.getLayers().get("Water").getObjects().getByType(RectangleMapObject.class))) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


              bdef.type = BodyDef.BodyType.StaticBody;
               bdef.position.set((rect.getX() + rect.getWidth() / 2) / WATERFOWLLAKE.PPM, (rect.getY() + rect.getHeight() / 2) / WATERFOWLLAKE.PPM);

            //System.out.println((int)(rect.getWidth()/32) + " " + (int)(rect.getHeight()/32));

            int rows = (int)(rect.getWidth()/32);
            int collumns = (int)(rect.getHeight()/32);
            System.out.println(rows);
            System.out.println(collumns);
            System.out.println("next");
                body = world.createBody(bdef);

//  !            shape.setAsBox(rect.getWidth() / 2 / WATERFOWLLAKE.PPM, rect.getHeight() / 2 / WATERFOWLLAKE.PPM);
//  !           fdef.shape = shape;
//  !            body.createFixture(fdef);


            for (int i = 0; i < rows; i++) {
                int bodyy = (int) (body.getPosition().y * WATERFOWLLAKE.PPM / 32);
                for (int j = 0; j < collumns; j++) {
                    int bodyx = (int) (body.getPosition().x * WATERFOWLLAKE.PPM / 32);
                    Water.add(new Water( bodyy + j, bodyx -i));
                }
            }

        }
    }

    public static List<com.mygdx.game.objects.Water> getWater() {
        return Water;
    }
    public static List<Treespots> getObjectX() {
        return objectX;
    }
}
