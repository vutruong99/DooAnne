package com.dooanne.ui.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
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
import com.dooanne.viewmodel.CardsViewModel;
import com.dooanne.viewmodel.DeckAdapter;
import com.dooanne.R;
import com.dooanne.model.Deck;
import com.dooanne.viewmodel.DeckViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class DeckFragment extends Fragment {

    private ArrayList<Deck> deckList;
    private CarouselView mCarouselView;
    private RecyclerView mRecyclerView;
    private DeckAdapter mDeckAdapter;
    private DeckViewModel mDeckViewModel;
    private CardsViewModel mCardsViewModel;
    private ImageView searchButton;

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
        getDecksFromDatabase();
        initRecyclerView(view);
        initCarouselView(view);
        initView(view);

        return view;
    }

    private void initView(View view) {
        searchButton = requireActivity().findViewById(R.id.search_button);
        searchButton.setOnClickListener(searchClickListener);
    }

    private void getDecksFromDatabase() {
        mDeckViewModel = ViewModelProviders.of(this).get(DeckViewModel.class);
        mDeckViewModel.getAllDecks().observe(getViewLifecycleOwner(), new Observer<List<Deck>>() {
            @Override
            public void onChanged(List<Deck> decks) {
                mDeckAdapter.setDecks(decks);
                mDeckAdapter.notifyDataSetChanged();
                mRecyclerView.scheduleLayoutAnimation();
            }
        });
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

        mDeckAdapter = new DeckAdapter(getActivity(), mItemClickListener);

        mCardsViewModel = ViewModelProviders.of(requireActivity()).get(CardsViewModel.class);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 2;
                int spacing = 60;//spacing between views in grid

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

    private void initDecks() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add("Bóng chày");
        cards.add("Bóng bầu dục");
        cards.add("Bóng bàn");
        cards.add("Cầu lông");
        cards.add("Tennis");
        cards.add("Bóng rổ");
        cards.add("Bóng đá");
        cards.add("Nhảy cao");
        cards.add("Ném tạ");
        cards.add("Boxing");
        cards.add("Đi bộ");
        cards.add("Chạy vượt rào");
        cards.add("Đua ngựa");
        cards.add("Thể dục dụng cụ");
        cards.add("Khúc côn cầu trên băng");
        cards.add("Bắn cung");
        cards.add("Bắn súng");
        cards.add("Đá cầu");
        cards.add("Cờ vua");
        cards.add("Bơi");
        deckList.add(new Deck(1,"Thể thao","d",this.getResources().getIdentifier("sports","drawable",requireActivity().getPackageName()),"#594F4F",false,1,cards));
        deckList.add(new Deck(1,"Người nổi tiếng","d",this.getResources().getIdentifier("teamwork","drawable",requireActivity().getPackageName()),"#547980",false,1,cards));
        deckList.add(new Deck(1,"Công việc","d",this.getResources().getIdentifier("suitcase","drawable",requireActivity().getPackageName()),"#45ADA8",false,1,cards));
        deckList.add(new Deck(1,"Đồ ăn","d",this.getResources().getIdentifier("diet","drawable",requireActivity().getPackageName()),"#9DE0AD",false,1,cards));
        deckList.add(new Deck(1,"Thương hiệu","d",this.getResources().getIdentifier("brand","drawable",requireActivity().getPackageName()),"#FE4365",false,1,cards));
    }

    private void createDecks() {
    }

    private final DeckAdapter.ItemClickListener mItemClickListener = new DeckAdapter.ItemClickListener() {
        @Override
        public void onClick(Deck deck) {
            DeckInfoFragment deckInfoFragment = new DeckInfoFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.big_fragment_container, deckInfoFragment)
                    .addToBackStack(null)
                    .commit();
            mCardsViewModel.setCurrentDeck(deck);

        }
    };


    private final View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SearchFragment searchFragment = new SearchFragment();
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.replace(R.id.big_fragment_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

}