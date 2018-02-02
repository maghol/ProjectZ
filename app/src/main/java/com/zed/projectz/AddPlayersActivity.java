package com.zed.projectz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddPlayersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        bindInitialValues();
    }

    private void bindInitialValues(){
        SessionData sessionData = DataHolder.getInstance().getData();
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
        SessionData sessionData = DataHolder.getInstance().getData();
        List<Player> players = new ArrayList<>();
        View rootView = view.getRootView();
        String playerOne = ((EditText) rootView.findViewById(R.id.playerOneTextBox)).getText().toString();
        if (!playerOne.isEmpty()){
            players.add(new Player(UUID.randomUUID(), playerOne));
        }
        String playerTwo = ((EditText) rootView.findViewById(R.id.playerTwoTextBox)).getText().toString();
        if (!playerTwo.isEmpty()){
            players.add(new Player(UUID.randomUUID(), playerTwo));
        }
        String playerThree = ((EditText) rootView.findViewById(R.id.playerThreeTextBox)).getText().toString();
        if (!playerThree.isEmpty()){
            players.add(new Player(UUID.randomUUID(), playerThree));
        }
        String playerFour = ((EditText) rootView.findViewById(R.id.playerFourTextBox)).getText().toString();
        if (!playerFour.isEmpty()){
            players.add(new Player(UUID.randomUUID(), playerFour));
        }
        String playerFive = ((EditText) rootView.findViewById(R.id.playerFiveTextBox)).getText().toString();
        if (!playerFive.isEmpty()){
            players.add(new Player(UUID.randomUUID(), playerFive));
        }
        String playerSix = ((EditText) rootView.findViewById(R.id.playerSixTextBox)).getText().toString();
        if (!playerSix.isEmpty()){
            players.add(new Player(UUID.randomUUID(), playerSix));
        }
        sessionData.Players = players;
        DataHolder.getInstance().setData(sessionData);
        finish();
    }
}