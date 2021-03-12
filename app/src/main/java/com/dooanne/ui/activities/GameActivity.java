package com.dooanne.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.dooanne.R;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    ConstraintLayout constraintLayout;
    private Sensor mSensor;
    final float threshHold = 3.0f;
    final float guessingToCorrectCutOff = -2.5f;
    final float guessingToPassCutOff = 4.5f;

    TextView textView1;
    TextView textView2;
    TextView textView3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initSensor();

        textView1 = findViewById(R.id.X);
        textView2 = findViewById(R.id.Y);
        textView3 = findViewById(R.id.Z);
        constraintLayout = findViewById(R.id.background);
    }

    private void initSensor() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        textView1.setText(String.format("%s", event.values[0]));
        textView2.setText(String.format("%s", event.values[1]));
        textView3.setText(String.format("%s", event.values[2]));

        boolean canChangeToCorrect = Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold;
        boolean canChangeToPass = Math.abs(guessingToPassCutOff - event.values[2]) > threshHold;
        boolean canChangeToGuess = (Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold ||
                Math.abs(guessingToPassCutOff - event.values[2]) > threshHold);

        if ((event.values[2] < -3) && (canChangeToCorrect)) {
            //Change screen to Correct
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.purple_700));
        } else if ((event.values[2] > 4) && (canChangeToPass)) {
            //Change screen to Pass
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.purple_200));
        } else if ((guessingToPassCutOff > event.values[2] && event.values[2] > guessingToCorrectCutOff)
                && (canChangeToGuess)) {
            //Change screen to Guessing mode
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
        } 

    }

}
