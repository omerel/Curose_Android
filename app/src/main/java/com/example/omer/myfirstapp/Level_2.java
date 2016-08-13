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
import android.widget.Toast;

import java.util.Random;

public class Level_2 extends AppCompatActivity {

    private static Chronometer timer;
    private final int num_of_cards = 4;
    private static Intent goToScore;
    private int sum_found = 0;
    private final int total_imgs = 6;
    private boolean revealed = false;
    private int cardId_revealed = 0;
    private int[] board= new int[num_of_cards*2];
    int imgs_Id[] = new int[total_imgs];
    private String name;


    @Override
    public void onBackPressed() {
        finish();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_2);

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
        Toast.makeText(Level_2.this, "Start Play ", Toast.LENGTH_SHORT).show();
    }

    private int random(int num){
        Random rand = new Random();
        return rand.nextInt(num);
    }

    private  void initial_imgs_and_board(){
        imgs_Id[0]=R.drawable.card1;
        imgs_Id[1]=R.drawable.card2;
        imgs_Id[2]=R.drawable.card3;
        imgs_Id[3]=R.drawable.card4;
        imgs_Id[4]=R.drawable.card5;
        imgs_Id[5]=R.drawable.card6;

        for(int i=0;i<num_of_cards*2;i++)
            board[i]=0;
    }


    private void initial_game() {
        int temp_img;
        int temp_card;
        int count_cards = 0 ;
        // initial array of cards imgs id
        initial_imgs_and_board();

        // initial the board game with images
        while (count_cards<num_of_cards*2){
            temp_img = random(total_imgs);
            if (imgs_Id[temp_img] != 0) {
                // fill 2 cards
                for (int i=0;i<2;i++)
                    while (true){
                        temp_card=random(num_of_cards*2);
                        if (board[temp_card] == 0){
                            board[temp_card] = imgs_Id[temp_img];
                            count_cards++;
                            break;
                        }
                    }
                imgs_Id[temp_img] = 0;
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
            goToScore.putExtra("LEVEL","Level 2");
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
