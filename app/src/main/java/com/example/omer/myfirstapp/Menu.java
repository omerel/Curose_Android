package com.example.omer.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void validate(View view) {
        ToggleButton btn1 = (ToggleButton)findViewById(R.id.toggleButton_level1);
        ToggleButton btn2 = (ToggleButton)findViewById(R.id.toggleButton_level2);
        ToggleButton btn3 = (ToggleButton)findViewById(R.id.toggleButton_level3);
        EditText user_name = (EditText)findViewById(R.id.user_name);
        boolean valid = btn1.isChecked() | btn2.isChecked() | btn3.isChecked();
        if (user_name.getText().toString().trim().equals("")) {
            user_name.setError( "First name is required!" );
        }else {
            if (valid != true) {
                Toast.makeText(Menu.this, "Please choose level ", Toast.LENGTH_SHORT).show();
            }else {
                Intent startLevel;
                if (btn1.isChecked())
                    startLevel = new Intent(this,Level_1.class);
                else if (btn2.isChecked())
                    startLevel = new Intent(this,Level_2.class);
                else
                    startLevel = new Intent(this,Level_3.class);
                startLevel.putExtra("USER_NAME",user_name.getText().toString());
                startActivity(startLevel);
            }
        }
    }

    public void toggleButton_clicked(View view) {
        int btnId  = view.getId();
        ToggleButton btn;
        switch (btnId) {
            case R.id.toggleButton_level1: {
                btn = (ToggleButton)findViewById(R.id.toggleButton_level2);
                btn.setChecked(false);
                btn = (ToggleButton)findViewById(R.id.toggleButton_level3);
                btn.setChecked(false);
            }
            break;
            case R.id.toggleButton_level2: {
                btn = (ToggleButton)findViewById(R.id.toggleButton_level1);
                btn.setChecked(false);
                btn = (ToggleButton)findViewById(R.id.toggleButton_level3);
                btn.setChecked(false);
            }
            break;
            case R.id.toggleButton_level3: {
                btn = (ToggleButton)findViewById(R.id.toggleButton_level2);
                btn.setChecked(false);
                btn = (ToggleButton)findViewById(R.id.toggleButton_level1);
                btn.setChecked(false);
            }
            break;
        }
    }

    public void goToHighScore(View view) {
        Intent goToScore = new Intent(this,High_score.class);
        goToScore.putExtra("STATE","fromMenu");
        startActivity(goToScore);
    }
}
