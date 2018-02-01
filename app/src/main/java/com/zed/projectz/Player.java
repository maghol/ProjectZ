package com.zed.projectz;

import java.util.ArrayList;
import java.util.List;

public class Player {
    Player (String name){
        Name = name;
        RaceOptions = new ArrayList<>();
    }

    public String Name;

    public List<Race> Race;

    public List<Race> RaceOptions;
}
