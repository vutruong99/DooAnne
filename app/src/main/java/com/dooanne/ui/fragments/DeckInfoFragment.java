package com.dooanne.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dooanne.R;
import com.dooanne.model.Deck;
import com.dooanne.viewmodel.CardsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.Collections;

public class DeckInfoFragment extends BaseFragment {

    CardsViewModel mCardsViewModel;
    TextView deckName;
    ImageView deckLogo;
    TextView deckDescription;
    MaterialButton playButton;
    Deck mCurrentDeck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck_info, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        mCardsViewModel = ViewModelProviders.of(requireActivity()).get(CardsViewModel.class);
        deckName = view.findViewById(R.id.deckName);
        deckLogo = view.findViewById(R.id.deckLogo);
        deckDescription = view.findViewById(R.id.deckDescription);
        playButton = view.findViewById(R.id.playButton);
        mCardsViewModel.getCurrentDeck().observe(getViewLifecycleOwner(), deck -> {
            mCurrentDeck = deck;
            deckDescription.setText(mCurrentDeck.getDescription());
            deckName.setText(mCurrentDeck.getName());
        });

        playButton.setOnClickListener(v -> {
            GameFragment gameFragment = new GameFragment();
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.big_fragment_container, gameFragment)
                    .addToBackStack(null)
                    .commit();
        });


    }
}