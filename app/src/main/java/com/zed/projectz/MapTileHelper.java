package com.zed.projectz;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapTileHelper {
    private Context Ctx;

    private List<MapTile> obstacleMapTiles = new ArrayList<>();

    private List<MapTile> redMapTiles = new ArrayList<>();

    private List<MapTile> redWormholeMapTiles = new ArrayList<>();

    private List<MapTile> blueMapTiles = new ArrayList<>();

    private List<MapTile> blueWormholeMapTiles = new ArrayList<>();

    private List<MapTile> startingMapTiles = new ArrayList<>();

    private List<MapTile> mecatolRexTiles = new ArrayList<>();

    public List<Coordinate> allTileCoordinatesForSmallMap = new ArrayList<>();

    public List<Coordinate> allTileCoordinatesForLargeMap = new ArrayList<>();

    MapTileHelper(Context ctx) throws IOException {
        Ctx = ctx;
        initializeTileLists();
    }

    private void initializeTileLists() {
        try {
            obstacleMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Obstacle, "../assets/Obstacle.jpg", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/Obstacle.jpg", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/Obstacle.jpg", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/Obstacle.jpg", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/Obstacle.jpg", 0, Ctx)
            ));
            redMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Red, "../assets/OpenSpace.PNG", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace.PNG", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace.PNG", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace.PNG", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace.PNG", 0, Ctx)
            ));
            redWormholeMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Red, "../assets/A-Wormhole.PNG", 0, WormholeType.Alpha, Ctx),
                    new MapTile(TileType.Red, "../assets/B-Wormhole.png", 0, WormholeType.Beta, Ctx)
            ));
            blueMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Blue.jpg", 0, Ctx)
            ));
            blueWormholeMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Blue, "../assets/BlueAlphaWormhole.jpg", 0, WormholeType.Alpha, Ctx),
                    new MapTile(TileType.Blue, "../assets/BlueBetaWormhole.jpg", 0, WormholeType.Beta, Ctx)
            ));
            startingMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 0, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 1, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 2, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 3, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 4, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 5, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 6, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 7, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 8, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 9, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 10, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 11, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 12, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 13, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 14, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 15, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/StartingPosition.jpg", 16, Ctx)
            ));
            mecatolRexTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.MecatolRex, "../assets/MecatolRex.PNG", 0, Ctx)
            ));
            for (int xIndex = 0; xIndex < 7; xIndex++) {
                for (int yIndex = 0; yIndex < 7; yIndex++) {
                    if (yIndex < 6) {
                        allTileCoordinatesForSmallMap.add(new Coordinate(xIndex, yIndex));
                    }
                    allTileCoordinatesForLargeMap.add(new Coordinate(xIndex, yIndex));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public List<MapTile> listWormholeMapTilesOfGivenTileType(TileType tileType) {
        switch (tileType) {
            case Red: {
                return redWormholeMapTiles;
            }
            case Blue: {
                return blueWormholeMapTiles;
            }
            default: {
                List<MapTile> allWormholeMapTiles = blueWormholeMapTiles;
                allWormholeMapTiles.addAll(redWormholeMapTiles);
                return allWormholeMapTiles;
            }
        }
    }
}
