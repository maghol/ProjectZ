package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class RandomizeRacesActivity extends AppCompatActivity {

    private SessionData sessionData = DataHolder.getInstance().getData();
    private RaceHelper raceHelper = new RaceHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize_races);
        initialize();
    }

    private void initialize() {
        ViewGroup playersContainer = this.findViewById(R.id.playersLinearLayout);
        for (int i = 0; i < sessionData.Players.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(sessionData.Players.get(i).Name + "\n");
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            playersContainer.addView(textView);
        }
    }

    public void randomizeRaces(View view) {
        String numberOfRacesPerPlayerString = ((EditText) this.findViewById(R.id.numberOfRacesForEachPlayer)).getText().toString();
        int numberOfRacesPerPlayer = 1;
        if (numberOfRacesPerPlayerString != null && !numberOfRacesPerPlayerString.isEmpty()){
            numberOfRacesPerPlayer = Integer.parseInt(numberOfRacesPerPlayerString);
        }
        List<Player> players = raceHelper.RandomizeRaces(sessionData.Players, numberOfRacesPerPlayer);
        ViewGroup playersContainer = this.findViewById(R.id.playersLinearLayout);
        playersContainer.removeAllViews();
        for (int i = 0; i < sessionData.Players.size(); i++) {
            Player currentPlayer = players.get(i);
            TextView textView = new TextView(this);
            String races = "";
            for (int i2 = 0; i2 < currentPlayer.RaceOptions.size(); i2++) {
                races += "\n";
                races += currentPlayer.RaceOptions.get(i2).Name;
            }
            textView.setText(currentPlayer.Name + races + "\n");
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            playersContainer.addView(textView);
        }
    }
}
