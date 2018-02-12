package com.zed.projectz;

import java.util.ArrayList;

public class DataHolder {
    private SessionData sessionData;
    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {
        return holder;
    }

    DataHolder(){
        sessionData = new SessionData();
        sessionData.Players = new ArrayList<>();
        sessionData.Map = new Map();

    }

    public SessionData getData() { return sessionData; }

    public void setData(SessionData data) {
        this.sessionData = data;
    }
}