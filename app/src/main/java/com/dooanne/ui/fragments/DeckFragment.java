package com.dooanne.ui.fragments;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dooanne.DeckInfoDialog;
import com.dooanne.ScrollToTop;
import com.dooanne.ui.activities.BaseActivity;
import com.dooanne.viewmodel.CardsViewModel;
import com.dooanne.adapters.DeckAdapter;
import com.dooanne.R;
import com.dooanne.model.Deck;
import com.dooanne.viewmodel.DeckViewModel;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CirclePageIndicator;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;


public class DeckFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private ArrayList<Deck> deckList;
    private CarouselView mCarouselView;
    private RecyclerView mRecyclerView;
    private DeckAdapter mDeckAdapter;
    private DeckViewModel mDeckViewModel;
    private CardsViewModel mCardsViewModel;
    private ImageView searchButton;
    private ImageView mHelpButton;
    private ScrollView scrollView;
    private View mScrollSeparator;
    private ImageView deckFilter;
    int sampleImages = R.drawable.picture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deck, container, false);
        setHasOptionsMenu(true);
        createDecks();
        getDecksFromDatabase();
        initRecyclerView(view);
        initCarouselView(view);
        initView(view);



        return view;
    }

    private void initView(View view) {
        BaseActivity baseActivity =  (BaseActivity) requireActivity();

        baseActivity.setListener(mScrollListener);
        scrollView = view.findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0,0);
        mScrollSeparator = requireActivity().findViewById(R.id.scrollSeparator);
        deckFilter = view.findViewById(R.id.deckFilter);
        deckFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        scrollView.getViewTreeObserver().addOnScrollChangedListener(showSeparatorListener);

        searchButton = requireActivity().findViewById(R.id.search_button);
        searchButton.setOnClickListener(searchClickListener);
        mHelpButton = requireActivity().findViewById(R.id.help);
        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorialFragment tutorialFragment = new TutorialFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.big_fragment_container, tutorialFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.deck_type_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allDecks:
                Toast.makeText(requireActivity(), "T???t c???", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.vietnameseDecks:
                Toast.makeText(requireActivity(), "Vi???t Nam", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.internationalDecks:
                Toast.makeText(requireActivity(), "Th??? gi???i", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    private void getDecksFromDatabase() {
        deckList = new ArrayList<>();
        initDecks();
        mDeckViewModel = ViewModelProviders.of(this).get(DeckViewModel.class);
        mDeckViewModel.getAllDecks().observe(getViewLifecycleOwner(), new Observer<List<Deck>>() {
            @Override
            public void onChanged(List<Deck> decks) {

            }
        });
    }

    private void initCarouselView(View view) {
        mCarouselView = view.findViewById(R.id.carousel_view);
        mCarouselView.setPageCount(5);
        mCarouselView.setViewListener(viewListener);
        CirclePageIndicator circlePageIndicator = view.findViewById(R.id._image_carousel_indicator);
        circlePageIndicator.setViewPager(mCarouselView.getContainerViewPager());
    }


    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_deck);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

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
        mDeckAdapter.setDecks(deckList);
        mDeckAdapter.notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
        mRecyclerView.setAdapter(mDeckAdapter);



    }

    private void initDecks() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add("B??ng ch??y");
        cards.add("B??ng b???u d???c");
        cards.add("B??ng b??n");
        cards.add("C???u l??ng");
        cards.add("Tennis");
        cards.add("B??ng r???");
        cards.add("B??ng ????");
        cards.add("Nh???y cao");
        cards.add("N??m t???");
        cards.add("Boxing");
        cards.add("??i b???");
        cards.add("Ch???y v?????t r??o");
        cards.add("??ua ng???a");
        cards.add("Th??? d???c d???ng c???");
        cards.add("Kh??c c??n c???u tr??n b??ng");
        cards.add("B???n cung");
        cards.add("B???n s??ng");
        cards.add("???? c???u");
        cards.add("C??? vua");
        cards.add("B??i");
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        deckList.add(new Deck(1,"Th??? thao","Sport",desc,desc,"ball","FF5C5C",false,false,true,cards));
        deckList.add(new Deck(1,"Ng?????i n???i ti???ng","Famous",desc,desc,"boat","ffc13b",false,false,true,cards));
        deckList.add(new Deck(1,"Ngh??? nghi???p", "job",desc,desc,"watermelon","228B22",false,false,true,cards));
        deckList.add(new Deck(1,"???m th???c Vi???t Nam","food",desc,desc,"ukulele","FF85CD",false,false,true,cards));
        deckList.add(new Deck(1,"Th????ng hi???u","brand",desc,desc,"suitcase","FF7F50",false,false,true,cards));
    }

//    this.getResources().getIdentifier("swimming_suit","drawable",requireActivity().getPackageName())


    private void createDecks() {

    }

    private final DeckAdapter.ItemClickListener mItemClickListener = new DeckAdapter.ItemClickListener() {
        @Override
        public void onClick(Deck deck) {
            DeckInfoDialog deckInfoFragment = new DeckInfoDialog();
            deckInfoFragment.show(getParentFragmentManager(), "deckinfo");
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

    private ScrollToTop mScrollListener = new ScrollToTop() {
        @Override
        public void scrollToTop() {
            scrollView.smoothScrollTo(0,0);
        }
    };


    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.carousel_top_layout, null);

            return customView;
        }
    };

    ViewTreeObserver.OnScrollChangedListener showSeparatorListener = new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
            if (scrollView.getScrollY() != 0 && isVisible()) {
                mScrollSeparator.setVisibility(View.VISIBLE);
            } else {
                mScrollSeparator.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        mScrollSeparator.setVisibility(View.INVISIBLE);
        scrollView.getViewTreeObserver().removeOnScrollChangedListener(showSeparatorListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(showSeparatorListener);
    }

}