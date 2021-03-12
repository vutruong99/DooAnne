package com.dooanne.ui.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dooanne.R;

import java.util.Locale;


public class GameFragment extends BaseFragment implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private ConstraintLayout mConstraintLayout;
    final float threshHold = 3.0f;
    final float guessingToCorrectCutOff = -2.5f;
    final float guessingToPassCutOff = 4.5f;
    TextView mGameText;
    TextView mStartingTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        init(view);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                mStartingTime.setText(String.format(Locale.ENGLISH,"%d", 1 + (millisUntilFinished / 1000)));
            }

            public void onFinish() {
                initSensor();
                mStartingTime.setVisibility(View.GONE);
                mGameText.setText("some random ass words");
            }
        }.start();
        return view;
    }

    private void init(View view) {
        mConstraintLayout = view.findViewById(R.id.gameBackground);
        mGameText = view.findViewById(R.id.gameText);
        mStartingTime = view.findViewById(R.id.startingTime);

    }

    private void initSensor() {
        mSensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        boolean canChangeToCorrect = Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold;
        boolean canChangeToPass = Math.abs(guessingToPassCutOff - event.values[2]) > threshHold;
        boolean canChangeToGuess = (Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold ||
                Math.abs(guessingToPassCutOff - event.values[2]) > threshHold);

        if ((event.values[2] < -3) && (canChangeToCorrect)) {
            //Change screen to Correct
            mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            mGameText.setText(R.string.correct);
        } else if ((event.values[2] > 4) && (canChangeToPass)) {
            //Change screen to Pass
            mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.lightRed));
            mGameText.setText(R.string.pass);
        } else if ((guessingToPassCutOff > event.values[2] && event.values[2] > guessingToCorrectCutOff)
                && (canChangeToGuess)) {
            //Change screen to Guessing mode
            mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mGameText.setText("RANDOM ASS WORD");
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mSensorManager != null) {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}