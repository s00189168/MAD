package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * This class contains the scores, start button and the first sequence of which buttons should be pressed
 * All additional gameplay takes place is Page2.java
 *
 * Flow of the START of the ROUND:
 * onCreate (upon creating the activity) -> doPlay (upon starting the round) -> create ct + trigger N ticks
 * Move to Page2 (where user enters input and scores are collected
 * Move back to MainActivity where onCreate is triggered again
 *
 * Flow of the NEXT ROUND:
 * doPlay() + previous steps
 */
public class MainActivity extends AppCompatActivity {
    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    Button bRed, bBlue, bYellow, bGreen, fb;
    private Object mutex = new Object();
    int arrayIndex = 0;
    int[] gameSequence = new int[120];
    int totalRoundMilliseconds = 6000;
    int lastRoundScore;
    int totalScore;
    CountDownTimer ct = null;

    public ButtonValuePair selectRandomButton() {
        int randomInt = getRandomIntBetween1AndMaxValue(4);
        Button button = bBlue;
        String name = "";
        int value = 0;
        switch (randomInt) {
            case 1:
                button = bBlue;
                value = BLUE;
                name = "Blue";
                break;
            case 2:
                button = bRed;
                value = RED;
                name = "Red";
                break;
            case 3:
                button = bYellow;
                value = YELLOW;
                name = "Yellow";
                break;
            case 4:
                button = bGreen;
                value = GREEN;
                name = "Green";
                break;
            default:
                break;
        }

        return new ButtonValuePair(button, value, name);
    }

    private CountDownTimer createTimer(int totalRoundMilliseconds) {
        return new CountDownTimer(totalRoundMilliseconds,  1500) {

            public void onTick(long millisUntilFinished) {
                ButtonValuePair button = selectRandomButton();
                flashButton(button.button);
                System.out.println("TICK: " + button.name + " - " + String.valueOf(millisUntilFinished));
                gameSequence[arrayIndex++] = button.value;
            }

            public void onFinish() {
                for (int i = 0; i < arrayIndex; i++) {
                    Log.d("FINISH: Game sequence", String.valueOf(gameSequence[i]));
                }
                Intent i = new Intent(MainActivity.this, Page2.class);
                i.putExtra("numbers", gameSequence);
                i.putExtra("score", totalScore);
                i.putExtra("totalscore", totalScore);
                startActivity(i);
            }
        };
    }

    /**
     * Initialisation of game state
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("ON CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // listView = findViewById(R.id.lv);
        bRed = findViewById(R.id.btn_red);
        bBlue = findViewById(R.id.btn_blue);
        bYellow = findViewById(R.id.btn_yellow);
        bGreen = findViewById(R.id.btn_green);
    }

    /**
     * This is triggered upon clicking the PLAY button every round
     * @param view
     */
    public void doPlay(View view) {
        System.out.println("DO PLAY");
        gameSequence = new int[120];
        lastRoundScore = getIntent().getIntExtra("score",0);
        totalScore = totalScore + lastRoundScore;

        System.out.println(" -> Total score is currently: " + String.valueOf(totalScore));
        if (totalScore > 0 && totalScore <= 3){
            totalRoundMilliseconds += 3000;

        } else if (totalScore > 3 && totalScore <= 5 ){
            totalRoundMilliseconds += 6000;

        }  else if (totalScore >= 6) {
            totalRoundMilliseconds += 9000;
        }

        // Create an instance of the countdown timer set to the correct expected milliseconds
        ct = createTimer(totalRoundMilliseconds);

        System.out.println(" -> Total MS to use: " + String.valueOf(totalRoundMilliseconds));
        ct.start();
    }

    public int getRandomIntBetween1AndMaxValue(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);
            }
        };
        handler.postDelayed(r, 600);
    }

    public void doBlue(View view) {
    }

    public void doYellow(View view) {
    }

    public void doGreen(View view) {
    }

    public void doRed(View view) {
    }
}

class ButtonValuePair {
    public Button button;
    public Integer value;
    public String name;

    public ButtonValuePair(Button button, Integer value, String name) {
        this.button = button;
        this.value = value;
        this.name = name;
    }
}
