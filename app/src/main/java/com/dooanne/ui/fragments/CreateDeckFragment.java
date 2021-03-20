package com.dooanne.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dooanne.R;
import com.dooanne.StepperAdapter;
import com.dooanne.ui.activities.BaseActivity;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class CreateDeckFragment extends BaseFragment implements StepperLayout.StepperListener {

    private StepperLayout mStepperLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_deck, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showCreateDeckFragment(view);
    }

    public void showCreateDeckFragment(View view) {
        Toast.makeText(getActivity(),"A",Toast.LENGTH_SHORT).show();
        mStepperLayout = (StepperLayout) view.findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getChildFragmentManager(), requireActivity()));
    }

    @Override
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        getParentFragmentManager().popBackStack();
    }
}