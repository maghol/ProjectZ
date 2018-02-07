package com.zed.projectz;

import android.content.Context;
import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapHelper {
    private SessionData sessionData = DataHolder.getInstance().getData();
    private List<Player> players = DataHolder.getInstance().getData().Players;
    private int numberOfPlayers = players.size();
    private XmlHelper xmlHelper = new XmlHelper();
    private MapTileHelper mapTileHelper;
    private List<MapTile> blueMapTiles;
    private List<MapTile> blueWormholeMapTiles;
    private List<MapTile> redMapTiles;
    private List<MapTile> redWormholeMapTiles;
    private List<MapTile> obstacleMapTiles;
    private List<MapTile> startingPositionMapTiles;
    private MapTile mecatolRexMapTile;
    private List<Race> playersStartingRaces;
    private String blankTileImageSource = "../assets/Solid_Black.png";
    private int numberOfBlueTilesAddedToMap = 0;
    private int numberOfRedTilesAddedToMap = 0;
    private int totalNumberOfBlueTilesInMap = 0;
    private int totalNumberOfRedTilesInMap = 0;
    private int totalNumberOfTilesForSmallMap = 28;
    private int totalNumberOfTilesForLargeMap = 37;
    int blueAlphaWormholeIndex;
    int blueBetaWormholeIndex;
    int redAlphaWormholeIndex;
    int redBetaWormholeIndex;
    private List<Coordinate> blankTilesForSmallMap = new ArrayList<>(Arrays.asList(
            new Coordinate(0, 0),
            new Coordinate(0, 3),
            new Coordinate(0, 4),
            new Coordinate(0, 5),
            new Coordinate(1, 4),
            new Coordinate(1, 5),
            new Coordinate(2, 0),
            new Coordinate(4, 0),
            new Coordinate(5, 4),
            new Coordinate(5, 5),
            new Coordinate(6, 0),
            new Coordinate(6, 3),
            new Coordinate(6, 4),
            new Coordinate(6, 5)
    ));
    private List<Coordinate> blankTilesForBigMap = new ArrayList<>(Arrays.asList(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(0, 6),
            new Coordinate(1, 0),
            new Coordinate(1, 6),
            new Coordinate(2, 0),
            new Coordinate(4, 0),
            new Coordinate(5, 0),
            new Coordinate(5, 6),
            new Coordinate(6, 0),
            new Coordinate(6, 1),
            new Coordinate(6, 6)
    ));

    private void bindTileLists(Context ctx) {
        try {
            mapTileHelper = new MapTileHelper(ctx);
        } catch (IOException e) {
            e.printStackTrace();
        }
        blueMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.Blue);
        blueWormholeMapTiles = mapTileHelper.listWormholeMapTilesOfGivenTileType(TileType.Blue);
        redMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.Red);
        redWormholeMapTiles = mapTileHelper.listWormholeMapTilesOfGivenTileType(TileType.Red);
        obstacleMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.Obstacle);
        startingPositionMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.StartingPosition);
        mecatolRexMapTile = mapTileHelper.listMapTilesOfGivenTileType(TileType.MecatolRex).get(0);
        playersStartingRaces = new ArrayList<>();
        for (Player player : players) {
            playersStartingRaces.add(player.Race);
        }
    }

    public Map generateMap(AssetManager assetManager, Context ctx){
        String mapsXmlName;
        switch (numberOfPlayers) {
            case 3: {
                mapsXmlName = "maps_three_players.xml";
                break;
            }
            case 4: {
                mapsXmlName = "maps_four_players.xml";
                break;
            }
            case 5: {
                mapsXmlName = "maps_five_players.xml";
                break;
            }
            default: {
                mapsXmlName = "maps_six_players.xml";
            }
        }
        bindTileLists(ctx);
        return sessionData.Map = generatePlayerMap(xmlHelper.parseXML(mapsXmlName, assetManager), ctx);
    }

    private Map generatePlayerMap(Document document, Context ctx) {
        NodeList mapsNodes = document.getElementsByTagName("map");
        Random random = new Random();
        int randomNumber = random.nextInt(mapsNodes.getLength());
        Node mapNode = mapsNodes.item(randomNumber);
        Map map = new Map();
        map.NumberOfPlayers = numberOfPlayers;
        NodeList mapNodeChildren = mapNode.getChildNodes();
        for (int childIndex = 0; childIndex < mapNodeChildren.getLength(); childIndex++) {
            Node currentMapProperty = mapNodeChildren.item(childIndex);
            switch (currentMapProperty.getNodeName()){
                case "id": {
                    map.Id = currentMapProperty.getTextContent();
                    break;
                }
                case "tiles": {
                    List<MapTile> schemeMapTiles = getSchemeTiles(currentMapProperty, ctx);
                    map.MapTiles = randomizeMapTiles(schemeMapTiles, ctx);
                    break;
                }
            }
        }
        return map;
    }

    private List<MapTile> getSchemeTiles(Node mapProperty, Context ctx) {
        List<MapTile> mapTiles = new ArrayList<>();
        NodeList tileNodes = mapProperty.getChildNodes();
        for (int tileIndex = 0; tileIndex < tileNodes.getLength(); tileIndex++) {
            MapTile mapTile;
            try {
                mapTile = new MapTile(TileType.None, null, 0, ctx);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Node tileNode = tileNodes.item(tileIndex);
            NodeList tileProperties = tileNode.getChildNodes();
            for (int propertyIndex = 0; propertyIndex < tileProperties.getLength(); propertyIndex++) {
                Node tileProperty = tileProperties.item(propertyIndex);
                switch (tileProperty.getNodeName()) {
                    case "x-coordinate": {
                        mapTile.coordinate.Xcoordinate = Integer.parseInt(tileProperty.getTextContent());
                        break;
                    }
                    case "y-coordinate": {
                        mapTile.coordinate.Ycoordinate = Integer.parseInt(tileProperty.getTextContent());
                        break;
                    }
                    case "type": {
                        mapTile.TypeOfTile = convertStringToTileType(tileProperty.getTextContent());
                        if (mapTile.TypeOfTile == TileType.Red) {
                            totalNumberOfRedTilesInMap++;
                        }
                        break;
                    }
                }
            }
            if (mapTile.TypeOfTile != TileType.None) {
                mapTiles.add(mapTile);
            }
        }
        totalNumberOfBlueTilesInMap = sessionData.Players.size() == 3
                ? totalNumberOfTilesForSmallMap - mapTiles.size()
                : totalNumberOfTilesForLargeMap - mapTiles.size();
        return mapTiles;
    }

    private TileType convertStringToTileType(String stringTileType) {
        switch (stringTileType.toLowerCase()){
            case "obstacle": {
                return TileType.Obstacle;
            }
            case "red": {
                return TileType.Red;
            }
            case "mecatolrex": {
                return TileType.MecatolRex;
            }
            case "startingposition": {
                return TileType.StartingPosition;
            }
            default: {
                return TileType.Blue;
            }
        }
    }

    private List<MapTile> randomizeMapTiles(List<MapTile> schemeMapTiles, Context ctx) {
        int xMax = 7;
        int yMax = numberOfPlayers == 3 ? 6 : 7;
        List<MapTile> mapTiles = new ArrayList<>();
        calculateWormholePositions();
        for (int xIndex = 0; xIndex < xMax; xIndex++) {
            for (int yIndex = 0; yIndex < yMax; yIndex++) {
                TileType tileType = TileType.None;
                for (MapTile schemeMapTile : schemeMapTiles) {
                    if (schemeMapTile.coordinate.Xcoordinate == xIndex && schemeMapTile.coordinate.Ycoordinate == yIndex) {
                        tileType = schemeMapTile.TypeOfTile;
                        try {
                            mapTiles.add(new MapTile(tileType, randomizeMapTile(tileType, new Coordinate(xIndex, yIndex), ctx).ImgSource, 0, schemeMapTile.coordinate, WormholeType.None, ctx));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                if (tileType == TileType.None) {
                    Coordinate coordinate = new Coordinate(xIndex, yIndex);
                    MapTile randomMapTile = randomizeMapTile(TileType.Blue, coordinate, ctx);
                    if (blankTileImageSource.equals(randomMapTile.ImgSource)) {
                        randomMapTile.TypeOfTile = TileType.None;
                    }
                    mapTiles.add(randomMapTile);
                }
            }
        }
        return mapTiles;
    }

    private MapTile randomizeMapTile(TileType typeOfTile, Coordinate coordinate, Context ctx) {
        Random random = new Random();
        switch (typeOfTile) {
            case MecatolRex: {
                return mecatolRexMapTile;
            }
            case Blue: {
                if (numberOfPlayers == 3) {
                    for (Coordinate blankCoordinate : blankTilesForSmallMap) {
                        if (blankCoordinate.equals(coordinate)) {
                            try {
                                return new MapTile(TileType.None, blankTileImageSource, -1, ctx);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    for (Coordinate blankCoordinate : blankTilesForBigMap) {
                        if (blankCoordinate.equals(coordinate)) {
                            try {
                                return new MapTile(TileType.None, blankTileImageSource, -1, ctx);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (numberOfBlueTilesAddedToMap == blueAlphaWormholeIndex) {
                    for(MapTile mapTile : blueWormholeMapTiles) {
                        if (mapTile.WormholeType == WormholeType.Alpha) {
                            numberOfBlueTilesAddedToMap++;
                            mapTile.coordinate = coordinate;
                            return mapTile;
                        }
                    }
                } else if (numberOfBlueTilesAddedToMap == blueBetaWormholeIndex) {
                    for(MapTile mapTile : blueWormholeMapTiles) {
                        if (mapTile.WormholeType == WormholeType.Beta) {
                            numberOfBlueTilesAddedToMap++;
                            mapTile.coordinate = coordinate;
                            return mapTile;
                        }
                    }
                } else {
                    int randomNumber = random.nextInt(blueMapTiles.size());
                    MapTile mapTile = blueMapTiles.get(randomNumber);
                    mapTile.coordinate = coordinate;
                    blueMapTiles.remove(randomNumber);
                    numberOfBlueTilesAddedToMap++;
                    return mapTile;
                }
            }
            case Red: {
                if (numberOfRedTilesAddedToMap == redAlphaWormholeIndex) {
                    for(MapTile mapTile : redWormholeMapTiles) {
                        if (mapTile.WormholeType == WormholeType.Alpha) {
                            numberOfRedTilesAddedToMap++;
                            return mapTile;
                        }
                    }
                } else if (numberOfRedTilesAddedToMap == redBetaWormholeIndex) {
                    for(MapTile mapTile : redWormholeMapTiles) {
                        if (mapTile.WormholeType == WormholeType.Beta) {
                            numberOfRedTilesAddedToMap++;
                            return mapTile;
                        }
                    }
                } else {
                    int randomNumber = random.nextInt(redMapTiles.size());
                    MapTile mapTile = redMapTiles.get(randomNumber);
                    redMapTiles.remove(randomNumber);
                    numberOfRedTilesAddedToMap++;
                    return mapTile;
                }
            }
            case Obstacle: {
                int randomNumber = random.nextInt(obstacleMapTiles.size());
                MapTile mapTile = obstacleMapTiles.get(randomNumber);
                obstacleMapTiles.remove(randomNumber);
                return mapTile;
            }
            case StartingPosition: {
                int randomNumber = random.nextInt(playersStartingRaces.size());
                int raceId = playersStartingRaces.get(randomNumber).Id;
                playersStartingRaces.remove(randomNumber);
                for (int i = 0; i < startingPositionMapTiles.size(); i++) {
                    MapTile startingPositionMapTile = startingPositionMapTiles.get(i);
                    if (startingPositionMapTile.RaceId == raceId) {
                        startingPositionMapTiles.remove(i);
                        // TODO: check schemeTile StartingResource value.
                        // TODO: Also somehow add a +2/+4 resource icon to the img in a five player game.
                        return startingPositionMapTile;
                    }
                }
            }
        }
        return null;
    }

    private void calculateWormholePositions() {
        // TODO: Fix rule of "next to each other".
        Random random = new Random();
        blueAlphaWormholeIndex = random.nextInt(totalNumberOfBlueTilesInMap);
        blueBetaWormholeIndex = random.nextInt(totalNumberOfBlueTilesInMap);
        redAlphaWormholeIndex = random.nextInt(totalNumberOfRedTilesInMap);
        redBetaWormholeIndex = random.nextInt(totalNumberOfRedTilesInMap);
    }
}