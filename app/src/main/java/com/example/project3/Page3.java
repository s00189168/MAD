package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Page3 extends AppCompatActivity {
EditText name;
TextView score;
int playerScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        name=findViewById(R.id.etName);
        score = findViewById(R.id.tv_score);
       playerScore = getIntent().getIntExtra("score",0);
       score.setText("your score is "+ String.valueOf(playerScore));
    }

    public void doPlay(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void seeScores(View view) {
       Intent i = new Intent(this, Page4Scores.class);
       String n = name.getText().toString();
        i.putExtra("name",n);
        i.putExtra("score",playerScore);
       startActivity(i);


    }
}