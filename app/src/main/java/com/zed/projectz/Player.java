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

    public UUID Id;

    public String Name;

    public Race Race;

    public List<Race> RaceOptions;
}
