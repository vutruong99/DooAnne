package com.dooanne.ui.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dooanne.ui.activities.BaseActivity;
import com.dooanne.viewmodel.DeckAdapter;
import com.dooanne.R;
import com.dooanne.model.Deck;
import com.dooanne.viewmodel.DeckViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;


public class DeckFragment extends Fragment {

    private ArrayList<Deck> deckList;
    private CarouselView mCarouselView;
    private RecyclerView mRecyclerView;
    private DeckAdapter mDeckAdapter;
    private DeckViewModel mDeckViewModel;

    int[] sampleImages = {R.drawable.beach1, R.drawable.beach2, R.drawable.river, R.drawable.lake, R.drawable.flowers};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck, container, false);

        createDecks();
        initRecyclerView(view);
        initCarouselView(view);
        return view;
    }

    private void initCarouselView(View view) {
        mCarouselView = view.findViewById(R.id.carousel_view);
        mCarouselView.setPageCount(sampleImages.length);

        mCarouselView.setImageListener(imageListener);
    }

    ImageListener imageListener = (position, imageView) -> imageView.setImageResource(sampleImages[position]);

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_deck);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mDeckViewModel = new ViewModelProvider(requireActivity()).get(DeckViewModel.class);
        mDeckAdapter = new DeckAdapter(getActivity(), mItemClickListener);
        mDeckViewModel = ViewModelProviders.of(this).get(DeckViewModel.class);
        deckList = new ArrayList<>();
        deckList.add(new Deck(1,"Thể thao","d",this.getResources().getIdentifier("sports","drawable",getActivity().getPackageName()),"#594F4F",false,1,new ArrayList<>()));
        deckList.add(new Deck(1,"Người nổi tiếng","d",this.getResources().getIdentifier("teamwork","drawable",getActivity().getPackageName()),"#547980",false,1,new ArrayList<>()));
        deckList.add(new Deck(1,"Công việc","d",this.getResources().getIdentifier("suitcase","drawable",getActivity().getPackageName()),"#45ADA8",false,1,new ArrayList<>()));
        deckList.add(new Deck(1,"Đồ ăn","d",this.getResources().getIdentifier("diet","drawable",getActivity().getPackageName()),"#9DE0AD",false,1,new ArrayList<>()));
        deckList.add(new Deck(1,"Thương hiệu","d",this.getResources().getIdentifier("brand","drawable",getActivity().getPackageName()),"#FE4365",false,1,new ArrayList<>()));
        mDeckAdapter.setDecks(deckList);
        mDeckViewModel.getDeckById(1).observe(getViewLifecycleOwner(), (Deck decks) -> {
            Toast.makeText(getActivity(), decks.getName() + "", Toast.LENGTH_LONG).show();
        });
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 2;
                int spacing = 30;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });
        mRecyclerView.setAdapter(mDeckAdapter);



    }

    private void createDecks() {
    }

    private final DeckAdapter.ItemClickListener mItemClickListener = new DeckAdapter.ItemClickListener() {
        @Override
        public void onClick(Deck deck) {
            Toast.makeText(getContext(), "CLICKED",Toast.LENGTH_LONG).show();
            GameFragment gameFragment = new GameFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.big_fragment_container, gameFragment)
                    .addToBackStack(null)
                    .commit();

        }
    };


}