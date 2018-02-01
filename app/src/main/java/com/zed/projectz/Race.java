package com.zed.projectz;

public class Race implements Cloneable {
    public String Name;
    public int Id;

    Race(String name, int id) {
        Name = name;
        Id = id;
    }

    Race(Race race){
        Name = race.Name;
        Id = race.Id;
    }
}