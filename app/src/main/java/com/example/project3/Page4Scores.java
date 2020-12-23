package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Page4Scores extends AppCompatActivity {
    public ListView listView;
    public List scoreList = new ArrayList();
   int playerScore;
  // String playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4_scores);
        final ListView listView = (ListView)  findViewById(R.id.lv_scores);
        DatabaseHandler db = new DatabaseHandler(this);
        db.emptyHiScores();     // empty table if required
        // Inserting hi scores

        Log.i("Insert: ", "Inserting ..");
        // db.addHiScore(new HiScore("20 OCT 2020", "Frodo", 12));
        playerScore = getIntent().getIntExtra("score",0);
       String playerName = getIntent().getStringExtra("name");
        db.addHiScore(new HiScore("28 OCT 2020 ", "Dobby ", 3));
        db.addHiScore(new HiScore("20 NOV 2020 ", "DarthV ", 2));
        db.addHiScore(new HiScore("20 NOV 2020 ", "Bob ", 5));



        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");
        List<HiScore> hiScores = db.getAllHiScores();


        for (HiScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        HiScore singleScore = db.getHiScore(3);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");

        // Calling SQL statement
        List<HiScore> top5HiScores = db.getTopFiveScores();

        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        HiScore hiScore = top5HiScores.get(top5HiScores.size() - 1);
        // hiScore contains the 5th highest score
        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );

        // simple test to add a hi score
        int myCurrentScore = 40;
        // if 5th highest score < myCurrentScore, then insert new score
        if (hiScore.getScore() < myCurrentScore) {
            db.addHiScore(new HiScore("08 DEC 2020", playerName, playerScore));
        }

        Log.i("divider", "====================");

        // Calling SQL statement
        top5HiScores = db.getTopFiveScores();
        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        int j = 1;
        for (HiScore hs : top5HiScores) {

            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();


            // Writing HiScore to log
            Log.i("Score: ", log);
      //      scoreList.add(log);
        }

        Log.i("divider", "====================");
        Log.i("divider", "Scores in list <>>");
        for (String ss : scoresStr) {
            Log.i("Score: ", ss);

        }
        top5HiScores=db.getTopFiveScores();
        for(HiScore hscore : top5HiScores){
            String log = "player " +  hscore.getPlayer_name()+"   "+"score "+hscore.getScore();
            scoreList.add(log);
        }
       // listView.findViewById(R.id.lv_scores);

        ArrayAdapter<HiScore> itemsAdapter =
                new ArrayAdapter<HiScore>(this, android.R.layout.simple_list_item_1, scoreList);

        listView.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();
    }


    public void doPlay(View view) {
        Intent i = new Intent(Page4Scores.this, MainActivity.class);
        startActivity(i);
    }
}