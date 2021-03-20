package com.dooanne.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dooanne.R;
import com.dooanne.ui.activities.BaseActivity;
import com.google.android.material.button.MaterialButton;


public class CustomFragment extends Fragment {

    MaterialButton addDeckButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        addDeckButton = view.findViewById(R.id.addDeckButton);
        addDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDeckFragment createDeckFragment = new CreateDeckFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.big_fragment_container, createDeckFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


}