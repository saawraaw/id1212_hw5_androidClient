package com.example.sarah.homework5.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sarah.homework5.R;
import com.example.sarah.homework5.gamefinished.GameFinished;
import com.example.sarah.homework5.parser.GameParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient extends AppCompatActivity {
    private Socket clientSocket ;
    private BufferedReader fromServer ;
    private PrintWriter toServer ;
    private TextView attempts_view ;
    private TextView score_view ;
    private EditText guess_view ;
    private TextView current_word_view ;
    private TextView guesses;
    private GameParameters gameParameters ;
    private Boolean playAgain = false;
    String guessed_values = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);

        initialize();
    }

    private void initialize (){
        Intent intent = getIntent();
        playAgain = intent.getBooleanExtra("playAgain" , false);
        fromServer = ConnectionManager.getFromServer();
        if(fromServer==null)
            Log.e(this.getClass().toString(), "input buffer is null");
        toServer = ConnectionManager.getToServer();
        if(toServer==null)
            Log.e(this.getClass().toString(), "output is null");
        attempts_view = findViewById(R.id.attempts_value);
        score_view = findViewById(R.id.score_value);
        guess_view = findViewById(R.id.user_input);
        current_word_view = findViewById(R.id.current_word);
        guesses = findViewById(R.id.previously_guessed_chars);
        gameParameters = new GameParameters();
        if (playAgain) {
            new Sender().execute("play");
        } else {
            new Receiver().execute();
        }

    }


    public void sendGuessToServer(View view) {
        Log.d(this.getClass().toString(), "Size of the read input: " + guess_view.getText().toString().length() );
        guessed_values += guess_view.getText().toString() + ", " ;
        guesses.setText(guessed_values);
        new Sender().execute(guess_view.getText().toString());
        guess_view.setText(null);
    }

    private class Receiver extends AsyncTask <Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String str = fromServer.readLine();
                gameParameters.parseGameParameters(str);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(gameParameters.getGameFinished()==0) {
                setGUIParameters();
            } else {
                Intent intent = new Intent(TCPClient.this, GameFinished.class);
                intent.putExtra("gameWon",gameParameters.getGameWon());
                intent.putExtra("chosenWord" , gameParameters.getChosenWord());
                startActivity(intent);
            }
        }


        private void setGUIParameters () {
            attempts_view.setText(String.valueOf(gameParameters.getAttempts()));
            score_view.setText(String.valueOf(gameParameters.getScore()));
            current_word_view.setText(gameParameters.getCurrent_word());

        }
    }

    private class Sender extends AsyncTask <String,Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            toServer.println(strings[0]);
            toServer.flush();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            new Receiver().execute();
        }
    }


}
