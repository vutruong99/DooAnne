package com.dooanne.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dooanne.R;
import com.dooanne.adapters.ColorAdapter;
import com.dooanne.adapters.IconAdapter;
import com.dooanne.model.CustomColor;
import com.dooanne.model.Icon;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

public class DeckColorFragment extends BaseFragment implements Step {

    ConstraintLayout deckColor;
    ImageView deckIcon;
    FrameLayout deckShadow;
    RecyclerView mColorRecyclerView;
    RecyclerView mIconRecyclerView;
    ColorAdapter mColorAdapter;
    IconAdapter mIconAdapter;
    ArrayList<CustomColor> colors;
    ArrayList<Icon> icons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck_color, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        GravitySnapHelper colorGravitySnapper = new GravitySnapHelper(Gravity.CENTER);
        GravitySnapHelper iconGravitySnapper = new GravitySnapHelper(Gravity.CENTER);

        deckColor = view.findViewById(R.id.deck_view);
        deckShadow = view.findViewById(R.id.deckShadow);
        deckIcon = view.findViewById(R.id.deckImage);
        mColorRecyclerView = view.findViewById(R.id.colorRecyclerView);
        mIconRecyclerView = view.findViewById(R.id.iconRecyclerView);

        mColorRecyclerView.setHasFixedSize(true);
        mColorRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        mIconRecyclerView.setHasFixedSize(true);
        mIconRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        initColors();
        initIcons();

        colorGravitySnapper.attachToRecyclerView(mColorRecyclerView);
        colorGravitySnapper.smoothScrollToPosition(5);

        mColorAdapter = new ColorAdapter(requireActivity());
        mColorAdapter.setOnItemClickListener(new ColorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                colorGravitySnapper.smoothScrollToPosition(position);
                deckColor.setBackgroundColor(Color.parseColor("#" + colors.get(position).getColorCode()));
                deckShadow.setBackgroundColor(Color.parseColor("#80" + colors.get(position).getColorCode()));
            }
        });

        iconGravitySnapper.attachToRecyclerView(mIconRecyclerView);
        iconGravitySnapper.smoothScrollToPosition(5);

        deckColor.setBackgroundColor(Color.parseColor("#" + colors.get(5).getColorCode()));
        deckShadow.setBackgroundColor(Color.parseColor("#80" + colors.get(5).getColorCode()));
        deckIcon.setImageResource(icons.get(5).getIconCode());


        mIconAdapter = new IconAdapter(requireActivity());
        mIconAdapter.setOnItemClickListener(new IconAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                iconGravitySnapper.smoothScrollToPosition(position);
                deckIcon.setImageResource(icons.get(position).getIconCode());

            }
        });

        mColorAdapter.setColors(colors);
        mColorRecyclerView.setAdapter(mColorAdapter);

        mIconAdapter.setIcons(icons);
        mIconRecyclerView.setAdapter(mIconAdapter);

    }

    private void initIcons() {
        icons = new ArrayList<>();
        icons.add(new Icon(R.drawable.sports));
        icons.add(new Icon(R.drawable.teamwork));
        icons.add(new Icon(R.drawable.ukulele));
        icons.add(new Icon(R.drawable.watermelon));
        icons.add(new Icon(R.drawable.boat));
        icons.add(new Icon(R.drawable.sports));
        icons.add(new Icon(R.drawable.teamwork));
        icons.add(new Icon(R.drawable.ukulele));
        icons.add(new Icon(R.drawable.watermelon));
        icons.add(new Icon(R.drawable.boat));
    }

    private void initColors() {
        colors = new ArrayList<>();
        colors.add(new CustomColor("FF5C5C"));
        colors.add(new CustomColor("ffc13b"));
        colors.add(new CustomColor("228B22"));
        colors.add(new CustomColor("FF85CD"));
        colors.add(new CustomColor("FF7F50"));
        colors.add(new CustomColor("FF5C5C"));
        colors.add(new CustomColor("ffc13b"));
        colors.add(new CustomColor("228B22"));
        colors.add(new CustomColor("FF85CD"));
        colors.add(new CustomColor("FF7F50"));
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