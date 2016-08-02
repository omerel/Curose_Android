package com.example.omer.myfirstapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.omer.myfirstapp.R;

import java.util.Random;

public class Level_3 extends AppCompatActivity {

    private static Chronometer timer;
    private final int num_of_cards = 6;
    private static Intent goToScore;
    private int sum_found = 0;
    private boolean revealed = false;
    private int cardId_revealed = 0;
    private int[] board= new int[num_of_cards*2];
    private int[] array_used_cards= new int[num_of_cards];
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_3);

        // put the name on top left
        name = getIntent().getStringExtra("USER_NAME");
        TextView user_name = (TextView) findViewById(R.id.user_name);
        assert user_name != null;
        user_name.setText(name);
        // put stopwatch on top right
        timer = (Chronometer) findViewById(R.id.timer);
        assert timer != null;
        timer.start();

        // Arrange cards
        initial_game();
    }
    private int random_card(){
        Random rand = new Random();
        return rand.nextInt(num_of_cards);
    }

    private int invertPlace2ImgId(int temp_card){
        int imgId = 0;
        switch (temp_card){
            case 0:{imgId = R.drawable.card1;break;}
            case 1:{imgId = R.drawable.card2;break;}
            case 2:{imgId = R.drawable.card3;break;}
            case 3:{imgId = R.drawable.card4;break;}
            case 4:{imgId = R.drawable.card5;break;}
            case 5:{imgId = R.drawable.card6;break;}
        }
        return imgId;
    }
    private void initial_game() {

        int temp_card;
        // Initial array uesd cards
        for (int i = 0; i < num_of_cards; i++  )
            array_used_cards[i]=-2;

        // initial the board game wit images
        for (int j = 0; j < num_of_cards*2; j++){
            while (true){
                temp_card = random_card();
                if (array_used_cards[temp_card] < 0) {
                    board[j] = invertPlace2ImgId(temp_card);
                    array_used_cards[temp_card]++;
                    break;
                }
            }
        }
    }
    // Return the place of the card in the array
    private int return_card_placed_in_board(int cardId){
        int place = 0;
        switch (cardId){
            case R.id.card1: {place = 0; break;}
            case R.id.card2: {place = 1; break;}
            case R.id.card3: {place = 2; break;}
            case R.id.card4: {place = 3; break;}
            case R.id.card5: {place = 4; break;}
            case R.id.card6: {place = 5; break;}
            case R.id.card7: {place = 6; break;}
            case R.id.card8: {place = 7; break;}
            case R.id.card9: {place = 8; break;}
            case R.id.card10: {place = 9; break;}
            case R.id.card11: {place = 10; break;}
            case R.id.card12: {place = 11; break;}
        }
        return place;
    }
    public void Reveal_card(View view) throws InterruptedException {
        int imgButtonId = view.getId();
        final ImageButton img = (ImageButton) findViewById(imgButtonId);
        final int place_in_board = return_card_placed_in_board(imgButtonId);

        // check if is the first card to revealed or the second
        if(!revealed){
            // if its the first card
            revealed = true;
            // show the card
            img.setImageResource(board[place_in_board]);
            // remember it
            cardId_revealed = imgButtonId;
        }
        else{
            // if the user pressed the same card
            if (imgButtonId == cardId_revealed){
                revealed = false;
                img.setImageResource(R.drawable.back_card);
            }
            else{
                revealed = false;
                // show the card
                img.setImageResource(board[place_in_board]);
                // if cards are match
                if (board[place_in_board] == board[return_card_placed_in_board(cardId_revealed)]){
                    // set clickable off
                    img.setClickable(false);
                    ((ImageButton) findViewById(cardId_revealed)).setClickable(false);
                    sum_found++;
                }
                else {
                    // wait 0.5 sec
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // turn the cards
                            img.setImageResource(R.drawable.back_card);
                            ((ImageButton) findViewById(cardId_revealed)).setImageResource(R.drawable.back_card);
                        }
                    }, 500);
                }
            }
        }
        // if finished
        if ( sum_found == num_of_cards){
            timer.stop();
            // save score and intent to menu
            goToScore = new Intent(this,High_score.class);
            goToScore.putExtra("USER_NAME",name);
            goToScore.putExtra("SCORE",timer.getText());
            goToScore.putExtra("LEVEL","Level 3");
            goToScore.putExtra("STATE","fromGame");

            // create alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Congratulations "+name+" !!!");
            builder.setMessage("You finished the puzzle in "+timer.getText()+" seconds");
            builder.setPositiveButton("Watch score", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    startActivity(goToScore);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
}
