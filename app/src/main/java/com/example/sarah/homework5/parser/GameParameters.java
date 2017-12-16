package com.example.sarah.homework5.parser;

import android.util.Log;

/**
 * Created by Sarah on 12/15/2017.
 */

public class GameParameters {
    private int score ;
    private int attempts ;
    private int gameFinished ;
    private int gameWon ;
    private String current_word ;
    private String chosenWord ;

    public void parseGameParameters (String str) {
        //Log.d("Heeeeeeerrrrrrrrreeee", str);
        String[] split = str.split("#");
        gameFinished = Integer.parseInt(split[0]);
        gameWon = Integer.parseInt(split[1]);
        score = Integer.parseInt(split[2]);
        attempts = Integer.parseInt(split[3]);
        current_word = split[4];
        chosenWord = split[5];
    }

    public int getGameFinished () {
        return gameFinished ;
    }

    public int getGameWon () {
        return gameWon;
    }

    public int getScore (){
        return score;
    }

    public int getAttempts(){
        return attempts;
    }

    public String getCurrent_word () {
        return current_word;
    }

    public String getChosenWord (){
        return chosenWord;
    }
}
