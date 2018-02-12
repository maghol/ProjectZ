package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddPlayersActivity extends AppCompatActivity {

    private SessionData sessionData = DataHolder.getInstance().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        bindInitialValues();
    }

    private void bindInitialValues(){
        int numberOfPlayers = sessionData.Players.size();
        if (numberOfPlayers < 1){
            return;
        }
        ((EditText)this.findViewById(R.id.playerOneTextBox)).setText(sessionData.Players.get(0).Name);
        if (numberOfPlayers >= 2){
            ((EditText)this.findViewById(R.id.playerTwoTextBox)).setText(sessionData.Players.get(1).Name);
        }
        if (numberOfPlayers >= 3){
            ((EditText)this.findViewById(R.id.playerThreeTextBox)).setText(sessionData.Players.get(2).Name);
        }
        if (numberOfPlayers >= 4){
            EditText playerFourTextBox = this.findViewById(R.id.playerFourTextBox);
            playerFourTextBox.setVisibility(View.VISIBLE);
            playerFourTextBox.setText(sessionData.Players.get(3).Name);
        }
        if (numberOfPlayers >= 5){
            EditText playerFiveTextBox = this.findViewById(R.id.playerFiveTextBox);
            playerFiveTextBox.setVisibility(View.VISIBLE);
            playerFiveTextBox.setText(sessionData.Players.get(4).Name);
        }
        if (numberOfPlayers >= 6){
            EditText playerSixTextBox = this.findViewById(R.id.playerSixTextBox);
            playerSixTextBox.setVisibility(View.VISIBLE);
            playerSixTextBox.setText(sessionData.Players.get(5).Name);
        }
    }

    public void addPlayer(View view){
        View rootView = view.getRootView();
        EditText playerFourTextBox = rootView.findViewById(R.id.playerFourTextBox);
        EditText playerFiveTextBox = rootView.findViewById(R.id.playerFiveTextBox);
        EditText playerSixTextBox = rootView.findViewById(R.id.playerSixTextBox);
        if (!playerFourTextBox.isShown()){
            playerFourTextBox.setVisibility(View.VISIBLE);
        } else if (!playerFiveTextBox.isShown()){
            playerFiveTextBox.setVisibility(View.VISIBLE);
        } else if (!playerSixTextBox.isShown()){
            playerSixTextBox.setVisibility(View.VISIBLE);
            view.findViewById(R.id.addPlayerButton).setClickable(false);
        }
    }

    public void savePlayers(View view){
        View rootView = view.getRootView();
        String playerOne = ((EditText) rootView.findViewById(R.id.playerOneTextBox)).getText().toString();
        String playerTwo = ((EditText) rootView.findViewById(R.id.playerTwoTextBox)).getText().toString();
        String playerThree = ((EditText) rootView.findViewById(R.id.playerThreeTextBox)).getText().toString();
        String playerFour = ((EditText) rootView.findViewById(R.id.playerFourTextBox)).getText().toString();
        String playerFive = ((EditText) rootView.findViewById(R.id.playerFiveTextBox)).getText().toString();
        String playerSix = ((EditText) rootView.findViewById(R.id.playerSixTextBox)).getText().toString();
        if (sessionData.Players == null || sessionData.Players.size() == 0) {
            if (!playerOne.isEmpty()){
                sessionData.Players.add(new Player(UUID.randomUUID(), playerOne));
            }
            if (!playerTwo.isEmpty()){
                sessionData.Players.add(new Player(UUID.randomUUID(), playerTwo));
            }
            if (!playerThree.isEmpty()){
                sessionData.Players.add(new Player(UUID.randomUUID(), playerThree));
            }
            if (!playerFour.isEmpty()){
                sessionData.Players.add(new Player(UUID.randomUUID(), playerFour));
            }
            if (!playerFive.isEmpty()){
                sessionData.Players.add(new Player(UUID.randomUUID(), playerFive));
            }
            if (!playerSix.isEmpty()){
                sessionData.Players.add(new Player(UUID.randomUUID(), playerSix));
            }
        } else {
            List<String> playerNames = new ArrayList<>();
            if (!playerOne.isEmpty()){
                playerNames.add(playerOne);
            }
            if (!playerTwo.isEmpty()){
                playerNames.add(playerTwo);
            }
            if (!playerThree.isEmpty()){
                playerNames.add(playerThree);
            }
            if (!playerFour.isEmpty()){
                playerNames.add(playerFour);
            }
            if (!playerFive.isEmpty()){
                playerNames.add(playerFive);
            }
            if (!playerSix.isEmpty()){
                playerNames.add(playerSix);
            }
            for (int i = 0; i < playerNames.size(); i++) {
                if (sessionData.Players.size() < i + 1) {
                    sessionData.Players.add(new Player(UUID.randomUUID(), playerNames.get(i)));
                } else {
                    Player sessionPlayer = sessionData.Players.get(i);
                    if (sessionPlayer == null) {
                        sessionData.Players.set(i, new Player(UUID.randomUUID(), playerNames.get(i)));
                    } else {
                        sessionData.Players.set(i, new Player(sessionPlayer.Id, playerNames.get(i), sessionPlayer.Race, sessionPlayer.RaceOptions));
                    }
                }
            }
        }
        finish();
    }
}