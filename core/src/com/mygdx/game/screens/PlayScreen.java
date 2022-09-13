package com.mygdx.game.screens;

import box2dLight.BlendFunc;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.WATERFOWLLAKE;
import com.mygdx.game.entities.Player;
import com.mygdx.game.WATERFOWLLAKE;
import com.mygdx.game.entities.Slime;
import com.mygdx.game.entities.Uffe;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.input.movement;
import com.mygdx.game.map.OrthogonalTiledMapRendererWithSprites;
import com.mygdx.game.map.tiles;

import com.mygdx.game.objects.mapObjects;

// * --
// ? --
// ! --

public class PlayScreen implements Screen {

    StaticTiledMapTile brush;
    TiledMapTileLayer mapLayer;
    Vector3 mouseCoordinateVector = new Vector3();

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private WATERFOWLLAKE game;

    ShapeRenderer sr;

    //trying something with layering of uffe
    Texture textureUffe;
    MapLayer uffeLayer;
    TextureRegion textureRegion;
    MapProperties mapProperties;

    //adding uffe picture
    private TextureAtlas textureAtlas;

    int[] background = new int[]{0};
    int[] uffelayer = new int[]{1};
    int[] foreground = new int[]{2};
    int[] objects = new int[]{3,4,5};

    //TiledMap variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    //private OrthogonalTiledMapRenderer renderer;
    private  OrthogonalTiledMapRendererWithSprites renderer;
    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Uffe player;
    private Slime slime,slime1;

    private Vector2 velocity = new Vector2();

    // how fast the player moves around and graivty
    private float speed = 2f, gravity = 60 * 1.8f;

    int hoi = 0;


    public MapProperties getMapProperties() {
        return mapProperties;
    }

//    private Texture generatePixel(int width, int height, Color color) {
//        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
//        pixmap.setColor(color);
//        pixmap.fill();
//        return new Texture(pixmap);
//    }
//    private void processInput(OrthographicCamera camera) {
//        mouseCoordinateVector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//        camera.unproject(mouseCoordinateVector);
//
//        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
//            mapLayer.setCell((int) mouseCoordinateVector.x, (int) mouseCoordinateVector.y, null);
//            //checkAndRecalculateTileLighting((int) mouseCoordinateVector.x, (int) mouseCoordinateVector.y);
//        }
//        else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
//            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
//            cell.setTile(brush);
//            mapLayer.setCell((int) mouseCoordinateVector.x, (int) mouseCoordinateVector.y, cell);
//            //checkAndRecalculateTileLighting((int) mouseCoordinateVector.x, (int) mouseCoordinateVector.y);
//        }
//    }


    public PlayScreen(WATERFOWLLAKE game) {
        textureAtlas = new TextureAtlas("figure/uffeanimation/uffeanimations.atlas");

        this.game = game;
        //this creates the cam used to follow uffe through the world
        gamecam = new OrthographicCamera();

        textureUffe = new Texture(Gdx.files.internal("figure/uffe/uffesit1.png"));

        //creates a viewport to maintain the virtual aspect dispite the screen getting bigger.
        gamePort = new ScreenViewport(gamecam);
        //gamePort = new FitViewport(800,480,gamecam);

        //creates the GAME HUD
        hud = new Hud(game.batch);

        //Loads the map and setup the map renderer
        mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("maps/map1/tryoutmap.tmx");
        //renderer = new OrthogonalTiledMapRendererWithSprites(map);
        renderer = new OrthogonalTiledMapRendererWithSprites(map);

        //map.getLayers().get("Foreground").

        mapProperties = map.getProperties();

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();



        //creates uffe in the world
        player = new Uffe(world, this);
        slime = new Slime(world,this);
        slime1 = new Slime(world,this);


        player.b2body.setTransform(19/WATERFOWLLAKE.PPM*32,19/WATERFOWLLAKE.PPM*32,0);
        slime.b2body.setTransform(12/WATERFOWLLAKE.PPM*32,12/WATERFOWLLAKE.PPM*32,0);
        slime1.b2body.setTransform(12/WATERFOWLLAKE.PPM*32,18/WATERFOWLLAKE.PPM*32,0);


        mapObjects.loadObjects(map, world);


        //uffeLayer = map.getLayers().get("Uffe");

        //textureRegion = new TextureRegion(textureUffe,23,23);

        //TextureMapObject tmo = new TextureMapObject(textureRegion);
        //tmo.setX(430);
        //tmo.setY(795);
        //uffeLayer.getObjects().add(tmo);

        // ! https://github.com/lyze237-examples/LibgdxTiledLightingExample/blob/main/core/src/main/java/dev/lyze/tiledLightingExample/Map.java#L127
        // ! https://github.com/lyze237-examples/LibgdxTiledLightingExample/blob/main/core/src/main/java/dev/lyze/tiledLightingExample/Map.java#L60
//        mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
//        brush = new StaticTiledMapTile(new TextureRegion(generatePixel(mapLayer.getTileWidth(), mapLayer.getTileHeight(), Color.TEAL)));

        //sets the gamecam
        //gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);




    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    public TextureAtlas getAtlas(){
        return textureAtlas;
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(player.b2body.getPosition().x * WATERFOWLLAKE.PPM / 32),
                (int)(player.b2body.getPosition().y * WATERFOWLLAKE.PPM / 32));
    }

