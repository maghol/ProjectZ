package com.zed.projectz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SessionData sessionData = DataHolder.getInstance().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume(){
        super.onResume();
        initialize();
    }

    private void initialize() {
        if (sessionData.Players == null || sessionData.Players.size() < 3) {
            this.findViewById(R.id.randomizeRacesButton).setClickable(false);
        } else {
            this.findViewById(R.id.randomizeRacesButton).setClickable(true);
        }
    }

    public void addPlayers(View view) {
        startActivity(new Intent(this, AddPlayersActivity.class));
    }

    public void randomizeRaces(View view) {
        startActivity(new Intent(this, RandomizeRacesActivity.class));
    }

    public void generateMap(View view) {
        startActivity(new Intent(this, GenerateMapActivity.class));
    }

    public void rules(View view) {
        startActivity(new Intent(this, RulesActivity.class));
    }
}
