package com.zed.projectz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapTileHelper {
    private List<MapTile> obstacleMapTiles = new ArrayList<>(Arrays.asList(
            new MapTile(TileType.Obstacle, "../res/drawable/btn_plus.xml", 0),
            new MapTile(TileType.Obstacle, "../res/drawable/btn_plus.xml", 0),
            new MapTile(TileType.Obstacle, "../res/drawable/btn_plus.xml", 0),
            new MapTile(TileType.Obstacle, "../res/drawable/btn_plus.xml", 0),
            new MapTile(TileType.Obstacle, "../res/drawable/btn_plus.xml", 0)
    ));

    private List<MapTile> redMapTiles = new ArrayList<>(Arrays.asList(
            new MapTile(TileType.Red, "../res/drawable/btn_minus.xml", 0),
            new MapTile(TileType.Red, "../res/drawable/btn_minus.xml", 0),
            new MapTile(TileType.Red, "../res/drawable/btn_minus.xml", 0),
            new MapTile(TileType.Red, "../res/drawable/btn_minus.xml", 0),
            new MapTile(TileType.Red, "../res/drawable/btn_minus.xml", 0),
            new MapTile(TileType.Red, "../res/drawable/btn_minus.xml", 0),
            new MapTile(TileType.Red, "../res/drawable/btn_minus.xml", 0)
            ));

    private List<MapTile> blueMapTiles = new ArrayList<>(Arrays.asList(
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0),
            new MapTile(TileType.Blue, "../res/drawable/btn_check.xml", 0)
            ));

    private List<MapTile> startingMapTiles = new ArrayList<>(Arrays.asList(
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 0),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 1),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 2),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 3),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 4),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 5),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 6),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 7),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 8),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 9),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 10),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 11),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 12),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 13),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 14),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 15),
            new MapTile(TileType.StartingPosition, "../res/drawable/btn_circle.xml", 16)
            ));

    private List<MapTile> mecatolRexTiles = new ArrayList<>(Arrays.asList(
            new MapTile(TileType.MecatolRex, "../res/drawable/btn_star.xml", 0)
    ));

    public List<MapTile> listMapTilesOfGivenTileType(TileType tileType) {
        switch (tileType) {
            case Obstacle: {
                return obstacleMapTiles;
            }
            case Red: {
                return redMapTiles;
            }
            case StartingPosition: {
                return startingMapTiles;
            }
            case MecatolRex: {
                return mecatolRexTiles;
            }
            case Blue: {
                return blueMapTiles;
            }
            default: {
                List<MapTile> allMapTiles = blueMapTiles;
                allMapTiles.addAll(redMapTiles);
                allMapTiles.addAll(startingMapTiles);
                allMapTiles.addAll(mecatolRexTiles);
                allMapTiles.addAll(obstacleMapTiles);
                return allMapTiles;
            }
        }
    }
}
