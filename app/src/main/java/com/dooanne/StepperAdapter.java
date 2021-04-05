package com.dooanne;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.dooanne.ui.fragments.DeckMetaDataFragment;
import com.dooanne.ui.fragments.DeckColorFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class StepperAdapter extends AbstractFragmentStepAdapter {
    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position){
            case 0:
                DeckMetaDataFragment step1 = new DeckMetaDataFragment();
                Bundle b1 = new Bundle();
                b1.putInt("CURRENT_STEP_POSITION_KEY", position);
                step1.setArguments(b1);
                return step1;
            case 1:
                DeckColorFragment step2 = new DeckColorFragment();
                Bundle b2 = new Bundle();
                b2.putInt("CURRENT_STEP_POSITION_KEY", position);
                step2.setArguments(b2);
                return step2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        switch (position){
            case 0:
                return new StepViewModel.Builder(context)
                        .setTitle("Tabs 1") //can be a CharSequence instead
                        .create();
            case 1:
                return new StepViewModel.Builder(context)
                        .setTitle("Tabs 2") //can be a CharSequence instead
                        .create();
        }
        return null;
    }
}
