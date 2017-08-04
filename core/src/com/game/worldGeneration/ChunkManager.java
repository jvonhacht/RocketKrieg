package com.game.worldGeneration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.StringBuilder;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.*;
import com.game.objects.alien.AlienShip;
import com.game.objects.alien.AlienShipSpecial;
import com.game.objects.alien.Laser;
import com.game.objects.collision.CollisionManager;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.objects.ship.shipComponent.Missile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.err;

/**
 *  ChunkManager class handling the
 *  procedural generation of the world.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-04-26)
 */
public class ChunkManager {
    private SpriteBatch batch;
    private HashMap<Pair,Chunk> chunks;
    static HashMap<Pair,ArrayList<Entity>> hashGrid;
    private CollisionManager colHandler;
    private OrthographicCamera camera;
    private PlayerSpaceShip ship;

    /**
     * Constructor for ChunkManager.
     * @param ship PlayerSpaceShip
     */
    public ChunkManager(PlayerSpaceShip ship, OrthographicCamera camera) {
        batch = GameEntry.batch;
        this.camera = camera;
        this.ship = ship;
        chunks = new HashMap<Pair,Chunk>();
        hashGrid = new HashMap<Pair, ArrayList<Entity>>();
        colHandler = new CollisionManager();
        addEntity(ship);
    }

