package com.dooane;

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

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    ConstraintLayout constraintLayout;
    private Sensor mSensor;

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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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

        // Don't receive any more updates from either sensor.
        mSensorManager.unregisterListener(this);
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    @Override
    public void onSensorChanged(SensorEvent event) {

        textView1.setText(event.values[0] + "");
        textView2.setText(event.values[1] + "");
        textView3.setText(event.values[2] + "");
        float threshHold = 3.0f;
        float guessingToCorrectCutOff = -2.5f;
        float guessingToPassCutOff = 4.5f;
        if ((event.values[2] < -3) && (Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold))  {
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.purple_700));
        } else if ((event.values[2] > 4) && (Math.abs(guessingToPassCutOff - event.values[2]) > threshHold)) {
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.purple_200));
        } else if ((guessingToPassCutOff > event.values[2] && event.values[2] > guessingToCorrectCutOff )
                    && ((Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold) ||
                        Math.abs(guessingToPassCutOff - event.values[2]) > threshHold)) {
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            System.arraycopy(event.values, 0, accelerometerReading,
//                    0, accelerometerReading.length);
//
//        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//            System.arraycopy(event.values, 0, magnetometerReading,
//                    0, magnetometerReading.length);
//
//        }

//        updateOrientationAngles();
    }

    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.

}
