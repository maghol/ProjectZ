package com.zed.projectz;

import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapHelper {
    private List<Player> players = DataHolder.getInstance().getData().Players;
    private int numberOfPlayers = players.size();
    private XmlHelper xmlHelper = new XmlHelper();
    private MapTileHelper mapTileHelper = new MapTileHelper();
    private List<MapTile> blueMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.Blue);
    private List<MapTile> redMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.Red);
    private List<MapTile> obstacleMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.Obstacle);
    private List<MapTile> startingPositionMapTiles = mapTileHelper.listMapTilesOfGivenTileType(TileType.StartingPosition);
    private MapTile mecatolRexMapTile = mapTileHelper.listMapTilesOfGivenTileType(TileType.MecatolRex).get(0);

    public Map generateMap(AssetManager assetManager){
        String mapsXmlName;
        switch (numberOfPlayers){
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
        return generatePlayerMap(xmlHelper.parseXML(mapsXmlName, assetManager));
    }

    private Map generatePlayerMap(Document document) {
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
                    List<MapTile> schemeMapTiles = getSchemeTiles(currentMapProperty);
                    map.MapTiles = randomizeMapTiles(schemeMapTiles);
                    break;
                }
            }
        }
        return map;
    }

    private List<MapTile> getSchemeTiles(Node mapProperty) {
        List<MapTile> mapTiles = new ArrayList<>();
        NodeList tileNodes = mapProperty.getChildNodes();
        for (int tileIndex = 0; tileIndex < tileNodes.getLength(); tileIndex++) {
            MapTile mapTile = new MapTile(TileType.None, null, 0);
            Node tileNode = tileNodes.item(tileIndex);
            NodeList tileProperties = tileNode.getChildNodes();
            for (int propertyIndex = 0; propertyIndex < tileProperties.getLength(); propertyIndex++) {
                Node tileProperty = tileProperties.item(propertyIndex);
                switch (tileProperty.getNodeName()) {
                    case "position": {
                        mapTile.Position = Integer.parseInt(tileProperty.getTextContent());
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

    private List<MapTile> randomizeMapTiles(List<MapTile> schemeMapTiles) {
        int numberOfTiles = 28;
        if (numberOfPlayers != 3) {
            numberOfTiles = 37;
        }
        List<MapTile> mapTiles = new ArrayList<>();
        for (int i = 0; i < numberOfTiles; i++) {
            MapTile mapTile = new MapTile(TileType.None, null, 0);
            for (MapTile schemeMapTile : schemeMapTiles) {
                if (schemeMapTile.Position == i + 1) {
                    mapTile.TypeOfTile = schemeMapTile.TypeOfTile;
                    mapTile.ImgSource = randomizeMapTile(schemeMapTile.TypeOfTile, i + 1);
                    break;
                }
            }
            if (mapTile.TypeOfTile == TileType.None) {
                mapTile.Position = i + 1;
                mapTile.TypeOfTile = TileType.Blue;
                mapTile.ImgSource = randomizeMapTile(TileType.Blue, i + 1);
            }
            mapTiles.add(mapTile);
        }
        return mapTiles;
    }

    private String randomizeMapTile(TileType typeOfTile, int position) {
        // TODO: Handle wormholes not ending up next to each other.
        Random random = new Random();
        switch (typeOfTile) {
            case MecatolRex: {
                return mecatolRexMapTile.ImgSource;
            }
            case Blue: {
                int randomNumber = random.nextInt(blueMapTiles.size());
                MapTile mapTile = blueMapTiles.get(randomNumber);
                blueMapTiles.remove(randomNumber);
                return mapTile.ImgSource;
            }
            case Red: {
                int randomNumber = random.nextInt(redMapTiles.size());
                MapTile mapTile = redMapTiles.get(randomNumber);
                redMapTiles.remove(randomNumber);
                return mapTile.ImgSource;
            }
            case Obstacle: {
                int randomNumber = random.nextInt(obstacleMapTiles.size());
                MapTile mapTile = obstacleMapTiles.get(randomNumber);
                obstacleMapTiles.remove(randomNumber);
                return mapTile.ImgSource;
            }
            case StartingPosition: {
                int randomNumber = random.nextInt(players.size());
                int raceId = players.get(randomNumber).Race.Id;
                for (int i = 0; i < startingPositionMapTiles.size(); i++) {
                    MapTile startingPositionMapTile = startingPositionMapTiles.get(i);
                    if (startingPositionMapTile.RaceId == raceId) {
                        String imgSource = startingPositionMapTile.ImgSource;
                        startingPositionMapTiles.remove(i);
                        if (players.size() == 5) {
                            // TODO: check schemeTile StartingRecource value.
                            if (position == 1 || position == 37) {
                                // TODO: Also somehow add a +2 resource icon to the img.
                            } else if (position == 16) {
                                // TODO: Also somehow add a +4 resource icon to the img.
                            }
                        }
                        return imgSource;
                    }
                }
            }
        }
        return "No tile img src";
    }
}