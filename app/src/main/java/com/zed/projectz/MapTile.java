package com.zed.projectz;

public class MapTile {
    public int Position;
    public TileType TypeOfTile;
    public String ImgSource;
    public int RaceId;

    MapTile (TileType tileType, String imgSource, int raceId){
        Position = 0;
        TypeOfTile = tileType;
        ImgSource = imgSource;
        RaceId = raceId;
    }
}
