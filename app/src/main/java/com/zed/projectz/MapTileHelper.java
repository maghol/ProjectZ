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
                    new MapTile(TileType.Obstacle, "../assets/AsteroidField_1.png", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/AsteroidField_2.png", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/SuperNova.png", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/GravityRift.png", 0, Ctx),
                    new MapTile(TileType.Obstacle, "../assets/Nebula.png", 0, Ctx)
            ));
            redMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Red, "../assets/OpenSpace_1.png", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace_2.png", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace_3.png", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace_4.png", 0, Ctx),
                    new MapTile(TileType.Red, "../assets/OpenSpace_5.png", 0, Ctx)
            ));
            redWormholeMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Red, "../assets/Alpha_Wormhole.png", 0, WormholeType.Alpha, Ctx),
                    new MapTile(TileType.Red, "../assets/Beta_Wormhole.png", 0, WormholeType.Beta, Ctx)
            ));
            blueMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Blue, "../assets/Abyz_Fria.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Lazar_Sakulag.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Qucenn_Rarron.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Arinam_Meer.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/NewAlbion_Starpoint.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Corneeq_Resculon.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/MeharXull.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Centauri_Gral.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Vefut.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/DalBootha_Xxehan.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Saudor.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Mellon_Zohbat.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/TequRan_Torkan.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Bereg_Lirta.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Wellon.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Arnor_Lor.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/Thibah.png", 0, Ctx),
                    new MapTile(TileType.Blue, "../assets/TarMann.png", 0, Ctx)
            ));
            blueWormholeMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.Blue, "../assets/Quann.png", 0, WormholeType.Alpha, Ctx),
                    new MapTile(TileType.Blue, "../assets/Lodor.png", 0, WormholeType.Beta, Ctx)
            ));
            startingMapTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.StartingPosition, "../assets/TrenLak_Quinarra.png", 0, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Nestphar.png", 1, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/ArcPrime_WrenTerra.png", 2, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Lisis_Ragh.png", 3, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Muaat.png", 4, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Arretze_Hercant_Kamdorn.png", 5, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Jord.png", 6, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Creuss.png", 7, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/000.png", 8, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/MollPrimus.png", 9, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Maaluuk_Druaa.png", 10, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Mordai.png", 11, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Nar_Jol.png", 12, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Winnu.png", 13, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/ArchonRen_ArchonTau.png", 14, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Darien.png", 15, Ctx),
                    new MapTile(TileType.StartingPosition, "../assets/Retillon_Shalloo.png", 16, Ctx)
            ));
            mecatolRexTiles = new ArrayList<>(Arrays.asList(
                    new MapTile(TileType.MecatolRex, "../assets/Mecatol_Rex.png", 0, Ctx)
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