    /**
     * Method to render and update everything in the world.
     */
    public void render(boolean update, float delta) {
        Vector3 camPosition = camera.position;
        Vector2 position = new Vector2(camPosition.x,camPosition.y);

        //Up side
        Vector2 downSide = new Vector2();
        downSide.x = position.x;
        downSide.y = position.y + Gdx.graphics.getWidth()/2;

        //Down side
        Vector2 upSide = new Vector2();
        upSide.x = position.x;
        upSide.y = position.y - Gdx.graphics.getWidth()/2;

        //Right side
        Vector2 rightSide = new Vector2();
        rightSide.x = position.x + Gdx.graphics.getWidth()/2;
        rightSide.y = position.y;

        //Right corner up
        Vector2 rightSideUp = new Vector2();
        rightSideUp.x = position.x + Gdx.graphics.getWidth()/2;
        rightSideUp.y = position.y + Gdx.graphics.getHeight()/2;

        //Right corner down
        Vector2 rightSideDown = new Vector2();
        rightSideDown.x = position.x + Gdx.graphics.getWidth()/2;
        rightSideDown.y = position.y - Gdx.graphics.getHeight()/2;

        //Left side
        Vector2 leftSide = new Vector2();
        leftSide.x = position.x - Gdx.graphics.getWidth()/2;
        leftSide.y = position.y;

        //Left corner up
        Vector2 leftSideUp = new Vector2();
        leftSideUp.x = position.x - Gdx.graphics.getWidth()/2;
        leftSideUp.y = position.y + Gdx.graphics.getHeight()/2;

        //Left corner down
        Vector2 leftSideDown = new Vector2();
        leftSideDown.x = position.x - Gdx.graphics.getWidth()/2;
        leftSideDown.y = position.y - Gdx.graphics.getHeight()/2;

        int chunkSize = Chunk.WIDTH*Tile.TILE_SIZE;

        //anchor points is where chunk starts e.g. 0.0,
        Vector2 anchor = getAnchor(position, chunkSize);
        Vector2 anchor1 = getAnchor(upSide, chunkSize);
        Vector2 anchor2 = getAnchor(downSide, chunkSize);
        Vector2 anchor3 = getAnchor(rightSide, chunkSize);
        Vector2 anchor4 = getAnchor(rightSideUp, chunkSize);
        Vector2 anchor5 = getAnchor(rightSideDown, chunkSize);
        Vector2 anchor6 = getAnchor(leftSide, chunkSize);
        Vector2 anchor7 = getAnchor(leftSideUp, chunkSize);
        Vector2 anchor8 = getAnchor(leftSideDown, chunkSize);

        //add nearby neighbour chunks to render que.
        Set<Pair> anchors = new HashSet<Pair>();
        anchors.add(new Pair(anchor.x,anchor.y));
        anchors.add(new Pair(anchor1.x,anchor1.y));
        anchors.add(new Pair(anchor2.x,anchor2.y));
        anchors.add(new Pair(anchor3.x,anchor3.y));
        anchors.add(new Pair(anchor4.x,anchor4.y));
        anchors.add(new Pair(anchor5.x,anchor5.y));
        anchors.add(new Pair(anchor6.x,anchor6.y));
        anchors.add(new Pair(anchor7.x,anchor7.y));
        anchors.add(new Pair(anchor8.x,anchor8.y));

        //create a render que that renders at the end of the method.
        ArrayList<Entity> entitiesToRender = new ArrayList<Entity>();

        //add all chunks to hashmap and render.
        Iterator iterator = anchors.iterator();
        while(iterator.hasNext()) {
            Pair chunkPair = (Pair)iterator.next();
            if(chunks.containsKey(chunkPair)) {
                Tile[][] tiles = chunks.get(chunkPair).getTiles();
                //render background tiles in chunk.
                for (int k=0; k<tiles.length; k++) {
                    for (int l = 0; l<tiles.length; l++) {
                        Tile tile = tiles[k][l];
                        float tileX = tile.getX();
                        float tileY = tile.getY();
                        if(!update) {
                            //=======!!CHANGE TO TILE.GETIMG() IF NPE FIXED!!======
                            batch.draw(AssetStorage.tile1,tileX,tileY);
                        }

                        //try collect entities in tile.
                        try {
                            //rehash, add to render que and do collisions for all entities.
                            Pair objectPair = new Pair(tileX, tileY);
                            ArrayList<Entity> entities = hashGrid.get(objectPair);
                            hashGrid.remove(objectPair);
                            colHandler.collides(entities);
                            for (Entity ent:entities) {
                                entitiesToRender.add(ent);
                                addEntity(ent);
                            }
                        } catch (NullPointerException e){}
                    }
                }
            } else {
                //no chunks, make new ones.
                Chunk chunk = new Chunk(chunkPair.getX(),chunkPair.getY(), true);
                chunks.put(chunkPair,chunk);
            }
        }
        if(update) {
            //render and update the entity que.
            for (Entity ent:entitiesToRender) {
                ent.update(delta);
            }
        } else {
            //render and update the entity que.
            for (Entity ent:entitiesToRender) {
                if(!(ent instanceof PlayerSpaceShip)) {
                    ent.render(batch);
                }
            }
        }
    }

    /**
     * Add an entity to the spatial hashgrid.
     * @param ent Entity to be added.
     */
    public static void addEntity(Entity ent) {
        Vector2 position = ent.getPosition();
        Vector2 anchor = getAnchor(position, Tile.TILE_SIZE);
        int anchorX = (int)anchor.x;
        int anchorY = (int)anchor.y;
        Pair pair = new Pair(anchorX,anchorY);
        if(hashGrid.containsKey(pair)) {
            hashGrid.get(pair).add(ent);
        } else {
            hashGrid.put(pair, new ArrayList<Entity>());
            hashGrid.get(pair).add(ent);
        }
    }

    /**
     * Remove an entity to the spatial hashgrid.
     * @param ent Entity to be removed.
     */
    public static void removeEntity(Entity ent) {
        Vector2 position = ent.getPosition();
        Vector2 anchor = getAnchor(position, Tile.TILE_SIZE);
        int anchorX = (int)anchor.x;
        int anchorY = (int)anchor.y;
        Pair pair = new Pair(anchorX,anchorY);
        if(hashGrid.containsKey(pair)) {
            ArrayList<Entity> gridTile = hashGrid.get(pair);
            gridTile.remove(ent);
        }
    }

