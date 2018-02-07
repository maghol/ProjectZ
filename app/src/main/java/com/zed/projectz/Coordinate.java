package com.zed.projectz;

public class Coordinate {
    public int Xcoordinate;
    public int Ycoordinate;

    Coordinate(int xCoordinate, int yCoordinate) {
        Xcoordinate = xCoordinate;
        Ycoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() == Coordinate.class.getClass()) {
            return false;
        }
        Coordinate inCoordinate = (Coordinate) o;
        return Xcoordinate == inCoordinate.Xcoordinate && Ycoordinate == inCoordinate.Ycoordinate;
    }
}
