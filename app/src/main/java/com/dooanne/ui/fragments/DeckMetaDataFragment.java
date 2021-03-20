package com.dooanne.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dooanne.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import petrov.kristiyan.colorpicker.ColorPicker;

public class DeckMetaDataFragment extends BaseFragment implements Step {

    MaterialButton colorPickerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck_meta_data, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        colorPickerButton = view.findViewById(R.id.colorPickerBtn);
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(requireActivity());
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position,int color) {
                        if (position != -1) {
                            colorPickerButton.setBackgroundColor(color);
                        }
                    }

                    @Override
                    public void onCancel(){

                    }
                }).setColumns(5).setRoundColorButton(true).show();
            }
        });
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}