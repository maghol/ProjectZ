package com.zed.projectz;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class MapTile {
    public Coordinate coordinate;
    public TileType TypeOfTile;
    public String ImgSource;
    public int RaceId;
    public Bitmap Image;
    public WormholeType WormholeType;

    MapTile (TileType tileType, String imgSource, int raceId, Context ctx) throws IOException {
        initialize(tileType, imgSource, raceId, new Coordinate(-1, -1), WormholeType.None, ctx);
    }

    MapTile (TileType tileType, String imgSource, int raceId, WormholeType wormholeType, Context ctx) throws IOException {
        initialize(tileType, imgSource, raceId, new Coordinate(-1, -1), wormholeType, ctx);
    }

    MapTile (TileType tileType, String imgSource, int raceId, Coordinate inCoordinate, WormholeType wormholeType, Context ctx) throws IOException {
        initialize(tileType, imgSource, raceId, inCoordinate, wormholeType, ctx);
    }

    private void initialize(TileType tileType, String imgSource, int raceId, Coordinate inCoordinate, WormholeType wormholeType, Context ctx) throws IOException {
        coordinate = inCoordinate;
        TypeOfTile = tileType;
        ImgSource = imgSource;
        RaceId = raceId;
        WormholeType = wormholeType;
        final AssetManager assetManager = ctx.getAssets();
        if (imgSource != null) {
            String[] imageSourceSplit = ImgSource.split("/");
            final String imageName = imageSourceSplit[imageSourceSplit.length - 1];
            final InputStream in = assetManager.open(imageName);
            try {
                final Bitmap loaded = BitmapFactory.decodeStream(in);
                Image = loaded;
            } finally {
                in.close();
            }
        }
    }
}
