package com.zed.projectz;

public class Race implements Cloneable {
    public String Name;
    public int Id;
    public int RaceIconId;

    Race(String name, int id, int raceIconId) {
        Name = name;
        Id = id;
        RaceIconId = raceIconId;
    }

    Race(Race race){
        Name = race.Name;
        Id = race.Id;
        RaceIconId = race.RaceIconId;
    }
}