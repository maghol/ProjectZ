package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GenerateMapActivity extends AppCompatActivity {

    private MapHelper mapHelper = new MapHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_map);
        initialize();
    }

    private void initialize() {
        SessionData sessionData = DataHolder.getInstance().getData();
        if (sessionData.Map == null) {
            displayMap(mapHelper.generateMap(getAssets()));
        }
    }

    public void generateNewMap(View view) {
        displayMap(mapHelper.generateMap(getAssets()));
    }

    private void displayMap(Map map) {
        // TODO: Display the map somehow.

    }
}
