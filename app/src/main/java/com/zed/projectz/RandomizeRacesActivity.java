package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION;

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
        createPlayersView(sessionData.Players);
    }

    public void randomizeRaces(View view) {
        String numberOfRacesPerPlayerString = ((EditText) this.findViewById(R.id.numberOfRacesForEachPlayer)).getText().toString();
        int numberOfRacesPerPlayer = 1;
        if (!numberOfRacesPerPlayerString.isEmpty()){
            numberOfRacesPerPlayer = Integer.parseInt(numberOfRacesPerPlayerString);
        }
        createPlayersView(raceHelper.RandomizeRaces(sessionData.Players, numberOfRacesPerPlayer));
        // TODO: Save race options to players.
    }

    public void selectRaces(View view) {
        View rootView = view.getRootView();
        // TODO: Verkar inte sparas, why not?
        for (Player player : sessionData.Players) {
            ArrayList<View> checkBoxViews = new ArrayList<>();
            rootView.findViewsWithText(checkBoxViews, view.getContentDescription(), FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
            for (View checkBoxView : checkBoxViews) {
                CheckBox checkBox = (CheckBox) checkBoxView;
                if (checkBox.isChecked()) {
                    player.Race = raceHelper.getRaceById(Integer.parseInt(checkBox.getTag().toString()));
                    break;
                }
            }
        }
        finish();
    }

    private void createPlayersView(List<Player> players) {
        ViewGroup playersContainer = this.findViewById(R.id.playersLinearLayout);
        playersContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Player currentPlayer : players) {
            // Player layout
            View inflatedPlayerLayout = inflater.inflate(R.layout.randomize_race_player_view, null, false);
            TextView playerNameTextView = inflatedPlayerLayout.findViewById(R.id.playerName);
            playerNameTextView.setText(currentPlayer.Name);
            playersContainer.addView(inflatedPlayerLayout);
            // Race layout
            if (currentPlayer.RaceOptions != null && currentPlayer.RaceOptions.size() > 0) {
                for (Race race : currentPlayer.RaceOptions) {
                    View inflatedRaceLayout = inflater.inflate(R.layout.randomize_race_race_view, null, false);
                    TextView raceNameTextView = inflatedRaceLayout.findViewById(R.id.raceName);
                    raceNameTextView.setText(race.Name);
                    playersContainer.addView(inflatedRaceLayout);
                    CheckBox checkBox = inflatedRaceLayout.findViewById(R.id.selectRaceCheckBox);
                    checkBox.setContentDescription(currentPlayer.Id.toString());
                    checkBox.setTag(race.Id);
                    if (currentPlayer.Race != null && race.Id == currentPlayer.Race.Id) {
                        checkBox.setChecked(true);
                    }
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                checkBoxClicked(buttonView);
                            }
                        }
                    });
                }
            }
        }
    }

    public void checkBoxClicked(View view) {
        View rootView = view.getRootView();
        ArrayList<View> checkBoxViews = new ArrayList<>();
        rootView.findViewsWithText(checkBoxViews, view.getContentDescription(), FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        checkBoxViews.remove(view);
        for (View checkBoxView : checkBoxViews) {
            CheckBox checkBox = (CheckBox) checkBoxView;
            checkBox.setChecked(false);
        }
    }
}