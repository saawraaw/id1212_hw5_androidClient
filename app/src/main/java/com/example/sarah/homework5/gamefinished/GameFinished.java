package com.example.sarah.homework5.gamefinished;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sarah.homework5.R;
import com.example.sarah.homework5.main.TCPClient;

public class GameFinished extends AppCompatActivity {
    private TextView gameOverMsg ;
    private TextView chosenWord ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
        initialize();
    }

    private void initialize () {
        gameOverMsg = findViewById(R.id.game_over);
        chosenWord = findViewById(R.id.chosen_word);
        Intent intent = getIntent();
        int gameWon = intent.getIntExtra("gameWon" , 0);
        String word = intent.getStringExtra("chosenWord");
        chosenWord.setText(word);
        if (gameWon ==0) {
            gameOverMsg.setText("You Have Lost!");
        } else {
            gameOverMsg.setText("You Have Won!");
        }
    }

    public void playAgain(View view) {
        Intent intent = new Intent(GameFinished.this, TCPClient.class);
        intent.putExtra("playAgain" , true);
        startActivity(intent);
    }
}
