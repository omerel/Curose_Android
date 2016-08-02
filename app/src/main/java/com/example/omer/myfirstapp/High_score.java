package com.example.omer.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;



public  class High_score extends AppCompatActivity {

    private TextView score;
    private TextView name;
    private String newScore;
    private String newLevel;
    private String newName;
    private String state;
    private String newMin, newSec, oldMin, oldSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        restore();
        state = getIntent().getStringExtra("STATE");
        if (state.equals("fromGame"))
            updateScore();
    }

    public void updateScore(){

        newName = getIntent().getStringExtra("USER_NAME");
        newScore = getIntent().getStringExtra("SCORE");
        newLevel = getIntent().getStringExtra("LEVEL");
        newMin = newScore.substring(0, 2);
        newSec = newScore.substring(3, 5);

        switch (newLevel) {
            case "Level 1": {
                score = (TextView) findViewById(R.id.score_01);
                name = (TextView) findViewById(R.id.name_01);
                break;
            }
            case "Level 2": {
                score = (TextView) findViewById(R.id.score_02);
                name = (TextView) findViewById(R.id.name_02);
                break;
            }
            case "Level 3": {
                score = (TextView) findViewById(R.id.score_03);
                name = (TextView) findViewById(R.id.name_03);
                break;
            }
            default:
                break;
        }
        if (name.getText().toString().equals("empty")) {
            store();
        } else {
            oldMin = score.getText().toString().substring(0, 2);
            oldSec = score.getText().toString().substring(3, 5);
            if (Integer.parseInt(newMin) < Integer.parseInt(oldMin)) {
                store();
            } else if (Integer.parseInt(newMin) == Integer.parseInt(oldMin))
                if (Integer.parseInt(newSec) < Integer.parseInt(oldSec)) {
                    store();
                }
        }
        restore();
    }

    private void store(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (newLevel) {
            case "Level 1": {
                editor.putString("NAME1",newName);
                editor.putString("SCORE1",newScore);
            }
            break;
            case "Level 2": {
                editor.putString("NAME2",newName);
                editor.putString("SCORE2",newScore);
            }
            break;
            case "Level 3": {
                editor.putString("NAME3",newName);
                editor.putString("SCORE3",newScore);
            }
            break;
        }
        editor.commit();
    }
    private void restore() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "-";
        String defaultValue2 = "99:99";

        // update level 1
        score = (TextView) findViewById(R.id.score_01);
        name = (TextView) findViewById(R.id.name_01);
        name.setText(sharedPref.getString("NAME1",defaultValue));
        score.setText(sharedPref.getString("SCORE1",defaultValue2));

        // update level 2
        score = (TextView) findViewById(R.id.score_02);
        name = (TextView) findViewById(R.id.name_02);
        name.setText(sharedPref.getString("NAME2",defaultValue));
        score.setText(sharedPref.getString("SCORE2",defaultValue2));

        // update level 3
        score = (TextView) findViewById(R.id.score_03);
        name = (TextView) findViewById(R.id.name_03);
        name.setText(sharedPref.getString("NAME3",defaultValue));
        score.setText(sharedPref.getString("SCORE3",defaultValue2));

    }

    public void goToMenu(View view) {
        Intent goToMenu = new Intent(this,Menu.class);
        startActivity(goToMenu);
    }
}

