package com.dooanne;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.dooanne.model.Deck;
import com.dooanne.ui.fragments.GameFragment;
import com.dooanne.viewmodel.CardsViewModel;
import com.dooanne.viewmodel.DeckViewModel;
import com.google.android.material.button.MaterialButton;

public class DeckInfoDialog extends AppCompatDialogFragment {

    CardsViewModel mCardsViewModel;
    DeckViewModel mDeckViewModel;
    ImageView deckLogo;
    TextView deckName;
    TextView deckDescription;
    LinearLayout deckBackground;
    ImageView backButton;
    MaterialButton playButton;
    MaterialButton likeButton;
    Deck currentDeck;
    boolean liked = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_deck_info, null);


        init(view);
        mCardsViewModel.getCurrentDeck().observe(requireActivity(), deck -> {
            currentDeck = deck;
            deckDescription.setText(currentDeck.getDescription());
            deckLogo.setImageResource(currentDeck.getImageLink());
            deckName.setText(currentDeck.getName());
            playButton.setTextColor(Color.parseColor(currentDeck.getColor()));
            Drawable backgroundDrawable = deckBackground.getBackground();
            backgroundDrawable.setColorFilter(Color.parseColor(currentDeck.getColor()), PorterDuff.Mode.SRC_ATOP);
            liked = currentDeck.isFavorite();

        });

        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        Window window = builder.create().getWindow();
//        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);

        return dialog;
    }

    private void init(View view) {
        mCardsViewModel = ViewModelProviders.of(requireActivity()).get(CardsViewModel.class);
        mDeckViewModel = ViewModelProviders.of(requireActivity()).get(DeckViewModel.class);
        deckDescription = view.findViewById(R.id.deckDescription);
        deckName = view.findViewById(R.id.deckName);
        deckLogo = view.findViewById(R.id.deckLogo);
        deckBackground = view.findViewById(R.id.deckInfoBackground);
        backButton = view.findViewById(R.id.backButton);
        playButton = view.findViewById(R.id.playButton);
        likeButton = view.findViewById(R.id.likeButton);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameFragment gameFragment = new GameFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.big_fragment_container, gameFragment)
                        .addToBackStack(null)
                        .commit();

                dismiss();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDeck.setFavorite(!liked);
                liked = !liked;
                if (liked) {
                    likeButton.setIconTintResource(R.color.lightRed);
                    likeButton.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    likeButton.setIconTintResource(R.color.white);
                    likeButton.setStrokeColor(AppCompatResources.getColorStateList(requireContext(),R.color.white));
                    likeButton.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
//                mDeckViewModel.update(currentDeck);
            }
        });
    }
}
