package com.example.myapplication;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    int activePlayer = 0;
    boolean gameIsActive = true;
    // 2 unplayed;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //hor
            {0,3,6}, {1,4,7}, {2,5,8}, // ver
            {0,4,8}, {2,4,6}
    };         // dia
    public void dropIn (View view) {
        Button counter = (Button) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2  && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setText("X");
                activePlayer = 1;
            } else {
                counter.setText("O");
                activePlayer = 0;
            }
            counter.animate()
                    .translationYBy(1000f)
                    .rotation(360)
                    .setDuration(500);
            //checking for the winner
            for (int[] winningPosition : winningPositions)
            {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] !=2 )
                {
                    //Someone has won
                    gameIsActive = false;

                    String winner = "O";
                    if (gameState[winningPosition[0]] == 0)
                    {
                        winner = "X";
                    }
                    TextView winnerMsg = (TextView) findViewById(R.id.winnerMsg);
                    winnerMsg.setText(winner + " has won!");
                    ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                    break;
                }  else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState ) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }
                    if (gameIsOver) {
                        TextView winnerMsg = (TextView) findViewById(R.id.winnerMsg);
                        winnerMsg.setText("It is a DRAW!");
                        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);

                    }
                }
            }
        }
    }
    public void playAgain(View view) {
        gameIsActive = true;
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            int White = Color.WHITE;
            ((Button) gridLayout.getChildAt(i)).setBackgroundColor(White);
             ((Button) gridLayout.getChildAt(i)).setText("");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