    @Override
    public void show() {
    }

    public void update(float dt){
        movement.handleInput(dt,player,3);
        Slime.movetoplayer(player,slime);
        Slime.movetoplayer(player,slime1);

        world.step(1/60f,6,2);

        player.update(dt);
        slime.update(dt);
        slime1.update(dt);

        gamecam.position.x = player.b2body.getPosition().x * WATERFOWLLAKE.PPM;
        gamecam.position.y = player.b2body.getPosition().y * WATERFOWLLAKE.PPM;
        //((OrthographicCamera) gamePort.getCamera()).zoom = 0.4f;
        gamecam.update();

        renderer.setView(gamecam);

    }

    private void cameraUpdate(float dt) {
        Vector3 position = gamecam.position;

    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clears the game screen with BLACK
        //Gdx.gl20.glEnable(GL20.GL_BLEND);
        //Gdx.gl.glClear(GL20.GL_ALPHA_BITS);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
        //OB.parseMap(map);

        //mapObjects.Layering(map,world,player);

        renderer.render(background);
        //  renderer.render(foreground);
        //  renderer.render(objects);
//        renderer.render(background);
//        renderer.render(uffelayer);
//        renderer.render(foreground);
        //renders object lines AND circle
         b2dr.render(world, gamecam.combined.scl(WATERFOWLLAKE.PPM));
        //b2dr.render(world, gamecam.combined);
        //renders the box2d debuglines
        game.batch.setProjectionMatrix(gamecam.combined);


        // ! uffe needs to get a Z værdi, which puts him higher, changing the layers.
        // ! making this more dynamic

        if(tiles.tile(mapProperties,player)){
            game.batch.begin();
            slime.draw(game.batch);
            slime1.draw(game.batch);
            player.draw(game.batch);
            game.batch.end();


            renderer.render(foreground);
        } else{

            renderer.render(foreground);
            game.batch.begin();
            slime.draw(game.batch);
            slime1.draw(game.batch);
            player.draw(game.batch);
            game.batch.end();

        }


        //Sets batch to now draw what the HuD camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        //processInput(gamecam);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    b2dr.dispose();
    map.dispose();
    game.dispose();
    world.dispose();
    renderer.dispose();
    }

}

//    SpriteBatch Batch;
//    TextureAtlas textureAtlas;
//    Animation<TextureRegion> animation;
//
//    private TiledMap map;
//    private OrthogonalTiledMapRenderer renderer;
//    private OrthographicCamera camera;
//
//    private Player player;
//
//    private Animation<TextureRegion> uffe_animation;
//
//
//    @Override
//    public void render(float delta) {
//        //elapsedTime += Gdx.graphics.getDeltaTime();
//
//        Gdx.gl.glClearColor(0,0,0,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2,0);
//        camera.update();
//
//        renderer.setView(camera);
//        renderer.render();
//
//        renderer.getBatch().begin();
//        player.draw(renderer.getBatch());
//        renderer.getBatch().end();
//    }
//
//    @Override
//    public void show() {
//
//
//        TmxMapLoader loader = new TmxMapLoader();
//        map = loader.load("maps/treemap.tmx");
//
//        renderer = new OrthogonalTiledMapRenderer(map);
//        camera = new OrthographicCamera();
//
//        //uffe_animation = player.uffeanimation();
//
//        textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/myspritesheet.atlas"));
//        animation = new Animation(1f/15f, textureAtlas.getRegions());
//
//
//        player = new Player(new Sprite(new Animation<TextureRegion>(animation)),
//                (TiledMapTileLayer) map.getLayers().get(1));
//
//        player.setPosition(15* player.getCollisionlayer().getTileWidth(),(13 * player.getCollisionlayer().getTileHeight()));
//        Gdx.input.setInputProcessor(player);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//            camera.viewportWidth = width;
//            camera.viewportHeight = height;
//            camera.update();
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//        dispose();
//    }
//
//    @Override
//    public void dispose() {
//        map.dispose();
//        renderer.dispose();
//        player.getTexture().dispose();
//
//    }
//}


/** this was in show() */
//running_sheet = new Texture(Gdx.files.internal("uffe.png"));
//Animation still, left, right;
//batch = new SpriteBatch();
//ufferunningAtlas = new TextureAtlas(Gdx.files.internal("figure/uffewalkanimation/ufferuns.atlas"));
//left = new Animation(1/ 6f,ufferunningAtlas.getRegions());

//uffesittingAtlas= new TextureAtlas(Gdx.files.internal("figure/uffesitanimation/uffesitting.atlas"));
//still = new Animation(1 / 6f, uffesittingAtlas.getRegions());
//TexturePacker
//still = new Animation(1 / 2f, ufferunningAtlas.);

//ufferunningAtlas = new TextureAtlas(Gdx.files.internal("figure/uffewalkanimation/ufferuns.atlas"));
//right = new Animation(1/ 6f,ufferunningAtlas.getRegions());

//still.setPlayMode(Animation.PlayMode.LOOP);
//left.setPlayMode(Animation.PlayMode.LOOP);
//right.setPlayMode(Animation.PlayMode.LOOP);

//player = new Player(new Sprite(new Textur