    /**
     * Method to get anchor point from a position and size.
     * @param position of object.
     * @param size distance between anchor points.
     * @return Vector2 anchor.
     */
    public static Vector2 getAnchor(Vector2 position, int size) {
        Vector2 anchor = new Vector2();
        int anchorX;
        int anchorY;
        if(position.x<0) {
            anchorX = size - 1;
        } else {
            anchorX = 0;
        }
        if(position.y<0) {
            anchorY = size - 1;
        } else {
            anchorY = 0;
        }
        anchor.set((int)(position.x - anchorX) / size * size,(int)(position.y - anchorY) / size * size);
        return anchor;
    }

    /**
     * Method to save world information to file.
     */
    public void saveGame() {
        //save chunks: x:y=tileId tileId ... tileId
        try {
            File gameChunks = new File("chunkData.rk");
            FileOutputStream fos = new FileOutputStream(gameChunks);
            PrintWriter pw = new PrintWriter(fos);

            for(Map.Entry<Pair,Chunk> m :chunks.entrySet()){
                StringBuilder sb = new StringBuilder();
                sb.append(m.getKey());
                sb.append("=");

                Tile[][] gameDat = m.getValue().getTiles();
                for (int i=0; i<gameDat.length; i++) {
                    for (int j=0; j<gameDat.length; j++) {
                        sb.append(gameDat[i][j].getImgId());
                    }
                }
                String toPrint = sb.toString();
                toPrint = Base64Coder.encodeString(toPrint);
                pw.println(toPrint);
            }
            pw.flush();
            pw.close();
            fos.close();
        } catch(Exception e) {
            err.println(e);
        }
        //===========================================================//
        //save entities: position & velocity & acceleration & angle & angularvelocity & sizeX & sizeY
        try {
            File entityData = new File("entityData.rk");
            FileOutputStream fos = new FileOutputStream(entityData);
            PrintWriter pw = new PrintWriter(fos);

            for(Map.Entry<Pair,ArrayList<Entity>> m :hashGrid.entrySet()){
                StringBuilder sb = new StringBuilder();
                ArrayList<Entity> entitiesToSave = m.getValue();
                for (int i=0; i<entitiesToSave.size(); i++) {
                    Entity ent = entitiesToSave.get(i);
                    sb.append(ent.getId());                sb.append("&");
                    sb.append(ent.getPosition().x);        sb.append("&"); //position
                    sb.append(ent.getPosition().y);        sb.append("&");
                    sb.append(ent.getVelocity().x);        sb.append("&"); //velocity
                    sb.append(ent.getVelocity().y);        sb.append("&");
                    sb.append(ent.getAcceleration().x);    sb.append("&"); //acceleration
                    sb.append(ent.getAcceleration().y);    sb.append("&");
                    sb.append(ent.getAngle());             sb.append("&"); //angle
                    sb.append(ent.getAngularVelocity());   sb.append("&"); //angular velocity
                    sb.append(ent.getSizeX());             sb.append("&"); //size x
                    sb.append(ent.getSizeY());                             //size y
                    sb.append("=");
                }
                sb.deleteCharAt(sb.length-1);
                String toPrint = sb.toString();
                toPrint = Base64Coder.encodeString(toPrint);
                pw.println(toPrint);

            }
            pw.flush();
            pw.close();
            fos.close();
        } catch(Exception e) {
            err.println(e);
        }
    }

