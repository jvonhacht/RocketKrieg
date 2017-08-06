package com.game.worldGeneration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.ship.PlayerSpaceShip;

/**
 * Created by JohanvonHacht on 2017-08-06.
 */
public class ZoneManager {
    private PlayerSpaceShip ship;
    private static int alienKills;
    private static int alienSpecialKills;
    private static int scorePointsCollected;
    private int zone;
    private final int ZONESIZE = 5; //in tiles
    private Sprite tileImg;
    private boolean playerIsAlive;
    private String missionMessage;

    public ZoneManager(PlayerSpaceShip ship) {
        this.ship = ship;
        tileImg = AssetStorage.tile2;
        tileImg.setAlpha(0.5f);
        alienKills = 0;
        alienSpecialKills = 0;
        scorePointsCollected = 0;
        zone = 1;
        playerIsAlive = true;
    }

    /**
     * Update zone manager.
     */
    public void update() {
        checkMissionProgress();
        render();
        if(Math.abs(ship.position.x)> zone*ZONESIZE*Tile.TILE_SIZE || Math.abs(ship.position.y)> zone*ZONESIZE*Tile.TILE_SIZE) {
            playerIsAlive = false;
            ship.hit(true);
        }
    }

    /**
     * Render borders.
     */
    public void render() {
        int zoneLength = zone*ZONESIZE*Tile.TILE_SIZE;
        if((zoneLength-Math.abs(ship.position.x))< Gdx.graphics.getWidth()/2) {
            int start = (int)ship.position.y - Gdx.graphics.getHeight()/2 - Tile.TILE_SIZE;
            for (int i=start; i<start+Gdx.graphics.getHeight()+2*Tile.TILE_SIZE ; i+=Tile.TILE_SIZE) {
                Vector2 anchor = ChunkManager.getAnchor(new Vector2(zoneLength,i),Tile.TILE_SIZE);
                if(ship.position.x>0) {
                    GameEntry.batch.draw(tileImg, anchor.x, anchor.y);
                    GameEntry.batch.draw(tileImg, anchor.x+Tile.TILE_SIZE, anchor.y);
                }
                if(ship.position.x<0) {
                    GameEntry.batch.draw(tileImg, -anchor.x - Tile.TILE_SIZE, anchor.y);
                    GameEntry.batch.draw(tileImg, -anchor.x - 2*Tile.TILE_SIZE, anchor.y);
                }
            }
        }
        if((zoneLength-Math.abs(ship.position.y))< Gdx.graphics.getHeight()/2) {
            int start = (int)ship.position.x - Gdx.graphics.getWidth()/2 - 250;
            for (int i=start; i<start+Gdx.graphics.getWidth()+2*Tile.TILE_SIZE; i+=Tile.TILE_SIZE) {
                Vector2 anchor = ChunkManager.getAnchor(new Vector2(i,zoneLength),Tile.TILE_SIZE);
                if(ship.position.y>0) {
                    GameEntry.batch.draw(tileImg,anchor.x,anchor.y);
                    GameEntry.batch.draw(tileImg, anchor.x, anchor.y+Tile.TILE_SIZE);
                }
                if(ship.position.y<0) {
                    GameEntry.batch.draw(tileImg, anchor.x, -anchor.y - Tile.TILE_SIZE);
                    GameEntry.batch.draw(tileImg, anchor.x, -anchor.y - 2*Tile.TILE_SIZE);
                }
            }
        }
    }

    /**
     * Check progress of mission and open next zone if complete.
     */
    public void checkMissionProgress() {
        switch(zone) {
            case 1:
                missionMessage = "Kill " +  (5-alienKills) + " more aliens to progress to the next zone";
                if(alienKills > 4) {
                    //displayZoneCompleteMessage();
                    zone++;
                    resetKills();
                }
                break;
            case 2:
                missionMessage = "Kill " +  (5-alienKills)  + " more aliens and " +  (5-alienSpecialKills) + " more suicide aliens to progress";
                if(alienKills > 9 &&
                        alienSpecialKills > 5) {
                    zone ++;
                }
        }
    }

    /**
     * Reset kills count for each zone.
     */
    public void resetKills() {
        alienKills = 0;
        alienSpecialKills = 0;
        scorePointsCollected = 0;
    }

    /**
     * Add alien kills to counter
     * @param amount
     */
    public static void addAlienKills(int amount) {
        alienKills += amount;
    }

    /**
     * Add alien special kills to counter
     * @param amount
     */
    public static void addAlienSpecialKills(int amount) {
        alienSpecialKills += amount;
    }

    /**
     * Add score point to counter
     * @param amount
     */
    public static void addScorePointsCollected(int amount) {
        scorePointsCollected += amount;
    }

    /**
     * Get zone player is in.
     * @return zone.
     */
    public int getZone() {
        return zone;
    }

    /**
     * Get current mission message.
     * @return missionMessage
     */
    public String getMissionMessage() {
        return missionMessage;
    }

}
