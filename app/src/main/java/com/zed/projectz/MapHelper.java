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
    private List<MapTile> schemeMapTiles;
    private String blankTileImageSource = "../assets/Solid_Black.png";
    Coordinate blueAlphaWormholeCoordinate;
    Coordinate blueBetaWormholeCoordinate;
    Coordinate redAlphaWormholeCoordinate;
    Coordinate redBetaWormholeCoordinate;
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
                    schemeMapTiles = getSchemeTiles(currentMapProperty, ctx);
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
                        break;
                    }
                }
            }
            if (mapTile.TypeOfTile != TileType.None) {
                mapTiles.add(mapTile);
            }
        }
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
                            MapTile randomMapTile = randomizeMapTile(tileType, new Coordinate(xIndex, yIndex), ctx);
                            mapTiles.add(new MapTile(tileType, randomMapTile.ImgSource, 0, schemeMapTile.coordinate, WormholeType.None, ctx));
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
        List<MapTile> tmp = new ArrayList<>();
        for (MapTile mapTile : mapTiles) {
            if (mapTile.WormholeType != WormholeType.None) {
                tmp.add(mapTile);
            }
        }
        return mapTiles;
    }

    private MapTile randomizeMapTile(TileType typeOfTile, Coordinate coordinate, Context ctx) {
        switch (typeOfTile) {
            case MecatolRex: {
                return mecatolRexMapTile;
            }
            case Blue: {
                return randomizeBlueTile(coordinate, ctx);
            }
            case Red: {
                return randomizeRedTile();
            }
            case Obstacle: {
                return randomizeObstacleTile();
            }
            case StartingPosition: {
                return randomizeStartingPositionTile();
            }
        }
        return null;
    }

    private MapTile randomizeBlueTile(Coordinate coordinate, Context ctx) {
        if (numberOfPlayers == 3) {
            for (Coordinate blankCoordinate : blankTilesForSmallMap) {
                if (blankCoordinate.Xcoordinate == coordinate.Xcoordinate
                        && blankCoordinate.Ycoordinate == coordinate.Ycoordinate) {
                    try {
                        return new MapTile(TileType.None, blankTileImageSource, -1, coordinate, WormholeType.None, ctx);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            for (Coordinate blankCoordinate : blankTilesForBigMap) {
                if (blankCoordinate.Xcoordinate == coordinate.Xcoordinate
                        && blankCoordinate.Ycoordinate == coordinate.Ycoordinate) {
                    try {
                        return new MapTile(TileType.None, blankTileImageSource, -1, coordinate, WormholeType.None, ctx);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Random random = new Random();
        int randomNumber = random.nextInt(blueMapTiles.size());
        MapTile mapTile = blueMapTiles.get(randomNumber);
        mapTile.coordinate = coordinate;
        if (coordinate.Xcoordinate == blueAlphaWormholeCoordinate.Xcoordinate
                && coordinate.Ycoordinate == blueAlphaWormholeCoordinate.Ycoordinate
                || coordinate.Xcoordinate == blueBetaWormholeCoordinate.Xcoordinate
                && coordinate.Ycoordinate == blueBetaWormholeCoordinate.Ycoordinate) {
            int wormholeRandomNumber = random.nextInt(blueWormholeMapTiles.size());
            mapTile = blueWormholeMapTiles.get(wormholeRandomNumber);
            for(MapTile wormholeMapTile : blueWormholeMapTiles) {
                if (wormholeMapTile.WormholeType == mapTile.WormholeType) {
                    mapTile.coordinate = coordinate;
                    blueWormholeMapTiles.remove(wormholeRandomNumber);
                    return mapTile;
                }
            }
        } else if (coordinate.Xcoordinate == redAlphaWormholeCoordinate.Xcoordinate
                && coordinate.Ycoordinate == redAlphaWormholeCoordinate.Ycoordinate
                || coordinate.Xcoordinate == redBetaWormholeCoordinate.Xcoordinate
                && coordinate.Ycoordinate == redBetaWormholeCoordinate.Ycoordinate) {
            int wormholeRandomNumber = random.nextInt(redWormholeMapTiles.size());
            mapTile = redWormholeMapTiles.get(wormholeRandomNumber);
            for(MapTile wormholeMapTile : redWormholeMapTiles) {
                if (wormholeMapTile.WormholeType == mapTile.WormholeType) {
                    mapTile.coordinate = coordinate;
                    redWormholeMapTiles.remove(wormholeRandomNumber);
                    return mapTile;
                }
            }
        } else {
            blueMapTiles.remove(randomNumber);
            return mapTile;
        }
        return null;
    }

    private MapTile randomizeRedTile() {
        Random random = new Random();
        int randomNumber = random.nextInt(redMapTiles.size());
        MapTile mapTile = redMapTiles.get(randomNumber);
        redMapTiles.remove(randomNumber);
        return mapTile;
    }

    private MapTile randomizeObstacleTile() {
        Random random = new Random();
        int randomNumber = random.nextInt(obstacleMapTiles.size());
        MapTile mapTile = obstacleMapTiles.get(randomNumber);
        obstacleMapTiles.remove(randomNumber);
        return mapTile;
    }

    private MapTile randomizeStartingPositionTile() {
        Random random = new Random();
        int randomNumber = random.nextInt(playersStartingRaces.size());
        int raceId = playersStartingRaces.get(randomNumber).Id;
        playersStartingRaces.remove(randomNumber);
        for (int i = 0; i < startingPositionMapTiles.size(); i++) {
            MapTile startingPositionMapTile = startingPositionMapTiles.get(i);
            if (startingPositionMapTile.RaceId == raceId) {
                startingPositionMapTiles.remove(i);
                // TODO: Check schemeTile StartingResource value.
                // TODO: Also somehow add a +2/+4 resource icon to the img in a five player game.
                return startingPositionMapTile;
            }
        }
        return null;
    }

    private void calculateWormholePositions() {
        List<Coordinate> blueMapTilesCoordinates;
        if (numberOfPlayers == 3) {
            blueMapTilesCoordinates = mapTileHelper.allTileCoordinatesForSmallMap;
            for (Coordinate blankTileCoordinate : blankTilesForSmallMap) {
                for (int coordinateIndex = 0; coordinateIndex < blueMapTilesCoordinates.size(); coordinateIndex++) {
                    Coordinate coordinate = blueMapTilesCoordinates.get(coordinateIndex);
                    if (coordinate.Xcoordinate == blankTileCoordinate.Xcoordinate
                            && coordinate.Ycoordinate == blankTileCoordinate.Ycoordinate) {
                        blueMapTilesCoordinates.remove(coordinateIndex);
                        break;
                    }
                }
            }
        } else {
            blueMapTilesCoordinates = mapTileHelper.allTileCoordinatesForLargeMap;
            for (Coordinate blankTileCoordinate : blankTilesForBigMap) {
                for (int coordinateIndex = 0; coordinateIndex < blueMapTilesCoordinates.size(); coordinateIndex++) {
                    Coordinate coordinate = blueMapTilesCoordinates.get(coordinateIndex);
                    if (coordinate.Xcoordinate == blankTileCoordinate.Xcoordinate
                            && coordinate.Ycoordinate == blankTileCoordinate.Ycoordinate) {
                        blueMapTilesCoordinates.remove(coordinateIndex);
                        break;
                    }
                }
            }
        }
        List<Coordinate> schemeMapTilesCoordinates = new ArrayList<>();
        for (MapTile schemeMapTile : schemeMapTiles) {
            schemeMapTilesCoordinates.add(schemeMapTile.coordinate);
        }
        for (MapTile schemeMapTile : schemeMapTiles) {
            for (int coordinateIndex = 0; coordinateIndex < blueMapTilesCoordinates.size(); coordinateIndex++) {
                Coordinate coordinate = blueMapTilesCoordinates.get(coordinateIndex);
                if (coordinate.Xcoordinate == schemeMapTile.coordinate.Xcoordinate
                        && coordinate.Ycoordinate == schemeMapTile.coordinate.Ycoordinate) {
                    blueMapTilesCoordinates.remove(coordinateIndex);
                    break;
                }
            }
        }
        Random random = new Random();
        int blueAlphaRandom = random.nextInt(blueMapTilesCoordinates.size());
        blueAlphaWormholeCoordinate = blueMapTilesCoordinates.get(blueAlphaRandom);
        blueMapTilesCoordinates.remove(blueAlphaRandom);
        int blueBetaRandom = random.nextInt(blueMapTilesCoordinates.size());
        blueBetaWormholeCoordinate = blueMapTilesCoordinates.get(blueBetaRandom);
        blueMapTilesCoordinates.remove(blueBetaRandom);
        while (true) {
            int redAlphaRandom = random.nextInt(blueMapTilesCoordinates.size());
            redAlphaWormholeCoordinate = blueMapTilesCoordinates.get(redAlphaRandom);
            if (checkWormholeCoordinate(blueAlphaWormholeCoordinate, redAlphaWormholeCoordinate)) {
                blueMapTilesCoordinates.remove(redAlphaRandom);
                break;
            }
        }
        while (true) {
            int redBetaRandom = random.nextInt(blueMapTilesCoordinates.size());
            redBetaWormholeCoordinate = blueMapTilesCoordinates.get(redBetaRandom);
            if (checkWormholeCoordinate(blueBetaWormholeCoordinate, redBetaWormholeCoordinate)) {
                blueMapTilesCoordinates.remove(redBetaRandom);
                break;
            }
        }
    }

    private boolean checkWormholeCoordinate(Coordinate exitingCoordinate, Coordinate newCoordinate) {
        int alphaX = exitingCoordinate.Xcoordinate;
        int alphaY = exitingCoordinate.Ycoordinate;
        int betaX = newCoordinate.Xcoordinate;
        int betaY = newCoordinate.Ycoordinate;
        // Top coordinate
        if (alphaX == betaX && alphaY == betaY - 1) {
            return false;
        }
        // Bottom coordinate
        if (alphaX == betaX && alphaY == betaY + 1) {
            return false;
        }
        if (betaX % 2 == 0) {
            // Top-left coordinate
            if (alphaX == betaX - 1 && alphaY == betaY - 1) {
                return false;
            }
            // Bottom-left coordinate
            if (alphaX == betaX - 1 && alphaY == betaY) {
                return false;
            }
            // Top-right coordinate
            if (alphaX == betaX + 1 && alphaY == betaY - 1) {
                return false;
            }
            // Bottom-right coordinate
            if (alphaX == betaX + 1 && alphaY == betaY) {
                return false;
            }
        } else {
            // Top-left coordinate
            if (alphaX == betaX - 1 && alphaY == betaY) {
                return false;
            }
            // Bottom-left coordinate
            if (alphaX == betaX - 1 && alphaY == betaY + 1) {
                return false;
            }
            // Top-right coordinate
            if (alphaX == betaX + 1 && alphaY == betaY) {
                return false;
            }
            // Bottom-right coordinate
            if (alphaX == betaX + 1 && alphaY == betaY + 1) {
                return false;
            }
        }
        return true;
    }
}