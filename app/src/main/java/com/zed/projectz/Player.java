package com.zed.projectz;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    Player (UUID id, String name){
        Id = id;
        Name = name;
        RaceOptions = new ArrayList<>();
    }

    Player (UUID id, String name, Race race, List<Race> raceOptions){
        Id = id;
        Name = name;
        Race = race;
        RaceOptions = raceOptions;
    }

    public UUID Id;

    public String Name;

    public Race Race;

    public List<Race> RaceOptions;
}