    /**
     * Method to reload game information from file.
     */
    public void reloadGame() {
        //read chunk info
        try{
            File toRead = new File("chunkData.rk");
            FileInputStream fis = new FileInputStream(toRead);

            Scanner sc = new Scanner(fis);

            chunks = new HashMap<Pair,Chunk>();

            //read data from file line by line:
            String currentLine;
            while(sc.hasNextLine()){
                currentLine = sc.nextLine();
                byte[] byteLine = Base64Coder.decode(currentLine);
                currentLine = new String(byteLine,"UTF-8");
                //split position data and tile data
                String[] data = currentLine.split(Pattern.quote("="));
                //create chunkPair
                String[] coord = data[0].split(Pattern.quote(":"));
                Pair chunkPair = new Pair(Float.parseFloat(coord[0]),Float.parseFloat(coord[1]));
                //create chunk
                Chunk chunk = new Chunk(chunkPair.getX(),chunkPair.getY(), false);
                chunk.setTiles(data[1]);

                chunks.put(chunkPair,chunk);
            }
            fis.close();
        }catch(Exception e){}

        //===========================================================//
        //read entities:
        try{
            File toRead = new File("entityData.rk");
            FileInputStream fis = new FileInputStream(toRead);

            Scanner sc = new Scanner(fis);

            hashGrid = new HashMap<Pair,ArrayList<Entity>>();

            //read data from file line by line:
            String currentLine;
            while(sc.hasNextLine()){
                currentLine = sc.nextLine();
                byte[] byteLine = Base64Coder.decode(currentLine);
                currentLine = new String(byteLine,"UTF-8");
                //split position data and tile data
                String[] entities = currentLine.split(Pattern.quote("="));
                for (int i=0; i<entities.length; i++) {
                    String entity = entities[i];
                    String[] entityData = entity.split(Pattern.quote("&"));
                    int ID = Integer.parseInt(entityData[0]);

                    //retrieve location data.
                    Vector2 position = new Vector2(Float.parseFloat(entityData[1]),Float.parseFloat(entityData[2]));
                    Vector2 velocity = new Vector2(Float.parseFloat(entityData[3]),Float.parseFloat(entityData[4]));
                    Vector2 acceleration = new Vector2(Float.parseFloat(entityData[5]),Float.parseFloat(entityData[6]));
                    float angle = Float.parseFloat(entityData[7]);
                    float angularVelocity = Float.parseFloat(entityData[8]);
                    float sizeX = Float.parseFloat(entityData[9]);
                    float sizeY = Float.parseFloat(entityData[10]);
                    //create new entity.
                    switch (ID) {
                        case 1: ID = 1;
                            ship.setProperties(position,velocity,acceleration,angle,angularVelocity,sizeX,sizeY);
                            addEntity(ship);
                            break;
                        case 2: ID = 2;
                            Missile missile = new Missile(position,velocity,acceleration,angle,angularVelocity);
                            missile.setProperties(position,velocity,acceleration,angle,angularVelocity,sizeX,sizeY);
                            addEntity(missile);
                            break;
                        case 3: ID = 3;
                            Planet planet = new Planet(position.x,position.y,sizeX,sizeY);
                            planet.setProperties(position,velocity,acceleration,angle,angularVelocity,sizeX,sizeY);
                            addEntity(planet);
                            break;
                        case 4: ID = 4;
                            ScorePoint score = new ScorePoint(position.x,position.y);
                            score.setProperties(position,velocity,acceleration,angle,angularVelocity,sizeX,sizeY);
                            addEntity(score);
                            break;
                        case 5: ID = 5;
                            AlienShip alien = new AlienShip(position.x,position.y);
                            alien.setProperties(position,velocity,acceleration,angle,angularVelocity,sizeX,sizeY);
                            addEntity(alien);
                            break;
                        case 6: ID = 6;
                            AlienShipSpecial alienSpecial = new AlienShipSpecial(position.x,position.y);
                            alienSpecial.setProperties(position,velocity,acceleration,angle,angularVelocity,sizeX,sizeY);
                            addEntity(alienSpecial);
                            break;
                        case 7: ID = 7;
                            Laser laser = new Laser(position,velocity,acceleration,angle);
                            laser.setProperties(position,velocity,acceleration,angle,angularVelocity,sizeX,sizeY);
                            addEntity(laser);
                            break;
                    }
                }
            }
            fis.close();
        }catch(Exception e){}
    }
}

