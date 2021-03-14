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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dooanne.R;
import com.dooanne.viewmodel.CardsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class GameFragmentFromSearch extends Fragment {
    Context mAppContext;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private ConstraintLayout mConstraintLayout;
    final float threshHold = 3.0f;
    final float guessingToCorrectCutOff = -2.5f;
    final float guessingToPassCutOff = 4.5f;
    TextView mGameText;
    TextView mStartingTime;
    CardsViewModel mCardsViewModel;
    ArrayList<String> mCurrentCards;
    int mCardPosition = 0;
    boolean isDeviceUp = true;
    TextView mTimer;

    // Game variables
    int mScore = 0;
    int mTime = 60000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        initViews(view);
        init();
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                mStartingTime.setText(String.format(Locale.ENGLISH, "%d", 1 + (millisUntilFinished / 1000)));
            }

            public void onFinish() {
                initSensor();
                mStartingTime.setVisibility(View.GONE);
                mGameText.setText(mCurrentCards.get(mCardPosition));

                new CountDownTimer(mTime, 1000) {

                    public void onTick(long millisUntilFinished) {
                        mTimer.setText(String.format(Locale.ENGLISH, "%d", 1 + (millisUntilFinished / 1000)));
                    }

                    public void onFinish() {
                        mStartingTime.setVisibility(View.GONE);
                        mGameText.setText("HẾT GIỜ!");
                        mConstraintLayout.setBackgroundColor(mAppContext.getResources().getColor(R.color.colorPrimary));
                        mSensorManager.unregisterListener(sensorEventListener);

                    }
                }.start();
            }
        }.start();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCardsViewModel = ViewModelProviders.of(requireActivity()).get(CardsViewModel.class);
        mCardsViewModel.getCurrentDeck().observe(getViewLifecycleOwner(), deck -> {
            Toast.makeText(getActivity(), deck.getName(), Toast.LENGTH_LONG).show();
            mCurrentCards = deck.getCards();
            Collections.shuffle(mCurrentCards);
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        mSensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        mCurrentCards = new ArrayList<>();
    }

    private void initViews(View view) {
        mConstraintLayout = view.findViewById(R.id.gameBackground);
        mGameText = view.findViewById(R.id.gameText);
        mStartingTime = view.findViewById(R.id.startingTime);
        mTimer = view.findViewById(R.id.timer);
        mGameText.setText("CHUẨN BỊ NHÁ");

    }

    private void initSensor() {
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSensorManager.registerListener(sensorEventListener, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            boolean canChangeToCorrect = Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold;
            boolean canChangeToPass = Math.abs(guessingToPassCutOff - event.values[2]) > threshHold;
            boolean canChangeToGuess = (Math.abs(guessingToCorrectCutOff - event.values[2]) > threshHold ||
                    Math.abs(guessingToPassCutOff - event.values[2]) > threshHold);

            if ((event.values[2] < -3) && (canChangeToCorrect) && isDeviceUp) {
                // Change screen to Correct
                mScore++;
                isDeviceUp = false;
                mConstraintLayout.setBackgroundColor(mAppContext.getResources().getColor(R.color.lightGreen));
                mGameText.setText(R.string.correct);
            } else if ((event.values[2] > 4) && (canChangeToPass) && isDeviceUp) {
                // Change screen to Pass
                isDeviceUp = false;
                mConstraintLayout.setBackgroundColor(mAppContext.getResources().getColor(R.color.lightRed));
                mGameText.setText(R.string.pass);
            } else if ((guessingToPassCutOff > event.values[2] && event.values[2] > guessingToCorrectCutOff && !isDeviceUp)
                    && (canChangeToGuess)) {
                // Change screen to Guessing mode
                mConstraintLayout.setBackgroundColor(mAppContext.getResources().getColor(R.color.colorPrimary));
                isDeviceUp = true;
                if (mCardPosition + 1 < mCurrentCards.size()) {
                    mCardPosition++;
                    mGameText.setText(mCurrentCards.get(mCardPosition));
                } else {
                    mGameText.setText("Hết bài òi");
                }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mSensorManager != null) {
            mSensorManager.registerListener(sensorEventListener, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(mAppContext == null) {
            mAppContext = context.getApplicationContext();
        }
    }
}