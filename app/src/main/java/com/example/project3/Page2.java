package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class contains the actual game play where the user should select from
 * a series of buttons
 */
public class Page2 extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public float [] northValue;
    Button bRed, bBlue, bYellow, bGreen, fb;
    boolean north,south,east,west,neutral;
    public TextView dir;
    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    int[] gameSequence = new int[24];
    int arrayIndex = 0;
    int sequenceCounter = 0;
    public static int sequenceMax =4;
    int[] arrayB = new int[24];
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        bRed = findViewById(R.id.btn_red);
        bBlue = findViewById(R.id.btn_blue);
        bYellow = findViewById(R.id.btn_yellow);
        bGreen = findViewById(R.id.btn_green);
        dir = findViewById(R.id.tv_dirState);
        Bundle b = getIntent().getExtras();
        arrayB = b.getIntArray("numbers");
    }

    public boolean areArraysEqual(int[] array1in, int[] array2in) {
        boolean isSame = true;
        for (int i = 0; i<array1in.length; i++) {
            if (array1in[i]!=array2in[i]) {
                isSame = false;
            }
        }
        return isSame;
    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        // turn off listener to save power
        mSensorManager.unregisterListener(this);
    }

    /*private void flashButton(Button button) {
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

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        /*float[] northC = {-1f,0.1f,0};
        float[] southC = {9.6f,0.14f,0};
        float[] eastC = {7.1f,2.6f,0};
        float[] westC = {7f,1f,0};
        float[] neutralC = {8.5f,0.12f,0};

        // Can we get a north movement
       if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {

                highLimit = true;
                bGreen.setPressed(false);
        }
        if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true)) {
            // we have a tilt to the north
            if(z > 3.4) {
                counter++;
                highLimit = false;
                bGreen.setPressed(true);
                Toast.makeText(this, "north activated", Toast.LENGTH_SHORT).show();
            }
        }


        //north
        if(((x <=northC[0])&& (y<=northC[1]))&& isNeutral()==true){
            greenPress();
            dir.setText("north");
            neutral= false;
            bGreen.setPressed(true);
        }
        else{
           bGreen.setPressed(false);
            highLimit=true;

        }
        //neutral
        if(((x >= neutralC[0])&& (y>=neutralC[1]))){
         directionReset();

          //  Toast.makeText(this, "neutral", Toast.LENGTH_SHORT).show();
            dir.setText("neutral");
        }
        else{

            isNeutral();

        }


        //south
        if(((x < southC[0] )&& (y> southC[1]))&&isNeutral() == true){
           bluePress();
           dir.setText("south");
           neutral = false;
           bBlue.setPressed(true);
        }
        else{
           bBlue.setPressed(false);
            highLimit=true;
        }
    //east
        if((x>eastC[0])&&(y>eastC[1])&& isNeutral() == true){
           yellowPress();
           dir.setText("east");
           neutral = false;
           bYellow.setPressed(true);
        }
        else{
           bYellow.setPressed(false);
            highLimit=true;
        }

        //west

        if((x<westC[0])&&(y<westC[1])&& isNeutral() == true){
            redPress();
            dir.setText("west");
            neutral = false;
            bRed.setPressed(true);
        }
        else{
            bRed.setPressed(false);
            highLimit=true;
        }*/

    }

    /*
    public void directionReset(){
        north = false;
        south = false;
        east= false;
        west = false;
    }
    public boolean isNeutral(){
        boolean answer = false;
        if((north == false)&&(south == false) && (east == false) &&(west == false)){
            answer =true;

        }
        else {
            answer = false;
        }
        return answer;
    }
*/

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    // work with button press
    public void doBlue(View view) {
        gameSequence[arrayIndex++] = BLUE;
        System.out.println("Blue pressed");
        sequenceCheck();
    }

    public void doYellow(View view) {
        gameSequence[arrayIndex++] = YELLOW;
        System.out.println("Yellow pressed");
        sequenceCheck();
    }

    public void doGreen(View view) {
        gameSequence[arrayIndex++] = GREEN;
        System.out.println("Green pressed");
        sequenceCheck();
    }

    public void doRed(View view){
        gameSequence[arrayIndex++] = RED;
        System.out.println("Red pressed");
        sequenceCheck();
    }

    public void sequenceCheck() {

        sequenceCounter++;
        if (sequenceCounter == sequenceMax) {
            score += 2;

            if (areArraysEqual(gameSequence, arrayB)) {
                Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
                sequenceMax += 2;
                int i2 = getIntent().getIntExtra("score", 0);
                Intent i = new Intent(this, MainActivity.class);
                score = score + i2;
                i.putExtra("score", score);
                startActivity(i);


            } else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
                int i2 = getIntent().getIntExtra("totalscore",0);
                Intent i = new Intent(this, Page3.class);

                i.putExtra("score", i2);
                startActivity(i);
            }
        }
    }
}